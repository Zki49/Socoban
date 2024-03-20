package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.List;

public class MapPlay extends Map{
    private static int MapWidth;
    private static int MapHeight;
    private final SimpleIntegerProperty mapWidth = new SimpleIntegerProperty();
    private final SimpleIntegerProperty mapHeight = new SimpleIntegerProperty();
    private final SimpleIntegerProperty totalCells = new SimpleIntegerProperty(mapWidth.getValue() * mapHeight.getValue());
    private final CellPlay[][] cellPlay;
    private MapDesign mapDesign;



    private List<String> elementsFromFile;
    private  BooleanBinding isWon;
    private BooleanBinding isNotWon;
    private IntegerBinding numberGoals;
    private IntegerBinding numberBoxOnGoal;

    public SimpleIntegerProperty scoreProperty() {
        return Score;
    }

    private SimpleIntegerProperty Score = new SimpleIntegerProperty(0);

    private LongBinding cellWithObject;

    private Point currentCellWithPlayer;

    public int getNumberBoxe(int line, int col) {
      return   cellPlay[line][col].getIndexOfBoxe();
    }

    //je crée une classe Point pour enregistré la cellul où le joueur se trouve
    class Point{
        int col;
        int line;
        Point(int line, int col){
            this.col = line;
            this.line = col;
        }
    }





    /*les variables contains sont calculer par rapport cells et recalculer a chaque changement dans cells*/
    /*
     * il y a deux constructeurs , un qui est utilisé lorsqu'on crée un map à partir d'une largeur/hauteur un autre à partir d'une Liste de String/largeur/hauteur
     * */
    MapPlay(int mapWidth, int mapHeight) {

        this(null, mapWidth, mapHeight);



    }

    public MapPlay(List<String> elementsFromFile, int mapWidth, int mapHeight) {
        MapWidth = mapWidth;
        MapHeight = mapHeight;
        this.mapWidth.set(MapWidth);
        this.mapHeight.set(MapHeight);
        cellPlay = new CellPlay[mapHeight][mapWidth];

        cellWithObject = Bindings.createLongBinding(
                () -> Arrays.stream(cellPlay).flatMap(Arrays::stream).filter(cellDesign -> cellDesign.containsObjectInMap()).count()
        );
        if(elementsFromFile != null){

            this.elementsFromFile = elementsFromFile;
            fillMapByFile();
        }
        else
            fillMap();
    }

    public MapPlay(MapDesign mapDesign){
        this.mapDesign = mapDesign;
        MapWidth = mapDesign.getMapWidth();
        MapHeight = mapDesign.getMapHeight();
        this.mapWidth.set(MapWidth);
        this.mapHeight.set(MapHeight);
        cellPlay = new CellPlay[MapHeight][MapWidth];
        Box.setIndex(0);
        fillMapByMap();
        findPlayer();
        isWon = Bindings.createBooleanBinding(() ->numberBoxOnGoal.get() == numberGoals.get());
        isNotWon = Bindings.createBooleanBinding(() ->numberBoxOnGoal.get()!= numberGoals.get());
        numberGoals = Bindings.createIntegerBinding(() -> Math.toIntExact(Arrays.stream(cellPlay).flatMap(Arrays::stream).
                filter(cellPlay -> cellPlay.containsGoal()).count()));
        numberBoxOnGoal = Bindings.createIntegerBinding(() -> Math.toIntExact(Arrays.stream(cellPlay).flatMap(Arrays::stream).
                filter(cellPlay -> cellPlay.containsGoal() && cellPlay.containsBox()).count()));


    }
    public void invalidateBiddings(){
        numberGoals.invalidate();
        numberBoxOnGoal.invalidate();
        isWon.invalidate();
        isNotWon.invalidate();
    }

    public Boolean getIsNotWon() {
        return isNotWon.get();
    }

    public BooleanBinding isNotWonProperty() {
        return isNotWon;
    }

    public Boolean getIsWon() {
        return isWon.get();
    }

    public BooleanBinding isWonProperty() {
        return isWon;
    }

    public Number getNumberGoals() {
        return numberGoals.get();
    }

    public IntegerBinding numberGoalsProperty() {
        return numberGoals;
    }

    public Number getNumberBoxOnGoal() {
        return numberBoxOnGoal.get();
    }

    public IntegerBinding numberBoxOnGoalProperty() {
        return numberBoxOnGoal;
    }

    private void fillMapByMap() {
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                cellPlay[i][j] = new CellPlay(mapDesign.getCellByLineColonne(i,j));
            }
        }
    }

    private void findPlayer() {
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                if (cellPlay[i][j].containsPlayer()) {
                    currentCellWithPlayer = new Point(i, j);
                }
            }
        }
    }
    //fonction appelé lorsque je veux monter et je verifie que je ne suis pas sur le premiere ligne
    public void moveUp() {

        if(currentCellWithPlayer.col > 0 ){

            if(availableCell(currentCellWithPlayer.col-1, currentCellWithPlayer.line)){
                if(cellPlay[currentCellWithPlayer.col-1][currentCellWithPlayer.line].containsBox()){
                    if(availableCellForBox(currentCellWithPlayer.col-2, currentCellWithPlayer.line)){
                        deletePlayer();
                        cellPlay[currentCellWithPlayer.col-1][currentCellWithPlayer.line].deleteByIdx(0);
                        addPlayer(currentCellWithPlayer.line, currentCellWithPlayer.col-1);
                        cellPlay[currentCellWithPlayer.col-1][currentCellWithPlayer.line].addObjectInMap("BOX");
                        scoreProperty().set(scoreProperty().get()+1);
                    }
                }
                else{
                    deletePlayer();
                    addPlayer(currentCellWithPlayer.line, currentCellWithPlayer.col-1);
                    scoreProperty().set(scoreProperty().get()+1);
                }

            }

            //System.out.println("map height: "+ MapHeight +"map Width: " + MapWidth + " player point line"+ currentCellWithPlayer.line + "col"+ currentCellWithPlayer.col);
            invalidateBiddings();
        }
    }
    public void moveDown(){
        if (currentCellWithPlayer.col < MapHeight-1){
            if(availableCell(currentCellWithPlayer.col+1, currentCellWithPlayer.line)){
                if(cellPlay[currentCellWithPlayer.col+1][currentCellWithPlayer.line].containsBox()){
                    if(availableCellForBox(currentCellWithPlayer.col+2, currentCellWithPlayer.line)){
                        deletePlayer();
                        cellPlay[currentCellWithPlayer.col+1][currentCellWithPlayer.line].deleteByIdx(0);
                        addPlayer(currentCellWithPlayer.line, currentCellWithPlayer.col+1);
                        cellPlay[currentCellWithPlayer.col+1][currentCellWithPlayer.line].addObjectInMap("BOX");
                        scoreProperty().set(scoreProperty().get()+1);
                    }
                }
                else{
                    deletePlayer();
                    addPlayer(currentCellWithPlayer.line, currentCellWithPlayer.col+1);
                    scoreProperty().set(scoreProperty().get()+1);
                }
            }
        }
        invalidateBiddings();

    }
    private boolean availableCell(int col , int line){
        if(col < 0 || col >= MapHeight || line < 0 || line >= MapWidth)
            return false;
        if(cellPlay[col][line].containsWall())
            return false;
        return true;
    }
    private boolean availableCellForBox(int col , int line){
        if(col < 0 || col >= MapHeight || line < 0 || line >= MapWidth)
            return false;
        if(cellPlay[col][line].containsWall() || cellPlay[col][line].containsBox())
            return false;
        return true;
    }

    public void moveLeft(){
        if (currentCellWithPlayer.line > 0){
            if(availableCell(currentCellWithPlayer.col, currentCellWithPlayer.line - 1)){
                if(cellPlay[currentCellWithPlayer.col][currentCellWithPlayer.line -1].containsBox()){
                    if(availableCellForBox(currentCellWithPlayer.col, currentCellWithPlayer.line -2)){
                        deletePlayer();
                        cellPlay[currentCellWithPlayer.col][currentCellWithPlayer.line -1].deleteByIdx(0);
                        addPlayer(currentCellWithPlayer.line -1 , currentCellWithPlayer.col );
                        cellPlay[currentCellWithPlayer.col][currentCellWithPlayer.line -1 ].addBoxInGame();
                        scoreProperty().set(scoreProperty().get()+1);
                    }
                }

                else{
                    deletePlayer();
                    addPlayer(currentCellWithPlayer.line -1 , currentCellWithPlayer.col);
                    scoreProperty().set(scoreProperty().get()+1);
                }
            }


        }
        invalidateBiddings();
    }
    public void moveRight(){
        if (currentCellWithPlayer.line < MapWidth-1){
            if(availableCell(currentCellWithPlayer.col, currentCellWithPlayer.line + 1)){
                if(cellPlay[currentCellWithPlayer.col][currentCellWithPlayer.line +1].containsBox()){
                    if(availableCellForBox(currentCellWithPlayer.col, currentCellWithPlayer.line +2)){
                        deletePlayer();
                        cellPlay[currentCellWithPlayer.col][currentCellWithPlayer.line +1].deleteByIdx(0);
                        addPlayer(currentCellWithPlayer.line +1 , currentCellWithPlayer.col );
                        cellPlay[currentCellWithPlayer.col][currentCellWithPlayer.line +1 ].addBoxInGame();
                        scoreProperty().set(scoreProperty().get()+1);
                    }
                }

                else{
                    deletePlayer();
                    addPlayer(currentCellWithPlayer.line +1 , currentCellWithPlayer.col);
                    scoreProperty().set(scoreProperty().get()+1);
                }
            }

        }
        //System.out.println(MapHeight + " player point line"+ currentCellWithPlayer.line + "col"+ currentCellWithPlayer.col);
        invalidateBiddings();
    }

    private void addPlayer(int col, int line) {
        cellPlay[line][col].addPlayer();
        currentCellWithPlayer = new Point(line, col);
    }

    @Override
    public String toString() {
        String map = "";
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                if (j == MapWidth - 1) {
                    map += cellPlay[i][j] + "\n";
                } else {
                    map += cellPlay[i][j];
                }

            }
            System.out.println();

        }
        return map;
    }

    public ObservableList<ObjectInMap> getObjectList(int line, int col) {
        return cellPlay[line][col].getObjectList();
    }


    public void fillMap() {
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                cellPlay[i][j] = new CellPlay();
            }
        }
    }

    public void fillMapByFile() {

        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                String symbole = String.valueOf(elementsFromFile.get(i).charAt(j));
                cellPlay[i][j] = new CellPlay(symbole);

            }

        }
    }



    public CellPlay getCellContainsPlayer() {
        CellPlay cellPlay = null;
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                cellPlay = getCellByLineColonne(j, i);
                if (cellPlay.containsPlayer()) {
                    return cellPlay;
                }
            }
        }
        return cellPlay;
    }


    public static int getMapdWidth() {
        return MapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.MapWidth = mapWidth;
    }

    public static int getMapHeight() {
        return MapHeight;
    }

    public int getMapWidth() {
        return mapWidth.get();
    }

    public SimpleIntegerProperty mapWidthProperty() {
        return mapWidth;
    }

    public SimpleIntegerProperty mapHeightProperty() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.MapHeight = mapHeight;
    }

    public CellPlay[][] getCells() {
        return cellPlay;
    }

    public CellPlay getCellByLineColonne(int line, int colonne) {
        return cellPlay[line][colonne];
    }

    public int getSize() {
        return MapWidth * MapHeight;
    }

    public int getTotalCells() {
        return totalCells.get();
    }

    public SimpleIntegerProperty totalCellsProperty() {
        return totalCells;
    }

    public void setTotalCells(int totalCells) {
        this.totalCells.set(totalCells);
    }

    //il faut verifier que l'on est pas au max de cellavailable (si c'est le cas on verifie si la cellule est vide si oui on annule)/*
    // si on ajoute un object on appel invalidatebidings  */

    public void addObject(int x, int y, String currentObject) {


        if (currentObject.equals("PLAYER") && !notContainsPlayer() ) {
            deletePlayer();
        }

        if(cellWithObject.get() >= (this.getSize()/2)-1 && getCellByLineColonne(x,y).containsObjectInMap() || cellWithObject.get() <= (this.getSize()/2)-1) {
            cellPlay[x][y].addObjectInMap(currentObject);
        }




    }



    private boolean notContainsPlayer() {
        for(int i = 0; i < MapHeight; i++) {
            for(int j = 0; j < MapWidth; j++) {
                CellPlay cellPlay = getCellByLineColonne(i,j);
                if (cellPlay.containsPlayer()){
                    return false;
                }
            }
        }
        return true;
    }


    private void deletePlayer() {

        for(int i = 0; i < MapHeight; i++) {
            for(int j = 0; j < MapWidth; j++) {
                CellPlay cellPlay = getCellByLineColonne(i,j);
                if (cellPlay.containsPlayer()){
                    cellPlay.deleteByIdx(0);

                }
            }
        }

    }

    //si on vide une cellule on appel invalidatebidings  */
    public void emptyCell(int x, int y) {
        cellPlay[x][y].delete();

    }

    /*invalider les bidings permet de les obligé a recalculer leur valeurs */






    public LongBinding cellWithObjectProperty() {
        return cellWithObject;
    }
}

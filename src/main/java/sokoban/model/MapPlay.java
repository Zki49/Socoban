package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

class MapPlay extends Map{
    private final CellPlay[][] cellPlay;
    private final HashMap<Point, ObservableList<ObjectInMap> > objectInMapList = new HashMap<>();
    private MapDesign mapDesign;




    private  BooleanBinding isWon;
    private BooleanBinding isNotWon;
    private IntegerBinding numberGoals;
    private IntegerBinding numberBoxOnGoal;
    private SimpleBooleanProperty showMushroom = new SimpleBooleanProperty(false);

    public SimpleIntegerProperty scoreProperty() {
        return Score;
    }

    private SimpleIntegerProperty Score = new SimpleIntegerProperty(0);

    private LongBinding cellWithObject;
    private List<Integer> shuffledBox = new ArrayList<Integer>();

    private Point currentCellWithPlayer;
    private Point currentCellWithMushroom;



    //je crée une classe Point pour enregistré la cellul où le joueur se trouve
    class Point{
        int col;
        int line;
        Point(int line, int col){
            this.col = line;
            this.line = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point point)) return false;
            return col == point.col && line == point.line;
        }

        @Override
        public int hashCode() {
            return Objects.hash(col, line);
        }
    }

    MapPlay(MapDesign mapDesign){
        this.mapDesign = mapDesign;
        MapWidth = mapDesign.getMapWidth();
        MapHeight = mapDesign.getMapHeight();
        this.mapWidth.set(MapWidth);
        this.mapHeight.set(MapHeight);
        cellPlay = new CellPlay[MapHeight][MapWidth];
        Box.resetIndex();
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
    public int getNumberBoxe(int line, int col) {
        return   cellPlay[line][col].getIndexOfBoxe();
    }


    public void reduceScore(int index) {
        scoreProperty().setValue(scoreProperty().getValue()-index);
    }

    public void incrementScore(int penality) {
        scoreProperty().setValue(scoreProperty().getValue()+penality);
    }

    public void showMushroom() {
        showMushroom.setValue(!showMushroom.getValue());
    }

    public boolean isShowMushroom() {
        return showMushroom.get();
    }

    public SimpleBooleanProperty showMushroomProperty() {
        return showMushroom;
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
                if(mapDesign.getCellByLineColonne(i,j).containsObjectInMap()){
                    cellPlay[i][j] =  new CellPlay(mapDesign.getCellByLineColonne(i,j));
                    addObjectInList(cellPlay[i][j], new Point(i,j));
                }
                else{
                    cellPlay[i][j] = new CellPlay();
                }


            }
        }
        addMushroom();
    }

    private void addMushroom() {

        boolean flag = true;

        while (flag) {
            Random random = new Random();
            int line = random.nextInt(1,MapHeight);
            int col = random.nextInt(1,MapWidth);
            if (!cellPlay[line][col].containsObjectInMap()) {
                cellPlay[line][col].addMushroom();
                currentCellWithMushroom = new Point(line, col);
                flag = false;
            }
        }
    }
    public boolean containsMushroom(int line, int col) {
        return cellPlay[line][col].containsMushroom();
    }

    public void resetMushroom(){
        cellPlay[currentCellWithMushroom.col][currentCellWithMushroom.line].deleteMushroom();
        addMushroom();
    }
    public void shuffleBox() {

        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                if (cellPlay[i][j].containsBox()) {
                    Box box = cellPlay[i][j].getBox();
                        cellPlay[i][j].deleteByIdx(0);
                        findRandomCell(box);

                }
            }
        }
    }
    private void findRandomCell(ObjectInMap box) {
        Random random = new Random();
        int line, col;
        boolean flag = true;
        while (flag) {
             line = random.nextInt(1,MapHeight);
             col = random.nextInt(1,MapWidth);
             if(availableCellForBox(line, col)) {
                 cellPlay[line][col].addObject(box);
                 flag = false;
             }

        }
    }

    private void addObjectInList(CellPlay cellPlay, Point point){

            ObservableList<ObjectInMap> list = FXCollections.observableArrayList();
           list.addAll(cellPlay.getObjectList());
            objectInMapList.put(point, list);


    }

    private void copyObject(CellPlay cell, ObservableList<ObjectInMap> listToCopy) {

        for(ObjectInMap objectInMap : listToCopy){

            try{
                cell.addObject(objectInMap);
            }catch (Exception e){
                System.out.println(e.getMessage() + "  " + objectInMap.getweight());
            }


        }
    }

    public void resetMap(){
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                if(objectInMapList.getOrDefault(new Point(i,j), null) != null){

                    cellPlay[i][j].reset();
                    copyObject(cellPlay[i][j], objectInMapList.get(new Point(i,j)));
                }
                else{
                    cellPlay[i][j].reset();
                }
            }
        }

        findPlayer();
        resetMushroom();

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
    public void moveBack(LastMove lastMove, Box box) {
        Point playerLocation = new Point(currentCellWithPlayer.col, currentCellWithPlayer.line);

        switch (lastMove){
            case UP -> moveDown();
            case DOWN -> moveUp();
            case RIGHT -> moveLeft();
            default -> moveRight();
        }
        if(box != null){
            deleteBox(box);
            cellPlay[playerLocation.col][playerLocation.line].addObject(box);
        }
        }

    private void deleteBox(Box box) {
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                if (cellPlay[i][j].containsBox()) {
                    Box boxInCell = cellPlay[i][j].getBox();
                  if(boxInCell.getIndex() == box.getIndex()){
                      cellPlay[i][j].deleteByIdx(0);

                  }

                }
            }
        }
    }

    //fonction appelé lorsque je veux monter et je verifie que je ne suis pas sur le premiere ligne
    public Box moveUp() {
        Box boxMoved = null;
        if(currentCellWithPlayer.col > 0 ){

            if(availableCell(currentCellWithPlayer.col-1, currentCellWithPlayer.line)){
                if(cellPlay[currentCellWithPlayer.col-1][currentCellWithPlayer.line].containsBox()){
                    if(availableCellForBox(currentCellWithPlayer.col-2, currentCellWithPlayer.line)){
                        deletePlayer();
                        Box box = cellPlay[currentCellWithPlayer.col-1][currentCellWithPlayer.line].getBox();
                        boxMoved = box;
                        cellPlay[currentCellWithPlayer.col-1][currentCellWithPlayer.line].deleteByIdx(0);
                        addPlayer(currentCellWithPlayer.line, currentCellWithPlayer.col-1);
                        cellPlay[currentCellWithPlayer.col-1][currentCellWithPlayer.line].addObject(box);
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
        return boxMoved;
    }
    public Box moveDown(){
        Box boxMoved = null;
        if (currentCellWithPlayer.col < MapHeight-1){
            if(availableCell(currentCellWithPlayer.col+1, currentCellWithPlayer.line)){
                if(cellPlay[currentCellWithPlayer.col+1][currentCellWithPlayer.line].containsBox()){
                    if(availableCellForBox(currentCellWithPlayer.col+2, currentCellWithPlayer.line)){
                        deletePlayer();
                        Box box = cellPlay[currentCellWithPlayer.col+1][currentCellWithPlayer.line].getBox();
                        boxMoved = box;
                        cellPlay[currentCellWithPlayer.col+1][currentCellWithPlayer.line].deleteByIdx(0);
                        addPlayer(currentCellWithPlayer.line, currentCellWithPlayer.col+1);
                        cellPlay[currentCellWithPlayer.col+1][currentCellWithPlayer.line].addObject(box);
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
        return boxMoved;
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

    public Box moveLeft(){
        Box boxMoved = null;
        if (currentCellWithPlayer.line > 0){
            if(availableCell(currentCellWithPlayer.col, currentCellWithPlayer.line - 1)){
                if(cellPlay[currentCellWithPlayer.col][currentCellWithPlayer.line -1].containsBox()){
                    if(availableCellForBox(currentCellWithPlayer.col, currentCellWithPlayer.line -2)){
                        deletePlayer();
                        Box box = cellPlay[currentCellWithPlayer.col][currentCellWithPlayer.line - 1].getBox();
                        boxMoved = box;
                        cellPlay[currentCellWithPlayer.col][currentCellWithPlayer.line -1].deleteByIdx(0);
                        addPlayer(currentCellWithPlayer.line -1 , currentCellWithPlayer.col );

                        cellPlay[currentCellWithPlayer.col][currentCellWithPlayer.line -1 ].addObject(box);
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
        return boxMoved;
    }
    public Box moveRight(){
        Box boxMoved = null;
        if (currentCellWithPlayer.line < MapWidth-1){
            if(availableCell(currentCellWithPlayer.col, currentCellWithPlayer.line + 1)){
                if(cellPlay[currentCellWithPlayer.col][currentCellWithPlayer.line +1].containsBox()){
                    if(availableCellForBox(currentCellWithPlayer.col, currentCellWithPlayer.line +2)){
                        deletePlayer();
                        Box box = cellPlay[currentCellWithPlayer.col][currentCellWithPlayer.line + 1].getBox();
                        boxMoved = box;
                        cellPlay[currentCellWithPlayer.col][currentCellWithPlayer.line +1].deleteByIdx(0);
                        addPlayer(currentCellWithPlayer.line +1 , currentCellWithPlayer.col );
                        cellPlay[currentCellWithPlayer.col][currentCellWithPlayer.line +1 ].addObject(box);
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
        return boxMoved;
    }

    private void addPlayer(int col, int line) {
        cellPlay[line][col].addPlayer();
        currentCellWithPlayer = new Point(line, col);
    }

    public ObservableList<ObjectInMap> getObjectList(int line, int col) {
        return cellPlay[line][col].getObjectList();
    }

    public CellPlay getCellByLineColonne(int line, int colonne) {
        return cellPlay[line][colonne];
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
    public LongBinding cellWithObjectProperty() {
        return cellWithObject;
    }
}

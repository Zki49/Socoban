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

     SimpleIntegerProperty scoreProperty() {
        return Score;
    }

    private SimpleIntegerProperty Score = new SimpleIntegerProperty(0);

    private LongBinding cellWithObject;
    private List<Integer> shuffledBox = new ArrayList<Integer>();


    private Point currentCellWithMushroom;

     void MoveAllObjectInMap(HashMap<Point, ObjectInMap> objectslLocation) {
        for(Point point : objectslLocation.keySet()){
            ObjectInMap objectInMap = objectslLocation.get(point);
            if(objectInMap instanceof Box){
                Box box = (Box) objectInMap;
               deleteBox(box);
               cellPlay[point.line][point.col].addObjectInMap(box);
            }
            else{

                deleteMushroom();
                cellPlay[point.line][point.col].addObjectInMap(objectInMap);
            }

        }
        invalidateBiddings();
    }

    private void deleteMushroom() {
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                if (cellPlay[i][j].containsMushroom()) {

                        cellPlay[i][j].deleteMushroom();




                }
            }
        }
    }


    //je crée une classe Point pour enregistré la cellul où le joueur se trouve



    MapPlay(MapDesign mapDesign){
        this.mapDesign = mapDesign;
        MapWidth = mapDesign.getMapWidth();
        MapHeight = mapDesign.getMapHeight();
        this.mapWidth.set(MapWidth);
        this.mapHeight.set(MapHeight);
        cellPlay = new CellPlay[MapHeight][MapWidth];
        Box.resetIndex();
        fillMap();
        findPlayer();
        isWon = Bindings.createBooleanBinding(() ->numberBoxOnGoal.get() == numberGoals.get());
        isNotWon = Bindings.createBooleanBinding(() ->numberBoxOnGoal.get()!= numberGoals.get());
        numberGoals = Bindings.createIntegerBinding(() -> Math.toIntExact(Arrays.stream(cellPlay).flatMap(Arrays::stream).
                filter(cellPlay -> cellPlay.containsGoal()).count()));
        numberBoxOnGoal = Bindings.createIntegerBinding(() -> Math.toIntExact(Arrays.stream(cellPlay).flatMap(Arrays::stream).
                filter(cellPlay -> cellPlay.containsGoal() && cellPlay.containsBox()).count()));


    }
     void invalidateBiddings(){
        numberGoals.invalidate();
        numberBoxOnGoal.invalidate();
        isWon.invalidate();
        isNotWon.invalidate();
    }
     int getNumberBoxe(int line, int col) {
        return   cellPlay[line][col].getIndexOfBoxe();
    }


     void reduceScore(int index) {
        scoreProperty().setValue(scoreProperty().getValue()-index);
    }

     void incrementScore(int penality) {
        scoreProperty().setValue(scoreProperty().getValue()+penality);
    }

     void showMushroom() {
        if(!showMushroom.getValue())
            incrementScore(10);
        showMushroom.setValue(!showMushroom.getValue());
    }

     boolean isShowMushroom() {
        return showMushroom.get();
    }

     SimpleBooleanProperty showMushroomProperty() {
        return showMushroom;
    }




     Boolean getIsNotWon() {
        return isNotWon.get();
    }

     BooleanBinding isNotWonProperty() {
        return isNotWon;
    }

     Boolean getIsWon() {
        return isWon.get();
    }

     BooleanBinding isWonProperty() {
        return isWon;
    }

     Number getNumberGoals() {
        return numberGoals.get();
    }

     IntegerBinding numberGoalsProperty() {
        return numberGoals;
    }

     Number getNumberBoxOnGoal() {
        return numberBoxOnGoal.get();
    }

     IntegerBinding numberBoxOnGoalProperty() {
        return numberBoxOnGoal;
    }

     void fillMap() {
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

       // while (flag) {
            Random random = new Random();
            int line = random.nextInt(1,MapHeight);
            int col = random.nextInt(1,MapWidth);
         //   if (!cellPlay[line][col].containsObjectInMap()) {
                cellPlay[line][col].addMushroom();
                currentCellWithMushroom = new Point(line, col);
                flag = false;
          //  }
       // }
    }
     boolean containsMushroom(int line, int col) {
        return cellPlay[line][col].containsMushroom();
    }

     void resetMushroom(){
        cellPlay[currentCellWithMushroom.line][currentCellWithMushroom.col].deleteMushroom();
        addMushroom();
    }
     HashMap<Point, ObjectInMap> getInitialLocationOfObject() {
        HashMap<Point, ObjectInMap> locationOfObject = new HashMap<Point, ObjectInMap>();

        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                if (cellPlay[i][j].containsBox() ) {
                   locationOfObject.put(new Point(i, j), cellPlay[i][j].getBox() );

                }
                 if(cellPlay[i][j].containsMushroom()){
                    locationOfObject.put(new Point(i, j), cellPlay[i][j].getMushroom() );
                }
            }
        }
        

        return locationOfObject;
    }

     HashMap<Point, ObjectInMap> shuffleBox() {
        HashMap<Point, ObjectInMap> locationOfObject = new HashMap<Point, ObjectInMap>();
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                if (cellPlay[i][j].containsBox()) {
                    Box box = cellPlay[i][j].getBox();
                        cellPlay[i][j].deleteByIdx(0);
                        findRandomCell(box , locationOfObject);

                }
                if(cellPlay[i][j].containsMushroom()){
                    ObjectInMap mushroom = cellPlay[i][j].getMushroom();
                    cellPlay[i][j].deleteMushroom();
                    findRandomCell(mushroom, locationOfObject);
                }
            }
        }
        invalidateBiddings();
        return locationOfObject;
    }
    private void findRandomCell(ObjectInMap objectInMap, HashMap<Point, ObjectInMap> locationOfObject) {
        Random random = new Random();
        int line, col;
        boolean flag = true;
        while (flag) {
             line = random.nextInt(1,MapHeight);
             col = random.nextInt(1,MapWidth);
             if(objectInMap instanceof Mushroom){
                 cellPlay[line][col].addObjectInMap(objectInMap);
                 flag = false;
                 locationOfObject.put(new Point(line, col), objectInMap);
             }
             else if(availableCellForBox(line, col)) {
                 cellPlay[line][col].addObjectInMap(objectInMap);
                 flag = false;
                 locationOfObject.put(new Point(line, col), objectInMap);

             }

        }
    }

    private void addObjectInList(CellPlay cellPlay, Point point){

            ObservableList<ObjectInMap> list = FXCollections.observableArrayList();
           list.addAll(cellPlay.getObjectList());
            objectInMapList.put(point, list);


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
     void moveBack(LastMove lastMove, Box box) {
        Point playerLocation = new Point(currentCellWithPlayer.line, currentCellWithPlayer.col);

        switch (lastMove){
            case UP -> moveDown();
            case DOWN -> moveUp();
            case RIGHT -> moveLeft();
            default -> moveRight();
        }
        if(box != null){
            deleteBox(box);
            cellPlay[playerLocation.line][playerLocation.col].addObjectInMap(box);
        }
        invalidateBiddings();
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
     Box moveUp() {
        Box boxMoved = null;
        if(currentCellWithPlayer.line > 0 ){

            if(availableCell(currentCellWithPlayer.line -1, currentCellWithPlayer.col)){
                if(cellPlay[currentCellWithPlayer.line -1][currentCellWithPlayer.col].containsBox()){
                    if(availableCellForBox(currentCellWithPlayer.line -2, currentCellWithPlayer.col)){
                        deletePlayer();
                        Box box = cellPlay[currentCellWithPlayer.line -1][currentCellWithPlayer.col].getBox();
                        boxMoved = box;
                        cellPlay[currentCellWithPlayer.line -1][currentCellWithPlayer.col].deleteByIdx(0);
                        addPlayer(currentCellWithPlayer.col, currentCellWithPlayer.line -1);
                        cellPlay[currentCellWithPlayer.line -1][currentCellWithPlayer.col].addObjectInMap(box);
                        scoreProperty().set(scoreProperty().get()+1);
                    }
                }
                else{
                    deletePlayer();
                    addPlayer(currentCellWithPlayer.col, currentCellWithPlayer.line -1);
                    scoreProperty().set(scoreProperty().get()+1);
                }

            }

            //System.out.println("map height: "+ MapHeight +"map Width: " + MapWidth + " player point line"+ currentCellWithPlayer.line + "col"+ currentCellWithPlayer.col);
            invalidateBiddings();
        }
        return boxMoved;
    }
     Box moveDown(){
        Box boxMoved = null;
        if (currentCellWithPlayer.line < MapHeight-1){
            if(availableCell(currentCellWithPlayer.line +1, currentCellWithPlayer.col)){
                if(cellPlay[currentCellWithPlayer.line +1][currentCellWithPlayer.col].containsBox()){
                    if(availableCellForBox(currentCellWithPlayer.line +2, currentCellWithPlayer.col)){
                        deletePlayer();
                        Box box = cellPlay[currentCellWithPlayer.line +1][currentCellWithPlayer.col].getBox();
                        boxMoved = box;
                        cellPlay[currentCellWithPlayer.line +1][currentCellWithPlayer.col].deleteByIdx(0);
                        addPlayer(currentCellWithPlayer.col, currentCellWithPlayer.line +1);
                        cellPlay[currentCellWithPlayer.line +1][currentCellWithPlayer.col].addObjectInMap(box);
                        scoreProperty().set(scoreProperty().get()+1);
                    }
                }
                else{
                    deletePlayer();
                    addPlayer(currentCellWithPlayer.col, currentCellWithPlayer.line +1);
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

     Box moveLeft(){
        Box boxMoved = null;
        if (currentCellWithPlayer.col > 0){
            if(availableCell(currentCellWithPlayer.line, currentCellWithPlayer.col - 1)){
                if(cellPlay[currentCellWithPlayer.line][currentCellWithPlayer.col -1].containsBox()){
                    if(availableCellForBox(currentCellWithPlayer.line, currentCellWithPlayer.col -2)){
                        deletePlayer();
                        Box box = cellPlay[currentCellWithPlayer.line][currentCellWithPlayer.col - 1].getBox();
                        boxMoved = box;
                        cellPlay[currentCellWithPlayer.line][currentCellWithPlayer.col -1].deleteByIdx(0);
                        addPlayer(currentCellWithPlayer.col -1 , currentCellWithPlayer.line);

                        cellPlay[currentCellWithPlayer.line][currentCellWithPlayer.col -1 ].addObjectInMap(box);
                        scoreProperty().set(scoreProperty().get()+1);
                    }
                }

                else{
                    deletePlayer();
                    addPlayer(currentCellWithPlayer.col -1 , currentCellWithPlayer.line);
                    scoreProperty().set(scoreProperty().get()+1);
                }
            }


        }
        invalidateBiddings();
        return boxMoved;
    }
     Box moveRight(){
        Box boxMoved = null;
        if (currentCellWithPlayer.col < MapWidth-1){
            if(availableCell(currentCellWithPlayer.line, currentCellWithPlayer.col + 1)){
                if(cellPlay[currentCellWithPlayer.line][currentCellWithPlayer.col +1].containsBox()){
                    if(availableCellForBox(currentCellWithPlayer.line, currentCellWithPlayer.col +2)){
                        deletePlayer();
                        Box box = cellPlay[currentCellWithPlayer.line][currentCellWithPlayer.col + 1].getBox();
                        boxMoved = box;
                        cellPlay[currentCellWithPlayer.line][currentCellWithPlayer.col +1].deleteByIdx(0);
                        addPlayer(currentCellWithPlayer.col +1 , currentCellWithPlayer.line);
                        cellPlay[currentCellWithPlayer.line][currentCellWithPlayer.col +1 ].addObjectInMap(box);
                        scoreProperty().set(scoreProperty().get()+1);
                    }
                }

                else{
                    deletePlayer();
                    addPlayer(currentCellWithPlayer.col +1 , currentCellWithPlayer.line);
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

     ObservableList<ObjectInMap> getObjectList(int line, int col) {
        return cellPlay[line][col].getObjectList();
    }

     CellPlay getCellByLineColonne(int line, int colonne) {
        return cellPlay[line][colonne];
    }


     int getTotalCells() {
        return totalCells.get();
    }

     SimpleIntegerProperty totalCellsProperty() {
        return totalCells;
    }

     void setTotalCells(int totalCells) {
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
     LongBinding cellWithObjectProperty() {
        return cellWithObject;
    }
}

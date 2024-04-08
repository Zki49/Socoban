package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public class BoardPlay extends Board{
    private MapPlay mapPlay;


    private final MoveExecutor moveExecutor = new MoveExecutor();
    public BoardPlay(BoardDesign boardDesign){
        mapPlay = new MapPlay(boardDesign.getMap());

    }

    public int getMapWidth() {
        return mapPlay.getMapWidth();
    }
    public int getMapHeight() {
        return mapPlay.getMapHeight();
    }

    public void moveUp() {
        moveExecutor.executeMove(new moveCommandeDirection(mapPlay, LastMove.UP, () -> mapPlay.moveUp()));
    }
    public void moveLeft() {
        moveExecutor.executeMove(new moveCommandeDirection(mapPlay, LastMove.LEFT, () -> mapPlay.moveLeft()));

    }
    public void moveRight() {
        moveExecutor.executeMove(new moveCommandeDirection(mapPlay, LastMove.RIGHT, () -> mapPlay.moveRight()));
    }

    public void moveDown() {
        moveExecutor.executeMove(new moveCommandeDirection(mapPlay, LastMove.DOWN, ()-> mapPlay.moveDown()));
    }
    public void moveBack(){
        moveExecutor.moveBack((int index) -> mapPlay.reduceScore(index),(int penality) -> mapPlay.incrementScore(penality));
    }
    public void movefront() {
        moveExecutor.moveFront();
    }
    public ObservableList<ObjectInMap> getObjectList(int line, int col) {
        return  mapPlay.getObjectList(line,col);
    }

    public BooleanBinding isWon(){
        return mapPlay.isWonProperty();
    }
    public IntegerBinding numberOfGoal(){
        return mapPlay.numberGoalsProperty();
    }
    public SimpleIntegerProperty scoreProperty(){
        return mapPlay.scoreProperty();
    }
    public IntegerBinding numberBoxOnGoal(){
        return mapPlay.numberBoxOnGoalProperty();
    }

    public BooleanBinding isNotWon() {
        return mapPlay.isNotWonProperty();
    }

    public int getNumberBoxe(int line, int col) {
        return mapPlay.getNumberBoxe(line,col);
    }



    public SimpleBooleanProperty showMushroomProperty(){
        return mapPlay.showMushroomProperty();
    }

    public void showMushroom() {
        mapPlay.showMushroom();
    }

    public void shuffleBox() {
        moveExecutor.executeMove(new MoveCommandeShuffle(mapPlay ));

    }

    public boolean containsMushroom(int line, int col) {
        return mapPlay.containsMushroom(line,col);
    }
}

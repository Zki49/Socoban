package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

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
        moveExecutor.executeMove(() -> mapPlay.moveUp());
    }
    public void moveLeft() {
        moveExecutor.executeMove(() -> mapPlay.moveLeft());

    }
    public void moveRight() {
        moveExecutor.executeMove(() -> mapPlay.moveRight());
    }

    public void moveDown() {
        moveExecutor.executeMove(() ->  mapPlay.moveDown());
    }
    public void moveBack(){
        moveExecutor.moveBack(()-> mapPlay.resetMap() , (int index) -> mapPlay.reduceScore(index),(int penality) -> mapPlay.incrementScore(penality));
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



}

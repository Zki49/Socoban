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

public class BoardPlay {
    private MapPlay mapPlay;
    private final FileReader fileReader = new FileReader();
    private int maxFilledCells;
    private boolean hasBeenChanged = false;
    private IntegerBinding maxCellAvailable ;
    private IntegerBinding totalCells;
    private final SimpleStringProperty title = new SimpleStringProperty("Sokoban");

    private final SimpleBooleanProperty isReloadedMap = new SimpleBooleanProperty(false);
    private final static int MIN_Size = 10;
    private final static int MAX_Size = 50;
    public BoardPlay() {
        mapPlay = new MapPlay(15,10);
        this.maxFilledCells = mapPlay.getSize()/2;

        maxCellAvailable = Bindings.createIntegerBinding(() -> mapPlay.getSize()/2, mapPlay.mapHeightProperty(), mapPlay.mapWidthProperty());
        totalCells = Bindings.createIntegerBinding(() -> mapPlay.getSize(), mapPlay.mapHeightProperty(), mapPlay.mapWidthProperty());
    }
    public BoardPlay(BoardDesign boardDesign){
        mapPlay = new MapPlay(boardDesign.getMap());
        this.maxFilledCells = mapPlay.getSize()/2;

        maxCellAvailable = Bindings.createIntegerBinding(() -> mapPlay.getSize()/2, mapPlay.mapHeightProperty(), mapPlay.mapWidthProperty());
        totalCells = Bindings.createIntegerBinding(() -> mapPlay.getSize(), mapPlay.mapHeightProperty(), mapPlay.mapWidthProperty());
    }

    public int getMapWidth() {
        return mapPlay.getMapWidth();
    }
    public int getMapHeight() {
        return mapPlay.getMapHeight();
    }

    public void moveUp() {
        mapPlay.moveUp();
    }
    public void moveLeft() {
        mapPlay.moveLeft();
    }
    public void moveRight() {
        mapPlay.moveRight();
    }

    public void moveDown() {
        mapPlay.moveDown();
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

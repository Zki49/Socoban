package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.util.List;

public class Board {



    private final Map map = new Map(15,10);
    private int maxFilledCells;
    private IntegerBinding maxCellAvailable = Bindings.createIntegerBinding(() -> map.getSize()/2, map.mapHeightProperty(), map.mapWidthProperty());
    private IntegerBinding totalCells = Bindings.createIntegerBinding(() -> map.getSize(), map.mapHeightProperty(), map.mapWidthProperty());
    public Board() {
        this.maxFilledCells = map.getSize()/2;

    }


    public int getMaxFilledCells() {
        return maxFilledCells;
    }

    public void setMaxFilledCells(int maxFilledCells) {
        this.maxFilledCells = maxFilledCells;
    }
    public IntegerBinding getMaxCellAvailable() {
        return maxCellAvailable;
    }

    public Number getTotalCells() {
        return totalCells.get();
    }

    public BooleanBinding contentError(){return map.getContaintErrorProperty();}
    public IntegerBinding totalCellsProperty() {
        return totalCells;
    }
    public BooleanBinding containsPlayer() {
        return map.notContaintPlayerProperty();
    }
    public BooleanBinding containsGoal() {
        return map.notContainsGoalProperty();
    }
    public BooleanBinding containsBox() {
        return map.notContaintBoxProperty();
    }
    public BooleanBinding containsWall() {
        return map.containsWallProperty();
    }
    public BooleanBinding boxIsEqualToGoal() {
        return map.boxIsEqualToGoalProperty();
    }
    /*verifie si le type est correct sinon envoie une exception*/
    public void addObject( int x, int y) {
            map.addObject( x, y);
    }
    public SimpleStringProperty getCurrentObject() {
        return map.currentObjectProperty();
    }
    public LongBinding cellWithObjectProperty() {
        return map.cellWithObjectProperty();
    }

    public List<String> getObjectsPath(int line, int col) {
        return map.getObjectsPath(line, col);
    }

    public ObservableList<ObjectInMap> getObjectList(int line, int col) {
        return  map.getObjectList(line,col);
    }
}

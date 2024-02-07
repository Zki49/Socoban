package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;

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

    public IntegerBinding totalCellsProperty() {
        return totalCells;
    }
    public BooleanBinding containsPlayer() {
        return map.containsPlayerProperty();
    }
    public BooleanBinding containsGoal() {
        return map.containsGoalProperty();
    }
    public BooleanBinding containsBox() {
        return map.containsBoxProperty();
    }
    public BooleanBinding containsWall() {
        return map.containsWallProperty();
    }
    public BooleanBinding boxIsEqualToGoal() {
        return map.boxIsEqualToGoalProperty();
    }
    /*verifie si le type est correct sinon envoie une exception*/
    public void addObject(String type, int x, int y) {
        try {
            map.addObject(TypeOfObjectInMap.valueOf(type), x, y);
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid type of object ");
        }

    }
}

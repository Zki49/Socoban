package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;

public class Board {



    private final Map map = new Map(15,10);
    private int maxFilledCells;
    private IntegerBinding maxCellAvailable = Bindings.createIntegerBinding(() -> map.getSize()/2, map.mapHeightProperty(), map.mapWidthProperty());

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

}

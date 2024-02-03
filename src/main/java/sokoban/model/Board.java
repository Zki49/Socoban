package sokoban.model;

public class Board {

    private int maxFilledCells;

    private final Map map = new Map(15,10);

    public Board() {
        this.maxFilledCells = map.getSize()/2;

    }

    public int getMaxFilledCells() {
        return maxFilledCells;
    }

    public void setMaxFilledCells(int maxFilledCells) {
        this.maxFilledCells = maxFilledCells;
    }
}

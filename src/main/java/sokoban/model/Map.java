package sokoban.model;

public class Map {

    int gridWidth;
    int gridHeight;
    private final Cell[][] cells ;

    public Map(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        cells = new Cell[gridWidth][gridHeight];
        fillMap();
    }
    public void fillMap() {
        for(int i = 0; i <gridHeight; i++) {
            cells [i] = new Cell[gridWidth];
            for(int j = 0; j < gridWidth; j++) {
                cells [i][j] = new Cell();
            }
        }
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getSize(){
        return gridWidth * gridHeight;
    }
}

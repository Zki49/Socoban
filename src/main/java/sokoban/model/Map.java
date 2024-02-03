package sokoban.model;

public class Map {

    private static int MapWidth;
    private static int MapHeight;
    private final Cell[][] cells ;

    public Map(int mapWidth, int mapHeight) {
        this.MapWidth = mapWidth;
        this.MapHeight = mapHeight;
        cells = new Cell[mapWidth][mapHeight];
        fillMap();
    }
    public void fillMap() {
        for(int i = 0; i < MapHeight; i++) {
            cells [i] = new Cell[MapWidth];
            for(int j = 0; j < MapWidth; j++) {
                cells [i][j] = new Cell();
            }
        }
    }

    public static int getMapdWidth() {
        return MapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.MapWidth = mapWidth;
    }

    public static int getMapHeight() {
        return MapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.MapHeight = mapHeight;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getSize(){
        return MapWidth * MapHeight;
    }
}

package sokoban.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Map {

    private static int MapWidth;
    private static int MapHeight;
    private SimpleIntegerProperty mapWidth = new SimpleIntegerProperty();
    private SimpleIntegerProperty mapHeight = new SimpleIntegerProperty();
    private final Cell[][] cells ;

    public Map(int mapWidth, int mapHeight) {
        MapWidth = mapWidth;
        MapHeight = mapHeight;
        this.mapWidth.set(MapWidth);
        this.mapHeight.set(MapHeight);
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

    public int getMapWidth() {
        return mapWidth.get();
    }

    public SimpleIntegerProperty mapWidthProperty() {
        return mapWidth;
    }

    public SimpleIntegerProperty mapHeightProperty() {
        return mapHeight;
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

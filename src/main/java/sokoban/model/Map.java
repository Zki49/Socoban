package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Arrays;

public class Map {

    private static int MapWidth;
    private static int MapHeight;
    private final SimpleIntegerProperty mapWidth = new SimpleIntegerProperty();
    private final SimpleIntegerProperty mapHeight = new SimpleIntegerProperty();
    private final SimpleIntegerProperty totalCells = (SimpleIntegerProperty) mapHeight.multiply(mapWidth);
    private final Cell[][] cells ;
    private final BooleanBinding containsPlayer ;
    private final BooleanBinding containsGoal ;
    private final BooleanBinding containsBox ;
    private final BooleanBinding containsWall ;

    private final BooleanBinding boxIsEqualToGoal ;
    private final LongBinding cellWithObject;

    /*les variables contains sont calculer par rapport cells et recalculer a chaque changement dans cells*/

    public Map(int mapWidth, int mapHeight) {
        MapWidth = mapWidth;
        MapHeight = mapHeight;
        this.mapWidth.set(MapWidth);
        this.mapHeight.set(MapHeight);
        cells = new Cell[mapWidth][mapHeight];

        fillMap();


        containsPlayer = Bindings.createBooleanBinding(() -> Arrays.stream(cells)
                .flatMap(Arrays::stream).filter(Cell::containsPlayer).count() > 0);
        containsBox = Bindings.createBooleanBinding(() -> Arrays.stream(cells)
                .flatMap(Arrays::stream).filter(Cell::containsBox).count() > 0);
        containsGoal = Bindings.createBooleanBinding(() -> Arrays.stream(cells)
              .flatMap(Arrays::stream).filter(Cell::containsGoal).count() > 0);
        containsWall = Bindings.createBooleanBinding(() -> Arrays.stream(cells)
                .flatMap(Arrays::stream).filter(Cell::containsWall).count() > 0);
        boxIsEqualToGoal = Bindings.createBooleanBinding(() -> Arrays.stream(cells)
                .flatMap(Arrays::stream).filter(Cell::containsGoal).count() == Arrays.stream(cells)
                .flatMap(Arrays::stream).filter(Cell::containsBox).count());
        cellWithObject = Bindings.createLongBinding(
                () ->   Arrays.stream(cells).flatMap(Arrays::stream).filter(Cell::containsObjectInMap).count()

        );

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

    public int getTotalCells() {
        return totalCells.get();
    }

    public SimpleIntegerProperty totalCellsProperty() {
        return totalCells;
    }

    public void setTotalCells(int totalCells) {
        this.totalCells.set(totalCells);
    }

    //il faut verifier que l'on est pas au max de cellavailable (si c'est le cas on verifie si la cellule est vide si oui on annule)/*
    // si on ajoute un object on appel invalidatebidings  */
    public void addObject(TypeOfObjectInMap typeOfObjectInMap, int x, int y) {
        cells[x][y].addObjectInMap(typeOfObjectInMap);
        invalidateBidings();
    }
    //si on vide une cellule on appel invalidatebidings  */
    public void emptyCell(int x, int y) {
        invalidateBidings();
    }
    /*invalider les bidings permet de les obligé a recalculer leur valeurs */
    public void invalidateBidings(){
        containsPlayer.invalidate();
        containsGoal.invalidate();
        containsBox.invalidate();
        containsWall.invalidate();
        boxIsEqualToGoal.invalidate();
        cellWithObject.invalidate();
    }

    public Boolean getContainsPlayer() {
        return containsPlayer.get();
    }

    public BooleanBinding containsPlayerProperty() {
        return containsPlayer;
    }

    public Boolean getContainsGoal() {
        return containsGoal.get();
    }

    public BooleanBinding containsGoalProperty() {
        return containsGoal;
    }

    public Boolean getContainsBox() {
        return containsBox.get();
    }

    public BooleanBinding containsBoxProperty() {
        return containsBox;
    }

    public Boolean getContainsWall() {
        return containsWall.get();
    }

    public BooleanBinding containsWallProperty() {
        return containsWall;
    }

    public Boolean getBoxIsEqualToGoal() {
        return boxIsEqualToGoal.get();
    }

    public BooleanBinding boxIsEqualToGoalProperty() {
        return boxIsEqualToGoal;
    }

    public Number getCellWithObject() {
        return cellWithObject.get();
    }

    public LongBinding cellWithObjectProperty() {
        return cellWithObject;
    }
}

package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




public class Map {

    private static int MapWidth;
    private static int MapHeight;
    private final SimpleIntegerProperty mapWidth = new SimpleIntegerProperty();
    private final SimpleIntegerProperty mapHeight = new SimpleIntegerProperty();
    private final SimpleIntegerProperty totalCells = new SimpleIntegerProperty(mapWidth.getValue() * mapHeight.getValue());
    private final Cell[][] cells;
    private final BooleanBinding notContainsPlayer;
    private final BooleanBinding notContainsGoal;
    private final BooleanBinding notContainsBox;
    private final BooleanBinding containsWall;
    private final BooleanBinding containsError;

    private final SimpleStringProperty currentObject = new SimpleStringProperty("WALL");

    private final BooleanBinding boxIsNotEqualToGoal;
    private LongBinding cellWithObject;
    private List<String> elementsFromFile = new ArrayList<String>();

    /*les variables contains sont calculer par rapport cells et recalculer a chaque changement dans cells*/

    Map(int mapWidth, int mapHeight) {
        MapWidth = mapWidth;
        MapHeight = mapHeight;
        this.mapWidth.set(MapWidth);
        this.mapHeight.set(MapHeight);
        cells = new Cell[mapHeight][mapWidth];


        fillMap();


        notContainsPlayer = Bindings.createBooleanBinding(() -> Arrays.stream(cells)
                .flatMap(Arrays::stream).filter(Cell::containsPlayer).count() == 0);
        notContainsBox = Bindings.createBooleanBinding(() -> Arrays.stream(cells)
                .flatMap(Arrays::stream).filter(Cell::containsBox).count() == 0);
        notContainsGoal = Bindings.createBooleanBinding(() -> Arrays.stream(cells)
                .flatMap(Arrays::stream).filter(Cell::containsGoal).count() == 0);
        containsWall = Bindings.createBooleanBinding(() -> Arrays.stream(cells)
                .flatMap(Arrays::stream).filter(Cell::containsWall).count() > 0);
        boxIsNotEqualToGoal = Bindings.createBooleanBinding(() -> Arrays.stream(cells)
                .flatMap(Arrays::stream).filter(Cell::containsGoal).count() != Arrays.stream(cells)
                .flatMap(Arrays::stream).filter(Cell::containsBox).count());

        containsError = Bindings.createBooleanBinding(() -> {
            if (notContainsPlayer.getValue() || notContainsGoal.getValue() || notContainsBox.getValue()) {
                return true;
            }
            return false;
        });

        cellWithObject = Bindings.createLongBinding(
                () -> Arrays.stream(cells).flatMap(Arrays::stream).filter(cell -> cell.containsObjectInMap()).count()
        );

    }

    public Map(List<String> elementsFromFile, int width, int height) {
        this(width, height);
        this.elementsFromFile = elementsFromFile;
        fillMapByFile();
        invalidateBidings();


    }

    @Override
    public String toString() {
        String map = "";
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                if (j == MapWidth - 1) {
                    map += cells[i][j] + "\n";
                } else {
                    map += cells[i][j];
                }

            }
            System.out.println();

        }
        return map;
    }

    public ObservableList<ObjectInMap> getObjectList(int line, int col) {
        return cells[line][col].getObjectList();
    }


    public void fillMap() {
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public void fillMapByFile() {

        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                String symbole = String.valueOf(elementsFromFile.get(i).charAt(j));
                cells[i][j] = new Cell(symbole);

            }

        }
    }

    public Cell getCellContainsPlayer() {
        Cell cell = null;
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                cell = getCellByLineColonne(j, i);
                if (cell.containsPlayer()) {
                    return cell;
                }
            }
        }
        return cell;
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

    public Cell getCellByLineColonne(int line, int colonne) {
        return cells[line][colonne];
    }

    public int getSize() {
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
    public void addObject(int x, int y) {

        if (currentObject.getValue() == "GROUND") {
            cells[x][y].delete();
        } else {
            if (notContainsPlayer.getValue() == false && currentObject.getValue() == "PLAYER") {
                deletePlayer();
            }
            if (true/*condition*/) {
                cells[x][y].addObjectInMap(currentObject.getValue());

            }

        }
        invalidateBidings();
    }


    private void deletePlayer() {
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                Cell cell = getCellByLineColonne(i, j);
                if (cell.containsPlayer()) {
                    cell.delete();
                }
            }
        }

    }

    //si on vide une cellule on appel invalidatebidings  */
    public void emptyCell(int x, int y) {
        cells[x][y].delete();
        invalidateBidings();
    }

    /*invalider les bidings permet de les obligé a recalculer leur valeurs */
    public void invalidateBidings() {
        notContainsPlayer.invalidate();
        notContainsGoal.invalidate();
        notContainsBox.invalidate();
        containsWall.invalidate();
        boxIsNotEqualToGoal.invalidate();
        cellWithObject.invalidate();
        containsError.invalidate();

    }

    public Boolean getNotContainsPlayer() {
        return notContainsPlayer.get();
    }

    public BooleanBinding notContainsPlayerProperty() {
        return notContainsPlayer;
    }

    public Boolean getNotContainsGoal() {
        return notContainsGoal.get();
    }

    public BooleanBinding notContainsGoalProperty() {
        return notContainsGoal;
    }

    public Boolean getNotContainsBox() {
        return notContainsBox.get();
    }

    public BooleanBinding notContainsBoxProperty() {
        return notContainsBox;
    }

    public Boolean getContainsWall() {
        return containsWall.get();
    }

    public BooleanBinding containsWallProperty() {
        return containsWall;
    }

    public Boolean getBoxIsNotEqualToGoal() {
        return boxIsNotEqualToGoal.get();
    }

    public BooleanBinding boxIsNotEqualToGoalProperty() {
        return boxIsNotEqualToGoal;
    }

    public BooleanBinding getContaintErrorProperty() {
        return containsError;
    }

    public Number getCellWithObject() {
        return cellWithObject.get();
    }

    public LongBinding cellWithObjectProperty() {
        return cellWithObject;
    }

    public String getCurrentObject() {
        return currentObject.get();
    }

    public SimpleStringProperty currentObjectProperty() {
        return currentObject;
    }

    public List<String> getObjectsPath(int line, int col) {
        return cells[line][col].getObjectsPath();
    }

}
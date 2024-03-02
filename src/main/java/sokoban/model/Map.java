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


    private final SimpleStringProperty currentObject = new SimpleStringProperty("");


    private List<String> elementsFromFile;







    /*les variables contains sont calculer par rapport cells et recalculer a chaque changement dans cells*/
    /*
    * il y a deux constructeurs , un qui est utilisé lorsqu'on crée un map à partir d'une largeur/hauteur un autre à partir d'une Liste de String/largeur/hauteur
    * */
    Map(int mapWidth, int mapHeight) {

        this(new ArrayList<String>(), mapWidth, mapHeight);



    }

    public Map(List<String> elementsFromFile,int mapWidth, int mapHeight) {
        MapWidth = mapWidth;
        MapHeight = mapHeight;
        this.mapWidth.set(MapWidth);
        this.mapHeight.set(MapHeight);
        cells = new Cell[mapHeight][mapWidth];



        this.elementsFromFile = elementsFromFile;
        //la taille de elementsFromfile nous permet de savoir comment fill la map
        if(elementsFromFile.size() > 0)
        fillMapByFile();

        else
            fillMap();




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
        if(currentObject.getValue() != ""){
            if (currentObject.getValue() == "GROUND") {

                cells[x][y].delete();
            } else {
                if (notContainsPlayer() == false && currentObject.getValue() == "PLAYER") {
                    deletePlayer();
                }


                if(cellWithObject() >= (this.getSize()/2)-1 && getCellByLineColonne(x,y).containsObjectInMap() || cellWithObject() <= (this.getSize()/2)-1)

                {

                    cells[x][y].addObjectInMap(currentObject.getValue());

                }

            }

        }

    }

    private int cellWithObject() {
        int countObject = 0;
        for(int i = 0; i < MapHeight; i++) {
            for(int j = 0; j < MapWidth; j++) {
                Cell cell = getCellByLineColonne(i,j);
                if (cell.containsObjectInMap()){
                    ++countObject;
                }
            }
        }
        return countObject;
    }

    private boolean notContainsPlayer() {
        for(int i = 0; i < MapHeight; i++) {
            for(int j = 0; j < MapWidth; j++) {
                Cell cell = getCellByLineColonne(i,j);
                if (cell.containsPlayer()){
                    return false;
                }
            }
        }
        return true;
    }


    private void deletePlayer() {

        for(int i = 0; i < MapHeight; i++) {
            for(int j = 0; j < MapWidth; j++) {
                Cell cell = getCellByLineColonne(i,j);
                if (cell.containsPlayer()){
                    cell.deleteByIdx(0);

                }
            }
        }

    }

    //si on vide une cellule on appel invalidatebidings  */
    public void emptyCell(int x, int y) {
        cells[x][y].delete();

    }

    /*invalider les bidings permet de les obligé a recalculer leur valeurs */



    public String getCurrentObject() {
        return currentObject.get();
    }

    public SimpleStringProperty currentObjectProperty() {
        return currentObject;
    }



}
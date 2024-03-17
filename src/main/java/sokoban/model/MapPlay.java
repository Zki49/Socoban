package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.List;

public class MapPlay extends Map{
    private static int MapWidth;
    private static int MapHeight;
    private final SimpleIntegerProperty mapWidth = new SimpleIntegerProperty();
    private final SimpleIntegerProperty mapHeight = new SimpleIntegerProperty();
    private final SimpleIntegerProperty totalCells = new SimpleIntegerProperty(mapWidth.getValue() * mapHeight.getValue());
    private final CellPlay[][] cellPlay;





    private List<String> elementsFromFile;

    private LongBinding cellWithObject;





    /*les variables contains sont calculer par rapport cells et recalculer a chaque changement dans cells*/
    /*
     * il y a deux constructeurs , un qui est utilisé lorsqu'on crée un map à partir d'une largeur/hauteur un autre à partir d'une Liste de String/largeur/hauteur
     * */
    MapPlay(int mapWidth, int mapHeight) {

        this(null, mapWidth, mapHeight);



    }

    public MapPlay(List<String> elementsFromFile, int mapWidth, int mapHeight) {
        MapWidth = mapWidth;
        MapHeight = mapHeight;
        this.mapWidth.set(MapWidth);
        this.mapHeight.set(MapHeight);
        cellPlay = new CellPlay[mapHeight][mapWidth];

        cellWithObject = Bindings.createLongBinding(
                () -> Arrays.stream(cellPlay).flatMap(Arrays::stream).filter(cellDesign -> cellDesign.containsObjectInMap()).count()
        );



        if(elementsFromFile != null){

            this.elementsFromFile = elementsFromFile;
            fillMapByFile();
        }
        else
            fillMap();




    }

    @Override
    public String toString() {
        String map = "";
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                if (j == MapWidth - 1) {
                    map += cellPlay[i][j] + "\n";
                } else {
                    map += cellPlay[i][j];
                }

            }
            System.out.println();

        }
        return map;
    }

    public ObservableList<ObjectInMap> getObjectList(int line, int col) {
        return cellPlay[line][col].getObjectList();
    }


    public void fillMap() {
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                cellPlay[i][j] = new CellPlay();
            }
        }
    }

    public void fillMapByFile() {

        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                String symbole = String.valueOf(elementsFromFile.get(i).charAt(j));
                cellPlay[i][j] = new CellPlay(symbole);

            }

        }
    }



    public CellPlay getCellContainsPlayer() {
        CellPlay cellPlay = null;
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                cellPlay = getCellByLineColonne(j, i);
                if (cellPlay.containsPlayer()) {
                    return cellPlay;
                }
            }
        }
        return cellPlay;
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

    public CellPlay[][] getCells() {
        return cellPlay;
    }

    public CellPlay getCellByLineColonne(int line, int colonne) {
        return cellPlay[line][colonne];
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

    public void addObject(int x, int y, String currentObject) {


        if (currentObject.equals("PLAYER") && !notContainsPlayer() ) {
            deletePlayer();
        }

        if(cellWithObject.get() >= (this.getSize()/2)-1 && getCellByLineColonne(x,y).containsObjectInMap() || cellWithObject.get() <= (this.getSize()/2)-1) {
            cellPlay[x][y].addObjectInMap(currentObject);
        }

        cellWithObject.invalidate();


    }



    private boolean notContainsPlayer() {
        for(int i = 0; i < MapHeight; i++) {
            for(int j = 0; j < MapWidth; j++) {
                CellPlay cellPlay = getCellByLineColonne(i,j);
                if (cellPlay.containsPlayer()){
                    return false;
                }
            }
        }
        return true;
    }


    private void deletePlayer() {

        for(int i = 0; i < MapHeight; i++) {
            for(int j = 0; j < MapWidth; j++) {
                CellPlay cellPlay = getCellByLineColonne(i,j);
                if (cellPlay.containsPlayer()){
                    cellPlay.deleteByIdx(0);

                }
            }
        }
        cellWithObject.invalidate();
    }

    //si on vide une cellule on appel invalidatebidings  */
    public void emptyCell(int x, int y) {
        cellPlay[x][y].delete();
        cellWithObject.invalidate();
    }

    /*invalider les bidings permet de les obligé a recalculer leur valeurs */






    public LongBinding cellWithObjectProperty() {
        return cellWithObject;
    }
}

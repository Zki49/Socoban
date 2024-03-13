package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.List;




public class MapDesign extends Map {

    private static int MapWidth;
    private static int MapHeight;
    private final SimpleIntegerProperty mapWidth = new SimpleIntegerProperty();
    private final SimpleIntegerProperty mapHeight = new SimpleIntegerProperty();
    private final SimpleIntegerProperty totalCells = new SimpleIntegerProperty(mapWidth.getValue() * mapHeight.getValue());
    private final CellDesign[][] cellDesigns;





    private List<String> elementsFromFile;

    private LongBinding cellWithObject;





    /*les variables contains sont calculer par rapport cells et recalculer a chaque changement dans cells*/
    /*
    * il y a deux constructeurs , un qui est utilisé lorsqu'on crée un map à partir d'une largeur/hauteur un autre à partir d'une Liste de String/largeur/hauteur
    * */
    MapDesign(int mapWidth, int mapHeight) {

        this(null, mapWidth, mapHeight);



    }

    public MapDesign(List<String> elementsFromFile, int mapWidth, int mapHeight) {
        MapWidth = mapWidth;
        MapHeight = mapHeight;
        this.mapWidth.set(MapWidth);
        this.mapHeight.set(MapHeight);
        cellDesigns = new CellDesign[mapHeight][mapWidth];

        cellWithObject = Bindings.createLongBinding(
                () -> Arrays.stream(cellDesigns).flatMap(Arrays::stream).filter(cellDesign -> cellDesign.containsObjectInMap()).count()
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
                    map += cellDesigns[i][j] + "\n";
                } else {
                    map += cellDesigns[i][j];
                }

            }
            System.out.println();

        }
        return map;
    }

    public ObservableList<ObjectInMap> getObjectList(int line, int col) {
        return cellDesigns[line][col].getObjectList();
    }


    public void fillMap() {
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                cellDesigns[i][j] = new CellDesign();
            }
        }
    }

    public void fillMapByFile() {

        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                String symbole = String.valueOf(elementsFromFile.get(i).charAt(j));
                cellDesigns[i][j] = new CellDesign(symbole);

            }

        }
    }



    public CellDesign getCellContainsPlayer() {
        CellDesign cellDesign = null;
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                cellDesign = getCellByLineColonne(j, i);
                if (cellDesign.containsPlayer()) {
                    return cellDesign;
                }
            }
        }
        return cellDesign;
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

    public CellDesign[][] getCells() {
        return cellDesigns;
    }

    public CellDesign getCellByLineColonne(int line, int colonne) {
        return cellDesigns[line][colonne];
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
                    cellDesigns[x][y].addObjectInMap(currentObject);
                }

            cellWithObject.invalidate();


    }



    private boolean notContainsPlayer() {
        for(int i = 0; i < MapHeight; i++) {
            for(int j = 0; j < MapWidth; j++) {
                CellDesign cellDesign = getCellByLineColonne(i,j);
                if (cellDesign.containsPlayer()){
                    return false;
                }
            }
        }
        return true;
    }


    private void deletePlayer() {

        for(int i = 0; i < MapHeight; i++) {
            for(int j = 0; j < MapWidth; j++) {
                CellDesign cellDesign = getCellByLineColonne(i,j);
                if (cellDesign.containsPlayer()){
                    cellDesign.deleteByIdx(0);

                }
            }
        }
    cellWithObject.invalidate();
    }

    //si on vide une cellule on appel invalidatebidings  */
    public void emptyCell(int x, int y) {
        cellDesigns[x][y].delete();
        cellWithObject.invalidate();
    }

    /*invalider les bidings permet de les obligé a recalculer leur valeurs */






    public LongBinding cellWithObjectProperty() {
        return cellWithObject;
    }
}
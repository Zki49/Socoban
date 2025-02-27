package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.List;




class MapDesign extends Map {
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

    MapDesign(List<String> elementsFromFile, int mapWidth, int mapHeight) {
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

     ObservableList<ObjectInMap> getObjectList(int line, int col) {
        return cellDesigns[line][col].getObjectList();
    }


    void fillMap() {
        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {

                cellDesigns[i][j] = new CellDesign();
            }
        }
    }

    void fillMapByFile() {

        for (int i = 0; i < MapHeight; i++) {
            for (int j = 0; j < MapWidth; j++) {
                String symbole = String.valueOf(elementsFromFile.get(i).charAt(j));
                cellDesigns[i][j] = new CellDesign(symbole);
                if(cellDesigns[i][j].containsPlayer()){
                    currentCellWithPlayer = new Point(i , j);
                }

            }

        }
    }

    CellDesign[][] getCells() {
        return cellDesigns;
    }

    CellDesign getCellByLineColonne(int line, int colonne) {
        return cellDesigns[line][colonne];
    }

    int getTotalCells() {
        return totalCells.get();
    }

    SimpleIntegerProperty totalCellsProperty() {
        return totalCells;
    }

    void setTotalCells(int totalCells) {
        this.totalCells.set(totalCells);
    }

    //il faut verifier que l'on est pas au max de cellavailable (si c'est le cas on verifie si la cellule est vide si oui on annule)/*
    // si on ajoute un object on appel invalidatebidings  */

    void addObject(int x, int y, TypeOfObjectInMap currentObject) {


                if (currentObject.name().equals("PLAYER") ) {
                    deletePlayer();
                }

                if(isFree(x,y)) {

                    cellDesigns[x][y].addObjectInMap(currentObject);
                    if(cellDesigns[x][y].containsPlayer())
                        currentCellWithPlayer = new Point(x , y);
                }

            cellWithObject.invalidate();


    }
    public boolean isFree(int x, int y) {
        return  cellWithObject.get() >= (this.getSize()/2)-1 && getCellByLineColonne(x,y).containsObjectInMap() || cellWithObject.get() <= (this.getSize()/2)-1;
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
      
        if(currentCellWithPlayer != null){
            cellDesigns[currentCellWithPlayer.line][currentCellWithPlayer.col].deletePlayer();
        }


    }

    //si on vide une cellule on appel invalidatebidings  */
    void emptyCell(int x, int y) {
        cellDesigns[x][y].delete();
        cellWithObject.invalidate();
    }

    /*invalider les bidings permet de les obligé a recalculer leur valeurs */






    LongBinding cellWithObjectProperty() {
        return cellWithObject;
    }
}
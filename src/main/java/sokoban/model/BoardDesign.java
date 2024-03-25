package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class BoardDesign  extends Board{
    private MapDesign mapDesign;
    private final FileSaver fileSaver;
    private final FileReader fileReader = new FileReader();
    private int maxFilledCells;
    private boolean hasBeenChanged = false;
    private IntegerBinding maxCellAvailable ;
    private IntegerBinding totalCells;

    private final RulesHandling rulesHandling;
    private final SimpleBooleanProperty isReloadedMap = new SimpleBooleanProperty(false);
    private final static int MIN_Size = 10;
    private final static int MAX_Size = 50;
    public BoardDesign() {
         mapDesign = new MapDesign(15,10);
        this.maxFilledCells = mapDesign.getSize()/2;
        fileSaver = new FileSaver(mapDesign);
        rulesHandling = new RulesHandling(mapDesign.getCells());
        maxCellAvailable = Bindings.createIntegerBinding(() -> mapDesign.getSize()/2, mapDesign.mapHeightProperty(), mapDesign.mapWidthProperty());
        totalCells = Bindings.createIntegerBinding(() -> mapDesign.getSize(), mapDesign.mapHeightProperty(), mapDesign.mapWidthProperty());
    }

    public static int getMaxSize() {
        return MAX_Size;
    }

    public static int getMinSize() {
        return MIN_Size;
    }

    public int getMaxFilledCells() {
        return maxFilledCells;
    }
    public int getMapWidth() {
        return mapDesign.getMapWidth();
    }
    public int getMapHeight() {
        return mapDesign.getMapHeight();
    }

    public void setMaxFilledCells(int maxFilledCells) {
        this.maxFilledCells = maxFilledCells;
    }
    public IntegerBinding getMaxCellAvailable() {
        return maxCellAvailable;
    }

    public Number getTotalCells() {
        return totalCells.get();
    }

    public BooleanBinding contentError(){
        return rulesHandling.getContaintErrorProperty();
    }
    public IntegerBinding totalCellsProperty() {
        return totalCells;
    }
    public BooleanBinding containsPlayer() {
        return rulesHandling.notContainsPlayerProperty();
    }
    public BooleanBinding containsGoal() {
        return rulesHandling.notContainsGoalProperty();
    }
    public BooleanBinding containsBox() {
        return rulesHandling.notContainsBoxProperty();
    }
    public BooleanBinding containsWall() {
        return rulesHandling.containsWallProperty();
    }
    public BooleanBinding boxIsEqualToGoal() {
        return rulesHandling.boxIsNotEqualToGoalProperty();
    }
    public void addObject( int x, int y, TypeOfObjectInMap currentObject) {
        mapDesign.addObject( x, y,currentObject);
        rulesHandling.invalidateBidings();

    }

    public LongBinding cellWithObjectProperty() {
        return mapDesign.cellWithObjectProperty();
    }



    public ObservableList<ObjectInMap> getObjectList(int line, int col) {
        return  mapDesign.getObjectList(line,col);
    }

    public void saveMap(File file) {
        try{
            fileSaver.save(file);
        }catch (Exception e){
            System.out.println( e.getMessage());
        }

    }
    public void loadMap(File file,String nameFile){
        try{
            fileReader.readFile(file, nameFile);
            List<String> elementsFromFile = fileReader.getElement();

            loadMap(elementsFromFile);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    public void loadMap(List<String> elementsFromFile){
        int width = elementsFromFile.get(0).length();
        int height = elementsFromFile.size();

        mapDesign = new MapDesign(elementsFromFile,width, height);


        resetAllValue();
    }
    public void resetAllValue(){
        maxFilledCells = mapDesign.getSize()/2;

        rulesHandling.changeMap(mapDesign.getCells());
        totalCells.invalidate();
        maxCellAvailable.invalidate();
        isReloadedMap.setValue(!isReloadedMap.getValue());
        hasBeenChanged = false;
        fileSaver.setMap(mapDesign);
        title.set("Sokoban");
    }



    public SimpleBooleanProperty isReloadedMapProperty() {
        return isReloadedMap;
    }

    public void deleteObject(int line, int col) {
        mapDesign.emptyCell(line,col);
        rulesHandling.invalidateBidings();

    }



    public boolean hasBeenChanged() {
       return hasBeenChanged ;
    }
    public void setHasBeenChanged(Boolean stateOfChanged) {
        hasBeenChanged = stateOfChanged;
        if(stateOfChanged)
        title.set("Sokoban *");
        else
            title.set("Sokoban");
    }

    public void newMap(int width, int height) {
        mapDesign = new MapDesign(width,height);
        resetAllValue();
    }

    public String getNameFile() {
        return fileReader.getNameFile();
    }

    public boolean isValidFile(File file) {
        try {

            return fileReader.isValideFile(file);
        }catch (FileNotFoundException e) {
            return false;
        }

    }

    public MapDesign getMap() {
        return mapDesign;
    }
}

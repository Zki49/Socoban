package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.List;

public class Board {
    private  Map map ;
    private final FileSaver fileSaver;
    private final FileReader fileReader = new FileReader();
    private int maxFilledCells;
    private boolean hasBeenChanged = false;
    private IntegerBinding maxCellAvailable ;
    private IntegerBinding totalCells;
    private final SimpleStringProperty title = new SimpleStringProperty("Sokoban");

    private final ErrorHandling errorHandling;
    private final SimpleBooleanProperty isReloadedMap = new SimpleBooleanProperty(false);
    private final static int MIN_Size = 10;
    private final static int MAX_Size = 50;
    public Board() {
         map = new Map(15,10);
        this.maxFilledCells = map.getSize()/2;
        fileSaver = new FileSaver(map);
        errorHandling = new ErrorHandling(map.getCells());
        maxCellAvailable = Bindings.createIntegerBinding(() -> map.getSize()/2, map.mapHeightProperty(), map.mapWidthProperty());
        totalCells = Bindings.createIntegerBinding(() -> map.getSize(), map.mapHeightProperty(), map.mapWidthProperty());
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
        return map.getMapWidth();
    }
    public int getMapHeight() {
        return map.getMapHeight();
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
        return errorHandling.getContaintErrorProperty();
    }
    public IntegerBinding totalCellsProperty() {
        return totalCells;
    }
    public BooleanBinding containsPlayer() {
        return errorHandling.notContainsPlayerProperty();
    }
    public BooleanBinding containsGoal() {
        return errorHandling.notContainsGoalProperty();
    }
    public BooleanBinding containsBox() {
        return errorHandling.notContainsBoxProperty();
    }
    public BooleanBinding containsWall() {
        return errorHandling.containsWallProperty();
    }
    public BooleanBinding boxIsEqualToGoal() {
        return errorHandling.boxIsNotEqualToGoalProperty();
    }
    /*verifie si le type est correct sinon envoie une exception*/
    public void addObject( int x, int y) {
        map.addObject( x, y);
        errorHandling.invalidateBidings();

    }
    public SimpleStringProperty getCurrentObject() {
        return map.currentObjectProperty();
    }
    public LongBinding cellWithObjectProperty() {
        return map.cellWithObjectProperty();
    }



    public ObservableList<ObjectInMap> getObjectList(int line, int col) {
        return  map.getObjectList(line,col);
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

        map = new Map(elementsFromFile,width, height);


        resetAllValue();
    }
    public void resetAllValue(){
        maxFilledCells = map.getSize()/2;

        errorHandling.changeMap(map.getCells());
        totalCells.invalidate();
        maxCellAvailable.invalidate();
        isReloadedMap.setValue(!isReloadedMap.getValue());
        hasBeenChanged = false;
        fileReader.setMap(map);
        fileSaver.setMap(map);
        title.set("Sokoban");
    }



    public SimpleBooleanProperty isReloadedMapProperty() {
        return isReloadedMap;
    }

    public void deleteObject(int line, int col) {
        map.emptyCell(line,col);
        errorHandling.invalidateBidings();

    }

    public SimpleStringProperty getTitle() {
        return title;
    }

    public boolean hasBeenChanged() {
       return hasBeenChanged ;
    }
    public void setHasBeenChanged(){
        hasBeenChanged = true;
        title.set("Sokoban *");
    }

    public void newMap(int width, int height) {
        map = new Map(width,height);
        resetAllValue();
    }

    public String getNameFile() {
        return fileReader.getNameFile();
    }
}

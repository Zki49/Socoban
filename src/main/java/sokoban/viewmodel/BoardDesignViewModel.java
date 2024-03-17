package sokoban.viewmodel;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import sokoban.model.BoardDesign;

import java.io.File;

public class BoardDesignViewModel {
    private  final BoardDesign boardDesign;



    public BoardDesignViewModel(BoardDesign boardDesign) {
        this.boardDesign = boardDesign;

    }
    public BoardDesignViewModel(BoardDesignViewModel boardDesignViewModel) {
        this.boardDesign = new BoardDesign();
    }

    public  int getMapWidth() {
        return boardDesign.getMapWidth();
    }
    public  int getMapHeight() {
        return boardDesign.getMapHeight();
    }


    public MapDesignViewModel getMapViewModel() {
        return new MapDesignViewModel(boardDesign);
    }

    public IntegerBinding getMaxCellAvailable() {
        return boardDesign.getMaxCellAvailable();
    }

    public LongBinding cellWithObjectProperty() {
        return boardDesign.cellWithObjectProperty();
    }

    public IntegerBinding getTotalCells() {
        return boardDesign.totalCellsProperty();
    }

    public BooleanBinding containsPlayer() {
        return boardDesign.containsPlayer();
    }


    public BooleanBinding containsError(){
        return boardDesign.contentError();
    }

    public BooleanBinding containsGoal(){
        return boardDesign.containsGoal();
    }

    public BooleanBinding boxIsEqualToGoal(){
        return boardDesign.boxIsEqualToGoal();
    }

    public BooleanBinding containsBox(){
        return boardDesign.containsBox();
    }

    public BooleanBinding containsWall(){
        return boardDesign.containsWall();
    }

    public SimpleStringProperty getCurrentObject(){
        return CellDesignViewModel.getCurrentObject();
    }

    public void saveMap(File file){
        boardDesign.saveMap(file);
    }
    public void loadMap(File file, String nameFile){
        boardDesign.loadMap(file, nameFile);
    }
    public void newMap(){
        newMap(15,10);
    }
    public void newMap(int width,int height){
        boardDesign.newMap(width,height);
    }
    public SimpleBooleanProperty reloadMapProperties(){
        return boardDesign.isReloadedMapProperty();
    }
    public int getMaxSize() {
        return boardDesign.getMaxSize();
    }
    public int getMinSize() {
        return boardDesign.getMinSize();
    }


    public SimpleStringProperty getTitle() {
        return boardDesign.getTitle();
    }

    public boolean hasBeenChanged() {
       return boardDesign.hasBeenChanged();
    }

    public String getNameFile() {
        return boardDesign.getNameFile();
    }

    public void setHasBeenChanged(boolean stateOfChanged) {
        boardDesign.setHasBeenChanged(stateOfChanged);
    }

    public boolean isValidFile(File file) {
       return boardDesign.isValidFile(file);
    }

    public BoardDesign getBoard() {
        return boardDesign;
    }
}

package sokoban.viewmodel;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import sokoban.model.Board;

import java.io.File;

public class BoardViewModel {
    private  final Board board;



    public BoardViewModel(Board board) {
        this.board = board;

    }
    public BoardViewModel(BoardViewModel boardViewModel) {
        this.board = new Board();
    }

    public  int getMapWidth() {
        return board.getMapWidth();
    }
    public  int getMapHeight() {
        return board.getMapHeight();
    }


    public MapViewModel getMapViewModel() {
        return new MapViewModel(board);
    }

    public IntegerBinding getMaxCellAvailable() {
        return board.getMaxCellAvailable();
    }

    public LongBinding cellWithObjectProperty() {
        return board.cellWithObjectProperty();
    }

    public IntegerBinding getTotalCells() {
        return board.totalCellsProperty();
    }

    public BooleanBinding containsPlayer() {
        return board.containsPlayer();
    }


    public BooleanBinding containsError(){
        return board.contentError();
    }

    public BooleanBinding containsGoal(){
        return board.containsGoal();
    }

    public BooleanBinding boxIsEqualToGoal(){
        return board.boxIsEqualToGoal();
    }

    public BooleanBinding containsBox(){
        return board.containsBox();
    }

    public BooleanBinding containsWall(){
        return board.containsWall();
    }

    public SimpleStringProperty getCurrentObject(){
        return CellViewModel.getCurrentObject();
    }

    public void saveMap(File file){
        board.saveMap(file);
    }
    public void loadMap(File file, String nameFile){
        board.loadMap(file, nameFile);
    }
    public void newMap(){
        newMap(15,10);
    }
    public void newMap(int width,int height){
        board.newMap(width,height);
    }
    public SimpleBooleanProperty reloadMapProperties(){
        return board.isReloadedMapProperty();
    }
    public int getMaxSize() {
        return board.getMaxSize();
    }
    public int getMinSize() {
        return board.getMinSize();
    }


    public SimpleStringProperty getTitle() {
        return board.getTitle();
    }

    public boolean hasBeenChanged() {
       return board.hasBeenChanged();
    }

    public String getNameFile() {
        return board.getNameFile();
    }

    public void setHasBeenChanged(boolean stateOfChanged) {
        board.setHasBeenChanged(stateOfChanged);
    }

    public boolean isValidFile(File file) {
       return board.isValidFile(file);
    }
}

package sokoban.viewmodel;
git
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import sokoban.model.Board;

import java.io.File;

public class BoardViewModel {
    private  final Board board;



    public BoardViewModel(Board board) {
        this.board = board;

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
        return board.getCurrentObject();
    }

    public void saveMap(File file){
        board.saveMap(file);
    }
    public void loadMap(File file){
        board.loadMap(file);
    }
    public SimpleBooleanProperty reloadMapProperties(){
        return board.isReloadedMapProperty();
    }
    public int getMaxHeight() {
        return board.getMapHeight();
    }
    public int getMaxWidth() {
        return board.getMaxWidth();
    }


    public SimpleStringProperty getTitle() {
        return board.getTitle();
    }

    public boolean hasBeenChanged() {
       return board.hasBeenChanged();
    }
}

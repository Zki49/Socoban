package sokoban.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleStringProperty;
import sokoban.model.Board;
import sokoban.model.Map;

public class BoardViewModel {
    private  final Board board;
    private final   MapViewModel mapViewModel;

    public BoardViewModel(Board board) {
        this.board = board;
        mapViewModel = new MapViewModel(board);
    }

    public static int mapWidth() {
        return Map.getMapdWidth();
    }

    public static int mapHeight() {
        return Map.getMapHeight();
    }

    public MapViewModel getMapViewModel() {
        return mapViewModel;
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

}

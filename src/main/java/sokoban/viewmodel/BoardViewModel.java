package sokoban.viewmodel;

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
}

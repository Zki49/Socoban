package sokoban.viewmodel;

import sokoban.model.Board;

public class MapViewModel {
    private final Board board;

    public MapViewModel(Board board) {
        this.board = board;
    }
    public CellViewModel getCellViewModel(int line, int col) {
        return new CellViewModel(line, col, board);
    }
}

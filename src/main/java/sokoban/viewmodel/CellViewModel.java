package sokoban.viewmodel;

import sokoban.model.Board;

public class CellViewModel {

    private final int line, col;
    private final Board board;

    public CellViewModel(int line, int col, Board board) {
        this.line = line;
        this.col = col;
        this.board = board;
    }
}

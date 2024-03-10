package sokoban.viewmodel;

import javafx.beans.binding.DoubleBinding;


import sokoban.model.Board;

public class MapViewModel {
    private final Board board;

    public MapViewModel(Board board) {
        this.board = board;
    }
    public CellViewModel getCellViewModel(int line, int col) {
        return new CellViewModel(line, col, board);
    }

    public int getmapWidth() {
      return  board.getMapWidth();
    }

    public int mapHeight() {
        return  board.getMapHeight();
    }
}

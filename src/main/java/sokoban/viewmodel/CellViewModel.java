package sokoban.viewmodel;

import sokoban.model.Board;

import java.util.List;

public class CellViewModel {

    private final int line, col;
    private final Board board;

    public CellViewModel(int line, int col, Board board) {
        this.line = line;
        this.col = col;
        this.board = board;
    }

    public void addObject(){
        board.addObject(line, col);
    }
    public List<String> getObjectsPath(){
       return board.getObjectsPath(line, col);
    }
}

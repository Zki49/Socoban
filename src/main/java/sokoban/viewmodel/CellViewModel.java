package sokoban.viewmodel;

import javafx.collections.ObservableList;
import sokoban.model.Board;
import sokoban.model.ObjectInMap;

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
    public ObservableList<ObjectInMap> getObjectList(){
        return board.getObjectList(line,col);
    }
}

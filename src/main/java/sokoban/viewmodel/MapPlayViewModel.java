package sokoban.viewmodel;

import sokoban.model.BoardDesign;
import sokoban.model.BoardPlay;

public class MapPlayViewModel extends MapViewModel {
    private final BoardPlay boardPlay;

    public MapPlayViewModel(BoardPlay boardPlay) {
        this.boardPlay = boardPlay;
    }
    public CellPlayViewModel getCellViewModel(int line, int col) {
        return new CellPlayViewModel(line, col, boardPlay);
    }

    public int getmapWidth() {
        return  boardPlay.getMapWidth();
    }

    public int mapHeight() {
        return  boardPlay.getMapHeight();
    }
}

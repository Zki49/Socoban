package sokoban.viewmodel;


import sokoban.model.BoardDesign;

public class MapDesignViewModel {
    private final BoardDesign boardDesign;

    public MapDesignViewModel(BoardDesign boardDesign) {
        this.boardDesign = boardDesign;
    }
    public CellDesignViewModel getCellViewModel(int line, int col) {
        return new CellDesignViewModel(line, col, boardDesign);
    }

    public int getmapWidth() {
      return  boardDesign.getMapWidth();
    }

    public int mapHeight() {
        return  boardDesign.getMapHeight();
    }
}

package sokoban.viewmodel;

import sokoban.model.BoardDesign;
import sokoban.model.BoardPlay;

public class BoardPlayViewModel  {

    private  final BoardPlay boardPlay;

    public BoardPlayViewModel(BoardPlay boardPlay) {
        this.boardPlay = boardPlay;

    }
    public BoardPlayViewModel(BoardDesignViewModel boardPlay) {
        this.boardPlay = new BoardPlay(boardPlay.getBoard());
    }

    public  int getMapWidth() {
        return boardPlay.getMapWidth();
    }
    public  int getMapHeight() {
        return boardPlay.getMapHeight();
    }

    public MapPlayViewModel getMapViewModel() {
        return new MapPlayViewModel(boardPlay);
    }

    public void moveUp() {
        boardPlay.moveUp();
    }
    public void moveDown(){
        boardPlay.moveDown();
    }

    public void moveRight(){
        boardPlay.moveRight();
    }

    public void moveLeft() {
        boardPlay.moveLeft();
    }

}

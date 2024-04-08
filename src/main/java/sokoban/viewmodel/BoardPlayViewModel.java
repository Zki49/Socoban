package sokoban.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleIntegerProperty;
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
    public void moveBack(){
        boardPlay.moveBack();
    }
    public void movefront() {
        boardPlay.movefront();
    }

    public BooleanBinding isWon(){
        return boardPlay.isWon();
    }
    public BooleanBinding isNotWon(){
        return boardPlay.isNotWon();
    }

    public IntegerBinding numberofGoals(){
        return boardPlay.numberOfGoal();
    }
    public IntegerBinding numberBoxesOnGoal(){
        return boardPlay.numberBoxOnGoal();
    }
    public SimpleIntegerProperty scoreProperty(){
        return boardPlay.scoreProperty();
    }

    public void showMushroom() {
        boardPlay.showMushroom();
    }
}

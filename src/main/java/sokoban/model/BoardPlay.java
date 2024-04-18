package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class BoardPlay extends Board{
    private MapPlay mapPlay;

    private SimpleStringProperty titleMushrumButton = new SimpleStringProperty("Show mushroom");
    private SimpleBooleanProperty isVisibleMushroom = new SimpleBooleanProperty(false);
    private final MoveExecutor moveExecutor = new MoveExecutor();
    public BoardPlay(BoardDesign boardDesign){
        mapPlay = new MapPlay(boardDesign.getMap());

    }

    public int getMapWidth() {
        return mapPlay.getMapWidth();
    }
    public int getMapHeight() {
        return mapPlay.getMapHeight();
    }

    public void setIsVisibleMushroom(boolean isVisibleMushroom) {
        this.isVisibleMushroom.set(isVisibleMushroom);
        changeTitleButton();

    }

    private void changeTitleButton() {
        String title = isVisibleMushroom.get() ? "hide mushroom" : "show mushroom";
        titleMushrumButton.set(title);
    }

    public SimpleStringProperty titleMushrumButtonProperty() {
        return titleMushrumButton;
    }


    public void moveLeft() {
        if(!isVisibleMushroom.getValue() && mapPlay.isAvailableMove(LastMove.LEFT))
            moveExecutor.executeMove(new moveCommandeDirection(mapPlay, LastMove.LEFT, () -> mapPlay.moveLeft()));

    }
    public void moveRight() {
        if(!isVisibleMushroom.getValue() && mapPlay.isAvailableMove(LastMove.RIGHT))
         moveExecutor.executeMove(new moveCommandeDirection(mapPlay, LastMove.RIGHT, () -> mapPlay.moveRight()));
    }

    public void moveDown() {
        if(!isVisibleMushroom.getValue() && mapPlay.isAvailableMove(LastMove.DOWN))
         moveExecutor.executeMove(new moveCommandeDirection(mapPlay, LastMove.DOWN, ()-> mapPlay.moveDown()));
    }

    public void moveUp() {
        if(!isVisibleMushroom.getValue() && mapPlay.isAvailableMove(LastMove.UP))
            moveExecutor.executeMove(new moveCommandeDirection(mapPlay, LastMove.UP, () -> mapPlay.moveUp()));
    }
    public void moveBack(){
        if(!isVisibleMushroom.getValue())
             moveExecutor.moveBack((int penality) -> mapPlay.incrementScore(penality));
    }
    public void movefront() {
        if(!isVisibleMushroom.getValue())
             moveExecutor.moveFront();
    }
    public ObservableList<ObjectInMap> getObjectList(int line, int col) {
        return  mapPlay.getObjectList(line,col);
    }

    public BooleanBinding isWon(){
        return mapPlay.isWonProperty();
    }
    public IntegerBinding numberOfGoal(){
        return mapPlay.numberGoalsProperty();
    }
    public SimpleIntegerProperty scoreProperty(){
        return mapPlay.scoreProperty();
    }
    public IntegerBinding numberBoxOnGoal(){
        return mapPlay.numberBoxOnGoalProperty();
    }

    public BooleanBinding isNotWon() {
        return mapPlay.isNotWonProperty();
    }

    public int getNumberBoxe(int line, int col) {
        return mapPlay.getNumberBoxe(line,col);
    }



    public SimpleBooleanProperty showMushroomProperty(){
        return mapPlay.showMushroomProperty();
    }

    public void showMushroom() {
        setIsVisibleMushroom(!isVisibleMushroom.get());
        mapPlay.showMushroom();
    }

    public void shuffleBox() {
        if(!isVisibleMushroom.getValue()){
            moveExecutor.executeMove(new MoveCommandeShuffle(mapPlay ));
            mapPlay.incrementScore(20);
        }


    }

    public boolean containsMushroom(int line, int col) {
        return mapPlay.containsMushroom(line,col);
    }
}

package sokoban.viewmodel;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleDoubleProperty;

import javafx.collections.ObservableList;
import sokoban.model.Board;
import sokoban.model.ObjectInMap;

import java.util.ArrayList;
import java.util.List;

public class CellViewModel {
    private static final double DEFAULT_SCALE = 0.5;
    private static final double EPSILON = 1e-3;
    private final SimpleDoubleProperty scale = new SimpleDoubleProperty(DEFAULT_SCALE);
    private final BooleanBinding mayIncrementScale = scale.lessThan(1 - EPSILON);
    private final BooleanBinding mayDecrementScale = scale.greaterThan(0.1 + EPSILON);
    private static final SimpleStringProperty currentObject = new SimpleStringProperty("");

    private final int line, col;
    private final Board board;

    public CellViewModel(int line, int col, Board board) {
        this.line = line;
        this.col = col;
        this.board = board;
    }

    public void addObject() {
        if(currentObject.equals("")){
            if(currentObject.getValue() == "GROUND"){
                board.deleteObject(line,col);
            }

            else {
                board.addObject(line, col, currentObject.getValue());
            }
        }


        scale.set(DEFAULT_SCALE);
    }
    public List<String> getObjectsPath() {
        List<String> paths = new ArrayList<String>();
        for(ObjectInMap objectInMap : getObjectList()){
            paths.add(getPath(objectInMap));
        }
       return paths;
    }
    public String getPath(ObjectInMap objectInMap ){
        return switch (objectInMap.getClass().getName()){
            case "sokoban.model.Wall" -> "wall.png";
            case "sokoban.model.Goal" -> "goal.png";
            case "sokoban.model.Player" -> "player.png";
            default -> "box.png";

        };

    }
    public ObservableList<ObjectInMap> getObjectList() {
        return board.getObjectList(line,col);
    }

    public StringProperty getCurrentObjectPath() {
        return currentObject;
    }


    public void deleteObject() {
        board.deleteObject(line, col);

    }

    public void hasBeenChanged(boolean stateOfChanged) {
        board.setHasBeenChanged(stateOfChanged);
    }
    public static SimpleStringProperty getCurrentObject(){
        return currentObject;
    }

}

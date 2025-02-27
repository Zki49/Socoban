package sokoban.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import sokoban.model.BoardDesign;
import sokoban.model.BoardPlay;
import sokoban.model.ObjectInMap;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

public class CellPlayViewModel {
    private static final double DEFAULT_SCALE = 0.5;
    private static final double EPSILON = 1e-3;
    private final SimpleDoubleProperty scale = new SimpleDoubleProperty(DEFAULT_SCALE);

    private final BooleanBinding mayIncrementScale = scale.lessThan(1 - EPSILON);
    private final BooleanBinding mayDecrementScale = scale.greaterThan(0.1 + EPSILON);
    private static final SimpleStringProperty currentObject = new SimpleStringProperty("");

    private final int line, col;
    private final BoardPlay boardPlay;

    public CellPlayViewModel(int line, int col, BoardPlay boardPlay) {
        this.line = line;
        this.col = col;
        this.boardPlay = boardPlay;
    }



    public SimpleBooleanProperty showMushroomProperty(){
        return boardPlay.showMushroomProperty();
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
            case "sokoban.model.Wall" -> "wall";
            case "sokoban.model.Goal" -> "goal";
            case "sokoban.model.Player" -> "player";
            case "sokoban.model.Mushroom" -> "mushroom";
            default -> "box";

        };

    }
    public ObservableList<ObjectInMap> getObjectList() {
        return boardPlay.getObjectList(line,col);
    }

    public int getNumberBoxe() {
       return boardPlay.getNumberBoxe(line , col);
    }

    public void shuffleBox() {
        if(containsMushroom()){
            boardPlay.shuffleBox();
        }

    }
    public boolean containsMushroom(){
       return boardPlay.containsMushroom(line, col);
    }

    public BooleanBinding isWon() {
        return boardPlay.isWon();
    }
}

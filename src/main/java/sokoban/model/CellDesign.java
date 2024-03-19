package sokoban.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

public class CellDesign extends Cell {



    //private final List<ObjectInMap >objectList = new ArrayList<>();
    //cette liste sera observée par le cellview




    public CellDesign() {
    }

    public CellDesign(String symbol){
        fillListBySymbol(symbol);
    }

    public static boolean isValideSymbole(char symbole) {
        String regex = "[ @#.$*+]"; // Character class containing the allowed characters
        return String.valueOf(symbole).matches(regex);

    }




    @Override
    public String toString() {
        return getSign();
    }
    public String getSign(){
        if(objectList.isEmpty()){
            return " ";
        }
        if(this.containsWall()){
            return "#";
        }
        if(objectList.size() == 1){
            if(containsPlayer()){
                return "@";
            }
            if(containsBox()){
                return "$";
            }
            else{
                return ".";
            }
        }
        if(containsBox() && containsGoal()){
            return "*";
        }
        else{
            return "+";
        }


    }
}

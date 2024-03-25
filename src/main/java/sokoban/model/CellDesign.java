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
    public void addObjectInMap(String  stringTypeOfObjectInMap){
        var typeOfObjectInMap = TypeOfObjectInMap.valueOf(stringTypeOfObjectInMap);
        if(!doContainThisObject(typeOfObjectInMap.getObjectInMap())){
            ObjectInMap newObjectInMap = typeOfObjectInMap.getObjectInMap();
            if(containsWall() || stringTypeOfObjectInMap.equals("WALL")) {
                objectList.clear();
            }
            if (containsBox() && stringTypeOfObjectInMap.equals("PLAYER")){
                objectList.remove(0);
            }
            if (containsBox() && stringTypeOfObjectInMap.equals("WALL")){
                objectList.clear();
            }
            if (containsPlayer() && stringTypeOfObjectInMap.equals("PLAYER") ){
                objectList.remove(0);
            }
            objectList.add(newObjectInMap);
            Collections.sort(objectList);
        }

    }
    public boolean doContainThisObject(ObjectInMap newObject){
        for (ObjectInMap objectInMap : objectList) {
            if (objectInMap.getClass().equals(newObject.getClass())) {
                return true;
            }
        }
        return false;
    }
}

package sokoban.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

class CellDesign extends Cell {



    //private final List<ObjectInMap >objectList = new ArrayList<>();
    //cette liste sera observée par le cellview




    CellDesign() {
    }

    CellDesign(String symbol){
        fillListBySymbol(symbol);
    }
    //verifie si le symbole est valide (utiliser dans isValideElementInfile())
    static boolean isValideSymbole(char symbole) {
        String regex = "[ @#.$*+]"; // Character class containing the allowed characters
        return String.valueOf(symbole).matches(regex);

    }



    // methode outils utiliser dans le toString de mapDesign
    @Override
    public String toString() {
        return getSign();
    }
    String getSign(){
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
    void addObjectInMap(TypeOfObjectInMap typeOfObjectInMap){

        if(!doContainThisObject(typeOfObjectInMap.getObjectInMap())){

            ObjectInMap newObjectInMap = typeOfObjectInMap.getObjectInMap();

            if(containsWall() || typeOfObjectInMap.name().equals("WALL")) {
                objectList.clear();
            }
            if (containsBox() && typeOfObjectInMap.name().equals("PLAYER")){
                objectList.remove(0);
            }
            if (containsBox() && typeOfObjectInMap.name().equals("WALL")){
                objectList.clear();
            }
            if (containsPlayer() && typeOfObjectInMap.name().equals("PLAYER") ){
                objectList.remove(0);
            }
            if (containsPlayer() && typeOfObjectInMap.name().equals("BOX") ){
                objectList.remove(0);
            }
            objectList.add(newObjectInMap);
            Collections.sort(objectList);
        }

    }
    boolean doContainThisObject(ObjectInMap newObject){
        for (ObjectInMap objectInMap : objectList) {
            if (objectInMap.getClass().equals(newObject.getClass())) {
                return true;
            }
        }
        return false;
    }
}

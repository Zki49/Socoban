package sokoban.model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    //cette liste sera observée par le cellview
    private final ObservableList<ObjectInMap> objectList = FXCollections.observableArrayList();

    public boolean containsPlayer() {
        for (ObjectInMap objectInMap : objectList) {
            if (objectInMap.getTypeOfObjectInMap() == TypeOfObjectInMap.PLAYER) {
                return true;
            }
        }
        return false;
    }
    public boolean containsBox(){
        for (ObjectInMap objectInMap : objectList) {
            if (objectInMap.getTypeOfObjectInMap() == TypeOfObjectInMap.BOX) {
                return true;
            }
        }
        return false;
    }
    public boolean containsGoal(){
        for (ObjectInMap objectInMap : objectList) {
            if (objectInMap.getTypeOfObjectInMap() == TypeOfObjectInMap.GOAL) {
                return true;
            }
        }
        return false;
    }
    public boolean containsWall(){
        for (ObjectInMap objectInMap : objectList) {
            if (objectInMap.getTypeOfObjectInMap() == TypeOfObjectInMap.WALL) {
                return true;
            }
        }
        return false;
    }
    public boolean containsObjectInMap(){
       return !objectList.isEmpty();
    }

    /*on implementera les conditions pour ajouté un object et le comportement a adopter lors d'un ajout dans cette methode*/
    public void addObjectInMap(TypeOfObjectInMap typeOfObjectInMap){
        ObjectInMap newObjectInMap = typeOfObjectInMap.getObjectInMap();

        objectList.add(newObjectInMap);
    }


    
}

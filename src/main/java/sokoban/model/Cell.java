package sokoban.model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cell {

    //cette liste sera observée par le cellview
    private final ObservableList<ObjectInMap> objectList = FXCollections.observableArrayList();
    //private final List<ObjectInMap >objectList = new ArrayList<>();

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
    public void addObjectInMap(String  stringTypeOfObjectInMap){
        var typeOfObjectInMap = TypeOfObjectInMap.valueOf(stringTypeOfObjectInMap);
        ObjectInMap newObjectInMap = typeOfObjectInMap.getObjectInMap();
        if(containsWall() || stringTypeOfObjectInMap.equals("WALL")) {
            objectList.clear();
        }
        objectList.add(newObjectInMap);
        Collections.sort(objectList);
    }


    public List<String> getObjectsPath() {
        List<String> objectsPath = new ArrayList<>();
        for (ObjectInMap objectInMap : objectList) {
            //requete trop longue créer methode dans object in map;
            objectsPath.add(objectInMap.getPath());
        }
        return objectsPath;
    }
    public void delete(){
        objectList.clear();
    }
}

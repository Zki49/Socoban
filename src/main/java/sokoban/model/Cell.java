package sokoban.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

 abstract class Cell {

    protected final ObservableList<ObjectInMap> objectList = FXCollections.observableArrayList();



     void fillListBySymbol(String symbol) {
            objectList.clear();
        switch(symbol){
            case "@" -> {
                objectList.add(new Player());
            }
            case "#" -> {
                objectList.add(new Wall());
            }
            case "$" -> {
                objectList.add(new Box());
            }
            case "." -> {
                objectList.add(new Goal());
            }
            case "+" -> {
                objectList.add(new Player());
                objectList.add(new Goal());
            }
            case "*" -> {
                objectList.add(new Box());
                objectList.add(new Goal());
            }
            default -> {}
        }
    }
    boolean containsPlayer() {
        for (ObjectInMap objectInMap : objectList) {
            if (objectInMap.getTypeOfObjectInMap() == TypeOfObjectInMap.PLAYER) {
                return true;
            }
        }
        return false;
    }
    ObservableList<ObjectInMap> getObjectList() {
        return FXCollections.unmodifiableObservableList(objectList);
    }
    boolean containsBox(){
        for (ObjectInMap objectInMap : objectList) {
            if (objectInMap.getTypeOfObjectInMap() == TypeOfObjectInMap.BOX) {
                return true;
            }
        }
        return false;
    }
    boolean containsGoal(){
        for (ObjectInMap objectInMap : objectList) {
            if (objectInMap.getTypeOfObjectInMap() == TypeOfObjectInMap.GOAL) {
                return true;
            }
        }
        return false;
    }
    boolean containsWall(){
        for (ObjectInMap objectInMap : objectList) {
            if (objectInMap.getTypeOfObjectInMap() == TypeOfObjectInMap.WALL) {
                return true;
            }
        }
        return false;
    }
    boolean containsObjectInMap(){
        return !objectList.isEmpty();

    }
    /*on implementera les conditions pour ajouté un object et le comportement a adopter lors d'un ajout dans cette methode*/
    void delete(){
        if(objectList.size() > 1){
            objectList.remove(objectList.size() - 1);
        }
        else{
            if(!objectList.isEmpty()) {
                objectList.clear();
            }

        }

    }
    void deleteByIdx(int idx){
        objectList.remove(idx);
    }

}

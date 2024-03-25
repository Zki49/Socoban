package sokoban.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

 abstract class Cell {

    final ObservableList<ObjectInMap> objectList = FXCollections.observableArrayList();
    public void fillListBySymbol(String symbol) {

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
    public boolean containsPlayer() {
        for (ObjectInMap objectInMap : objectList) {
            if (objectInMap.getTypeOfObjectInMap() == TypeOfObjectInMap.PLAYER) {
                return true;
            }
        }
        return false;
    }
    public ObservableList<ObjectInMap> getObjectList() {
        return FXCollections.unmodifiableObservableList(objectList);
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
    public void delete(){
        if(objectList.size() > 1){
            objectList.remove(1);
        }
        else{
            if(!objectList.isEmpty()) {
                objectList.clear();
            }

        }

    }
    public void deleteByIdx(int idx){
        objectList.remove(idx);
    }

}

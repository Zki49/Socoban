package sokoban.model;

import javafx.collections.ObservableList;

import java.util.Collections;

class CellPlay extends Cell{
    //private final List<ObjectInMap >objectList = new ArrayList<>();
    //cette liste sera observée par le cellview




     CellPlay() {
    }

    CellPlay(CellDesign cellDesign){
        fillListByCellDesign(cellDesign);
    }

    private void fillListByCellDesign(CellDesign cellDesign) {
        fillListBySymbol(cellDesign.getSign());
    }





     int getIndexOfBoxe() {
        int index = 0;
        for(ObjectInMap element : objectList){
            if(element instanceof Box){

                index = ((Box) element).getIndex();
            }
        }
        return index;
    }

     Box getBox() {
        return (Box)objectList.get(0);
    }

     void addObjectInMap(ObjectInMap object) {
        objectList.add(object);
        Collections.sort(objectList);
    }
     void addMushroom(){
        objectList.add(new Mushroom());
        Collections.sort(objectList);
    }
     void reset(){
        objectList.clear();
    }

     void reset(CellDesign designCell) {
        ObservableList<ObjectInMap> designList = designCell.getObjectList();
        if(mustBeReset(designList)){
            fillListBySymbol(designCell.getSign());
        }
    }
     boolean mustBeReset(ObservableList<ObjectInMap> designList){
        if(objectList.size() == designList.size()) {
            for(int i = 0; i < designList.size(); i++) {
                if(!objectList.get(i).getClass().equals(designList.get(i).getClass())){
                    return true;
                }
            }
            return false;
        }
        return true;
    }


     void deleteMushroom() {
        for(ObjectInMap object : objectList){
            if(object instanceof Mushroom){
                objectList.remove(object);
                break;
            }
        }
    }

    boolean containsMushroom() {
        for(ObjectInMap object : objectList){
            if(object instanceof Mushroom){
               return true;
            }
        }
        return false;
    }

    ObjectInMap getMushroom() {
        for(ObjectInMap object : objectList){
            if(object instanceof Mushroom){
                return object;
            }
        }
        return null;
    }
}

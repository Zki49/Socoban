package sokoban.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

public class CellPlay extends Cell{
    //private final List<ObjectInMap >objectList = new ArrayList<>();
    //cette liste sera observée par le cellview




    public CellPlay() {
    }

    public CellPlay(String symbol){
        fillListBySymbol(symbol);
    }
    public CellPlay(CellDesign cellDesign){
        fillListByCellDesign(cellDesign);
    }

    private void fillListByCellDesign(CellDesign cellDesign) {
        fillListBySymbol(cellDesign.getSign());
    }


    public void addBoxInGame(){
        objectList.add(new Box());
        Collections.sort(objectList);
    }
    public void addPlayer() {
        objectList.add(new Player());
        Collections.sort(objectList);
    }


    public int getIndexOfBoxe() {
        int index = 0;
        for(ObjectInMap element : objectList){
            if(element instanceof Box){

                index = ((Box) element).getIndex();
            }
        }
        return index;
    }

    public ObjectInMap getBox() {
        return objectList.get(0);
    }

    public void addBox(ObjectInMap box) {
        objectList.add(box);
        Collections.sort(objectList);
    }
}

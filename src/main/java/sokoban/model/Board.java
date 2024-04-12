package sokoban.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public abstract class Board {
    final SimpleStringProperty title = new SimpleStringProperty("Sokoban");

    abstract ObservableList<ObjectInMap> getObjectList(int line, int col) ;
    public SimpleStringProperty getTitle() {
        return title;
    }

    abstract  int getMapWidth();
    abstract  int getMapHeight();


}

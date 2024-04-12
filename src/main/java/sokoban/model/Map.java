package sokoban.model;

import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

import java.util.List;

abstract class Map {
     static int MapWidth;
     static int MapHeight;
     //addpointplayer
     final SimpleIntegerProperty mapWidth = new SimpleIntegerProperty();
     final SimpleIntegerProperty mapHeight = new SimpleIntegerProperty();
     final SimpleIntegerProperty totalCells = new SimpleIntegerProperty(mapWidth.getValue() * mapHeight.getValue());

     int getMapWidth() {
          return MapWidth;
     }

     SimpleIntegerProperty mapWidthProperty() {
          return mapWidth;
     }
     static int getMapHeight() {
          return MapHeight;
     }

     SimpleIntegerProperty mapHeightProperty() {
          return mapHeight;
     }
     int getSize() {
          return MapWidth * MapHeight;
     }
     abstract void fillMap();
      abstract  ObservableList<ObjectInMap> getObjectList(int line, int col);
}

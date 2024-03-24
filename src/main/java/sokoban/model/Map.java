package sokoban.model;

import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;

public abstract class Map {
     static int MapWidth;
     static int MapHeight;
     final SimpleIntegerProperty mapWidth = new SimpleIntegerProperty();
     final SimpleIntegerProperty mapHeight = new SimpleIntegerProperty();
     final SimpleIntegerProperty totalCells = new SimpleIntegerProperty(mapWidth.getValue() * mapHeight.getValue());

     public int getMapWidth() {
          return MapWidth;
     }

     public SimpleIntegerProperty mapWidthProperty() {
          return mapWidth;
     }
     public static int getMapHeight() {
          return MapHeight;
     }

     public SimpleIntegerProperty mapHeightProperty() {
          return mapHeight;
     }
     public int getSize() {
          return MapWidth * MapHeight;
     }
}

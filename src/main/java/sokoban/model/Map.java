package sokoban.model;

import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Objects;

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

     protected Point currentCellWithPlayer;
      abstract  ObservableList<ObjectInMap> getObjectList(int line, int col);
     class Point{
          int line;
          int col;
          Point(int line, int col){
               this.line = line;
               this.col = col;
          }

          @Override
          public boolean equals(Object o) {
               if (this == o) return true;
               if (!(o instanceof Point point)) return false;
               return line == point.line && col == point.col;
          }

          @Override
          public int hashCode() {
               return Objects.hash(line, col);
          }
     }

}

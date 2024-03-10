package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import sokoban.viewmodel.BoardViewModel;
import sokoban.viewmodel.MapViewModel;

public class MapView extends GridPane {

    private static final int PADDING = 10;

    private   int MAP_WIDTH ;
    private  int MAP_HEIGHT;

    public MapView(MapViewModel mapViewModel, DoubleBinding mapWidth, DoubleBinding mapHeight) {

        setPadding(new Insets(PADDING));

        MAP_WIDTH = mapViewModel.getmapWidth();
        MAP_HEIGHT = mapViewModel.mapHeight();
        DoubleBinding cellWidth = mapWidth
                .subtract(PADDING * 2)
                .divide(MAP_WIDTH);
        DoubleBinding cellSize = Bindings.createDoubleBinding( () -> {
                   return mapWidth.subtract(PADDING * 2)
                            .divide(MAP_WIDTH).getValue();
                }

        , mapWidth, mapHeight);

        /*
        *  DoubleBinding mapWidth = Bindings.createDoubleBinding(
                () -> {
                    var size = Math.min(widthProperty().get(), heightProperty().get() - menuBox.widthProperty().get());;
                    return Math.floor(size / MAP_WIDTH) * MAP_WIDTH;
                },
                widthProperty(),
                heightProperty(),
                headerBox.heightProperty());*/
        DoubleBinding cellHeight = mapHeight
                .subtract(PADDING * 2)
                .divide(MAP_HEIGHT);
        for (int i = 0; i < MAP_HEIGHT; ++i) {
            for (int j = 0; j < MAP_WIDTH; ++j) {
                CellView cellView = new CellView(mapViewModel.getCellViewModel(i, j), cellSize);
                add(cellView, j, i); // lignes/colonnes inversées dans gridpane
            }
        }

    }
}

package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import sokoban.viewmodel.MapDesignViewModel;
import sokoban.viewmodel.MapPlayViewModel;

public class MapPlayView extends GridPane {
    private static final int PADDING = 10;

    private   int MAP_WIDTH ;
    private  int MAP_HEIGHT;

    public MapPlayView(MapPlayViewModel mapPlayViewModel, DoubleBinding mapWidth, DoubleBinding mapHeight) {

        setPadding(new Insets(PADDING));

        MAP_WIDTH = mapPlayViewModel.getmapWidth();
        MAP_HEIGHT = mapPlayViewModel.mapHeight();
        DoubleBinding cellWidth = mapWidth
                .subtract(PADDING * 2)
                .divide(MAP_WIDTH);
        DoubleBinding cellSize = Bindings.createDoubleBinding( () -> {
                    return mapWidth.subtract(PADDING * 2)
                            .divide(MAP_WIDTH).getValue();
                }

                , mapWidth, mapHeight);
        setPadding(new Insets(0,0,40,0));
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
                CellPlayView cellPlayView = new CellPlayView(mapPlayViewModel.getCellViewModel(i, j), cellSize);
                add(cellPlayView, j, i); // lignes/colonnes inversées dans gridpane
            }
        }

    }
}

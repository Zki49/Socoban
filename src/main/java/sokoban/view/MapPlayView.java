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
        DoubleBinding cellSize3 = Bindings.createDoubleBinding( () -> {
                    return calculateCellSize(mapWidth.get(), mapHeight.get(), MAP_WIDTH, MAP_HEIGHT);
                }

                , mapWidth, mapHeight);


        DoubleBinding cellHeight = mapHeight
                .subtract(PADDING * 2)
                .divide(MAP_HEIGHT);
        for (int i = 0; i < MAP_HEIGHT; ++i) {
            for (int j = 0; j < MAP_WIDTH; ++j) {
                CellPlayView cellPlayView = new CellPlayView(mapPlayViewModel.getCellViewModel(i, j), cellSize3);
                add(cellPlayView, j, i); // lignes/colonnes inversées dans gridpane
            }
        }

    }
    public double calculateCellSize(double availableWidth, double availableHeight, int numCellsWidth, int numCellsHeight) {
        double cellSizeWidth = availableWidth / numCellsWidth;
        double cellSizeHeight = availableHeight / numCellsHeight;

        // Choose the smaller of the two cell sizes to ensure the cells fit within the available space
        return Math.min(cellSizeWidth, cellSizeHeight);
    }
}

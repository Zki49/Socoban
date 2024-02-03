package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import sokoban.viewmodel.BoardViewModel;
import sokoban.viewmodel.MapViewModel;

public class MapView extends GridPane {

    private static final int PADDING = 10;

    private static final int MAP_WIDTH = BoardViewModel.mapWidth();
    private static final int MAP_HEIGHT = BoardViewModel.mapHeight();

    public MapView(MapViewModel mapViewModel, DoubleBinding mapWidth, DoubleBinding mapHeight) {

        setPadding(new Insets(PADDING));
        DoubleBinding cellWidth = mapWidth
                .subtract(PADDING * 2)
                .divide(MAP_WIDTH);
        DoubleBinding cellHeight = mapHeight
                .subtract(PADDING * 2)
                .divide(MAP_HEIGHT);
        for (int i = 0; i < MAP_HEIGHT; ++i) {
            for (int j = 0; j < MAP_WIDTH; ++j) {
                CellView cellView = new CellView(mapViewModel.getCellViewModel(i, j), cellWidth);
                add(cellView, j, i); // lignes/colonnes inversées dans gridpane
            }
        }

    }
}

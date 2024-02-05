package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sokoban.viewmodel.BoardViewModel;

import java.util.Objects;

public class BoardView extends BorderPane {

    private static final int MAP_WIDTH = BoardViewModel.mapWidth();
    private static final int MAP_HEIGHT = BoardViewModel.mapHeight();
    private static final int SCENE_MIN_WIDTH = 420;
    private Header headerBox = new Header();
    private Menu menuBox = new Menu();

    private static final int SCENE_MIN_HEIGHT = 420;
    private final Label headerLabel = new Label("");
    private final BoardViewModel boardViewModel;
    public BoardView(Stage primaryStage, BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;

        start(primaryStage);
    }
    private void start(Stage primaryStage) {
        configMainComponents(primaryStage);
        Scene scene = new Scene(this, SCENE_MIN_WIDTH, SCENE_MIN_HEIGHT);
       // String cssFile = Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm();
        //scene.getStylesheets().add(cssFile);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
    }
    private void configMainComponents(Stage stage) {
        stage.setTitle("Sokoban");

        createGrid();
        createHeader();
        createMenu();
    }
    private void createHeader() {

        headerBox.setAlignment(Pos.CENTER);
        setTop(headerBox);
    }
    private void createMenu(){
        menuBox.setAlignment(Pos.CENTER);
        setLeft(menuBox);
    }

    private void createGrid() {

        /*
        * */
        DoubleBinding mapWidth = Bindings.createDoubleBinding(
                () -> {
                    var size = Math.min(widthProperty().get(), heightProperty().get() - menuBox.widthProperty().get());;
                    return Math.floor(size / MAP_WIDTH) * MAP_WIDTH;
                },
                widthProperty(),
                heightProperty(),
                headerBox.heightProperty());

        /*
        * */
        DoubleBinding mapHeight = Bindings.createDoubleBinding(
                () -> {
                    var size = Math.min(widthProperty().get(), heightProperty().get() - headerBox.heightProperty().get());
                    return Math.floor(size / MAP_HEIGHT) * MAP_HEIGHT;
                },
                widthProperty(),

                heightProperty());
        MapView mapView = new MapView(boardViewModel.getMapViewModel(), mapWidth, mapHeight);

        // Grille carrée
        mapView.minHeightProperty().bind(mapHeight);
        mapView.minWidthProperty().bind(mapWidth);
        mapView.maxHeightProperty().bind(mapHeight);
        mapView.maxWidthProperty().bind(mapWidth);
        /*
            * */
        setCenter(mapView);
    }

}

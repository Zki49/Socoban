package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sokoban.viewmodel.BoardViewModel;

public class BoardPlayView extends BoardView{

    private  int MAP_WIDTH;
    private  int MAP_HEIGHT;
    private static final int SCENE_MIN_WIDTH = 600;
    private  MapView mapView;
    private BooleanProperty isFinish = new SimpleBooleanProperty(false);

    public BoardPlayView(Stage primaryStage, BoardViewModel boardViewModel){
        super(primaryStage, boardViewModel);
        start();
        setFooter();
    }

    @Override
    void configMainComponents(Stage stage) {

        createMap();
    }

    @Override
    void createHeader() {

    }

    @Override
    void createMap() {
        MAP_WIDTH = getBoardViewModel().getMapWidth();
        MAP_HEIGHT = getBoardViewModel().getMapHeight();

        DoubleBinding mapWidth = Bindings.createDoubleBinding(
                () -> {
                    var size = Math.min(widthProperty().get(), heightProperty().get() );;
                    return Math.floor(size / MAP_WIDTH) * MAP_WIDTH;
                },
                widthProperty(),
                heightProperty()
                );

        /*
         * */
        DoubleBinding mapHeight = Bindings.createDoubleBinding(
                () -> {
                    var size = Math.min(widthProperty().get(), heightProperty().get() );
                    return Math.floor(size / MAP_HEIGHT) * MAP_HEIGHT;
                },
                widthProperty(),

                heightProperty());
        mapView = new MapView(getBoardViewModel().getMapViewModel(), mapWidth, mapHeight);

        // Grille carrée
        mapView.minHeightProperty().bind(mapHeight);
        mapView.minWidthProperty().bind(mapWidth);
        mapView.maxHeightProperty().bind(mapHeight);
        mapView.maxWidthProperty().bind(mapWidth);

        /*
         * */
        setCenter(mapView);
    }

    public boolean isIsFinish() {
        return isFinish.get();
    }

    public BooleanProperty isFinishProperty() {
        return isFinish;
    }

    @Override
    void setFooter() {
        HBox footer = new HBox();
        Button finish = new Button("finish");
        footer.getChildren().add(finish);
        setBottom(footer);
        finish.setOnAction(event -> {
            System.out.println("from play");
           isFinish.set(true);
        });
    }
}

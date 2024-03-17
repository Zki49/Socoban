package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sokoban.viewmodel.BoardDesignViewModel;
import sokoban.viewmodel.BoardPlayViewModel;

public class BoardPlayView extends BorderPane {

    private  int MAP_WIDTH;
    private  int MAP_HEIGHT;
    private static final int SCENE_MIN_WIDTH = 600;
    private static final int SCENE_MIN_HEIGHT = 420;
    private MapPlayView mapView;
    private BooleanProperty isFinish = new SimpleBooleanProperty(false);

    private final SimpleStringProperty title = new SimpleStringProperty("");

    private final Stage primaryStage;

    private final BoardPlayViewModel boardPlayViewModel;


    public BoardPlayView(Stage primaryStage, BoardPlayViewModel boardPlayViewModel){
//        super(primaryStage, boardPlayViewModel);
        this.primaryStage = primaryStage;
        this.boardPlayViewModel = boardPlayViewModel;
        start();
        setFooter();
    }

    void start() {
        configMainComponents(primaryStage);
        Scene scene = new Scene(this, SCENE_MIN_WIDTH, SCENE_MIN_HEIGHT);
        // String cssFile = Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm();
        //scene.getStylesheets().add(cssFile);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());

    }


    void configMainComponents(Stage stage) {

        createMap();
    }


    void createHeader() {

    }


    void createMap() {
        MAP_WIDTH = boardPlayViewModel.getMapWidth();
        MAP_HEIGHT = boardPlayViewModel.getMapHeight();

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
        mapView = new MapPlayView(boardPlayViewModel.getMapViewModel(), mapWidth, mapHeight);

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

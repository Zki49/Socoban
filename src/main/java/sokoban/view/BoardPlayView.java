package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import sokoban.viewmodel.BoardPlayViewModel;

public class BoardPlayView extends BorderPane {

    private  int MAP_WIDTH;
    private  int MAP_HEIGHT;
    private  double SCENE_MIN_WIDTH;
    private  double SCENE_MIN_HEIGHT;
    private MapPlayView mapView;
    private BooleanProperty isFinish = new SimpleBooleanProperty(false);

    private final SimpleStringProperty title = new SimpleStringProperty("");
    private HeaderPlay headerPlayBox;

    private final Stage primaryStage;
    private HBox footer;
    private  Button mushroom;
    private final BoardPlayViewModel boardPlayViewModel;

    private Scene scene;


    public BoardPlayView(Stage primaryStage, BoardPlayViewModel boardPlayViewModel , double widthScreen, double heightScreen){
//        super(primaryStage, boardPlayViewModel);
        this.primaryStage = primaryStage;
        this.boardPlayViewModel = boardPlayViewModel;
        this.SCENE_MIN_WIDTH = widthScreen;
        this.SCENE_MIN_HEIGHT = heightScreen;
        start();
        connectMovePlayer();



    }

    void start() {
        configMainComponents(primaryStage);

        scene = new Scene(this, SCENE_MIN_WIDTH, SCENE_MIN_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        mapView.requestFocus();
    }


    void configMainComponents(Stage stage) {
        createHeaderPlay();
        setFooter();
        createMap();
    }

    private void createHeaderPlay() {
        headerPlayBox = new HeaderPlay(boardPlayViewModel);
        headerPlayBox.setAlignment(Pos.CENTER);
        setTop(headerPlayBox);
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
                    var size = Math.min(widthProperty().get(), heightProperty().get() - headerPlayBox.heightProperty().get() - footer.heightProperty().get());
                    return Math.floor(size / MAP_HEIGHT) * MAP_HEIGHT;
                },
                widthProperty(),

                heightProperty(),  headerPlayBox.heightProperty());
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
        footer = new HBox();

        Button finish = new Button("finish");
         mushroom = new Button("mushroom");
        mushroom.textProperty().bind(boardPlayViewModel.getTitleButtonMushroom());
        footer.getChildren().addAll(finish, new Region(), mushroom);
        mushroom.setFocusTraversable(false);
        footer.setAlignment(Pos.TOP_CENTER);

        finish.disableProperty().bind(boardPlayViewModel.isNotWon());
        setBottom(footer);
        finish.setOnAction(event -> {

           isFinish.set(true);

        });
        mushroom.setOnAction(event -> {
            if(!boardPlayViewModel.isWon().getValue())
                 boardPlayViewModel.showMushroom();
            mapView.requestFocus();
        });



    }
    public double getSizeScreenWidth(){
        return scene.getWidth();
    }
    public double getSizeScreenHeight(){
        return scene.getHeight();
    }


    private void connectMovePlayer() {
        primaryStage.getScene().setOnKeyPressed(event -> {
            if(!boardPlayViewModel.isWon().getValue()){
                if (event.isControlDown() && event.getCode() == KeyCode.Z) {
                    boardPlayViewModel.moveBack();
                }
                else if (event.isControlDown() && event.getCode() == KeyCode.Y){
                    boardPlayViewModel.movefront();

                }

                else if (event.getCode() == KeyCode.UP  || event.getCode() == KeyCode.Z) {
                    boardPlayViewModel.moveUp();
                } else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                    boardPlayViewModel.moveDown();
                } else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                    boardPlayViewModel.moveRight();
                } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.Q) {
                    boardPlayViewModel.moveLeft();
                }

            }

            mapView.requestFocus();

        });
    }
}

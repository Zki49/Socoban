package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sokoban.viewmodel.BoardViewModel;

public class BoardView extends BorderPane {

    private  int MAP_WIDTH;
    private  int MAP_HEIGHT;
    private static final int SCENE_MIN_WIDTH = 600;
    private  MapView mapView;
    private Header headerBox ;
    private Menu menuBox ;
    private FileView fileView;

    private final SimpleBooleanProperty mapReloaded;
    private static final int SCENE_MIN_HEIGHT = 420;
    private final Label headerLabel = new Label("");
    private final BoardViewModel boardViewModel;
    private final SimpleStringProperty title = new SimpleStringProperty("");
    private final VBox topHeader = new VBox();
    private final Stage primaryStage;
    public BoardView(Stage primaryStage, BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        this.primaryStage = primaryStage;
        headerBox = new Header(boardViewModel);
       // menuBox =  new Menu(boardViewModel,heightProperty().get() - headerBox.heightProperty().get());
        fileView = new FileView(boardViewModel);
        mapReloaded = boardViewModel.reloadMapProperties();
        mapReloaded.addListener((obs, oldValue, newValue) -> reloadBoard());
        setBidings();
        start(primaryStage);
    }

    private void reloadBoard() {
        getChildren().clear();

        createHeader();
        createMenu();
        createGrid();
        setTopHeader();
        setBidings();
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
        stage.setTitle(title.getValue());


        createHeader();
        createMenu();
        createGrid();
        setTopHeader();

    }

    private void setTopHeader() {
        topHeader.getChildren().clear();
        topHeader.getChildren().add(fileView);
        topHeader.getChildren().add(headerBox);
        setTop(topHeader);



    }

    private void createHeader() {
        headerBox = new Header(boardViewModel);
        headerBox.setAlignment(Pos.CENTER);

    }
    private void createMenu(){

        DoubleBinding menuHeight = Bindings.createDoubleBinding(
                () -> {
                    var size = heightProperty().get() - headerBox.heightProperty().get();
                    return size;
                },
                widthProperty(),

                heightProperty(), headerBox.heightProperty());
        /*DoubleBinding menuWidth = Bindings.createDoubleBinding(
                () -> {
                    var size = widthProperty().get() - mapView.getWidth();;
                    return size;
                },
                widthProperty(),
                heightProperty(),
                headerBox.heightProperty());*/

        menuBox =  new Menu(boardViewModel, menuHeight);
        menuBox.setAlignment(Pos.CENTER);
        setLeft(menuBox);
    }

    private void createGrid() {

        /*
         * */
        MAP_WIDTH = boardViewModel.getMapWidth();
        MAP_HEIGHT = boardViewModel.getMapHeight();

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
        mapView = new MapView(boardViewModel.getMapViewModel(), mapWidth, mapHeight);

        // Grille carrée
        mapView.minHeightProperty().bind(mapHeight);
        mapView.minWidthProperty().bind(mapWidth);
        mapView.maxHeightProperty().bind(mapHeight);
        mapView.maxWidthProperty().bind(mapWidth);

        /*
         * */
        setCenter(mapView);
    }
    public void setBidings(){
        title.bind(boardViewModel.getTitle());
        title.addListener(val -> { primaryStage.setTitle(title.getValue());});
    }

}

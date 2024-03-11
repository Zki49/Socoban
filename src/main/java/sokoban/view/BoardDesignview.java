package sokoban.view;

import javafx.beans.binding.Bindings;
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

public class BoardDesignview extends BoardView {

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


    private final VBox topHeader = new VBox();
    private final BoardViewModel boardViewModel;

    public BoardDesignview(Stage primaryStage, BoardViewModel boardViewModel) {
        super(primaryStage, boardViewModel);
        this.boardViewModel = getBoardViewModel();
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
        createMap();
        setTopHeader();
        setBidings();
    }


     void configMainComponents(Stage stage) {
        stage.setTitle(getTitle());


        createHeader();
        createMenu();
        createMap();
        setTopHeader();

    }

    private void setTopHeader() {
        topHeader.getChildren().clear();
        topHeader.getChildren().add(fileView);
        topHeader.getChildren().add(headerBox);
        setTop(topHeader);



    }

     void createHeader() {

        headerBox = new Header(getBoardViewModel());
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

        menuBox =  new Menu(getBoardViewModel(), menuHeight);
        menuBox.setAlignment(Pos.CENTER);
        setLeft(menuBox);
    }

     void createMap() {

        /*
         * */
        MAP_WIDTH = getBoardViewModel().getMapWidth();
        MAP_HEIGHT = getBoardViewModel().getMapHeight();

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
                    var size = Math.min(widthProperty().get(), heightProperty().get() - headerBox.heightProperty().get() - fileView.heightProperty().get());
                    return Math.floor(size / MAP_HEIGHT) * MAP_HEIGHT;
                },
                widthProperty(),

                heightProperty(), headerBox.heightProperty());
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
    public void setBidings(){
        titleProperty().bind(getBoardViewModel().getTitle());
        titleProperty().addListener(val -> { getPrimaryStage().setTitle(titleProperty().getValue());});
    }

}

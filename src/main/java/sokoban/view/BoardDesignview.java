package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sokoban.viewmodel.BoardDesignViewModel;

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
    private final BooleanProperty isReadyToPlay = new SimpleBooleanProperty(false);
    private final Label headerLabel = new Label("");


    private final VBox topHeader = new VBox();
    private final BoardDesignViewModel boardDesignViewModel;

    public BoardDesignview(Stage primaryStage, BoardDesignViewModel boardDesignViewModel) {
        super(primaryStage, boardDesignViewModel);
        this.boardDesignViewModel = getBoardViewModel();
        headerBox = new Header(boardDesignViewModel);
       // menuBox =  new Menu(boardViewModel,heightProperty().get() - headerBox.heightProperty().get());
        fileView = new FileView(boardDesignViewModel);
        mapReloaded = boardDesignViewModel.reloadMapProperties();
        mapReloaded.addListener((obs, oldValue, newValue) -> reloadBoard());
        setBidings();


        start();



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



        createHeader();
        createMenu();
        createMap();
        setTopHeader();
        setFooter();

    }

    private void setTopHeader() {
        topHeader.getChildren().clear();
        topHeader.getChildren().add(fileView);
        topHeader.getChildren().add(headerBox);
        setTop(topHeader);



    }

    public boolean getIsReadyToPlay() {
        return isReadyToPlay.get();
    }

    public BooleanProperty isReadyToPlayProperty() {
        return isReadyToPlay;
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
        fileView.exitSystemProperty().addListener(exit -> getPrimaryStage().close());
    }

    @Override
    void setFooter() {
        HBox footer = new HBox();
        Button finish = new Button("Play");
        footer.getChildren().add(finish);
        setBottom(footer);
        finish.setOnAction(event -> {
            System.out.println("from design " + isReadyToPlay.get());
            isReadyToPlay.set(true);
        });
    }


}

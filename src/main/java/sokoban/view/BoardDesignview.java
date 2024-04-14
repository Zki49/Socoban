package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sokoban.viewmodel.BoardDesignViewModel;


public class BoardDesignview extends BorderPane {

    private  int MAP_WIDTH;
    private  int MAP_HEIGHT;
    private final double sceneMinWidth;
    private MapDesignView mapView;
    private Header headerBox ;
    private Menu menuBox ;
    private FileView fileView;
    private HBox footer;
// 600 420
    private final SimpleBooleanProperty mapReloaded;
    private final double sceneMinHeight;
    private final BooleanProperty isReadyToPlay = new SimpleBooleanProperty(false);
    private final Label headerLabel = new Label("");
    private Scene scene;


    private final VBox topHeader = new VBox();
    private final BoardDesignViewModel boardDesignViewModel;
    private final SimpleStringProperty title = new SimpleStringProperty("");

    private final Stage primaryStage;

    public BoardDesignview(Stage primaryStage, BoardDesignViewModel boardDesignViewModel,double heightScreen, double withScreen) {
//        super(primaryStage, boardDesignViewModel);
        this.boardDesignViewModel = boardDesignViewModel;
        this.primaryStage = primaryStage;
        headerBox = new Header(boardDesignViewModel);
       // menuBox =  new Menu(boardViewModel,heightProperty().get() - headerBox.heightProperty().get());
        fileView = new FileView(boardDesignViewModel);
        mapReloaded = boardDesignViewModel.reloadMapProperties();
        mapReloaded.addListener((obs, oldValue, newValue) -> reloadBoard());
        sceneMinHeight = heightScreen;
        sceneMinWidth = withScreen;
        setBidings();


        start();



    }

    void start() {
        configMainComponents(primaryStage);
         scene = new Scene(this, sceneMinWidth, sceneMinHeight);
        // String cssFile = Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm();
        //scene.getStylesheets().add(cssFile);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setTitle(boardDesignViewModel.getTitle().getValue());
        menuBox.setBorderObjectSelected();

    }


    private void reloadBoard() {
        getChildren().clear();

        createHeader();
        createMenu();
        setFooter();
        createMap();
        setTopHeader();
        setBidings();

        setFooter();


    }


     void configMainComponents(Stage stage) {
        createHeader();
        createMenu();
         setFooter();
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

        headerBox = new Header(boardDesignViewModel);
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


        menuBox =  new Menu(boardDesignViewModel, menuHeight);
        menuBox.setAlignment(Pos.CENTER);
        setLeft(menuBox);
    }

     void createMap() {

        /*
         * */
        MAP_WIDTH = boardDesignViewModel.getMapWidth();
        MAP_HEIGHT = boardDesignViewModel.getMapHeight();

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
                    var size = Math.min(widthProperty().get(), heightProperty().get() - topHeader.heightProperty().get()  - footer.heightProperty().get());
                    System.out.println(topHeader.heightProperty());
                    return Math.floor(size / MAP_HEIGHT) * MAP_HEIGHT;

                },
                widthProperty(),

                heightProperty(), topHeader.heightProperty());
        mapView = new MapDesignView(boardDesignViewModel.getMapViewModel(), mapWidth, mapHeight);


        mapView.minHeightProperty().bind(mapHeight);
        mapView.minWidthProperty().bind(mapWidth);
        mapView.maxHeightProperty().bind(mapHeight);
        mapView.maxWidthProperty().bind(mapWidth);


        setCenter(mapView);
    }
    public void setBidings(){
        title.bind(boardDesignViewModel.getTitle());
        title.addListener(val -> { primaryStage.setTitle(title.getValue());});
        fileView.exitSystemProperty().addListener(exit -> primaryStage.close());
    }
    public double getSizeScreenWidth(){
        return scene.getWidth();
    }
    public double getSizeScreenHeight(){
        return scene.getHeight();
    }

    void setFooter() {
        footer = new HBox();
        Button finish = new Button("Play");
        footer.getChildren().add(finish);
        footer.setAlignment(Pos.TOP_CENTER);

        setBottom(footer);
        finish.setOnAction(event -> {

            if (boardDesignViewModel.hasBeenChanged()) {
                fileView.hasBeenChanged();
                if(!fileView.getIsCancelled())
                    isReadyToPlay.set(true);
            }else {
                isReadyToPlay.set(true);
            }
        });
        finish.disableProperty().bind(boardDesignViewModel.containsError());

    }





}

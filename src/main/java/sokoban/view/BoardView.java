package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sokoban.viewmodel.BoardViewModel;

public abstract class BoardView extends BorderPane{
    private  int MAP_WIDTH;
    private  int MAP_HEIGHT;
    private static final int SCENE_MIN_WIDTH = 600;
    private  MapView mapView;
    private Header headerBox ;




    private static final int SCENE_MIN_HEIGHT = 420;

    private final BoardViewModel boardViewModel;
    private final SimpleStringProperty title = new SimpleStringProperty("");

    private final Stage primaryStage;

    public BoardView(Stage primaryStage, BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        this.primaryStage = primaryStage;


        // menuBox =  new Menu(boardViewModel,heightProperty().get() - headerBox.heightProperty().get());


    }



    public int getMAP_WIDTH() {
        return MAP_WIDTH;
    }

    public int getMAP_HEIGHT() {
        return MAP_HEIGHT;
    }

    public MapView getMapView() {
        return mapView;
    }

    public Header getHeaderBox() {
        return headerBox;
    }

    public BoardViewModel getBoardViewModel() {
        return boardViewModel;
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
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
    abstract void configMainComponents(Stage stage);



     abstract void createHeader();

    abstract void createMap() ;
    public void setBidings(){
        title.bind(boardViewModel.getTitle());
        title.addListener(val -> { primaryStage.setTitle(title.getValue());});
    }
    abstract void setFooter();

}



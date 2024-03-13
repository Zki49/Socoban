package sokoban.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sokoban.viewmodel.BoardDesignViewModel;

public abstract class BoardView extends BorderPane{
    private  int MAP_WIDTH;
    private  int MAP_HEIGHT;
    private static final int SCENE_MIN_WIDTH = 600;
    private  MapView mapView;
    private Header headerBox ;




    private static final int SCENE_MIN_HEIGHT = 420;

    private final BoardDesignViewModel boardDesignViewModel;
    private final SimpleStringProperty title = new SimpleStringProperty("");

    private final Stage primaryStage;

    public BoardView(Stage primaryStage, BoardDesignViewModel boardDesignViewModel) {
        this.boardDesignViewModel = boardDesignViewModel;
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

    public BoardDesignViewModel getBoardViewModel() {
        return boardDesignViewModel;
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
        title.bind(boardDesignViewModel.getTitle());
        title.addListener(val -> { primaryStage.setTitle(title.getValue());});
    }
    abstract void setFooter();

}



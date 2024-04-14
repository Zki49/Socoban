package sokoban.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;
import sokoban.model.BoardDesign;
import sokoban.viewmodel.BoardDesignViewModel;
import sokoban.viewmodel.BoardPlayViewModel;

public class BoardGeneral {

    private static final int SCENE_MIN_WIDTH = 600;
    private static final int SCENE_MIN_HEIGHT = 420;
    private final SimpleStringProperty title = new SimpleStringProperty("Sokoban");
    private final Stage primaryStage ;
    private  BoardDesignview designBoard;
    private BoardPlayView playBoard;

    private BoardDesignViewModel boardDesignViewModel;
    private BoardPlayViewModel boardPlayViewModel;

    public BoardGeneral(Stage primaryStage) {
        this.primaryStage = primaryStage;
       start(primaryStage);
       createBindingsDesign();


    }
    private void start(Stage primaryStage) {
        configMainComponents(primaryStage);
        //Scene scene = new Scene(this, SCENE_MIN_WIDTH, SCENE_MIN_HEIGHT);
        // String cssFile = Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm();
        //scene.getStylesheets().add(cssFile);
        /*primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());*/

    }

    private void configMainComponents(Stage stage) {
        stage.setTitle(title.getValue());


       creatBoardDesign();

    }

    private void creatBoardDesign() {
        if(playBoard == null){
            BoardDesign boardDesign = new BoardDesign();
             boardDesignViewModel = new BoardDesignViewModel(boardDesign);
            designBoard = new BoardDesignview(primaryStage, boardDesignViewModel , 420 , 600);
        }else {
            designBoard = new BoardDesignview(primaryStage, boardDesignViewModel,playBoard.getSizeScreenHeight(),playBoard.getSizeScreenWidth());
        }
        createBindingsDesign();
    }
    private void createBindingsDesign() {
        designBoard.isReadyToPlayProperty().addListener(val -> {
            if(designBoard.isReadyToPlayProperty().getValue())

                createBoardPlay();
        });

    }

    private void createBoardPlay() {

        BoardPlayViewModel vm = new BoardPlayViewModel(boardDesignViewModel);

        playBoard = new BoardPlayView(primaryStage,vm, designBoard.getSizeScreenWidth(), designBoard.getSizeScreenHeight());
        primaryStage.setTitle("Sokoban");
        createBindingsPlay();
    }

    private void createBindingsPlay() {
        playBoard.isFinishProperty().addListener(val -> {
            if(playBoard.isFinishProperty().getValue())
                creatBoardDesign();
        });
    }

}

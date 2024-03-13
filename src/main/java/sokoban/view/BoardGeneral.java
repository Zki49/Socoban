package sokoban.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;
import sokoban.model.Board;
import sokoban.viewmodel.BoardViewModel;

public class BoardGeneral {

    private static final int SCENE_MIN_WIDTH = 600;
    private static final int SCENE_MIN_HEIGHT = 420;
    private final SimpleStringProperty title = new SimpleStringProperty("Sokoban");
    private final Stage primaryStage ;
    private  BoardDesignview designBoard;
    private BoardPlayView playBoard;

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
        BoardViewModel vm;
        if(playBoard == null){
            Board board = new Board();
             vm = new BoardViewModel(board);
        }
        else{
            vm = playBoard.getBoardViewModel();
        }


        designBoard = new BoardDesignview(primaryStage, vm);
        createBindingsDesign();

    }
    private void createBindingsDesign() {
        designBoard.isReadyToPlayProperty().addListener(val -> {
            if(designBoard.isReadyToPlayProperty().getValue())

                createBoardPlay();
        });

    }

    private void createBoardPlay() {

        BoardViewModel vm = designBoard.getBoardViewModel();
        playBoard = new BoardPlayView(primaryStage,vm);
        createBindingsPlay();
    }

    private void createBindingsPlay() {
        playBoard.isFinishProperty().addListener(val -> {
            if(playBoard.isFinishProperty().getValue())
                creatBoardDesign();
        });
    }

}

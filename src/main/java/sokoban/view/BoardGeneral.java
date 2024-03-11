package sokoban.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sokoban.model.Board;
import sokoban.viewmodel.BoardViewModel;

public class BoardGeneral extends BorderPane {

    private static final int SCENE_MIN_WIDTH = 600;
    private static final int SCENE_MIN_HEIGHT = 420;
    private final SimpleStringProperty title = new SimpleStringProperty("Sokoban");

    public BoardGeneral(Stage primaryStage) {


       start(primaryStage);
       setFooter();

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


       creatBoardDesign();

    }

    private void creatBoardDesign() {
        Board board = new Board();
        BoardViewModel vm = new BoardViewModel(board);

        BoardDesignview designBoard = new BoardDesignview(new Stage(), vm);

        setCenter(designBoard);
    }
    private void setFooter(){
        HBox footer = new HBox();
        Button finish = new Button("Finish");
        footer.getChildren().add(finish);
        setBottom(footer);
        finish.setOnAction(event -> {
            System.out.println("ththht");
        });

    }
}

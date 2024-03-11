package sokoban;

import javafx.application.Application;
import javafx.stage.Stage;
import sokoban.view.BoardGeneral;

public class SokobanApp extends Application  {



    @Override
    public void start(Stage primaryStage) {
        // TODO: basez vous sur l'exercice de la grille comme point de départ pour votre projet
        /*Board board = new Board();
        BoardViewModel vm = new BoardViewModel(board);

        new BoardView(primaryStage, vm);*/
        new BoardGeneral(primaryStage);

    }

    public static void main(String[] args) {
        launch();
    }

}

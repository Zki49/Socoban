package sokoban;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ObservableArray;
import javafx.stage.Stage;
import sokoban.model.Board;
import sokoban.view.BoardView;
import sokoban.viewmodel.BoardViewModel;

public class SokobanApp extends Application  {



    @Override
    public void start(Stage primaryStage) {
        // TODO: basez vous sur l'exercice de la grille comme point de départ pour votre projet
        Board board = new Board();
        BoardViewModel vm = new BoardViewModel(board);

        new BoardView(primaryStage, vm);
    }

    public static void main(String[] args) {
        launch();
    }

}

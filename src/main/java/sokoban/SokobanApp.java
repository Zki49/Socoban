package sokoban;

import javafx.application.Application;
import javafx.stage.Stage;
import sokoban.view.BoardGeneral;

public class SokobanApp extends Application  {



    @Override
    public void start(Stage primaryStage) {
        // TODO: basez vous sur l'exercice de la grille comme point de départ pour votre projet

        new BoardGeneral(primaryStage);

    }

    public static void main(String[] args) {
        launch();
    }

}

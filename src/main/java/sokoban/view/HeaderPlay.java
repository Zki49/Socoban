package sokoban.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import sokoban.viewmodel.BoardPlayViewModel;

public class HeaderPlay extends VBox {
    Label score = new Label("Score");
    Label numberOfMovesPlayed = new Label("Number of moves played : ");
    Label numberOfGoalsReached = new Label("Number of goals reached : ");
    BoardPlayViewModel boardPlayViewModel;

    public HeaderPlay (BoardPlayViewModel boardPlayViewModel) {
        this.boardPlayViewModel = boardPlayViewModel;

        getChildren().addAll(score, numberOfMovesPlayed, numberOfGoalsReached);

        configureBindings();
        configureStyle();
    }


    public void configureBindings() {
        //numberOfMovesPlayed.textProperty().bind();
    }

    public void configureStyle() {
        score.setFont(new Font("Thoma", 20));
    }
}

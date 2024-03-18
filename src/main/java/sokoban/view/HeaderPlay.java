package sokoban.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import sokoban.viewmodel.BoardPlayViewModel;

public class HeaderPlay extends VBox {
    Label score = new Label("Score");
    Label numberOfMovesPlayed = new Label("Number of moves played : ");
    Label numberOfGoalsReached = new Label();
    Label numberOfGoals = new Label();
    BoardPlayViewModel boardPlayViewModel;

    public HeaderPlay (BoardPlayViewModel boardPlayViewModel) {
        this.boardPlayViewModel = boardPlayViewModel;

        getChildren().addAll(score, numberOfMovesPlayed, numberOfGoalsReached, numberOfGoals);

        configureBindings();
        configureStyle();
    }


    public void configureBindings() {
        //numberOfMovesPlayed.textProperty().bind();
        numberOfGoalsReached.textProperty().bind(boardPlayViewModel.numberBoxesOnGoal().asString());
        numberOfGoals.textProperty().bind(boardPlayViewModel.numberofGoals().asString());

        numberOfGoalsReached.textProperty().bind(boardPlayViewModel.numberBoxesOnGoal().asString()
                .concat("Number of goals reached : ")
                .concat(numberOfGoalsReached.textProperty())
                .concat(" of ")
                .concat(numberOfGoals.textProperty()));
    }

    public void configureStyle() {
        score.setFont(new Font("Thoma", 20));
    }
}

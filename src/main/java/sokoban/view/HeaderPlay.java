package sokoban.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import sokoban.viewmodel.BoardPlayViewModel;

public class HeaderPlay extends VBox {
    Label title = new Label("Score");
    Label numberOfMovesPlayed = new Label();
    Label numberOfGoalsReached = new Label();
    Label numberOfGoals = new Label();
    Label scoreResult = new Label();
    BoardPlayViewModel boardPlayViewModel;

    public HeaderPlay (BoardPlayViewModel boardPlayViewModel) {
        this.boardPlayViewModel = boardPlayViewModel;

        getChildren().addAll(title, numberOfMovesPlayed, numberOfGoalsReached, numberOfGoals, scoreResult);

        configureBindings();
        configureStyle();
    }


    public void configureBindings() {
        numberOfGoalsReached.textProperty().bind(boardPlayViewModel.numberBoxesOnGoal().asString());
        numberOfGoals.textProperty().bind(boardPlayViewModel.numberofGoals().asString());

        numberOfGoalsReached.textProperty().bind(boardPlayViewModel.numberBoxesOnGoal().asString()
                .concat("Number of goals reached : ")
                .concat(numberOfGoalsReached.textProperty())
                .concat(" of ")
                .concat(numberOfGoals.textProperty()));

        numberOfMovesPlayed.textProperty().bind(boardPlayViewModel.scoreProperty().asString()
                .concat("Number of moves played : ").concat(numberOfMovesPlayed.textProperty()));

        scoreResult.textProperty().bind(boardPlayViewModel.scoreProperty().asString()
                .concat("You won in")
                .concat(scoreResult.textProperty())
                .concat("moves, congratulations !!"));

    }

    public void configureStyle() {
        scoreResult.setFont(new Font("Thoma", 20));
        title.setFont(new Font("Thoma", 20));
    }
}

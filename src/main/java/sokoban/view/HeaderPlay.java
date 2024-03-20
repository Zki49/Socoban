package sokoban.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import sokoban.viewmodel.BoardPlayViewModel;

public class HeaderPlay extends VBox {
    private Label title = new Label("Score");
    private Label numberOfMovesPlayed = new Label();
    private Label numberOfGoalsReached = new Label();
    private Label numberOfGoals = new Label();
    private Label numbreOfGoalsReachedPlayed = new Label();
    private Label scoreResult = new Label();
    private Label move = new Label();

    private BoardPlayViewModel boardPlayViewModel;

    public HeaderPlay (BoardPlayViewModel boardPlayViewModel) {
        this.boardPlayViewModel = boardPlayViewModel;

        getChildren().addAll(title,numberOfMovesPlayed, numbreOfGoalsReachedPlayed,scoreResult);

        configureBindings();
        configureStyle();
    }


    public void configureBindings() {
       numberOfGoalsReached.textProperty().bind(boardPlayViewModel.numberBoxesOnGoal().asString());
        numberOfGoals.textProperty().bind(boardPlayViewModel.numberofGoals().asString());

        numbreOfGoalsReachedPlayed.textProperty().bind(boardPlayViewModel.numberBoxesOnGoal().asString("Number of goals reached : ")

                .concat(numberOfGoalsReached.textProperty())
                .concat(" of ")
                .concat(numberOfGoals.textProperty()));
        move.textProperty().bind(boardPlayViewModel.scoreProperty().asString());
        numberOfMovesPlayed.textProperty().bind(
                boardPlayViewModel.scoreProperty().asString("Number of moves played : ")
                .concat(move.textProperty()));


        scoreResult.textProperty().bind(boardPlayViewModel.scoreProperty().asString("You won in ")
                .concat(move.textProperty())
                .concat(" moves, congratulations !!"));
        scoreResult.managedProperty().bind(boardPlayViewModel.isWon());

        scoreResult.visibleProperty().bind(boardPlayViewModel.isWon());


    }

    public void configureStyle() {
        scoreResult.setFont(new Font("Thoma", 20));
        title.setFont(new Font("Thoma", 20));
    }
}

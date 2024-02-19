package sokoban.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sokoban.viewmodel.BoardViewModel;

public class Header extends VBox {
    Text nbOfFilledCell = new Text(""); //Number of filled cells : 0 of 75
    Label iterCell = new Label("");
    Label maxCellAvailable = new Label();
    Label errorField = new Label("Please correct the following error(s)");
    Label playerField = new Label("At least one player is required");
    Label targetField = new Label("At least one target is required");
    Label boxField = new Label("At least one box is required");
    Label boxEqualTargetField = new Label("Number of boxes and targets must be equal");
    //Label totalCell = new Label("hello");


    BoardViewModel boardViewModel;

    public Header(BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;

        getChildren().addAll(nbOfFilledCell, errorField,
                playerField, targetField, boxField, boxEqualTargetField);

        configureBindings();
        configureStyle();
        errorMessage();
        }

    public void configureBindings() {
        maxCellAvailable.textProperty().bind(boardViewModel.getMaxCellAvailable().asString());
        iterCell.textProperty().bind(boardViewModel.cellWithObjectProperty().asString());

        // Création de la chaîne formatée et binding à la propriété textuelle de textMaxCellAvailable
        nbOfFilledCell.textProperty().bind(
                boardViewModel.cellWithObjectProperty().asString("Number of filled cells : ")
                        .concat(iterCell.textProperty())
                        .concat(" / ")
                        .concat(maxCellAvailable.textProperty())
        );
    }

    public void configureStyle(){
        nbOfFilledCell.setFont(new Font("Thoma", 30));
        errorField.setTextFill(Color.RED);
        playerField.setTextFill(Color.RED);
        targetField.setTextFill(Color.RED);
        boxField.setTextFill(Color.RED);
        boxEqualTargetField.setTextFill(Color.RED);
    }

    public void errorMessage(){
        playerField.visibleProperty().bind(boardViewModel.containsPlayer());
        playerField.managedProperty().bind(boardViewModel.containsPlayer());

        targetField.visibleProperty().bind(boardViewModel.containsGoal());
        targetField.managedProperty().bind(boardViewModel.containsGoal());

        boxField.visibleProperty().bind(boardViewModel.containsBox());
        boxField.managedProperty().bind(boardViewModel.containsBox());

        errorField.visibleProperty().bind(boardViewModel.containsError());
        errorField.managedProperty().bind(boardViewModel.containsError());

        boxEqualTargetField.visibleProperty().bind(boardViewModel.boxIsEqualToGoal());
        boxEqualTargetField.managedProperty().bind(boardViewModel.boxIsEqualToGoal());
    }


}

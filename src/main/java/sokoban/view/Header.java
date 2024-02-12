package sokoban.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sokoban.viewmodel.BoardViewModel;

public class Header extends VBox {
    Text textMaxCellAvailable = new Text(""); //Number of filled cells : 0 of 75
    Label iterCell = new Label("");
    Label maxCellAvailable = new Label();
    Label errorField = new Label("Please correct the following error(s)");
    Label playerField = new Label("At least one player is required");
    Label targetField = new Label("At least one target is required");
    Label boxField = new Label("At least one box is required");

    BoardViewModel boardViewModel;

    public Header(BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;

        getChildren().addAll(textMaxCellAvailable, errorField,
                playerField, targetField, boxField);

        configureBidings();
        configureStyle();
        errorMessage();

        }


    public void configureBidings() {
        maxCellAvailable.textProperty().bind(boardViewModel.getMaxCellAvailable().asString());
        iterCell.textProperty().bind(boardViewModel.cellWithObjectProperty().asString());

        // Création de la chaîne formatée et binding à la propriété textuelle de textMaxCellAvailable
        textMaxCellAvailable.textProperty().bind(
                boardViewModel.cellWithObjectProperty().asString("Number of filled cells : ")
                        .concat(iterCell.textProperty())
                        .concat(" / ")
                        .concat(maxCellAvailable.textProperty())
        );
    }

    public void configureStyle(){
        textMaxCellAvailable.setFont(new Font("Thoma", 30));
        errorField.setTextFill(Color.RED);
        playerField.setTextFill(Color.RED);
        targetField.setTextFill(Color.RED);
        boxField.setTextFill(Color.RED);
    }

    public void errorMessage(){
        //playerField.textProperty().bind(boardViewModel.containsPlayer());
        System.out.println(boardViewModel.containsPlayer());
    }


}

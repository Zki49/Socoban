package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sokoban.viewmodel.BoardDesignViewModel;

public class Header extends VBox {
    private final Label nbOfFilledCell = new Label(""); //Number of filled cells : 0 of 75
    private final Label iterCell = new Label("");
    private final Label maxCellAvailable = new Label();
    private final Label errorField = new Label("Please correct the following error(s)");
    private final Label playerField = new Label("At least one player is required");
    private final Label targetField = new Label("At least one target is required");
    private final Label boxField = new Label("At least one box is required");
    private final Label boxEqualTargetField = new Label("Number of boxes and targets must be equal");
    //Label totalCell = new Label("hello");


    BoardDesignViewModel boardDesignViewModel;

    public Header(BoardDesignViewModel boardDesignViewModel) {
        this.boardDesignViewModel = boardDesignViewModel;

        getChildren().addAll(nbOfFilledCell, errorField,
                playerField, targetField, boxField, boxEqualTargetField);

        configureBindings();
        configureStyle();
        errorMessage();
        }

    public void configureBindings() {
        maxCellAvailable.textProperty().bind(boardDesignViewModel.getMaxCellAvailable().asString());
        iterCell.textProperty().bind(boardDesignViewModel.cellWithObjectProperty().asString());

        // Création de la chaîne formatée et binding à la propriété textuelle de textMaxCellAvailable
        nbOfFilledCell.textProperty().bind(
                boardDesignViewModel.cellWithObjectProperty().asString("Number of filled cells : ")
                        .concat(iterCell.textProperty())
                        .concat(" / ")
                        .concat(maxCellAvailable.textProperty())
        );
    }

    public void configureStyle(){
        //nbOfFilledCell.setFont(new Font("Thoma", 30));
        errorField.setTextFill(Color.RED);
        playerField.setTextFill(Color.RED);
        targetField.setTextFill(Color.RED);
        boxField.setTextFill(Color.RED);
        boxEqualTargetField.setTextFill(Color.RED);
    }

    public void errorMessage(){


        playerField.visibleProperty().bind(boardDesignViewModel.containsPlayer());
        playerField.managedProperty().bind(boardDesignViewModel.containsPlayer());

        targetField.visibleProperty().bind(boardDesignViewModel.containsGoal());
        targetField.managedProperty().bind(boardDesignViewModel.containsGoal());

        boxField.visibleProperty().bind(boardDesignViewModel.containsBox());
        boxField.managedProperty().bind(boardDesignViewModel.containsBox());

        errorField.visibleProperty().bind(boardDesignViewModel.containsError());
        errorField.managedProperty().bind(boardDesignViewModel.containsError());

        boxEqualTargetField.visibleProperty().bind(boardDesignViewModel.boxIsEqualToGoal());
        boxEqualTargetField.managedProperty().bind(boardDesignViewModel.boxIsEqualToGoal());
    }


}

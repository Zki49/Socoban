package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sokoban.viewmodel.BoardDesignViewModel;

public class Header extends VBox {
    Label nbOfFilledCell = new Label(""); //Number of filled cells : 0 of 75
    Label iterCell = new Label("");
    Label maxCellAvailable = new Label();
    Label errorField = new Label("Please correct the following error(s)");
    Label playerField = new Label("At least one player is required");
    Label targetField = new Label("At least one target is required");
    Label boxField = new Label("At least one box is required");
    Label boxEqualTargetField = new Label("Number of boxes and targets must be equal");
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

        DoubleBinding heightHeader =
                Bindings.createDoubleBinding(() -> {
                            double height = nbOfFilledCell.getHeight();
                            if (playerField.isVisible() || playerField.isManaged()) {
                                height += playerField.getHeight();
                            }
                            if (targetField.isVisible() || targetField.isManaged()) {
                                height += targetField.getHeight();
                            }
                            if (boxField.isVisible() || boxField.isManaged()) {
                                height += boxField.getHeight();
                            }
                            if (boxEqualTargetField.isVisible() || boxEqualTargetField.isManaged()) {
                                height += boxEqualTargetField.getHeight();
                            }
                            return height;
                        }, playerField.visibleProperty(), playerField.managedProperty(),
                        targetField.visibleProperty(), targetField.managedProperty(),
                        boxField.visibleProperty(), boxField.managedProperty(),
                        boxEqualTargetField.visibleProperty(), boxEqualTargetField.managedProperty());
        //minHeightProperty().bind(heightHeader);
        //maxHeightProperty().bind(heightHeader);
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

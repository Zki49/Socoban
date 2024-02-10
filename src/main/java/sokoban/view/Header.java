package sokoban.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import sokoban.viewmodel.BoardViewModel;

public class Header extends VBox {
    Label test = new Label("test");
    Label test2 = new Label("test2");
    Label maxCellAvailable = new Label();

    BoardViewModel boardViewModel;

    public Header(BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;

        getChildren().add(test);
        getChildren().add(test2);
        getChildren().add(maxCellAvailable);

        configureBidings();
    }


    public void configureBidings() {
        maxCellAvailable.textProperty().bind(boardViewModel.getMaxCellAvailable().asString());
        test2.textProperty().bind(boardViewModel.cellWithObjectProperty().asString());
    }


}

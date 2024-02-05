package sokoban.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Header extends VBox {
    Label test = new Label("test");

    public Header() {
        getChildren().add(test);
    }


}

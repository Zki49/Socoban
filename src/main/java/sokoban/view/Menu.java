package sokoban.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Menu extends VBox {

    private Image imageObject = new Image("ground.png");
    private final ImageView imageView = new ImageView();

    public Menu() {
        layoutControls();
    }

    private void layoutControls() {
        imageView.setPreserveRatio(true);
        imageView.setImage(imageObject);
        getChildren().add(imageView);
    }
}

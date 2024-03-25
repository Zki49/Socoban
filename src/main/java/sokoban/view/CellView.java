package sokoban.view;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public abstract class CellView extends StackPane {

    static Image ground = new Image("ground.png");
    static Image box = new Image("box.png");
    static Image goal = new Image("goal.png");
    static Image player = new Image("player.png");
    static Image wall = new Image("wall.png");

    public Image findImage(String path){
        return switch (path){
            case "ground" -> ground;
            case "box" -> box;
            case "goal" -> goal;
            case "player" -> player;
            default -> wall;
        };
    }

}

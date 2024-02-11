package sokoban.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import sokoban.viewmodel.BoardViewModel;

public class Menu extends VBox {

    private Image imageObject = new Image("player.png");
    private Image imageWall = new Image("wall.png");
    private Image imageBox = new Image("box.png");
    private Image imageGoal = new Image("goal.png");
    private Image imageGround = new Image("ground.png");
    private final ImageView imageViewPlayer = new ImageView();
    private final ImageView imageViewWall = new ImageView();
    private final ImageView imageViewBox = new ImageView();
    private final ImageView imageViewGoal = new ImageView();
    private final ImageView imageViewGround = new ImageView();
    private final BoardViewModel boardViewModel;
    private final SimpleStringProperty currentObject = new SimpleStringProperty();
    private final SimpleStringProperty number = new SimpleStringProperty();

    public Menu(BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        layoutControls();
        setOnChange();
        configureBindings();
    }

    private void layoutControls() {
        imageViewPlayer.setPreserveRatio(true);
        imageViewWall.setPreserveRatio(true);
        imageViewBox.setPreserveRatio(true);
        imageViewGoal.setPreserveRatio(true);
        imageViewGround.setPreserveRatio(true);

        imageViewPlayer.setImage(imageObject);
        imageViewWall.setImage(imageWall);
        imageViewBox.setImage(imageBox);
        imageViewGoal.setImage(imageGoal);
        imageViewGround.setImage(imageGround);

        getChildren().add(imageViewPlayer);
        getChildren().add(imageViewWall);
        getChildren().add(imageViewBox);
        getChildren().add(imageViewGoal);
        getChildren().add(imageViewGround);
    }
    public void setOnChange(){
        //ceci est un test n'oublie pas de mettre le nom des object en majuscule voire enum typeobjectinmap

        imageViewPlayer.setOnMouseClicked( mouseEvent -> {
            currentObject.set("PLAYER");
        });
        imageViewWall.setOnMouseClicked( mouseEvent -> {
            currentObject.set("WALL");
        });
        imageViewBox.setOnMouseClicked( mouseEvent -> {
            currentObject.set("BOX");
        });
        imageViewGoal.setOnMouseClicked( mouseEvent -> {
            currentObject.set("GOAL");
        });
        imageViewGround.setOnMouseClicked( mouseEvent -> {
            currentObject.set("GROUND");
        });
    }


    /*a chaque fois qu'on click sur une image on chage la valeur de la propriété currentObject exemple player
    currentobject is bindbidirectionel donc dans les 2 sens donc lors d'un ajout d'un object on connait
    le type d'object voir class map methode addObject
     */

    private void configureBindings() {
        currentObject.bindBidirectional(boardViewModel.getCurrentObject());

    }
}

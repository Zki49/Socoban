package sokoban.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import sokoban.viewmodel.BoardViewModel;

public class Menu extends VBox {

    private Image imageObject = new Image("ground.png");
    private final ImageView imageView = new ImageView();
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
        imageView.setPreserveRatio(true);
        imageView.setImage(imageObject);
        getChildren().add(imageView);
    }
    public void setOnChange(){
        //ceci est un test n'oublie pas de mettre le nom des object en majuscule voire enum typeobjectinmap

        imageView.setOnMouseClicked( mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
               currentObject.set("GOAL");
            }
            else{
                currentObject.set("PLAYER");
            }


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

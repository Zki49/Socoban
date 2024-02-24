package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
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
    private final BorderPane BorderPlayer = new BorderPane();
    private final BorderPane BorderWall = new BorderPane();
    private final BorderPane BorderBox = new BorderPane();
    private final BorderPane BorderGoal = new BorderPane();
    private final BorderPane BorderGround = new BorderPane();
    private final BoardViewModel boardViewModel;
    private final SimpleStringProperty currentObject = new SimpleStringProperty();
    private final SimpleStringProperty number = new SimpleStringProperty();
    private DoubleBinding heigthProperty;
    private DoubleBinding widthProperty;
    private DoubleBinding imageProperty;

    public Menu(BoardViewModel boardViewModel, DoubleBinding heigthProperty/*, DoubleBinding widthProperty*/) {
        this.boardViewModel = boardViewModel;
        this.heigthProperty = heigthProperty;
        this.widthProperty = widthProperty;
        layoutControls();
        setOnChange();
        configureBindings();
        setImageSize();
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

        BorderPlayer.setCenter(imageViewPlayer);
        BorderWall.setCenter(imageViewWall);
        BorderBox.setCenter(imageViewBox);
        BorderGoal.setCenter(imageViewGoal);
        BorderGround.setCenter(imageViewGround);

        getChildren().add(BorderPlayer);
        getChildren().add(BorderWall);
        getChildren().add(BorderBox);
        getChildren().add(BorderGoal);
        getChildren().add(BorderGround);
        prefHeightProperty().bind(heigthProperty);

        
       setSpacing(20);
      setPadding(new Insets(10));
    }
    public void setOnChange(){
        //ceci est un test n'oublie pas de mettre le nom des object en majuscule voire enum typeobjectinmap

        imageViewPlayer.setOnMouseClicked( mouseEvent -> {
            currentObject.set("PLAYER");

            BorderWall.setStyle("-fx-border-width : 0;");
            BorderBox.setStyle("-fx-border-width : 0;");
            BorderGoal.setStyle("-fx-border-width : 0;");
            BorderGround.setStyle("-fx-border-width : 0;");

            BorderPlayer.setStyle("-fx-border-color : blue; -fx-border-width : 5px");
        });


        //imageViewPlayer.fitWidthProperty().bind(heigthProperty);

        imageViewWall.setOnMouseClicked( mouseEvent -> {
            currentObject.set("WALL");

            BorderPlayer.setStyle("-fx-border-width : 0;");
            BorderBox.setStyle("-fx-border-width : 0;");
            BorderGoal.setStyle("-fx-border-width : 0;");
            BorderGround.setStyle("-fx-border-width : 0;");

            BorderWall.setStyle("-fx-border-color : blue; -fx-border-width : 5px");
        });
        imageViewBox.setOnMouseClicked( mouseEvent -> {
            currentObject.set("BOX");

            BorderWall.setStyle("-fx-border-width : 0;");
            BorderPlayer.setStyle("-fx-border-width : 0;");
            BorderGoal.setStyle("-fx-border-width : 0;");
            BorderGround.setStyle("-fx-border-width : 0;");

            BorderBox.setStyle("-fx-border-color : blue; -fx-border-width : 5px");
        });
        imageViewGoal.setOnMouseClicked( mouseEvent -> {
            currentObject.set("GOAL");

            BorderWall.setStyle("-fx-border-width : 0;");
            BorderBox.setStyle("-fx-border-width : 0;");
            BorderPlayer.setStyle("-fx-border-width : 0;");
            BorderGround.setStyle("-fx-border-width : 0;");

            BorderGoal.setStyle("-fx-border-color : blue; -fx-border-width : 5px");
        });
        imageViewGround.setOnMouseClicked( mouseEvent -> {
            currentObject.set("GROUND");

            BorderWall.setStyle("-fx-border-width : 0;");
            BorderBox.setStyle("-fx-border-width : 0;");
            BorderGoal.setStyle("-fx-border-width : 0;");
            BorderPlayer.setStyle("-fx-border-width : 0;");

            BorderGround.setStyle("-fx-border-color : blue; -fx-border-width : 5px");
        });
    }


    /*a chaque fois qu'on click sur une image on change la valeur de la propriété currentObject exemple player
    currentobject is bindbidirectionel donc dans les 2 sens donc lors d'un ajout d'un object on connait
    le type d'object voir class map methode addObject
     */

    private void configureBindings() {
        currentObject.bindBidirectional(boardViewModel.getCurrentObject());


    }
    private void setImageSize(){
        imageProperty =  Bindings.createDoubleBinding(
                () -> {
                    var size = heightProperty().get() / 10;
                    return size;
                },
                widthProperty(),

                heightProperty());


        imageViewBox.fitWidthProperty().bind(imageProperty);
        imageViewBox.setPreserveRatio(true);
        imageViewPlayer.fitWidthProperty().bind(imageProperty);
        imageViewPlayer.setPreserveRatio(true);
        imageViewWall.fitWidthProperty().bind(imageProperty);
        imageViewWall.setPreserveRatio(true);
        imageViewGoal.fitWidthProperty().bind(imageProperty);
        imageViewGoal.setPreserveRatio(true);
        imageViewGround.fitWidthProperty().bind(imageProperty);
        imageViewGround.setPreserveRatio(true);
    }

}

package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import sokoban.viewmodel.CellViewModel;

public class CellView extends StackPane {

    private Image imageObject = new Image("ground.png");
    private final CellViewModel viewModel;
    private final DoubleBinding widthProperty;


    private final ImageView imageView = new ImageView();

    public CellView(CellViewModel viewModel, DoubleBinding widthProperty) {
        this.viewModel = viewModel;
        this.widthProperty = widthProperty;
        setAlignment(Pos.CENTER);
        layoutControls();
        actionOnCell();
    }
    private void layoutControls() {

        imageView.setPreserveRatio(true);
        imageView.setImage(imageObject);
       getChildren().add(imageView);

    }

    private void actionOnCell() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.5); // Adjust brightness (values between -1 and 1)

        // Set the initial effect
        imageView.setEffect(colorAdjust);



        this.setOnMouseEntered(mouseEvent ->  imageView.setEffect(new ColorAdjust(colorAdjust.getHue(), -1, colorAdjust.getHue(), colorAdjust.getSaturation())));
        setOnMouseExited(mouseEvent ->  imageView.setEffect(colorAdjust));
        setOnMouseClicked(mouseEvent -> {
            viewModel.addObject();

            reloadImage();});

    }

    public void reloadImage(){
        getChildren().clear();
        getChildren().add(imageView);
        for(String path : viewModel.getObjectsPath()){
            Image imageObject = new Image(path);
            ImageView imageView = new ImageView();
            imageView.setPreserveRatio(true);
            imageView.setImage(imageObject);
            getChildren().add(imageView);
        }
    }




}

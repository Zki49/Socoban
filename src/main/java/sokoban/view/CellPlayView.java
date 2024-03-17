package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import sokoban.model.ObjectInMap;
import sokoban.viewmodel.CellDesignViewModel;
import sokoban.viewmodel.CellPlayViewModel;

public class CellPlayView extends CellView{

    private Image imageObject = new Image("ground.png");
    private final CellPlayViewModel viewModel;
    private final DoubleBinding widthProperty;

    private final ObservableList<ObjectInMap> objectList;
    private final ImageView imageView = new ImageView();

    public CellPlayView(CellPlayViewModel viewModel, DoubleBinding widthProperty) {
        this.viewModel = viewModel;
        this.widthProperty = widthProperty;
        objectList = viewModel.getObjectList();

        //must be fix
        (objectList).addListener((ListChangeListener<ObjectInMap>) change -> {
            reloadImage();

        });
        setAlignment(Pos.CENTER);
        layoutControls();
        actionOnCell();
        configureBindings();
        reloadImage();
    }

    private void configureBindings() {
        minWidthProperty().bind(widthProperty);
        minHeightProperty().bind(widthProperty);
        maxHeightProperty().bind(widthProperty);
        maxWidthProperty().bind(widthProperty);
        imageView.fitWidthProperty().bind(widthProperty);
        imageView.fitHeightProperty().bind(widthProperty);

    }

    private void layoutControls() {


        imageView.setImage(imageObject);
        imageView.fitWidthProperty().bind(widthProperty);
        imageView.setPreserveRatio(true);
        getChildren().add(imageView);

    }

    private void actionOnCell() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.5); // Adjust brightness (values between -1 and 1)

        // Set the initial effect
        imageView.setEffect(colorAdjust);



        this.setOnMouseEntered(mouseEvent ->  imageView.setEffect(new ColorAdjust(colorAdjust.getHue(), -1, colorAdjust.getHue(), colorAdjust.getSaturation())));
        setOnMouseExited(mouseEvent ->  imageView.setEffect(colorAdjust));

    }
    public void reloadImage(){
        getChildren().clear();
        getChildren().add(imageView);
        for(String path : viewModel.getObjectsPath()){
            Image imageObject = new Image(path);
            ImageView imageView = new ImageView();
            imageView.fitWidthProperty().bind(widthProperty);
            imageView.fitHeightProperty().bind(widthProperty);
            imageView.setPreserveRatio(true);
            imageView.setImage(imageObject);
            getChildren().add(imageView);
        }
    }
}

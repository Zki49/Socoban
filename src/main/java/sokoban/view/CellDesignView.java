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

public class CellDesignView extends CellView {

    private Image imageObject = new Image("ground.png");
    private final CellDesignViewModel viewModel;
    private final DoubleBinding widthProperty;

   private final ObservableList<ObjectInMap> objectList;
    private final ImageView imageView = new ImageView();

    public CellDesignView(CellDesignViewModel viewModel, DoubleBinding widthProperty) {
        this.viewModel = viewModel;
        this.widthProperty = widthProperty;
        objectList = viewModel.getObjectList();

        //must be fix
        (objectList).addListener((ListChangeListener<ObjectInMap>) change -> {
            reloadImage();

            viewModel.hasBeenChanged(true);
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
        setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.SECONDARY){
                viewModel.deleteObject();
            }
            else{
                viewModel.addObject();
                reloadImage();
            }
           });
        setOnDragDetected(e -> {
            this.startFullDrag();
        });
        setOnMouseDragEntered(mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.SECONDARY){
                viewModel.deleteObject();
            }
            //a modif crée une fonction dans le viewmodel
            else /*if (!viewModel.getCurrentObjectPath().getValue().equals("wall.png") || !viewModel.getCurrentObjectPath().getValue().equals("ground.png") )*/{
                viewModel.addObject();

            }
            reloadImage();
            });

    }
    public void reloadImage(){
        getChildren().clear();
        getChildren().add(imageView);
        for(String path : viewModel.getObjectsPath()){
            ImageView imageView = new ImageView();
            imageView.fitWidthProperty().bind(widthProperty);
            imageView.fitHeightProperty().bind(widthProperty);
            imageView.setPreserveRatio(true);
            imageView.setImage(findImage(path));
            getChildren().add(imageView);
        }
    }
}

package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sokoban.viewmodel.BoardDesignViewModel;


import java.io.File;

public class FileView extends MenuBar {

    private final BoardDesignViewModel boardDesignViewModel;
    Menu fileMenu = new Menu("File");
    MenuItem newMap = new MenuItem("New Map");
    MenuItem openMap = new MenuItem("Open Map");
    MenuItem saveMap = new MenuItem("Save Map");
    MenuItem exitMap = new MenuItem("Exit");
    Label errorWidth = new Label("Width must be at least 10");
    Label errorHeight = new Label("Height must be at most 50");
    Label errorWidthBis = new Label("Width must be at most 50");
    Label errorHeightBis = new Label("Height must be at least 10");
    Label errorInputHeight = new Label("please enter only numbers (max 50)");
    Label errorInputWidth = new Label("please enter only positive number (max 50)");
    Label LabelSaveChanged = new Label("Do you want to save your changed?");
    String labelNewGameDimensions = new String("Give new game dimensions");
    Button Button_ok = new Button("OK");
    BooleanProperty exitSystem = new SimpleBooleanProperty(false);
    Button Button_cancel = new Button("ANNULER");
    //verifie si le button cancel a été clicker
    private boolean isCancel = false;
    HBox hBox_button = new HBox();
    public FileView(BoardDesignViewModel boardDesignViewModel) {
        this.boardDesignViewModel = boardDesignViewModel;
        setMenuFile();
        getMenus().add(fileMenu);
        setAction();
    }

    public boolean isExitSystem() {
        return exitSystem.get();
    }

    public BooleanProperty exitSystemProperty() {
        return exitSystem;
    }

    private void setMenuFile() {
        fileMenu.getItems().addAll(newMap, openMap, saveMap, exitMap);

    }
    private void setAction(){
        saveMap.setOnAction(action -> {
            saveMap();

        });
        openMap.setOnAction(action -> {
            if(boardDesignViewModel.hasBeenChanged()) {
                hasBeenChanged();
                if(!isCancel){
                    openMap();
                }
            }
            else{
                openMap();
            }



        });

        newMap.setOnAction(action -> {
            if (boardDesignViewModel.hasBeenChanged()){
                hasBeenChanged();
                if(!isCancel){
                    setWeightWidth();
                }
            }
            else{
                setWeightWidth();
            }


        });

        exitMap.setOnAction(action -> {
            Stage stage = (Stage) getScene().getWindow();
            //verifie si la map a été changée
            if(boardDesignViewModel.hasBeenChanged()){
                hasBeenChanged();
                if(!isCancel){
                    System.exit(0);
                    exitSystem.set(true);
                }
            }
            //si la map n'est pas changé on peut sortir directement
            else{
                stage.close();
                exitSystem.set(true);
            }

        });
    }

    public void setWeightWidth(){
        // Création d'une fenêtre de dialogue d'alerte
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sokoban");
        alert.setHeaderText(labelNewGameDimensions);
        alert.setOnShowing(event -> alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(true));
        TextField widthField = new TextField();

        widthField.setPromptText("Width");
        TextField heightField = new TextField();
        heightField.setPromptText("Height");

        // Affichage des valeurs dans une boîte de dialogue
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Width:"), 0, 0);
        grid.add(widthField, 1, 0);
        errorWidth.setTextFill(Color.INDIANRED);
        errorHeight.setTextFill(Color.INDIANRED);
        errorInputWidth.setTextFill(Color.INDIANRED);
        errorWidth.setVisible(false);
        errorHeight.setVisible(false);
        errorInputWidth.setVisible(false);
        grid.add(errorWidth,1,1);
        grid.add(errorWidthBis,1,1);
        grid.add(errorInputWidth,1,1);
        widthField.setOnKeyReleased((p)-> {
            if (!widthField.getText().isEmpty()) {
                try{
                    if (Integer.parseInt(widthField.getText()) < boardDesignViewModel.getMinSize()) {
                        errorWidth.setVisible(true);
                        errorWidthBis.setVisible(false);
                    } else if (Integer.parseInt(widthField.getText()) > boardDesignViewModel.getMaxSize()) {
                        errorWidthBis.setVisible(true);
                        errorWidth.setVisible(false);
                    } else {
                        errorWidth.setVisible(false);
                        errorWidthBis.setVisible(false);
                    }
                    errorInputWidth.setVisible(false);
                }catch (Exception e){
                    errorInputWidth.setVisible(true);
                    errorWidth.setVisible(false);
                    errorWidthBis.setVisible(false);
                }

                alert.getDialogPane().lookupButton(ButtonType.OK).disableProperty().
                        bind(Bindings.createBooleanBinding(() ->
                                errorInputHeight.isVisible()|| errorHeight.isVisible() || errorWidth.isVisible() || errorHeightBis.isVisible() || errorWidthBis.isVisible() ||
                                widthField.getText().isEmpty() || heightField.getText().isEmpty()
                        ));
            }
        });


        grid.add(new Label("Height:"), 0, 2);
        grid.add(heightField, 1, 2);
        errorHeightBis.setTextFill(Color.INDIANRED);
        errorHeightBis.setVisible(false);
        errorWidthBis.setTextFill(Color.INDIANRED);
        errorWidthBis.setVisible(false);
        errorInputHeight.setTextFill(Color.INDIANRED);
        errorInputHeight.setVisible(false);
        grid.add(errorHeightBis,1,3);
        grid.add(errorHeight,1,3);
        grid.add(errorInputHeight,1,3);
        heightField.setOnKeyReleased((p)->{

            if (!heightField.getText().isEmpty()){
                try {
                    if (Integer.parseInt(heightField.getText()) > boardDesignViewModel.getMaxSize()){
                        errorHeight.setVisible(true);
                        errorHeightBis.setVisible(false);
                    } else if (Integer.parseInt(heightField.getText()) < boardDesignViewModel.getMinSize()) {
                        errorHeight.setVisible(false);
                        errorHeightBis.setVisible(true);
                    } else {
                        errorHeightBis.setVisible(false);
                        errorWidthBis.setVisible(false);
                    }
                    errorInputHeight.setVisible(false);
                }catch (Exception e) {
                    errorInputHeight.setVisible(true);
                    errorHeightBis.setVisible(false);
                    errorWidthBis.setVisible(false);
                }

                alert.getDialogPane().lookupButton(ButtonType.OK).disableProperty().
                        bind(Bindings.createBooleanBinding(() ->
                               errorInputHeight.isVisible() || errorHeight.isVisible() || errorWidth.isVisible() || errorHeightBis.isVisible() || errorWidthBis.isVisible() ||
                                (widthField.getText().isBlank() || heightField.getText().isBlank() || widthField.getText().isBlank() && heightField.getText().isBlank())
                        ));
            }
        });

        // Définition des boutons de la fenêtre de dialogue
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        alert.getDialogPane().setContent(grid);

//        alert.setResizable(true);
//        alert.getDialogPane().setPrefSize(350, 500);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());
                boardDesignViewModel.newMap(width,height);
                System.out.println("Width: " + width + ", Height: " + height);
            }
        });

    }
    public void saveMap(){
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Map");
            fileChooser.setInitialFileName(boardDesignViewModel.getNameFile() != null ? boardDesignViewModel.getNameFile() :"sokoban.xsb");
            File  initialDirectory = new File("boards");
            fileChooser.setInitialDirectory(initialDirectory);
            Stage stage = (Stage) getScene().getWindow(); // Assuming this method is inside a JavaFX control
            File file = fileChooser.showSaveDialog(stage);
            //si le fichier est selectioner on save la map dedans
            if(file != null){
                boardDesignViewModel.saveMap(file);
                boardDesignViewModel.setHasBeenChanged(false);
            }
        }catch (Exception e){
            System.out.println(e.getMessage() );
        }
    }
    public void hasBeenChanged(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Your board has been modified.");
        alert.getDialogPane().setContent(LabelSaveChanged);

        ButtonType buttonTypeYes = new ButtonType("Oui");
        ButtonType buttonTypeNo = new ButtonType("Non");
        ButtonType buttonTypeCancel = new ButtonType("Annuler");
        //nous permet de savoir si on click sur annuler
        isCancel = false;
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);

        alert.setResizable(false);
        alert.getDialogPane().setPrefSize(350, 100);

        alert.showAndWait().ifPresent(reponse -> {
              if (reponse == buttonTypeYes) {
                  saveMap();
            }
            else if(reponse == buttonTypeCancel){
                isCancel = true;
            }
        });
    }
    public void openMap(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Map");
        File initialDirectory = new File("boards");
        fileChooser.setInitialDirectory(initialDirectory);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.xsb"));
        Stage stage = (Stage) getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if(file != null){
            if(boardDesignViewModel.isValidFile(file)){
                boardDesignViewModel.loadMap(file,file.getName() );
            }
            else{
                showErroFile();
            }

        }


    }

    private void showErroFile() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("The file is not a valid map file.");


        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(buttonTypeOk);

        alert.showAndWait();
    }
}

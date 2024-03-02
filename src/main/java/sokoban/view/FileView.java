package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sokoban.viewmodel.BoardViewModel;


import java.io.File;
import java.util.Optional;

public class FileView extends MenuBar {

    private final BoardViewModel boardViewModel;
    Menu fileMenu = new Menu("File");
    MenuItem newMap = new MenuItem("New Map");
    MenuItem openMap = new MenuItem("Open Map");
    MenuItem saveMap = new MenuItem("Save Map");
    MenuItem exitMap = new MenuItem("Exit");
    Label errorWidth = new Label("Width must be at least 10");
    Label errorHeight = new Label("Height must be at most 50");
    Label LabelSaveChanged = new Label("Do you want to save your changed?");
    String labelNewGameDimensions = new String("Give new game dimensions");
    Button Button_ok = new Button("OK");
    Button Button_cancel = new Button("ANNULER");
    //verifie si le button cancel a été clicker
    private boolean isCancel = false;
    HBox hBox_button = new HBox();
    public FileView(BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        setMenuFile();
        getMenus().add(fileMenu);
        setAction();
    }

    private void setMenuFile() {
        fileMenu.getItems().addAll(newMap, openMap, saveMap, exitMap);

    }
    private void setAction(){
        saveMap.setOnAction(action -> {
            saveMap();

        });
        openMap.setOnAction(action -> {
            if(boardViewModel.hasBeenChanged()) {
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
            if (boardViewModel.hasBeenChanged()){
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
            if(boardViewModel.hasBeenChanged()){
                hasBeenChanged();
                if(!isCancel){
                    stage.close();
                }
            }
            //si la map n'est pas changé on peut sortir directement
            else{
                stage.close();
            }

        });
    }

    public void setWeightWidth(){
        // Création d'une fenêtre de dialogue d'alerte
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sokoban");
        alert.setHeaderText(labelNewGameDimensions);

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
        errorWidth.setVisible(false);
        grid.add(errorWidth,1,1);
        widthField.setOnKeyReleased((p)-> {
            if (!widthField.getText().isEmpty()) {
                if (Integer.parseInt(widthField.getText()) < boardViewModel.getMaxWidth()) {
                    errorWidth.setVisible(true);
                } else {
                    errorWidth.setVisible(false);
                }
                alert.getDialogPane().lookupButton(ButtonType.OK).disableProperty().
                        bind(Bindings.createBooleanBinding(() ->
                                errorWidth.isVisible()));
            }
        });


        grid.add(new Label("Height:"), 0, 2);
        grid.add(heightField, 1, 2);
        errorHeight.setTextFill(Color.INDIANRED);
        errorHeight.setVisible(false);
        grid.add(errorHeight,1,3);
        heightField.setOnKeyReleased((p)->{
            if (!heightField.getText().isEmpty()){
                if (Integer.parseInt(heightField.getText()) > boardViewModel.getMaxHeight()){
                    errorHeight.setVisible(true);
                    alert.getDialogPane().lookupButton(ButtonType.OK).disableProperty().
                            bind(Bindings.createBooleanBinding(() ->
                                    errorHeight.isVisible()));
                }else {
                    errorHeight.setVisible(false);
                }
                alert.getDialogPane().lookupButton(ButtonType.OK).disableProperty().
                        bind(Bindings.createBooleanBinding(() ->
                                errorHeight.isVisible()));
            }
        });
        alert.getDialogPane().lookupButton(ButtonType.OK).disableProperty().
                bind(Bindings.createBooleanBinding(() ->
                        errorHeight.isVisible() && errorWidth.isVisible()));


        // Définition des boutons de la fenêtre de dialogue
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        alert.getDialogPane().setContent(grid);

//        alert.setResizable(true);
//        alert.getDialogPane().setPrefSize(350, 500);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());
                boardViewModel.newMap(width,height);
                System.out.println("Width: " + width + ", Height: " + height);
            } else {
                System.out.println("Annuler");
            }
        });

    }
    public void saveMap(){
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Map");
            fileChooser.setInitialFileName("test.xsb");
            File  initialDirectory = new File("boards");
            fileChooser.setInitialDirectory(initialDirectory);
            Stage stage = (Stage) getScene().getWindow(); // Assuming this method is inside a JavaFX control
            File file = fileChooser.showSaveDialog(stage);
            //si le fichier est selectioner on save la map dedans
            if(file != null){
                boardViewModel.saveMap(file);
                boardViewModel.newMap(15,10);
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
                //setWeightWidth();
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

            boardViewModel.loadMap(file);
        }
        else{
            System.out.println("error");
        }

    }
}

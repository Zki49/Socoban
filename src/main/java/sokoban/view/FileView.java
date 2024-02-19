package sokoban.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sokoban.viewmodel.BoardViewModel;


import java.io.File;

public class FileView extends MenuBar {

    private final BoardViewModel boardViewModel;
    Menu fileMenu = new Menu("File");
    MenuItem newMap = new MenuItem("New Map");
    MenuItem openMap = new MenuItem("Open Map");
    MenuItem saveMap = new MenuItem("Save Map");
    MenuItem exitMap = new MenuItem("Exit");
    Label errorWidth = new Label("Width must be at least 10");
    Label errorHeight = new Label("Height must be at most 50");
    Label labelNewGameDimensions = new Label("Give new game dimensions");
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
                }
            }catch (Exception e){
                System.out.println(e.getMessage() );
            }

        });
        openMap.setOnAction(action -> {
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

        });

        newMap.setOnAction(action ->
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Sokoban");
            alert.setHeaderText(null);

            TextField widthField = new TextField();

            widthField.setPromptText("Width");
            TextField heightField = new TextField();
            heightField.setPromptText("Height");


            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            grid.add(new Label("Width:"), 0, 0);
            grid.add(widthField, 1, 0);
            grid.add(new Label("Height:"), 0, 1);
            grid.add(heightField, 1, 1);
            GridPane.setHalignment(labelNewGameDimensions, Pos.CENTER.getHpos());


            alert.getDialogPane().setContent(grid);


            alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);


            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {

                    int width = Integer.parseInt(widthField.getText());
                    int height = Integer.parseInt(heightField.getText());
                    System.out.println("Width: " + width + ", Height: " + height);

                } else {

                    System.out.println("Annuler");
                }
            });
        });

        exitMap.setOnAction(action -> {
            Stage stage = (Stage) getScene().getWindow();
            stage.close();
        });
    }

}

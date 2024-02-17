package sokoban.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
                if(file != null){
                    boardViewModel.saveMap(file);
                }
            }catch (Exception e){
                System.out.println(e.getMessage() +"test");
            }

        });
    }

}

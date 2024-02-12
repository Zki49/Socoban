package sokoban.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import sokoban.viewmodel.BoardViewModel;

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
    }

    private void setMenuFile() {
        fileMenu.getItems().addAll(newMap, openMap, saveMap, exitMap);

    }

}

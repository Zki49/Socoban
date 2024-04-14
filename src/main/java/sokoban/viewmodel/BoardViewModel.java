package sokoban.viewmodel;

import javafx.beans.property.SimpleStringProperty;

public abstract class BoardViewModel {

    public abstract SimpleStringProperty getTitle();

    public abstract MapViewModel getMapViewModel();
}

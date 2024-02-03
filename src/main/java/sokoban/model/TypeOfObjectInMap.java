package sokoban.model;

public enum TypeOfObjectInMap {
    PLAYER("player.png"),

    WALL("wall.png"),
    GOAL("goal.png"),
    BOX("box.png"),
    ;
    String path;
    TypeOfObjectInMap(String path) {
        this.path = path;
    }
    public String getPath() {
        return path;
    }
}

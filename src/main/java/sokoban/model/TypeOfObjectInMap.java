package sokoban.model;

public enum TypeOfObjectInMap {
    PLAYER("player.png"),

    WALL("wall.png"),
    GOAL("goal.png"),
    BOX("box.png");
    String path;
    TypeOfObjectInMap(String path) {
        this.path = path;
    }
    public String getPath() {
        return path;
    }


    //cette fonction renvoie le type d'object selon le type d'enum je l'utilise dans la class Cell lors
    // de l'ajout d'un objet dans la map
    public ObjectInMap getObjectInMap() {
        return switch(this.ordinal()){
            case 0 -> new Player();
            case 1 -> new Wall();
            case 2 -> new goal();
            default -> new Box();
        };
    }
}

package sokoban.model;

public enum TypeOfObjectInMap {

    PLAYER,

    WALL,
    GOAL,
    BOX,
    GROUND,
    MUSHROOM;

    String path;




    //cette fonction renvoie le type d'object selon le type d'enum je l'utilise dans la class Cell lors
    // de l'ajout d'un objet dans la map
    public ObjectInMap getObjectInMap() {
        return switch(this.ordinal()){
            case 0 -> new Player();
            case 1 -> new Wall();
            case 2 -> new Goal();
            case 3 -> new Box();
            default -> new Mushroom();
        };
    }
}

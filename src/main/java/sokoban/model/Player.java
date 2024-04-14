package sokoban.model;

class Player extends ObjectInMap{
    Player() {
        setTypeOfObjectInMap(TypeOfObjectInMap.PLAYER);
        setWeight(1);
    }
}

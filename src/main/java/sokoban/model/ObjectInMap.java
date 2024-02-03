package sokoban.model;

abstract public class ObjectInMap {

    private TypeOfObjectInMap typeOfObjectInMap;

    public ObjectInMap() {

    }

    public TypeOfObjectInMap getTypeOfObjectInMap() {
        return typeOfObjectInMap;
    }

    public void setTypeOfObjectInMap(TypeOfObjectInMap typeOfObjectInMap) {
        this.typeOfObjectInMap = typeOfObjectInMap;
    }
}

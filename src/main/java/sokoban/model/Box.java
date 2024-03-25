package sokoban.model;

class Box extends ObjectInMap{

    private static int indexPrimary = 1;
    private int index = indexPrimary++;

    Box() {
        setTypeOfObjectInMap(TypeOfObjectInMap.BOX);
        setWeight(1);
    }

    static void resetIndex() {
        indexPrimary = 1;
    }

    int getIndex() {
        return index;
    }
}

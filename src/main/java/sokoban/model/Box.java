package sokoban.model;

public class Box extends ObjectInMap{

    private static int indexPrimary = 1;
    private int index = indexPrimary++;

    public Box() {
        setTypeOfObjectInMap(TypeOfObjectInMap.BOX);
        setWeight(1);
    }

    public static void setIndex(int i) {
        indexPrimary = 1;
    }

    public int getIndex() {
        return index;
    }
}

package sokoban.model;
//test
abstract public class ObjectInMap implements Comparable<ObjectInMap> {

    private TypeOfObjectInMap typeOfObjectInMap;

    private int weight = 0;

    public ObjectInMap() {

    }

    public TypeOfObjectInMap getTypeOfObjectInMap() {
        return typeOfObjectInMap;
    }

    public void setTypeOfObjectInMap(TypeOfObjectInMap typeOfObjectInMap) {
        this.typeOfObjectInMap = typeOfObjectInMap;
    }



    public int getweight() {

        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    public  int compareTo(ObjectInMap o){
        return  o.getweight() - this.getweight() ;
    }
}

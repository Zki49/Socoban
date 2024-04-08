package sokoban.model;

import java.util.HashMap;

public class MoveCommandeShuffle extends MoveCommande{

    private HashMap<MapPlay.Point, ObjectInMap>  initialLocation = new HashMap<>();
    private HashMap<MapPlay.Point, ObjectInMap>  afterShufflelLocation = null;
    private MapPlay mapPlay;
    public MoveCommandeShuffle(MapPlay mapPlay) {
        this.mapPlay = mapPlay;
        initialLocation = mapPlay.getInitialLocationOfObject();

    }


    @Override
    public void doMove() {
        if(afterShufflelLocation == null){
            afterShufflelLocation = mapPlay.shuffleBox();
        }
        else{
            mapPlay.MoveAllObjectInMap(afterShufflelLocation);
        }

    }


    @Override
    public void undoMove() {
        mapPlay.MoveAllObjectInMap(initialLocation);

    }
}

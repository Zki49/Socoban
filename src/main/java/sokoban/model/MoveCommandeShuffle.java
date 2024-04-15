package sokoban.model;

import java.util.HashMap;

class MoveCommandeShuffle extends MoveCommande{

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
            mapPlay.incrementScore(5);
        }

    }


    @Override
    public void undoMove() {
        mapPlay.MoveAllObjectInMap(initialLocation);
        mapPlay.incrementScore(1);


    }
}

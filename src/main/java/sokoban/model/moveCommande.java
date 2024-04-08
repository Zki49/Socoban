package sokoban.model;

import java.util.Map;

public class moveCommande {


    private Box box = null;
    private LastMove lastMove = null;
    private MapPlay mapPlay ;
    private Move move;

    public moveCommande(MapPlay map , LastMove lastMove, Move move) {
        mapPlay = map;
        this.lastMove = lastMove;
        this.move = move;

    }

    public  void doMove(){
        box = move.movePlayer();

    }
    public void undoMove(){

        mapPlay.moveBack(lastMove, box);
    }

}

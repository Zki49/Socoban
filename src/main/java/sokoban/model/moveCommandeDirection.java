package sokoban.model;

class moveCommandeDirection extends MoveCommande{


    private Box box = null;
    private LastMove lastMove = null;
    private MapPlay mapPlay ;
    private Move move;

    moveCommandeDirection(MapPlay map , LastMove lastMove, Move move) {
        mapPlay = map;
        this.lastMove = lastMove;
        this.move = move;

    }

    void doMove(){
        box = move.movePlayer();

    }
    void undoMove(){

        mapPlay.moveBack(lastMove, box);
    }

}

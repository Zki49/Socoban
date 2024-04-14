package sokoban.model;

class moveCommandeDirection extends MoveCommande{


    private Box box = null;
    private LastMove lastMove = null;
    private MapPlay mapPlay ;
    private Move move;

    public moveCommandeDirection(MapPlay map , LastMove lastMove, Move move) {
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

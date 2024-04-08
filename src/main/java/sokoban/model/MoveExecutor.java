package sokoban.model;

import java.util.ArrayList;
import java.util.List;

public class MoveExecutor {

    private List<Move> moveList = new ArrayList<>();
    private int currentIndex = -1;

    public void executeMove(Move move) {
        move.movePlayer();
        if (currentIndex < moveList.size()-1){
            moveList = moveList.subList(0,currentIndex+1);
        }

        moveList.add(move);
        currentIndex++;
        System.out.println(currentIndex +" / "+ moveList.size() +" / ");
    }
    public void moveBack(Move move, modifyScore reduceScore, modifyScore penality) {
        if(currentIndex >= 0){
            int indexBeforLastMove = currentIndex - 1;
            penality.modify(5);
            reduceScore.modify(currentIndex);
            currentIndex = -1;
            move.movePlayer();
            //System.out.println(currentIndex +" / "+ moveList.size() +" / "+ indexBeforLastMove);
            for(int i = 0; i <= indexBeforLastMove; i++){
                moveList.get(i).movePlayer();
                currentIndex++;
                //1 2 3 4
            }
        }
    }
    public void moveFront() {
        if (currentIndex < moveList.size()-1){

            moveList.get(++currentIndex).movePlayer();
        }
    }
}

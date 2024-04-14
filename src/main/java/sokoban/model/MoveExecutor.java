package sokoban.model;

import java.util.ArrayList;
import java.util.List;

public class MoveExecutor {

    private List<MoveCommande> moveList = new ArrayList<>();
    private int currentIndex = -1;

    public void executeMove(MoveCommande move) {
        move.doMove();
        if (currentIndex < moveList.size()-1){
            moveList = moveList.subList(0,currentIndex+1);
        }

        moveList.add(move);
        currentIndex++;
        System.out.println(currentIndex +" / "+ moveList.size() +" / ");
    }
    public void moveBack( modifyScore reduceScore, modifyScore penality) {
        if(currentIndex >= 0) {

            penality.modify(4);

            moveList.get(currentIndex).undoMove();
            currentIndex--;
            //System.out.println(currentIndex +" / "+ moveList.size() +" / "+ indexBeforLastMove);
        }
    }
    public void moveFront() {
        if (currentIndex < moveList.size()-1){

            moveList.get(++currentIndex).doMove();
        }
    }
}

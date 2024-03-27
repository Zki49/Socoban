package sokoban.model;

import java.util.ArrayList;
import java.util.List;

public class MoveExecutor {

    private final List<Move> moveList = new ArrayList<>();
    private int currentIndex = -1;
    public void executeMove(Move move) {
        move.movePlayer();
        moveList.add(move);
        currentIndex++;
    }
    public void moveBack(Move move) {
        if(currentIndex >= 0){
            int indexBeforLastMove = currentIndex - 1;
            currentIndex = -1;
            move.movePlayer();
            for(int i = 0; i <= indexBeforLastMove; i++){
                executeMove(moveList.get(i));
            }

        }


    }
}

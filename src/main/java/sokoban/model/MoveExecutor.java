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
        move.movePlayer();

    }
}

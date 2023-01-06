package a01b.e2;

import java.util.Set;

public interface Logic {

    void registerMove(Pair<Integer, Integer> move);

    boolean isValid(Pair<Integer, Integer> move);

    int getMoveNumber();
    
    Set<Pair<Integer, Integer>> drawOnBoard();

    boolean isOver();
    
}

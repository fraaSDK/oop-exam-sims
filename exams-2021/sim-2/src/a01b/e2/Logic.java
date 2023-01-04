package a01b.e2;

import java.util.List;

public interface Logic {

    void newMove(int x, int y);
    
    List<Pair<Integer, Integer>> drawOnBoard();

    boolean isOver();
    
    void addToBoard(int x, int y, boolean played);

}

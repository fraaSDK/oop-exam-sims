package a01b.e2;

import java.util.ArrayList;
import java.util.List;

/**
 * Summary:
 * Started: 75 minutes.
 * Current: 90 minutes. Last deadline, end of exam.
 */
public class LogicImpl implements Logic {

    private List<Pair<Integer, Integer>> moves = new ArrayList<>();

    @Override
    public void newMove(int x, int y) {
        if (moves.size() != 3) {
            moves.add(new Pair<>(x, y));
        }
    }

    @Override
    public List<Pair<Integer, Integer>> drawOnBoard() {
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        if (isValid()) {
            // TODO
            return result;
        }
        return null;
    }

    @Override
    public boolean isOver() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void addToBoard(int x, int y, boolean played) {
        // TODO Auto-generated method stub
        
    }

    private boolean isValid() {
        // TODO
        return false;
    }
    
}

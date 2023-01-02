package a01a.e2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicImpl implements Logic {

    private Map<Pair<Integer, Integer>, Boolean> gameBoard = new HashMap<>();

    @Override
    public List<Pair<Integer, Integer>> computeRectangle() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isOver() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void addToGameBoard(int x, int y, boolean played) {
        gameBoard.put(new Pair<>(x, y), played);
    }
    
}

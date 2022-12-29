package a06b.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LogicImpl implements Logic {

    private Optional<Pair<Integer, Integer>> previousMove = Optional.empty();
    private Map<Pair<Integer, Integer>, Boolean> grid = new HashMap<>();

    @Override
    public boolean isValidMove(int x, int y) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isOver() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void addToGrid(int x, int y, boolean selected) {
        // TODO Auto-generated method stub
        
    }
    
}

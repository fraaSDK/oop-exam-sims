package a01a.e2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicImpl implements Logic {

    private Map<Pair<Integer, Integer>, Boolean> gameBoard = new HashMap<>();
    private List<Pair<Integer, Integer>> movesToProcess = new ArrayList<>();

    @Override
    public void registerMove(int x, int y) {
        if (enoughMoves()) {
            movesToProcess.clear();
        }

        movesToProcess.add(new Pair<>(x, y));
        // System.out.println("New move: " + movesToProcess);
    }

    @Override
    public List<Pair<Integer, Integer>> computeRectangle() {
        if (!enoughMoves()) {
            return Collections.emptyList();
        }

        var result = new ArrayList<Pair<Integer, Integer>>();
        var startPoint = movesToProcess.get(0);
        var endPoint = movesToProcess.get(1);
        var rowDiff = Math.abs(startPoint.getY() - endPoint.getY());
        var colDiff = Math.abs(startPoint.getX() - endPoint.getX());

        for (int i = startPoint.getY(); i <= startPoint.getY() + colDiff; i++) {
            for (int j = startPoint.getX(); j <= startPoint.getX() + rowDiff; j++) {
                // System.out.println("x: " + j + ", y: " + i);
                result.add(new Pair<>(j, i));
            }
        }

        return result;
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

    private boolean enoughMoves() {
        return movesToProcess.size() == 2;
    }

}

package a01a.e2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicImpl implements Logic {

    // private Map<Pair<Integer, Integer>, Boolean> gameBoard = new HashMap<>();
    private List<Pair<Integer, Integer>> movesToProcess = new ArrayList<>();

    @Override
    public void registerMove(int x, int y) {
        if (enoughMoves()) {
            movesToProcess.clear();
        }

        movesToProcess.add(new Pair<>(x, y));
        System.out.println("New move: " + movesToProcess);
    }

    @Override
    public List<Pair<Integer, Integer>> computeRectangle() {
        if (!enoughMoves()) {
            return Collections.emptyList();
        }

        var result = new ArrayList<Pair<Integer, Integer>>();

        var points = getRectangleEdges(movesToProcess.get(0), movesToProcess.get(1));
        
        // TODO

        return result;
    }
    
    @Override
    public boolean isOver() {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public void addToGameBoard(int x, int y, boolean played) {
        // gameBoard.put(new Pair<>(x, y), played);
    }

    private boolean enoughMoves() {
        return movesToProcess.size() == 2;
    }

    private List<Pair<Integer, Integer>> getRectangleEdges(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
        List<Pair<Integer, Integer>> edges = new ArrayList<>(
            List.of(p1, p2, new Pair<>(p2.getX(), p1.getY()), new Pair<>(p1.getX(), p2.getY()))
        );

        // Sorting by both coordinates.
        edges.sort((prev, next) -> {
            if (prev.getY() == next.getY()) {
                return prev.getX().compareTo(next.getX());
            } else {
                return prev.getY().compareTo(next.getY());
            }
        });

        return edges;
    }

}

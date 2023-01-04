package a01a.e2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogicImpl implements Logic {

    private Set<Pair<Integer, Integer>> moves = new HashSet<>();
    private List<Pair<Integer, Integer>> movesToProcess = new ArrayList<>();
    private int size;

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public void registerMove(int x, int y) {
        if (enoughMoves()) {
            movesToProcess.clear();
        }

        movesToProcess.add(new Pair<>(x, y));
    }

    @Override
    public List<Pair<Integer, Integer>> computeRectangle() {
        if (!enoughMoves()) {
            return Collections.emptyList();
        }

        var result = new ArrayList<Pair<Integer, Integer>>();

        var points = getRectangleEdges(movesToProcess.get(0), movesToProcess.get(1));
        for (int i = points.get(0).getX(); i <= points.get(1).getX(); i++) {
            for (int j = points.get(0).getY(); j <= points.get(2).getY(); j++) {
                result.add(new Pair<>(i, j));
            }
        }

        moves.addAll(result);
        return result;
    }
    
    @Override
    public boolean isOver() {
        return moves.size() == size * size;
    }

    private boolean enoughMoves() {
        return movesToProcess.size() == 2;
    }

    // Calculates the remaining edges and sort them by both coordinates.
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

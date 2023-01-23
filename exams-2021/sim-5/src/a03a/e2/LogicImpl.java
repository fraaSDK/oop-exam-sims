package a03a.e2;

import java.util.ArrayList;
import java.util.List;

public class LogicImpl implements Logic {

    private final int size;
    private Pair<Integer, Integer> currentMove;
    private List<Pair<Integer, Integer>> currentVertexes;

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public boolean registerMove(Pair<Integer, Integer> move) {
        if (!isValid(move)) {
            return false;
        }
        currentMove = new Pair<>(move.getX(), move.getY());
        return true;
    }

    @Override
    public List<Pair<Integer, Integer>> getRectangle() {
        currentVertexes = computeVertexes(currentMove);
        return pointsInVertexRange(currentVertexes);
    }

    @Override
    public boolean isOver() {
        if (currentMove == null || currentVertexes == null) {
            return false;
        }
        /*
         * If the distance between the X or Y coordinates is == 1 then the
         * rectangle is too small to have more inner rectangles.
         */
        return currentVertexes.get(1).getX() - currentVertexes.get(0).getX() == 1 ||
                currentVertexes.get(2).getY() - currentVertexes.get(0).getY() == 1;
    }

    private List<Pair<Integer, Integer>> computeVertexes(Pair<Integer, Integer> p) {
        Pair<Integer, Integer> diagonalPoint = new Pair<>(size - 1 - p.getX(), size - 1 - p.getY());

        return List.of(p, new Pair<>(diagonalPoint.getX(), p.getY()),
                new Pair<>(p.getX(), diagonalPoint.getY()), diagonalPoint);
    }

    private List<Pair<Integer, Integer>> pointsInVertexRange(List<Pair<Integer, Integer>> vertexes) {
        List<Pair<Integer, Integer>> points = new ArrayList<>();
        // Horizontal.
        for (int i = vertexes.get(0).getX(); i <= vertexes.get(1).getX(); i++) {
            points.add(new Pair<>(i, vertexes.get(0).getY()));
            points.add(new Pair<>(i, vertexes.get(2).getY()));
        }
        // Vertical.
        for (int i = vertexes.get(0).getY(); i <= vertexes.get(2).getY(); i++) {
            points.add(new Pair<>(vertexes.get(0).getX(), i));
            points.add(new Pair<>(vertexes.get(1).getX(), i));
        }
        
        return points;
    }

    private boolean isValid(Pair<Integer, Integer> move) {
        // Moves are allowed only in the top-left corner of the grid.
        if (move.getX() >= size / 2 || move.getY() >= size / 2) {
            return false;
        }
        return (move == null || currentMove == null || currentVertexes == null) ||
                move.getX() > currentVertexes.get(0).getX() &&
                move.getY() > currentVertexes.get(0).getY() &&
                move.getX() < currentVertexes.get(3).getX() &&
                move.getY() < currentVertexes.get(3).getY();
                
    }

}

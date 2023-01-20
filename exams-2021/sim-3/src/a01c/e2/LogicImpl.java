package a01c.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LogicImpl implements Logic {

    private static int MAX_MOVES = 2;
    private List<Pair<Integer, Integer>> moves = new ArrayList<>(MAX_MOVES);
    private Optional<Direction> previousDirection = Optional.empty();

    @Override
    public void registerMove(int x, int y) {
        if (hasEnoughMoves()) {
            previousDirection = getDirection(moves.get(0), moves.get(1));
            moves.remove(0);
        }
        
        moves.add(new Pair<>(x, y));
    }

    @Override
    public Optional<List<Pair<Integer, Integer>>> toDisplay() {
        if (!hasEnoughMoves()) {
            return Optional.empty();
        }

        List<Pair<Integer, Integer>> result = new ArrayList<>();
        var copy = copyAndSort(moves);
        for (int i = copy.get(0).getX(); i <= copy.get(1).getX(); i++) {
            for (int j = copy.get(0).getY(); j <= copy.get(1).getY(); j++) {
                result.add(new Pair<>(i, j));
            }
        }
        return Optional.of(result);
    }

    @Override
    public boolean isValid() {
        if (!previousDirection.isPresent()) {
            return true;
        }

        var direction = getDirection(moves.get(0), moves.get(1));
        var condition = direction.isPresent() && previousDirection.get() != direction.get();
        
        // If the move is not valid, we need to remove it.
        if (!condition) {
            moves.remove(1);
        }
        return condition;
    }

    private Optional<Direction> getDirection(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
        if (p1.equals(p2) || (p1.getX() != p2.getX() && p1.getY() != p2.getY())) {
            return Optional.empty();
        }

        return p1.getX() == p2.getX() ? Optional.of(Direction.VERTICAL) : Optional.of(Direction.HORIZONTAL);

    }

    private boolean hasEnoughMoves() {
        return moves.size() == MAX_MOVES;
    }

    private List<Pair<Integer, Integer>> copyAndSort(List<Pair<Integer, Integer>> list) {
        List<Pair<Integer, Integer>> l = new ArrayList<>(list);
        l.sort((prev, next) -> {
            if (prev.getX() == next.getX()) {
                return prev.getY().compareTo(next.getY());
            } else {
                return prev.getX().compareTo(next.getX());
            }
        });
        return l;
    }

    enum Direction {
        HORIZONTAL,
        VERTICAL
    }

}

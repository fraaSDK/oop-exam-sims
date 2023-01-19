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
            previousDirection = Optional.of(getDirection(moves.get(0), moves.get(1)));
            moves.remove(0);
        }
        
        moves.add(new Pair<>(x, y));
        
        System.out.println(moves);
        System.out.println(previousDirection);
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
        if (previousDirection.isPresent()) {
            System.out.println(previousDirection.get());
        }
        return !previousDirection.isPresent() ? true
                : previousDirection.get() != getDirection(moves.get(0), moves.get(1));
    }

    private Direction getDirection(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
        return p1.getX() == p2.getX() ? Direction.VERTICAL : Direction.HORIZONTAL;
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

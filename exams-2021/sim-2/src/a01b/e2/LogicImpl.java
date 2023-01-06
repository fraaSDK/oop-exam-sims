package a01b.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogicImpl implements Logic {

    private List<Pair<Integer, Integer>> moves = new ArrayList<>();

    @Override
    public void registerMove(Pair<Integer, Integer> move) {
        moves.add(new Pair<>(move.getX(), move.getY()));
    }

    @Override
    public boolean isValid(Pair<Integer, Integer> move) {
        switch (moves.size()) {
            case 0:
                return true;
            case 1:
                return (moves.get(0).getX() == move.getX() || moves.get(0).getY() == move.getY()) &&
                    !isPlayed(move);
            case 2:
                return !isPlayed(move) && (moves.get(0).getX() == moves.get(1).getX()
                    ? moves.get(0).getY() == move.getY()
                    : moves.get(0).getX() == move.getX());
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public int getMoveNumber() {
        return moves.size();
    }

    @Override
    public Set<Pair<Integer, Integer>> drawOnBoard() {
        var move2 = moves.get(1);
        var move3 = moves.get(2);

        var res = Stream.concat(getCoordinatesRange(move2).stream(), getCoordinatesRange(move3).stream())
                .collect(Collectors.toSet());

        return res;
    }

    @Override
    public boolean isOver() {
        return moves.size() == 3;
    }

    private List<Pair<Integer, Integer>> getCoordinatesRange(Pair<Integer, Integer> move) {
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        var firstMove = moves.get(0);
        if (move.getX() < firstMove.getX() || move.getY() < firstMove.getY()) {
            // swap values of firstMove and move
            var temp = firstMove;
            firstMove = move;
            move = temp;
        }
        for (int i = firstMove.getX(); i <= move.getX(); i++) {
            for (int j = firstMove.getY(); j <= move.getY(); j++) {
                result.add(new Pair<>(i, j));
            }
        }
        return result;
    }

    private boolean isPlayed(Pair<Integer, Integer> move) {
        return moves.contains(move);
    }

}

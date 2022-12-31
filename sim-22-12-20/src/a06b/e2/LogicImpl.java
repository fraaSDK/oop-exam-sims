package a06b.e2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class LogicImpl implements Logic {

    private Optional<Pair<Integer, Integer>> previousMove = Optional.empty();
    private Map<Integer, Pair<Integer, Integer>> grid = new HashMap<>();
    private List<Pair<Integer, Integer>> playedMoves = new ArrayList<>();
    private Random rand = new Random();

    @Override
    public Optional<Integer> getNextMove() {
        Pair<Integer, Integer> currentMove;

        // First time its not random.
        if (previousMove.isEmpty()) {
            currentMove = new Pair<>(3, 3); // TODO do not use magic numbers!!!
            previousMove = Optional.of(currentMove);

            System.out.println("MOVE: " + currentMove);    // Debug.

            playedMoves.add(currentMove);
            // Getting entry index.
            return Optional.of(getMoveIndex(currentMove));
        }
        
        currentMove = getRandomMove(previousMove.get());
        System.out.println("MOVE: " + currentMove);

        previousMove = Optional.of(currentMove);

        playedMoves.add(currentMove);
        return Optional.of(getMoveIndex(currentMove));

    }

    @Override
    public boolean isOver() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void addToGrid(int index, int x, int y) {
        grid.put(index, new Pair<>(x, y));
    }

    // Using the Pythagorean Theorem to check adjacency.
    private boolean isAdjacent(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
        var rowDifference = p1.getX() - p2.getX();
        var colDifference = p1.getY() - p2.getY();
        var distance = Math.sqrt(rowDifference * rowDifference + colDifference * colDifference);

        // First check for non-diagonal adjacency, then for diagonal.
        return Double.compare(distance, 1) == 0 || Double.compare(distance, Math.sqrt(2)) == 0;
    }

    private boolean isPlayed(Pair<Integer, Integer> move) {
        return playedMoves.contains(move);
    }

    private boolean isInBounds(Pair<Integer, Integer> move) {
        // TODO do not use magic numbers!!!
        return move.getX() < 7 && move.getY() < 7;
    }

    // Returns the key (index) of the given value (move).
    private int getMoveIndex(Pair<Integer, Integer> move) {
        return grid.entrySet().stream()
                .filter(entry -> entry.getValue().equals(move))
                .mapToInt(Map.Entry::getKey)
                .findFirst()
                .getAsInt();
    }

    // FIXME out of bound. Fix bound checking.
    private Pair<Integer, Integer> getRandomMove(Pair<Integer, Integer> previousMove) {
        var max = 2;
        var min = -1;
        var result = new Pair<>(previousMove.getX() + rand.nextInt(max - min) + min,
                previousMove.getY() + rand.nextInt(max - min) + min);

        while (!isInBounds(result) || !isAdjacent(previousMove, result) || isPlayed(result)) {
            result = new Pair<>(previousMove.getX() + rand.nextInt(max - min) + min,
                    previousMove.getY() + rand.nextInt(max - min) + min);
        }

        System.out.println("[DEBUG] RAND: " + result);  // Debug.

        return result;
    }

}

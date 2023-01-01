package a06b.e2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class LogicImpl implements Logic {

    private Optional<Pair<Integer, Integer>> previousMove = Optional.empty();
    private Map<Integer, Pair<Integer, Integer>> gameBoard = new HashMap<>();
    private List<Pair<Integer, Integer>> playedPositions = new ArrayList<>();
    private boolean isGameOver;
    private Random rand = new Random();

    @Override
    public Optional<Integer> getNextMove() {
        Pair<Integer, Integer> currentMove;

        // First time its not random.
        if (previousMove.isEmpty()) {
            var rowColNumber = (int) Math.sqrt(gameBoard.size());
            currentMove = new Pair<>(rowColNumber / 2, rowColNumber / 2);
            previousMove = Optional.of(currentMove);

            System.out.println("MOVE: " + currentMove);    // Debug.

            playedPositions.add(currentMove);
            return Optional.of(getMoveIndex(currentMove));
        }
        
        currentMove = getRandomMove(previousMove.get());
        System.out.println("MOVE: " + currentMove);

        previousMove = Optional.of(currentMove);

        playedPositions.add(currentMove);
        return Optional.of(getMoveIndex(currentMove));

    }

    @Override
    public boolean isOver() {
        // Calculate the total number of positions on the game board.
        var rowColNumber = (int) Math.sqrt(gameBoard.size());
        int totalPositions = rowColNumber * rowColNumber;

        // Check if all positions have been played or no legal moves are left.
        return playedPositions.size() == totalPositions || isGameOver;
    }

    @Override
    public void addToGrid(int index, int x, int y) {
        gameBoard.put(index, new Pair<>(x, y));
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
        return playedPositions.contains(move);
    }

    private boolean isInBounds(Pair<Integer, Integer> move) {
        var rowColNumber = (int) Math.sqrt(gameBoard.size());
        return move.getX() < rowColNumber && move.getY() < rowColNumber &&
                move.getX() >= 0 && move.getY() >= 0;
    }

    // Returns the key (index) of the given value (move).
    private int getMoveIndex(Pair<Integer, Integer> move) {
        return gameBoard.entrySet().stream()
                .filter(entry -> entry.getValue().equals(move))
                .mapToInt(Map.Entry::getKey)
                .findFirst()
                .getAsInt();
    }

    private Pair<Integer, Integer> getRandomMove(Pair<Integer, Integer> previousMove) {
        var max = 2;
        var min = -1;
        var result = new Pair<>(previousMove.getX() + rand.nextInt(max - min) + min,
                previousMove.getY() + rand.nextInt(max - min) + min);

        // TODO: this is not very efficient.
        var counter = 0;
        var limit = 50;
        while (!isInBounds(result) || !isAdjacent(previousMove, result) || isPlayed(result)) {
            if (counter == limit) {
                isGameOver = true;
                break;
            }
            result = new Pair<>(previousMove.getX() + rand.nextInt(max - min) + min,
                    previousMove.getY() + rand.nextInt(max - min) + min);
            counter++;
        }

        return result;
    }

}

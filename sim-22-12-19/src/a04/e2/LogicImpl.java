package a04.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LogicImpl implements Logic {

    private Optional<Pair<Integer, Integer>> previousMove = Optional.empty();
    private Map<Pair<Integer, Integer>, Boolean> grid = new HashMap<>(); 

    @Override
    public boolean isValidMove(int x, int y) {
        var currentMove = new Pair<Integer, Integer>(x, y);
        System.out.println("Move: " + currentMove.getX() + ", " + currentMove.getY());

        // First time.
        if (previousMove.isEmpty()) {
            previousMove = Optional.ofNullable(currentMove);
            addToGrid(previousMove.get(), true);
            return true;
        }

        if (isAdjacent(currentMove, previousMove.get())) {
            previousMove = Optional.ofNullable(currentMove);
            addToGrid(previousMove.get(), true);
            return true;
        }

        return false;
    }

    // This can be improved.
    @Override
    public boolean isOver() {
        var noMovesLeft = true;
        var moveX = previousMove.get().getX();
        var moveY = previousMove.get().getY();

        /*
         * Getting the number of rows and columns.
         * We assume the grid will be a square.
         * This is not optimal.
         */
        var numRows = Math.sqrt(grid.size());
        var numColumns = numRows;

        // Iterate over the eight possible positions around the previous move
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                
                // Skip the current position
                if (i == 0 && j == 0) {
                    continue;
                }

                // Check if the position is within the boundaries of the grid and its not selected yet.
                if (moveX + i >= 0 && moveX + i < numRows && moveY + j >= 0 && moveY + j < numColumns
                        && grid.get(new Pair<>(moveX + i, moveY + j)) == false) {
                    noMovesLeft = false;
                    break;
                }
            }
        }

        return noMovesLeft;
    }

    @Override
    public void addToGrid(int x, int y, boolean selected) {
        grid.put(new Pair<>(x, y), selected);
    }

    public void addToGrid(Pair<Integer, Integer> move, boolean selected) {
        grid.put(move, selected);
    }

    // Using the Pythagorean Theorem to check adjacency.
    private boolean isAdjacent(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
        var rowDifference = p1.getX() - p2.getX();
        var colDifference = p1.getY() - p2.getY();
        var distance = Math.sqrt(rowDifference * rowDifference + colDifference * colDifference);

        // First check for non-diagonal adjacency, then for diagonal.
        return Double.compare(distance, 1) == 0 || Double.compare(distance, Math.sqrt(2)) == 0;
    }

}

package a01a.e2;

import java.util.List;

public interface Logic {

    /**
     * This method is used to keep track of the
     * played moves.
     * 
     * @param x the move x coordinate.
     * @param y the move y coordinate.
     */
    void registerMove(int x, int y);

    /**
     * This method computes the coordinates of the
     * game board cells that are contained in the
     * requested rectangle.
     * 
     * @return a list of {@link Pair}<{@link Integer}>,
     *         representing the coordinates.
     *         An empty list if not enough moves are
     *         present.
     */
    List<Pair<Integer, Integer>> computeRectangle();
    
    /**
     * The game end.
     * 
     * @return {@code true} if the game has reached
     *         its ending condition.
     */
    boolean isOver();

    /**
     * Adds to the game board all the possible
     * playable moves.
     * 
     * @param x the move x coordinate.
     * @param y the move y coordinate.
     * @param played the move status (played/unplayed).
     */
    void addToGameBoard(int x, int y, boolean played);

}

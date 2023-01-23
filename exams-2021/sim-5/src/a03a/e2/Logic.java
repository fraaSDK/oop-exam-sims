package a03a.e2;

import java.util.List;

public interface Logic {

    /**
     * @param move a Pair containing the move's
     *        coordinates.
     * @return {@code true} if successful,
     *         {@code false} otherwise.
     */
    boolean registerMove(Pair<Integer, Integer> move);

    /**
     * @return a {@link List} of {@link Pair} that
     *         represents the rectangle's coordinates.
     */
    List<Pair<Integer, Integer>> getRectangle();

    /**
     * @return {@code true} if the game-over
     *         condition was met.
     */
    boolean isOver();

}

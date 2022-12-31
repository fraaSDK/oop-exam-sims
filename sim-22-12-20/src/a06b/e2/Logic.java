package a06b.e2;

import java.util.Optional;

public interface Logic {
    
    /**
     * @return the index of the button in the button list.
     */
    Optional<Integer> getNextMove();

    boolean isOver();

    void addToGrid(int index, int x, int y);
    
}

package a01c.e2;

import java.util.List;
import java.util.Optional;

public interface Logic {
    
    void registerMove(int x, int y);

    Optional<List<Pair<Integer, Integer>>> toDisplay();
    
    boolean isValid();

}

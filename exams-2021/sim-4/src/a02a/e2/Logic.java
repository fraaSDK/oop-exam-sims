package a02a.e2;

import java.util.Optional;

public interface Logic {
    
    Optional<Pair<Integer, Pair<Integer,Integer>>> makeMove();

    boolean isOver();

}

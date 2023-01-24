package a04.e2;

import java.util.Map;

public interface Logic {

    Map<Pair<Integer, Integer>, String> initGrid();

    boolean registerAction(Pair<Integer, Integer> action, String content);
    
    int computeResult();

}

package a01b.e1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Stream;

/*
 * In 45 minutes: sumEach(), flattenAll().
 */
public class FlattenerFactoryImpl implements FlattenerFactory {

    @Override
    public Flattener<Integer, Integer> sumEach() {
        return (list) -> list.stream().map(l -> l.stream().reduce(0, (a, b) -> a + b)).toList();
    }

    @Override
    public <X> Flattener<X, X> flattenAll() {
        return new Flattener<>() {

            @Override
            public List<X> flatten(List<List<X>> list) {
                List<X> result = new ArrayList<>();
                for (var l : list) {
                    for (var x : l) {
                        result.add(x);
                    }
                }
                return result;
            }
            
        };
    }

    @Override
    public Flattener<String, String> concatPairs() {
        return new Flattener<>() {

            @Override
            public List<String> flatten(List<List<String>> list) {
                Queue<List<String>> queue = new LinkedList<>(list);
                List<String> result = new ArrayList<>();
                int j = 0;
                for (int i = 0; i < list.size(); i++) {
                    j++;
                    if (j == 2) {
                        var l1 = queue.poll();
                        var l2 = queue.poll();
                        var l = Stream.concat(l1.stream(), l2.stream()).toList();
                        result.add(String.join("", l));
                        j = 0;
                    }
                }

                if (!queue.isEmpty()) {
                    var l = queue.poll();
                    result.add(String.join("", l));
                }
                
                return result;
            }
            
        };
    }

    @Override
    public <I, O> Flattener<I, O> each(Function<List<I>, O> mapper) {
        return (list) -> list.stream().map(l -> mapper.apply(l)).toList();
    }

    @Override
    public Flattener<Integer, Integer> sumVectors() {
        return new Flattener<Integer, Integer>() {

            @Override
            public List<Integer> flatten(List<List<Integer>> list) {
                Queue<List<Integer>> queue = new LinkedList<>(list);
                Map<Integer, Integer> sumMap = new HashMap<>();
                int i = 0;
                while (!queue.isEmpty()) {
                    var l = queue.poll();
                    while (i < l.size()) {
                        sumMap.merge(i, l.get(i), (a, b) -> a + b);
                        i++;
                    }
                    i = 0;
                }
                
                return sumMap.values().stream().toList();
            }
            
        };
    }

}

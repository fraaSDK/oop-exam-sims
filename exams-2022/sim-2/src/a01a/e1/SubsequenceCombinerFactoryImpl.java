package a01a.e1;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;

/*
 * 45 minutes: tripletsToSum(), tripletsToList(), singleReplacer(),
 * countUntilZero() a little over deadline (+ ~1min).
 */
public class SubsequenceCombinerFactoryImpl implements SubsequenceCombinerFactory {

    @Override
    public SubsequenceCombiner<Integer, Integer> tripletsToSum() {
        return new SubsequenceCombiner<>() {

            @Override
            public List<Integer> combine(List<Integer> list) {
                Queue<Integer> queue = new LinkedList<>(list);
                List<Integer> result = new ArrayList<>();
                while (queue.size() >= 3) {
                    result.add(queue.poll() + queue.poll() + queue.poll());
                }
                if (!queue.isEmpty()) {
                    int last = 0;
                    while (!queue.isEmpty()) {
                        last += queue.poll();
                    }
                    result.add(last);
                }
                return result;
            }
            
        };
    }

    @Override
    public <X> SubsequenceCombiner<X, List<X>> tripletsToList() {
        return new SubsequenceCombiner<>() {

            @Override
            public List<List<X>> combine(List<X> list) {
                Queue<X> queue = new LinkedList<>(list);
                List<List<X>> result = new ArrayList<>();
                while (queue.size() >= 3) {
                    result.add(List.of(queue.poll(), queue.poll(), queue.poll()));
                }
                if (!queue.isEmpty()) {
                    List<X> last = new ArrayList<>();
                    while (!queue.isEmpty()) {
                        last.add(queue.poll());
                    }
                    result.add(last);
                }
                return result;
            }
            
        };
    }

    @Override
    public SubsequenceCombiner<Integer, Integer> countUntilZero() {
        return new SubsequenceCombiner<>() {

            @Override
            public List<Integer> combine(List<Integer> list) {
                Deque<Integer> queue = new LinkedList<>(list);
                List<Integer> result = new ArrayList<>();
                int counter = 0;
                if (queue.peekLast() != 0) {
                    queue.addLast(0);;
                }
                while (!queue.isEmpty()) {
                    var n = queue.poll();
                    if (n == 0) {
                        result.add(counter);
                        counter = 0;
                    } else {
                        counter++;
                    }
                }
                if (!queue.isEmpty()) {
                    int sum = 0;
                    while (!queue.isEmpty()) {
                        sum += queue.poll();
                    }
                    result.add(sum);
                }

                return result;
            }
            
        };
    }

    @Override
    public <X, Y> SubsequenceCombiner<X, Y> singleReplacer(Function<X, Y> function) {
        return (list) -> list.stream().map(l -> function.apply(l)).toList();
    }

    @Override
    public SubsequenceCombiner<Integer, List<Integer>> cumulateToList(int threshold) {
        return new SubsequenceCombiner<>() {

            @Override
            public List<List<Integer>> combine(List<Integer> list) {
                List<List<Integer>> result = new ArrayList<>();
                List<Integer> tempList = new ArrayList<>();
                int currentSize = 0;

                for (var n : list) {
                    if (tempList.stream().reduce(0, (a, b) -> a + b) >= threshold) {
                        result.add(new ArrayList<>(tempList));
                        currentSize += tempList.size();
                        tempList.clear();
                    }
                    tempList.add(n);
                }
                if (currentSize < list.size()) {
                    result.add(list.subList(currentSize, list.size()));
                }

                return result;
            }
            
        };
    }

}

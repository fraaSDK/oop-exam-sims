package a04.e1;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class IntegerIteratorsFactoryImpl implements IntegerIteratorsFactory {

    private int index;
    private Optional<Integer> currentElement = Optional.empty();
    private Random rand = new Random();
    private int currentNumber;
    
    @Override
    public SimpleIterator<Integer> empty() {
        return () -> Optional.empty();
    }

    @Override
    public SimpleIterator<Integer> fromList(List<Integer> list) {
        return () -> {
            if (currentElement.isEmpty()) {
                currentElement = Optional.ofNullable(list.get(index++));
                return currentElement;
            }

            if (index < list.size()) {
                currentElement = Optional.ofNullable(list.get(index++));
                return currentElement;
            }

            return Optional.empty();
        };
    }

    @Override
    public SimpleIterator<Integer> random(int size) {
        currentNumber = 0;
        return () -> {
            if (currentNumber == size) {
                return Optional.empty();
            }
            currentNumber++;
            return Optional.ofNullable(rand.nextInt(size));
        };
    }

    @Override
    public SimpleIterator<Integer> quadratic() {
        currentElement = Optional.of(1);
        currentNumber = 0;
        return () -> {
            /*
             * Incrementing if it the current number
             * was returned currentElement times.
             */
            if (currentNumber == currentElement.get()) {
                currentElement = Optional.of(currentElement.get() + 1);
                currentNumber = 0;
            }
            currentNumber++;
            return currentElement;
        };
    }

    @Override
    public SimpleIterator<Integer> recurring() {
        return new SimpleIterator<Integer>() {
            private int value;
            private int limit;

            @Override
            public Optional<Integer> next() {
                if (value < limit + 1) {
                    return Optional.of(value++);
                } else {
                    limit++;
                    value = 0;
                    return Optional.of(value++);
                }
            }
            
        };
    }
    
}

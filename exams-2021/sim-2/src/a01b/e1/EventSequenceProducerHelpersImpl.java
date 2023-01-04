package a01b.e1;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Summary:
 * 51 minutes with only Javadoc. (First commit of the file). Failed bonus deadline (45 minutes).
 */
public class EventSequenceProducerHelpersImpl implements EventSequenceProducerHelpers {

    @Override
    public <E> EventSequenceProducer<E> fromIterator(Iterator<Pair<Double, E>> iterator) {
        return new EventSequenceProducer<E>() {

            @Override
            public Pair<Double, E> getNext() throws NoSuchElementException {
                if (!iterator.hasNext()) {
                    throw new NoSuchElementException();
                }

                while (iterator.hasNext()) {
                    return iterator.next();
                }

                return null;
            }
            
        };
    }

    @Override
    public <E> List<E> window(EventSequenceProducer<E> sequence, double fromTime, double toTime) {
        // TODO
        return null;
    }

    @Override
    public <E> Iterable<E> asEventContentIterable(EventSequenceProducer<E> sequence) {
        return () -> {
            return new Iterator<E>() {

                @Override
                public boolean hasNext() {
                    // TODO
                    return false;
                }

                @Override
                public E next() {
                    return sequence.getNext().get2();
                }
                
            };
        };
    }

    @Override
    public <E> Optional<Pair<Double, E>> nextAt(EventSequenceProducer<E> sequence, double time) {
        var value = sequence.getNext();
        System.out.println(value);
        while (Long.compare(Math.round(value.get1()), Math.round(time)) != 0) {
            try {
                value = sequence.getNext();
            } catch (NoSuchElementException e) {
                return Optional.empty();
            }
        }

        return Optional.of(value);
    }

    @Override
    public <E> EventSequenceProducer<E> filter(EventSequenceProducer<E> sequence, Predicate<E> predicate) {
        // TODO
        if (predicate.test(sequence.getNext().get2())) {
            return sequence;
        }

        return null;
    }

}

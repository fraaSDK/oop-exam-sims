package a01a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AcceptorFactoryImpl implements AcceptorFactory {

    @Override
    public Acceptor<String, Integer> countEmptyStringsOnAnySequence() {
        return new Acceptor<String,Integer>() {

            private int emptyStrings;

            @Override
            public boolean accept(String e) {
                if (e.isEmpty()) {
                    emptyStrings++;
                }
                return true;
            }

            @Override
            public Optional<Integer> end() {
                return Optional.of(emptyStrings);
            }
            
        };
    }

    @Override
    public Acceptor<Integer, String> showAsStringOnlyOnIncreasingSequences() {
        return new Acceptor<Integer,String>() {

            private List<Integer> sequence = new ArrayList<>();
            private int accepted;
            private int total;

            @Override
            public boolean accept(Integer e) {
                total++;

                // First time: accept any integer.
                if (sequence.isEmpty()) {
                    sequence.add(e);
                    accepted++;
                    return true;
                }

                if (e > sequence.get(sequence.size() - 1)) {
                    sequence.add(e);
                    accepted++;
                    return true;
                }

                return false;
            }

            @Override
            public Optional<String> end() {
                if (accepted < total) {
                    return Optional.empty();
                }
                var builder = new StringBuilder();
                sequence.forEach(i -> builder.append(i + ":"));
                return Optional.of(builder.deleteCharAt(builder.length() - 1).toString());
            }
            
        };
    }

    @Override
    public Acceptor<Integer, Integer> sumElementsOnlyInTriples() {
        return new Acceptor<Integer,Integer>() {

            private static int MAX_ELEMENTS = 3;
            private List<Integer> sequence = new ArrayList<>();
            private int total;

            @Override
            public boolean accept(Integer e) {
                sequence.add(e);
                total++;
                return total <= MAX_ELEMENTS;
            }

            @Override
            public Optional<Integer> end() {
                var sum = sequence.stream().mapToInt(i -> i).sum();
                return total == MAX_ELEMENTS ? Optional.of(sum) : Optional.empty();
            }
            
        };
    }

    @Override
    public <E, O1, O2> Acceptor<E, Pair<O1, O2>> acceptBoth(Acceptor<E, O1> a1, Acceptor<E, O2> a2) {
        return new Acceptor<E,Pair<O1,O2>>() {

            @Override
            public boolean accept(E e) {
                return a1.accept(e) && a2.accept(e);
            }

            @Override
            public Optional<Pair<O1, O2>> end() {
                var res1 = a1.end();
                var res2 = a2.end();

                if (res1.isPresent() && res2.isPresent()) {
                    return Optional.of(new Pair<>(res1.get(), res2.get()));
                }

                return Optional.empty();
            }

        };
    }

    @Override
    public <E, O, S> Acceptor<E, O> generalised(S initial, BiFunction<E, S, Optional<S>> stateFun,
            Function<S, Optional<O>> outputFun) {
        return new Acceptor<E,O>() {

            private Optional<S> state = Optional.of(initial);

            @Override
            public boolean accept(E e) {
                if (state.isEmpty()) {
                    return false;
                }
                state = stateFun.apply(e, state.get());
                return state.isPresent();
            }

            @Override
            public Optional<O> end() {
                if (state.isEmpty()) {
                    return Optional.empty();
                }
                return outputFun.apply(state.get());
            }
            
        };
    }
    
}

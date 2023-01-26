package a05.e1;

import java.util.Iterator;
import java.util.function.Function;

/*
 * in 45 minutes: fromFunction() (no map()), incSquareHalve();
 */
public class StateFactoryImpl implements StateFactory {

    @Override
    public <S, A> State<S, A> fromFunction(Function<S, Pair<S, A>> fun) {
        return new State<S,A>() {

            @Override
            public S nextState(S s) {
                return fun.apply(s).get1();
            }

            @Override
            public A value(S s) {
                return fun.apply(s).get2();
            }

            @Override
            public <B> State<S, B> map(Function<A, B> fun) {
                return fromFunction(f -> new Pair<>(nextState(f), fun.apply(value(f))));
            }

            @Override
            public Iterator<A> iterator(S s0) {
                return new Iterator<A>() {

                    private A currentValue;
                    private S currentState;

                    @Override
                    public boolean hasNext() {
                        return true;
                    }

                    @Override
                    public A next() {
                        if (currentValue == null) {
                            currentState = s0;
                            currentValue = value(s0);
                            return currentValue;
                        } else {
                            currentState = nextState(currentState);
                            currentValue = value(currentState);
                            return currentValue;
                        }
                    }
                    
                };
            }
            
        };
    }

    @Override
    public <S, A, B> State<S, B> compose(State<S, A> state1, State<S, B> state2) {
        return fromFunction(s -> {
            var element1 = new Pair<>(state1.nextState(s), state1.value(s));
            var element2 = new Pair<>(state2.nextState(element1.get1()), state2.value(element1.get1()));
            return new Pair<>(element2.get1(), element2.get2());
        });
    }

    @Override
    public State<Integer, String> incSquareHalve() {
        return new State<Integer,String>() {

            private int currentValue = 0;

            @Override
            public Integer nextState(Integer s) {
                return null;
            }

            @Override
            public String value(Integer s) {
                return Integer.toString(s);
            }

            @Override
            public <B> State<Integer, B> map(Function<String, B> fun) {
                return null;
            }

            @Override
            public Iterator<String> iterator(Integer s0) {
                currentValue = s0;
                return new Iterator<String>() {

                    @Override
                    public boolean hasNext() {
                        return true;
                    }

                    @Override
                    public String next() {
                        currentValue++;
                        currentValue *= currentValue;
                        currentValue /= 2;
                        return Integer.toString(currentValue);
                    }
                    
                };
            }
            
        };
    }

    @Override
    public State<Integer, Integer> counterOp(CounterOp op) {
        return fromFunction(s -> {
            return switch (op) {
                case INC -> new Pair<>(s + 1, null);
                case GET -> new Pair<>(s, s);
                case RESET -> new Pair<>(0, null);
            };
        });
    }

}

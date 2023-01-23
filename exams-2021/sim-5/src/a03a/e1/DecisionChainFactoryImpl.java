package a03a.e1;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

public class DecisionChainFactoryImpl implements DecisionChainFactory {

    @Override
    public <A, B> DecisionChain<A, B> oneResult(B b) {
        return new DecisionChain<>() {

            @Override
            public Optional<B> result(A a) {
                return Optional.of(b);
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                throw new NoSuchElementException();
            }
            
        };
    }

    @Override
    public <A, B> DecisionChain<A, B> simpleTwoWay(Predicate<A> predicate, B positive, B negative) {
        return twoWay(predicate, oneResult(positive), oneResult(negative));
    }

    @Override
    public <A, B> DecisionChain<A, B> enumerationLike(List<Pair<A, B>> mapList, B defaultReply) {
        /*
         * Creating a new list of (Predicate, B) pairs.
         * The condition of the predicate is t(1...n) == A(1...n)
         * and must be true to continue. If false it delegates the
         * decision to another decider.
         */
        var list = mapList.stream()
                .map(p -> new Pair<Predicate<A>, B>(t -> t.equals(p.get1()), p.get2()))
                .toList();
        return switchChain(list, defaultReply);
    }

    @Override
    public <A, B> DecisionChain<A, B> twoWay(Predicate<A> predicate, DecisionChain<A, B> positive,
            DecisionChain<A, B> negative) {
        return new DecisionChain<>() {

            @Override
            public Optional<B> result(A a) {
                return Optional.empty();
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                return predicate.test(a) ? positive : negative;
            }
            
        };
    }

    @Override
    public <A, B> DecisionChain<A, B> switchChain(List<Pair<Predicate<A>, B>> cases, B defaultReply) {
        return new DecisionChain<>() {

            @Override
            public Optional<B> result(A a) {
                if (cases.isEmpty()) {
                    return Optional.of(defaultReply);
                }
                if (cases.get(0).get1().test(a)) {
                    return Optional.of(cases.get(0).get2());
                }
                return Optional.empty();
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                /*
                 * Removing the head of the list because if
                 * this method was called, then the first
                 * element passed the predicate's test and
                 * needs to be removed to proceed.
                 */
                var list = new LinkedList<>(cases);
                list.poll();
                return switchChain(list, defaultReply);
            }
            
        };
    }

}

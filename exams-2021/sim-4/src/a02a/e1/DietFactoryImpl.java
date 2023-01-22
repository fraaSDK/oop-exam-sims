package a02a.e1;

import java.util.Set;
import java.util.function.Predicate;

public class DietFactoryImpl implements DietFactory {

    @Override
    public Diet standard() {
        return new AbstractDiet() {

            @Override
            protected Set<Pair<Predicate<Nutrient>, Predicate<Double>>> addLimitations() {
                return Set.<Pair<Predicate<Nutrient>, Predicate<Double>>>of(
                        new Pair<>(n -> true, c -> c >= 1500 && c <= 2000)
                );
            }
            
        };
    }

    @Override
    public Diet lowCarb() {
        return new AbstractDiet() {

            @Override
            protected Set<Pair<Predicate<Nutrient>, Predicate<Double>>> addLimitations() {
                return Set.<Pair<Predicate<Nutrient>, Predicate<Double>>>of(
                        new Pair<>(n -> true, c -> c >= 1000 && c <= 1500),
                        new Pair<>(n -> n == Nutrient.CARBS, c -> c <= 300)
                );
            }
            
        };
    }

    @Override
    public Diet highProtein() {
        return new AbstractDiet() {

            @Override
            protected Set<Pair<Predicate<Nutrient>, Predicate<Double>>> addLimitations() {
                return Set.<Pair<Predicate<Nutrient>, Predicate<Double>>>of(
                        new Pair<>(n -> true, c -> c >= 2000 && c <= 2500),
                        new Pair<>(n -> n == Nutrient.CARBS, c -> c <= 300),
                        new Pair<>(n -> n == Nutrient.PROTEINS, c -> c >= 1300)
                );
            }
            
        };
    }

    @Override
    public Diet balanced() {
        return new AbstractDiet() {

            @Override
            protected Set<Pair<Predicate<Nutrient>, Predicate<Double>>> addLimitations() {
                return Set.<Pair<Predicate<Nutrient>, Predicate<Double>>>of(
                        new Pair<>(n -> true, c -> c >= 1600 && c <= 2000),
                        new Pair<>(n -> n == Nutrient.CARBS, c -> c >= 600),
                        new Pair<>(n -> n == Nutrient.PROTEINS, c -> c >= 600),
                        new Pair<>(n -> n == Nutrient.FAT, c -> c >= 400),
                        new Pair<>(n -> n == Nutrient.FAT && n == Nutrient.PROTEINS, c -> c <= 1100)
                );
            }
            
        };
    }
    
}

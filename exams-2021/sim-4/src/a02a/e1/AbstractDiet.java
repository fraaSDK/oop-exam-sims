package a02a.e1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractDiet implements Diet {

    private Map<String, Map<Nutrient, Integer>> foods = new HashMap<>();

    @Override
    public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
        foods.put(name, nutritionMap);
    }

    @Override
    public boolean isValid(Map<String, Double> dietMap) {
        // Filtering the food map to keep only the items present in the diet map.
        var filteredFoods = foods.entrySet().stream()
                .filter(e -> dietMap.containsKey(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        /*
         * Copying (Map.of() creates an unmodifiable map) and
         * computing the amount to multiply by replacing all the values.
         */
        Map<String, Double> dietCopy = new HashMap<>(dietMap);
        dietCopy.replaceAll((k, v) -> v / 100.0);

        Map<Nutrient, Double> totalCalories = new HashMap<>();
        for (var f1 : dietCopy.entrySet()) {
            for (var f2 : filteredFoods.get(f1.getKey()).entrySet()) {
                /*
                 * If the key is not present in the map, the value gets inserted.
                 * If already present, the remapping function is performed over
                 * the old value and the current given value.
                 */
                totalCalories.merge(f2.getKey(), f2.getValue() * f1.getValue(), Double::sum);
            }
        }

        return addLimitations().stream().allMatch(l -> isValid(l, totalCalories));
    }

    /*
     * Outer test(): testing if the sum of the calories is within range.
     * Inner test(): used as filter for the allowed nutrients.
     */
    private boolean isValid(Pair<Predicate<Nutrient>, Predicate<Double>> limit, Map<Nutrient, Double> calories) {
        return limit.get2().test(calories.entrySet().stream()
                .filter(e -> limit.get1().test(e.getKey()))
                .mapToDouble(Map.Entry::getValue)
                .sum()
        );
    }

    /**
     * Allows to add limitations on the given {@link Nutrient}s and their calories
     * amount.
     * 
     * @return a Set of {@link Pair}(s) containing two Predicates (for the
     *         {@link Nutrient} and Calories).
     */
    protected abstract Set<Pair<Predicate<Nutrient>, Predicate<Double>>> addLimitations();

}

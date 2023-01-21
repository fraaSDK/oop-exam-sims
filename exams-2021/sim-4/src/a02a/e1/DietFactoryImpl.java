package a02a.e1;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DietFactoryImpl implements DietFactory {

    @Override
    public Diet standard() {
        return new Diet() {

            private static final int MIN_CALORIES = 1500;
            private static final int MAX_CALORIES = 2000;
            private Map<String, Map<Nutrient, Integer>> foods = new HashMap<>();

            @Override
            public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
                foods.put(name, nutritionMap);
            }

            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                var filteredFoods = foods.entrySet().stream()
                        .filter(e -> dietMap.containsKey(e.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                Map<String, Double> dietCopy = new HashMap<>(dietMap);
                dietCopy.replaceAll((k, v) -> v / 100.0);

                double totalCalories = 0;
                for (var f1 : dietCopy.entrySet()) {
                    for (var f2 : filteredFoods.entrySet()) {
                        if (f1.getKey().equals(f2.getKey())) {
                            var calories = f2.getValue().values().stream().reduce(0, Integer::sum);
                            totalCalories += calories * f1.getValue();
                        }
                    }
                }

                return totalCalories >= MIN_CALORIES && totalCalories <= MAX_CALORIES;
            }
            
        };
    }

    @Override
    public Diet lowCarb() {
        // TODO
        return null;
    }

    @Override
    public Diet highProtein() {
        // TODO
        return null;
    }

    @Override
    public Diet balanced() {
        // TODO
        return null;
    }
    
}

package a02a.e1;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import a02a.e1.Diet.Nutrient;

public class DietFactoryImpl implements DietFactory {

    private Diet ofCondition(final Predicate<Map<Nutrient, Double>> condition) {
        return new Diet() {
            final Map<String, Map<Nutrient, Integer>> foods = new HashMap<>();
            @Override
            public void addFood(final String name, final Map<Nutrient, Integer> nutritionMap) {
                foods.put(name, nutritionMap);
            }

            @Override
            public boolean isValid(final Map<String, Double> dietMap) {
                final var totalCalories = Stream.of(Nutrient.values()).collect(Collectors.toMap(Function.identity(), x -> 0.0));
                dietMap.entrySet().stream()
                    .map(
                        e -> foods.get(e.getKey()).entrySet().stream()
                                .map(ee -> Map.entry(ee.getKey(), (double) e.getValue() * (ee.getValue() / 100.0)))
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                    ).forEach(foodNutrients -> foodNutrients.forEach((n, c) -> {
                        totalCalories.put(n, totalCalories.get(n) + c);
                    }));
                return condition.test(totalCalories);
            }
            
        };
    }

    private Double totalSum(final Map<?, Double> map) {
        return map.values().stream().collect(Collectors.summingDouble(x -> x));
    }

    private boolean inRange(final double x, final double min, final double max) {
        return x >= min && x <= max;
    }

    @Override
    public Diet standard() {
        return ofCondition(calories -> inRange(totalSum(calories), 1500, 2000));
    }

    @Override
    public Diet lowCarb() {
        return ofCondition(calories -> inRange(totalSum(calories), 1000, 1500) && calories.get(Nutrient.CARBS) <= 300);
    }

    @Override
    public Diet highProtein() {
        return ofCondition(
            calories -> inRange(totalSum(calories), 2000, 2500) && 
                calories.get(Nutrient.CARBS) <= 300 &&
                calories.get(Nutrient.PROTEINS) >= 1300
        );
    }

    @Override
    public Diet balanced() {
        return ofCondition(
            calories -> inRange(totalSum(calories), 1600, 2000) && 
                calories.get(Nutrient.CARBS) >= 600 &&
                calories.get(Nutrient.PROTEINS) >= 600 &&
                calories.get(Nutrient.FAT) >= 400 &&
                calories.get(Nutrient.FAT) + calories.get(Nutrient.PROTEINS) <= 1100
        );
    }

}

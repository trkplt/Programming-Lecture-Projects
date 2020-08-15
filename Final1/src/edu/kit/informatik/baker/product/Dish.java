package edu.kit.informatik.baker.product;

import edu.kit.informatik.baker.ui.Main;

import java.util.HashMap;
import java.util.StringJoiner;

public enum Dish {

    YOGHURT("yoghurt", 0, 0, 3, 8),
    MERINGUE("meringue", 0, 3, 0, 9),
    BREAD("bread", 3, 0, 0, 10),
    BUN("bun", 2, 0, 1, 11),
    CREPE("crepe", 1, 2, 0, 12),
    PUDDING("pudding", 0, 1, 2, 13),
    CAKE("cake", 2, 2, 2, 22);

    private final String name;
    private final HashMap<RawMaterial, Integer> reqRawMaterials;
    private final int profit;

    Dish(String name, int flour, int egg, int milk, int profit) {
        this.name = name;
        this.reqRawMaterials = new HashMap<>();
        this.profit = profit;

        this.reqRawMaterials.put(RawMaterial.FLOUR, flour);
        this.reqRawMaterials.put(RawMaterial.EGG, egg);
        this.reqRawMaterials.put(RawMaterial.MILK, milk);
    }

    public String getName() {
        return this.name;
    }

    public int getReqRawMaterial(RawMaterial rawMaterial) {
        return this.reqRawMaterials.get(rawMaterial);
    }

    public int getProfit() {
        return this.profit;
    }

    public static String getPattern() {
        StringJoiner pattern = new StringJoiner(Main.PATTERN_OR, Main.PATTERN_GROUP_INIT, Main.PATTERN_GROUP_END);

        for (Dish dish : Dish.values()) {
            pattern.add(dish.getName());
        }
        return pattern.toString();
    }

    public static Dish getDish(String input) {
        for (Dish dish : Dish.values()) {
            if (input.equals(dish.getName())) {
                return dish;
            }
        }
        return null;
    }
}

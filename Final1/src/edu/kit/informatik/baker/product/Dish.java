package edu.kit.informatik.baker.product;

import edu.kit.informatik.baker.ui.Main;

import java.util.HashMap;
import java.util.StringJoiner;

/**
 * This enum contains the dishes in a baker game. It also encapsulates information about these dishes and provides
 * some useful operations on them.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public enum Dish {

    /**
     * This is a {@link edu.kit.informatik.baker.product.Dish} with the name "yoghurt". Call
     * {@link #getReqRawMaterial(RawMaterial)} to see the required raw materials to prepare this dish.
     */
    YOGHURT("yoghurt", 0, 0, 3, 8),

    /**
     * This is a {@link edu.kit.informatik.baker.product.Dish} with the name "meringue". Call
     * {@link #getReqRawMaterial(RawMaterial)} to see the required raw materials to prepare this dish.
     */
    MERINGUE("meringue", 0, 3, 0, 9),

    /**
     * This is a {@link edu.kit.informatik.baker.product.Dish} with the name "bread". Call
     * {@link #getReqRawMaterial(RawMaterial)} to see the required raw materials to prepare this dish.
     */
    BREAD("bread", 3, 0, 0, 10),

    /**
     * This is a {@link edu.kit.informatik.baker.product.Dish} with the name "bun". Call
     * {@link #getReqRawMaterial(RawMaterial)} to see the required raw materials to prepare this dish.
     */
    BUN("bun", 2, 0, 1, 11),

    /**
     * This is a {@link edu.kit.informatik.baker.product.Dish} with the name "crepe". Call
     * {@link #getReqRawMaterial(RawMaterial)} to see the required raw materials to prepare this dish.
     */
    CREPE("crepe", 1, 2, 0, 12),

    /**
     * This is a {@link edu.kit.informatik.baker.product.Dish} with the name "pudding". Call
     * {@link #getReqRawMaterial(RawMaterial)} to see the required raw materials to prepare this dish.
     */
    PUDDING("pudding", 0, 1, 2, 13),

    /**
     * This is a {@link edu.kit.informatik.baker.product.Dish} with the name "cake". Call
     * {@link #getReqRawMaterial(RawMaterial)} to see the required raw materials to prepare this dish.
     */
    CAKE("cake", 2, 2, 2, 22);

    private final String name;
    private final HashMap<RawMaterial, Integer> reqRawMaterials;
    private final int profit;

    /**
     * This constructor creates a {@link edu.kit.informatik.baker.product.Dish} with the given name and
     * {@link edu.kit.informatik.baker.product.RawMaterial} amounts.
     *
     * @param name is a String containing the name of this dish
     * @param flour is an integer representing the amount of needed
     *              {@link edu.kit.informatik.baker.product.RawMaterial#FLOUR} to prepare this dish
     * @param egg is an integer representing the amount of needed
     *            {@link edu.kit.informatik.baker.product.RawMaterial#EGG} to prepare this dish
     * @param milk is an integer representing the amount of needed
     *             {@link edu.kit.informatik.baker.product.RawMaterial#MILK} to prepare this dish
     * @param profit is an integer representing the profit a player can make if he prepares this dish
     */
    Dish(String name, int flour, int egg, int milk, int profit) {
        this.name = name;
        this.reqRawMaterials = new HashMap<>();
        this.profit = profit;

        this.reqRawMaterials.put(RawMaterial.FLOUR, flour);
        this.reqRawMaterials.put(RawMaterial.EGG, egg);
        this.reqRawMaterials.put(RawMaterial.MILK, milk);
    }

    /**
     * This method provides the name of this {@link edu.kit.informatik.baker.product.Dish}.
     *
     * @return a String containing the name of this dish
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method provides the amount of required {@link edu.kit.informatik.baker.product.RawMaterial} (given as a
     * parameter) to be able to prepare this dish.
     *
     * @param rawMaterial is the raw material to gather required amount of
     * @return an integer representing the amount of raw material needed
     */
    public int getReqRawMaterial(RawMaterial rawMaterial) {
        return this.reqRawMaterials.get(rawMaterial);
    }

    /**
     * This method provides the amount of gold a player acquires when he prepares this
     * {@link edu.kit.informatik.baker.product.Dish}.
     *
     * @return an integer representing the profit of this dish
     */
    public int getProfit() {
        return this.profit;
    }

    /**
     * This method provides the pattern for {@link edu.kit.informatik.baker.product.Dish}es in (x|y|...) format where
     * x and y are the names of dishes.
     *
     * @return a String containing the pattern of dishes
     */
    public static String getPattern() {
        StringJoiner pattern = new StringJoiner(Main.PATTERN_OR, Main.PATTERN_GROUP_INIT, Main.PATTERN_GROUP_END);

        for (Dish dish : Dish.values()) {
            pattern.add(dish.getName());
        }
        return pattern.toString();
    }

    /**
     * This method provides the wanted {@link edu.kit.informatik.baker.product.Dish} according to the given name.
     *
     * @param name is a String containing the name of the wanted dish
     * @return the matched dish or null if there was no match
     */
    public static Dish getDish(String name) {
        for (Dish dish : Dish.values()) {
            if (name.equals(dish.getName())) {
                return dish;
            }
        }
        return null;
    }
}

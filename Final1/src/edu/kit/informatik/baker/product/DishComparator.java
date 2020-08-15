package edu.kit.informatik.baker.product;

import edu.kit.informatik.baker.ui.Main;

import java.util.Comparator;
import java.util.Random;

public class DishComparator implements Comparator<Dish> {

    private static final int GREATER = Main.ONE;
    private static final int LESSER = Main.MINUS_ONE;
    private static final int NEUTRAL = Main.ZERO;

    @Override
    public int compare(Dish first, Dish second) {
        int value;

        if (first == second) {
            value = NEUTRAL;
        } else if (first.getProfit() < second.getProfit()) {
            value = LESSER;
        } else if (first.getProfit() > second.getProfit()) {
            value = GREATER;
        } else {
            value = new Random().nextInt();
            value = value == NEUTRAL ? GREATER : value;
        }
        return value;
    }
}

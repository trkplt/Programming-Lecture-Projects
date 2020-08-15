package edu.kit.informatik.baker.product;

import edu.kit.informatik.baker.ui.Main;

import java.util.Comparator;

public class RawMaterialComparator implements Comparator<RawMaterial> {

    private static final int GREATER = Main.ONE;
    private static final int LESSER = Main.MINUS_ONE;
    private static final int NEUTRAL = Main.ZERO;

    private final Market market;

    public RawMaterialComparator(final Market market) {
        this.market = market;
    }

    @Override
    public int compare(RawMaterial first, RawMaterial second) {
        int firstAmount = this.market.getAmount(first);
        int secondAmount = this.market.getAmount(second);
        int out;

        if (first == second) {
            out = NEUTRAL;
        } else if (firstAmount < secondAmount) {
            out = LESSER;
        } else if (firstAmount > secondAmount) {
            out = GREATER;
        } else {
            out = first.compareTo(second);
        }
        return out;
    }
}

package edu.kit.informatik.baker.product;

import edu.kit.informatik.baker.ui.Main;

import java.util.Comparator;

/**
 * This class serves as a comparator for {@link edu.kit.informatik.baker.product.RawMaterial}s.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public class RawMaterialComparator implements Comparator<RawMaterial> {

    private static final int GREATER = Main.ONE;
    private static final int LESSER = Main.MINUS_ONE;
    private static final int NEUTRAL = Main.ZERO;

    private final Market market;

    /**
     * This constructor creates a {@link edu.kit.informatik.baker.product.RawMaterialComparator} with the given
     * {@link edu.kit.informatik.baker.product.Market} to compare the raw materials according to their stocks (fewer
     * is better) in the market and their name (lexicographically) in case of an equality of stocks.
     *
     * @param market is the {@link edu.kit.informatik.baker.product.Market} to gather the stock information
     */
    protected RawMaterialComparator(final Market market) {
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

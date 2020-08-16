package edu.kit.informatik.baker.product;

import edu.kit.informatik.baker.Game;
import edu.kit.informatik.baker.ui.Main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.StringJoiner;
import java.util.TreeSet;

/**
 * This class represents a market in a baker game. It contains the stocks of the
 * {@link edu.kit.informatik.baker.product.RawMaterial}s and regulates the prices of the raw materials. It also has
 * some useful operations on these raw materials.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public class Market {

    private static final int MARKET_SIZE_PER_MATERIAL = 5;
    private static final int MARKET_START_STOCKS = 2;

    private final HashMap<RawMaterial, Integer> stock;

    /**
     * This constructor creates a {@link edu.kit.informatik.baker.product.Market} with a maximum size per raw
     * material of {@link #MARKET_SIZE_PER_MATERIAL} and with starting stocks {@link #MARKET_START_STOCKS}.
     */
    public Market() {
        this.stock = new HashMap<>();

        for (RawMaterial rawMaterial : RawMaterial.values()) {
            this.stock.put(rawMaterial, MARKET_START_STOCKS);
        }
    }

    /**
     * Basic getter for the price of a {@link edu.kit.informatik.baker.product.RawMaterial}.
     *
     * @param rawMaterial is the raw material to get the price of
     * @return an integer representing the price or -1 if the market does not have that raw material
     */
    public int getPrice(RawMaterial rawMaterial) {
        int amount = this.stock.get(rawMaterial);
        if (--amount >= Main.ZERO) {
            return MARKET_SIZE_PER_MATERIAL - amount;
        }
        return Main.MINUS_ONE;
    }

    /**
     * Basic getter for the amount of a {@link edu.kit.informatik.baker.product.RawMaterial}.
     *
     * @param rawMaterial is the raw material to get the amount of
     * @return an integer representing the amount of the raw material
     */
    public int getAmount(RawMaterial rawMaterial) {
        return this.stock.get(rawMaterial);
    }

    /**
     * This method checks if this market can buy one piece of the specified
     * {@link edu.kit.informatik.baker.product.RawMaterial}.
     *
     * @param rawMaterial is the raw material to check
     * @return true, if the market can buy one amount of the specified raw material, false otherwise
     */
    public boolean canTakeRawMaterial(RawMaterial rawMaterial) {
        return this.stock.get(rawMaterial) < MARKET_SIZE_PER_MATERIAL;
    }

    /**
     * This method checks if this market can sell one piece of the specified
     * {@link edu.kit.informatik.baker.product.RawMaterial}.
     *
     * @param rawMaterial is the raw material to check
     * @return true, if the market can sell one amount of the specified raw material, false otherwise
     */
    public boolean canGiveRawMaterial(RawMaterial rawMaterial) {
        return this.stock.get(rawMaterial) > Main.ZERO;
    }

    /**
     * This method adds one amount of the specified {@link edu.kit.informatik.baker.product.RawMaterial} to this
     * market. It does not complete any checks such as the check if the market can buy one of these raw materials.
     * Those checks should be maintained in either {@link edu.kit.informatik.baker.Game} or in
     * {@link edu.kit.informatik.baker.ui.Command} depending on the type of wished output.
     *
     * @param rawMaterial is the raw material to add to this market
     */
    public void addRawMaterial(RawMaterial rawMaterial) {
        this.stock.replace(rawMaterial, this.stock.get(rawMaterial) + Game.BUY_SELL_AMOUNT);
    }

    /**
     * This method deducts one amount of the specified {@link edu.kit.informatik.baker.product.RawMaterial} from this
     * market. It does not complete any checks such as the check if the market can sell one of these raw materials.
     * Those checks should be maintained in either {@link edu.kit.informatik.baker.Game} or in
     * {@link edu.kit.informatik.baker.ui.Command} depending on the type of wished output.
     *
     * @param rawMaterial is the raw material to deduct from this market
     */
    public void deductRawMaterial(RawMaterial rawMaterial) {
        this.stock.replace(rawMaterial, this.stock.get(rawMaterial) - Game.BUY_SELL_AMOUNT);
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        TreeSet<RawMaterial> rawMaterials = new TreeSet<>(new RawMaterialComparator(this));
        rawMaterials.addAll(Arrays.asList(RawMaterial.values()));

        for (RawMaterial rawMaterial : rawMaterials) {
            out.add(this.stock.get(rawMaterial) + Main.SEPARATOR + rawMaterial.getName());
        }
        return out.toString();
    }
}

package edu.kit.informatik.baker.player;

import edu.kit.informatik.baker.Game;
import edu.kit.informatik.baker.product.RawMaterial;

import java.util.HashMap;

/**
 * This class represents a container a {@link edu.kit.informatik.baker.player.Player} is keeping his bought
 * {@link edu.kit.informatik.baker.product.RawMaterial}s in. It also provides some useful operations on those raw
 * materials.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public class Pack {

    private static final int START_AMOUNT_PER_MATERIAL = 0;

    private final HashMap<RawMaterial, Integer> bag;

    /**
     * This constructor creates a {@link edu.kit.informatik.baker.player.Pack} with the predefined
     * {@link #START_AMOUNT_PER_MATERIAL}.
     */
    protected Pack() {
        this.bag = new HashMap<>();

        for (RawMaterial rawMaterial : RawMaterial.values()) {
            this.bag.put(rawMaterial, START_AMOUNT_PER_MATERIAL);
        }
    }

    /**
     * This method provides the amount of the given {@link edu.kit.informatik.baker.product.RawMaterial} in this
     * {@link edu.kit.informatik.baker.player.Pack}.
     *
     * @param rawMaterial is the {@link edu.kit.informatik.baker.product.RawMaterial} to be searched for in this Pack
     * @return an integer representing the amount of the given raw material
     */
    protected int getRawMaterialAmount(RawMaterial rawMaterial) {
        return this.bag.get(rawMaterial);
    }

    /**
     * This method adds the given {@link edu.kit.informatik.baker.product.RawMaterial} to this pack.
     *
     * @param rawMaterial is the raw material to be added to this {@link edu.kit.informatik.baker.player.Pack}
     */
    protected void addRawMaterial(RawMaterial rawMaterial) {
        this.bag.replace(rawMaterial, this.bag.get(rawMaterial) + Game.BUY_SELL_AMOUNT);
    }

    /**
     * This method deducts the given amount of the given {@link edu.kit.informatik.baker.product.RawMaterial} from
     * this pack. It does not complete any checks such as the check if there are enough raw materials in the pack.
     * Those checks should be maintained either in {@link edu.kit.informatik.baker.Game} or in
     * {@link edu.kit.informatik.baker.ui.Command} depending on the type of wished output.
     *
     * @param rawMaterial is the {@link edu.kit.informatik.baker.product.RawMaterial} to be deducted
     * @param amount is an integer representing the amount of raw materials to be deducted
     */
    protected void deductRawMaterial(RawMaterial rawMaterial, int amount) {
        this.bag.replace(rawMaterial, this.bag.get(rawMaterial) - amount);
    }
}

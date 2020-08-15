package edu.kit.informatik.baker.player;

import edu.kit.informatik.baker.Game;
import edu.kit.informatik.baker.product.RawMaterial;

import java.util.HashMap;

public class Pack {

    private static final int START_AMOUNT_PER_MATERIAL = 0;

    private final HashMap<RawMaterial, Integer> bag;

    Pack() {
        this.bag = new HashMap<>();

        for (RawMaterial rawMaterial : RawMaterial.values()) {
            this.bag.put(rawMaterial, START_AMOUNT_PER_MATERIAL);
        }
    }

    public int getRawMaterialAmount(RawMaterial rawMaterial) {
        return this.bag.get(rawMaterial);
    }

    public void addRawMaterial(RawMaterial rawMaterial) {
        this.bag.replace(rawMaterial, this.bag.get(rawMaterial) + Game.BUY_SELL_AMOUNT);
    }

    public boolean deductRawMaterial(RawMaterial rawMaterial, int amount) {
        if (this.getRawMaterialAmount(rawMaterial) < amount) {
            return false;
        }
        this.bag.replace(rawMaterial, this.bag.get(rawMaterial) - amount);
        return true;
    }
}

package edu.kit.informatik.baker.product;

import edu.kit.informatik.baker.Game;
import edu.kit.informatik.baker.ui.Main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.StringJoiner;
import java.util.TreeSet;

public class Market {

    public static final int MARKET_SIZE_PER_MATERIAL = 5;
    public static final int MARKET_START_STOCKS = 2;

    private final HashMap<RawMaterial, Integer> stock;

    public Market() {
        this.stock = new HashMap<>();

        for (RawMaterial rawMaterial : RawMaterial.values()) {
            this.stock.put(rawMaterial, MARKET_START_STOCKS);
        }
    }

    public int getPrice(RawMaterial rawMaterial) {
        int amount = this.stock.get(rawMaterial);
        if (--amount >= Main.ZERO) {
            return MARKET_SIZE_PER_MATERIAL - amount;
        }
        return Main.MINUS_ONE;
    }

    public int getAmount(RawMaterial rawMaterial) {
        return this.stock.get(rawMaterial);
    }

    public boolean canTakeRawMaterial(RawMaterial rawMaterial) {
        return this.stock.get(rawMaterial) < MARKET_SIZE_PER_MATERIAL;
    }

    public boolean canGiveRawMaterial(RawMaterial rawMaterial) {
        return this.stock.get(rawMaterial) > Main.ZERO;
    }

    public boolean addRawMaterial(RawMaterial rawMaterial) {
        if (this.canTakeRawMaterial(rawMaterial)) {
            this.stock.replace(rawMaterial, this.stock.get(rawMaterial) + Game.BUY_SELL_AMOUNT);
            return true;
        }
        return false;
    }

    public boolean deductRawMaterial(RawMaterial rawMaterial) {
        if (this.canGiveRawMaterial(rawMaterial)) {
            this.stock.replace(rawMaterial, this.stock.get(rawMaterial) - Game.BUY_SELL_AMOUNT);
            return true;
        }
        return false;
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

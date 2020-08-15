package edu.kit.informatik.baker.player;

import edu.kit.informatik.baker.ui.Main;

public class Purse {

    protected static final int WIN_GOLD_AMOUNT = 100;
    protected static final int MASTER_PRIZE = 25;
    public static final int START_GOLD_AMOUNT = 20;
    public static final int START_FIELD_PRIZE = 5;
    public static final int HARVEST_GOLD_EARN = Main.ONE;

    private int goldAmount;

    Purse() {
        this.goldAmount = START_GOLD_AMOUNT;
    }

    public boolean canAfford(int price) {
        return this.goldAmount >= price;
    }

    public int getGoldAmount() {
        return this.goldAmount;
    }

    public void addGold(int amount) {
        this.goldAmount += amount;
    }

    public boolean deductGold(int amount) {
        if (this.canAfford(amount)) {
            this.goldAmount -= amount;
            return true;
        }
        return false;
    }

    public boolean won() {
        return this.canAfford(WIN_GOLD_AMOUNT);
    }
}

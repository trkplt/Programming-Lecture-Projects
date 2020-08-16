package edu.kit.informatik.baker.player;

import edu.kit.informatik.baker.ui.Main;

/**
 * This class represents the purse of a {@link edu.kit.informatik.baker.player.Player} in a baker game. It
 * encapsulates the amount of gold a player has and provides some useful operations on this gold stock.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public class Purse {

    /**
     * The amount of gold a player has to reach in order to win the game.
     */
    protected static final int WIN_GOLD_AMOUNT = 100;

    /**
     * The amount of gold a player acquires as a one time prize when he prepares all the available dishes in the game.
     */
    protected static final int MASTER_PRIZE = 25;

    /**
     * The amount of gold a player starts a game with.
     */
    protected static final int START_GOLD_AMOUNT = 20;

    /**
     * The amount of gold a player acquires when he lands on the {@link edu.kit.informatik.baker.board.StartField}
     * after the dice roll.
     */
    protected static final int START_FIELD_PRIZE = 5;

    /**
     * The amount of gold a player acquires when he harvests and sells the harvested raw material to the market.
     */
    protected static final int HARVEST_GOLD_EARN = Main.ONE;

    private int goldAmount;

    /**
     * This constructor creates a {@link edu.kit.informatik.baker.player.Purse} with the {@link #START_GOLD_AMOUNT}.
     */
    protected Purse() {
        this.goldAmount = START_GOLD_AMOUNT;
    }

    /**
     * This method checks if the amount of gold in this {@link edu.kit.informatik.baker.player.Purse} is enough to buy
     * something with the given price.
     *
     * @param price is an integer representing the price of the material to buy
     * @return true if the current gold amount is enough, false otherwise
     */
    protected boolean canAfford(int price) {
        return this.goldAmount >= price;
    }

    /**
     * This method provides the amount of gold in this {@link edu.kit.informatik.baker.player.Purse}.
     *
     * @return an integer representing the amount of gold in this purse
     */
    protected int getGoldAmount() {
        return this.goldAmount;
    }

    /**
     * This method adds the given amount of gold to this {@link edu.kit.informatik.baker.player.Purse}.
     *
     * @param amount is an integer representing the amount of gold to add
     */
    protected void addGold(int amount) {
        this.goldAmount += amount;
    }

    /**
     * This method deducts the given amount of gold from this {@link edu.kit.informatik.baker.player.Purse}. It does
     * not check if there are enough golds in the purse. That check should be maintained either in
     * {@link edu.kit.informatik.baker.Game} or in {@link edu.kit.informatik.baker.ui.Command} depending on the type
     * of wished output.
     *
     * @param amount is an integer representing the amount of gold to deduct from the purse
     */
    protected void deductGold(int amount) {
        this.goldAmount -= amount;
    }

    /**
     * This method checks if the owner of this purse has won the game according to {@link #WIN_GOLD_AMOUNT}.
     *
     * @return true if the owner of this purse has won the game, false otherwise
     */
    protected boolean won() {
        return this.canAfford(WIN_GOLD_AMOUNT);
    }
}

package edu.kit.informatik.baker.player;

import edu.kit.informatik.baker.board.Field;
import edu.kit.informatik.baker.board.StartField;
import edu.kit.informatik.baker.product.Dish;
import edu.kit.informatik.baker.product.DishComparator;
import edu.kit.informatik.baker.product.RawMaterial;
import edu.kit.informatik.baker.ui.Main;

import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;

/**
 * This class represents a player in a baker game and encapsulates relevant information about a player such as current
 * gold amount and amount of {@link edu.kit.informatik.baker.product.RawMaterial}s the player currently has etc.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public class Player {

    /**
     * This integer defines the starting index of {@link edu.kit.informatik.baker.player.Player}s in a baker game.
     */
    public static final int PLAYER_INDEX_START = Main.ONE;

    /**
     * This integer defines the minimum number of {@link edu.kit.informatik.baker.player.Player}s in a baker game.
     */
    public static final int MIN_NUM_OF_PLAYERS = 2;

    /**
     * This integer defines the maximum number of {@link edu.kit.informatik.baker.player.Player}s in a baker game.
     */
    public static final int MAX_NUM_OF_PLAYERS = 4;

    private static final String PLAYER_NAME_INIT = "P";

    private final String name;
    private final Pack pack;
    private final Purse purse;
    private final Set<Dish> prepared;
    private boolean novice;

    /**
     * This constructor creates a {@link edu.kit.informatik.baker.player.Player} with the given index, 20 initial
     * golds and an empty {@link edu.kit.informatik.baker.player.Pack}.
     *
     * @param index is an integer to be used in the name of the {@link edu.kit.informatik.baker.player.Player} as "Px"
     *              where x is the integer
     */
    public Player(int index) {
        this.name = PLAYER_NAME_INIT + index;
        this.pack = new Pack();
        this.purse = new Purse();
        this.prepared = new TreeSet<>(new DishComparator());
        this.novice = true;
    }

    /**
     * This static method provides the {@link edu.kit.informatik.baker.player.Player} pattern in "(Px|Py|...)" format
     * where x and y are in interval [{@link #PLAYER_INDEX_START},{@link #MAX_NUM_OF_PLAYERS}].
     *
     * @return a String containing the player pattern
     */
    public static String getPlayerPattern() {
        StringJoiner pattern = new StringJoiner(Main.PATTERN_OR, Main.PATTERN_GROUP_INIT, Main.PATTERN_GROUP_END);

        for (int i = PLAYER_INDEX_START; i <= MAX_NUM_OF_PLAYERS; i++) {
            pattern.add(PLAYER_NAME_INIT + i);
        }

        return pattern.toString();
    }

    /**
     * This method provides the name of this player in "Px" format where x is the index of the
     * {@link edu.kit.informatik.baker.player.Player}.
     *
     * @return a String containing the name of the {@link edu.kit.informatik.baker.player.Player}
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method provides the current amount of golds from {@link edu.kit.informatik.baker.player.Player}'s
     * {@link edu.kit.informatik.baker.player.Purse}.
     *
     * @return an integer representing the amount of gold of this {@link edu.kit.informatik.baker.player.Player}
     */
    public int getGold() {
        return this.purse.getGoldAmount();
    }

    private boolean canPrepareDish(Dish dish) {
        for (RawMaterial rawMaterial : RawMaterial.values()) {
            if (dish.getReqRawMaterial(rawMaterial) > this.pack.getRawMaterialAmount(rawMaterial)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method provides a TreeSet containing the {@link edu.kit.informatik.baker.product.Dish}es this
     * {@link edu.kit.informatik.baker.player.Player} can currently prepare.
     *
     * @return a TreeSet containing the {@link edu.kit.informatik.baker.product.Dish}es that can be prepared by this
     * {@link edu.kit.informatik.baker.player.Player}
     */
    public TreeSet<Dish> canPrepare() {
        TreeSet<Dish> possible = new TreeSet<>(new DishComparator());

        for (Dish dish : Dish.values()) {
            if (canPrepareDish(dish)) {
                possible.add(dish);
            }
        }
        return possible;
    }

    /**
     * This method deducts the required {@link edu.kit.informatik.baker.product.RawMaterial}s for the given
     * {@link edu.kit.informatik.baker.product.Dish} from this {@link edu.kit.informatik.baker.player.Player}'s
     * {@link edu.kit.informatik.baker.player.Pack}, adds the prepared dish to the list of prepared dishes of this
     * player, adds the selling price to the player's {@link edu.kit.informatik.baker.player.Purse} and returns true,
     * if there are enough raw materials in it and does nothing and returns false, if otherwise. Additionally it adds
     * the {@link edu.kit.informatik.baker.player.Purse#MASTER_PRIZE} to this players purse, if the player has
     * prepared all the dishes. The master prize can only be acquired once.
     *
     * @param dish is the {@link edu.kit.informatik.baker.product.Dish} to be prepared
     * @return true if the operation successful, false otherwise
     */
    public boolean prepare(Dish dish) {
        if (!canPrepareDish(dish)) {
            return false;
        }

        for (RawMaterial rawMaterial : RawMaterial.values()) {
            this.pack.deductRawMaterial(rawMaterial, dish.getReqRawMaterial(rawMaterial));
        }

        this.purse.addGold(dish.getProfit());
        this.prepared.add(dish);

        if (novice && prepared.size() == Dish.values().length) {
            this.purse.addGold(Purse.MASTER_PRIZE);
            this.novice = false;
        }
        return true;
    }

    /**
     * This method only handles the {@link edu.kit.informatik.baker.player.Player}'s part of a bigger harvest
     * operation in the baker game. It only adds {@link edu.kit.informatik.baker.player.Purse#HARVEST_GOLD_EARN} to
     * the player's {@link edu.kit.informatik.baker.player.Purse}. It does not complete any checks such as the check
     * if the player already harvested in the current turn. Those checks should be maintained either in
     * {@link edu.kit.informatik.baker.Game} or in {@link edu.kit.informatik.baker.ui.Command} depending on the type
     * of wished output.
     */
    public void harvest() {
        this.purse.addGold(Purse.HARVEST_GOLD_EARN);
    }

    /**
     * This method only handles the {@link edu.kit.informatik.baker.player.Player}'s part of a bigger move operation
     * in the baker game. It only adds {@link edu.kit.informatik.baker.player.Purse#START_FIELD_PRIZE} to the player's
     * {@link edu.kit.informatik.baker.player.Purse}, if the given parameter is an instance of
     * {@link edu.kit.informatik.baker.board.StartField}. It does not change the position of the player on the board or
     * does not complete any checks such as the check if the player already moved in the current turn. Those
     * operations and checks should be maintained either in the {@link edu.kit.informatik.baker.Game} or in
     * {@link edu.kit.informatik.baker.ui.Command} depending on the type of wished output.
     *
     * @param resultingField is the {@link edu.kit.informatik.baker.board.Field} the player landed on after the roll
     *                       of dice
     */
    public void move(Field resultingField) {
        if (resultingField instanceof StartField) {
            this.purse.addGold(Purse.START_FIELD_PRIZE);
        }
    }

    /**
     * This method checks if this {@link edu.kit.informatik.baker.player.Player} can afford something with the given
     * price in the current state.
     *
     * @param price is an integer representing the price of the material the player wants to buy
     * @return true, if the player can afford the price, false otherwise
     */
    public boolean canBuy(int price) {
        return this.purse.canAfford(price);
    }

    /**
     * This method adds the given {@link edu.kit.informatik.baker.product.RawMaterial} to this
     * {@link edu.kit.informatik.baker.player.Player}'s {@link edu.kit.informatik.baker.player.Pack} and deducts the
     * given price from the player's {@link edu.kit.informatik.baker.player.Purse}. This method does not complete any
     * checks such as the check if the player can afford the raw material etc. Those checks should be maintained
     * either in the {@link edu.kit.informatik.baker.Game} or in the {@link edu.kit.informatik.baker.ui.Command}
     * depending on the type of wished output.
     *
     * @param rawMaterial is the {@link edu.kit.informatik.baker.product.RawMaterial} the player buys
     * @param price is an integer representing the price of the raw material
     */
    public void buy(RawMaterial rawMaterial, int price) {
        this.pack.addRawMaterial(rawMaterial);
        this.purse.deductGold(price);
    }

    /**
     * This method checks if this {@link edu.kit.informatik.baker.player.Player} has won the game according to
     * {@link edu.kit.informatik.baker.player.Purse#WIN_GOLD_AMOUNT}.
     *
     * @return true, if the player has won the game, false otherwise
     */
    public boolean won() {
        return this.purse.won();
    }

    @Override
    public String toString() {
        int gold = this.purse.getGoldAmount();
        int flour = this.pack.getRawMaterialAmount(RawMaterial.FLOUR);
        int egg = this.pack.getRawMaterialAmount(RawMaterial.EGG);
        int milk = this.pack.getRawMaterialAmount(RawMaterial.MILK);
        return gold + Main.SEPARATOR + flour + Main.SEPARATOR
                + egg + Main.SEPARATOR + milk;
    }
}

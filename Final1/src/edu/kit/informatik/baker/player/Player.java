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

public class Player {

    public static final int PLAYER_INDEX_START = Main.ONE;
    public static final int MIN_NUM_OF_PLAYERS = 2;
    public static final int MAX_NUM_OF_PLAYERS = 4;
    private static final String PLAYER_NAME_INIT = "P";

    private final String name;
    private final Pack pack;
    private final Purse purse;
    private final Set<Dish> prepared;
    private boolean novice;

    public Player(int index) {
        this.name = PLAYER_NAME_INIT + index;
        this.pack = new Pack();
        this.purse = new Purse();
        this.prepared = new TreeSet<>(new DishComparator());
        this.novice = true;
    }

    public static String getPlayerPattern() {
        StringJoiner pattern = new StringJoiner(Main.PATTERN_OR, Main.PATTERN_GROUP_INIT, Main.PATTERN_GROUP_END);

        for (int i = PLAYER_INDEX_START; i <= MAX_NUM_OF_PLAYERS; i++) {
            pattern.add(PLAYER_NAME_INIT + i);
        }

        return pattern.toString();
    }

    public String getName() {
        return this.name;
    }

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

    public TreeSet<Dish> canPrepare() {
        TreeSet<Dish> possible = new TreeSet<>(new DishComparator());

        for (Dish dish : Dish.values()) {
            if (canPrepareDish(dish)) {
                possible.add(dish);
            }
        }
        return possible;
    }

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

    public void harvest() {
        this.purse.addGold(Purse.HARVEST_GOLD_EARN);
    }

    public void move(Field field) {
        if (field instanceof StartField) {
            this.purse.addGold(Purse.START_FIELD_PRIZE);
        }
    }

    public boolean canBuy(int price) {
        return this.purse.canAfford(price);
    }

    public void buy(RawMaterial rawMaterial, int price) {
        this.pack.addRawMaterial(rawMaterial);
        this.purse.deductGold(price);
    }

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

package edu.kit.informatik.baker;

import edu.kit.informatik.baker.board.Board;
import edu.kit.informatik.baker.board.Field;
import edu.kit.informatik.baker.board.StartField;
import edu.kit.informatik.baker.player.Player;
import edu.kit.informatik.baker.product.Dish;
import edu.kit.informatik.baker.product.Market;
import edu.kit.informatik.baker.product.RawMaterial;
import edu.kit.informatik.baker.ui.Main;
import edu.kit.informatik.baker.ui.ProgramState;

import java.util.*;

public class Game {

    public static final int MIN_DICE_ROLL = Main.ONE;
    public static final int MAX_DICE_ROLL = 6;
    public static final String ROLL_PATTERN = Main.PATTERN_GROUP_INIT + Main.PATTERN_CHAR_CLASS_INIT + MIN_DICE_ROLL
            + Main.HYPHEN + MAX_DICE_ROLL + Main.PATTERN_CHAR_CLASS_END + Main.PATTERN_GROUP_END;
    public static final int BUY_SELL_AMOUNT = Main.ONE;

    private final Board board;
    private final Market market;
    private final LinkedList<HashMap<Player, Field>> playerQueue;
    private ProgramState programState;
    private Player activePlayer;
    private Field activeField;
    private boolean activePlayerMoved;
    private boolean activePlayerHarvested;
    private boolean activePlayerBought;
    private boolean gameFinished;

    public Game(int numOfPlayers, String fieldsString) {
        this.board = new Board(fieldsString);
        this.market = new Market();
        this.playerQueue = new LinkedList<>();
        this.gameFinished = false;
        this.programState = ProgramState.RUNNING;

        if (!this.board.isBoardValid()) {
            return;
        }

        for (int i = Player.PLAYER_INDEX_START; i <= numOfPlayers; i++) {
            HashMap<Player, Field> playerAndField = new HashMap<>();
            playerAndField.put(new Player(i), this.board.getStartField());
            this.playerQueue.add(playerAndField);
        }

        this.arrangeNewActivePlayer();
    }

    public boolean isGameRunning() {
        return this.programState.equals(ProgramState.RUNNING);
    }

    public boolean isBoardValid() {
        return this.board.isBoardValid();
    }

    public boolean isActivePlayerMoved() {
        return this.activePlayerMoved;
    }

    public boolean isActivePlayerHarvested() {
        return this.activePlayerHarvested;
    }

    public boolean isActivePlayerBought() {
        return this.activePlayerBought;
    }

    public boolean isGameFinished() {
        return this.gameFinished;
    }

    public String getActiveFieldAbbreviation() {
        return this.activeField.getAbbreviation();
    }

    public int getActivePlayerGold() {
        return this.activePlayer.getGold();
    }

    public boolean isActiveFieldStart() {
        return this.activeField instanceof StartField;
    }

    public boolean isStockEnough(String nameOfRawMaterial) {
        return this.market.canGiveRawMaterial(RawMaterial.getRawMaterial(nameOfRawMaterial));
    }

    public boolean isCapacityEnough() {
        return this.market.canTakeRawMaterial(this.activeField.getRawMaterial());
    }

    public String getActivePlayerName() {
        return this.activePlayer.getName();
    }

    public boolean canActivePlayerBuy(String nameOfRawMaterial) {
        RawMaterial rawMaterial = RawMaterial.getRawMaterial(nameOfRawMaterial);
        return this.activePlayer.canBuy(this.market.getPrice(rawMaterial));
    }

    public String getDoableDishes() {
        TreeSet<Dish> possible = this.activePlayer.canPrepare();
        StringJoiner out = new StringJoiner(System.lineSeparator());

        for (Dish dish : possible) {
            out.add(dish.getName());
        }

        return out.length() > Main.TWO ? out.toString() : null;
    }

    public String getMarketSummary() {
        return this.market.toString();
    }

    public String getPlayerSummary(String name) {
        if (this.activePlayer.getName().equals(name)) {
            return this.activePlayer.toString();
        }

        for (HashMap<Player, Field> playerAndField : this.playerQueue) {
            Map.Entry<Player, Field> playerField = playerAndField.entrySet().iterator().next();
            Player player = playerField.getKey();

            if (player.getName().equals(name)) {
                return player.toString();
            }
        }
        return null;
    }

    public ProgramState getProgramState() {
        return this.programState;
    }

    private void arrangeNewActivePlayer() {
        Map.Entry<Player, Field> playerAndField = this.playerQueue.poll().entrySet().iterator().next();
        this.activePlayer = playerAndField.getKey();
        this.activeField = playerAndField.getValue();
        this.activePlayerMoved = false;
        this.activePlayerHarvested = false;
        this.activePlayerBought = false;
    }

    private void winCheck() {
        if (this.activePlayer.won()) {
            this.gameFinished = true;
        }
    }

    public void moveActivePlayer(int amount) {
        this.activeField = this.board.getNextField(this.activeField, amount);
        this.activePlayer.move(this.activeField);
        this.activePlayerMoved = true;
        this.winCheck();
    }

    public String harvestActivePlayer() {
        this.market.addRawMaterial(this.activeField.getRawMaterial());
        this.activePlayer.harvest();
        this.winCheck();
        this.activePlayerHarvested = true;
        return this.activeField.getRawMaterial().getName();
    }

    public boolean prepare(String dishName) {
        Dish dish = Dish.getDish(dishName);
        boolean done = this.activePlayer.prepare(dish);
        this.winCheck();
        return done;
    }

    public String turn() {
        HashMap<Player, Field> pass = new HashMap<>();
        pass.put(this.activePlayer, this.activeField);
        this.playerQueue.add(pass);
        this.arrangeNewActivePlayer();
        return this.activePlayer.getName();
    }

    public int buy(String nameOfRawMaterial) {
        RawMaterial rawMaterial = RawMaterial.getRawMaterial(nameOfRawMaterial);
        int price = this.market.getPrice(rawMaterial);
        this.activePlayer.buy(rawMaterial, price);
        this.market.deductRawMaterial(rawMaterial);
        this.activePlayerBought = true;
        return price;
    }

    public void quit() {
        this.programState = ProgramState.CLOSED;
    }
}

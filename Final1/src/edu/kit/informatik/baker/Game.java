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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeSet;

/**
 * This is an association class for {@link edu.kit.informatik.baker.board.Board},
 * {@link edu.kit.informatik.baker.product.Market} and {@link edu.kit.informatik.baker.player.Player}s. It contains
 * all sorts of operations on these three and also delivers the success messages to
 * {@link edu.kit.informatik.baker.ui.Command}.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public class Game {

    /**
     * This is an integer representing the buy and sell amount for the operations with unspecified amount.
     */
    public static final int BUY_SELL_AMOUNT = Main.ONE;

    private static final int MIN_DICE_ROLL = Main.ONE;
    private static final int MAX_DICE_ROLL = 6;

    /**
     * This is a String containing the pattern for roll command.
     */
    public static final String ROLL_PATTERN = Main.PATTERN_GROUP_INIT + Main.PATTERN_CHAR_CLASS_INIT + MIN_DICE_ROLL
            + Main.HYPHEN + MAX_DICE_ROLL + Main.PATTERN_CHAR_CLASS_END + Main.PATTERN_GROUP_END;



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

    /**
     * This constructor tries to create a baker game with the given number of
     * {@link edu.kit.informatik.baker.player.Player}s, a market with standard initial status and the given String
     * representation of a {@link edu.kit.informatik.baker.board.Board}. If the board is not valid, then the program
     * shuts itself down.
     *
     * @param numOfPlayers is an integer representing the number of players in this game
     * @param fieldsString is a String containing the format of the Board
     */
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

    /**
     * Basic getter for the {@link edu.kit.informatik.baker.ui.ProgramState}.
     *
     * @return the ProgramState the program currently is in.
     */
    public boolean isGameRunning() {
        return this.getProgramState().equals(ProgramState.RUNNING);
    }

    /**
     * Basic getter for the validity of the {@link edu.kit.informatik.baker.board.Board}.
     *
     * @return true, if the Board is valid, false otherwise
     */
    public boolean isBoardValid() {
        return this.board.isBoardValid();
    }

    /**
     * This method provides information about the move state of the active
     * {@link edu.kit.informatik.baker.player.Player}.
     *
     * @return true, if the active player already moved in this turn, false otherwise
     */
    public boolean isActivePlayerMoved() {
        return this.activePlayerMoved;
    }

    /**
     * This method provides information about the harvest state of the active
     * {@link edu.kit.informatik.baker.player.Player}.
     *
     * @return true, if the active player already harvested in this turn, false otherwise
     */
    public boolean isActivePlayerHarvested() {
        return this.activePlayerHarvested;
    }

    /**
     * This method provides information about the buy state of the active
     * {@link edu.kit.informatik.baker.player.Player}.
     *
     * @return true, if the active player already bought in this turn, false otherwise
     */
    public boolean isActivePlayerBought() {
        return this.activePlayerBought;
    }

    /**
     * This method tells if the game is already won by one of the {@link edu.kit.informatik.baker.player.Player}s.
     *
     * @return true, if the game is won by one of the players, false otherwise
     */
    public boolean isGameFinished() {
        return this.gameFinished;
    }

    /**
     * Basic getter for the abbreviation of the {@link edu.kit.informatik.baker.board.Field} active
     * {@link edu.kit.informatik.baker.player.Player} standing on.
     *
     * @return the active Field
     */
    public String getActiveFieldAbbreviation() {
        return this.activeField.getAbbreviation();
    }

    /**
     * Basic getter for the amount of gold the active {@link edu.kit.informatik.baker.player.Player} has.
     *
     * @return an integer representing the amount of gold the active player currently has
     */
    public int getActivePlayerGold() {
        return this.activePlayer.getGold();
    }

    /**
     * This method checks if the {@link edu.kit.informatik.baker.board.Field} the active
     * {@link edu.kit.informatik.baker.player.Player} standing on is an instance of
     * {@link edu.kit.informatik.baker.board.StartField}.
     *
     * @return true, if the active player stands on a StartField, false otherwise
     */
    public boolean isActiveFieldStart() {
        return this.activeField instanceof StartField;
    }

    /**
     * This method checks if the market can currently sell one amount of the
     * {@link edu.kit.informatik.baker.product.RawMaterial} represented by the given String.
     *
     * @param nameOfRawMaterial is a String containing the name of the raw material
     * @return true, if the market can sell, false otherwise
     */
    public boolean isStockEnough(String nameOfRawMaterial) {
        return this.market.canGiveRawMaterial(RawMaterial.getRawMaterial(nameOfRawMaterial));
    }

    /**
     * This method checks if the market can currently buy one amount of the
     * {@link edu.kit.informatik.baker.product.RawMaterial} that can be harvested from the
     * {@link edu.kit.informatik.baker.board.Field} the active {@link edu.kit.informatik.baker.player.Player} standing
     * on.
     *
     * @return true, if the market can buy, false otherwise
     */
    public boolean isCapacityEnough() {
        return this.market.canTakeRawMaterial(this.activeField.getRawMaterial());
    }

    /**
     * Basic getter for the name of the active {@link edu.kit.informatik.baker.player.Player}.
     *
     * @return a String containing the name of the active player
     */
    public String getActivePlayerName() {
        return this.activePlayer.getName();
    }

    /**
     * This method checks if the active {@link edu.kit.informatik.baker.player.Player} can afford the current price of
     * the {@link edu.kit.informatik.baker.product.RawMaterial} represented by the given String.
     *
     * @param nameOfRawMaterial is a String containing the name of the raw material
     * @return true, if the player can afford, false otherwise
     */
    public boolean canActivePlayerBuy(String nameOfRawMaterial) {
        RawMaterial rawMaterial = RawMaterial.getRawMaterial(nameOfRawMaterial);
        return this.activePlayer.canBuy(this.market.getPrice(rawMaterial));
    }

    /**
     * This method provides the names of the {@link edu.kit.informatik.baker.product.Dish}es (each on a different
     * line) the active {@link edu.kit.informatik.baker.player.Player} can prepare.
     *
     * @return a String containing the names of Dishes or null if the player cannot prepare anything
     */
    public String getDoableDishes() {
        TreeSet<Dish> possible = this.activePlayer.canPrepare();
        StringJoiner out = new StringJoiner(System.lineSeparator());

        for (Dish dish : possible) {
            out.add(dish.getName());
        }

        return out.length() > Main.TWO ? out.toString() : null;
    }

    /**
     * This method provides a String representation of the {@link edu.kit.informatik.baker.product.Market} in "x;y"
     * format where x the amount of {@link edu.kit.informatik.baker.product.RawMaterial} the market currently has and
     * y the name of the raw material with each raw material on a new line.
     *
     * @return a String representation of the market
     */
    public String getMarketSummary() {
        return this.market.toString();
    }

    /**
     * This method provides a String representation of the {@link edu.kit.informatik.baker.player.Player} represented
     * by the given String in "w;x;y;z" format where w the gold amount, x the flour amount, y the egg amount and z the
     * milk amount the player currently has.
     *
     * @param name is a String containing the name of the player
     * @return a String representation of the player
     */
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

    /**
     * Basic getter for the {@link edu.kit.informatik.baker.ui.ProgramState} in which the program is.
     *
     * @return the program state in which the program is
     */
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

    /**
     * This method moves the active {@link edu.kit.informatik.baker.player.Player} the given amount of times forward
     * on the baker game {@link edu.kit.informatik.baker.board.Board}. It does not complete any checks such as the
     * check if the current player already rolled the dice in this turn etc. Those checks should be maintained in
     * {@link edu.kit.informatik.baker.ui.Command}.
     *
     * @param amount is an integer representing the amount of forward moves
     */
    public void moveActivePlayer(int amount) {
        this.activeField = this.board.getNextField(this.activeField, amount);
        this.activePlayer.move(this.activeField);
        this.activePlayerMoved = true;
        this.winCheck();
    }

    /**
     * This method harvests the {@link edu.kit.informatik.baker.board.Field} the active
     * {@link edu.kit.informatik.baker.player.Player} stands on and then it adds it to the market and so on without
     * checking if the player already harvested in this turn etc. Those checks should be maintained in the
     * {@link edu.kit.informatik.baker.ui.Command}.
     *
     * @return a String with the format "<nameOfHarvestedRawMaterial>;<currentGoldAmountOfActivePlayer>"
     */
    public String harvestActivePlayer() {
        this.market.addRawMaterial(this.activeField.getRawMaterial());
        this.activePlayer.harvest();
        this.winCheck();
        this.activePlayerHarvested = true;
        return this.activeField.getRawMaterial().getName();
    }

    /**
     * This method makes the active {@link edu.kit.informatik.baker.player.Player} prepare the
     * {@link edu.kit.informatik.baker.product.Dish} represented by the given String.
     *
     * @param dishName is a String containing the name of the Dish
     * @return true if the prepare operation was successful, false otherwise
     */
    public boolean prepare(String dishName) {
        Dish dish = Dish.getDish(dishName);
        boolean done = this.activePlayer.prepare(dish);
        this.winCheck();
        return done;
    }

    /**
     * This method ends the turn of the last active {@link edu.kit.informatik.baker.player.Player} and picks the next
     * player on the queue as the active player.
     *
     * @return a String containing the name of the new active player
     */
    public String turn() {
        HashMap<Player, Field> pass = new HashMap<>();
        pass.put(this.activePlayer, this.activeField);
        this.playerQueue.add(pass);
        this.arrangeNewActivePlayer();
        return this.activePlayer.getName();
    }

    /**
     * This method makes the active {@link edu.kit.informatik.baker.player.Player} buy the
     * {@link edu.kit.informatik.baker.product.RawMaterial} represented by the given String without checking if the
     * active player already harveste on this turn etc. Those checks should be maintained in
     * {@link edu.kit.informatik.baker.ui.Command}.
     *
     * @param nameOfRawMaterial is a String containing the name of the raw material to buy
     * @return a String containing "<buyingPrice>;<goldAmountOfActivePlayerAfterBuy>"
     */
    public int buy(String nameOfRawMaterial) {
        RawMaterial rawMaterial = RawMaterial.getRawMaterial(nameOfRawMaterial);
        int price = this.market.getPrice(rawMaterial);
        this.activePlayer.buy(rawMaterial, price);
        this.market.deductRawMaterial(rawMaterial);
        this.activePlayerBought = true;
        return price;
    }

    /**
     * This method changes the state of this program to {@link edu.kit.informatik.baker.ui.ProgramState#CLOSED}.
     */
    public void quit() {
        this.programState = ProgramState.CLOSED;
    }
}

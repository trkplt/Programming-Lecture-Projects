package edu.kit.informatik.baker.ui;

import edu.kit.informatik.baker.Game;
import edu.kit.informatik.baker.player.Player;
import edu.kit.informatik.baker.product.Dish;
import edu.kit.informatik.baker.product.RawMaterial;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This enum contains the commands the baker game can process. The inputs are being processed in this class and
 * outputs are returned from here to the main.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public enum Command {

    /**
     * This is the roll command. It processes the input and prepares the output so that main can write it.
     */
    ROLL("roll", Main.SPACE + Game.ROLL_PATTERN,
            "'roll'" + Main.SHALL_HAVE_STRUCTURE + "'roll <number>' (<number>" + Main.INTEGER_IN_INTERVAL + "[1-6])") {
        @Override
        public String process(Matcher input, Game game) {
            if (game.isGameFinished()) {
                return GAME_FINISHED;
            } else if (game.isActivePlayerMoved()) {
                return PLAYER_MOVED;
            }

            int rollNumber = Integer.parseInt(input.group(Main.COMMAND_FIRST_GROUP));
            game.moveActivePlayer(rollNumber);

            String winOut = Command.winMessage(game);
            String normalOut = game.getActiveFieldAbbreviation() + Main.SEPARATOR + game.getActivePlayerGold();
            return winOut == null ? normalOut : normalOut + System.lineSeparator() + winOut;
        }
    },

    /**
     * This is the harvest command. It processes the input and prepares the output so that main can write it.
     */
    HARVEST("harvest", Main.EMPTY_STRING, "'harvest'" + Main.SHALL_NOT_PROCEED) {
        @Override
        public String process(Matcher input, Game game) {
            if (game.isGameFinished()) {
                return GAME_FINISHED;
            } else if (game.isActivePlayerBought()) {
                return PLAYER_BOUGHT;
            } else if (game.isActivePlayerHarvested()) {
                return PLAYER_HARVESTED;
            } else if (!game.isActivePlayerMoved()) {
                return PLAYER_NOT_MOVED;
            } else if (game.isActiveFieldStart()) {
                return START_FIELD_ERROR;
            }

            String rawMaterial;
            if (game.isCapacityEnough()) {
                rawMaterial = game.harvestActivePlayer();
            } else {
                return MARKET_FULL;
            }

            String winOut = Command.winMessage(game);
            String normalOut = rawMaterial + Main.SEPARATOR + game.getActivePlayerGold();
            return winOut == null ? normalOut : normalOut + System.lineSeparator() + winOut;
        }
    },

    /**
     * This is the buy command. It processes the input and prepares the output so that main can write it.
     */
    BUY("buy", Main.SPACE + RawMaterial.getPattern(),
            "'buy'" + Main.SHALL_HAVE_STRUCTURE + "'buy <resource>' (<resource>" + Main.FROM_GAME) {
        @Override
        public String process(Matcher input, Game game) {
            if (game.isGameFinished()) {
                return GAME_FINISHED;
            } else if (game.isActivePlayerHarvested()) {
                return PLAYER_HARVESTED;
            } else if (!game.isActivePlayerMoved()) {
                return PLAYER_NOT_MOVED;
            } else if (game.isActiveFieldStart()) {
                return START_FIELD_ERROR;
            }

            String nameOfRawMaterial = input.group(Main.COMMAND_FIRST_GROUP);
            boolean canBuy = game.canActivePlayerBuy(nameOfRawMaterial);
            boolean stockEnough = game.isStockEnough(nameOfRawMaterial);

            if (!stockEnough) {
                return MARKET_EMPTY;
            } else if (!canBuy) {
                return NOT_ENOUGH_GOLD;
            } else {
                int price = game.buy(nameOfRawMaterial);
                return price + Main.SEPARATOR + game.getActivePlayerGold();
            }
        }
    },

    /**
     * This is the prepare command. It processes the input and prepares the output so that main can write it.
     */
    PREPARE("prepare", Main.SPACE + Dish.getPattern(),
            "'prepare'" + Main.SHALL_HAVE_STRUCTURE + "'prepare <recipe>' (<recipe>" + Main.FROM_GAME) {
        @Override
        public String process(Matcher input, Game game) {
            if (game.isGameFinished()) {
                return GAME_FINISHED;
            } else if (!game.isActivePlayerMoved()) {
                return Command.PLAYER_NOT_MOVED;
            }

            String dishName = input.group(Main.COMMAND_FIRST_GROUP);
            boolean successful = game.prepare(dishName);
            int gold = game.getActivePlayerGold();

            String winOut = Command.winMessage(game);
            String normalOut = successful ? String.valueOf(gold) : NOT_ENOUGH_MATERIALS;
            return winOut == null ? normalOut : normalOut + System.lineSeparator() + winOut;
        }
    },

    /**
     * This is the can-prepare? command. It processes the input and prepares the output so that main can write it.
     */
    CAN_PREPARE("can-prepare\\?", Main.EMPTY_STRING, "'can-prepare?'" + Main.SHALL_NOT_PROCEED) {
        @Override
        public String process(Matcher input, Game game) {
            if (game.isGameFinished()) {
                return GAME_FINISHED;
            }
            return game.getDoableDishes();
        }
    },

    /**
     * This is the show-market command. It processes the input and prepares the output so that main can write it.
     */
    SHOW_MARKET("show-market", Main.EMPTY_STRING, "'show-market'" + Main.SHALL_NOT_PROCEED) {
        @Override
        public String process(Matcher input, Game game) {
            return game.getMarketSummary();
        }
    },

    /**
     * This is the show-player command. It processes the input and prepares the output so that main can write it.
     */
    SHOW_PLAYER("show-player", Main.SPACE + Player.getPlayerPattern(),
            "'show-player'" + Main.SHALL_HAVE_STRUCTURE
                    + "'show-player <Px>' (x" + Main.INTEGER_IN_INTERVAL + "[1-4])") {
        @Override
        public String process(Matcher input, Game game) {
            String out = game.getPlayerSummary(input.group(Main.COMMAND_FIRST_GROUP));
            return out == null ? NO_SUCH_PLAYER : out;
        }
    },

    /**
     * This is the turn command. It processes the input and prepares the output so that main can write it.
     */
    TURN("turn", Main.EMPTY_STRING, "'turn'" + Main.SHALL_NOT_PROCEED) {
        @Override
        public String process(Matcher input, Game game) {
            if (game.isGameFinished()) {
                return GAME_FINISHED;
            } else if (!game.isActivePlayerMoved()) {
                return PLAYER_NOT_MOVED;
            }

            return game.turn();
        }
    },

    /**
     * This is the quit command. It processes the input and prepares the output so that main can write it.
     */
    QUIT("quit", Main.EMPTY_STRING, "'quit'" + Main.SHALL_NOT_PROCEED) {
        @Override
        public String process(Matcher input, Game game) {
            game.quit();
            return null;
        }
    };

    private static final String GENERAL_PARAMETER_PATTERN
            = "([ a-zA-z0-9\"'öüğşçıÖÜĞŞÇİ!^+%&/()=?é@€æß|><.:,;`_\\-*£#$½{\\[\\]}]*)";
    private static final String UNSUPPORTED_COMMAND = Main.ERROR_START + "the given command is not supported!";
    private static final String EXECUTE_FAIL = "this command cannot be executed!";
    private static final String PLAYER_MOVED = Main.ERROR_START + "active player already rolled the dice in this turn!";
    private static final String GAME_FINISHED = Main.ERROR_START + "one of the players won, " + EXECUTE_FAIL;
    private static final String PLAYER_BOUGHT = Main.ERROR_START + "active player already bought something, "
            + EXECUTE_FAIL;
    private static final String PLAYER_HARVESTED = Main.ERROR_START + "active player already harvested, "
            + EXECUTE_FAIL;
    private static final String PLAYER_NOT_MOVED = Main.ERROR_START + "active player not moved yet, " + EXECUTE_FAIL;
    private static final String START_FIELD_ERROR = Main.ERROR_START + "the active player landed on start field, "
            + EXECUTE_FAIL;
    private static final String MARKET_FULL = Main.ERROR_START + "the market cannot buy the harvested raw material!";
    private static final String MARKET_EMPTY = Main.ERROR_START + "the market does not have enough stock of the "
            + "specified raw material!";
    private static final String NOT_ENOUGH_GOLD = Main.ERROR_START + "active player does not have enough gold!";
    private static final String NOT_ENOUGH_MATERIALS = Main.ERROR_START + "active player does not have enough "
            + "raw materials!";
    private static final String NO_SUCH_PLAYER = Main.ERROR_START + "there is no such player!";

    private final Pattern pattern;
    private final Pattern generalPattern;
    private final String notMatchedError;

    /**
     * This constructor creates a command for the baker game.
     *
     * @param coreCommandPattern is the String containing the core pattern of a command such as "roll" or "buy"
     * @param parameterPattern is the String containing the parameter pattern of a command such as " [1-6]" or
     *                         " (milk|egg|flour)"
     * @param notMatchedError is the String containing the error message without
     *                        {@link edu.kit.informatik.baker.ui.Main#ERROR_START} to print out
     *                        in case the general pattern excluding the specific parameter pattern of the command
     *                        matches with the input but not the specific pattern including the parameter pattern
     */
    Command(final String coreCommandPattern, final String parameterPattern, final String notMatchedError) {
        this.pattern = Pattern.compile(coreCommandPattern + parameterPattern);
        this.generalPattern = Pattern.compile(coreCommandPattern + GENERAL_PARAMETER_PATTERN);
        this.notMatchedError = Main.ERROR_START + notMatchedError;
    }

    /**
     * This static method tries to match the given input with one of the commands in this enum. If successful, it
     * delivers the input and the {@link edu.kit.informatik.baker.Game} instance to the
     * {@link #process(Matcher, Game)} so that it can process the needs of the command on the given game instance.
     *
     * @param input is the String to be matched with the commands
     * @param game is the Game instance to be made changes on
     * @return a String containing either a success or an error message
     */
    public static String processCommand(String input, Game game) {
        for (final Command command : Command.values()) {
            final Matcher generalMatcher = command.generalPattern.matcher(input);

            if (!generalMatcher.matches()) {
                continue;
            }

            final Matcher matcher = command.pattern.matcher(input);
            return matcher.matches() ? command.process(matcher, game) : command.notMatchedError;
        }
        return UNSUPPORTED_COMMAND;
    }

    private static String winMessage(Game game) {
        if (game.isGameFinished()) {
            return game.getActivePlayerName() + " wins";
        }
        return null;
    }

    /**
     * This abstract method shall be implemented by the members of this enum. It should make changes on the given
     * {@link edu.kit.informatik.baker.Game} instance according to the given input. In case of an error or an
     * unsuccessful process should an error message be returned.
     *
     * @param input is the Matcher instance containing the processed input
     * @param game is the {@link edu.kit.informatik.baker.Game} instance to be made changes on
     * @return a String containing either a success or an error message
     */
    protected abstract String process(Matcher input, Game game);
}

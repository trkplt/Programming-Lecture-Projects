package edu.kit.informatik.baker.ui;

import edu.kit.informatik.baker.Game;
import edu.kit.informatik.baker.player.Player;
import edu.kit.informatik.baker.product.Dish;
import edu.kit.informatik.baker.product.RawMaterial;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Command {

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
    CAN_PREPARE("can-prepare\\?", Main.EMPTY_STRING, "'can-prepare?'" + Main.SHALL_NOT_PROCEED) {
        @Override
        public String process(Matcher input, Game game) {
            if (game.isGameFinished()) {
                return GAME_FINISHED;
            }
            return game.getDoableDishes();
        }
    },
    SHOW_MARKET("show-market", Main.EMPTY_STRING, "'show-market'" + Main.SHALL_NOT_PROCEED) {
        @Override
        public String process(Matcher input, Game game) {
            return game.getMarketSummary();
        }
    },
    SHOW_PLAYER("show-player", Main.SPACE + Player.getPlayerPattern(),
            "'show-player'" + Main.SHALL_HAVE_STRUCTURE
                    + "'show-player <Px>' (x" + Main.INTEGER_IN_INTERVAL + "[1-4])") {
        @Override
        public String process(Matcher input, Game game) {
            String out = game.getPlayerSummary(input.group(Main.COMMAND_FIRST_GROUP));
            return out == null ? NO_SUCH_PLAYER : out;
        }
    },
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

    Command(final String coreCommandPattern, final String parameterPattern, final String notMatchedError) {
        this.pattern = Pattern.compile(coreCommandPattern + parameterPattern);
        this.generalPattern = Pattern.compile(coreCommandPattern + GENERAL_PARAMETER_PATTERN);
        this.notMatchedError = Main.ERROR_START + notMatchedError;
    }

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

    public static String winMessage(Game game) {
        if (game.isGameFinished()) {
            return game.getActivePlayerName() + " wins";
        }
        return null;
    }

    public abstract String process(Matcher input, Game game);
}

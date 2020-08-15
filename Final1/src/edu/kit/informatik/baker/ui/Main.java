package edu.kit.informatik.baker.ui;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.baker.Game;
import edu.kit.informatik.baker.player.Player;

public class Main {

    public static final String EMPTY_STRING = "";
    public static final String SEPARATOR = ";";
    public static final String COMMA = ",";
    public static final String HYPHEN = "-";
    public static final String ERROR_START = "Error, ";
    public static final String PATTERN_GROUP_INIT = "(";
    public static final String PATTERN_GROUP_END = ")";
    public static final String PATTERN_OR = "|";
    public static final String PATTERN_COUNT_INIT = "{";
    public static final String PATTERN_COUNT_END = "}";
    public static final String PATTERN_CHAR_CLASS_INIT = "[";
    public static final String PATTERN_CHAR_CLASS_END = "]";
    public static final String SPACE = " ";
    public static final String INTEGER_IN_INTERVAL = " must be an integer in interval ";
    public static final String SHALL_NOT_PROCEED = " command shall not be proceeded by anything!";
    public static final String SHALL_HAVE_STRUCTURE = " command shall have the following structure: ";
    public static final String FROM_GAME = " must be from the game!)";
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int MINUS_ONE = -1;
    public static final int COMMAND_FIRST_GROUP = ONE;

    private static final int PROGRAM_ARGUMENT_NUMBER = 2;
    private static final int NUM_OF_PLAYERS_ARGS_INDEX = ZERO;
    private static final int BOARD_STRING_ARGS_INDEX = ONE;
    private static final String UNSUPPORTED_NUM_OF_ARGS = ERROR_START + "the program expects 2 arguments!";
    private static final String UNSUPPORTED_FIRST_ARG = ERROR_START
            + "number of players must be given as the first argument as an integer in interval [2,4]";
    private static final String UNSUPPORTED_BOARD = ERROR_START
            + "a valid string representation of board must be given as the second argument!";

    public static void main(String[] args) {
        if (args.length != PROGRAM_ARGUMENT_NUMBER) {
            Terminal.printLine(UNSUPPORTED_NUM_OF_ARGS);
            return;
        }

        int numOfPlayers;
        try {
            numOfPlayers = Integer.parseInt(args[NUM_OF_PLAYERS_ARGS_INDEX]);
        } catch (NumberFormatException e) {
            Terminal.printLine(UNSUPPORTED_FIRST_ARG);
            return;
        }

        if (numOfPlayers < Player.MIN_NUM_OF_PLAYERS || numOfPlayers > Player.MAX_NUM_OF_PLAYERS) {
            Terminal.printLine(UNSUPPORTED_FIRST_ARG);
            return;
        }

        Game game = new Game(numOfPlayers, args[BOARD_STRING_ARGS_INDEX]);

        if (!game.isBoardValid()) {
            Terminal.printLine(UNSUPPORTED_BOARD);
            return;
        }

        while (game.isGameRunning()) {
            String input = Terminal.readLine();
            String output = Command.processCommand(input, game);

            if (output != null) {
                Terminal.printLine(output);
            }
        }
    }
}

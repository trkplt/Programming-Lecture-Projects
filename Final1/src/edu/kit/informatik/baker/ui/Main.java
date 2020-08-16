package edu.kit.informatik.baker.ui;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.baker.Game;
import edu.kit.informatik.baker.player.Player;

/**
 * This is the entry class to the program. First argument checks are maintained in this class. It also has some static
 * final values that are being used program wide.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public final class Main {

    /**
     * This is a String with no characters in it. It can be used to initialize an empty String.
     */
    public static final String EMPTY_STRING = "";

    /**
     * This is a String with a semicolon in it. It can be used in in- and outputs.
     */
    public static final String SEPARATOR = ";";

    /**
     * This is a String with a comma in it. It can be used in patterns.
     */
    public static final String COMMA = ",";

    /**
     * This is a String with a hyphen in it. It can be used in patterns.
     */
    public static final String HYPHEN = "-";

    /**
     * This is a String with a normal left brackets in it. It can be used in patterns as a group initializer.
     */
    public static final String PATTERN_GROUP_INIT = "(";

    /**
     * This is a String with a normal right brackets in it. It can be used in patterns as a group ending.
     */
    public static final String PATTERN_GROUP_END = ")";

    /**
     * This is a String with a logical or character in it. It can be used in patterns.
     */
    public static final String PATTERN_OR = "|";

    /**
     * This is a String with a curly left brackets in it. It can be used in patterns.
     */
    public static final String PATTERN_COUNT_INIT = "{";

    /**
     * This is a String with a curly right brackets in it. It can be used in patterns.
     */
    public static final String PATTERN_COUNT_END = "}";

    /**
     * This is a String with a square left brackets in it. It can be used in patterns as a character class initializer.
     */
    public static final String PATTERN_CHAR_CLASS_INIT = "[";

    /**
     * This is a String with a square right brackets in it. It can be used in patterns as a character class ending.
     */
    public static final String PATTERN_CHAR_CLASS_END = "]";

    /**
     * This is an integer with the value 0.
     */
    public static final int ZERO = 0;

    /**
     * This is an integer with the value 1.
     */
    public static final int ONE = 1;

    /**
     * This is an integer with the value 2.
     */
    public static final int TWO = 2;

    /**
     * This is an integer with the value -1.
     */
    public static final int MINUS_ONE = -1;

    /**
     * This indicates the index of the first group in a matcher.
     */
    protected static final int COMMAND_FIRST_GROUP = ONE;

    /**
     * This is the common error message start.
     */
    protected static final String ERROR_START = "Error, ";

    /**
     * This is a String containing one white space character. It can be used in patterns.
     */
    protected static final String SPACE = " ";

    /**
     * This is a String containing a common part of an error message about intervals.
     */
    protected static final String INTEGER_IN_INTERVAL = " must be an integer in interval ";

    /**
     * This is a String containing a common part of an error message about proceeding character of a command.
     */
    protected static final String SHALL_NOT_PROCEED = " command shall not be proceeded by anything!";

    /**
     * This is a String containing a common part of an error message about the structure of a command.
     */
    protected static final String SHALL_HAVE_STRUCTURE = " command shall have the following structure: ";

    /**
     * This is a String containing a common part of an error message about something being from the game.
     */
    protected static final String FROM_GAME = " must be from the game!)";

    private static final int PROGRAM_ARGUMENT_NUMBER = 2;
    private static final int NUM_OF_PLAYERS_ARGS_INDEX = ZERO;
    private static final int BOARD_STRING_ARGS_INDEX = ONE;
    private static final String UNSUPPORTED_NUM_OF_ARGS = ERROR_START + "the program expects 2 arguments!";
    private static final String UNSUPPORTED_FIRST_ARG = ERROR_START
            + "number of players must be given as the first argument as an integer in interval [2,4]";
    private static final String UNSUPPORTED_BOARD = ERROR_START
            + "a valid string representation of board must be given as the second argument!";

    private Main() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * This is the entry point to the program. First checks on the arguments happen in this method. It also controls
     * the in- and output of the program.
     *
     * @param args is the String array containing the arguments of the program
     */
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

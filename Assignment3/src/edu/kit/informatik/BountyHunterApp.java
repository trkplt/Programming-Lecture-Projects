package edu.kit.informatik;

/**
 * Main class for BountyHunterApp. Contains io operations for the app.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public final class BountyHunterApp {

    //private constructor because no objects of this class shall be created.
    private BountyHunterApp() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * Main method for the app. Contains initial check for the arguments.
     *
     * @param args arguments for the program, must only contain a path to the input text file
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            Terminal.printError("only a path to a text file must be given!");
            return;
        }

        BountyHunterLogic calculator = new BountyHunterLogic(Terminal.readFile(args[0]));

        try {
            Terminal.printLine(calculator.getDroidCount());
        } catch (IllegalArgumentException e) {
            Terminal.printError("the given text file does not meet the conditions!");
        }
    }
}

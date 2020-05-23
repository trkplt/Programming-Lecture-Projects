package edu.kit.informatik;

/**
 * This class is here for logic and maintaining in- and output of the holoalphabetic-check programme.
 * @author Tarik Polat
 * @version 1.0.0
 */
public final class Holoalphabetic {

    /*
    Private because no objects of this class shall be created, this class is here only for logic and maintaining
    in- and output of the holoalphabetic-check programme.
     */
    private Holoalphabetic() { }

    /**
     * Main method for the holoalphabetic-check programme. Inputs are taken care of in this method.
     * @param args arguments of the programme but not needed because commands are taken via command line entries
     *             after the run of the program
     */
    public static void main(String[] args) {
        boolean go = true;

        while (go) {
            String[] arguments = Terminal.readLine().split(" ");

            switch (arguments[0]) {
                case "holoalphabetic?":
                    holoalphabetic(arguments);
                    break;
                case "quit":
                    go = false;
                    break;
                default:
            }
        }
    }

    /**
     * The logic of the holoalphabetic-check programme. The outputs are taken care of in this method.
     * @param args the exact args from the main method
     */
    private static void holoalphabetic(String[] args) {
        int containedLetterCount = 0;
        int secondAppearedCount = 0;
        String sentence = "";

        for (int i = 1; i < args.length; i++) {
            sentence += args[i];
        }

        for (int i = 65; i < 91; i++) {
            int firstLower = sentence.indexOf(i);
            int lastLower = sentence.lastIndexOf(i);
            int firstUpper = sentence.indexOf(i + 32);
            int lastUpper = sentence.lastIndexOf(i + 32);

            boolean fLExists = firstLower != -1;
            boolean lLExists = lastLower != firstLower;
            boolean fUExists = firstUpper != -1;
            boolean lUExists = lastUpper != firstUpper;

            if (fLExists | fUExists) {
                containedLetterCount++;
            }

            if ((fLExists | fUExists)
                    & ((fLExists & lLExists) | (fUExists & lUExists) | (fLExists & fUExists))) {
                secondAppearedCount++;
            }
        }

        boolean completeUse = containedLetterCount == 26;

        if (completeUse & secondAppearedCount > 0) {
            Terminal.printLine("pangram");
        } else if (completeUse) {
            Terminal.printLine("isogram");
        } else {
            Terminal.printLine("false");
        }
    }
}

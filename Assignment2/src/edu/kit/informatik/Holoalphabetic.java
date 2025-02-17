package edu.kit.informatik;

/**
 * This class is here for logic and maintaining in- and output of the holoalphabetic-check programme.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public final class Holoalphabetic {

    /*
    Private because no objects of this class shall be created, this class is here only for logic and maintaining
    in- and output of the holoalphabetic-check programme.
     */
    private Holoalphabetic() {
    }

    /**
     * Main method for the holoalphabetic-check programme. Inputs are taken care of in this method.
     *
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
     *
     * @param args the exact args from the main method
     */
    private static void holoalphabetic(String[] args) {
        /*
        these ints are counters. contained... must be exactly 26 for both isogram and pangram
        and second... must be exactly 0 for isogram and greater than 0 for pangram.
         */
        int containedLetterCount = 0;
        int secondAppearedCount = 0;
        String sentence = "";

        //gathering the words
        for (int i = 1; i < args.length; i++) {
            sentence += args[i];
        }

        //lower so that we don't have to deal with both lower and upper cases
        sentence = sentence.toLowerCase();

        //lower case alphabetic chars start with the value 97 and end with 122 incl.
        for (int i = 97; i < 123; i++) {
            int first = sentence.indexOf(i);
            int other = sentence.lastIndexOf(i);

            boolean firstExists = first != -1;
            boolean otherExists = other != -1 && other != first;

            if (firstExists) {
                containedLetterCount++;
            }

            if (otherExists) {
                secondAppearedCount++;
            }
        }

        //true if all the letters are used
        boolean completeUse = containedLetterCount == 26;

        //secondAppearedCount should be greater than 0 if at least one letter is used at least twice
        if (completeUse & secondAppearedCount > 0) {
            Terminal.printLine("pangram");
        } else if (completeUse) {
            Terminal.printLine("isogram");
        } else {
            Terminal.printLine("false");
        }
    }
}

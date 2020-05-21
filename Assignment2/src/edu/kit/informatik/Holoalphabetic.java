package edu.kit.informatik;

public class Holoalphabetic {

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

                if ((fLExists & lLExists) | (fUExists & lUExists)) {
                    secondAppearedCount++;
                }
            }
        }

        if (containedLetterCount == 26) {
            if (secondAppearedCount > 0) {
                Terminal.printLine("pangram");
            } else {
                Terminal.printLine("isogram");
            }
        } else {
            Terminal.printLine("false");
        }
    }
}

package edu.kit.informatik;

public class DictionaryApp {

    private static Dictionary dictionary;

    private static void add(String[] input) {
        //TODO: IMPLEMENT!
    }

    private static void remove(String[] input) {
        //TODO: IMPLEMENT!
    }

    private static void printAll() {
        //TODO: IMPLEMENT!
    }

    private static void printStartsWith(String[] input) {
        if (input[1].toCharArray().length != 1 || !DictionaryUtil.getAlphabet().contains(input[1])) {
            Terminal.printError("a single letter from German alphabet "
                    + "must be given for the command 'print <letter>'! "
                    + "Alternatively the command 'print' can be used.");
            return;
        }

        //TODO: IMPLEMENT!
    }

    private static void print(String[] input) {
        if (input.length == 1) {
            printAll();
            return;
        }

        if (input.length != 2) {
            Terminal.printError("the given print command is not valid! "
                    + "Supported print commands are 'print' and 'print <letter>'.");
            return;
        }

        printStartsWith(input);
    }

    private static void translateWord() {
        //TODO: IMPLEMENT!
    }

    private static void translateSentence() {
        //TODO: IMPLEMENT!
    }

    private static void translate(String[] input) {
        //TODO: IMPLEMENT!
    }

    private static boolean quitValid(String[] input) {
        //TODO: IMPLEMENT!
        return true;
    }

    private static void processFile(String[] args) {
        String[] lines = Terminal.readFile(args[0]);
        dictionary = new Dictionary();

        for (String line : lines) {
            String[] words = line.split(" - ");

            if (words.length != 2) {
                Terminal.printError("the text file does not meet the conditions!");
                dictionary = new Dictionary();
                break;
            }

            try {
                dictionary.add(new Word(words[0]), new Word(words[1]));
            } catch (IllegalArgumentException e) {
                Terminal.printError("the text file contains at least one word "
                        + "containing at least one character outside of German alphabet!");
                dictionary = new Dictionary();
                break;
            } catch (NullPointerException e) {
                Terminal.printError("the text file does not meet the conditions!");
                dictionary = new Dictionary();
                break;
            }
        }
    }

    private static void processCommand() {
        boolean go = true;
        String[] input = Terminal.readLine().split(" ");

        if (input.length == 0 || input[0].equals("")) {
            Terminal.printError("no command has been given!");
            processCommand();
            return;
        }

        switch (input[0]) {
            case "add":
                add(input);
                break;
            case "remove":
                remove(input);
                break;
            case "print":
                print(input);
                break;
            case "translate":
                translate(input);
                break;
            case "quit":
                go = !quitValid(input);
                break;
            default:
                Terminal.printError("the given command is not supported!");
        }

        if (go) {
            processCommand();
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            Terminal.printError("only a path to a text file must be given as a parameter!");
            dictionary = new Dictionary();
            processCommand();
            return;
        }

        processFile(args);
        processCommand();
    }
}

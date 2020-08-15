package edu.kit.informatik;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class for the DictionaryApp. Contains methods for io and input check.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public final class DictionaryApp {

    private static Dictionary dictionary;

    //private constructor because no objects of this class shall be created.
    private DictionaryApp() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    //input check for add command and connection to add operation of the Dictionary
    private static void add(String[] input) {
        if (input.length != 3) {
            Terminal.printError("add command must have the form: 'add <word> <translated_word>'");
            return;
        }

        try {
            if (!dictionary.add(new Word(input[1]), new Word(input[2]))) {
                Terminal.printError("the given couple of words are already included in the dictionary!");
            }
        } catch (IllegalArgumentException e) {
            Terminal.printError("at least one of the given words contains a character outside of German alphabet!");
        } catch (NullPointerException e) {
            Terminal.printError("the given command does not meet the conditions!");
        }
    }

    //input check for remove command and connection to remove operation of the Dictionary
    private static void remove(String[] input) {
        if (input.length != 2) {
            Terminal.printError("remove command must have the form: 'remove <word>'");
            return;
        }

        try {
            if (!dictionary.remove(new Word(input[1]))) {
                Terminal.printError("the given word is not included in the dictionary!");
            }
        } catch (IllegalArgumentException e) {
            Terminal.printError("at least one of the given words contains a character outside of German alphabet!");
        } catch (NullPointerException e) {
            Terminal.printError("the given command does not meet the conditions!");
        }
    }

    //helper method to get String representation of the word and its translations
    private static String getWords(Word origin) throws IllegalArgumentException, NullPointerException {
        String output = origin.getWord() + " - ";

        for (Word translation : dictionary.getTranslations(origin)) {
            output += translation.getWord() + ",";
        }

        output = output.substring(0, output.length() - 1);
        output += System.getProperty("line.separator");
        return output;
    }

    //method to print all the words and their translations in the dictionary
    private static void printAll() {
        String output = "";
        List<Word> origins = dictionary.getOriginWords();

        for (Word origin : origins) {
            output += getWords(origin);
        }

        if (output.length() > 0) {
            Terminal.printLine(output.substring(0, output.length() - 1));
        } else {
            Terminal.printError("no words in dictionary!");
        }
    }

    //input check for print '<letter>' and printing action if correct
    private static void printStartsWith(String input) {
        if (input.length() != 1 || !DictionaryUtil.getAlphabet().contains(input)) {
            Terminal.printError("a single letter from German alphabet "
                    + "must be given for the command 'print <letter>'! "
                    + "Alternatively the command 'print' can be used.");
            return;
        }

        char wanted = input.toLowerCase().charAt(0);
        List<Word> origins = dictionary.getOriginWords();
        String output = "";

        for (Word origin : origins) {
            if (origin.getWord().toLowerCase().charAt(0) == wanted) {
                output += getWords(origin);
            }
        }

        if (output.length() > 0) {
            Terminal.printLine(output.substring(0, output.length() - 1));
        } else {
            Terminal.printError("no words in dictionary starting with the given letter!");
        }
    }

    //input check for print commands and connection to corresponding method
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

        printStartsWith(input[1]);
    }

    //input check for 'translate <word>' and printing action if correct
    private static void translateWord(String input) {
        List<Word> translations;

        try {
            translations = dictionary.getTranslations(new Word(input));
        } catch (IllegalArgumentException e) {
            Terminal.printError("the given word includes at least one character outside of German alphabet!");
            return;
        } catch (NullPointerException e) {
            Terminal.printError("the given command does not meet the conditions!");
            return;
        }

        if (translations == null) {
            Terminal.printError("the given word is not included in the dictionary!");
            return;
        }

        String output = "";
        for (Word translation : translations) {
            output += translation.getWord() + ",";
        }

        if (output.length() > 0) {
            Terminal.printLine(output.substring(0, output.length() - 1));
        } else {
            Terminal.printError("the given word is not included in the dictionary!");
        }
    }

    //helper method to build sentences
    private static List<String> sentenceBuilder(String start, List<Word> sequels) {
        List<String> sentences = new ArrayList<>();

        for (Word sequel : sequels) {
            sentences.add(start + " " + sequel.getWord());
        }

        if (sentences.size() == 0) {
            return null;
        } else {
            return sentences;
        }
    }

    //input check for 'translate <sentence>' and print action if correct
    private static void translateSentence(String[] input) {
        List<List<Word>> possibleTranslations = new ArrayList<>();

        for (int i = 1; i < input.length; i++) {
            List<Word> translations;

            try {
                translations = dictionary.getTranslations(new Word(input[i]));
            } catch (IllegalArgumentException e) {
                Terminal.printError("at least one of the words in the sentence contains "
                        + "at least one character outside of German alphabet!");
                return;
            } catch (NullPointerException e) {
                Terminal.printError("the command does not meet the consitions!");
                return;
            }

            if (translations == null) {
                Terminal.printError("at least one word is not included in the dictionary!");
                return;
            }
            possibleTranslations.add(translations);
        }

        List<String> sentences = new ArrayList<>();
        sentences.add("");

        try {
            for (List<Word> translations : possibleTranslations) {
                List<String> newSentences = new ArrayList<>();

                for (String sentence : sentences) {
                    newSentences.addAll(sentenceBuilder(sentence, translations));
                }

                sentences = newSentences;
            }
        } catch (NullPointerException e) {
            Terminal.printError("at least one word is not included in the dictionary!");
        }

        String output = "";
        for (String sentence : sentences) {
            output += sentence.substring(1) + System.getProperty("line.separator");
        }

        Terminal.printLine(output.substring(0, output.length() - 1));
    }

    //input check for translate commands and connection to corresponding methods
    private static void translate(String[] input) {
        if (input.length == 1) {
            Terminal.printError("supported translate commands are 'translate <word>' and 'translate <sentence>'!");
        } else if (input.length == 2) {
            translateWord(input[1]);
        } else {
            translateSentence(input);
        }
    }

    //input check for 'quit' command
    private static boolean quitValid(String[] input) {
        if (input.length != 1) {
            Terminal.printError("quit command shall not be followed by anything!");
            return false;
        }
        return true;
    }

    //method to check the input and to construct a dictionary if correct
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

    //method to check the commands and connect to corresponding methods
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

    /**
     * Main method for the app.
     *
     * @param args arguments for the app, must contain only the path to the input text file
     */
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

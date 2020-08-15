package edu.kit.informatik;

/**
 * Contains utility methods for DictinaryApp.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public final class DictionaryUtil {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜẞabcdefghijklmnopqrstuvwxyzäöüß";
    private static final String SPECIALS = "ÄÖÜẞäöüß";

    //private constructor because no objects of this class shall be created, this is a utility class.
    private DictionaryUtil() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * To get the German alphabet as a String.
     *
     * @return German alphabet as a String
     */
    public static String getAlphabet() {
        return ALPHABET;
    }

    /**
     * Checks if the given word is only made of the letters from German alphabetç
     *
     * @param word the word to be checked
     * @return true if the word only contains the letters from the German alphabet,
     * false if it contains at least one character out of the German alphabet
     * @throws NullPointerException if the given word is null
     */
    public static boolean isAlphabet(Word word) throws NullPointerException {
        String string = word.getWord();

        for (char letter : string.toCharArray()) {
            if (ALPHABET.contains(String.valueOf(letter))) {
                continue;
            }
            return false;
        }
        return true;
    }

    //transforms the word to a easier to check state for comparison
    private static String transform(String word) {
        String transformed = "";
        for (SpecialCharacters special : SpecialCharacters.values()) {
            transformed = word.replaceAll(special.getLetter(), special.getReplacement());
        }
        return transformed;
    }

    /**
     * Compares two words lexicographically (for German words)
     *
     * @param firstWord  first word to be compared
     * @param secondWord second word to be compared
     * @return a negative integer if firstWord has a higher priority,
     * a positive integer if the secondWord has a higher priority,
     * zero if {@code firstWord.equals(secondWord)} returns true
     */
    public static int compare(Word firstWord, Word secondWord) {
        if (firstWord.equals(secondWord)) {
            return 0;
        }

        String firstTransformed = transform(firstWord.getWord());
        String secondTransformed = transform(secondWord.getWord());

        String firstLower = firstTransformed.toLowerCase();
        String secondLower = secondTransformed.toLowerCase();

        for (int i = 0; i < Integer.min(firstLower.length(), secondLower.length()); i++) {
            if (firstLower.charAt(i) < secondLower.charAt(i)) {
                return -1;
            } else if (firstLower.charAt(i) > secondLower.charAt(i)) {
                return 1;
            }
        }

        if (firstLower.length() < secondLower.length()) {
            return -1;
        } else if (firstLower.length() > secondLower.length()) {
            return 1;
        }

        for (int i = 0; i < firstTransformed.length(); i++) {
            if (firstTransformed.charAt(i) > secondTransformed.charAt(i)) {
                return -1;
            } else if (firstTransformed.charAt(i) < secondTransformed.charAt(i)) {
                return 1;
            }
        }

        if (firstTransformed.equals(firstWord.getWord()) && secondTransformed.equals(secondWord.getWord())) {
            return 0;
        }

        String first = firstWord.getWord();
        String second = secondWord.getWord();

        for (int i = 0; i < Integer.min(first.length(), second.length()); i++) {
            boolean firstSpecial = SPECIALS.contains(String.valueOf(first.charAt(i)));
            boolean secondSpecial = SPECIALS.contains(String.valueOf(second.charAt(i)));

            if (!firstSpecial && secondSpecial) {
                return -1;
            } else if (firstSpecial && !secondSpecial) {
                return 1;
            }
        }

        return 0;
    }
}

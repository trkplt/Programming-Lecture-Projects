package edu.kit.informatik;

public class DictionaryUtil {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜẞabcdefghijklmnopqrstuvwxyzäöüß";
    private static final String SPECIALS = "ÄÖÜẞäöüß";

    public static String getAlphabet() {
        return ALPHABET;
    }

    public static boolean isAlphabet(Word word) {
        String string = word.getWord();

        for (char letter : string.toCharArray()) {
            if (ALPHABET.contains(String.valueOf(letter))) {
                continue;
            }
            return false;
        }
        return true;
    }

    //TODO: WORKS CORRECTLY?
    private static String transform(String word) {
        String transformed = "";
        for (SpecialCharacters special : SpecialCharacters.values()) {
            transformed = word.replaceAll(special.getLetter(), special.getReplacement());
        }
        return transformed;
    }

    //TODO: IMPLEMENT
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

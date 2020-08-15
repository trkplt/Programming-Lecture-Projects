package edu.kit.informatik;

/**
 * Enum to deal with the special characters in the German alphabet
 * so that they are easier to compare in the dictionary.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public enum SpecialCharacters {

    /**
     *
     */
    upperAWithDots("Ä", "A"),
    /**
     *
     */
    upperOWithDots("Ö", "O"),
    /**
     *
     */
    upperUWithDots("Ü", "U"),
    /**
     *
     */
    upperEszett("ẞ", "Ss"),
    /**
     *
     */
    lowerAWithDots("ä", "a"),
    /**
     *
     */
    lowerOWithDots("ö", "o"),
    /**
     *
     */
    lowerUWithDots("ü", "u"),
    /**
     *
     */
    lowerEszett("ß", "ss");

    private final String letter;
    private final String replacement;

    SpecialCharacters(String letter, String replacement) {
        this.letter = letter;
        this.replacement = replacement;
    }

    /**
     * To get the corresponding letter.
     *
     * @return the letter as a String
     */
    String getLetter() {
        return letter;
    }

    /**
     * To get the replacement of the corresponding letter.
     *
     * @return the replacement as a String
     */
    String getReplacement() {
        return replacement;
    }
}

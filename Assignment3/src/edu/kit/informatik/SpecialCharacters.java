package edu.kit.informatik;

public enum SpecialCharacters {
    //TODO: WRITE JAVADOC
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

    String getLetter() {
        return letter;
    }

    String getReplacement() {
        return replacement;
    }
}

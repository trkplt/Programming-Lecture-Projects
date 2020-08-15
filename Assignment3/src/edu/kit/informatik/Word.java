package edu.kit.informatik;

import java.util.Objects;

/**
 * Represents a word. Contains methods to retrieve information about it.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public class Word implements Comparable<Word> {
    private final String word;

    /**
     * Constructs a Word object with the given string.
     *
     * @param word the string to be represented
     */
    public Word(String word) {
        this.word = word;
    }

    /**
     * To get the represented word as a String.
     *
     * @return the string representation of the word
     */
    public String getWord() {
        return word;
    }

    /**
     * Checks if this object is equal to the given one.
     *
     * @param o the other object
     * @return true if the Strings of the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word other = (Word) o;
        return Objects.equals(getWord(), other.getWord());
    }

    /**
     * To get a hashcode of the word.
     *
     * @return the hashcode of the word
     */
    @Override
    public int hashCode() {
        return Objects.hash(getWord());
    }

    /**
     * To compare this word with the other word.
     *
     * @param other other word
     * @return a negative integer if this word has a higher priority,
     * a positive integer if the other word has a higher priority,
     * zero if {@code this.equals(other)} returns true
     */
    @Override
    public int compareTo(Word other) {
        return DictionaryUtil.compare(this, other);
    }
}

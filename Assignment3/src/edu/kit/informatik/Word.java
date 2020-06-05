package edu.kit.informatik;

import java.util.Objects;

public class Word implements Comparable<Word> {
    private final String word;

    public Word(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word other = (Word) o;
        return Objects.equals(getWord(), other.getWord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWord());
    }

    @Override
    public int compareTo(Word other) {
        return DictionaryUtil.compare(this, other);
    }
}

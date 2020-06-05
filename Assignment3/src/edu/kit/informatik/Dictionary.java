package edu.kit.informatik;

import java.util.TreeMap;
import java.util.TreeSet;

public class Dictionary {

    private final TreeMap<Word, TreeSet<Word>> dictionary;

    public Dictionary() {
        dictionary = new TreeMap<>();
    }

    public boolean add(Word origin, Word translation) throws IllegalArgumentException, NullPointerException {
        if (origin == null || translation == null) {
            throw new NullPointerException();
        }

        if (!DictionaryUtil.isAlphabet(origin) || !DictionaryUtil.isAlphabet(translation)) {
            throw new IllegalArgumentException();
        }

        if (dictionary.containsKey(origin)) {
            return dictionary.get(origin).add(translation);
        }

        TreeSet<Word> value = new TreeSet<>();
        value.add(translation);
        return dictionary.put(origin, value) == null;
    }

    public boolean remove(Word word) {
        return dictionary.remove(word) != null;
    }

    public TreeMap<Word, TreeSet<Word>> getDictionary() {
        return dictionary;
    }

    public TreeSet<Word> getTranslations(Word word) {
        return dictionary.get(word);
    }
}

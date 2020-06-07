package edu.kit.informatik;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class Dictionary {

    private final TreeMap<Word, TreeSet<Word>> dictionary;

    public Dictionary() {
        dictionary = new TreeMap<>();
    }

    public boolean add(Word origin, Word translation) throws IllegalArgumentException, NullPointerException {
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

    public boolean remove(Word word) throws IllegalArgumentException, NullPointerException {
        if (!DictionaryUtil.isAlphabet(word)) {
            throw new IllegalArgumentException();
        }

        return dictionary.remove(word) != null;
    }

    public List<Word> getOriginWords() {
        return new ArrayList<>(dictionary.keySet());
    }

    public List<Word> getTranslations(Word word) throws IllegalArgumentException, NullPointerException {
        if (!DictionaryUtil.isAlphabet(word)) {
            throw new IllegalArgumentException();
        }
        TreeSet<Word> translations = dictionary.get(word);

        if (translations == null) {
            return null;
        } else {
            return new ArrayList<>(translations);
        }
    }
}

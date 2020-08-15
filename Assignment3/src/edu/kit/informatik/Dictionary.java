package edu.kit.informatik;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Contains the dictionary and basic operations on it for the DictionaryApp.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public class Dictionary {

    //contains words and their translations
    private final TreeMap<Word, TreeSet<Word>> dictionary;

    /**
     * Constructs a Dictionary object with no words in it.
     */
    public Dictionary() {
        dictionary = new TreeMap<>();
    }

    /**
     * To add a word and its translation to the dictionary.
     *
     * @param origin      the word from the origin language
     * @param translation the word from the destination language
     * @return true if the adding operation successful, false if the dictionary already contains the couple
     * @throws IllegalArgumentException if at least one of the given words is not German-alphabetic
     * @throws NullPointerException     if at least one of the parameters is null
     */
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

    /**
     * To remove the given word and its translations from the dictionary.
     *
     * @param word the word to be removed
     * @return true if the remove actions successful, false if the dictionary does not contains the word
     * @throws IllegalArgumentException if the given word is not German-alphabetic
     * @throws NullPointerException     if the parameter is null
     */
    public boolean remove(Word word) throws IllegalArgumentException, NullPointerException {
        if (!DictionaryUtil.isAlphabet(word)) {
            throw new IllegalArgumentException();
        }

        return dictionary.remove(word) != null;
    }

    /**
     * To get the words from origin language which contained in the dictionary.
     *
     * @return a list containing origin words
     */
    public List<Word> getOriginWords() {
        return new ArrayList<>(dictionary.keySet());
    }

    /**
     * To get the translations of the given word in the destination language.
     *
     * @param word the word from origin language to be translated
     * @return the list containing translations of the word, null if the dictionary does not contain the word
     * @throws IllegalArgumentException if the given word is not German-alphabetic
     * @throws NullPointerException     if the parameter is null
     */
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

package edu.kit.informatik;

/**
 * This class includes static methods to check some features of strings.
 * @author Tarık Polat
 * @version 1.0.0
 */
public class StringUtility {

    /**
     * This method takes a string and gives back the reversed version of it.
     * @param word is the string to be reversed.
     * @return the reversed string.
     */
    public static String reverse(String word) {

        String copyWord = word;
        String newWord = "";

        while (copyWord.length() > 0) {

            newWord += copyWord.substring(copyWord.length() - 1);

            if (copyWord.length() > 1) {
                copyWord = copyWord.substring(0, copyWord.length() - 1);
            } else {
                copyWord = "";
            }
        }

        return newWord;
    }

    /**
     * This method checks if a string is a palindrome.
     * @param word is the string to be checked.
     * @return true if the word is a palindrome, false otherwise.
     */
    public static boolean checkPalindrome(String word) {

        return word.equals(reverse(word));
    }

    /**
     * This method deletes the character at the specified index and returns the remaining characters.
     * @param word is the initial string.
     * @param index is the index at which the character will be deleted.
     * @return the string made out of remaining characters with the same order as the initial word.
     */
    public static String removeCharacter(String word, int index) {

        String newWord = "";

        for (int i = 0; i < word.length(); i++) {
            if (index == i) {
                if (i < word.length() - 1) {
                    newWord += word.substring(i + 1);
                    break;
                }
            } else {
                newWord += word.substring(i, i + 1);
            }
        }

        return newWord;
    }

    /**
     * This method checks if two words are anagrams.
     * @param word1 is the first string to be checked.
     * @param word2 is the second string to be checked.
     * @return true if the strings are anagrams, false otherwise.
     */
    public static boolean checkAnagram(String word1, String word2) {

        String copy1 = word1;
        String copy2 = word2;

        for (int i = 0; i < word1.length(); i++) {
            int j = copy2.lastIndexOf(word1.charAt(i));
            if (j != -1) {
                copy2 = removeCharacter(copy2, j);
            }
        }

        for (int i = 0; i < word2.length(); i++) {
            int j = copy1.lastIndexOf(word2.charAt(i));
            if (j != -1) {
                copy1 = removeCharacter(copy1, j);
            }
        }

        return (copy1.length() == 0 && copy2.length() == 0);
    }

    /**
     * This method takes a string and returns it by lowering its first character.
     * @param word is the string whose first character will be lowered.
     * @return the string with the lowered first character.
     */
    public static String lowercase(String word) {

        String headless = "";

        if (word.length() > 1) {
            headless = word.substring(1);
        }

        return word.substring(0, 1).toLowerCase() + headless;
    }

    /**
     * This method returns the count of the given character in the given string.
     * @param word is the string to be checked.
     * @param character is the character to be counted.
     * @return the count of characters in the given string.
     */
    public static int countCharacter(String word, char character) {

        String copyWord = word;
        int count = 0;
        int index;

        while ((index = copyWord.lastIndexOf(character)) != -1) {
            copyWord = removeCharacter(copyWord, index);
            count++;
        }

        return count;
    }
}

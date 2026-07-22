package no08_string.practice.palindrome_anagram_analyzer;

/**
 * A utility class to analyze strings for palindrome and anagram properties.
 */
public class PalindromeAnagramAnalyzer {

    /**
     * Checks if the given string is a palindrome.
     * A palindrome is a word, phrase, number, or other sequence of characters 
     * that reads the same forward and backward.
     * 
     * For this exercise, you must:
     * - Ignore case (e.g. 'A' == 'a').
     * - Ignore all non-alphanumeric characters (spaces, punctuation, symbols).
     * - If string is null, return false.
     * - An empty or single-character alphanumeric string is a palindrome.
     *
     * Example:
     * "A man, a plan, a canal: Panama" -> true
     * "race a car" -> false
     *
     * @param str the string to analyze
     * @return true if the string is a palindrome under the rules, false otherwise
     */
    public static boolean isPalindrome(String str) {
        // TODO: Implement the two-pointer palindrome check.
        return false;
    }

    /**
     * Checks if two strings are anagrams of each other.
     * An anagram is a word or phrase formed by rearranging the letters of a different
     * word or phrase, typically using all the original letters exactly once.
     *
     * For this exercise, you must:
     * - Ignore case (e.g. 'A' == 'a').
     * - Ignore all non-alphabetic characters (spaces, punctuation, digits).
     * - If either string is null, return false.
     *
     * Example:
     * "Listen" and "Silent" -> true
     * "Astronomer" and "Moon starer" -> true
     * "hello" and "bello" -> false
     *
     * @param str1 the first string
     * @param str2 the second string
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean areAnagrams(String str1, String str2) {
        // TODO: Implement the anagram check using letter frequency counts.
        return false;
    }
}

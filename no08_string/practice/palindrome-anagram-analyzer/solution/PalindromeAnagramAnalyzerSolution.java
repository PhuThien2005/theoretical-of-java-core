package no08_string.practice.palindrome_anagram_analyzer;

/**
 * Reference solution for PalindromeAnagramAnalyzerSolution.
 */
public class PalindromeAnagramAnalyzerSolution {

    /**
     * Checks if the given string is a palindrome.
     * We use a two-pointer approach starting from both ends of the string.
     * Non-alphanumeric characters are skipped dynamically.
     */
    public static boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }

        int left = 0;
        int right = str.length() - 1;

        while (left < right) {
            char leftChar = str.charAt(left);
            char rightChar = str.charAt(right);

            // Skip non-alphanumeric characters from left
            if (!Character.isLetterOrDigit(leftChar)) {
                left++;
            }
            // Skip non-alphanumeric characters from right
            else if (!Character.isLetterOrDigit(rightChar)) {
                right--;
            }
            // Both are alphanumeric; compare ignoring case
            else {
                if (Character.toLowerCase(leftChar) != Character.toLowerCase(rightChar)) {
                    return false;
                }
                left++;
                right--;
            }
        }

        return true;
    }

    /**
     * Checks if two strings are anagrams of each other.
     * We use an alphabet counter array of size 26 (for 'a' through 'z').
     * Increment for the first string, and decrement for the second string.
     */
    public static boolean areAnagrams(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return false;
        }

        int[] letterCounts = new int[26];

        // Count letters in str1
        for (int i = 0; i < str1.length(); i++) {
            char c = Character.toLowerCase(str1.charAt(i));
            if (c >= 'a' && c <= 'z') {
                letterCounts[c - 'a']++;
            }
        }

        // Subtract count for letters in str2
        for (int i = 0; i < str2.length(); i++) {
            char c = Character.toLowerCase(str2.charAt(i));
            if (c >= 'a' && c <= 'z') {
                letterCounts[c - 'a']--;
            }
        }

        // If the strings are anagrams, the net frequency of every letter must be 0
        for (int count : letterCounts) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }
}

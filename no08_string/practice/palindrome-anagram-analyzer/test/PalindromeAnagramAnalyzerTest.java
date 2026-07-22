package no08_string.practice.palindrome_anagram_analyzer;

/**
 * Test runner for PalindromeAnagramAnalyzer.
 */
public class PalindromeAnagramAnalyzerTest {

    public static void main(String[] args) {
        try {
            testIsPalindrome();
            testAreAnagrams();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(boolean expected, boolean actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testIsPalindrome() {
        // Palindromes
        assertEquals(true, PalindromeAnagramAnalyzer.isPalindrome("A man, a plan, a canal: Panama"), "Standard complex palindrome");
        assertEquals(true, PalindromeAnagramAnalyzer.isPalindrome("racecar"), "Simple palindrome");
        assertEquals(true, PalindromeAnagramAnalyzer.isPalindrome("No 'x' in Nixon"), "Palindrome with punctuation");
        assertEquals(true, PalindromeAnagramAnalyzer.isPalindrome(""), "Empty string palindrome");
        assertEquals(true, PalindromeAnagramAnalyzer.isPalindrome("a"), "Single character palindrome");
        assertEquals(true, PalindromeAnagramAnalyzer.isPalindrome("121"), "Numeric palindrome");

        // Non-palindromes
        assertEquals(false, PalindromeAnagramAnalyzer.isPalindrome("race a car"), "Non-palindrome");
        assertEquals(false, PalindromeAnagramAnalyzer.isPalindrome("hello"), "Non-palindrome hello");
        assertEquals(false, PalindromeAnagramAnalyzer.isPalindrome(null), "Null string is not a palindrome");
    }

    private static void testAreAnagrams() {
        // Anagrams
        assertEquals(true, PalindromeAnagramAnalyzer.areAnagrams("Listen", "Silent"), "Listen and Silent");
        assertEquals(true, PalindromeAnagramAnalyzer.areAnagrams("Astronomer", "Moon starer"), "Astronomer and Moon starer");
        assertEquals(true, PalindromeAnagramAnalyzer.areAnagrams("Dormitory", "dirty room"), "Dormitory and dirty room");
        assertEquals(true, PalindromeAnagramAnalyzer.areAnagrams("", ""), "Empty strings are anagrams");

        // Non-anagrams
        assertEquals(false, PalindromeAnagramAnalyzer.areAnagrams("hello", "bello"), "hello and bello");
        assertEquals(false, PalindromeAnagramAnalyzer.areAnagrams("apple", "peal"), "apple and peal (different letter counts)");
        assertEquals(false, PalindromeAnagramAnalyzer.areAnagrams("a", "b"), "a and b");
        assertEquals(false, PalindromeAnagramAnalyzer.areAnagrams(null, "abc"), "Null first string");
        assertEquals(false, PalindromeAnagramAnalyzer.areAnagrams("abc", null), "Null second string");
    }
}

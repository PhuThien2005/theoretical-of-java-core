import java.util.Arrays;
import java.util.List;

/**
 * Test runner for CardDeckSimulator.
 */
public class CardDeckSimulatorTest {

    public static void main(String[] args) {
        try {
            testDeckCreation();
            testHandValueCalculation();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testDeckCreation() {
        List<Card> deck = CardDeckSimulator.createDeck();
        assertEquals(52, deck.size(), "Deck should have exactly 52 cards");

        // Verify count of each suit is 13
        int clubs = 0, diamonds = 0, hearts = 0, spades = 0;
        for (Card card : deck) {
            switch (card.getSuit()) {
                case CLUBS -> clubs++;
                case DIAMONDS -> diamonds++;
                case HEARTS -> hearts++;
                case SPADES -> spades++;
            }
        }

        assertEquals(13, clubs, "13 Clubs");
        assertEquals(13, diamonds, "13 Diamonds");
        assertEquals(13, hearts, "13 Hearts");
        assertEquals(13, spades, "13 Spades");
    }

    private static void testHandValueCalculation() {
        // Create a hand: Ace, Jack, and Two
        // Values: Ace (11) + Jack (10) + Two (2) = 23
        List<Card> hand = Arrays.asList(
            new Card(Suit.HEARTS, Rank.ACE),
            new Card(Suit.SPADES, Rank.JACK),
            new Card(Suit.CLUBS, Rank.TWO)
        );

        int handValue = CardDeckSimulator.calculateHandValue(hand);
        assertEquals(23, handValue, "Hand value (Ace + Jack + Two) should be 23");

        // Hand with nulls/empty
        assertEquals(0, CardDeckSimulator.calculateHandValue(null), "Null hand should return 0");
    }
}

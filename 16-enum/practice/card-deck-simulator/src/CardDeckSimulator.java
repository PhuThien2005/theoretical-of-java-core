import java.util.List;

/**
 * Starter template for a card game helper using enums.
 */
public class CardDeckSimulator {

    /**
     * Creates and returns a full deck of 52 playing cards.
     * Contains exactly one card for every combination of Suit and Rank.
     */
    public static List<Card> createDeck() {
        // TODO: Generate and return 52 cards (nested loop over Suit and Rank values)
        return null;
    }

    /**
     * Computes the total point value of a hand of cards.
     */
    public static int calculateHandValue(List<Card> hand) {
        // TODO: Sum the values of ranks of cards in the hand (handle null elements safely)
        return 0;
    }
}

/**
 * Represents the Suit of a card.
 */
enum Suit {
    CLUBS,
    DIAMONDS,
    HEARTS,
    SPADES
}

/**
 * Represents the Rank of a card.
 * Each Rank has an associated point value.
 */
enum Rank {
    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
    JACK(10), QUEEN(10), KING(10), ACE(11);

    public final int value;

    Rank(int value) {
        // TODO: Initialize field. For starter, assign 0 so it compiles.
        this.value = 0;
    }
}

/**
 * Represents a playing card.
 */
class Card {
    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

package no16_enum.practice.card_deck_simulator;

import java.util.ArrayList;
import java.util.List;

/**
 * Reference solution for CardDeckSimulatorSolution.
 */
public class CardDeckSimulatorSolution {

    public static List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deck.add(new Card(suit, rank));
            }
        }
        return deck;
    }

    public static int calculateHandValue(List<Card> hand) {
        if (hand == null) {
            return 0;
        }
        int totalValue = 0;
        for (Card card : hand) {
            if (card != null) {
                totalValue += card.getRank().value;
            }
        }
        return totalValue;
    }
}

enum Suit {
    CLUBS,
    DIAMONDS,
    HEARTS,
    SPADES
}

enum Rank {
    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
    JACK(10), QUEEN(10), KING(10), ACE(11);

    // Declared public final int to match starter
    public final int value;

    Rank(int value) {
        this.value = value;
    }
}

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

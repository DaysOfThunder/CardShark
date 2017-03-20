// ======================================================================
// Filename: CardDeck.java
// Description: Implement an interface for accessing a standard 52 card
// deck.
// Created: Mon Mar 20 10:00:29 2017 (-0500)
// Last-Updated: Mon Mar 20 11:46:58 2017 (-0500)
// Author: Ivan Guerra <Ivan.E.Guerra-1@ou.edu>
// ======================================================================
package com.mouthofrandom.cardshark.game.blackjack;

import java.util.Random;

public class CardDeck {
    private enum Suit {SPADE, HEART, CLUB, DIAMOND};
    private enum Value {ACE, TWO, THREE, FOUR, FIVE, SIX,
                        SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING};

    private final int DECK_SIZE = 52;
    private Card[] cards;
    private int activeCardCount;
    private Suit[] suits;
    private Value[] values;

    private class Card {
        private Suit suit;
        private Value value;

        public Card(Suit suit, Value value) {
            this.suit = suit;
            this.value = value;
        }

        public Suit getSuit() {
            return suit;
        }

        public Value getValue() {
            return value;
        }

        public String toString() {
            return ("(" + this.suit + ", " + this.value + ")");
        }
    }

    public CardDeck() {
        activeCardCount = 0;
        cards = new Card[DECK_SIZE];
        suits = Suit.values();
        values = Value.values();

        for (int i = 0 ; i < suits.length; i++){
            for (int j = 0 ; j < values.length; j++){
                cards[activeCardCount++] = new Card(suits[i], values[j]);
            }
        }
    }

    /* Return the maximum size of the deck. */
    public int getSize() {
        return this.DECK_SIZE;
    }

    /* Return the number of cards currently stored in the deck. */
    public int getCurrentCount() {
        return this.activeCardCount;
    }

    /* Return a Card object from the top of the deck. */
    public Card deal() {
	if (activeCardCount <= 0) {
	    System.out.println("ERROR: EMPTY DECK");
	}
        return cards[--activeCardCount];
    }

    /* Resets the deck by resetting the deck stack pointer. */
    public void resetDeck() {
        this.activeCardCount = DECK_SIZE;
    }

    /*
     * Randomly shuffle the current deck activeCardCount times
     * (i.e., shuffle the deck as many times as there are cards in the deck).
     */
    public void shuffle() {
        Random rand = new Random();

        for (int i = 0; i < activeCardCount; i++) {
            int m = rand.nextInt(activeCardCount);
            int n = rand.nextInt(activeCardCount);

            Card temp = cards[m];
            cards[m] = cards[n];
            cards[n] = temp;
        }
    }

    /* Sort the deck by suit and value. */
    public void sort() {
        Card bucketCards[][] = new Card[suits.length][values.length];

        for (int i = 0; i < activeCardCount; i++){
            bucketCards[cards[i].getSuit().ordinal()][cards[i].getValue().ordinal()]=cards[i];
        }

        int pointer = 0;
        for (int i = 0; i < suits.length; i++){
            for (int j = 0; j < values.length; j++){
                if (bucketCards[i][j] != null)
                    cards[pointer++] = bucketCards[i][j];
            }
        }
    }

    /* Prints out the card deck. */
    public void printStack() {
        int cardPointer = 0;
        for (int i = 0; i < suits.length; i++){
            for (int j = 0; j < values.length; j++){
                System.out.print(cards[cardPointer++] + " ");
            }
            System.out.println("\n");
        }
    }

    /* Convert the card deck contents to a String. */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < activeCardCount; i++){
            sb.append(cards[i]);
            sb.append(" ");
        }
        return sb.toString();
    }
}

// ======================================================================
// Filename: CardDeck.java
// Description: Implement an interface for accessing a standard 52 card
// deck.
// Created: Mon Mar 20 10:00:29 2017 (-0500)
// Last-Updated: Sat Mar 25 11:28:45 2017 (-0500)
// Author: Ivan Guerra <Ivan.E.Guerra-1@ou.edu>
// ======================================================================
package com.mouthofrandom.cardshark.game.blackjack;

import java.util.Random;

public class CardDeck {
    private final String[] SUITS = new String[]{"SPADES","CLUBS","DIAMONDS","HEARTS"};
    private final String[] CARD_TYPES = new String[]{"VALUE","KING","QUEEN","JACK","ACE"};
    private final int[] VALUES = new int[]{2,3,4,5,6,7,8,9,10,11};
    private final int DECK_SIZE = 52;

    private Card[] cards;
    private int activeCardCount;

    public CardDeck() {
        activeCardCount = 0;
        cards = new Card[DECK_SIZE];

        for (int i = 0 ; i < SUITS.length; i++){
            for (int j = 0 ; j < 9; j++){
                cards[activeCardCount++] = new Card(SUITS[i], CARD_TYPES[0], VALUES[j]);
            }
            cards[activeCardCount++] = new Card(SUITS[i], CARD_TYPES[1], VALUES[8]);
            cards[activeCardCount++] = new Card(SUITS[i], CARD_TYPES[2], VALUES[8]);
            cards[activeCardCount++] = new Card(SUITS[i], CARD_TYPES[3], VALUES[8]);
            cards[activeCardCount++] = new Card(SUITS[i], CARD_TYPES[4], VALUES[9]);
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

    /* Prints out the card deck. */
    public void printStack() {
        int cardPointer = 0;
        for (int i = 0; i < SUITS.length; i++){
            for (int j = 0; j < VALUES.length; j++){
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
            sb.append("\n");
        }
        return sb.toString();
    }
}

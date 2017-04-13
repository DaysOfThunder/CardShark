// ======================================================================
// Filename: Hand.java
// Description: Define and implement a blackjack player hand.
// Created: Fri Mar 24 22:16:20 2017 (-0500)
// Last-Updated: Wed Apr 12 20:39:49 2017 (-0500)
// Author: Ivan Guerra <Ivan.E.Guerra-1@ou.edu>
// ======================================================================
package com.mouthofrandom.cardshark.game.blackjack;

import java.util.ArrayList;
import java.util.Comparator;

public class Hand {
    /* Cards held by the player. */
    private ArrayList<Card> cards;

    /* Total value of player's hand (i.e., the sum of their card values). */
    private int totalValue;

    public Hand() {
        this.cards = new ArrayList<Card>();
        this.totalValue = 0;
    }

    /* Return the card that was most recently drawn from the deck. */
    public Card topCard() {
        return cards.get(cards.size()-1);
    }

    /* Return all cards in this hand. */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /* Add a new card to the current hand. */
    public void draw(final Card c) {
        totalValue += c.getValue();
        cards.add(c);
    }

    /* Return the total value of this player's hand. */
    public int getTotalValue() {
        return totalValue;
    }

    /* Remove all cards from this player's hand. */
    public void clearHand() {
        cards.clear();
        totalValue = 0;
    }

    /* 
     * Compare two card hands. 
     * Return 1 if h1 > h2, 0 if h1 == h2, and -1 otherwise. 
     */
    public int compare(final Hand h1, final Hand h2) {
	int h1Sum = 0;
	int h2Sum = 0;

	for (Card c : h1.getCards()) {
	    h1Sum += c.getValue();
	}
	
	for (Card c : h2.getCards()) {
	    h2Sum += c.getValue();
	}

	if (h1Sum == h2Sum) {
	    return 0;
	}
	return (h1Sum > h2Sum) ? 1 : -1;
    }

    /* Wrapper method allowing objects of the Hand class to compare themselves directly to other Hand objects. */
    public int compareHand(final Hand rhs) {
	return compare(this, rhs);
    }
}

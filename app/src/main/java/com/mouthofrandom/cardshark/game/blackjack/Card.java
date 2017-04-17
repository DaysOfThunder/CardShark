// ======================================================================
// Filename: Card.java
// Description: Define and implement a basic playing card.
// Created: Fri Mar 24 21:37:59 2017 (-0500)
// Last-Updated: Wed Apr 12 20:40:50 2017 (-0500)
// Author: Ivan Guerra <Ivan.E.Guerra-1@ou.edu>
// ======================================================================
package com.mouthofrandom.cardshark.game.blackjack;
import java.util.Comparator;

public class Card implements Comparator<Card> {
    private String suit;
    private String cardType;
    private int value;

    public Card(final String suit, final String cardType, final int value) {
        this.suit = suit;
        this.cardType = cardType;
        this.value = value;
    }

    /* Return the card suit. */
    public String getSuit() {
        return suit;
    }

    /* Return the card type. */
    public String getCardType() {
        return cardType;
    }

    /* Return the card value. */
    public int getValue() {
        return value;
    }

    /* Return the string representation of a Card. */
    public String toString() {
        return ("(" + this.suit + ", " + this.cardType + ", " + this.value + ")");
    }

    /* 
     * Compare two cards. 
     * Return 1 if c1 > c2, 0 if c1 == c2, and -1 otherwise. 
     */
    public int compare(final Card c1, final Card c2) {
	int c1Value = c1.getValue();
	int c2Value = c2.getValue();
	
	if (c1Value == c2Value) {
	    return 0;
	}
	return (c1Value > c2Value) ? 1 : -1;
    }

    /* Wrapper method allowing objects of the Card class to compare themselves directly to other Card objects. */
    public int compareCard(final Card rhs) {
	return compare(this, rhs);
    }
}

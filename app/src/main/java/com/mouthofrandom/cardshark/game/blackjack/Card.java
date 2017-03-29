// ======================================================================
// Filename: Card.java
// Description: Define and implement a basic playing card.
// Created: Fri Mar 24 21:37:59 2017 (-0500)
// Last-Updated: Sat Mar 25 11:28:29 2017 (-0500)
// Author: Ivan Guerra <Ivan.E.Guerra-1@ou.edu>
// ======================================================================
package com.mouthofrandom.cardshark.game.blackjack;

public class Card {
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
}

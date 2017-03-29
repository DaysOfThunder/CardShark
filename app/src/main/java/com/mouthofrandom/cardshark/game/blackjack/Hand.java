// ======================================================================
// Filename: Hand.java
// Description: Define and implement a blackjack player hand.
// Created: Fri Mar 24 22:16:20 2017 (-0500)
// Last-Updated: Sat Mar 25 11:29:00 2017 (-0500)
// Author: Ivan Guerra <Ivan.E.Guerra-1@ou.edu>
// ======================================================================
package com.mouthofrandom.cardshark.game.blackjack;

import java.util.ArrayList;

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
}

// ======================================================================
// Filename: BlackJack.java
// Description: Define and implement a simplified blackjack simulator.
// Created: Fri Mar 24 22:20:34 2017 (-0500)
// Last-Updated: Fri Mar 24 23:13:06 2017 (-0500)
// Author: Ivan Guerra <Ivan.E.Guerra-1@ou.edu>
// ======================================================================
package com.mouthofrandom.cardshark.game.blackjack;

import java.util.ArrayList;

public class BlackJack {
    private final int BLACKJACK = 21;

    private CardDeck deck;
    private Hand playerHand;
    private Hand machineHand;
    private int bet;

    /* Return a boolean indicating whether the player has won the game. */
    private boolean playerWon() {
	int machineTotal = machineHand.getTotalValue();
	int playerTotal = playerHand.getTotalValue();
	
	if (BLACKJACK == playerTotal) {  /* If the player or player and machine achieve BJ, then the player wins. */
	    return true;
	} else if (BLACKJACK == machineTotal) {
	    return false;
	} else if (playerTotal > BLACKJACK) {  /* If the player or player and machine busted, then the player loses by default. */
	    return false;
	} else if (machineTotal > BLACKJACK) {
	    return true;
	} else {
	    /* Neither the player or machine hit BJ or busted, thus whoever had the highest total value has won. */
	    return (playerTotal > machineTotal);
	}
    }

    /* 
     * Allow the machine to decide whether to draw or stand during its turn.
     * The algorithm is stupid simple but it actually leads to fairly balanced
     * wins/losses.
     */
    private void machineAction() {
	if (machineHand.getTotalValue() < 18) {
	    machineHand.draw(deck.deal());
	}
    }

    /* Prepare the deck and card hands for a new game. */
    private void initGame() {
	playerHand.clearHand();
	machineHand.clearHand();
	deck.resetDeck();
	deck.shuffle();
	
	for (int i = 0; i < 2; i++) {
	    playerHand.draw(deck.deal());
	    machineHand.draw(deck.deal());
	}
    }
    
    public BlackJack() {
	this.deck = new CardDeck();
	this.playerHand = new Hand();
	this.machineHand = new Hand();
	this.bet = 0;
    }

    /* Initialize the game and set the bet value to the parameter bet value. */
    public void play(int bet) {
	initGame();
	this.bet = bet;
    }

    /* 
     * Return a reference to the machine's top card. In blackjack,
     * the player can only see the dealer's top card even though the dealer
     * always has two or more cards.
     */
    public Card dealerTopCard() {
	return machineHand.topCard();
    }

    /* Return a list of the cards in the player's hand. */
    public ArrayList<Card> playerCards() {
	return playerHand.getCards();
    }

    /* Allow the player to draw a new card from the deck and let the machine take action. */
    public void hit() {
	playerHand.draw(deck.deal());
	machineAction();
    }

    /* Allow the machine to make one final action and then determine the payout value. */
    public int stand() {
	machineAction();
	return payout();
    }

    /* 
     * Return two times the bet amount if the 
     * player wins otherwise return the negative of this value indicating a loss. 
     */
    public int payout() {
	return (playerWon()) ? 2 * bet : -2 * bet;
    }
}

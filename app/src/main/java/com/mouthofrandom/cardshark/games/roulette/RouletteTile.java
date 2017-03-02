// ======================================================================
// Filename: RouletteTile.java
// Description: Define and Implement a Roulette game tile.
// Created: Fri Feb 24 16:01:38 2017 (-0600)
// Last-Updated: Fri Feb 24 17:52:00 2017 (-0600)
// Author: Ivan Guerra <Ivan.E.Guerra-1@ou.edu>
// ======================================================================

package com.mouthofrandom.cardshark.games.roulette;

class RouletteTile {
    private String color;
    private String descriptor;
    private int betAmount;
    private int payoutMultiplier;
    private boolean winner;

    public RouletteTile() {
	this.color = null;
	this.descriptor = null;
	this.betAmount = 0;
	this.payoutMultiplier = 0;
	this.winner = false;
    }

    public RouletteTile(final String descriptor, final String color, int betAmount, int payoutMultiplier) {
	this.descriptor = descriptor;
	this.color = color;
	this.betAmount = betAmount;
	this.payoutMultiplier = payoutMultiplier;
	this.winner = false;
    }

    public RouletteTile(final String descriptor, int betAmount, int payoutMultiplier) {
	this.descriptor = descriptor;
	this.color = null;
	this.betAmount = betAmount;
	this.payoutMultiplier = payoutMultiplier;
	this.winner = false;
    }

    public String getColor() {
	return this.color;
    }

    public String getDescriptor() {
	return this.descriptor;
    }

    public int getBetAmount() {
	return this.betAmount;
    }

    public int getPayoutMultiplier() {
	return this.payoutMultiplier;
    }

    public boolean isWinner() {
	return this.winner;
    }

    public void setBetAmount(int betAmount) {
	this.betAmount = betAmount;
    }

    public void addBetAmount(int betAmount) { this.betAmount += betAmount; }

    public void setWinner(boolean winner) {
	this.winner = winner;
    }
}

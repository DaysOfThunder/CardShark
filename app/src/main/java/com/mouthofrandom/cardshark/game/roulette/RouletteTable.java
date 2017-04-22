package com.mouthofrandom.cardshark.game.roulette;// ======================================================================
// Filename: RouletteTable.java
// Description: Define and implement an American Roulette board.
// Created: Fri Feb 24 13:14:29 2017 (-0600)
// Last-Updated: Fri Feb 24 17:59:26 2017 (-0600)
// Author: Ivan Guerra <Ivan.E.Guerra-1@ou.edu>
// ======================================================================

import java.util.Random;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class RouletteTable {
    /* Constants used in generating random roulette wheel values. */
    private static final int LOWERBOUND = 0;
    private static final int UPPERBOUND = 18;
    private static final Random WHEEL_RNG = new Random();

    /* Constant used to represent non-numeric game board tile descriptors. */
    private static final String RED = "RED";
    private static final String BLACK = "BLACK";
	private static final String GREEN = "GREEN";
    private static final String ODD = "ODD";
    private static final String EVEN = "EVEN";
//    private static final String ONE_TO_EIGHTEEN = "1 to 18";
//    private static final String NINETEEN_TO_THIRTYSIX = "19 to 36";
    private static final String FIRST_SIX = "1st 6";
    private static final String SECOND_SIX = "2nd 6";
    private static final String THIRD_SIX = "3rd 6";

    private HashMap<String, RouletteTile> boardTiles;
    private ArrayList<RouletteTile> betTiles;  /* List containing tiles the user has placed bets on. */

    /* Returns a string representation of an integer in the range [0,36]. */
    private String spinWheel() {
	return String.valueOf(WHEEL_RNG.nextInt(UPPERBOUND - LOWERBOUND) + LOWERBOUND);
    }

    /* Initializes the roulette game board tile map. */
    private void initBoard() {
	for (int i = 1; i < UPPERBOUND+1; i++) {
	    String descriptor = String.valueOf(i);
	    if (0 == i % 2) {
		boardTiles.put(descriptor, new RouletteTile(descriptor, BLACK, 0, 17));
	    } else {
		boardTiles.put(descriptor, new RouletteTile(descriptor, RED, 0, 17));
	    }
	}

	boardTiles.put("0", new RouletteTile("0", GREEN, 0, 17));

	boardTiles.put(FIRST_SIX, new RouletteTile(FIRST_SIX, 0, 2));
	boardTiles.put(SECOND_SIX, new RouletteTile(SECOND_SIX, 0, 2));
	boardTiles.put(THIRD_SIX, new RouletteTile(THIRD_SIX, 0, 2));

//	boardTiles.put(ONE_TO_EIGHTEEN, new RouletteTile(ONE_TO_EIGHTEEN, 0, 1));
//	boardTiles.put(NINETEEN_TO_THIRTYSIX, new RouletteTile(NINETEEN_TO_THIRTYSIX, 0, 1));

	boardTiles.put(EVEN, new RouletteTile(EVEN, 0, 1));
	boardTiles.put(ODD, new RouletteTile(ODD, 0, 1));

	boardTiles.put(RED, new RouletteTile(RED, 0, 1));
	boardTiles.put(BLACK, new RouletteTile(BLACK, 0, 1));
    }

    /* Clear the bets placed on the board. */
    private void clearBoard() {
	betTiles.clear();
	for(String id : boardTiles.keySet())
	{
		boardTiles.get(id).setBetAmount(0);
	}
    }

    public RouletteTable() {
	betTiles = new ArrayList<RouletteTile>();
	boardTiles = new HashMap<String, RouletteTile>();
	initBoard();
    }

    /* Returns an integer value representing the number of tokens the user earned based on the number of winning bets they had. */
    public int payout() {
	int reward_tokens = 0;
	for (RouletteTile t : betTiles) {
	    if (t.isWinner()) {
		reward_tokens += t.getBetAmount() * (t.getPayoutMultiplier() + 1);
	    }
	}
	this.clearBoard();

	return reward_tokens;
    }

    /*
     * Initializes the betTiles attribute based on the bets parameter entries.
     * Note the bets parameter is a map whose first template argument represents
     * the descriptor for a particular game board tile (i.e., a number in [0,36], 'EVEN', '1st 12', etc.).
     * The second template argument is the amount the user would like to bet on this particular tile.
     */
    public void placeBet(final HashMap<String, Integer> bets) {
	if (bets.isEmpty()) {
	    return;
	}

	for (Map.Entry<String, Integer> entry : bets.entrySet()) {
	    RouletteTile selectedTile = boardTiles.get(entry.getKey());

	    selectedTile.setBetAmount(entry.getValue());
	    betTiles.add(selectedTile);
	}
    }

	/**
	 *
	 * @param id
	 * @param value
     */
	public void placeSingleBet(String id, int value)
	{
		RouletteTile selectedTile = boardTiles.get(id);

		selectedTile.setBetAmount(value);
		if(!betTiles.contains(selectedTile)) {
			betTiles.add(selectedTile);
		}
	}

	/**
	 *
	 * @param id
	 * @param value
	 */
	public void addSingleBet(String id, int value)
	{
		RouletteTile selectedTile = boardTiles.get(id);

		selectedTile.addBetAmount(value);
		if(!betTiles.contains(selectedTile)) {
			betTiles.add(selectedTile);
		}
	}

	/**
	 *
	 * @param id
	 * @return
     */
	public int getTileBetAmount(String id)
	{
		RouletteTile selectedTile = boardTiles.get(id);
		return selectedTile.getBetAmount();
	}

    /* 
     * Pick a random numeric tile in the game board and then loop through betTiles setting the tiles corresponding winner attribute
     * to true if the tile satisfy any of the roulette win conditions.
     */
    public int play() {
	RouletteTile winTile = boardTiles.get(spinWheel());
	System.out.println("RNG Tile: (" + winTile.getDescriptor() + ", " + winTile.getColor() + ")");
	int winTileValue = Integer.parseInt(winTile.getDescriptor());

	for (RouletteTile t : betTiles) {
	    System.out.println("User Bet Tile: (" + t.getDescriptor() + ", " + String.valueOf(t.getBetAmount()) + ")");
	    if (t.getDescriptor().equals(winTile.getDescriptor())) {
		t.setWinner(true);
	    } else if (0 == winTileValue % 2 && 0 != winTileValue && EVEN.equals(t.getDescriptor())) {
		t.setWinner(true);
	    } else if (0 != winTileValue % 2 && ODD.equals(t.getDescriptor())) {
		t.setWinner(true);
//	    } else if ((winTileValue >= 1 && winTileValue <= 18) && ONE_TO_EIGHTEEN.equals(t.getDescriptor())) {
//		t.setWinner(true);
//	    } else if ((winTileValue >= 19 && winTileValue <= 36) && NINETEEN_TO_THIRTYSIX.equals(t.getDescriptor())) {
//		t.setWinner(true);
	    } else if ((winTileValue >= 1 && winTileValue <= 6) && FIRST_SIX.equals(t.getDescriptor())) {
		t.setWinner(true);
	    } else if ((winTileValue >= 7 && winTileValue <= 12) && SECOND_SIX.equals(t.getDescriptor())) {
		t.setWinner(true);
	    } else if ((winTileValue >= 13 && winTileValue <= 18) && THIRD_SIX.equals(t.getDescriptor())) {
		t.setWinner(true);
	    } else if (RED.equals(winTile.getColor()) && RED.equals(t.getDescriptor())) {
		t.setWinner(true);
	    } else if (BLACK.equals(winTile.getColor()) && BLACK.equals(t.getDescriptor())) {
		t.setWinner(true);
	    }
	}

	return winTileValue;
    }
}

package oh_heaven.game;

import java.util.HashMap;

import ch.aplu.jcardgame.*;
import oh_heaven.game.Oh_Heaven.Rank;
import oh_heaven.game.Oh_Heaven.Suit;

public class OpponentInfo {

	private HashMap<Suit, Rank> highestPossible;
	private HashMap<Suit, Boolean> hasSuits;
	
	private int bid;
	private int tricksWon = 0;
	
	public OpponentInfo() {
		HashMap<Suit, Rank> highestPossible = new HashMap<Suit, Rank>();
		HashMap<Suit, Boolean> hasSuits = new HashMap<Suit, Boolean>();
	}
	
	public void playCard(Card card) {
		
	}
	
	public void wonTrick() {
		tricksWon++;
	}
	
	public void setBid(int bid) {
		this.bid = bid;
	}
}

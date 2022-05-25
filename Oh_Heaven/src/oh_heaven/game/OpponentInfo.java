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
	private boolean playsLegally;;
	
	public OpponentInfo(boolean playsLegally) {
		HashMap<Suit, Rank> highestPossible = new HashMap<Suit, Rank>();
		HashMap<Suit, Boolean> hasSuits = new HashMap<Suit, Boolean>();
		this.playsLegally = playsLegally;
	}
	
	public void playCard(Card card, Boolean leadingSuit) {
		if (playsLegally & leadingSuit) {
			// Highest possible now 1 lower than what they played
			highestPossible.put((Suit) card.getSuit(), (Rank) Rank.values()[card.getRankId() + 1]);
		}
		if (playsLegally & !leadingSuit) {
			// Does not have card in leading suit
			hasSuits.put((Suit) card.getSuit(), false);
		}
	}
	
	public void wonTrick() {
		tricksWon++;
	}
	
	public void setBid(int bid) {
		this.bid = bid;
	}
	
	
}

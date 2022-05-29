package oh_heaven.game;

import java.util.HashMap;

import ch.aplu.jcardgame.*;
import oh_heaven.game.Oh_Heaven.Rank;
import oh_heaven.game.Oh_Heaven.Suit;

public class OpponentInfo {

	private HashMap<Suit, Integer> highestPossible;
	private HashMap<Suit, Boolean> hasSuits;
	
	private int bid;
	private int tricksWon = 0;
	private int score = 0;
	private boolean playsLegally;;
	
	OpponentInfo(boolean playsLegally) {
		highestPossible = new HashMap<Suit, Integer>();
		hasSuits = new HashMap<Suit, Boolean>();
		this.playsLegally = playsLegally;
	}
	
	public void playCard(Card card, Boolean leadingSuit) {
		if (playsLegally && leadingSuit) {
			// Highest possible now 1 lower than what they played
			highestPossible.put((Suit) card.getSuit(), card.getRankId() + 1);
		}
		if (playsLegally && !leadingSuit) {
			// Does not have card in leading suit
			hasSuits.put((Suit) card.getSuit(), false);
		}
	}
	
	public void wonTrick() {
		tricksWon++;
	}
	
	public void updateScore() {
		score += tricksWon;
		if (tricksWon == bid) {
			score += Oh_Heaven.getInstance().madeBidBonus;
		}
		tricksWon = 0;
	}
	
	public void setBid(int bid) {
		this.bid = bid;
	}
	
	
}

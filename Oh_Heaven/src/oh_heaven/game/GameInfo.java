package oh_heaven.game;

import java.util.ArrayList;
import java.util.HashMap;

import ch.aplu.jcardgame.*;
import oh_heaven.game.Oh_Heaven.Rank;
import oh_heaven.game.Oh_Heaven.Suit;

public class GameInfo {

	private int playerid;
	private Suit trump;
	private Suit lead;
	private int bestMatchingLead;
	private boolean trumpPlayed;
	private int bestMatchingTrump;
	private HashMap<Suit, Integer> highestPossible;
	private ArrayList<OpponentInfo> opponentInfo;
	
	GameInfo(int playerid, Boolean[] playsLegally){
		//Stores 4, only updates 3 opponents.
		// Didn't want to have to map player ids to new values.
		opponentInfo = new ArrayList<OpponentInfo>(4);
		highestPossible = new HashMap<Suit, Integer>();
		bestMatchingLead = Rank.values().length;
		bestMatchingTrump = Rank.values().length;
		this.playerid = playerid;
		for (int i=0;i<4;i++) {
			opponentInfo.add(new OpponentInfo(playsLegally[i]));
		}
		for (Suit suit : Suit.values()) {
			highestPossible.put(suit, 0); // Reverse-ordered Rank enum
		}
		Oh_Heaven.getInstance().addObserver(this);
	}
		
	public void playedCard(Card card, int playerid) {
		// Played trump suit
		if (((Suit) card.getSuit()).equals(trump)) {
			trumpPlayed = true;
		}
		// Highest remaining in suit played
		if (card.getRankId() == highestPossible.get((Suit) card.getSuit())) {
			// Decrease highest possible by a single rank if maximum has just been played
			highestPossible.put((Suit) card.getSuit(), highestPossible.get((Suit) card.getSuit()) + 1);
		}
		// New card out ranks previous best in lead suit
		if ((Suit) card.getSuit() == lead && card.getRankId() < bestMatchingLead) {
			bestMatchingLead = card.getRankId();
		}
		if ((Suit) card.getSuit() == trump && card.getRankId() < bestMatchingTrump) {
			trumpPlayed = true;
			bestMatchingTrump = card.getRankId();
		}
		if (playerid == this.playerid) {
			// No need to log ourselves
			return;
		}
		else {
			opponentInfo.get(playerid).playCard(card, card.getSuit().equals(lead));
		}
		
	}
	
	public Suit getLead() {
		return lead;
	}
	
	public Suit getTrump() {
		return trump;
	}
	
	public void setTrump(Suit trump) {
		this.trump = trump;
	}
	
	public boolean isTrumpPlayed() {
		return trumpPlayed;
	}

	public int getBestMatchingLead() {
		return bestMatchingLead;
	}

	public int getHighestTrump() {
		return highestPossible.get(trump);
	}
	
	public void trickWon(int playerid) {
		if (playerid == this.playerid) {
			return;
		}
		else {
			opponentInfo.get(playerid).wonTrick();
		}
	}

	public void setLead(Card lead) {
		//System.out.println("Player " + playerid + " received lead update: " + lead);
		this.lead = (Suit) lead.getSuit();
		bestMatchingLead = lead.getRankId();
	}
	public void setBid(int bid, int playerid) {
		if (playerid == this.playerid) {
			return;
		}
		else {
			opponentInfo.get(playerid).setBid(bid);
		}
	}
	public void updateScores() {
		for (int i=0;i<4;i++) {
			if (i == playerid) {
				continue;
			}
			else {
				opponentInfo.get(playerid).updateScore();
			}
		}
	}
}

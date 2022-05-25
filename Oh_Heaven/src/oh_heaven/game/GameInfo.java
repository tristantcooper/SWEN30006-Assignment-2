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
	private HashMap<Suit, Integer> highestPossible;
	private ArrayList<OpponentInfo> opponentInfo;
	
	GameInfo(int playerid, Boolean[] playsLegally){
		//Stores 4, only updates 3 opponents.
		// Didn't want to have to map player ids to new values.
		opponentInfo = new ArrayList<OpponentInfo>(4);
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
		if (card.getRankId() == highestPossible.get((Suit) card.getSuit())) {
			// Decrease highest possible rank by 1 if maximum has just been played
			highestPossible.put((Suit) card.getSuit(), highestPossible.get((Suit) card.getSuit()) + 1);
		}
		if (playerid == this.playerid) {
			return;
		}
		else {
			opponentInfo.get(playerid).playCard(card);
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

	public void setLead(Suit lead) {
		System.out.println("Player " + playerid + " received lead update: " + lead);
		this.lead = lead;
	}
	public void setBid(int bid, int playerid) {
		if (playerid == this.playerid) {
			return;
		}
		else {
			opponentInfo.get(playerid).setBid(bid);
		}
	}
}

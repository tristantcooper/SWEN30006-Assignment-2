package oh_heaven.game;

import java.util.ArrayList;

import ch.aplu.jcardgame.*;
import oh_heaven.game.Oh_Heaven.Suit;

public class GameInfo {

	private int playerid;
	private Suit trump;
	private Suit lead;
	private ArrayList<OpponentInfo> opponentInfo;
	
	GameInfo(int playerid){
		//Stores 4, only updates 3 opponents.
		// Didn't want to have to map player ids to new values.
		opponentInfo = new ArrayList<OpponentInfo>(4);
		this.playerid = playerid;
		for (int i=0;i<4;i++) {
			opponentInfo.add(new OpponentInfo());
		}
		Oh_Heaven.getInstance().addObserver(this);
	}
	
	public void playedCard(Card card, int playerid) {
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

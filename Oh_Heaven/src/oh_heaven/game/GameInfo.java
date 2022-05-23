package oh_heaven.game;

import java.util.ArrayList;

import ch.aplu.jcardgame.*;
import oh_heaven.game.Oh_Heaven.Suit;

public class GameInfo {

	private Suit trump;
	private Suit lead;
	private ArrayList<OpponentInfo> opponentInfo;
	
	GameInfo(){
		//Stores 4, only updates 3 opponents.
		// Didn't want to have to map player ids to new values.
		opponentInfo = new ArrayList<OpponentInfo>(4);
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
		this.lead = lead;
	}
}

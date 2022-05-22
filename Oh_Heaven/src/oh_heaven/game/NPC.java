package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Oh_Heaven.Suit;

public class NPC extends Player {
	INPCStrategy npcStrategy;
	 private Hand hand; 
	 private Suit lead; 
	 private Suit trump;

	NPC(int id, boolean isNPC, INPCStrategy npcStrategy) {
		super(id, isNPC);
		this.npcStrategy = npcStrategy;
		// TODO Auto-generated constructor stub
	}
	
	public void placeLead() {
		npcStrategy.leadDecision(getHand(), getLead(), getTrump());
	}
	public void placeFollowing() {
		npcStrategy.followDecision(getHand(), getLead(), getTrump());
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	public Suit getLead() {
		return lead;
	}

	public void setLead(Suit lead) {
		this.lead = lead;
	}
	public Suit getTrump() {
		return trump;
	}

	public void setTrump(Suit trump) {
		this.trump = trump;
	}
}

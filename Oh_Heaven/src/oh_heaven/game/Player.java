package oh_heaven.game;

import ch.aplu.jcardgame.*;

public class Player {
	
	public final boolean isNPC;
	public final int id;
	protected Hand hand;
	protected final String playerType ;
	
	public Player(int id,  String playerType) {
		this.id = id;
		this.playerType = playerType;
		
		if (playerType.equals("human")) {
			this.isNPC = false;}
		else this.isNPC = true;
		
	}

	// Not needed. Hand shifting logic already in Oh_Heaven.
	// Keep it there via info expert.
	// Player only needs to decide WHAT card to play, game can do the shifting to trick.
	protected void playCard(Card card) {
		hand.remove(card, false);
		System.out.println("Player "+id+" now has hand: "+hand);
	}
	
	public String getPlayerType() {
		return playerType;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}
	
	public Card placeLead() {
		return null;
	}
	public Card placeFollowing() {
		return null;
	}
}

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
	
	protected void playCard(Card card) {
		hand.remove(card, false);
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
}

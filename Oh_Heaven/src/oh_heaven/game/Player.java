package oh_heaven.game;

import ch.aplu.jcardgame.*;

public class Player {
	
	public boolean isNPC;
	private int id;
	protected Hand hand;
	protected String playerType ;
	
	Player(int id,  String playerType) {
		this.id = id;
		this.playerType = playerType;
		
		if (playerType == "human")
			this.isNPC = false;
		else this.isNPC = true;
		
	}
	
	protected void playCard(Card card) {
		hand.remove(card, false);
	}

}

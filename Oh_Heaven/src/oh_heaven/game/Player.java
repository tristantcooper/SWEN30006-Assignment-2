package oh_heaven.game;

import ch.aplu.jcardgame.*;

public class Player {
	
	public final boolean isNPC;
	private int id;
	protected Hand hand;
	
	Player(int id, boolean isNPC) {
		this.id = id;
		this.isNPC = isNPC;
	}
	
	protected void playCard(Card card) {
		hand.remove(card, false);
	}

}

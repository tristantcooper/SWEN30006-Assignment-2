package oh_heaven.game;

import ch.aplu.jcardgame.*;

public class Player {
	
	public final boolean isNPC;
	private int id;
	private Hand hand;
	
	Player(int id, boolean isNPC) {
		this.id = id;
		this.isNPC = isNPC;
	}

}

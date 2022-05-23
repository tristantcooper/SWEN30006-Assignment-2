package oh_heaven.game;

import ch.aplu.jcardgame.*;

public class Player {
	
	public boolean isNPC;
	private int id;
	public Hand hand;
	protected String playerType ;
	
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
	
	public boolean isNPC() {
		return isNPC;
	}
	
	public String getPlayerType() {
		return playerType;
	}

}

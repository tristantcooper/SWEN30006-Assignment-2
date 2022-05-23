package oh_heaven.game;

import ch.aplu.jcardgame.Card;

public class NPC extends Player {
	INPCStrategy npcStrategy;

	NPC(int id, String playerType) {
		super(id, playerType);
		
		if(playerType == "legal")
		this.npcStrategy = new LegalStrategy();
		else 
			this.npcStrategy = new SmartStrategy();
		// TODO Auto-generated constructor stub
	}
	
	public Card placeLead() {
		Card card = npcStrategy.leadDecision(hand);
		playCard(card);
		return card;
	}
	public Card placeFollowing() {
		Card card = npcStrategy.followDecision(hand);
		playCard(card);
		return card;
	}

}

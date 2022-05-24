package oh_heaven.game;

import ch.aplu.jcardgame.Card;

public class NPC extends Player {
	INPCStrategy npcStrategy;

	NPC(int id, String playerType) {
		super(id, playerType);
		
		if (playerType == "smart") {
			this.npcStrategy = new SmartStrategy();
		}
		
		else {
			this.npcStrategy = new LegalStrategy();
		}
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

package oh_heaven.game;

import ch.aplu.jcardgame.Card;

public class NPC extends Player {
	INPCStrategy npcStrategy;

	NPC(int id, String playerType) {
		super(id, playerType);
		
		// Default to legal play
		this.npcStrategy = new LegalStrategy(id);
		
		if (playerType.equals("smart")) {
			this.npcStrategy = new SmartStrategy(id);
		}
		if (playerType.equals("random")) {
			this.npcStrategy = new RandomStrategy();
		}
	}
	
	@Override
	public Card placeLead() {
		Card card = npcStrategy.leadDecision(hand);
		return card;
	}
	@Override
	public Card placeFollowing() {
		Card card = npcStrategy.followDecision(hand);
		return card;
	}
	
	

}

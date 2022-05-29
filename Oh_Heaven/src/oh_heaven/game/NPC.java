package oh_heaven.game;

import ch.aplu.jcardgame.Card;

public class NPC extends Player {
	INPCStrategy npcStrategy;

	NPC(int id, String playerType) {
		super(id, playerType);
		
		if (playerType.equals("smart")) {
			this.npcStrategy = new SmartStrategy(id);
		}
		if (playerType.equals("random")) {
			this.npcStrategy = new RandomStrategy();
		}
		else {
			this.npcStrategy = new LegalStrategy(id);
		}
	}
	
	@Override
	public Card placeLead() {
		Card card = npcStrategy.leadDecision(hand);
		//playCard(card);
		return card;
	}
	@Override
	public Card placeFollowing() {
		Card card = npcStrategy.followDecision(hand);
		//playCard(card);
		return card;
	}
	
	

}

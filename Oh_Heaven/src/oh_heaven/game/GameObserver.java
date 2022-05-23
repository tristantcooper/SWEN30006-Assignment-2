package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import oh_heaven.game.Oh_Heaven.Suit;

public class GameObserver implements IGameObserver {
	private GameInfo gameInfo;
	
	GameObserver(GameInfo gameInfo) {
		this.gameInfo = gameInfo;
	}

	@Override
	public void updatePlayedCard(Card card, int playerid) {
		gameInfo.playedCard(card, playerid);
	}

	@Override
	public void updateTrump(Suit trump) {
		gameInfo.setTrump(trump);
	}

	@Override
	public void updateLead(Suit lead) {
		gameInfo.setLead(lead);
	}

}

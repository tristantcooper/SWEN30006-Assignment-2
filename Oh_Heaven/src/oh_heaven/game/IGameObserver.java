package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import oh_heaven.game.Oh_Heaven.Suit;

public interface IGameObserver {

	public void updateBid(int bid, int playerid);
	public void updatePlayedCard(Card card, int playerid);
	public void updateTrump(Suit trump);
	public void updateLead(Suit lead);
}

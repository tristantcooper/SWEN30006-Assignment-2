package oh_heaven.game;

import ch.aplu.jcardgame.*;
import oh_heaven.game.Oh_Heaven.Suit;

public interface INPCStrategy {
	public Card leadDecision(Hand hand);
	public Card followDecision(Hand hand);
}

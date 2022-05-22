package oh_heaven.game;

import ch.aplu.jcardgame.*;

public interface INPCStrategy {
	public Card leadDecision(Hand hand);
	public Card followDecision(Hand hand);
}

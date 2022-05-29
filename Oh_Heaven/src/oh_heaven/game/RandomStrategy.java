package oh_heaven.game;

import java.util.Random;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class RandomStrategy implements INPCStrategy {

	private Random random = new Random();
	
	@Override
	public Card leadDecision(Hand hand) {
		int x = random.nextInt(hand.getNumberOfCards());
	    return hand.get(x);
	}

	@Override
	public Card followDecision(Hand hand) {
		int x = random.nextInt(hand.getNumberOfCards());
	    return hand.get(x);
	}

}

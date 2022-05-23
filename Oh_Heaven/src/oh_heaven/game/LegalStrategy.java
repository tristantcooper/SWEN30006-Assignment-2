package oh_heaven.game;

import java.util.ArrayList;
import java.util.Random;

import ch.aplu.jcardgame.*;
import oh_heaven.game.Oh_Heaven.Suit;

public class LegalStrategy implements INPCStrategy {
	private Random random = new Random();
	
	private final GameInfo gameInfo = new GameInfo();

	@Override
	public Card leadDecision(Hand hand) {
		return randomCard(hand);
	}

	@Override
	public Card followDecision(Hand hand) {
		return playLegalCard(hand);
	}
	
	public Card playLegalCard(Hand hand) {
		/**
		 * Returns a single card of the possible
		 * legal cards that can be played
		 * in the current turn.
		 */
		ArrayList<Card> legalCards = hand.getCardsWithSuit(gameInfo.getLead());
		int numCards = legalCards.size();
		if (numCards == 0) {
			return randomCard(hand);
		}
		else {
			int x = random.nextInt(numCards);
			return legalCards.get(x);
		}	
	}
	
	public Card randomCard(Hand hand){
	      int x = random.nextInt(hand.getNumberOfCards());
	      return hand.get(x);
	  }

}

package oh_heaven.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import ch.aplu.jcardgame.*;
//import oh_heaven.game.Oh_Heaven.Suit;

public class LegalStrategy implements INPCStrategy {
	private Random random = new Random();
	
	private final GameInfo gameInfo;
	
	public LegalStrategy(int playerid) {
		Boolean[] playsLegally = new Boolean[4];
		Arrays.fill(playsLegally, true);
		gameInfo = new GameInfo(playerid, playsLegally);
	}

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
		System.out.println("Lead: " + gameInfo.getLead());
		ArrayList<Card> legalCards = hand.getCardsWithSuit(gameInfo.getLead());
		Card cardToPlay;
		int numCards = legalCards.size();
		if (numCards == 0) {
			cardToPlay = randomCard(hand);
		}
		else {
			int x = random.nextInt(numCards);
			cardToPlay = legalCards.get(x);
		}	
		System.out.println("Playing card: " + cardToPlay);
		return cardToPlay;
	}
	
	public Card randomCard(Hand hand){
	      int x = random.nextInt(hand.getNumberOfCards());
	      return hand.get(x);
	  }

}

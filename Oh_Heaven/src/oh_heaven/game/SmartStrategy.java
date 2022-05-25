package oh_heaven.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import ch.aplu.jcardgame.*;
import oh_heaven.game.Oh_Heaven.Suit;
//import oh_heaven.game.Oh_Heaven.Suit;

public class SmartStrategy implements INPCStrategy {
	private Random random = new Random();
	
	private final GameInfo gameInfo;
	
	public SmartStrategy(int playerid) {
		Boolean[] playsLegally = new Boolean[4];
		Arrays.fill(playsLegally, true);
		gameInfo = new GameInfo(playerid, playsLegally);
	}

	@Override
	public Card leadDecision(Hand hand) {
		Card highestTrump = hand.getCardsWithSuit(gameInfo.getTrump()).get(0); // already sorts
		if (highestTrump.getRankId() == gameInfo.getHighestTrump()) {
			// Will always win if you lead with the highest ranked remaining
			// trump out of active cards.
			return highestTrump;
		}
		ArrayList<Card> matchingLeads =  hand.getCardsWithRank(gameInfo.getLead());
		if (matchingLeads.size() > 0) {
			// Have cards that match the leading suit
			Card bestMatchingCard = matchingLeads.get(0);
			if (bestMatchingCard.getRankId() > gameInfo.getBestMatchingLead()) {
				// Can beat the current best leading suits
				return bestMatchingCard;
			}
			else {
				// Can't win, so play worst card
				return matchingLeads.get(matchingLeads.size() - 1);
			}
		}
		else {
			return randomCard(hand);
		}
		
	}

	@Override
	public Card followDecision(Hand hand) {
		ArrayList<Card> cardsMatchingLead = hand.getCardsWithSuit(gameInfo.getLead());
		
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
	
	public Card getHighestCard(Hand hand, Suit suit) {
		ArrayList<Card> cards = hand.getCardsWithSuit(suit); // Sorts cards based on Rank
		return cards.get(0); // Reverse-ordered Rank enum
	}

}

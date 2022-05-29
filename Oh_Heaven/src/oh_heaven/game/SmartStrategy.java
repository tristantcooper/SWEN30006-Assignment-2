package oh_heaven.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import ch.aplu.jcardgame.*;
import oh_heaven.game.Oh_Heaven.Suit;
//import oh_heaven.game.Oh_Heaven.Suit;

public class SmartStrategy implements INPCStrategy {
	private Random random = new Random(1);
	
	private final GameInfo gameInfo;
	
	public SmartStrategy(int playerid) {
		Boolean[] playsLegally = new Boolean[4];
		Arrays.fill(playsLegally, true);
		gameInfo = new GameInfo(playerid, playsLegally);
	}

	@Override
	public Card leadDecision(Hand hand) {
		
		
		if (!hand.getCardsWithSuit(gameInfo.getTrump()).isEmpty()) {
			Card highestTrump = hand.getCardsWithSuit(gameInfo.getTrump()).get(0); // already sorts
			if (highestTrump.getRankId() == gameInfo.getHighestTrump()) {
				// Will always win if you lead with the highest ranked remaining
				// trump out of active cards.
				return highestTrump;
			}
		}
		return randomCard(hand);
	}

	@Override
	public Card followDecision(Hand hand) {
		ArrayList<Card> cardsMatchingLead = hand.getCardsWithSuit(gameInfo.getLead());
		//System.out.println("Cards matching suit: " + cardsMatchingLead.size());
		if (cardsMatchingLead.size() > 0) {
			// Have cards that match the leading suit
			Card bestMatchingCard = cardsMatchingLead.get(0);
			//System.out.println("Best on table is: " + gameInfo.getBestMatchingLead());
			//System.out.println("Best card is: " + bestMatchingCard);
			if (bestMatchingCard.getRankId() < gameInfo.getBestMatchingLead()) { // reverse enum order
				// Can beat the current best leading suits
				return bestMatchingCard;
			}
			else {
				// Can't win, so play worst card
				//System.out.println("Playing worst card.");
				return cardsMatchingLead.get(cardsMatchingLead.size() - 1);
			}
		}
		return randomCard(hand);
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

package oh_heaven.game;

// Oh_Heaven.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import oh_heaven.utility.PropertiesLoader;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class Oh_Heaven extends CardGame {
	
  public enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  public enum Rank
  {
    // Reverse order of rank importance (see rankGreater() below)
	// Order of cards is tied to card images
	ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
  }
  
  final String trumpImage[] = {"bigspade.gif","bigheart.gif","bigdiamond.gif","bigclub.gif"};

  static public final int seed = 30006;
  static final Random random = new Random(seed);
  
  // return random Enum value
  public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
      int x = random.nextInt(clazz.getEnumConstants().length);
      return clazz.getEnumConstants()[x];
  }

  // return random Card from Hand
  public static Card randomCard(Hand hand){
      int x = random.nextInt(hand.getNumberOfCards());
      return hand.get(x);
  }
 
  // return random Card from ArrayList
  public static Card randomCard(ArrayList<Card> list){
      int x = random.nextInt(list.size());
      return list.get(x);
  }
  
  private void dealingOut(int nbPlayers, int nbCardsPerPlayer) {
	  Hand pack = deck.toHand(false);
	  // pack.setView(Oh_Heaven.this, new RowLayout(hideLocation, 0));
	  for (int i = 0; i < nbCardsPerPlayer; i++) {
		  for (int j=0; j < nbPlayers; j++) {
			  if (pack.isEmpty()) return;
			  Card dealt = randomCard(pack);
		      // System.out.println("Cards = " + dealt);
		      dealt.removeFromHand(false);
		      players.get(j).getHand().insert(dealt, false);
		      //hands[j].insert(dealt, false);
			  // dealt.transfer(hands[j], true);
		  }
	  }
  }
  
  public boolean rankGreater(Card card1, Card card2) {
	  return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
  }
  
  private static Oh_Heaven instance;
  private ArrayList<Player> players = new ArrayList<Player>();
  private final String version = "1.0";
  public int currentPlayer;
  public final int nbPlayers = 4;
  public final int nbStartCards;
  public final int nbRounds;
  public final int madeBidBonus = 10;
  private final int handWidth = 400;
  private final int trickWidth = 40;
  private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
  private final Location[] handLocations = {
			  new Location(350, 625),
			  new Location(75, 350),
			  new Location(350, 75),
			  new Location(625, 350)
	  };
  private final Location[] scoreLocations = {
			  new Location(575, 675),
			  new Location(25, 575),
			  new Location(575, 25),
			  // new Location(650, 575)
			  new Location(575, 575)
	  };
  private Actor[] scoreActors = {null, null, null, null };
  private final Location trickLocation = new Location(350, 350);
  private final Location textLocation = new Location(350, 450);
  private final int thinkingTime = 500;//2000;
  private Location hideLocation = new Location(-500, - 500);
  private Location trumpsActorLocation = new Location(50, 50);
  private boolean enforceRules=false;
  private boolean hideNPCHands;

  public void setStatus(String string) { setStatusText(string); }

private ArrayList<IGameObserver> gameObservers = new ArrayList<IGameObserver>();

Font bigFont = new Font("Serif", Font.BOLD, 36);

public void addObserver(GameInfo gameInfo) {
	gameObservers.add(new GameObserver(gameInfo));
}

public void updateBid(int bid, int playerid) {
	for (IGameObserver observer : gameObservers) {
		observer.updateBid(bid, playerid);
	}
}

public void updateTrump(Suit trump) {
	for (IGameObserver observer : gameObservers) {
		observer.updateTrump(trump);
	}
}

public void updateLead(Card lead) {
	for (IGameObserver observer : gameObservers) {
		System.out.println("1 observer lead update");
		observer.updateLead(lead);
	}
}

public void updateRoundEnd() {
	for (IGameObserver observer : gameObservers) {
		observer.updateRoundEnd();
	}
}

public void updateTrickWon(int playerid) {
	for (IGameObserver observer : gameObservers) {
		observer.updateTrickWon(playerid);
	}
}

public void updatePlayedCard(Card card, int playerid) {
	for (IGameObserver observer : gameObservers) {
		observer.updatePlayedCard(card, playerid);
	}
}

private void initPlayers(Properties properties) {
	if (properties == null) {
		for(int i = 0 ; i< nbPlayers; i++) {
			players.add(new Player(i,"legal"));
		}
	}
	for(int i = 0 ; i< nbPlayers; i++) {
		String playerType= properties.getProperty("players."+i);
		if (playerType.equals("human")) {
			players.add(new Player(i,playerType));
		}
		else {
			players.add((Player) new NPC(i,playerType));
		}
		System.out.println("Players: " + players.size());
	}
}

private void initScore() {
	 for (int i = 0; i < nbPlayers; i++) {
		 // scores[i] = 0;
		 String text = "[" + String.valueOf(players.get(i).getScore()) + "]" + String.valueOf(players.get(i).getTricks()) + "/" + String.valueOf(players.get(i).getBid());
		 scoreActors[i] = new TextActor(text, Color.WHITE, bgColor, bigFont);
		 addActor(scoreActors[i], scoreLocations[i]);
	 }
  }

private void updateScore(int player) {
	removeActor(scoreActors[player]);
	String text = "[" + String.valueOf(players.get(player).getScore()) + "]" + String.valueOf(players.get(player).getTricks()) + "/" + String.valueOf(players.get(player).getBid());
	scoreActors[player] = new TextActor(text, Color.WHITE, bgColor, bigFont);
	addActor(scoreActors[player], scoreLocations[player]);
}

private void updateScores() {
	 for (int i = 0; i < nbPlayers; i++) {
		 players.get(i).updateScore();
	 }
}

private void initBids(Suit trumps, int nextPlayer) {
	int total = 0;
	int bid;
	for (int i = nextPlayer; i < nextPlayer + nbPlayers; i++) {
		 int iP = i % nbPlayers;
		 bid = nbStartCards / 4 + random.nextInt(2);
		 players.get(iP).setBid(bid);
		 total += bid;
		 updateBid(bid, iP);
	 }
	 if (total == nbStartCards) {  // Force last bid so not every bid possible
		 int iP = (nextPlayer + nbPlayers) % nbPlayers;
		 bid = players.get(iP).getBid();
		 if (bid == 0) {
			 bid = 1;
			 players.get(iP).setBid(bid);
		 } else {
			 bid += random.nextBoolean() ? -1 : 1;
			 players.get(iP).setBid(bid);
		 }
		 updateBid(bid, iP);
	 }
	// for (int i = 0; i < nbPlayers; i++) {
	// 	 bids[i] = nbStartCards / 4 + 1;
	//  }
 }

private Card selected;

private void initRound() {
	for (int i = 0; i < nbPlayers; i++) {
		players.get(i).setHand(new Hand(deck));
	}
	dealingOut(nbPlayers, nbStartCards);
	 for (int i = 0; i < nbPlayers; i++) {
		   players.get(i).getHand().sort(Hand.SortType.SUITPRIORITY, true);
	 }
	 // Set up human player for interaction
	for (int i=0; i< nbPlayers ; i++) {
		currentPlayer = i;
		if(!players.get(i).isNPC) {
			CardListener cardListener = new CardAdapter()  // Human Player plays card
				    {
				      public void leftDoubleClicked(Card card) { selected = card; 
				      //hands[0].setTouchEnabled(false);
				      //using player list
				      
				      players.get(currentPlayer).getHand().setTouchEnabled(false); }
				    };
				    players.get(currentPlayer).getHand().addCardListener(cardListener);
		}
	}
	 
	 
	 // graphics
    RowLayout[] layouts = new RowLayout[nbPlayers];
    for (int i = 0; i < nbPlayers; i++) {
      layouts[i] = new RowLayout(handLocations[i], handWidth);
      layouts[i].setRotationAngle(90 * i);
      // layouts[i].setStepDelay(10);
      //hands[i].setView(this, layouts[i]);
      //using list
      players.get(i).getHand().setView(this, layouts[i]);
      players.get(i).getHand().setTargetArea(new TargetArea(trickLocation));
      players.get(i).getHand().draw();
    }
    if (hideNPCHands) {
    	for (int i = 0; i < nbPlayers; i++) {
        	if(players.get(i).isNPC) {
        	players.get(i).getHand().setVerso(true);	
        	}
    	}    	
    	// This code can be used to visually hide the cards in a hand (make them face down)
    }
    			// You do not need to use or change this code.
    // End graphics
}
private void ruleCheck(int nextPlayer, Suit lead) {
	if (selected.getSuit() != lead &&  players.get(nextPlayer).getHand().getNumberOfCardsWithSuit(lead) > 0) {
		 // Rule violation
		 String violation = "Follow rule broken by player " + nextPlayer + " attempting to play " + selected;
		 System.out.println(violation);
		 if (enforceRules) 
			 try {
				 throw(new BrokeRuleException(violation));
				} catch (BrokeRuleException e) {
					e.printStackTrace();
					System.out.println("A cheating player spoiled the game!");
					System.exit(0);
				}  
	 }
}

private void selectLead(int nextPlayer) {
	if (!players.get(nextPlayer).isNPC) {  // Select lead depending on player type
		 players.get(nextPlayer).getHand().setTouchEnabled(true);
		setStatus("Player " + nextPlayer + " double-click on card to lead.");
		while (null == selected) delay(100);
    } else {
		setStatusText("Player " + nextPlayer + " thinking...");
        delay(thinkingTime);
        selected = players.get(nextPlayer).placeLead();
    }
}

private void playFollowing(int nextPlayer) {
	if (!players.get(nextPlayer).isNPC ) {
		players.get(nextPlayer).getHand().setTouchEnabled(true);
		setStatus("Player " + nextPlayer + " double-click on card to follow.");
		while (null == selected) delay(100);
    } else {
        setStatusText("Player " + nextPlayer + " thinking...");
        delay(thinkingTime);
        selected = players.get(nextPlayer).placeFollowing();
    }
}

private void playRound() {
	// Select and display trump suit
		final Suit trumps = randomEnum(Suit.class);
		updateTrump(trumps);
		final Actor trumpsActor = new Actor("sprites/"+trumpImage[trumps.ordinal()]);
	    addActor(trumpsActor, trumpsActorLocation);
	// End trump suit
	Hand trick;
	int winner;
	Card winningCard;
	Suit lead;
	int nextPlayer = random.nextInt(nbPlayers); // randomly select player to lead for this round
	initBids(trumps, nextPlayer);
    for (int i = 0; i < nbPlayers; i++) updateScore(i);
	for (int i = 0; i < nbStartCards; i++) {
		trick = new Hand(deck);
    	selected = null;
    	selectLead(nextPlayer);
        // Lead with selected card
	        trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards()+2)*trickWidth));
			trick.draw();
			selected.setVerso(false);
			// No restrictions on the card being lead
			lead = (Suit) selected.getSuit();
			updateLead(selected);
			selected.transfer(trick, true); // transfer to trick (includes graphic effect)
			winner = nextPlayer;
			winningCard = selected;
		// End Lead
		for (int j = 1; j < nbPlayers; j++) {
			if (++nextPlayer >= nbPlayers) nextPlayer = 0;  // From last back to first
			selected = null;
			playFollowing(nextPlayer);
			updatePlayedCard(selected, nextPlayer);
	        // Follow with selected card
		        trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards()+2)*trickWidth));
				trick.draw();
				selected.setVerso(false);  // In case it is upside down
				// Check: Following card must follow suit if possible
				ruleCheck(nextPlayer, lead);
				
				 selected.transfer(trick, true); // transfer to trick (includes graphic effect)
				 System.out.println("winning: " + winningCard);
				 System.out.println(" played: " + selected);
				 // System.out.println("winning: suit = " + winningCard.getSuit() + ", rank = " + (13 - winningCard.getRankId()));
				 // System.out.println(" played: suit = " +    selected.getSuit() + ", rank = " + (13 -    selected.getRankId()));
				 if ( // beat current winner with higher card
					 (selected.getSuit() == winningCard.getSuit() && rankGreater(selected, winningCard)) ||
					  // trumped when non-trump was winning
					 (selected.getSuit() == trumps && winningCard.getSuit() != trumps)) {
					 System.out.println("NEW WINNER");
					 winner = nextPlayer;
					 winningCard = selected;
				 }
			// End Follow
		}
		delay(600);
		trick.setView(this, new RowLayout(hideLocation, 0));
		trick.draw();		
		nextPlayer = winner;
		setStatusText("Player " + nextPlayer + " wins trick.");
		players.get(nextPlayer).wonTrick();
		updateTrickWon(nextPlayer);
		updateScore(nextPlayer);
	}
	removeActor(trumpsActor);
}


  public void startGame(Properties properties) {
	  System.out.println("Starting game");

	  setTitle("Oh_Heaven (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
	    setStatusText("Initializing...");
	    initPlayers(properties);
	    //initScores();
	    initScore();
	    for (int i=0; i <nbRounds; i++) {
	      //initTricks();
	      initRound();
	      playRound();
	      updateScores();
	      updateRoundEnd();
	    };
	    for (int i=0; i <nbPlayers; i++) updateScore(i);
	    int maxScore = 0;
	    for (int i = 0; i <nbPlayers; i++) if (players.get(i).getScore() > maxScore) maxScore = players.get(i).getScore();
	    Set <Integer> winners = new HashSet<Integer>();
	    for (int i = 0; i <nbPlayers; i++) if (players.get(i).getScore() == maxScore) winners.add(i);
	    String winText;
	    if (winners.size() == 1) {
	    	winText = "Game over. Winner is player: " +
	    			winners.iterator().next();
	    }
	    else {
	    	winText = "Game Over. Drawn winners are players: " +
	    			String.join(", ", winners.stream().map(String::valueOf).collect(Collectors.toSet()));
	    }
	    addActor(new Actor("sprites/gameover.gif"), textLocation);
	    setStatusText(winText);
	    refresh();
  }
  
  public static Oh_Heaven getInstance() {
	  if (instance == null) {
		  instance = new Oh_Heaven();
	  }
	  return instance;
  }
  
  private Oh_Heaven()
  {
	super(700, 700, 30);
	nbStartCards = 13;
	nbRounds = 2;
	enforceRules = false;
	hideNPCHands = false;
	instance = this;
  }
  
  private Oh_Heaven(Properties properties)
  {
	super(700, 700, 30);
	nbStartCards =  Integer.parseInt(properties.getProperty("nbStartCards"));
	nbRounds = Integer.parseInt(properties.getProperty("rounds"));
	enforceRules = Boolean.parseBoolean(properties.getProperty("enforceRules"));
	if (properties.getProperty("enforceRules") != null) {
		hideNPCHands = Boolean.parseBoolean(properties.getProperty("hideNPCHands"));
	}
	else {
		hideNPCHands = false;
	}
	instance = this;
	
  }

  public static void main(String[] args)
  {
	// System.out.println("Working Directory = " + System.getProperty("user.dir"));
	final Properties properties;
	if (args == null || args.length == 0) {
	  properties = PropertiesLoader.loadPropertiesFile(null);
	} else {
	      properties = PropertiesLoader.loadPropertiesFile(args[0]);
	}
	Oh_Heaven game = new Oh_Heaven(properties);
    game.startGame(properties);
  }

}

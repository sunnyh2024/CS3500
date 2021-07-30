package cs3500.freecell.model.hw02;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * represents the cell model that will be used to control card movement and piles for the Freecell
 * game.
 */
public class SimpleFreecellModel implements FreecellModel<ICard> {

  private final ArrayList<IPile<ICard>> openPiles;
  private final ArrayList<IPile<ICard>> foundationPiles;
  private final ArrayList<IPile<ICard>> cascadePiles;
  private boolean isStarted;

  /**
   * constructs a simple free cell model.
   */
  public SimpleFreecellModel() {
    this.openPiles = new ArrayList<>();
    this.cascadePiles = new ArrayList<>();
    this.foundationPiles = new ArrayList<>(
        (List.of(new FoundationPile(), new FoundationPile(), new FoundationPile(),
            new FoundationPile())));
    this.isStarted = false;
  }

  @Override
  public int getNumCardsInFoundationPile(int index) {
    getNumCardsHelp(index, foundationPiles);
    return foundationPiles.get(index).size();
  }

  @Override
  public int getNumCardsInCascadePile(int index) {
    getNumCardsHelp(index, cascadePiles);
    return cascadePiles.get(index).size();
  }

  @Override
  public int getNumCardsInOpenPile(int index) {
    getNumCardsHelp(index, openPiles);
    return openPiles.get(index).size();
  }

  private void getNumCardsHelp(int index, ArrayList<IPile<ICard>> piles) {
    if (!isStarted) {
      throw new IllegalStateException("game has not started.");
    }
    if (index >= piles.size() || index < 0) {
      throw new IllegalArgumentException("index out of bounds.");
    }
  }

  @Override
  public int getNumCascadePiles() {
    if (!isStarted) {
      return -1;
    }
    return cascadePiles.size();
  }

  @Override
  public int getNumOpenPiles() {
    if (!isStarted) {
      return -1;
    }
    return openPiles.size();
  }

  @Override
  public ICard getFoundationCardAt(int pileIndex, int cardIndex) {
    getCardAtHelp(pileIndex, cardIndex, foundationPiles);
    return foundationPiles.get(pileIndex).getCard(cardIndex);
  }

  @Override
  public ICard getCascadeCardAt(int pileIndex, int cardIndex) {
    getCardAtHelp(pileIndex, cardIndex, cascadePiles);
    return cascadePiles.get(pileIndex).getCard(cardIndex);
  }

  @Override
  public ICard getOpenCardAt(int pileIndex) {
    if (!isStarted) {
      throw new IllegalStateException("game has not started.");
    }
    if (pileIndex >= openPiles.size() || pileIndex < 0) {
      throw new IllegalArgumentException("index for pile is out of bounds.");
    }
    if (openPiles.get(pileIndex).size() == 0) {
      return null;
    }
    return openPiles.get(pileIndex).getCard(0);
  }

  /**
   * ensures that the parameters passed in are valid.
   *
   * @param pileIndex index of the piles param, starting at 0
   * @param cardIndex index of the card in the above pile
   * @param piles     the list of piles being searched
   * @throws IllegalStateException    if the game has not started
   * @throws IllegalArgumentException if the params are invalid
   */
  private void getCardAtHelp(int pileIndex, int cardIndex, ArrayList<IPile<ICard>> piles) {
    if (!isStarted) {
      throw new IllegalStateException("game has not started.");
    }
    if (pileIndex >= piles.size() || pileIndex < 0) {
      throw new IllegalArgumentException("index for pile is out of bounds.");
    }
    if (cardIndex >= piles.get(pileIndex).size() || cardIndex < 0) {
      throw new IllegalArgumentException("index for card is out of bounds.");
    }
  }

  @Override
  public List<ICard> getDeck() {
    ArrayList<ICard> deck = new ArrayList<>();
    for (CardValue val : CardValue.values()) {
      for (CardSuite suite : CardSuite.values()) {
        deck.add(new Card(val, suite));
      }
    }
    return deck;
  }

  @Override
  public void startGame(List<ICard> deck, int numCascadePiles, int numOpenPiles, boolean
      shuffle) {
    if (numOpenPiles < 1 || numCascadePiles < 4) {
      throw new IllegalArgumentException("invalid number of open and cascade piles.");
    }
    if (deck.size() != 52) {
      throw new IllegalArgumentException("deck must have 52 cards.");
    }
    if (hasDuplicates(deck)) {
      throw new IllegalArgumentException("deck contains duplicate cards.");
    }
    foundationPiles.clear();
    openPiles.clear();
    cascadePiles.clear();
    for (int i = 0; i < 4; i += 1) {
      foundationPiles.add(new FoundationPile());
    }
    for (int i = 0; i < numOpenPiles; i += 1) {
      openPiles.add(new OpenPile());
    }
    for (int i = 0; i < numCascadePiles; i += 1) {
      cascadePiles.add(new CascadePile());
    }
    if (shuffle) {
      Collections.shuffle(deck);
    }
    for (int i = 0; i < numCascadePiles; i += 1) {
      for (int j = i; j < deck.size(); j += numCascadePiles) {
        cascadePiles.get(i).addCard(deck.get(j));
      }
    }
    isStarted = true;
  }

  /**
   * returns whether the deck has any duplicate cards.
   *
   * @param deck deck of cards being checked
   * @return true if there are duplicate cards in the deck, false otherwise
   */
  private boolean hasDuplicates(List<ICard> deck) {
    ArrayList<ICard> uniques = new ArrayList<>();
    for (ICard card : deck) {
      if (uniques.contains(card)) {
        return true;
      } else {
        uniques.add(card);
      }
    }
    return false;
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) {
    if (!isStarted) {
      throw new IllegalStateException("game has not started.");
    }
    ICard mover;
    IPile<ICard> start;
    IPile<ICard> dest;
    switch (source) {
      case CASCADE:
        mover = getCascadeCardAt(pileNumber, cardIndex);
        start = cascadePiles.get(pileNumber);
        break;
      case FOUNDATION:
        mover = getFoundationCardAt(pileNumber, cardIndex);
        start = foundationPiles.get(pileNumber);
        break;
      default:
        mover = getOpenCardAt(pileNumber);
        start = openPiles.get(pileNumber);
        break;
    }
    dest = moveHelp(destination, destPileNumber);
    start.move(dest, mover);
  }

  /**
   * returns the destination pile of the move.
   *
   * @param destination    the pileType of the destination pile
   * @param destPileNumber the index of the pile
   * @return the pile at the given index of the pileType
   * @throws IllegalArgumentException if the destination is invalid (move is not possible)
   */
  private IPile<ICard> moveHelp(PileType destination, int destPileNumber) {
    switch (destination) {
      case CASCADE:
        if (destPileNumber >= cascadePiles.size() || destPileNumber < 0) {
          throw new IllegalArgumentException("move is not possible.");
        }
        return cascadePiles.get(destPileNumber);
      case FOUNDATION:
        if (destPileNumber >= foundationPiles.size() || destPileNumber < 0) {
          throw new IllegalArgumentException("move is not possible.");
        }
        return foundationPiles.get(destPileNumber);
      case OPEN:
        if (destPileNumber >= openPiles.size() || destPileNumber < 0) {
          throw new IllegalArgumentException("move is not possible.");
        }
        return openPiles.get(destPileNumber);
      default:
        throw new IllegalArgumentException("move is not possible.");
    }
  }

  @Override
  public boolean isGameOver() {
    if (!isStarted) {
      return false;
    }
    for (IPile<ICard> pile : foundationPiles) {
      if (pile.size() != 13) {
        return false;
      }
    }
    return true;
  }
}

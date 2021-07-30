package cs3500.freecell.model.hw04;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.IPile;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the cell model that will be used to control card movement and piles for the Version of
 * Freecell where the player can move multiple cards at once. However, if the player chooses to make
 * a move with multiple cards, the result must also be obtainable from a series of single moves.
 * Updates the state of the game using inputs given by the controller to play a game of Freecell.
 */
public class MultiFreecellModel extends SimpleFreecellModel {

  public MultiFreecellModel() {
    super();
  }

  @Override
  public void startGame(List<ICard> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {
    super.startGame(deck, numCascadePiles, numOpenPiles, shuffle);
    cascadePiles.clear();
    for (int i = 0; i < numCascadePiles; i += 1) {
      cascadePiles.add(new MultiCascadePile());
    }
    for (int i = 0; i < numCascadePiles; i += 1) {
      for (int j = i; j < deck.size(); j += numCascadePiles) {
        cascadePiles.get(i).addCard(deck.get(j));
      }
    }
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
      case OPEN:
        mover = getOpenCardAt(pileNumber);
        start = openPiles.get(pileNumber);
        break;
      default:
        throw new IllegalArgumentException("move is not possible");
    }
    int freeCascade = this.getNumFreeCascadePiles();
    int freeOpen = this.getNumFreeOpenPiles();
    int moveSize = start.size() - cardIndex;
    if (moveSize > (freeOpen + 1) * Math.pow(2, freeCascade)) {
      throw new IllegalArgumentException("cannot move that many cards");
    }
    switch (destination) {
      case CASCADE:
        if (destPileNumber >= cascadePiles.size() || destPileNumber < 0) {
          throw new IllegalArgumentException("move is not possible.");
        }
        dest = cascadePiles.get(destPileNumber);
        break;
      case FOUNDATION:
        if (moveSize > 1 || destPileNumber >= foundationPiles.size() || destPileNumber < 0) {
          throw new IllegalArgumentException("move is not possible.");
        }
        dest = foundationPiles.get(destPileNumber);
        break;
      case OPEN:
        if (destPileNumber >= openPiles.size() || destPileNumber < 0) {
          throw new IllegalArgumentException("move is not possible.");
        }
        dest = openPiles.get(destPileNumber);
        break;
      default:
        throw new IllegalArgumentException("move is not possible.");
    }
    start.move(dest, mover);
  }

  /**
   * returns the number of piles with no cards in them from the given ArrayList of piles.
   *
   * @param piles the list of piles to be filtered through
   * @return number of empty piles
   * @throws IllegalArgumentException if the given pile is null
   */
  private int getNumFreePiles(ArrayList<IPile<ICard>> piles) {
    int counter = 0;
    for (IPile<ICard> pile : piles) {
      if (pile.size() == 0) {
        counter += 1;
      }
    }
    return counter;
  }

  /**
   * returns the number of cascade piles in this model that have no cards in them.
   *
   * @return number of empty cascade piles
   */
  private int getNumFreeCascadePiles() {
    return getNumFreePiles(cascadePiles);
  }

  /**
   * returns the number of open piles in this model that have no cards in them.
   *
   * @return number of empty open piles.
   */
  private int getNumFreeOpenPiles() {
    return getNumFreePiles(openPiles);
  }
}

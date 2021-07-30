package cs3500.freecell.model.hw02;

import cs3500.freecell.model.PileType;

/**
 * Represents an open pile for cards.
 */
public class OpenPile extends APile {

  private static final PileType TYPE = PileType.OPEN;

  /**
   * constructs an open pile.
   */
  public OpenPile() {
    super();
  }

  @Override
  public boolean validMove(IPile<ICard> destination, ICard card) {
    if (card == null || destination == null) {
      return false;
    }
    return cards.contains(card) && destination.validMoveHelp(card);
  }

  @Override
  public boolean validMoveHelp(ICard card) {
    if (card == null) {
      return false;
    }
    return cards.size() == 0;
  }
}

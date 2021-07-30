package cs3500.freecell.model.hw02;

import cs3500.freecell.model.PileType;

/**
 * Represents a foundation pile where cards will be stacked in order based on suite.
 * Cards cannot be removed from foundation piles after they are added.
 */
public class FoundationPile extends APile {

  private static final PileType TYPE = PileType.FOUNDATION;

  /**
   * constructs an open pile.
   */
  public FoundationPile() {
    super();
  }

  @Override
  public boolean validMove(IPile<ICard> destination, ICard card) {
    return false;
  }

  @Override
  public boolean validMoveHelp(ICard card) {
    if (card == null) {
      return false;
    } else if (cards.size() == 0) {
      return card.getValue().equals("A");
    } else {
      ICard top = cards.get(cards.size() - 1);
      return top.isValueOneGreater(card) && top.isSameSuite(card);
    }
  }
}

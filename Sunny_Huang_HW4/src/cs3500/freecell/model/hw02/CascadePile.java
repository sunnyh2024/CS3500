package cs3500.freecell.model.hw02;

import cs3500.freecell.model.PileType;

/**
 * Represents a cascading pile for cards.
 * Cards are initially distributed into these piles.
 */
public class CascadePile extends APile {

  private static final PileType TYPE = PileType.CASCADE;

  /**
   * constructs an open pile.
   */
  public CascadePile() {
    super();
  }

  @Override
  public boolean validMove(IPile<ICard> destination, ICard card) {
    ICard top = cards.get(cards.size() - 1);
    return top.sameCard(card) && destination.validMoveHelp(card);
  }

  @Override
  public boolean validMoveHelp(ICard card) {
    if (card == null) {
      return false;
    } else if (cards.size() == 0) {
      return true;
    } else {
      ICard top = cards.get(cards.size() - 1);
      return card.isValueOneGreater(top) && top.isOppositeColor(card);
    }
  }
}

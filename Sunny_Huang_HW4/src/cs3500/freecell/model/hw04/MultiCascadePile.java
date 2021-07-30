package cs3500.freecell.model.hw04;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.CascadePile;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.IPile;

/**
 * representation of a cascade pile that can handle moves consisting of multiple cards. These piles
 * will be used in the MultiMove mode of Freecell.
 */
public class MultiCascadePile extends CascadePile {

  private static final PileType TYPE = PileType.CASCADE;

  /**
   * constructs a MultiCascadePile.
   */
  public MultiCascadePile() {
    super();
  }

  @Override
  public boolean validMove(IPile<ICard> destination, ICard card) {
    return cards.contains(card) && destination.validMoveHelp(card);
  }

  @Override
  public void move(IPile<ICard> other, ICard card) throws IllegalArgumentException {
    int index = cards.indexOf(card);
    int numMoves = cards.size() - index;
    for (int i = 0; i < numMoves; i += 1) {
      ICard mover = cards.get(index);
      if (validMove(other, mover)) {
        other.addCard(mover);
        this.removeCard(mover);
      } else {
        throw new IllegalArgumentException("invalid card.");
      }
    }
  }
}

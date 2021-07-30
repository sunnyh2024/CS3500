package cs3500.freecell.model.hw02;

import java.util.ArrayList;

/**
 * represents an abstract pile of cards in the FreeCell game.
 */
public abstract class APile implements IPile<ICard> {

  protected final ArrayList<ICard> cards;

  /**
   * constructs an abstract pile of cards with an empty list of cards.
   * (updated constructor from default to protected so it can be used when constructing
   * MultiCascadePile in the hw04 package).
   */
  protected APile() {
    this.cards = new ArrayList<>();
  }

  @Override
  public void addCard(ICard card) {
    if (card == null) {
      throw new IllegalArgumentException("invalid card.");
    }
    cards.add(card);
  }

  @Override
  public void removeCard(ICard card) {
    if (card == null || !cards.contains(card)) {
      throw new IllegalArgumentException("invalid card.");
    }
    cards.remove(card);
  }

  @Override
  public void move(IPile<ICard> other, ICard card) {
    if (validMove(other, card)) {
      this.removeCard(card);
      other.addCard(card);
    } else {
      throw new IllegalArgumentException("invalid move.");
    }
  }

  @Override
  public abstract boolean validMove(IPile<ICard> destination, ICard card);

  @Override
  public abstract boolean validMoveHelp(ICard card);

  @Override
  public int size() {
    return cards.size();
  }

  @Override
  public ICard getCard(int index) {
    if (index >= cards.size() || index < 0) {
      throw new IllegalArgumentException("index out of bounds.");
    }
    return cards.get(index);
  }
}

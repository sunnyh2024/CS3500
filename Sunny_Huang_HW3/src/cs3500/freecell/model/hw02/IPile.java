package cs3500.freecell.model.hw02;

/**
 * represents a pile of type K (in this case will be ICards).
 *
 * @param <K> the generic type for the pile
 */
public interface IPile<K> {

  /**
   * adds the given card to this IPile.
   *
   * @param card the card being added
   * @throws IllegalArgumentException if the card is invalid
   */
  void addCard(ICard card);

  /**
   * removes the given card to this IPile.
   *
   * @param card the card being removed
   * @throws IllegalArgumentException if card is invalid or if the card is not in the pile
   */
  void removeCard(ICard card);

  /**
   * moves the given card from this pile to the other pile.
   *
   * @param other the destination pile of the given card
   * @param card  the card being moved
   * @throws IllegalArgumentException if the move cannot be made
   */
  void move(IPile<ICard> other, ICard card);

  /**
   * returns whether the given card can be moved from this pile onto the given pile.
   *
   * @param destination the pile the card is being moved to
   * @param card        the card being moved
   */
  boolean validMove(IPile<ICard> destination, ICard card);

  /**
   * returns whether can the given card be added onto this pile.
   *
   * @param card the card being moved
   * @return true if the card can be added, false otherwise
   */
  boolean validMoveHelp(ICard card);

  /**
   * returns the size of the pile.
   *
   * @return size of the pile as an integer
   */
  int size();

  /**
   * returns the card at the given index of the pile.
   *
   * @return ICard at the given index
   * @throws IllegalArgumentException if index is invalid
   */
  ICard getCard(int index) throws IllegalArgumentException;
}

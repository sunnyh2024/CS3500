package cs3500.freecell.model.hw02;

/**
 * represents a card in a standard 52-card deck.
 */
public interface ICard {

  /**
   * prints the card as a string with its value first (A-K) and its suit (♦, ♠, ♥, ♣).
   *
   * @return String representing the card
   */
  String toString();

  /**
   * returns the value of the card.
   *
   * @return String representing value of the card
   */
  String getValue();

  /**
   * returns the suite of the card.
   *
   * @return String representing the suite of the card
   */
  String getSuite();

  /**
   * returns whether the given card's value is 1 greater than this card's value.
   *
   * @param other the other card being compared
   * @return true if other's value is 1 greater than this card's value, false otherwise
   * @throws IllegalArgumentException if other is an invalid card
   */
  boolean isValueOneGreater(ICard other);

  /**
   * returns whether the given card's suite is the same as this card's suite.
   *
   * @param other the other card being compared
   * @return true if other's suite is same as this' suite, false otherwise
   * @throws IllegalArgumentException if other is an invalid card
   */
  boolean isSameSuite(ICard other);

  /**
   * returns whether the given's card's suite has the opposite color as this card's suite.
   *
   * @param other the other card being compared
   * @return true if other's suite is the opposite color from this' suite, false otherwise
   * @throws IllegalArgumentException if other is an invalid card
   */
  boolean isOppositeColor(ICard other);

  /**
   * returns whether this card has the same value and suite as the given card.
   *
   * @param other ICard being compared
   * @return true if the two cards are the same, false otherwise
   */
  boolean sameCard(ICard other);
}

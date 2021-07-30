package cs3500.freecell.model.hw02;

/**
 * represents the different suites in a deck of standard playing cards (ace, diamond, heart, or
 * club).
 */
public enum CardSuite {
  HEART("♥"), DIAMOND("♦"), SPADE("♠"), CLUB("♣");

  private final String value;

  /**
   * Constructs a CardSuite.
   *
   * @param value the suite of the card
   */
  CardSuite(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}

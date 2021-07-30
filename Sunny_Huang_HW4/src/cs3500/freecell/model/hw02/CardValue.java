package cs3500.freecell.model.hw02;

/**
 * represents the set of values in a set of standard playing cards (A-10, J, Q, K).
 */
public enum CardValue {
  KING("K"), QUEEN("Q"), JACK("J"), TEN("10"), NINE("9"), EIGHT("8"), SEVEN("7"), SIX("6"), FIVE(
      "5"), FOUR("4"), THREE("3"), TWO("2"), ACE("A");

  private final String value;

  /**
   * Constructs a CardValue.
   *
   * @param value the value of the card
   */
  CardValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}

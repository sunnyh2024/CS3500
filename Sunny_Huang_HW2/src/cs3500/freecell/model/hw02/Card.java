package cs3500.freecell.model.hw02;

/**
 * Cards represented with a value and a suit.
 */
public final class Card implements ICard {

  private final CardValue value;
  private final CardSuite suite;

  /**
   * constructs a card.
   *
   * @param value the card's value
   * @param suite the card's suite
   * @throws IllegalArgumentException if value or suite in null
   */
  public Card(CardValue value, CardSuite suite) {
    if (value == null || suite == null) {
      throw new IllegalArgumentException("card must have non null fields.");
    }
    this.value = value;
    this.suite = suite;
  }

  @Override
  public String toString() {
    return value.toString() + suite.toString();
  }

  @Override
  public String getValue() {
    return value.toString();
  }

  @Override
  public String getSuite() {
    return suite.toString();
  }

  @Override
  public boolean isValueOneGreater(ICard other) {
    if (other == null) {
      throw new IllegalArgumentException("invalid card.");
    }
    switch (getValue()) {
      case "A":
        return other.getValue().equals("2");
      case "2":
        return other.getValue().equals("3");
      case "3":
        return other.getValue().equals("4");
      case "4":
        return other.getValue().equals("5");
      case "5":
        return other.getValue().equals("6");
      case "6":
        return other.getValue().equals("7");
      case "7":
        return other.getValue().equals("8");
      case "8":
        return other.getValue().equals("9");
      case "9":
        return other.getValue().equals("10");
      case "10":
        return other.getValue().equals("J");
      case "J":
        return other.getValue().equals("Q");
      case "Q":
        return other.getValue().equals("K");
      default:
        return false;
    }
  }

  @Override
  public boolean isSameSuite(ICard other) {
    if (other == null) {
      throw new IllegalArgumentException("invalid card.");
    }
    return other.getSuite().equals(getSuite());
  }

  @Override
  public boolean isOppositeColor(ICard other) {
    if (other == null) {
      throw new IllegalArgumentException("invalid card.");
    }
    if (getSuite().equals("♦") || getSuite().equals("♥")) {
      return other.getSuite().equals("♠") || other.getSuite().equals("♣");
    } else {
      return other.getSuite().equals("♦") || other.getSuite().equals("♥");
    }
  }

  @Override
  public boolean sameCard(ICard other) {
    if (other == null) {
      return false;
    }
    return toString().equals(other.toString());
  }
}

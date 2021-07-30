package cs3500.freecell.view;

import cs3500.freecell.model.FreecellModel;

/**
 * represents a text view of the FreeCell game.
 */
public class FreecellTextView implements FreecellView {

  private final FreecellModel<?> model;

  /**
   * constructs a FreecellTextView.
   *
   * @param model the model of the game
   */
  public FreecellTextView(FreecellModel<?> model) {
    if (model == null) {
      throw new IllegalArgumentException("model must be non-null.");
    }
    this.model = model;
  }

  @Override
  public String toString() {
    try {
      model.getNumCardsInFoundationPile(0);
    }
    catch (IllegalStateException e) {
      return "";
    }
    StringBuilder ans = new StringBuilder();
    for (int i = 0; i < 4; i += 1) {
      ans.append("F");
      ans.append(Integer.toString(i + 1));
      ans.append(": ");
      for (int j = 0; j < model.getNumCardsInFoundationPile(i); j += 1) {
        ans.append(model.getFoundationCardAt(i, j).toString());
        if (j < model.getNumCardsInFoundationPile(i) - 1) {
          ans.append(", ");
        }
      }
      ans.append("\n");
    }
    for (int i = 0; i < model.getNumOpenPiles(); i += 1) {
      ans.append("O");
      ans.append(Integer.toString(i + 1));
      ans.append(": ");
      if (model.getNumCardsInOpenPile(i) == 1) {
        ans.append(model.getOpenCardAt(i).toString());
      }
      ans.append("\n");
    }
    for (int i = 0; i < model.getNumCascadePiles(); i += 1) {
      ans.append("C");
      ans.append(Integer.toString(i + 1));
      ans.append(": ");
      for (int j = 0; j < model.getNumCardsInCascadePile(i); j += 1) {
        ans.append(model.getCascadeCardAt(i, j).toString());
        if (j < model.getNumCardsInCascadePile(i) - 1) {
          ans.append(", ");
        }
      }
      ans.append("\n");
    }
    return ans.toString().trim();
  }
}

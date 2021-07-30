package cs3500.freecell.view;

import cs3500.freecell.model.FreecellModel;
import java.io.IOException;

/**
 * represents a text view of the FreeCell game. Updates after each move and displays the changes in
 * the game state to the player in a text (String) format.
 */
public class FreecellTextView implements FreecellView {

  private final FreecellModel<?> model;
  private final Appendable out;

  /**
   * constructs a FreecellTextView with an appendable as the view destination.
   *
   * @param ap the view destination that will display the current state of the Freecell game
   * @throws IllegalArgumentException if the ap is null
   */
  public FreecellTextView(FreecellModel<?> model, Appendable ap) {
    if (ap == null || model == null) {
      throw new IllegalArgumentException("model cannot be null.");
    }
    this.model = model;
    this.out = ap;
  }

  /**
   * constructs a FreecellTextView with an empty StringBuilder as the output.
   *
   * @param model the model of the game
   */
  public FreecellTextView(FreecellModel<?> model) {
    this.model = model;
    this.out = null;
  }

  @Override
  public String toString() {
    try {
      model.getNumCardsInFoundationPile(0);
    } catch (IllegalStateException e) {
      return "";
    }
    StringBuilder ans = new StringBuilder();
    for (int i = 0; i < 4; i += 1) {
      ans.append("F");
      ans.append(Integer.toString(i + 1));
      ans.append(":");
      for (int j = 0; j < model.getNumCardsInFoundationPile(i); j += 1) {
        ans.append(" ");
        ans.append(model.getFoundationCardAt(i, j).toString());
        if (j < model.getNumCardsInFoundationPile(i) - 1) {
          ans.append(",");
        }
      }
      ans.append("\n");
    }
    for (int i = 0; i < model.getNumOpenPiles(); i += 1) {
      ans.append("O");
      ans.append(Integer.toString(i + 1));
      ans.append(":");
      if (model.getNumCardsInOpenPile(i) == 1) {
        ans.append(" ");
        ans.append(model.getOpenCardAt(i).toString());
      }
      ans.append("\n");
    }
    for (int i = 0; i < model.getNumCascadePiles(); i += 1) {
      ans.append("C");
      ans.append(Integer.toString(i + 1));
      ans.append(":");
      for (int j = 0; j < model.getNumCardsInCascadePile(i); j += 1) {
        ans.append(" ");
        ans.append(model.getCascadeCardAt(i, j).toString());
        if (j < model.getNumCardsInCascadePile(i) - 1) {
          ans.append(",");
        }
      }
      ans.append("\n");
    }
    return ans.toString().trim();
  }

  @Override
  public void renderBoard() throws IOException {
    if (out == null) {
      System.out.print(this);
    } else {
      out.append(this.toString());
    }
  }

  @Override
  public void renderMessage(String message) throws IOException {
    if (out == null) {
      System.out.print(message);
    } else {
      out.append(message);
    }
  }
}

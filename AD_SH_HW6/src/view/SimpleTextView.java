package view;

import java.io.IOException;
import model.Utils;

/**
 * represents an implementation of the text view that will only be able to send messages to the
 * appendable object. This view will not be able to display any images.
 */
public class SimpleTextView implements ITextView {

  private final Appendable dest;

  /**
   * Constructs a SimpleTextView when not given an Appendable.
   */
  public SimpleTextView() {
    this.dest = null;
  }

  /**
   * Constructs a SimpleTextView with the given Appendable.
   *
   * @param dest the appendable that messages will be written to
   * @throws IllegalArgumentException if the given Appendable is null
   */
  public SimpleTextView(Appendable dest) throws IllegalArgumentException {
    Utils.requireNonNull(dest);
    this.dest = dest;
  }

  @Override
  public void renderMessage(String message) throws IOException, IllegalArgumentException {
    Utils.requireNonNull(message);
    if (this.dest == null) {
      System.out.println(message);
    } else {
      this.dest.append(message);
    }
  }
}

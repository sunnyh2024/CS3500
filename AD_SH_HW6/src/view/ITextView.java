package view;

import java.io.IOException;

/**
 * Represents a text version of the view that will send messages to the appendable. This type of
 * view will be unable to render images or offer any sort of GUI to the user.
 */
public interface ITextView {

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException              if transmission of the board to the provided data destination
   *                                  fails
   * @throws IllegalArgumentException if the given message is null
   */
  void renderMessage(String message) throws IOException, IllegalArgumentException;
}

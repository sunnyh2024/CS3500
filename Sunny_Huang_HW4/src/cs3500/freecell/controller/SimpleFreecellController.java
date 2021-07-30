package cs3500.freecell.controller;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a controller for the freecell card game. Will deal with the inputs and work with
 * SimpleFreecellModel to play a game of Freecell. This controller (updated to also work with
 * MultiFreecellModel), will parse inputs and send the resulting state changes in the model to the
 * view to be displayed.
 */
public class SimpleFreecellController implements FreecellController<ICard> {

  private final Scanner input;
  private final FreecellModel<ICard> model;
  private final FreecellView view;

  /**
   * constructs a SimpleFreecellController.
   *
   * @param model model that the controller will send information to
   * @param rd    readable the controller will get input from
   * @param ap    destination the state of the board will be transmitted to
   * @throws IllegalArgumentException if the model, readable or appendable are null
   */
  public SimpleFreecellController(FreecellModel<ICard> model, Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null.");
    }
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("input/output cannot be null.");
    }
    this.model = model;
    this.input = new Scanner(rd);
    this.view = new FreecellTextView(this.model, ap);
  }

  @Override
  public void playGame(List<ICard> deck, int numCascades, int
      numOpens, boolean shuffle) throws IllegalArgumentException, IllegalStateException {
    if (deck == null) {
      throw new IllegalArgumentException("deck cannot be null.");
    }
    try {
      this.model.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException e) {
      this.renderMessage("Could not start game.");
      return;
    }

    try {
      view.renderMessage("\n");
      view.renderBoard();
    } catch (Exception e) {
      throw new IllegalStateException("Failed to write to view Appendable.");
    }

    ControlState currentState = ControlState.SOURCE;
    PileType source = null;
    int pileIndex = -1;
    int cardIndex = -1;
    PileType destination = null;
    int destIndex = -1;

    while (input.hasNext() && !model.isGameOver()) {
      String nextInput = input.next();

      if (nextInput.equals("q") || nextInput.equals("Q")) {
        this.renderMessage("\nGame quit prematurely.");
        return;
      }

      switch (currentState) {
        case SOURCE:
          try {
            source = this.pileHelp(nextInput);
            pileIndex = Integer.parseInt(nextInput.substring(1)) - 1;
            currentState = ControlState.INDEX;
          } catch (IllegalArgumentException e) {
            this.renderMessage("\nInvalid source pile. Try again.");
          }
          break;
        case INDEX:
          try {
            cardIndex = Integer.parseInt(nextInput) - 1;
            currentState = ControlState.DESTINATION;
          } catch (IllegalArgumentException e) {
            this.renderMessage("\nInvalid card index. Try again.");
          }
          break;
        default:
          try {
            destination = this.pileHelp(nextInput);
            destIndex = Integer.parseInt(nextInput.substring(1)) - 1;
            try {
              model.move(source, pileIndex, cardIndex, destination, destIndex);
            } catch (IllegalArgumentException e1) {
              this.renderMessage("\nInvalid move. Try again.");
            }
            try {
              view.renderMessage("\n");
              view.renderBoard();
            } catch (IOException e2) {
              throw new IllegalStateException("Failed to write to view Appendable.");
            }
            currentState = ControlState.SOURCE;
          } catch (IllegalArgumentException e) {
            this.renderMessage("\nInvalid destination pile. Try again.");
          }
          break;
      }
    }
    if (model.isGameOver()) {
      this.renderMessage("\nGame over.");
      return;
    }
    if (!input.hasNext()) {
      throw new IllegalStateException("Failed to read input.");
    }
  }

  /**
   * returns the pileType that the given String is referring to.
   *
   * @param input string input that will be parsed and interpreted
   * @return the PileType that the input is referring to (Cascade, Open, or Foundation)
   * @throws IllegalArgumentException if the input is bad and does not refer to a valid PileType
   */
  private PileType pileHelp(String input) throws IllegalArgumentException {
    char pileChar = input.charAt(0);
    switch (pileChar) {
      case 'C':
        return PileType.CASCADE;
      case 'O':
        return PileType.OPEN;
      case 'F':
        return PileType.FOUNDATION;
      default:
        throw new IllegalArgumentException("bad character.");
    }
  }

  /**
   * tries to render the given message and throws an IllegalStateException if unable to do so.
   *
   * @param message message that will be rendered
   * @throws IllegalStateException if the message is unable to be rendered
   */
  private void renderMessage(String message) throws IllegalStateException {
    try {
      view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("message cannot be written to appendable.");
    }
  }
}

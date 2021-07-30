package controller;

import controller.commands.IEditorCommand;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.IMultiLayerEditorModel;
import model.Utils;
import view.ITextView;
import view.SimpleTextView;

/**
 * To represent a controller that takes input from the user to control the editor model. The
 * controller will take in a map, from which it will be able to support any of the commands included
 * in that map. Currently, the controller will be sending these commands to an
 * IMultiLayerEditorModel only (This controller cannot support SimpleImageEditorModels).
 */
public class SimpleEditorController implements IEditorController {

  private final IMultiLayerEditorModel model;
  private final Scanner sc;
  private final ITextView view;
  private final Map<String, Function<Scanner, IEditorCommand>> commands;

  /**
   * Constructs the controller with given objects to handle input and output, as well as a map that
   * determines the functionality that this controller will support.
   *
   * @param model The IMultiLayerEditorModel that is used to provide the game state
   * @param rd    The Readable object to handle input
   * @param ap    the Appendable object to handle output
   * @throws IllegalArgumentException if any of the given inputs is null
   */
  public SimpleEditorController(IMultiLayerEditorModel model, Readable rd, Appendable ap,
      Map<String, Function<Scanner, IEditorCommand>> cmds)
      throws IllegalArgumentException {
    this.model = Utils.requireNonNull(model);
    this.sc = new Scanner(Utils.requireNonNull(rd));
    this.view = new SimpleTextView(Utils.requireNonNull(ap));
    this.commands = Utils.requireNonNull(cmds);
  }

  @Override
  public void startEditor() throws IllegalStateException {
    while (sc.hasNext()) {
      IEditorCommand c;
      String next = sc.next().toLowerCase();
      if (next.equals("quit")) {
        this.renderMessage("Quit Editor.");
        return;
      }
      Function<Scanner, IEditorCommand> command = this.commands.getOrDefault(next, null);
      if (command == null) {
        this.renderMessage("Could not parse command. Please try again.\n");
      } else {
        try {
          c = command.apply(sc);
          c.execute(this.model);
        } catch (IllegalArgumentException iae) {
          if (iae.getMessage().equals("Quit")) {
            this.renderMessage("Quit Editor.");
            return;
          } else {
            this.renderMessage(iae.getMessage());
            this.renderMessage("\n");
          }
        }
      }
    }
    if (!sc.hasNext()) {
      throw new IllegalStateException("Cannot read input.");
    }
  }

  /**
   * Renders the message to the Appendable message through the view and handles the IOExcpetion.
   *
   * @param message The message to be written to the Appendable object
   * @throws IllegalStateException when the given message fails to write to the Appendable object
   */
  private void renderMessage(String message) throws IllegalStateException {
    try {
      this.view.renderMessage(message);
    } catch (IOException ie) {
      throw new IllegalStateException("Message failed to write to Appendable object");
    }
  }
}

package controller.commands;

import model.IMultiLayerEditorModel;
import model.Utils;

/**
 * Function object that represents a command that will be executed on an IMultiLayerEditorModel to
 * copy a layer to given index.
 */
public class EditorCopyCommand implements IEditorCommand {

  private final int copyLayer;
  private final int destLayer;

  /**
   * Constructs an EditorCopyCommand with the given layer to be copied and the destination index of
   * the copied layer.
   *
   * @param copyLayer index of layer that will be copied
   * @param destLayer index of the destination layer
   * @throws IllegalArgumentException if either index is less than 1
   */
  public EditorCopyCommand(int copyLayer, int destLayer) throws IllegalArgumentException {
    if (copyLayer < 1 || destLayer < 1) {
      throw new IllegalArgumentException("Layer must be positive");
    }
    this.copyLayer = copyLayer - 1;
    this.destLayer = destLayer - 1;
  }

  @Override
  public void execute(IMultiLayerEditorModel model) throws IllegalArgumentException {
    Utils.requireNonNull(model);
    model.copyImage(copyLayer, destLayer);
  }
}

package controller.commands;

import model.IMultiLayerEditorModel;
import model.Utils;

/**
 * Function object that represents a command that will be executed on an IMultiLayerEditorModel to
 * apply a sepia filter to a layer of the model.
 */
public class EditorSepiaCommand implements IEditorCommand {

  private final int layer;

  /**
   * Constructs an EditorSepiaCommand with the given layer index to which the sepia filter will be
   * applied.
   *
   * @param layer the layer that the sepia filter will be applied to
   * @throws IllegalArgumentException if the layer is not positive
   */
  public EditorSepiaCommand(int layer) throws IllegalArgumentException {
    if (layer < 1) {
      throw new IllegalArgumentException("Layer must be positive");
    }
    this.layer = layer - 1;
  }

  @Override
  public void execute(IMultiLayerEditorModel model) throws IllegalArgumentException {
    Utils.requireNonNull(model);
    model.sepia(this.layer);
  }
}

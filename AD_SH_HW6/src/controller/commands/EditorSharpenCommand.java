package controller.commands;

import model.IMultiLayerEditorModel;
import model.Utils;

/**
 * Function object that represents a command that will be executed on an IMultiLayerEditorModel to
 * apply a sharpen filter to a layer of the model.
 */
public class EditorSharpenCommand implements IEditorCommand {

  private final int layer;

  /**
   * Constructs an EditorSharpenCommand with the given layer in the editor to be sharpened.
   *
   * @param layer the layer to be sharpened
   * @throws IllegalArgumentException if the layer is not positive
   */
  public EditorSharpenCommand(int layer) throws IllegalArgumentException {
    if (layer < 1) {
      throw new IllegalArgumentException("Layer must be positive");
    }
    this.layer = layer - 1;
  }

  @Override
  public void execute(IMultiLayerEditorModel model) throws IllegalArgumentException {
    Utils.requireNonNull(model);
    model.sharpen(this.layer);
  }
}

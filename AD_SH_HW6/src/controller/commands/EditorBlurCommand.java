package controller.commands;

import model.IMultiLayerEditorModel;
import model.Utils;

/**
 * Function object that represents a command that will be executed on an IMultiLayerEditorModel to
 * blur a layer of the model.
 */
public class EditorBlurCommand implements IEditorCommand {

  private final int layer;

  /**
   * Constructs an EditorBlurCommand with the given layer in the editor to be blurred.
   *
   * @param layer the layer to be blurred
   * @throws IllegalArgumentException if the layer is not positive
   */
  public EditorBlurCommand(int layer) throws IllegalArgumentException {
    if (layer < 1) {
      throw new IllegalArgumentException("Layer must be positive");
    }
    this.layer = layer - 1;
  }

  @Override
  public void execute(IMultiLayerEditorModel model) throws IllegalArgumentException {
    Utils.requireNonNull(model);
    model.blur(this.layer);
  }
}

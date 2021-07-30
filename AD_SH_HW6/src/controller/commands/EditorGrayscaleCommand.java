package controller.commands;

import model.IMultiLayerEditorModel;
import model.Utils;

/**
 * Function object that represents a command that will be executed on an IMultiLayerEditorModel to
 * apply a grayscale filter to a layer of the model.
 */
public class EditorGrayscaleCommand implements IEditorCommand {

  private final int layer;

  /**
   * Constructs an EditorGrayscaleCommand with the given layer index to which the grayscale filter
   * will be applied.
   *
   * @param layer the layer the grayscale filter will be applied to
   * @throws IllegalArgumentException if the layer is not positive
   */
  public EditorGrayscaleCommand(int layer) throws IllegalArgumentException {
    if (layer < 1) {
      throw new IllegalArgumentException("Layer must be positive");
    }
    this.layer = layer - 1;
  }

  @Override
  public void execute(IMultiLayerEditorModel model) throws IllegalArgumentException {
    Utils.requireNonNull(model);
    model.grayscale(this.layer);
  }
}

package controller.commands;

import model.IMultiLayerEditorModel;
import model.Utils;

/**
 * Function object that represents a command that will be executed on an IMultiLayerEditorModel to
 * change the visibility of a layer.
 */
public class EditorChangeVisibilityCommand implements IEditorCommand {

  private final int layer;

  /**
   * Constructs an EditorChangeVisibilityCommand with the given layer in the editor whose visibility
   * will be changed.
   *
   * @param layer the layer whose visibility will be altered
   * @throws IllegalArgumentException if the layer is not positive
   */
  public EditorChangeVisibilityCommand(int layer) throws IllegalArgumentException {
    if (layer < 1) {
      throw new IllegalArgumentException("Layer must be positive.");
    }
    this.layer = layer - 1;
  }

  @Override
  public void execute(IMultiLayerEditorModel model) throws IllegalArgumentException {
    Utils.requireNonNull(model);
    model.changeVisibility(layer);
  }
}

package controller.commands;

import model.IMultiLayerEditorModel;
import model.Utils;

/**
 * Function object that represents a command that will be executed on an IMultiLayerEditorModel to
 * remove all layers from the model.
 */
public class EditorRemoveAllCommand implements IEditorCommand {


  @Override
  public void execute(IMultiLayerEditorModel model) throws IllegalArgumentException {
    Utils.requireNonNull(model);
    int size = model.getAllImages().size();
    for (int i = size; i > 0; i -= 1) {
      model.removeImage(i - 1);
    }
  }
}

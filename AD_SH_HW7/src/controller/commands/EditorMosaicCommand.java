package controller.commands;

import model.ECMultiLayerModel;
import model.IMultiLayerEditorModel;
import model.Utils;

/**
 * Function object that represents a command that will be executed on an IMultiLayerEditorModel to
 * applies a mosaic filter to an image at the given layer.
 */
public class EditorMosaicCommand implements IEditorCommand {

  private int seedct;
  private int layer;

  /**
   * Constructs an EditorMosaicCommand with the given seed count and layer.
   *
   * @param seedct The number of seeds that will be created in the filter
   * @param layer  the layer of the model at which the filter will be applied
   * @throws IllegalArgumentException if the seed count or layer is invalid
   */
  public EditorMosaicCommand(int seedct, int layer) throws IllegalArgumentException {
    if (seedct < 0 || layer < 1) {
      throw new IllegalArgumentException(
          "Number of seeds should be at least 0 and layer should be at least 1");
    }
    this.seedct = seedct;
    this.layer = layer - 1;
  }

  @Override
  public void execute(IMultiLayerEditorModel model) throws IllegalArgumentException {
    ECMultiLayerModel newModel = new ECMultiLayerModel(
        Utils.requireNonNull(model).getSingleImage(layer));
    newModel.mosaic(seedct, 0);
    model.removeImage(layer);
    model.addImage(newModel.getSingleImage(0), layer);
  }
}

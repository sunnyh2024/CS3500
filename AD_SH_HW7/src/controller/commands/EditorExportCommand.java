package controller.commands;

import model.IMultiLayerEditorModel;
import model.IPixel;
import model.ImageUtil;
import model.Utils;

/**
 * Function object that represents a command that will be executed to export a single layer in an
 * IMultiLayerEditorModel as an image.
 */
public class EditorExportCommand implements IEditorCommand {

  private final String dest;
  private final String type;
  private final int layer;

  /**
   * Constructs an EditorExportCommand with the given destination path and layer index of the image
   * to be exported.
   *
   * @param dest  the filepath that the image will be located at
   * @param layer the layer of the model that will be exported
   * @throws IllegalArgumentException if the layer index is not positive or if the destination is
   *                                  invalid
   */
  public EditorExportCommand(String dest, int layer) throws IllegalArgumentException {
    if (layer < 1) {
      throw new IllegalArgumentException("Layer must be positive.");
    }
    this.layer = layer - 1;
    this.dest = Utils.requireNonNull(dest);
    this.type = dest.substring(dest.lastIndexOf(".") + 1);
  }

  @Override
  public void execute(IMultiLayerEditorModel model) throws IllegalArgumentException {
    Utils.requireNonNull(model);
    IPixel[][] img = model.getSingleImage(layer);
    if (type.equalsIgnoreCase("ppm")) {
      ImageUtil.writePPM(img, dest);
    } else if (type.equalsIgnoreCase("jpeg") || type.equalsIgnoreCase("jpg")) {
      ImageUtil.exportJPEG(img, dest);
    } else if (type.equalsIgnoreCase("png")) {
      ImageUtil.exportPNG(img, dest);
    } else {
      throw new IllegalArgumentException("Image type not supported.");
    }
  }
}

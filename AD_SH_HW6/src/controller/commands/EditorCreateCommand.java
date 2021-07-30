package controller.commands;

import model.IMultiLayerEditorModel;
import model.IPixel;
import model.ImageUtil;
import model.Utils;

/**
 * Function object that represents a command that will be executed on an IMultiLayerEditorModel to
 * create a layer at the given index.
 */
public class EditorCreateCommand implements IEditorCommand {

  private final String file;
  private final int layer;

  /**
   * Constructs an EditorCreateCommand with the given file and layer index it will be created at.
   *
   * @param file  that file that will be read and converted into a 2D IPixel array
   * @param layer the layer of the model at which the image will be added
   * @throws IllegalArgumentException if the layer is not positive or the file is invalid
   */
  public EditorCreateCommand(String file, int layer) throws IllegalArgumentException {
    if (layer < 1) {
      throw new IllegalArgumentException("Layer must be positive");
    }
    this.layer = layer - 1;
    this.file = (String) Utils.requireNonNull(file);
  }

  @Override
  public void execute(IMultiLayerEditorModel model) throws IllegalArgumentException {
    Utils.requireNonNull(model);
    IPixel[][] img;
    if (file.contains(".ppm")) {
      img = ImageUtil.readPPM(file);
    } else {
      img = ImageUtil.readImage(file);
    }
    model.addImage(img, layer);
  }
}

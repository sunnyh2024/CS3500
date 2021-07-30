package controller.commands;

import model.IMultiLayerEditorModel;
import model.IPixel;
import model.ImageUtil;
import model.Utils;

/**
 * Function object that represents a command that will be executed on a IMultiLayerEditorModel to
 * export the topmost visible layer in the model, ignoring any invisible layers.
 */
public class EditorExportTopmostCommand implements IEditorCommand {

  private final String dest;
  private final String type;

  /**
   * Constructs a EditorExportTopmostCommand with the given dest filename, where the image will be
   * exported to.
   *
   * @param dest destination filepath of the exported image
   * @throws IllegalArgumentException if the destination is invalid
   */
  public EditorExportTopmostCommand(String dest) throws IllegalArgumentException {
    Utils.requireNonNull(dest);
    this.dest = dest;
    this.type = dest.substring(dest.lastIndexOf(".") + 1);
  }

  @Override
  public void execute(IMultiLayerEditorModel model) throws IllegalArgumentException {
    Utils.requireNonNull(model);
    IPixel[][] img = model.getTopMostImage();
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

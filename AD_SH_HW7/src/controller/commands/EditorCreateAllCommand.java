package controller.commands;

import java.util.ArrayList;
import model.IMultiLayerEditorModel;
import model.IPixel;
import model.ImageUtil;
import model.Utils;

/**
 * Function object that represents a command that will be executed on an IMultiLayerEditorModel to
 * add all the layers from a given directory to the model, starting at the given index.
 */
public class EditorCreateAllCommand implements IEditorCommand {

  private final String dir;
  private final int layer;

  /**
   * Constructs an EditorCreateAllCommand with the given file and starting layer index at which
   * images will be loaded/created.
   *
   * @param dir   the directory to be used to get all the files to be added to a model
   * @param layer the layer of the model at which the image will be added
   * @throws IllegalArgumentException if the layer is not positive or the directory is invalid
   */
  public EditorCreateAllCommand(String dir, int layer) throws IllegalArgumentException {
    if (layer < 1) {
      throw new IllegalArgumentException("Layer must be positive");
    }
    this.layer = layer - 1;
    this.dir = (String) Utils.requireNonNull(dir);
  }

  @Override
  public void execute(IMultiLayerEditorModel model) throws IllegalArgumentException {
    Utils.requireNonNull(model);
    ArrayList<IPixel[][]> images = ImageUtil.readDirectory(dir);
    ArrayList<Boolean> visibilities = ImageUtil.getVisibilities(dir);
    for (int i = 0; i < images.size(); i += 1) {
      model.addImage(images.get(i), layer + i);
      if (!visibilities.get(i)) {
        model.changeVisibility(i);
      }
    }
  }
}

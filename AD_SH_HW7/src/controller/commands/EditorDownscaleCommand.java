package controller.commands;

import java.util.ArrayList;
import model.ECMultiLayerModel;
import model.IMultiLayerEditorModel;
import model.IPixel;
import model.Utils;

/**
 * Function object that represents a command that will be executed on an IMultiLayerEditorModel to
 * downscale images by the given width and height in pixels.
 */
public class EditorDownscaleCommand implements IEditorCommand {

  private int numWidthPixels;
  private int numHeightPixels;

  /**
   * Constructs an EditorDownscaleCommand with the given width and height reduction.
   *
   * @param numWidthPixels  Number of pixels for the width to reduce an image by
   * @param numHeightPixels Number of pixels for the height to reduce an image by
   * @throws IllegalArgumentException if the width or height is less than 0
   */
  public EditorDownscaleCommand(int numWidthPixels, int numHeightPixels)
      throws IllegalArgumentException {
    if (numHeightPixels < 0 || numWidthPixels < 0) {
      throw new IllegalArgumentException("Both inputs should be at least 0");
    }
    this.numWidthPixels = numWidthPixels;
    this.numHeightPixels = numHeightPixels;
  }

  @Override
  public void execute(IMultiLayerEditorModel model) throws IllegalArgumentException {
    ECMultiLayerModel newModel = new ECMultiLayerModel(Utils.requireNonNull(model));
    newModel.downscale(numWidthPixels, numHeightPixels);
    new EditorRemoveAllCommand().execute(model);
    ArrayList<IPixel[][]> imgs = newModel.getAllImages();
    for (int i = 0; i < imgs.size(); i += 1) {
      model.addImage(imgs.get(i), i);
    }
  }
}

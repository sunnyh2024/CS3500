package model;

import filters.DownscaleFilter;
import filters.IFilter;
import filters.MosaicFilter;
import java.util.ArrayList;

/**
 * Represents a model that has all the functionality of IMultiLayerEditorModel, as well as the
 * ability to downscale and apply mosaic effects to layers, the ability to get the total number of
 * layers in the model, as well as the ability to get the index of the topmost layer.
 */
public class ECMultiLayerModel extends MultiLayerEditorModel implements IECMultiLayerModel {

  /**
   * Constructs an ECMultiLayerModel using the given array of images represented as 2D arrays of
   * IPixels.
   *
   * @param images that images that will be added to this model
   */
  public ECMultiLayerModel(IPixel[][]... images) {
    super(images);
  }

  /**
   * Constructs an ECMultiLayerModel using the given IMultiLayerEditorModel.
   *
   * @param model the parent model whose images will be added into this model
   * @throws IllegalArgumentException if the given model is invalid
   */
  public ECMultiLayerModel(IMultiLayerEditorModel model) {
    Utils.requireNonNull(model);
    ArrayList<IPixel[][]> imgs = model.getAllImages();
    for (int i = 0; i < imgs.size(); i++) {
      super.addImage(imgs.get(i), i);
    }
  }

  @Override
  public void mosaic(int seeds, int layer) throws IllegalArgumentException {
    if (seeds > super.width * super.height || seeds < 1) {
      throw new IllegalArgumentException("Given number of seeds is not possible for the image");
    }
    super.checkIndex(layer);
    IFilter mosaicFilter = new MosaicFilter(seeds);
    IPixel[][] mosaicImage = mosaicFilter.apply(super.getSingleImage(layer));
    super.removeImage(layer);
    super.addImage(mosaicImage, layer);
  }

  @Override
  public void downscale(int numPixelsWidth, int numPixelsHeight) throws IllegalArgumentException {
    if (numPixelsWidth > super.width || numPixelsHeight > super.height || numPixelsWidth < 0
        || numPixelsHeight < 0) {
      throw new IllegalArgumentException(
          "Given number of pixels to downscale by is not possible for the image");
    }
    int newWidth = super.width - numPixelsWidth;
    int newHeight = super.height - numPixelsHeight;
    IFilter downscaleFilter = new DownscaleFilter(newWidth, newHeight);
    ArrayList<IPixel[][]> newImages = new ArrayList<IPixel[][]>();
    for (int i = 0; i < super.imageModels.size(); i += 1) {
      newImages.add(downscaleFilter.apply(super.getSingleImage(i)));
    }
    for (int i = newImages.size(); i > 0; i -= 1) {
      super.removeImage(i - 1);
    }
    // Separated into two for loops because of the size invariant in the addImage method
    for (int i = 0; i < newImages.size(); i += 1) {
      super.addImage(newImages.get(i), i);
    }
  }

  @Override
  public int getNumLayers() {
    return this.imageModels.size();
  }

  @Override
  public int getTopLayerIndex() {
    ArrayList<Boolean> visibility = super.getLayerVisibility();
    for (int i = visibility.size() - 1; i > -1; i -= 1) {
      if (visibility.get(i)) {
        return i;
      }
    }
    return -1;
  }

}

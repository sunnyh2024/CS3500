package model;

/**
 * Represents an extension of IMultiLayerEditorModel with additional functionality that allows the
 * user to downscale images and apply mosaic filters to images. Also includes convenience methods
 * that get the total number of images in the model, as well as the index of the topmost visible
 * layer in the model that are not available in IMultiLayerEditorModel.
 */
public interface IECMultiLayerModel extends IMultiLayerEditorModel {

  /**
   * Applies a mosaic filter to the given layer with the given number of seeds.
   *
   * @param seeds number of seeds that the image layer should contain
   * @param layer the layer of the model to apply the mosaic filter
   * @throws IllegalArgumentException if the seeds or if the layer index is out of bounds
   */
  void mosaic(int seeds, int layer) throws IllegalArgumentException;

  /**
   * Downscales all image layers by the numbers of given pixels.
   *
   * @param numPixelsWidth  the number of pixels to reduce by for the width
   * @param numPixelsHeight the number of pixels to reduce by for the height
   * @throws IllegalArgumentException if too many pixels are removed or if the model does not
   *                                  contain any images
   */
  void downscale(int numPixelsWidth, int numPixelsHeight) throws IllegalArgumentException;

  /**
   * Finds the number of layers in this model.
   *
   * @return the number of layers in this model
   */
  int getNumLayers();

  /**
   * Finds the index of the highest image that is visible.
   *
   * @return the index of the highest image that is visible. Returns -1 if there is no visible image
   *         layer
   */
  int getTopLayerIndex();
}

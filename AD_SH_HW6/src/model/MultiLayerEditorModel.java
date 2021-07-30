package model;

import java.util.ArrayList;

/**
 * Represents the model for a multi layer image editor capable of the following actions.
 * - blurring and sharpening image layers.
 * - applying monochrome and sepia filters to image layers.
 * - removing, adding, and copying image layers.
 * - getting a single image layer, the top-most image layer(the highest image layer that is visible)
 * , all of the images at once, and all of the visibility identifiers at once for the images
 * The model uses a SimpleImageEditorModel for each image(a 2D array of IPixels) to edit each layer.
 * This model can deal with multiple image layers at once.
 */
public class MultiLayerEditorModel implements IMultiLayerEditorModel {

  private final ArrayList<ImageEditorModel> imageModels;
  private final ArrayList<Boolean> visibilityIdentifiers;
  private int height;
  private int width;


  /**
   * Constructs a MultiLayerEditorModel from multiple IPixel 2D array arguments which represent
   * images.
   * @param images Array of 2D array of IPixel arguments
   * @throws IllegalArgumentException if any of the given images are invalid(null, not rectangular,
   *                                  or not the same size as the other images)
   */
  public MultiLayerEditorModel(IPixel[][]... images) throws IllegalArgumentException {
    this.imageModels = new ArrayList<>();
    this.visibilityIdentifiers = new ArrayList<Boolean>();
    if (images.length > 0) {
      this.height = images[0].length;
      this.width = images[0][0].length;
      for (IPixel[][] image : images) {
        ImageUtil.checkImageRect(image);
        if (image.length != height || image[0].length != width) {
          throw new IllegalArgumentException("Images must have the same dimensions.");
        }
        imageModels.add(new SimpleImageEditorModel(image));
        visibilityIdentifiers.add(true);
      }
    }
  }

  @Override
  public ArrayList<IPixel[][]> getAllImages() throws IllegalArgumentException {
    ArrayList<IPixel[][]> images = new ArrayList<>();
    for (ImageEditorModel model : imageModels) {
      images.add(model.getImage());
    }
    return images;
  }

  @Override
  public IPixel[][] getSingleImage(int index) throws IllegalArgumentException {
    this.checkIndex(index);
    return imageModels.get(index).getImage();
  }

  @Override
  public IPixel[][] getTopMostImage() throws IllegalArgumentException {
    IPixel[][] image = null;
    int size = imageModels.size();
    for (int i = size - 1; i >= 0; i -= 1) {
      if (this.visibilityIdentifiers.get(i)) {
        image = imageModels.get(i).getImage();
        break;
      }
    }
    if (image == null) {
      throw new IllegalArgumentException("No visible image found");
    }
    return image;
  }

  @Override
  public ArrayList<Boolean> getLayerVisibility() {
    ArrayList<Boolean> list = new ArrayList<>();
    for (Boolean b : this.visibilityIdentifiers) {
      if (b) {
        list.add(true);
      } else {
        list.add(false);
      }
    }
    return list;
  }


  @Override
  public void blur(int index) throws IllegalArgumentException {
    this.checkIndex(index);
    imageModels.get(index).blur();
  }

  @Override
  public void sharpen(int index) throws IllegalArgumentException {
    this.checkIndex(index);
    imageModels.get(index).sharpen();
  }

  @Override
  public void grayscale(int index) throws IllegalArgumentException {
    this.checkIndex(index);
    imageModels.get(index).grayscale();
  }

  @Override
  public void sepia(int index) throws IllegalArgumentException {
    this.checkIndex(index);
    imageModels.get(index).sepia();
  }

  @Override
  public void addImage(IPixel[][] image, int index) throws IllegalArgumentException {
    ImageUtil.checkImageRect(image);
    if (index > imageModels.size() || index < 0) {
      throw new IllegalArgumentException("Index out of bounds.");
    }
    if (imageModels.size() == 0 || (image.length == height && image[0].length == width)) {
      if (imageModels.size() == 0) {
        this.height = image.length;
        this.width = image[0].length;
      }
      imageModels.add(index, new SimpleImageEditorModel(image));
      this.visibilityIdentifiers.add(index, true);
    } else {
      throw new IllegalArgumentException("Given image is not the same size as other layers.");
    }
  }

  @Override
  public void removeImage(int index) throws IllegalArgumentException {
    this.checkIndex(index);
    this.imageModels.remove(index);
  }

  @Override
  public void copyImage(int copyIndex, int destIndex) throws IllegalArgumentException {
    this.checkIndex(copyIndex);
    this.addImage(this.getSingleImage(copyIndex), destIndex);
  }

  @Override
  public void changeVisibility(int index) throws IllegalArgumentException {
    this.checkIndex(index);
    boolean visible = this.visibilityIdentifiers.get(index);
    this.visibilityIdentifiers.set(index, !visible);
  }

  /**
   * Checks whether the given index that corresponds to this model's layers is valid.
   *
   * @param index The desired index that corresponds to a ImageEditorModel
   * @throws IllegalArgumentException if the index is out of bounds of imageModels
   */
  private void checkIndex(int index) throws IllegalArgumentException {
    if (index >= imageModels.size() || index < 0) {
      throw new IllegalArgumentException("Index out of bounds.");
    }
  }
}

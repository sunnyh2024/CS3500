package model;

import filters.BlurFilter;
import filters.GrayscaleFilter;
import filters.IFilter;
import filters.SepiaFilter;
import filters.SharpenFilter;

/**
 * Represents the model for a simple image editor capable of the following filters.
 * - blurring and sharpening images.
 * - applying monochrome and sepia filters.
 * The model represents an image as a double array of IPixel objects.
 */
public class SimpleImageEditorModel implements ImageEditorModel {

  private IPixel[][] image;
  private final int width;
  private final int height;
  private final IFilter blurFilter;
  private final IFilter sharpenFilter;
  private final IFilter grayscaleFilter;
  private final IFilter sepiaFilter;

  /**
   * Constructs a model (makes a deep copy of the given double array of pixels).
   *
   * @param image double array of pixels
   * @throws IllegalArgumentException if the image is null or the image is not rectangular
   */
  public SimpleImageEditorModel(IPixel[][] image) throws IllegalArgumentException {
    ImageUtil.checkImageRect(image);
    this.width = image[0].length;
    this.height = image.length;
    IPixel[][] copy = new IPixel[this.height][this.width];
    for (int i = 0; i < this.height; i += 1) {
      for (int j = 0; j < this.width; j += 1) {
        copy[i][j] = new PixelImpl(image[i][j].getRGB());
      }
    }
    this.image = copy;
    this.blurFilter = new BlurFilter();
    this.sharpenFilter = new SharpenFilter();
    this.grayscaleFilter = new GrayscaleFilter();
    this.sepiaFilter = new SepiaFilter();
  }

  @Override
  public IPixel[][] getImage() {
    IPixel[][] copy = new IPixel[this.height][this.width];
    for (int i = 0; i < this.height; i += 1) {
      for (int j = 0; j < this.width; j += 1) {
        copy[i][j] = new PixelImpl(image[i][j].getRGB());
      }
    }
    return copy;
  }

  // Does not throw any exception from the blur filter because the images provided are valid
  @Override
  public void blur() {
    this.image = this.blurFilter.apply(this.getImage());
  }

  // Does not throw any exception from the sharpen filter because the images provided are valid
  @Override
  public void sharpen() {
    this.image = this.sharpenFilter.apply(this.getImage());
  }

  // Does not throw any exception from the grayscale filter because the images provided are valid
  @Override
  public void grayscale() {
    this.image = this.grayscaleFilter.apply(this.getImage());
  }

  // Does not throw any exception from the sepia filter because the images provided are valid
  @Override
  public void sepia() {
    this.image = this.sepiaFilter.apply(this.getImage());
  }
}

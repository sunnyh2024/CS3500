package filters;

import model.IPixel;
import model.PixelImpl;
import model.Utils;
import model.ImageUtil;

/**
 * Represents a type of filter where the color profile can be applied to the given image, changing
 * the colors of the pixels in the image.
 */
public abstract class AColorFilter implements IFilter {

  double[][] colorMatrix;

  /**
   * Constructs a color filter using the given color matrix.
   *
   * @param matrix 2D array of values used to manipulate individual pixels of an image
   * @throws IllegalArgumentException if the given matrix is invalid
   */
  public AColorFilter(double[][] matrix) throws IllegalArgumentException {
    Utils.requireNonNull(matrix);
    if (matrix.length != 3 || matrix[0].length != 3 || matrix[1].length != 3
        || matrix[2].length != 3) {
      throw new IllegalArgumentException("Given color matrix is invalid, must be a 3 x 3");
    }
    this.colorMatrix = matrix;
  }

  @Override
  public IPixel[][] apply(IPixel[][] image) throws IllegalArgumentException {
    ImageUtil.checkImageRect(image);
    int height = image.length;
    int width = image[0].length;
    IPixel[][] newImage = new IPixel[height][width];
    for (int i = 0; i < height; i += 1) {
      for (int j = 0; j < width; j += 1) {
        int[] newRGB = Utils.vectorMult(this.colorMatrix, image[i][j].getRGB());
        for (int k = 0; k < 3; k++) {
          if (newRGB[k] < 0) {
            newRGB[k] = 0;
          }
          if (newRGB[k] > 255) {
            newRGB[k] = 255;
          }
        }
        newImage[i][j] = new PixelImpl(newRGB);
      }
    }
    return newImage;
  }

}

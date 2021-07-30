package filters;

import model.IPixel;
import model.ImageUtil;
import model.PixelImpl;
import model.Utils;

/**
 * Abstract class representing a type of filter that edits the image's "resolution" (for now,
 * includes blurring and sharpening images).
 */
public abstract class AResFilter implements IFilter {

  private double[][] kernel;
  private final int kSize;

  /**
   * Constructs an abstract resolutionFilter.
   *
   * @param matrix the kernel that will be used during the filtering operation
   * @throws IllegalArgumentException if the given matrix is invalid
   */
  public AResFilter(double[][] matrix) {
    Utils.requireNonNull(matrix);
    int size = matrix[0].length;
    for (double[] row : matrix) {
      if (row.length != size) {
        throw new IllegalArgumentException("matrix must be rectangular.");
      }
    }
    this.kernel = matrix;
    this.kSize = matrix.length;
  }

  @Override
  public IPixel[][] apply(IPixel[][] image) throws IllegalArgumentException {
    ImageUtil.checkImageRect(image);
    int height = image.length;
    int width = image[0].length;
    IPixel[][] newImage = new IPixel[height][width];
    for (int i = 0; i < height; i += 1) {
      for (int j = 0; j < width; j += 1) {
        IPixel[][] pMatrix = Utils.getMatrix(image, kSize, i, j);
        int[][] rMatrix = new int[kSize][kSize];
        int[][] gMatrix = new int[kSize][kSize];
        int[][] bMatrix = new int[kSize][kSize];
        for (int x = 0; x < kSize; x += 1) {
          for (int y = 0; y < kSize; y += 1) {
            rMatrix[x][y] = pMatrix[x][y].getRGB()[0];
            gMatrix[x][y] = pMatrix[x][y].getRGB()[1];
            bMatrix[x][y] = pMatrix[x][y].getRGB()[2];
          }
        }
        int[] newRgb = {Utils.matrixDot(kernel, rMatrix), Utils.matrixDot(kernel, gMatrix),
            Utils.matrixDot(kernel, bMatrix)};
        for (int z = 0; z < 3; z += 1) {
          if (newRgb[z] > 255) {
            newRgb[z] = 255;
          }
          if (newRgb[z] < 0) {
            newRgb[z] = 0;
          }
        }
        newImage[i][j] = new PixelImpl(newRgb);
      }
    }
    return newImage;
  }
}

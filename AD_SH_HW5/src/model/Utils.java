package model;

/**
 * Represents a class that has utilities that are useful for this project, including matrix
 * computations and exception checking for image representations.
 */
public class Utils {

  /**
   * Ensures that the given Object is not null.
   *
   * @return the non-null object
   * @throws IllegalArgumentException if the given Object is null
   */
  public static Object requireNonNull(Object o) {
    if (o == null) {
      throw new IllegalArgumentException("Null object.");
    }
    return o;
  }



  /**
   * Calculates the product of the given vector with the given matrix.
   *
   * @param matrix a matrix of doubles
   * @param vector a vector of integers
   * @return the product of the matrix by the vector (in the order: matrix * vector)
   * @throws IllegalArgumentException if the matrix and/or vector is null or the dimensions don't
   *                                  match
   */
  public static int[] vectorMult(double[][] matrix, int[] vector) {
    Utils.requireNonNull(matrix);
    Utils.requireNonNull(vector);
    if (matrix.length == 0 || vector.length == 0) {
      throw new IllegalArgumentException("Invalid dimensions.");
    }
    int height = matrix.length;
    int width = matrix[0].length;
    int[] ans = new int[height];
    for (int i = 0; i < height; i += 1) {
      if (width != vector.length) {
        throw new IllegalArgumentException("Dimensions do not match.");
      }
      double sum = 0;
      for (int j = 0; j < width; j += 1) {
        sum += matrix[i][j] * vector[j];
      }
      ans[i] = (int) Math.round(sum);
    }
    return ans;
  }

  /**
   * Calculates the dot product of the given matrix m1 with the other given matrix m2.
   *
   * @param m1 a matrix of doubles
   * @param m2 a matrix of integers
   * @return the product of m1 by m2
   * @throws IllegalArgumentException if the dimensions do not match
   */
  public static int matrixDot(double[][] m1, int[][] m2) {
    Utils.requireNonNull(m1);
    Utils.requireNonNull(m2);
    if (m1.length == 0 || m2.length == 0 || m1[0].length == 0 || m2[0].length == 0) {
      throw new IllegalArgumentException("Invalid dimensions.");
    }
    if (m1.length != m2.length) {
      throw new IllegalArgumentException("Dimensions do not match.");
    }
    double sum = 0;
    int height = m1.length;
    int width = m1[0].length;
    for (int i = 0; i < height; i += 1) {
      if (m1[i].length != width || m1[i].length != m2[i].length) {
        throw new IllegalArgumentException("dimensions do not match.");
      }
      for (int j = 0; j < width; j += 1) {
        sum += m1[i][j] * m2[i][j];
      }
    }
    return (int) Math.round(sum);
  }

  /**
   * returns a matrix of the given size using the given double array of IPixels. The pixel at
   * position [x][y] will be the center pixel of the matrix. If any elements of the matrix are null,
   * it will be filled with a black pixel (with rgb values 0, 0, 0).
   *
   * @param image image used to create the matrix
   * @param mSize size of the matrix (must be odd so a center pixel exists)
   * @param x     position of the pixel in the outer array of the image
   * @param y     position of the pixel int he inner array of the image
   * @return a matrix of size mSize containing the pixel at position [x][y] and its neighbors
   * @throws IllegalArgumentException if image is null, given matrix size is not positive, or the
   *                                  given x and y are out of range
   */
  public static IPixel[][] getMatrix(IPixel[][] image, int mSize, int x, int y)
      throws IllegalArgumentException {
    Utils.requireNonNull(image);
    if (x < 0 || y < 0 || x >= image.length || y >= image[0].length || mSize < 1) {
      throw new IllegalArgumentException("invalid x y arguments.");
    }
    IPixel[][] ans = new IPixel[mSize][mSize];
    int span = (mSize - 1) / 2;
    for (int i = 0; i < mSize; i += 1) {
      for (int j = 0; j < mSize; j += 1) {
        try {
          ans[i][j] = image[x - span + i][y - span + j];
        } catch (Exception e) {
          ans[i][j] = new PixelImpl(0, 0, 0);
        }
      }
    }
    return ans;
  }
}

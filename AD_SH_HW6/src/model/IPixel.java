package model;

/**
 * Represents a pixel in an image.
 */
public interface IPixel {

  /**
   * Gets the rgb values of this pixel as 3-value array.
   *
   * @return a 3-value array representing the Red, Green, and Blue channels of this pixel,
   *         respectively
   */
  int[] getRGB();
}

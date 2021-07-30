package filters;

import model.IPixel;

/**
 * Represents a filter/edit that can be applied to an image to alter its appearance.
 */
public interface IFilter {

  /**
   * Applies this filter to the given image (represented as a double array of pixels).
   *
   * @param image the double array that will be edited
   * @return the new edited image
   * @throws IllegalArgumentException if the given image is not valid
   */
  IPixel[][] apply(IPixel[][] image);
}

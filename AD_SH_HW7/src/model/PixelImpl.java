package model;

import java.util.Arrays;
import java.util.Objects;


/**
 * Represents an implementation of IPixel where this pixel has red, green, blue values.
 */
public class PixelImpl implements IPixel {

  private final int red;
  private final int green;
  private final int blue;

  /**
   * Construct a PixelImpl from the given 3 red, green, and blue integers.
   *
   * @param red   The red color value for this pixel
   * @param green The green color value for this pixel
   * @param blue  The blue color value for this pixel
   * @throws IllegalArgumentException if at least one of the rgb values is not valid
   */
  public PixelImpl(int red, int green, int blue) throws IllegalArgumentException {
    if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
      throw new IllegalArgumentException(
          "At least one of the given color values is not between 0 and 255");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Construct a PixelImpl from the given array of rgb values.
   *
   * @param rgb The array of rgb values to be assigned to the fields of this PixelImpl
   * @throws IllegalArgumentException if the given array of rgb values is not valid
   */
  public PixelImpl(int[] rgb) throws IllegalArgumentException {
    Utils.requireNonNull(rgb);
    if (rgb.length != 3) {
      throw new IllegalArgumentException("Given array is not of size 3");
    }
    for (int i : rgb) {
      if (i < 0 || i > 255) {
        throw new IllegalArgumentException(
            "At least one of the given color values is not between 0 and 255");
      }
    }
    this.red = rgb[0];
    this.green = rgb[1];
    this.blue = rgb[2];
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof IPixel)) {
      return false;
    }
    return Arrays.equals(((IPixel) that).getRGB(), this.getRGB());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue);
  }

  @Override
  public int[] getRGB() {
    int[] ans = {this.red, this.green, this.blue};
    return ans;
  }
}

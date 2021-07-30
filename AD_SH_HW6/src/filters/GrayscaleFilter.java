package filters;

/**
 * Represents a color filter in which in turns an image into an image with only gray shades.
 */
public class GrayscaleFilter extends AColorFilter {

  /**
   * Constructs a GrayscaleFilter using AColorFilter with the GrayScale matrix.
   */
  public GrayscaleFilter() throws IllegalArgumentException {
    super(new double[][]{
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}
    });
  }
}

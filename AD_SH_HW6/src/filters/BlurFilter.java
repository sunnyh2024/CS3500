package filters;

/**
 * Represents a filter that will blur an image. Blurring mellows out the edges between high contrast
 * regions, making it harder to differentiate objects in the image.
 */
public class BlurFilter extends AResFilter {

  /**
   * Constructs a BlurFilter with the blur matrix.
   */
  public BlurFilter() {
    super(new double[][]{
        {.0625, .125, .0625},
        {.125, .25, .125},
        {.0625, .125, .0625}
    });
  }
}

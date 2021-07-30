package filters;

/**
 * Represents a color filter in which in turns a image into an image with a reddish brown tone.
 */
public class SepiaFilter extends AColorFilter {

  /**
   * Constructs a SepiaFilter using AColorFilter with the sepia color matrix.
   */
  public SepiaFilter() {
    super(new double[][]{
        {0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}
    });
  }
}

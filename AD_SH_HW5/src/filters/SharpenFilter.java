package filters;

/**
 * Represents a filter that sharpens an image. Sharpening will accentuate edges between high
 * contrast regions.
 */
public class SharpenFilter extends AResFilter {

  /**
   * Constructs a SharpenFilter.
   */
  public SharpenFilter() {
    super(new double[][]{
        {-.125, -.125, -.125, -.125, -.125},
        {-.125, .25, .25, .25, -.125},
        {-.125, .25, 1, .25, -.125},
        {-.125, .25, .25, .25, -.125},
        {-.125, -.125, -.125, -.125, -.125}});
  }
}

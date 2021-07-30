import filters.IFilter;
import filters.GrayscaleFilter;
import filters.SepiaFilter;
import model.IPixel;
import model.PixelImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Represents tests that are common among the classes that extend AColorFilter.
 */
public abstract class AColorFilterTest {

  IFilter filter;

  @Before
  public void init() {
    this.filter = this.aColorFilter();
  }

  // Test apply for a null image
  @Test(expected = IllegalArgumentException.class)
  public void testApplyNullImage() {
    this.filter.apply(null);
  }

  // Test apply for a non-rectangular image
  @Test(expected = IllegalArgumentException.class)
  public void testApplyNonRectImage() {
    IPixel[][] img = new PixelImpl[2][2];
    img[0][0] = new PixelImpl(0, 0, 0);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][0] = new PixelImpl(0, 0, 0);
    this.filter.apply(img);
  }

  // Test apply for an empty image(A 2D array with no pixels)
  @Test(expected = IllegalArgumentException.class)
  public void testApplyEmptyImage() {
    IPixel[][] img = new PixelImpl[0][0];
    this.filter.apply(img);
  }

  // Test apply for a black image(the result should be the sae among the filters)
  @Test
  public void testApplyBlackImage() {
    IPixel[][] img = new PixelImpl[2][2];
    img[0][0] = new PixelImpl(0, 0, 0);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][0] = new PixelImpl(0, 0, 0);
    img[1][1] = new PixelImpl(0, 0, 0);
    IPixel[][] img2 = new PixelImpl[2][2];
    img2[0][0] = new PixelImpl(0, 0, 0);
    img2[0][1] = new PixelImpl(0, 0, 0);
    img2[1][0] = new PixelImpl(0, 0, 0);
    img2[1][1] = new PixelImpl(0, 0, 0);
    assertArrayEquals(img2, this.filter.apply(img));

  }

  /**
   * Constructs an instance of a class that extends AColorFilter.
   *
   * @return a new instance of AColorFilter
   */

  protected abstract IFilter aColorFilter();

  /**
   * Concrete class for testing GrayscaleFilter implementation of AColorFilter.
   */

  public static final class GrayscaleFilterTest extends AColorFilterTest {

    @Override
    protected IFilter aColorFilter() {
      return new GrayscaleFilter();
    }
  }

  /**
   * Concrete class for testing GrayscaleFilter implementation of AColorFilter.
   */

  public static final class SepiaFilterTest extends AColorFilterTest {

    @Override
    protected IFilter aColorFilter() {
      return new SepiaFilter();
    }
  }

}

import filters.AResFilter;
import filters.BlurFilter;
import filters.SharpenFilter;
import model.IPixel;
import model.PixelImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Tests for the AResFilter class and all dependencies: tests to ensure that AResFilter and all
 * methods inside work correctly.
 */
public abstract class AResFilterTest {

  protected AResFilter filter;

  @Before
  public void init() {
    filter = createFilter();
  }

  // Tests the apply method with a null image
  @Test(expected = IllegalArgumentException.class)
  public void testApplyNullImage() {
    filter.apply(null);
  }

  // Tests the apply method with a non-rectangular image
  @Test(expected = IllegalArgumentException.class)
  public void testApplyNonRectImage() {
    IPixel[][] img = new IPixel[2][2];
    img[0][0] = new PixelImpl(0, 0, 0);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][0] = new PixelImpl(0, 0, 0);
    filter.apply(img);
  }

  // Tests the apply method with an empty image
  @Test(expected = IllegalArgumentException.class)
  public void testApplyEmptyImage() {
    IPixel[][] img = new IPixel[0][0];
    IPixel[][] result = new IPixel[0][0];
    assertArrayEquals(result, filter.apply(img));
  }

  // Tests the apply method with a black image
  @Test
  public void testApplyBlackImage() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel[][] img = new IPixel[][]{{b, b, b, b}, {b, b, b, b}, {b, b, b, b}, {b, b, b, b}};
    IPixel[][] result = new IPixel[][]{{b, b, b, b}, {b, b, b, b}, {b, b, b, b}, {b, b, b, b}};
    assertArrayEquals(result, filter.apply(img));
  }

  /**
   * Creates the filter that will be tested (either BlurFilter or SharpenFilter).
   */
  protected abstract AResFilter createFilter();

  /**
   * Tests for the BlurFilter class and all dependencies: tests to ensure that BlurFilter and all
   * methods inside work correctly.
   */
  public static final class BlurFilterTest extends AResFilterTest {

    @Override
    protected AResFilter createFilter() {
      return new BlurFilter();
    }
  }

  /**
   * Tests for the SharpenFilter class and all dependencies: tests to ensure that SharpenFilter and
   * all methods inside work correctly.
   */
  public static final class SepiaFilterTest extends AResFilterTest {

    @Override
    protected AResFilter createFilter() {
      return new SharpenFilter();
    }
  }
}

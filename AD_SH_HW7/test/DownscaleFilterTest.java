import filters.DownscaleFilter;
import filters.IFilter;
import java.awt.Color;
import model.IPixel;
import model.ImageUtil;
import model.PixelImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Tests for the DownscaleFilter class and all dependencies: tests to ensure that DownscaleFilter
 * and all methods inside work correctly.
 */
public class DownscaleFilterTest {

  IFilter filter;
  IPixel[][] img;

  @Before
  public void init() {
    filter = new DownscaleFilter(100, 100);
    img = ImageUtil.createChecker(Color.CYAN, Color.ORANGE, 10, 10, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyNullImage() {
    filter.apply(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyNonRectImage() {
    IPixel[][] img = new PixelImpl[2][2];
    img[0][0] = new PixelImpl(0, 0, 0);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][0] = new PixelImpl(0, 0, 0);
    filter.apply(img);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyEmptyImage() {
    IPixel[][] img = new PixelImpl[0][0];
    filter.apply(img);
  }

  @Test
  public void testApplyToSinglePixel() {
    filter = new DownscaleFilter(1, 1);
    IPixel[][] expected = new IPixel[][]{{new PixelImpl(0, 255, 255)}};
    assertArrayEquals(filter.apply(img), expected);
  }

  @Test
  public void testApplyNoChanges() {
    IPixel[][] expected = ImageUtil.createChecker(Color.CYAN, Color.ORANGE, 10, 10, 10);
    assertArrayEquals(filter.apply(img), expected);
  }

  @Test
  public void testApplySameProportions() {
    filter = new DownscaleFilter(50, 50);
    IPixel[][] expected = ImageUtil.createChecker(Color.CYAN, Color.ORANGE, 5, 10, 10);
    assertArrayEquals(filter.apply(img), expected);
  }

  @Test
  public void testApplyChangeProportions() {
    filter = new DownscaleFilter(4, 6);
    IPixel[][] start = ImageUtil.createChecker(Color.white, Color.black, 5, 2, 2);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel[][] expected = new IPixel[][]{
        {w, w, b, b},
        {w, w, b, b},
        {w, w, b, b},
        {b, b, w, w},
        {b, b, w, w},
        {b, b, w, w}
    };
    assertArrayEquals(filter.apply(start), expected);
  }

  @Test
  public void testApplyChangeWidthOnly() {
    filter = new DownscaleFilter(6, 12);
    IPixel[][] start = ImageUtil.createChecker(Color.white, Color.black, 4, 3, 3);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel[][] expected = new IPixel[][]{
        {w, w, b, b, w, w},
        {w, w, b, b, w, w},
        {w, w, b, b, w, w},
        {w, w, b, b, w, w},
        {b, b, w, w, b, b},
        {b, b, w, w, b, b},
        {b, b, w, w, b, b},
        {b, b, w, w, b, b},
        {w, w, b, b, w, w},
        {w, w, b, b, w, w},
        {w, w, b, b, w, w},
        {w, w, b, b, w, w}
    };
    assertArrayEquals(filter.apply(start), expected);
  }

  @Test
  public void testApplyChangeHeightOnly() {
    filter = new DownscaleFilter(12, 6);
    IPixel[][] start = ImageUtil.createChecker(Color.white, Color.black, 4, 3, 3);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel[][] expected = new IPixel[][]{
        {w, w, w, w, b, b, b, b, w, w, w, w},
        {w, w, w, w, b, b, b, b, w, w, w, w},
        {b, b, b, b, w, w, w, w, b, b, b, b},
        {b, b, b, b, w, w, w, w, b, b, b, b},
        {w, w, w, w, b, b, b, b, w, w, w, w},
        {w, w, w, w, b, b, b, b, w, w, w, w},
    };
    assertArrayEquals(filter.apply(start), expected);
  }

  @Test
  public void testApplyNonCheckerImage() {
    filter = new DownscaleFilter(8, 8);
    IPixel[][] start = ImageUtil.readImage("res/amongus.png");
    IPixel[][] result = filter.apply(start);
    IPixel p1 = new PixelImpl(255, 255, 255);
    IPixel p2 = new PixelImpl(41, 0, 0);
    IPixel p3 = new PixelImpl(255, 0, 0);
    IPixel p4 = new PixelImpl(230, 254, 254);
    IPixel p5 = new PixelImpl(106, 0, 32);
    IPixel p6 = new PixelImpl(23, 26, 98);
    IPixel p7 = new PixelImpl(106, 0, 33);
    IPixel p8 = new PixelImpl(1, 0, 1);
    IPixel p9 = new PixelImpl(254, 0, 0);
    IPixel p10 = new PixelImpl(40, 0, 0);
    IPixel[][] expected = new IPixel[][]{
        {p1, p1, p1, p1, p1, p1, p1, p1},
        {p1, p1, p1, p2, p1, p1, p1, p1},
        {p1, p1, p4, p6, p7, p1, p1, p1},
        {p1, p1, p8, p3, p5, p10, p1, p1},
        {p1, p1, p9, p9, p5, p2, p1, p1},
        {p1, p1, p3, p3, p5, p1, p1, p1},
        {p1, p1, p2, p2, p2, p1, p1, p1},
        {p1, p1, p1, p1, p1, p1, p1, p1}
    };
    assertArrayEquals(result, expected);
  }
}

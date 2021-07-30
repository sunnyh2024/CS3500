import filters.IFilter;
import filters.SharpenFilter;
import java.awt.Color;
import model.IPixel;
import model.ImageUtil;
import model.PixelImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Tests for the SharpenFilter class and all dependencies: tests to ensure that SharpenFilter and
 * all methods inside work correctly.
 */
public class SharpenFilterTest {

  private IFilter filter;

  @Before
  public void init() {
    filter = new SharpenFilter();
  }

  @Test
  public void testApplySinglePixel() {
    IPixel[][] img = new IPixel[][]{{new PixelImpl(200, 200, 200)}};
    IPixel[][] result = new IPixel[][]{{new PixelImpl(200, 200, 200)}};
    assertArrayEquals(result, filter.apply(img));
  }

  @Test
  public void testApplyWhiteImage() {
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel[][] img = new IPixel[][]{{w, w, w, w}, {w, w, w, w}, {w, w, w, w}, {w, w, w, w}};
    IPixel[][] result = new IPixel[][]{{w, w, w, w}, {w, w, w, w}, {w, w, w, w}, {w, w, w, w}};
    assertArrayEquals(result, filter.apply(img));
  }

  @Test
  public void testApplyCheckerBoardImage() {
    IPixel[][] img = ImageUtil
        .createChecker(new Color(128, 128, 0), new Color(0, 128, 128), 2, 3, 3);
    IPixel corner = new PixelImpl(208, 144, 0);
    IPixel col1Side = new PixelImpl(192, 192, 0);
    IPixel col2Side = new PixelImpl(0, 144, 176);
    IPixel col1SemiMid = new PixelImpl(208, 255, 64);
    IPixel col2SemiMid = new PixelImpl(32, 208, 176);
    IPixel mid = new PixelImpl(128, 128, 0);
    IPixel[][] result = new IPixel[][]{
        {corner, col1Side, col2Side, col2Side, col1Side, corner},
        {col1Side, col1SemiMid, col2SemiMid, col2SemiMid, col1SemiMid, col1Side},
        {col2Side, col2SemiMid, mid, mid, col2SemiMid, col2Side},
        {col2Side, col2SemiMid, mid, mid, col2SemiMid, col2Side},
        {col1Side, col1SemiMid, col2SemiMid, col2SemiMid, col1SemiMid, col1Side},
        {corner, col1Side, col2Side, col2Side, col1Side, corner}
    };
    assertArrayEquals(result, filter.apply(img));
  }

  @Test
  public void testApplyNonSquareImage() {
    IPixel[][] img = ImageUtil
        .createChecker(new Color(157, 0, 157), new Color(0, 157, 157), 2, 1, 3);
    IPixel c1outer = new PixelImpl(255, 0, 236);
    IPixel c2outer = new PixelImpl(255, 39, 255);
    IPixel mid = new PixelImpl(0, 255, 255);
    IPixel[][] result = new IPixel[][]{
        {c1outer, c1outer},
        {c2outer, c2outer},
        {mid, mid},
        {mid, mid},
        {c2outer, c2outer},
        {c1outer, c1outer},
    };

    assertArrayEquals(result, filter.apply(img));
  }

  @Test
  public void testApply5By5() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel g = new PixelImpl(137, 137, 137);
    IPixel[][] img = new IPixel[][]{
        {b, g, g, g, w},
        {g, b, g, w, g},
        {g, g, g, g, g},
        {g, w, g, b, g},
        {w, g, g, g, b}
    };
    IPixel one22 = new PixelImpl(122, 122, 122);
    IPixel one52 = new PixelImpl(152, 152, 152);
    IPixel one32 = new PixelImpl(132, 132, 132);
    IPixel two23 = new PixelImpl(223, 223, 223);
    IPixel one08 = new PixelImpl(108, 108, 108);
    IPixel[][] result = new IPixel[][]{
        {b, one22, one52, w, w},
        {one22, one08, two23, w, w},
        {one52, two23, one32, two23, one52},
        {w, w, two23, one08, one22},
        {w, w, one52, one22, b}
    };
    assertArrayEquals(result, filter.apply(img));
  }
}

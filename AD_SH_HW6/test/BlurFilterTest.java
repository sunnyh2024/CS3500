import filters.BlurFilter;
import filters.IFilter;
import java.awt.Color;
import model.IPixel;
import model.ImageUtil;
import model.PixelImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Tests for the BlurFilter class and all dependencies: tests to ensure that BlurFilter and all
 * methods inside work correctly.
 */
public final class BlurFilterTest {

  private IFilter filter;

  @Before
  public void init() {
    filter = new BlurFilter();
  }

  @Test
  public void testApplySinglePixel() {
    IPixel[][] img = new IPixel[][]{{new PixelImpl(200, 200, 200)}};
    IPixel[][] result = new IPixel[][]{{new PixelImpl(50, 50, 50)}};
    assertArrayEquals(result, filter.apply(img));
  }

  @Test
  public void testApplyWhiteImage() {
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel corner = new PixelImpl(143, 143, 143);
    IPixel side = new PixelImpl(191, 191, 191);
    IPixel[][] img = new IPixel[][]{{w, w, w, w}, {w, w, w, w}, {w, w, w, w}, {w, w, w, w}};
    IPixel[][] result = new IPixel[][]{
        {corner, side, side, corner},
        {side, w, w, side},
        {side, w, w, side},
        {corner, side, side, corner}};
    assertArrayEquals(result, filter.apply(img));
  }

  @Test
  public void testApplyCheckerBoardImage() {
    IPixel[][] img = ImageUtil
        .createChecker(new Color(128, 128, 0), new Color(0, 128, 128), 2, 3, 3);
    IPixel corner = new PixelImpl(72, 72, 0);
    IPixel col1Side = new PixelImpl(72, 96, 24);
    IPixel col2Side = new PixelImpl(24, 96, 72);
    IPixel col1Mid = new PixelImpl(80, 128, 48);
    IPixel col2Mid = new PixelImpl(48, 128, 80);
    IPixel[][] result = new IPixel[][]{
        {corner, col1Side, col2Side, col2Side, col1Side, corner},
        {col1Side, col1Mid, col2Mid, col2Mid, col1Mid, col1Side},
        {col2Side, col2Mid, col1Mid, col1Mid, col2Mid, col2Side},
        {col2Side, col2Mid, col1Mid, col1Mid, col2Mid, col2Side},
        {col1Side, col1Mid, col2Mid, col2Mid, col1Mid, col1Side},
        {corner, col1Side, col2Side, col2Side, col1Side, corner}
    };
    assertArrayEquals(result, filter.apply(img));
  }

  @Test
  public void testApplyNonSquareImage() {
    IPixel[][] img = ImageUtil
        .createChecker(new Color(160, 0, 160), new Color(0, 160, 160), 2, 1, 3);
    IPixel outer = new PixelImpl(90, 0, 90);
    IPixel mid = new PixelImpl(90, 30, 120);
    IPixel inner = new PixelImpl(30, 90, 120);
    IPixel[][] result = new IPixel[][]{
        {outer, outer},
        {mid, mid},
        {inner, inner},
        {inner, inner},
        {mid, mid},
        {outer, outer}
    };
    assertArrayEquals(result, filter.apply(img));
  }

  @Test
  public void testApply3By3() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel g = new PixelImpl(137, 137, 137);
    IPixel[][] img = new IPixel[][]{
        {b, g, w},
        {g, g, g},
        {w, g, b},
    };
    IPixel hundred = new PixelImpl(100, 100, 100);
    IPixel one07 = new PixelImpl(107, 107, 107);
    IPixel four3 = new PixelImpl(43, 43, 43);
    IPixel[][] result = new IPixel[][]{
        {new PixelImpl(43, 43, 43), hundred, new PixelImpl(107, 107, 107)},
        {hundred, new PixelImpl(135, 135, 135), hundred},
        {new PixelImpl(107, 107, 107), hundred, new PixelImpl(43, 43, 43)},
    };
    assertArrayEquals(result, filter.apply(img));
  }
}

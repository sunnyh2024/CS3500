import filters.IFilter;
import filters.MosaicFilter;
import java.awt.Color;
import java.util.Arrays;
import java.util.Random;
import model.IPixel;
import model.ImageUtil;
import model.PixelImpl;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests for the MosaicFilter class and all dependencies: tests to ensure that BlurFilter and all
 * methods inside work correctly.
 */
public class MosaicFilterTest {

  IFilter filter;

  @Test (expected = IllegalArgumentException.class)
  public void testFilterConstructorWithRandomInvalidNumSeeds() {
    this.filter = new MosaicFilter(0, new Random());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testFilterConstructorWithoutRandomInvalidNumSeeds() {
    this.filter = new MosaicFilter(0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testFilterConstructorWithRandomNullRandom() {
    this.filter = new MosaicFilter(0, null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testApplyNullImage() {
    this.filter = new MosaicFilter(100, new Random());
    this.filter.apply(null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testApplyNonRectImage() {
    this.filter = new MosaicFilter(100, new Random());
    IPixel[][] img = new PixelImpl[2][2];
    img[0][0] = new PixelImpl(0, 0, 0);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][0] = new PixelImpl(0, 0, 0);
    this.filter.apply(img);
  }

  @Test
  public void testApplyOneSeedSinglePixel() {
    this.filter = new MosaicFilter(1, new Random(1));
    IPixel[][] img = new PixelImpl[1][1];
    img[0][0] = new PixelImpl(0, 0, 0);
    assertArrayEquals(img, this.filter.apply(img));
  }

  @Test
  public void testApplyOneSeedMultiplePixels() {
    this.filter = new MosaicFilter(1, new Random(1));
    IPixel[][] img = ImageUtil.createChecker(Color.RED, Color.BLUE, 1, 4, 4);
    IPixel p = new PixelImpl(127, 0 ,127);
    IPixel[][] result = new IPixel[][] {
        {p, p, p, p},
        {p, p, p, p},
        {p, p, p, p},
        {p, p, p, p}
    };
    assertArrayEquals(result, this.filter.apply(img));
  }

  @Test
  public void testApplyMutipleSeedsCheckers() {
    this.filter = new MosaicFilter(5, new Random(5));

    IPixel[][] img = ImageUtil.createChecker(Color.RED, Color.BLUE, 1, 8, 8);
    IPixel p1 = new PixelImpl(127, 0, 127);
    IPixel p2 = new PixelImpl(106, 0, 148);
    IPixel p3 = new PixelImpl(117, 0, 137);
    IPixel p4 = new PixelImpl(145, 0, 109);
    IPixel p5 = new PixelImpl(140, 0, 114);
    IPixel[][] result2 = this.filter.apply(img);
    IPixel[][] result = new IPixel[][] {
        {p1, p1, p1, p1, p1, p1, p1, p1},
        {p2, p2, p1, p1, p1, p1, p3, p4},
        {p5, p2, p2, p2, p2, p3, p3, p4},
        {p5, p5, p2, p2, p2, p3, p3, p4},
        {p5, p5, p5, p2, p2, p3, p3, p4},
        {p5, p5, p5, p5, p2, p3, p3, p4},
        {p5, p5, p5, p5, p5, p3, p3, p4},
        {p5, p5, p5, p5, p5, p3, p3, p4}
    };
    assertArrayEquals(result, result2);
  }

  @Test
  public void testMosaicRandom() {
    this.filter = new MosaicFilter(5);
    IPixel[][] img = ImageUtil.createChecker(Color.RED, Color.BLUE, 1, 8, 8);
    IPixel[][] imgResult = this.filter.apply(img);
    IPixel[][] imgResult2 = this.filter.apply(img);
    assertFalse(Arrays.equals(imgResult, imgResult2));
  }
}

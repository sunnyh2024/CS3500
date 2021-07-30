import model.IPixel;
import model.PixelImpl;
import model.Utils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

/**
 * Tests for the BlurFilter class and all dependencies: tests to ensure that BlurFilter and all
 * methods inside work correctly.
 */
public class UtilsTest {

  @Test(expected = IllegalArgumentException.class)
  public void testRequireNonNullNull() {
    Utils.requireNonNull(null);
  }

  @Test
  public void testRequireNonNullValid() {
    boolean nonNull = true;
    try {
      Utils.requireNonNull(new PixelImpl(125, 126, 127));
    } catch (IllegalArgumentException iae) {
      nonNull = false;
    }
    assertEquals(true, nonNull);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testVectorMultNullMatrix() {
    Utils.vectorMult(null, new int[]{12, 24, 36});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVectorMultNullVector() {
    Utils.vectorMult(new double[][]{{1, 2, 3}, {2, 4, 6}, {3, 6, 9}}, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVectorMultEmptyMatrix() {
    Utils.vectorMult(new double[][]{}, new int[]{1, 2, 3});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVectorMultEmptyMatrixRows() {
    Utils.vectorMult(new double[][]{{}, {}}, new int[]{1, 2, 3});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVectorMultMismatchedDimen() {
    Utils.vectorMult(new double[][]{{1, 2, 3}, {2, 4, 6}, {3, 6, 9}}, new int[]{12, 24});
  }

  @Test
  public void testVectorMultValid() {
    int[] ans = Utils
        .vectorMult(new double[][]{{1, 2, 3}, {2, 4, 6}, {3, 6, 9}}, new int[]{12, 24, 36});
    assertArrayEquals(new int[]{168, 336, 504}, ans);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMatrixDotNullMOne() {
    Utils.matrixDot(null, new int[][]{{1, 3, 5}, {2, 3, 4}});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMatrixDotNullMTwo() {
    Utils.matrixDot(new double[][]{{1, 3, 5}, {2, 3, 4}}, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMatrixDotEmptyMatrix() {
    Utils.matrixDot(new double[][]{},
        new int[][]{{1, 2, 3}});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMatrixDotEmptyMatrixRows() {
    Utils.matrixDot(new double[][]{{1, 2}, {2, 3}},
        new int[][]{{}, {}});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMatrixDotMismatchedDimen() {
    Utils.matrixDot(new double[][]{{1, 3, 5}, {2, 3, 4}},
        new int[][]{{1, 3, 5}, {2, 3, 4}, {1, 2, 3}});
  }

  @Test
  public void testMatrixDotValid() {
    int ans = Utils.matrixDot(new double[][]{{.1, .3, .5}, {.2, .3, .4}, {.3, .5, .7}},
        new int[][]{{10, 11, 12}, {20, 30, 40}, {15, 25, 35}});
    assertEquals(81, ans);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetMatrixNullImage() {
    Utils.getMatrix(null, 5, 644, 700);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetMatrixBadSize() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel[][] img = new IPixel[][]{{b, w, b, w, b}, {w, b, w, b, w}, {b, w, b, w, b},
        {w, b, w, b, w}, {b, w, b, w, b}};
    Utils.getMatrix(img, -1, 3, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetMatrixBadX() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel[][] img = new IPixel[][]{{b, w, b, w, b}, {w, b, w, b, w}, {b, w, b, w, b},
        {w, b, w, b, w}, {b, w, b, w, b}};
    Utils.getMatrix(img, 3, 2560, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetMatrixBadY() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel[][] img = new IPixel[][]{{b, w, b, w, b}, {w, b, w, b, w}, {b, w, b, w, b},
        {w, b, w, b, w}, {b, w, b, w, b}};
    Utils.getMatrix(img, 3, 2, 3932);
  }

  @Test
  public void testGetMatrixValid() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel[][] img = new IPixel[][]{{b, w, b, w, b}, {w, b, w, b, w}, {b, w, b, w, b},
        {w, b, w, b, w}, {b, w, b, w, b}};
    IPixel[][] ans = new IPixel[][]{{b, w, b}, {w, b, w}, {b, w, b}};
    assertArrayEquals(ans, Utils.getMatrix(img, 3, 1, 1));
  }

  @Test
  public void testGetMatrixCornerPixel() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel[][] img = new IPixel[][]{{b, w, b, w, b}, {w, b, w, b, w}, {b, w, b, w, b},
        {w, b, w, b, w}, {b, w, b, w, b}};
    IPixel[][] ans = new IPixel[][]{{b, b, b}, {b, b, w}, {b, w, b}};
    assertArrayEquals(ans, Utils.getMatrix(img, 3, 0, 0));
  }

  @Test
  public void testGetMatrixSidePixel() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel[][] img = new IPixel[][]{{b, w, b, w, b}, {w, b, w, b, w}, {b, w, b, w, b},
        {w, b, w, b, w}, {b, w, b, w, b}};
    IPixel[][] ans = new IPixel[][]{{b, w, b}, {b, b, w}, {b, w, b}};
    assertArrayEquals(ans, Utils.getMatrix(img, 3, 2, 0));
  }
}

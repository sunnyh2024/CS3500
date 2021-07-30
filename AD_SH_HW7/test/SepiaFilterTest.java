import static org.junit.Assert.assertArrayEquals;

import filters.IFilter;
import filters.SepiaFilter;
import java.awt.Color;
import model.IPixel;
import model.ImageUtil;
import model.PixelImpl;
import org.junit.Test;

/**
 * Represents tests that are specific for SepiaFilter.
 */

public class SepiaFilterTest {

  IFilter filter = new SepiaFilter();

  // Tests the apply method for SepiaFilter with a 1 x 1 with a white pixel. Also tests that the
  // resulting pixel caps its rgb value at 255
  @Test
  public void testApplyWhitePixel() {
    IPixel[][] img = new PixelImpl[1][1];
    img[0][0] = new PixelImpl(255, 255, 255);
    IPixel[][] img2 = new PixelImpl[1][1];
    img2[0][0] = new PixelImpl(255, 255, 239);
    assertArrayEquals(img2, this.filter.apply(img));
  }

  // Tests the apply method for SepiaFilter with a 1 x 1 with a blue pixel
  @Test
  public void testApplyBluePixel() {
    IPixel[][] img = new PixelImpl[1][1];
    img[0][0] = new PixelImpl(0, 0, 255);
    IPixel[][] img2 = new PixelImpl[1][1];
    img2[0][0] = new PixelImpl(48, 43, 33);
    assertArrayEquals(img2, this.filter.apply(img));
  }

  // Tests the apply method for SepiaFilter with a 1 x 1 with a purple pixel
  @Test
  public void testApplyPurplePixel() {
    IPixel[][] img = new PixelImpl[1][1];
    img[0][0] = new PixelImpl(200, 0, 200);
    IPixel[][] img2 = new PixelImpl[1][1];
    img2[0][0] = new PixelImpl(116, 103, 81);
    assertArrayEquals(img2, this.filter.apply(img));
  }

  // Tests the apply method for SepiaFilter with a 2 x 2 with different colors in each pixel
  @Test
  public void testApplyTwoByTwo() {
    IPixel[][] img = new PixelImpl[2][2];
    img[0][0] = new PixelImpl(100, 30, 60);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][0] = new PixelImpl(90, 60, 30);
    img[1][1] = new PixelImpl(250, 80, 60);
    IPixel[][] img2 = new PixelImpl[2][2];
    img2[0][0] = new PixelImpl(74, 66, 51);
    img2[0][1] = new PixelImpl(0, 0, 0);
    img2[1][0] = new PixelImpl(87, 78, 60);
    img2[1][1] = new PixelImpl(171, 152, 119);
    assertArrayEquals(img2, this.filter.apply(img));
  }

  // Tests the apply method for SepiaFilter with a 2 x 3 with different colors in each pixel
  // to show that the method works for rectangles that are not squares
  @Test
  public void testApplyTwoByThree() {
    IPixel[][] img = new PixelImpl[3][2];
    img[0][0] = new PixelImpl(100, 30, 60);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][0] = new PixelImpl(90, 60, 30);
    img[1][1] = new PixelImpl(250, 80, 60);
    img[2][0] = new PixelImpl(255, 255, 255);
    img[2][1] = new PixelImpl(0, 0, 1);
    IPixel[][] img2 = new PixelImpl[3][2];
    img2[0][0] = new PixelImpl(74, 66, 51);
    img2[0][1] = new PixelImpl(0, 0, 0);
    img2[1][0] = new PixelImpl(87, 78, 60);
    img2[1][1] = new PixelImpl(171, 152, 119);
    img2[2][0] = new PixelImpl(255, 255, 239);
    img2[2][1] = new PixelImpl(0, 0, 0);
    assertArrayEquals(img2, this.filter.apply(img));
  }

  // Tests the apply method for a SepiaFilter with a black and white checkerboard
  @Test
  public void testApplyBWCheckerBoard() {
    IPixel[][] img = ImageUtil.createChecker(Color.BLACK, Color.WHITE, 100, 10, 10);
    IPixel[][] img2 = ImageUtil.createChecker(Color.BLACK, new Color(255, 255, 239), 100, 10, 10);
    assertArrayEquals(img2, this.filter.apply(img));
  }

  // Tests the apply method for a SepiaFilter with a cyan and orange checkerboard
  @Test
  public void testApplyCOCheckerBoard() {
    IPixel[][] img = ImageUtil.createChecker(Color.CYAN, Color.ORANGE, 70, 15, 10);
    IPixel[][] img2 = ImageUtil.createChecker(new Color(244, 218, 170), new Color(254, 226, 176),
        70, 15, 10);
    assertArrayEquals(img2, this.filter.apply(img));
  }
}

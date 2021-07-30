import java.awt.Color;
import model.IPixel;
import model.ImageEditorModel;
import model.ImageUtil;
import model.PixelImpl;
import model.SimpleImageEditorModel;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the ImageUtil class and all dependencies: tests to ensure that ImageUtil and all
 * methods inside work correctly.
 */
public class ImageUtilsTest {

  // Tests the checkImageRect method with null array
  @Test(expected = IllegalArgumentException.class)
  public void testCheckImageNullRect() {
    ImageUtil.checkImageRect(null);
  }

  // Tests the checkImageRect method with a non-rectangular image(not all pixels are filled)
  @Test(expected = IllegalArgumentException.class)
  public void testCheckImageNotRect() {
    IPixel[][] img = new IPixel[2][2];
    img[0][0] = new PixelImpl(0, 0, 0);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][0] = new PixelImpl(0, 0, 0);
    ImageUtil.checkImageRect(img);
  }

  // Tests the checkImageRect method with a non-rectangular image(cascading array)
  @Test(expected = IllegalArgumentException.class)
  public void testCheckImageNotRectBadArray() {
    IPixel p = new PixelImpl(0, 0, 0);
    IPixel[][] img = new IPixel[][]{{p}, {p, p}, {p, p, p}};
    ImageUtil.checkImageRect(img);
  }

  // Tests the checkImageRect method with an empty image
  @Test(expected = IllegalArgumentException.class)
  public void testCheckImageRectEmptyImage() {
    IPixel p = new PixelImpl(0, 0, 0);
    IPixel[][] img = new IPixel[0][0];
    ImageUtil.checkImageRect(img);
  }

  // Tests the checkImageRect method with a valid rectangular array
  @Test
  public void testCheckImageRectValid() {
    IPixel[][] img = new IPixel[2][2];
    img[0][0] = new PixelImpl(0, 0, 0);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][0] = new PixelImpl(0, 0, 0);
    img[1][1] = new PixelImpl(0, 0, 0);
    boolean checkImageRect = true;
    try {
      ImageUtil.checkImageRect(img);
    } catch (IllegalArgumentException iae) {
      checkImageRect = false;
    }
    assertEquals(true, checkImageRect);
  }

  // Tests the checkImageRect method with a checkerboard
  @Test
  public void testCheckImageRectCheckerboardValid() {
    IPixel[][] img = ImageUtil
        .createChecker(new Color(0, 0, 204), new Color(255, 0, 127), 20, 10, 10);
    ImageUtil.checkImageRect(img);
    boolean checkImageRect = true;
    try {
      ImageUtil.checkImageRect(img);
    } catch (IllegalArgumentException iae) {
      checkImageRect = false;
    }
    assertEquals(true, checkImageRect);
  }

  // Tests the readPPM method with a null file name
  @Test(expected = IllegalArgumentException.class)
  public void testReadPPMNullFileName() {
    ImageUtil.readPPM(null);
  }

  // Tests the readPPM method with a non-existent file name
  @Test(expected = IllegalArgumentException.class)
  public void testReadPPMBadFileName() {
    ImageUtil.readPPM("res/nonExistentFile");
  }

  // Tests the readPPM method with a ppm file that is not P3
  @Test(expected = IllegalArgumentException.class)
  public void testReadPPMNotP3() {
    ImageUtil.readPPM("res/P6File.ppm");
  }

  // Tests the readPPM method with a valid ppm file
  @Test
  public void testReadPPMValid() {
    IPixel[][] write = ImageUtil.createChecker(Color.white, Color.black, 1, 3, 3);
    ImageUtil.writePPM(write, "res/readPPMTest.ppm");
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel[][] img = new IPixel[][]{
        {w, b, w},
        {b, w, b},
        {w, b, w}
    };
    assertArrayEquals(img, ImageUtil.readPPM("res/readPPMTest.ppm"));
  }

  // Tests the writePPm method with a null image
  @Test(expected = IllegalArgumentException.class)
  public void testWritePPMNullImage() {
    ImageUtil.writePPM(null, "res/testFile.ppm");
  }

  // Tests the writePPm method with a null destination
  @Test(expected = IllegalArgumentException.class)
  public void testWritePPMNullDest() {
    IPixel[][] img = ImageUtil.createChecker(Color.black, Color.white, 50, 5, 5);
    ImageUtil.writePPM(img, null);
  }

  // Tests the writePPm method with a valid destination and image
  @Test
  public void testWritePPMValid() {
    IPixel r = new PixelImpl(200, 0, 0);
    IPixel g = new PixelImpl(0, 200, 0);
    IPixel[][] img = new IPixel[][]{
        {g, r, g, r},
        {g, r, g, r},
        {r, g, r, g},
        {r, g, r, g}
    };
    ImageUtil.writePPM(img, "res/writePPMTest.ppm");
    assertArrayEquals(ImageUtil.readPPM("res/writePPMTest.ppm"), img);
  }

  // Testing that reading and writing a file multiple times does not change the file.
  @Test
  public void testReadWritePPMRepeat() {
    IPixel r = new PixelImpl(200, 0, 0);
    IPixel g = new PixelImpl(0, 200, 0);
    IPixel[][] img = new IPixel[][]{
        {g, r, g, r},
        {g, r, g, r},
        {r, g, r, g},
        {r, g, r, g}
    };
    ImageUtil.writePPM(img, "res/writePPMTest.ppm");
    assertArrayEquals(ImageUtil.readPPM("res/writePPMTest.ppm"), img);
    IPixel[][] newImg = ImageUtil.readPPM("res/writePPMTest.ppm");
    ImageUtil.writePPM(newImg, "res/writePPMTest.ppm");
    IPixel[][] finImg = ImageUtil.readPPM("res/writePPMTest.ppm");
    assertArrayEquals(finImg, img);
    assertArrayEquals(finImg, newImg);
  }

  // Tests reading a file, applying a filter to the image, writing to another file, and reading it
  // again to make sure it is expected
  @Test
  public void testReadWritePPMFilter() {
    IPixel r = new PixelImpl(200, 0, 0);
    IPixel g = new PixelImpl(0, 200, 0);
    IPixel[][] img = new IPixel[][]{
        {g, r, g, r},
        {g, r, g, r},
        {r, g, r, g},
        {r, g, r, g}
    };
    IPixel p1 = new PixelImpl(43, 43, 43);
    IPixel p2 = new PixelImpl(143, 143, 143);
    IPixel[][] img2 = new IPixel[][]{
        {p2, p1, p2, p1},
        {p2, p1, p2, p1},
        {p1, p2, p1, p2},
        {p1, p2, p1, p2}
    };

    ImageUtil.writePPM(img, "res/writePPMTest.ppm");
    ImageEditorModel model = new SimpleImageEditorModel(ImageUtil.readPPM("res/writePPMTest.ppm"));
    model.grayscale();
    ImageUtil.writePPM(model.getImage(), "res/writePPMTest.ppm");
    assertArrayEquals(img2, ImageUtil.readPPM("res/writePPMTest.ppm"));

  }

  // Testing createChecker throws exception when given a null color.
  @Test(expected = IllegalArgumentException.class)
  public void testCreateCheckerNullCol1() {
    ImageUtil.createChecker(null, Color.WHITE, 3, 5, 6);
  }

  // Testing createChecker throws exception when given a null color.
  @Test(expected = IllegalArgumentException.class)
  public void testCreateCheckerNullCol2() {
    ImageUtil.createChecker(Color.BLACK, null, 3, 5, 6);
  }

  // Testing createChecker throws exception when given invalid dimensions.
  @Test(expected = IllegalArgumentException.class)
  public void testCreateCheckerBadDimensions() {
    ImageUtil.createChecker(Color.BLACK, Color.WHITE, -7, 0, -1);
  }

  // Tests createChecker creating a single-pixel image.
  @Test
  public void testCreateCheckSinglePixel() {
    IPixel[][] img = new IPixel[][]{{new PixelImpl(200, 0, 128)}};
    assertArrayEquals(ImageUtil.createChecker(new Color(200, 0, 128), Color.black, 1, 1, 1), img);
  }

  // Tests createChecker creating image with even dimensions for width and height.
  @Test
  public void testCreateCheckerEvenDimension() {
    IPixel b = new PixelImpl(0, 0, 200);
    IPixel r = new PixelImpl(200, 0, 0);
    IPixel[][] img = new IPixel[][]{
        {r, b, r, b, r, b},
        {b, r, b, r, b, r},
        {r, b, r, b, r, b},
        {b, r, b, r, b, r},
        {r, b, r, b, r, b},
        {b, r, b, r, b, r}
    };
    assertArrayEquals(ImageUtil.createChecker(new Color(200, 0, 0), new Color(0, 0, 200), 1, 6, 6),
        img);
  }

  // Tests createChecker creating image with odd dimensions for width and height.
  @Test
  public void testCreateCheckerOddDimension() {
    IPixel b = new PixelImpl(0, 0, 200);
    IPixel r = new PixelImpl(200, 0, 0);
    IPixel[][] img = new IPixel[][]{
        {r, b, r, b, r},
        {b, r, b, r, b},
        {r, b, r, b, r},
        {b, r, b, r, b},
        {r, b, r, b, r}
    };
    assertArrayEquals(ImageUtil.createChecker(new Color(200, 0, 0), new Color(0, 0, 200), 1, 5, 5),
        img);
  }

  // Tests createChecker with non-square dimensions for width and height.
  @Test
  public void testCreateCheckerNonSquare() {
    IPixel b = new PixelImpl(0, 0, 200);
    IPixel r = new PixelImpl(200, 0, 0);
    IPixel[][] img = new IPixel[][]{
        {r, b, r, b, r},
        {b, r, b, r, b},
        {r, b, r, b, r},
        {b, r, b, r, b},
        {r, b, r, b, r},
        {b, r, b, r, b},
        {r, b, r, b, r},
        {b, r, b, r, b},
        {r, b, r, b, r},
        {b, r, b, r, b}
    };
    assertArrayEquals(ImageUtil.createChecker(new Color(200, 0, 0), new Color(0, 0, 200), 1, 5, 10),
        img);
  }

  // Tests createChecker with a box size greater than 1.
  @Test
  public void testCreateCheckerLargerSize() {
    IPixel b = new PixelImpl(0, 0, 200);
    IPixel r = new PixelImpl(200, 0, 0);
    IPixel[][] img = new IPixel[][]{
        {b, b, b, r, r, r, b, b, b},
        {b, b, b, r, r, r, b, b, b},
        {b, b, b, r, r, r, b, b, b},
        {r, r, r, b, b, b, r, r, r},
        {r, r, r, b, b, b, r, r, r},
        {r, r, r, b, b, b, r, r, r},
        {b, b, b, r, r, r, b, b, b},
        {b, b, b, r, r, r, b, b, b},
        {b, b, b, r, r, r, b, b, b}
    };
    assertArrayEquals(ImageUtil.createChecker(new Color(0, 0, 200), new Color(200, 0, 0), 3, 3, 3),
        img);
  }
}


import java.awt.Color;
import model.IPixel;
import model.ImageUtil;
import model.PixelImpl;
import model.SimpleImageEditorModel;
import org.junit.Test;
import model.ImageEditorModel;

import static org.junit.Assert.assertArrayEquals;

/**
 * Tests for the SimpleImageEditorModel class and all dependencies: tests to ensure that
 * SimpleImageEditorModel and all methods inside work correctly.
 */
public class SimpleImageEditorModelTest {

  // Tests the constructor when given a null image
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullImage() {
    new SimpleImageEditorModel(null);
  }

  // Tests the constructor when given a non-rectangular image
  @Test(expected = IllegalArgumentException.class)
  public void testConstrutorNonRectangularImage() {
    IPixel[][] img = new PixelImpl[2][2];
    img[0][0] = new PixelImpl(0, 0, 0);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][1] = new PixelImpl(0, 0, 0);
    new SimpleImageEditorModel(img);
  }

  // Tests the getImage method as well as make sure that the image provided to the constructor is
  // assigned properly
  @Test
  public void testGetImage() {
    IPixel[][] img = new PixelImpl[2][2];
    img[0][0] = new PixelImpl(100, 30, 60);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][0] = new PixelImpl(90, 60, 30);
    img[1][1] = new PixelImpl(250, 80, 60);
    ImageEditorModel model = new SimpleImageEditorModel(img);
    assertArrayEquals(img, model.getImage());
  }

  // Tests blurring the model's image and make sure it results in a proper blurred image.
  // (Specific tests relating to blurring images is in the BlurFilterTest class)
  @Test
  public void testBlur() {
    IPixel[][] img = ImageUtil
        .createChecker(new Color(128, 128, 0), new Color(0, 128, 128), 2, 3, 3);
    ImageEditorModel model = new SimpleImageEditorModel(img);
    model.blur();
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
    assertArrayEquals(result, model.getImage());
  }

  // Tests sharpening the model's image and make sure it results in a proper sharpened image.
  // (Specific tests relating to sharpening images is in the SharpenFilterTest class)
  @Test
  public void testSharpen() {
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
    ImageEditorModel model = new SimpleImageEditorModel(img);
    model.sharpen();
    assertArrayEquals(result, model.getImage());
  }

  // Tests applying a grayscale filter to the model's image and make sure
  // it results in a proper monochrome image.
  // (Specific tests relating to applying a grayscale filter to images is
  // in the GrayScaleFilter class)
  @Test
  public void testGrayScale() {
    IPixel[][] img = new PixelImpl[2][2];
    img[0][0] = new PixelImpl(100, 30, 60);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][0] = new PixelImpl(90, 60, 30);
    img[1][1] = new PixelImpl(250, 80, 60);
    ImageEditorModel model = new SimpleImageEditorModel(img);
    IPixel[][] img2 = new PixelImpl[2][2];
    img2[0][0] = new PixelImpl(47, 47, 47);
    img2[0][1] = new PixelImpl(0, 0, 0);
    img2[1][0] = new PixelImpl(64, 64, 64);
    img2[1][1] = new PixelImpl(115, 115, 115);
    model.grayscale();
    assertArrayEquals(img2, model.getImage());

  }

  // Tests applying a sepia filter to the model's image and make sure
  // it results in a proper sepia image.
  // (Specific tests relating to applying a sepia filter to images is
  // in the SepiaFilter class)
  @Test
  public void testSepia() {
    IPixel[][] img = new PixelImpl[2][2];
    img[0][0] = new PixelImpl(100, 30, 60);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][0] = new PixelImpl(90, 60, 30);
    img[1][1] = new PixelImpl(250, 80, 60);
    ImageEditorModel model = new SimpleImageEditorModel(img);
    IPixel[][] img2 = new PixelImpl[2][2];
    img2[0][0] = new PixelImpl(74, 66, 51);
    img2[0][1] = new PixelImpl(0, 0, 0);
    img2[1][0] = new PixelImpl(87, 78, 60);
    img2[1][1] = new PixelImpl(171, 152, 119);
    model.sepia();
    assertArrayEquals(img2, model.getImage());
  }

  // Tests applying the blur filter twice
  @Test
  public void testBlurTwice() {
    IPixel[][] img = ImageUtil
        .createChecker(new Color(128, 128, 0), new Color(0, 128, 128), 2, 3, 3);
    ImageEditorModel model = new SimpleImageEditorModel(img);
    model.blur();
    model.blur();
    IPixel p1 = new PixelImpl(41, 50, 9);
    IPixel p2 = new PixelImpl(48, 75, 28);
    IPixel p3 = new PixelImpl(32, 80, 48);
    IPixel p4 = new PixelImpl(63, 113, 50);
    IPixel p5 = new PixelImpl(55, 120, 65);
    IPixel p6 = new PixelImpl(68, 128, 60);
    IPixel[][] result = new IPixel[][]{
        {p1, p2, p3, p3, p2, p1},
        {p2, p4, p5, p5, p4, p2},
        {p3, p5, p6, p6, p5, p3},
        {p3, p5, p6, p6, p5, p3},
        {p2, p4, p5, p5, p4, p2},
        {p1, p2, p3, p3, p2, p1}
    };
    assertArrayEquals(result, model.getImage());
  }

  // Tests applying the sharpen filter twice
  @Test
  public void testSharpenTwice() {
    IPixel[][] img = ImageUtil
        .createChecker(new Color(128, 128, 0), new Color(0, 128, 128), 2, 3, 3);
    ImageEditorModel model = new SimpleImageEditorModel(img);
    model.sharpen();
    model.sharpen();
    IPixel p1 = new PixelImpl(255, 200, 0);
    IPixel p2 = new PixelImpl(255, 255, 16);
    IPixel p3 = new PixelImpl(0, 196, 250);
    IPixel p4 = new PixelImpl(255, 255, 152);
    IPixel p5 = new PixelImpl(68, 255, 250);
    IPixel p6 = new PixelImpl(92, 110, 0);
    IPixel[][] result = new IPixel[][]{
        {p1, p2, p3, p3, p2, p1},
        {p2, p4, p5, p5, p4, p2},
        {p3, p5, p6, p6, p5, p3},
        {p3, p5, p6, p6, p5, p3},
        {p2, p4, p5, p5, p4, p2},
        {p1, p2, p3, p3, p2, p1}
    };
    assertArrayEquals(result, model.getImage());
  }

  // Tests applying the grayScale filter twice
  @Test
  public void testGrayScaleTwice() {
    IPixel[][] img = new PixelImpl[2][2];
    img[0][0] = new PixelImpl(100, 30, 60);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][0] = new PixelImpl(90, 60, 30);
    img[1][1] = new PixelImpl(250, 80, 60);
    ImageEditorModel model = new SimpleImageEditorModel(img);
    IPixel[][] img2 = new PixelImpl[2][2];
    img2[0][0] = new PixelImpl(47, 47, 47);
    img2[0][1] = new PixelImpl(0, 0, 0);
    img2[1][0] = new PixelImpl(64, 64, 64);
    img2[1][1] = new PixelImpl(115, 115, 115);
    model.grayscale();
    model.grayscale();
    assertArrayEquals(img2, model.getImage());
  }

  // Tests applying the sepia filter twice
  @Test
  public void testSepiaFilterTwice() {
    IPixel[][] img = new PixelImpl[2][2];
    img[0][0] = new PixelImpl(100, 30, 60);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][0] = new PixelImpl(90, 60, 30);
    img[1][1] = new PixelImpl(250, 80, 60);
    ImageEditorModel model = new SimpleImageEditorModel(img);
    IPixel[][] img2 = new PixelImpl[2][2];
    img2[0][0] = new PixelImpl(89, 80, 62);
    img2[0][1] = new PixelImpl(0, 0, 0);
    img2[1][0] = new PixelImpl(106, 94, 73);
    img2[1][1] = new PixelImpl(207, 184, 143);
    model.sepia();
    model.sepia();
    assertArrayEquals(img2, model.getImage());
  }

  // Tests applying all the filters once.
  @Test
  public void testAllFilters() {
    IPixel[][] img = ImageUtil
        .createChecker(new Color(160, 0, 160), new Color(0, 160, 160), 2, 3, 3);
    ImageEditorModel model = new SimpleImageEditorModel(img);
    model.sharpen();
    model.blur();
    model.sepia();
    model.grayscale();
    IPixel seven4 = new PixelImpl(74, 74, 74);
    IPixel one13 = new PixelImpl(113, 113, 113);
    IPixel one35 = new PixelImpl(135, 135, 135);
    IPixel one67 = new PixelImpl(167, 167, 167);
    IPixel one74 = new PixelImpl(174, 174, 174);
    IPixel one38 = new PixelImpl(138, 138, 138);
    IPixel[][] result = new IPixel[][]{
        {seven4, one13, one35, one35, one13, seven4},
        {one13, one67, one74, one74, one67, one13},
        {one35, one74, one38, one38, one74, one35},
        {one35, one74, one38, one38, one74, one35},
        {one13, one67, one74, one74, one67, one13},
        {seven4, one13, one35, one35, one13, seven4}
    };
    assertArrayEquals(result, model.getImage());
  }
}

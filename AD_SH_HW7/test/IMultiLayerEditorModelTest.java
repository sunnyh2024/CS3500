import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import model.ECMultiLayerModel;
import model.IMultiLayerEditorModel;
import model.ImageUtil;
import model.MultiLayerEditorModel;
import model.PixelImpl;
import org.junit.Before;
import org.junit.Test;
import model.IPixel;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the IMultiLayerEditorModel implementations and all dependencies: tests to ensure that
 * the implementations of IMultiLayerEditorModel and all methods inside work correctly.
 */
public abstract class IMultiLayerEditorModelTest {

  IPixel[][] img1;
  IPixel[][] img2;
  IPixel[][] img3;
  IPixel[][] nonRectImg;

  @Before
  public void init() {
    this.img1 = new PixelImpl[2][2];
    this.img1[0][0] = new PixelImpl(100, 30, 60);
    this.img1[0][1] = new PixelImpl(0, 0, 0);
    this.img1[1][0] = new PixelImpl(90, 60, 30);
    this.img1[1][1] = new PixelImpl(250, 80, 60);

    this.img2 = new PixelImpl[1][1];
    img2[0][0] = new PixelImpl(100, 30, 60);

    this.img3 = new PixelImpl[1][1];
    this.img3[0][0] = new PixelImpl(70, 60, 60);

    this.nonRectImg = new IPixel[2][2];
    this.img1[0][0] = new PixelImpl(100, 30, 60);
    this.img1[0][1] = new PixelImpl(0, 0, 0);
    this.img1[1][0] = new PixelImpl(90, 60, 30);
  }

  // Tests constructing a model with a null image
  @Test(expected = IllegalArgumentException.class)
  public void testConstructModelNullImage() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, null, img3);
  }

  // Tests constructing a model with images of different sizes
  @Test(expected = IllegalArgumentException.class)
  public void testConstructModelDifferentSizes() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img1, img3);
  }

  // Tests constructing a model with a non-rectangular image
  @Test(expected = IllegalArgumentException.class)
  public void testConstructModelNonRectangularImage() {
    IMultiLayerEditorModel model = createMultiLayerModel(img1, this.nonRectImg);
  }

  // Tests getting all images from an empty model. Also makes sure that constructing a model
  // with no images is constructed properly
  @Test
  public void testGetAllImagesEmptyModel() {
    IMultiLayerEditorModel model = createMultiLayerModel();
    assertEquals(new ArrayList<IPixel[][]>(), model.getAllImages());
  }

  // Tests getting all images from a model with one image. Also makes sure that constructing a model
  //  with one image is constructed properly
  @Test
  public void testGetAllImagesModelOneImage() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2);
    assertArrayEquals(img2, model.getAllImages().get(0));
  }

  // Test getting all images from a model with multiple images. lso makes sure that constructing
  // a model with multiple images is constructed properly
  @Test
  public void testGetAllImagesModelMultipleImages() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3);
    assertArrayEquals(img2, model.getAllImages().get(0));
    assertArrayEquals(img3, model.getAllImages().get(1));
  }

  // Tests getting a single image with a negative index
  @Test(expected = IllegalArgumentException.class)
  public void testGetSingleImageNegativeIndex() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2);
    model.getSingleImage(-1);
  }

  // Tests getting a single image with a too large of an index
  @Test(expected = IllegalArgumentException.class)
  public void testGetSingleImageTooLargeIndex() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3);
    model.getSingleImage(4);
  }

  // Tests getting a single image from a model with no images
  @Test(expected = IllegalArgumentException.class)
  public void testGetSingleImageEmptyModel() {
    IMultiLayerEditorModel model = createMultiLayerModel();
    model.getSingleImage(0);
  }

  // Tests getting a single image from a model with a valid index
  @Test
  public void testGetSingleImageValidIndex() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3);
    assertArrayEquals(img3, model.getSingleImage(1));
  }

  // Tests getting the top most image with no images at all in the model
  @Test(expected = IllegalArgumentException.class)
  public void testGetTopMostImageNone() {
    IMultiLayerEditorModel model = createMultiLayerModel();
    IPixel[][] image = model.getTopMostImage();
  }

  // Tests getting the top most image with all images being invisible
  @Test(expected = IllegalArgumentException.class)
  public void testGetTopMostImageAllInvisible() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3, img2);
    model.changeVisibility(0);
    model.changeVisibility(1);
    model.changeVisibility(2);
    IPixel[][] image = model.getTopMostImage();
  }

  // Tests getting the top most image with every image being visible
  @Test
  public void testGetTopMostImageAllVisible() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3, img2);
    assertArrayEquals(img2, model.getTopMostImage());
  }

  // Tests getting the top most image with top image invisible
  @Test
  public void testGetTopMostImageTopInvisible() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3, img2);
    model.changeVisibility(2);
    assertArrayEquals(img3, model.getTopMostImage());
  }

  // Tests getting the top most image with top 2 images invisible
  @Test
  public void testGetTopMostImageTopTwoInvisible() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3, img2);
    model.changeVisibility(2);
    model.changeVisibility(1);
    assertArrayEquals(img2, model.getTopMostImage());
  }

  // Tests getting the layer visibility for a model with no images
  @Test
  public void testGetLayerVisibilityNoImages() {
    IMultiLayerEditorModel model = createMultiLayerModel();
    assertEquals(new ArrayList<Boolean>(), model.getLayerVisibility());
  }

  // Tests getting the layer visibility for a model with all visible images
  @Test
  public void testGetLayerVisibilityAllVisibleImages() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3, img2);
    assertEquals(new ArrayList<Boolean>(Arrays.asList(true, true, true)),
        model.getLayerVisibility());
  }

  // Tests getting the layer visibility for a model with top image invisible
  @Test
  public void testGetLayerVisibilitySomeVisibleImages() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3, img2);
    model.changeVisibility(2);
    assertEquals(new ArrayList<Boolean>(Arrays.asList(true, true, false)),
        model.getLayerVisibility());
  }

  // Tests the blur method with a negative index
  @Test(expected = IllegalArgumentException.class)
  public void testBlurNegativeIndex() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3);
    model.blur(-1);
  }

  // Tests the blur method with too large of an index
  @Test(expected = IllegalArgumentException.class)
  public void testBlurTooLargeIndex() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3);
    model.blur(3);
  }

  // Tests the blur method with a proper index
  @Test
  public void testBlurValidIndex() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel g = new PixelImpl(137, 137, 137);
    IPixel[][] img = new IPixel[][]{
        {b, g, w},
        {g, g, g},
        {w, g, b},
    };
    IPixel[][] otherImg = ImageUtil.createChecker(Color.CYAN, Color.ORANGE, 1, 3, 3);

    IMultiLayerEditorModel model = createMultiLayerModel(otherImg, img, img);
    model.blur(2);
    IPixel[][] finalImage = model.getSingleImage(2);
    IPixel hundred = new PixelImpl(100, 100, 100);
    IPixel one07 = new PixelImpl(107, 107, 107);
    IPixel four3 = new PixelImpl(43, 43, 43);
    IPixel[][] result = new IPixel[][]{
        {new PixelImpl(43, 43, 43), hundred, new PixelImpl(107, 107, 107)},
        {hundred, new PixelImpl(135, 135, 135), hundred},
        {new PixelImpl(107, 107, 107), hundred, new PixelImpl(43, 43, 43)},
    };
    assertArrayEquals(result, finalImage);
  }

  // Tests the blur method with a proper index and a single image
  @Test
  public void testBlurValidIndexSingle() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel g = new PixelImpl(137, 137, 137);
    IPixel[][] img = new IPixel[][]{
        {b, g, w},
        {g, g, g},
        {w, g, b},
    };

    IMultiLayerEditorModel model = createMultiLayerModel(img);
    model.blur(0);
    IPixel[][] finalImage = model.getSingleImage(0);
    IPixel hundred = new PixelImpl(100, 100, 100);
    IPixel one07 = new PixelImpl(107, 107, 107);
    IPixel four3 = new PixelImpl(43, 43, 43);
    IPixel[][] result = new IPixel[][]{
        {new PixelImpl(43, 43, 43), hundred, new PixelImpl(107, 107, 107)},
        {hundred, new PixelImpl(135, 135, 135), hundred},
        {new PixelImpl(107, 107, 107), hundred, new PixelImpl(43, 43, 43)},
    };
    assertArrayEquals(result, finalImage);
  }

  // Tests the sharpen method with a negative index
  @Test(expected = IllegalArgumentException.class)
  public void testSharpenNegativeIndex() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3);
    model.sharpen(-1);
  }

  // Tests the sharpen method with too large of an index
  @Test(expected = IllegalArgumentException.class)
  public void testSharpenTooLargeIndex() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3);
    model.sharpen(3);
  }

  // Tests the sharpen method with a proper index
  @Test
  public void testSharpenValidIndex() {
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
    IPixel[][] otherImg = ImageUtil.createChecker(Color.CYAN, Color.ORANGE, 1, 5, 5);

    IMultiLayerEditorModel model = createMultiLayerModel(otherImg, otherImg, img);
    model.sharpen(2);
    IPixel[][] finalImage = model.getSingleImage(2);
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
    assertArrayEquals(result, finalImage);
  }

  // Tests the sharpen method with a proper index and a single image
  @Test
  public void testSharpenValidIndexSingle() {
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

    IMultiLayerEditorModel model = createMultiLayerModel(img);
    model.sharpen(0);
    IPixel[][] finalImage = model.getSingleImage(0);
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
    assertArrayEquals(result, finalImage);
  }

  // Tests the grayscale method with a negative index
  @Test(expected = IllegalArgumentException.class)
  public void testGrayscaleNegativeIndex() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3);
    model.grayscale(-1);
  }

  // Tests the grayscale method with too large of an index
  @Test(expected = IllegalArgumentException.class)
  public void testGrayscaleTooLargeIndex() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3);
    model.grayscale(3);
  }

  // Tests the grayscale method with a proper index
  @Test
  public void testGrayscaleValidIndex() {
    IPixel[][] img = ImageUtil.createChecker(Color.CYAN, Color.ORANGE, 1, 3, 3);
    IPixel[][] result = ImageUtil
        .createChecker(new Color(201, 201, 201), new Color(197, 197, 197),
            1, 3, 3);

    IMultiLayerEditorModel model = createMultiLayerModel(img, img, img);
    model.grayscale(2);
    IPixel[][] finalImage = model.getSingleImage(2);
    assertArrayEquals(result, finalImage);
  }

  // Tests the grayscale method with a proper index and a single image
  @Test
  public void testGrayscaleValidIndexSingle() {
    IPixel[][] img = ImageUtil.createChecker(Color.CYAN, Color.ORANGE, 1, 3, 3);
    IPixel[][] result = ImageUtil
        .createChecker(new Color(201, 201, 201), new Color(197, 197, 197),
            1, 3, 3);

    IMultiLayerEditorModel model = createMultiLayerModel(img);
    model.grayscale(0);
    IPixel[][] finalImage = model.getSingleImage(0);
    assertArrayEquals(result, finalImage);
  }

  // Tests the sepia method with a negative index
  @Test(expected = IllegalArgumentException.class)
  public void testSepiaNegativeIndex() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3);
    model.sepia(-1);
  }

  // Tests the sepia method with too large of an index
  @Test(expected = IllegalArgumentException.class)
  public void testSepiaTooLargeIndex() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3);
    model.sepia(3);
  }

  // Tests the sepia method with a proper index
  @Test
  public void testSepiaValidIndex() {
    IPixel[][] img = ImageUtil.createChecker(Color.CYAN, Color.ORANGE, 1, 3, 3);
    IPixel[][] result = ImageUtil.createChecker(new Color(244, 218, 170), new Color(254, 226, 176),
        1, 3, 3);

    IMultiLayerEditorModel model = createMultiLayerModel(img, img, img);
    model.sepia(2);
    IPixel[][] finalImage = model.getSingleImage(2);
    assertArrayEquals(result, finalImage);
  }

  // Tests the sepia method with a proper index and a single image
  @Test
  public void testSepiaValidIndexSingle() {
    IPixel[][] img = ImageUtil.createChecker(Color.CYAN, Color.ORANGE, 1, 3, 3);
    IPixel[][] result = ImageUtil.createChecker(new Color(244, 218, 170), new Color(254, 226, 176),
        1, 3, 3);

    IMultiLayerEditorModel model = createMultiLayerModel(img);
    model.sepia(0);
    IPixel[][] finalImage = model.getSingleImage(0);
    assertArrayEquals(result, finalImage);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testAddImageNullImage() {
    IMultiLayerEditorModel model = createMultiLayerModel();
    model.addImage(null, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddImageBadIndex() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel[][] img = new IPixel[][]{
        {b, b, b, b},
        {w, w, w, w},
        {b, b, b, b}
    };
    IMultiLayerEditorModel model = createMultiLayerModel(img, img);
    model.addImage(img, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddImageBadImageDimensions() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel[][] img = new IPixel[][]{
        {b, b, b, b},
        {w, w, w, w},
        {b, b, b, b}
    };
    IPixel[][] badImg = new IPixel[][]{
        {b, b, b},
        {w, w, w},
        {b, b, b}
    };
    IMultiLayerEditorModel model = createMultiLayerModel(img, img);
    model.addImage(badImg, 1);
  }

  @Test
  public void testAddImageToTop() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel r = new PixelImpl(255, 0, 0);
    IPixel g = new PixelImpl(0, 255, 0);
    IPixel[][] placeHolder = new IPixel[][]{
        {r, g, r, g},
        {r, g, r, g},
        {r, g, r, g}
    };
    IPixel[][] img = new IPixel[][]{
        {b, b, b, b},
        {w, w, w, w},
        {b, b, b, b}
    };
    IMultiLayerEditorModel model = createMultiLayerModel(placeHolder, placeHolder);
    model.addImage(img, 2);
    assertEquals(3, model.getAllImages().size());
    assertArrayEquals(img, model.getSingleImage(2));
  }

  @Test
  public void testAddImageToMiddle() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel r = new PixelImpl(255, 0, 0);
    IPixel g = new PixelImpl(0, 255, 0);
    IPixel[][] placeHolder = new IPixel[][]{
        {r, g, r, g},
        {r, g, r, g},
        {r, g, r, g}
    };
    IPixel[][] img = new IPixel[][]{
        {b, b, b, b},
        {w, w, w, w},
        {b, b, b, b}
    };
    IMultiLayerEditorModel model = createMultiLayerModel(placeHolder, placeHolder);
    model.addImage(img, 1);
    assertEquals(3, model.getAllImages().size());
    assertArrayEquals(img, model.getSingleImage(1));
  }

  @Test
  public void testAddImageToBottom() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel r = new PixelImpl(255, 0, 0);
    IPixel g = new PixelImpl(0, 255, 0);
    IPixel[][] placeHolder = new IPixel[][]{
        {r, g, r, g},
        {r, g, r, g},
        {r, g, r, g}
    };
    IPixel[][] img = new IPixel[][]{
        {b, b, b, b},
        {w, w, w, w},
        {b, b, b, b}
    };
    IMultiLayerEditorModel model = createMultiLayerModel(placeHolder, placeHolder);
    model.addImage(img, 0);
    assertEquals(3, model.getAllImages().size());
    assertArrayEquals(img, model.getSingleImage(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveImageBadIndex() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel[][] img = new IPixel[][]{
        {b, b, b, b},
        {w, w, w, w},
        {b, b, b, b}
    };
    IMultiLayerEditorModel model = createMultiLayerModel(img);
    model.removeImage(2);
  }

  @Test
  public void testRemoveImageTop() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel r = new PixelImpl(255, 0, 0);
    IPixel g = new PixelImpl(0, 255, 0);
    IPixel[][] placeHolder = new IPixel[][]{
        {r, g, r, g},
        {r, g, r, g},
        {r, g, r, g}
    };
    IPixel[][] img = new IPixel[][]{
        {b, b, b, b},
        {w, w, w, w},
        {b, b, b, b}
    };
    IMultiLayerEditorModel model = createMultiLayerModel(img, placeHolder, img);
    model.removeImage(2);
    assertEquals(2, model.getAllImages().size());
    assertArrayEquals(placeHolder, model.getSingleImage(1));
  }

  @Test
  public void testRemoveImageMiddle() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel r = new PixelImpl(255, 0, 0);
    IPixel g = new PixelImpl(0, 255, 0);
    IPixel[][] placeHolder = new IPixel[][]{
        {r, g, r, g},
        {r, g, r, g},
        {r, g, r, g}
    };
    IPixel[][] img = new IPixel[][]{
        {b, b, b, b},
        {w, w, w, w},
        {b, b, b, b}
    };
    IMultiLayerEditorModel model = createMultiLayerModel(img, placeHolder, img);
    model.removeImage(1);
    assertEquals(2, model.getAllImages().size());
    assertArrayEquals(img, model.getSingleImage(1));
  }

  @Test
  public void testRemoveImageBottom() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel r = new PixelImpl(255, 0, 0);
    IPixel g = new PixelImpl(0, 255, 0);
    IPixel[][] placeHolder = new IPixel[][]{
        {r, g, r, g},
        {r, g, r, g},
        {r, g, r, g}
    };
    IPixel[][] img = new IPixel[][]{
        {b, b, b, b},
        {w, w, w, w},
        {b, b, b, b}
    };
    IMultiLayerEditorModel model = createMultiLayerModel(img, placeHolder, img);
    model.removeImage(0);
    assertEquals(2, model.getAllImages().size());
    assertArrayEquals(placeHolder, model.getSingleImage(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCopyImageBadCopyIndex() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel[][] img = new IPixel[][]{
        {b, b, b, b},
        {w, w, w, w},
        {b, b, b, b}
    };
    IMultiLayerEditorModel model = createMultiLayerModel(img, img);
    model.copyImage(3, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCopyImageBadDestIndex() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel[][] img = new IPixel[][]{
        {b, b, b, b},
        {w, w, w, w},
        {b, b, b, b}
    };
    IMultiLayerEditorModel model = createMultiLayerModel(img, img);
    model.copyImage(1, 4);
  }

  @Test
  public void testCopyImageValid() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel r = new PixelImpl(255, 0, 0);
    IPixel g = new PixelImpl(0, 255, 0);
    IPixel[][] placeHolder = new IPixel[][]{
        {r, g, r, g},
        {r, g, r, g},
        {r, g, r, g}
    };
    IPixel[][] img = new IPixel[][]{
        {b, b, b, b},
        {w, w, w, w},
        {b, b, b, b}
    };
    IMultiLayerEditorModel model = createMultiLayerModel(img, placeHolder, img);
    model.copyImage(1, 3);
    assertEquals(4, model.getAllImages().size());
    assertArrayEquals(placeHolder, model.getSingleImage(3));
  }

  // Tests changing visibility of an image with an invalid index
  @Test(expected = IllegalArgumentException.class)
  public void testChangeVisibilityInvalidIndex() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3, img2);
    model.changeVisibility(4);
  }

  // Tests changing visibility of an image with no images in the model
  @Test(expected = IllegalArgumentException.class)
  public void testChangeVisibilityNoImages() {
    IMultiLayerEditorModel model = createMultiLayerModel();
    model.changeVisibility(0);
  }

  // Tests changing visibility of an image with a valid index
  @Test
  public void testChangeVisibilityValidIndex() {
    IMultiLayerEditorModel model = createMultiLayerModel(img2, img3);
    assertEquals(new ArrayList<Boolean>(Arrays.asList(true, true)), model.getLayerVisibility());
    model.changeVisibility(0);
    assertEquals(new ArrayList<Boolean>(Arrays.asList(false, true)), model.getLayerVisibility());
    model.changeVisibility(1);
    assertEquals(new ArrayList<Boolean>(Arrays.asList(false, false)), model.getLayerVisibility());
    model.changeVisibility(0);
    assertEquals(new ArrayList<Boolean>(Arrays.asList(true, false)), model.getLayerVisibility());
    model.changeVisibility(1);
    assertEquals(new ArrayList<Boolean>(Arrays.asList(true, true)), model.getLayerVisibility());
  }

  /**
   * Constructs an instance of a class that implements IMultiLayerEditorModel.
   *
   * @return a new instance of IMultiLayerEditorModel
   */
  protected abstract IMultiLayerEditorModel createMultiLayerModel(IPixel[][] ... images);

  /**
   * Concrete class for testing MultiLayerEditorModel implementation of IMultiLayerEditorModel.
   */

  public static final class MultiLayerEditorModelTest extends IMultiLayerEditorModelTest {

    @Override
    protected IMultiLayerEditorModel createMultiLayerModel(IPixel[][] ... images) {
      return new MultiLayerEditorModel(images);
    }
  }

  /**
   * Concrete class for testing ECMultiLayerModel implementation of IMultiLayerEditorModel.
   */

  public static final class ECMultiLayerModelTest extends IMultiLayerEditorModelTest {

    @Override
    protected IMultiLayerEditorModel createMultiLayerModel(IPixel[][] ... images) {
      return new ECMultiLayerModel(images);
    }
  }


}

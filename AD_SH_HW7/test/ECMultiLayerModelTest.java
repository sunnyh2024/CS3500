import java.awt.Color;
import java.util.Arrays;
import model.ECMultiLayerModel;
import model.IECMultiLayerModel;
import model.IPixel;
import model.ImageUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests for the ECMultiLayerModel class and all dependencies: tests to ensure that
 * ECMultiLayerModel and all methods inside work correctly.
 */
public class ECMultiLayerModelTest {

  private IPixel[][] img;
  private IECMultiLayerModel model;

  @Before
  public void init() {
    img = ImageUtil.createChecker(Color.white, Color.black, 10, 10, 10);
    model = new ECMultiLayerModel(img, img);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMosaicBadSeed() {
    model.mosaic(10001, 1);
  }

  // Note: Not sure how to properly test mosaic as we are unable to pass a Random object from here.
  // We are relying on the MosaicFilter tests to make sure it works properly
  @Test
  public void testMosaic() {
    IECMultiLayerModel model2 = new ECMultiLayerModel(img, img);
    model2.mosaic(100, 0);
    model.mosaic(100, 0);
    assertFalse(Arrays.equals(model2.getSingleImage(0), model.getSingleImage(1)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMosaicNegSeed() {
    model.mosaic(-1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMosaicBadLayer() {
    model.mosaic(100, 2);
  }

  // No clue how to test this because of mosaic being random
  //  @Test
  //  public void testMosaicValid() {
  //
  //  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleBadWidth() {
    model.downscale(200, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleBadHeight() {
    model.downscale(50, -50);
  }

  @Test
  public void testDownscaleValid() {
    IPixel[][] expected = ImageUtil.createChecker(Color.white, Color.black, 5, 10, 10);
    model.downscale(50, 50);
    assertArrayEquals(model.getSingleImage(0), expected);
    assertArrayEquals(model.getSingleImage(1), expected);
  }
}

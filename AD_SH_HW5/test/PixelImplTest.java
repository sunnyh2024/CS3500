
import model.IPixel;
import model.PixelImpl;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;

/**
 * Tests for the PixelImpl class and all dependencies: tests to ensure that PixelImpl and all
 * methods inside work correctly.
 */
public class PixelImplTest {

  // Tests the PixelImpl constructor for taking in an invalid Red value
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadRVal() {
    new PixelImpl(512, 128, 0);
  }

  // Tests the PixelImpl constructor for taking in an invalid Blue value
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadGVal() {
    new PixelImpl(12, 360, 0);
  }

  // Tests the PixelImpl constructor for taking in an invalid Green value
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadBVal() {
    new PixelImpl(90, 128, -65);
  }

  // Tests the PixelImpl constructor for taking multiple invalid values
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorAllBadVal() {
    new PixelImpl(1200, -128, -65);
  }

  // Tests the PixelImpl constructor for taking in an invalid Red value in an array
  @Test(expected = IllegalArgumentException.class)
  public void testArrayConstructorBadR() {
    int[] badArr = {-29, 0, 54};
    new PixelImpl(badArr);
  }

  // Tests the PixelImpl constructor for taking in an invalid Green value in an array
  @Test(expected = IllegalArgumentException.class)
  public void testArrayConstructorBadG() {
    int[] badArr = {29, -233, 54};
    new PixelImpl(badArr);
  }

  // Tests the PixelImpl constructor for taking in an invalid Blue value in an array
  @Test(expected = IllegalArgumentException.class)
  public void testArrayConstructorBadB() {
    int[] badArr = {100, 120, 8908};
    new PixelImpl(badArr);
  }

  // Tests the PixelImpl constructor for taking in an array with an invalid size
  @Test(expected = IllegalArgumentException.class)
  public void testArrayConstructorBadSize() {
    int[] badArr = {100, 120, 12, 129, 0};
    new PixelImpl(badArr);
  }

  // Tests the getRGB method as well as making sure the constructor with individual values
  // work properly
  @Test
  public void testGetRGBIntConstructor() {
    IPixel pix = new PixelImpl(123, 124, 125);
    int[] arr = {123, 124, 125};
    assertArrayEquals(pix.getRGB(), arr);
  }

  // Tests the array constructor of PixelImpl to ensure that a null array cannot be passed in
  @Test(expected = IllegalArgumentException.class)
  public void testNullArrayConstructor() {
    new PixelImpl(null);
  }


  // Tests the getRGB method as well as making sure the constructor with array of values
  // work properly
  @Test
  public void testGetRGBArrayConstructor() {
    int[] arr = {123, 124, 125};
    IPixel pix = new PixelImpl(arr);
    assertArrayEquals(pix.getRGB(), arr);
  }

  // Tests the equals method with the same object
  @Test
  public void testEqualsSameObject() {
    int[] arr = {255, 255, 255};
    IPixel pix = new PixelImpl(arr);
    assertTrue(pix.equals(pix));
  }

  // Tests the equals method with a different type of object
  @Test
  public void testEqualsDifferentType() {
    int[] arr = {255, 255, 255};
    IPixel pix = new PixelImpl(arr);
    assertFalse(pix.equals(arr));
  }

  // Tried to test .equals() with null but Handins would not allow for it because of javastyle

  // Tests the equals method with two different PixelImpls with the same rgb values
  @Test
  public void testEqualsSameValues() {
    int[] arr = {255, 255, 255};
    IPixel pix = new PixelImpl(arr);
    IPixel pix2 = new PixelImpl(255, 255, 255);
    assertTrue(pix.equals(pix2));
  }

  // Tests the equals method with two different PixelImpls with different rgb values
  @Test
  public void testEqualsDifferentValues() {
    int[] arr = {255, 255, 255};
    IPixel pix = new PixelImpl(arr);
    IPixel pix2 = new PixelImpl(128, 127, 127);
    assertFalse(pix.equals(pix2));
  }

  // Tests the hashCode method with the same object
  @Test
  public void testHashcodeSameObject() {
    int[] arr = {255, 255, 255};
    IPixel pix = new PixelImpl(arr);
    assertTrue(pix.hashCode() == pix.hashCode());
  }

  // Tests the hashCode method with two different objects but the same rbg values
  @Test
  public void testHashcodeSameRGB() {
    int[] arr = {255, 255, 255};
    IPixel pix = new PixelImpl(arr);
    IPixel pix2 = new PixelImpl(255, 255, 255);
    assertTrue(pix.hashCode() == pix2.hashCode());
  }

  // Tests the hashCode method with two different objects and different rbg values
  @Test
  public void testHashcodeDifferentRGB() {
    int[] arr = {255, 255, 255};
    IPixel pix = new PixelImpl(arr);
    IPixel pix2 = new PixelImpl(128, 127, 127);
    assertFalse(pix.hashCode() == pix2.hashCode());
  }
}

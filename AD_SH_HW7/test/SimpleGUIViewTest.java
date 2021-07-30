
import java.io.IOException;
import model.IPixel;
import model.PixelImpl;
import org.junit.Before;
import org.junit.Test;
import view.IGUIView;
import view.SimpleGUIView;

/**
 * Tests for the SimpleGUIView class and all dependencies: tests to ensure that SimpleGUIView
 * and all methods inside work correctly.
 */
public class SimpleGUIViewTest {

  IGUIView view;

  @Before
  public void init() {
    view = new SimpleGUIView();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullCaption() {
    new SimpleGUIView(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRenderMessageNullMessage() {
    try {
      view.renderMessage(null);
    } catch (IOException e) {
      throw new IllegalArgumentException("Test exception");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCurrentLayerTextBadInput() {
    view.setCurrentLayerText(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetNumOfLayersTextBadInput() {
    view.setNumOfLayersText(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFeaturesNullFeatures() {
    view.addFeatures(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRenderImageNullImage() {
    view.renderImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRenderImageNonRect() {
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel[][] img = new IPixel[][] {{b, b}, {b}};
    view.renderImage(img);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateImageNullImage() {
    view.updateImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetInputNullMessage() {
    view.getInput(null);
  }
}

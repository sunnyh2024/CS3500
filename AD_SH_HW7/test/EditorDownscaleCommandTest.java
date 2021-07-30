import controller.commands.EditorDownscaleCommand;
import controller.commands.IEditorCommand;
import java.awt.Color;
import model.IMultiLayerEditorModel;
import model.IPixel;
import model.ImageUtil;
import model.MultiLayerEditorModel;
import model.PixelImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
 * Tests for the EditorDownscaleCommand class and all dependencies: tests to ensure that
 * EditorDownscaleCommand and all methods inside work correctly.
 */
public class EditorDownscaleCommandTest {

  private IMultiLayerEditorModel model;

  @Before
  public void init() {
    model = new MultiLayerEditorModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNotPositiveNumPixelsWidth() {
    IEditorCommand cmd = new EditorDownscaleCommand(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNotPositiveNumPixelsHeight() {
    IEditorCommand cmd = new EditorDownscaleCommand(0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteNullModel() {
    IEditorCommand cmd = new EditorDownscaleCommand(1, 1);
    cmd.execute(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidExecute() {
    IEditorCommand cmd = new EditorDownscaleCommand(10000, 1);
    IPixel[][] img = new PixelImpl[][]{};
    model.addImage(img, 0);
    cmd.execute(model);
  }

  @Test
  public void testValidExecute() {
    IPixel[][] start = ImageUtil.createChecker(Color.white, Color.black, 5, 2, 2);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel[][] expected = new IPixel[][] {
        {w, w, b, b},
        {w, w, b, b},
        {w, w, b, b},
        {b, b, w, w},
        {b, b, w, w},
        {b, b, w, w}
    };
    model.addImage(start, 0);
    IEditorCommand cmd = new EditorDownscaleCommand(6, 4);
    cmd.execute(model);
    assertArrayEquals(expected, model.getSingleImage(0));
  }

}

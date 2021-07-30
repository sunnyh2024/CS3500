import static org.junit.Assert.assertArrayEquals;

import controller.commands.EditorSharpenCommand;
import controller.commands.IEditorCommand;
import model.IMultiLayerEditorModel;
import model.IPixel;
import model.MultiLayerEditorModel;
import model.PixelImpl;
import org.junit.Test;

/**
 * Represents tests for the EditorSharpenCommand class and its dependencies: tests to ensure that
 * EditorSharpenCommand and the execute method work correctly.
 */
public class EditorSharpenCommandTest {

  // Tests the construction of an EditorSharpenCommand with an invalid layer index (less than 1)
  @Test(expected = IllegalArgumentException.class)
  public void testConstructInvalidLayer() {
    IEditorCommand command = new EditorSharpenCommand(0);
  }

  // Tests the execute method with a null model
  @Test(expected = IllegalArgumentException.class)
  public void testExecuteNullModel() {
    IEditorCommand command = new EditorSharpenCommand(1);
    command.execute(null);
  }

  // Tests the execute method with a valid model but receives an IAE from the model
  @Test(expected = IllegalArgumentException.class)
  public void testExecuteValidModelWithIAE() {
    IEditorCommand command = new EditorSharpenCommand(1);
    IMultiLayerEditorModel model = new MultiLayerEditorModel();
    command.execute(model);
  }

  // Tests the execute method with a valid model that works properly
  @Test
  public void testExecuteValidModelValidExecute() {
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
    IEditorCommand command = new EditorSharpenCommand(1);
    IMultiLayerEditorModel model = new MultiLayerEditorModel(img);
    command.execute(model);
    assertArrayEquals(result, model.getSingleImage(0));
  }

}

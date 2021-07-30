import static org.junit.Assert.assertArrayEquals;

import controller.commands.EditorSepiaCommand;
import controller.commands.IEditorCommand;
import model.IMultiLayerEditorModel;
import model.IPixel;
import model.MultiLayerEditorModel;
import model.PixelImpl;
import org.junit.Test;

/**
 * Represents tests for the EditorSepiaCommand class and its dependencies: tests to ensure that
 * EditorSepiaCommand and the execute method work correctly.
 */
public class EditorSepiaCommandTest {

  // Tests the construction of an EditorSepiaCommand with an invalid layer index (less than 1)
  @Test(expected = IllegalArgumentException.class)
  public void testConstructInvalidLayer() {
    IEditorCommand command = new EditorSepiaCommand(0);
  }

  // Tests the execute method with a null model
  @Test(expected = IllegalArgumentException.class)
  public void testExecuteNullModel() {
    IEditorCommand command = new EditorSepiaCommand(1);
    command.execute(null);
  }

  // Tests the execute method with a valid model but receives an IAE from the model
  @Test(expected = IllegalArgumentException.class)
  public void testExecuteValidModelWithIAE() {
    IEditorCommand command = new EditorSepiaCommand(1);
    IMultiLayerEditorModel model = new MultiLayerEditorModel();
    command.execute(model);
  }

  // Tests the execute method with a valid model that works properly
  @Test
  public void testExecuteValidModelValidExecute() {
    IPixel[][] img = new PixelImpl[2][2];
    img[0][0] = new PixelImpl(100, 30, 60);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][0] = new PixelImpl(90, 60, 30);
    img[1][1] = new PixelImpl(250, 80, 60);
    IPixel[][] result = new PixelImpl[2][2];
    result[0][0] = new PixelImpl(74, 66, 51);
    result[0][1] = new PixelImpl(0, 0, 0);
    result[1][0] = new PixelImpl(87, 78, 60);
    result[1][1] = new PixelImpl(171, 152, 119);
    IEditorCommand command = new EditorSepiaCommand(1);
    IMultiLayerEditorModel model = new MultiLayerEditorModel(img);
    command.execute(model);
    assertArrayEquals(result, model.getSingleImage(0));
  }

}

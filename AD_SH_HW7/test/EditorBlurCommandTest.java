import controller.commands.EditorBlurCommand;
import controller.commands.IEditorCommand;
import model.IMultiLayerEditorModel;
import model.IPixel;
import model.MultiLayerEditorModel;
import model.PixelImpl;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;


/**
 * Represents tests for the EditorBlurCommand class and its dependencies: tests to ensure that
 * EditorBlurCommand and the execute method work correctly.
 */
public class EditorBlurCommandTest {

  // Tests the construction of an EditorBlurCommand with an invalid layer index (less than 1)
  @Test(expected = IllegalArgumentException.class)
  public void testConstructEditorBlurCommandInvalidLayer() {
    IEditorCommand command = new EditorBlurCommand(0);
  }

  // Tests the execute method with a null model
  @Test(expected = IllegalArgumentException.class)
  public void testExecuteNullModel() {
    IEditorCommand command = new EditorBlurCommand(1);
    command.execute(null);
  }

  // Tests the execute method with a valid model but receives an IAE from the model
  @Test(expected = IllegalArgumentException.class)
  public void testExecuteValidModelWithIAE() {
    IEditorCommand command = new EditorBlurCommand(1);
    IMultiLayerEditorModel model = new MultiLayerEditorModel();
    command.execute(model);
  }

  // Tests the execute method with a valid model that works properly
  @Test
  public void testExecuteValidModelValidExecute() {
    IPixel[][] img = new IPixel[][]{{new PixelImpl(200, 200, 200)}};
    IPixel[][] result = new IPixel[][]{{new PixelImpl(50, 50, 50)}};
    IEditorCommand command = new EditorBlurCommand(1);
    IMultiLayerEditorModel model = new MultiLayerEditorModel(img);
    command.execute(model);
    assertArrayEquals(result, model.getSingleImage(0));
  }


}

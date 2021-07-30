import controller.commands.EditorChangeVisibilityCommand;
import controller.commands.IEditorCommand;
import java.util.ArrayList;
import java.util.Arrays;
import model.IMultiLayerEditorModel;
import model.IPixel;
import model.MultiLayerEditorModel;
import model.PixelImpl;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Represents tests for the EditorChangeVisibilityCommand class and its dependencies: tests to
 * ensure that EditorChangeVisibilityCommand and the execute method work correctly.
 */
public class EditorChangeVisibilityCommandTest {

  // Tests the construction of an EditorChangeVisibilityCommand
  // with an invalid layer index (less than 1)
  @Test(expected = IllegalArgumentException.class)
  public void testConstructInvalidLayer() {
    IEditorCommand command = new EditorChangeVisibilityCommand(0);
  }

  // Tests the execute method with a null model
  @Test(expected = IllegalArgumentException.class)
  public void testExecuteNullModel() {
    IEditorCommand command = new EditorChangeVisibilityCommand(1);
    command.execute(null);
  }

  // Tests the execute method with a valid model but receives an IAE from the model
  @Test(expected = IllegalArgumentException.class)
  public void testExecuteValidModelWithIAE() {
    IEditorCommand command = new EditorChangeVisibilityCommand(1);
    IMultiLayerEditorModel model = new MultiLayerEditorModel();
    command.execute(model);
  }

  // Tests the execute method with a valid model that works properly
  @Test
  public void testExecuteValidModelValidExecute() {
    IPixel[][] img = new IPixel[][]{{new PixelImpl(200, 200, 200)}};
    IEditorCommand command = new EditorChangeVisibilityCommand(3);
    IMultiLayerEditorModel model = new MultiLayerEditorModel(img, img, img);
    command.execute(model);
    assertEquals(new ArrayList<Boolean>(Arrays.asList(true, true, false)),
        model.getLayerVisibility());
  }
}

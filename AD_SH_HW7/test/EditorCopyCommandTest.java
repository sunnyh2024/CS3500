import controller.commands.EditorCopyCommand;
import controller.commands.IEditorCommand;
import model.IMultiLayerEditorModel;
import model.IPixel;
import model.MultiLayerEditorModel;
import model.PixelImpl;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
 * Represents tests for the EditorCopyCommand class and its dependencies: tests to ensure that
 * EditorCopyCommand and the execute method work correctly.
 */
public class EditorCopyCommandTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructInvalidCopyLayer() {
    IEditorCommand command = new EditorCopyCommand(0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructInvalidDestLayer() {
    IEditorCommand command = new EditorCopyCommand(1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteNullModel() {
    IEditorCommand command = new EditorCopyCommand(1, 1);
    command.execute(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteReceiveIAEFromModel() {
    IEditorCommand command = new EditorCopyCommand(1, 2);
    IMultiLayerEditorModel model = new MultiLayerEditorModel();
    command.execute(model);
  }

  @Test
  public void testExecuteValid() {
    IPixel[][] img = new IPixel[][]{{new PixelImpl(200, 200, 200)}};
    IEditorCommand command = new EditorCopyCommand(1, 2);
    IMultiLayerEditorModel model = new MultiLayerEditorModel(img);
    command.execute(model);
    assertArrayEquals(img, model.getSingleImage(1));
  }
}

import controller.commands.EditorCheckerCommand;
import controller.commands.IEditorCommand;
import model.IMultiLayerEditorModel;
import model.IPixel;
import model.MultiLayerEditorModel;
import model.PixelImpl;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Represents tests for the EditorCheckerCommand class and its dependencies: tests to ensure that
 * EditorCheckerCommand and the execute method work correctly.
 */
public class EditorCheckerCommandTest {

  // Tests the construction of an EditorCheckerCommand with a null color string
  @Test(expected = IllegalArgumentException.class)
  public void testConstructNullColor() {
    IEditorCommand command = new EditorCheckerCommand(null, "green", 1, 1, 1, 1);
  }

  // Tests the construction of an EditorCheckerCommand with a string that is not a color
  @Test(expected = IllegalArgumentException.class)
  public void testConstructInvalidColor() {
    IEditorCommand command = new EditorCheckerCommand("sdfsfad", "green", 1, 1, 1, 1);
  }

  // Tests the construction of an EditorCheckerCommand with an invalid size(less than 1)
  @Test(expected = IllegalArgumentException.class)
  public void testConstructInvalidSize() {
    IEditorCommand command = new EditorCheckerCommand("blue", "green", 0, 1, 1, 1);
  }

  // Tests the construction of an EditorCheckerCommand with an invalid width(less than 1)
  @Test(expected = IllegalArgumentException.class)
  public void testConstructInvalidWidth() {
    IEditorCommand command = new EditorCheckerCommand("blue", "green", 1, 0, 1, 1);
  }

  // Tests the construction of an EditorCheckerCommand with an invalid height(less than 1)
  @Test(expected = IllegalArgumentException.class)
  public void testConstructInvalidHeight() {
    IEditorCommand command = new EditorCheckerCommand("blue", "green", 1, 1, 0, 1);
  }

  // Tests the construction of an EditorCheckerCommand with an invalid layer(less than 1)
  @Test(expected = IllegalArgumentException.class)
  public void testConstructInvalidLayer() {
    IEditorCommand command = new EditorCheckerCommand("blue", "green", 1, 1, 1, 0);
  }

  // Tests the execute method with a null model
  @Test(expected = IllegalArgumentException.class)
  public void testExecuteNullModel() {
    IEditorCommand command = new EditorCheckerCommand("blue", "blue", 1, 1, 1, 1);
    command.execute(null);
  }

  // Tests the execute method with two colors that are the same
  @Test(expected = IllegalArgumentException.class)
  public void testExecuteSameColor() {
    IEditorCommand command = new EditorCheckerCommand("blue", "blue", 1, 1, 1, 1);
    IMultiLayerEditorModel model = new MultiLayerEditorModel();
    command.execute(model);
  }

  // Tests the execute method with receiving an IAE from the model
  @Test(expected = IllegalArgumentException.class)
  public void testExecuteWithIAEFromModel() {
    IEditorCommand command = new EditorCheckerCommand("green", "blue", 1, 1, 1, 4);
    IMultiLayerEditorModel model = new MultiLayerEditorModel();
    command.execute(model);
  }

  // Tests the execute method that is properly executed
  @Test
  public void testExecuteValid() {
    IPixel b = new PixelImpl(0, 0, 255);
    IPixel r = new PixelImpl(255, 0, 0);
    IPixel[][] img = new IPixel[][]{
        {b, b, b, r, r, r, b, b, b},
        {b, b, b, r, r, r, b, b, b},
        {b, b, b, r, r, r, b, b, b},
        {r, r, r, b, b, b, r, r, r},
        {r, r, r, b, b, b, r, r, r},
        {r, r, r, b, b, b, r, r, r},
        {b, b, b, r, r, r, b, b, b},
        {b, b, b, r, r, r, b, b, b},
        {b, b, b, r, r, r, b, b, b}
    };
    IEditorCommand command = new EditorCheckerCommand("blue", "red", 3, 3, 3, 1);
    IMultiLayerEditorModel model = new MultiLayerEditorModel();
    command.execute(model);
    assertArrayEquals(img ,model.getSingleImage(0));
  }

}

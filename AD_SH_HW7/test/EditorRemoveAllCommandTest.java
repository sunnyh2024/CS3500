import controller.commands.EditorRemoveAllCommand;
import controller.commands.IEditorCommand;
import java.awt.Color;
import model.IMultiLayerEditorModel;
import model.ImageUtil;
import model.MultiLayerEditorModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the EditorRemoveAllCommand class and all dependencies: tests to ensure that
 * EditorRemoveAllCommand and all methods inside work correctly.
 */
public class EditorRemoveAllCommandTest {

  private IMultiLayerEditorModel model;

  @Before
  public void init() {
    model = new MultiLayerEditorModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteNullModel() {
    IEditorCommand cmd = new EditorRemoveAllCommand();
    cmd.execute(null);
  }

  @Test
  public void testExecuteSingleImageInModel() {
    model.addImage(ImageUtil.createChecker(Color.white, Color.black, 2, 2, 2), 0);
    IEditorCommand cmd = new EditorRemoveAllCommand();
    cmd.execute(model);
    assertEquals(0, model.getAllImages().size());
  }

  @Test
  public void testExecuteMultipleImagesInModel() {
    model.addImage(ImageUtil.createChecker(Color.white, Color.black, 2, 2, 2), 0);
    model.addImage(ImageUtil.createChecker(Color.black, Color.blue, 2, 2, 2), 0);
    model.addImage(ImageUtil.createChecker(Color.red, Color.pink, 2, 2, 2), 0);
    IEditorCommand cmd = new EditorRemoveAllCommand();
    cmd.execute(model);
    assertEquals(0, model.getAllImages().size());
  }
}

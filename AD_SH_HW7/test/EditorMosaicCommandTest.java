import controller.commands.EditorMosaicCommand;
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
 * Tests for the EditorMosaicCommand class and all dependencies: tests to ensure that
 * EditorMosaicCommand and all methods inside work correctly.
 */

public class EditorMosaicCommandTest {
  private IMultiLayerEditorModel model;

  @Before
  public void init() {
    model = new MultiLayerEditorModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSeedCt() {
    IEditorCommand cmd = new EditorMosaicCommand(-1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidLayer() {
    IEditorCommand cmd = new EditorMosaicCommand(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteNullModel() {
    IEditorCommand cmd = new EditorMosaicCommand(1, 1);
    cmd.execute(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidExecute() {
    IEditorCommand cmd = new EditorMosaicCommand(1000000, 1);
    IPixel[][] img = new PixelImpl[][]{};
    model.addImage(img, 0);
    cmd.execute(model);
  }

  @Test
  public void testValidExecute() {
    IEditorCommand cmd = new EditorMosaicCommand(1, 1);
    IPixel[][] img = ImageUtil.createChecker(Color.RED, Color.BLUE, 1, 4, 4);
    model.addImage(img, 0);
    IPixel p = new PixelImpl(127, 0 ,127);
    IPixel[][] result = new IPixel[][] {
        {p, p, p, p},
        {p, p, p, p},
        {p, p, p, p},
        {p, p, p, p}
    };
    cmd.execute(model);
    assertArrayEquals(result, model.getSingleImage(0));
  }
}

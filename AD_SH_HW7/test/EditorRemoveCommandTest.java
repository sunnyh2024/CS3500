import controller.commands.EditorRemoveCommand;
import controller.commands.IEditorCommand;
import java.awt.Color;
import model.IMultiLayerEditorModel;
import model.IPixel;
import model.ImageUtil;
import model.MultiLayerEditorModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the EditorRemoveAllCommand class and all dependencies: tests to ensure that
 * EditorRemoveAllCommand and all methods inside work correctly.
 */
public class EditorRemoveCommandTest {

  private IMultiLayerEditorModel model;

  @Before
  public void init() {
    model = new MultiLayerEditorModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadLayer() {
    IEditorCommand cmd = new EditorRemoveCommand(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteNullModel() {
    IEditorCommand cmd = new EditorRemoveCommand(1);
    cmd.execute(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteOutOfBoundLayer() {
    model.addImage(ImageUtil.createChecker(Color.white, Color.black, 2, 2, 2), 0);
    IEditorCommand cmd = new EditorRemoveCommand(2);
    cmd.execute(model);
  }

  @Test
  public void testExecuteSingleImageInModel() {
    model.addImage(ImageUtil.createChecker(Color.white, Color.black, 2, 2, 2), 0);
    IEditorCommand cmd = new EditorRemoveCommand(1);
    cmd.execute(model);
    assertEquals(0, model.getAllImages().size());
  }

  @Test
  public void testExecuteMultipleImagesInModel() {
    IPixel[][] img = ImageUtil.createChecker(Color.white, Color.black, 2, 2, 2);
    IPixel[][] img2 = ImageUtil.createChecker(Color.green, Color.blue, 2, 2, 2);
    IPixel[][] img3 = ImageUtil.createChecker(Color.black, Color.red, 2, 2, 2);
    model.addImage(img, 0);
    model.addImage(img2, 1);
    model.addImage(img3, 2);
    assertEquals(3, model.getAllImages().size());
    IEditorCommand cmd = new EditorRemoveCommand(1);
    cmd.execute(model);
    assertEquals(2, model.getAllImages().size());
    assertArrayEquals(model.getSingleImage(0), img2);
    IPixel[][] newImg = model.getSingleImage(1);
    for (int i = 0; i < newImg.length; i += 1) {
      for (int j = 0; j < newImg[0].length; j += 1) {
        assertEquals(newImg[i][j].getRGB()[0], img3[i][j].getRGB()[0], 10);
        assertEquals(newImg[i][j].getRGB()[1], img3[i][j].getRGB()[1], 10);
        assertEquals(newImg[i][j].getRGB()[2], img3[i][j].getRGB()[2], 10);
      }
    }
  }
}

import controller.commands.EditorCreateCommand;
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
 * Tests for the EditorCreateCommand class and all dependencies: tests to ensure that
 * EditorCreateCommand and all methods inside work correctly.
 */
public class EditorCreateCommandTest {

  private IMultiLayerEditorModel model;

  @Before
  public void init() {
    model = new MultiLayerEditorModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadLayer() {
    IEditorCommand cmd = new EditorCreateCommand("res/badFileName.png", -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullFilename() {
    IEditorCommand cmd = new EditorCreateCommand(null, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteBadFilename() {
    IEditorCommand cmd = new EditorCreateCommand("res/badFileName.png", 1);
    cmd.execute(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteNullModel() {
    IEditorCommand cmd = new EditorCreateCommand("res/amongus.png", 1);
    cmd.execute(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteInvalidLayerIndex() {
    IEditorCommand cmd = new EditorCreateCommand("res/amongus.png", 10);
    cmd.execute(model);
  }

  @Test
  public void testExecuteValidPPM() {
    IPixel[][] img = ImageUtil.createChecker(Color.red, Color.green, 10, 10, 10);
    ImageUtil.writePPM(img, "res/testCreateCommand.ppm");
    IEditorCommand cmd = new EditorCreateCommand("res/testCreateCommand.ppm", 1);
    cmd.execute(model);
    assertArrayEquals(model.getSingleImage(0), img);
  }

  @Test
  public void testExecuteValidPNG() {
    IPixel[][] img = ImageUtil.createChecker(Color.red, Color.green, 10, 10, 10);
    ImageUtil.exportPNG(img, "res/testCreateCommand.png");
    IEditorCommand cmd = new EditorCreateCommand("res/testCreateCommand.png", 1);
    cmd.execute(model);
    assertArrayEquals(model.getSingleImage(0), img);
  }

  @Test
  public void testExecuteValidJPG() {
    IPixel[][] img = ImageUtil.readImage("res/amongus.jpg");
    IEditorCommand cmd = new EditorCreateCommand("res/amongus.jpg", 1);
    cmd.execute(model);
    IPixel[][] newImg = model.getSingleImage(0);
    for (int i = 0; i < newImg.length; i += 1) {
      for (int j = 0; j < newImg[0].length; j += 1) {
        assertEquals(newImg[i][j].getRGB()[0], img[i][j].getRGB()[0], 10);
        assertEquals(newImg[i][j].getRGB()[1], img[i][j].getRGB()[1], 10);
        assertEquals(newImg[i][j].getRGB()[2], img[i][j].getRGB()[2], 10);
      }
    }
  }
}

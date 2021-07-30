import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import controller.commands.EditorExportCommand;
import controller.commands.IEditorCommand;
import java.awt.Color;
import model.IMultiLayerEditorModel;
import model.IPixel;
import model.ImageUtil;
import model.MultiLayerEditorModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the EditorExportCommand class and all dependencies: tests to ensure that
 * EditorExportCommand and all methods inside work correctly.
 */
public class EditorExportCommandTest {

  private IMultiLayerEditorModel model;

  @Before
  public void init() {
    model = new MultiLayerEditorModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadLayer() {
    IEditorCommand cmd = new EditorExportCommand("res/ReadDirTest", -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullFilename() {
    IEditorCommand cmd = new EditorExportCommand(null, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteBadFilename() {
    IEditorCommand cmd = new EditorExportCommand("res/testExportCommand.gif", 1);
    cmd.execute(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteNullModel() {
    IEditorCommand cmd = new EditorExportCommand("res/testExportCommand.png", 1);
    cmd.execute(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteInvalidLayerIndex() {
    IPixel[][] img = ImageUtil.createChecker(Color.red, Color.green, 10, 10, 10);
    IPixel[][] img2 = ImageUtil.createChecker(Color.red, Color.blue, 10, 10, 10);
    model.addImage(img, 0);
    model.addImage(img2, 1);
    new EditorExportCommand("res/testExportCommand.png", 10).execute(model);
  }

  @Test
  public void testExecuteValidPPM() {
    IPixel[][] img = ImageUtil.createChecker(Color.red, Color.green, 10, 10, 10);
    IPixel[][] img2 = ImageUtil.createChecker(Color.red, Color.blue, 10, 10, 10);
    model.addImage(img, 0);
    model.addImage(img2, 1);
    IEditorCommand cmd = new EditorExportCommand("res/testExportCommand.ppm", 1);
    cmd.execute(model);
    assertArrayEquals(ImageUtil.readPPM("res/testExportCommand.ppm"), img);
  }

  @Test
  public void testExecuteValidPNG() {
    IPixel[][] img = ImageUtil.createChecker(Color.red, Color.green, 10, 10, 10);
    IPixel[][] img2 = ImageUtil.createChecker(Color.red, Color.blue, 10, 10, 10);
    model.addImage(img, 0);
    model.addImage(img2, 1);
    IEditorCommand cmd = new EditorExportCommand("res/testExportCommand.png", 1);
    cmd.execute(model);
    assertArrayEquals(ImageUtil.readImage("res/testExportCommand.png"), img);
  }

  @Test
  public void testExecuteValidJPG() {
    IPixel[][] img = ImageUtil.readImage("res/amongus.png");
    IPixel[][] img2 = ImageUtil.readImage("res/amongus.png");
    model.addImage(img, 0);
    model.addImage(img2, 1);
    model.grayscale(1);
    img2 = model.getSingleImage(1);
    IEditorCommand cmd = new EditorExportCommand("res/testExportCommand.jpg", 2);
    cmd.execute(model);

    IPixel[][] newImg2 = ImageUtil.readImage("res/testExportCommand.jpg");
    for (int i = 0; i < newImg2.length; i += 1) {
      for (int j = 0; j < newImg2[0].length; j += 1) {
        assertEquals(newImg2[i][j].getRGB()[0], img2[i][j].getRGB()[0], 50);
        assertEquals(newImg2[i][j].getRGB()[1], img2[i][j].getRGB()[1], 50);
        assertEquals(newImg2[i][j].getRGB()[2], img2[i][j].getRGB()[2], 50);
      }
    }
  }
}

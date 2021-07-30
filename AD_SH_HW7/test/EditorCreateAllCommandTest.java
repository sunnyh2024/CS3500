import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import controller.commands.EditorCreateAllCommand;
import controller.commands.EditorExportAllCommand;
import controller.commands.EditorRemoveAllCommand;
import controller.commands.IEditorCommand;
import java.awt.Color;
import model.IMultiLayerEditorModel;
import model.IPixel;
import model.ImageUtil;
import model.MultiLayerEditorModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the EditorCreateAllCommand class and all dependencies: tests to ensure that
 * EditorCreateAllCommand and all methods inside work correctly.
 */
public class EditorCreateAllCommandTest {

  private IMultiLayerEditorModel model;

  @Before
  public void init() {
    model = new MultiLayerEditorModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadLayer() {
    IEditorCommand cmd = new EditorCreateAllCommand("res/ReadDirTest", -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullFilename() {
    IEditorCommand cmd = new EditorCreateAllCommand(null, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteBadFilename() {
    IEditorCommand cmd = new EditorCreateAllCommand("res/badFileName", 1);
    cmd.execute(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteNullModel() {
    IEditorCommand cmd = new EditorCreateAllCommand("res/amongus.png", 1);
    cmd.execute(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteInvalidLayerIndex() {
    IEditorCommand cmd = new EditorCreateAllCommand("res/amongus.png", 10);
    cmd.execute(model);
  }

  @Test
  public void testExecuteValidPPM() {
    IPixel[][] img = ImageUtil.createChecker(Color.red, Color.green, 10, 10, 10);
    IPixel[][] img2 = ImageUtil.createChecker(Color.red, Color.blue, 10, 10, 10);
    model = new MultiLayerEditorModel(img, img2);
    new EditorExportAllCommand("res/testCreateAllCommand", "ppm").execute(model);
    new EditorRemoveAllCommand().execute(model);
    IEditorCommand cmd = new EditorCreateAllCommand("res/testCreateAllCommand", 1);
    cmd.execute(model);
    assertArrayEquals(model.getSingleImage(0), img);
    assertArrayEquals(model.getSingleImage(1), img2);
  }

  @Test
  public void testExecuteValidPNG() {
    IPixel[][] img = ImageUtil.createChecker(Color.red, Color.green, 10, 10, 10);
    IPixel[][] img2 = ImageUtil.createChecker(Color.red, Color.blue, 10, 10, 10);
    model = new MultiLayerEditorModel(img, img2);
    new EditorExportAllCommand("res/testCreateAllCommand", "png").execute(model);
    new EditorRemoveAllCommand().execute(model);
    IEditorCommand cmd = new EditorCreateAllCommand("res/testCreateAllCommand", 1);
    cmd.execute(model);
    assertArrayEquals(model.getSingleImage(0), img);
    assertArrayEquals(model.getSingleImage(1), img2);
  }

  @Test
  public void testExecuteValidJPG() {
    IPixel[][] img = ImageUtil.readImage("res/amongus.png");
    IPixel[][] img2 = ImageUtil.readImage("res/amongus.png");
    model = new MultiLayerEditorModel(img, img2);
    model.grayscale(1);
    img2 = model.getSingleImage(1);

    new EditorExportAllCommand("res/testCreateAllCommand", "jpg").execute(model);
    new EditorRemoveAllCommand().execute(model);
    IEditorCommand cmd = new EditorCreateAllCommand("res/testCreateAllCommand", 1);
    cmd.execute(model);

    IPixel[][] newImg = model.getSingleImage(0);
    for (int i = 0; i < newImg.length; i += 1) {
      for (int j = 0; j < newImg[0].length; j += 1) {
        assertEquals(newImg[i][j].getRGB()[0], img[i][j].getRGB()[0], 85);
        assertEquals(newImg[i][j].getRGB()[1], img[i][j].getRGB()[1], 85);
        assertEquals(newImg[i][j].getRGB()[2], img[i][j].getRGB()[2], 85);
      }
    }
    IPixel[][] newImg2 = model.getSingleImage(1);
    for (int i = 0; i < newImg.length; i += 1) {
      for (int j = 0; j < newImg[0].length; j += 1) {
        assertEquals(newImg2[i][j].getRGB()[0], img2[i][j].getRGB()[0], 85);
        assertEquals(newImg2[i][j].getRGB()[1], img2[i][j].getRGB()[1], 85);
        assertEquals(newImg2[i][j].getRGB()[2], img2[i][j].getRGB()[2], 85);
      }
    }
  }
}

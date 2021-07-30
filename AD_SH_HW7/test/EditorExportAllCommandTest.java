import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import controller.commands.EditorCreateAllCommand;
import controller.commands.EditorExportAllCommand;
import controller.commands.IEditorCommand;
import java.awt.Color;
import model.IMultiLayerEditorModel;
import model.IPixel;
import model.ImageUtil;
import model.MultiLayerEditorModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the EditorExportAllCommand class and all dependencies: tests to ensure that
 * EditorExportAllCommand and all methods inside work correctly.
 */
public class EditorExportAllCommandTest {

  private IMultiLayerEditorModel model;

  @Before
  public void init() {
    model = new MultiLayerEditorModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullImageType() {
    IEditorCommand cmd = new EditorExportAllCommand("res/ReadDirTest", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullFilename() {
    IEditorCommand cmd = new EditorExportAllCommand(null, "png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteBadFilename() {
    IEditorCommand cmd = new EditorExportAllCommand("res/amongus.png", "png");
    cmd.execute(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteNullModel() {
    IEditorCommand cmd = new EditorExportAllCommand("res/amongus.png", "jpg");
    cmd.execute(null);
  }

  @Test
  public void testExecuteMtModel() {
    IEditorCommand cmd = new EditorExportAllCommand("res/testExportCommand", "png");
    cmd.execute(model);
    IEditorCommand cmd2 = new EditorCreateAllCommand("res/testExportCommand", 1);
    assertEquals(0, model.getAllImages().size());
  }

  @Test
  public void testExecuteValidPPM() {
    IPixel[][] img = ImageUtil.createChecker(Color.red, Color.green, 10, 10, 10);
    IPixel[][] img2 = ImageUtil.createChecker(Color.red, Color.blue, 10, 10, 10);
    model.addImage(img, 0);
    model.addImage(img2, 1);
    new EditorExportAllCommand("res/testExportCommand", "ppm").execute(model);
    assertArrayEquals(ImageUtil.readDirectory("res/testExportCommand").get(0), img);
    assertArrayEquals(ImageUtil.readDirectory("res/testExportCommand").get(1), img2);
  }

  @Test
  public void testExecuteValidPNG() {
    IPixel[][] img = ImageUtil.createChecker(Color.red, Color.green, 10, 10, 10);
    IPixel[][] img2 = ImageUtil.createChecker(Color.red, Color.blue, 10, 10, 10);
    model.addImage(img, 0);
    model.addImage(img2, 1);
    new EditorExportAllCommand("res/testExportCommand", "png").execute(model);
    assertArrayEquals(ImageUtil.readDirectory("res/testExportCommand").get(0), img);
    assertArrayEquals(ImageUtil.readDirectory("res/testExportCommand").get(1), img2);
  }

  @Test
  public void testExecuteValidJPG() {
    IPixel[][] img = ImageUtil.readImage("res/amongus.png");
    IPixel[][] img2 = ImageUtil.readImage("res/amongus.png");
    model.addImage(img, 0);
    model.addImage(img2, 1);
    model.grayscale(1);
    img2 = model.getSingleImage(1);
    new EditorExportAllCommand("res/testCreateAllCommand", "jpg").execute(model);

    IPixel[][] newImg = ImageUtil.readDirectory("res/testCreateAllCommand").get(0);
    for (int i = 0; i < newImg.length; i += 1) {
      for (int j = 0; j < newImg[0].length; j += 1) {
        assertEquals(newImg[i][j].getRGB()[0], img[i][j].getRGB()[0], 85);
        assertEquals(newImg[i][j].getRGB()[1], img[i][j].getRGB()[1], 85);
        assertEquals(newImg[i][j].getRGB()[2], img[i][j].getRGB()[2], 85);
      }
    }
    IPixel[][] newImg2 = ImageUtil.readDirectory("res/testCreateAllCommand").get(1);
    for (int i = 0; i < newImg2.length; i += 1) {
      for (int j = 0; j < newImg2[0].length; j += 1) {
        assertEquals(newImg2[i][j].getRGB()[0], img2[i][j].getRGB()[0], 40);
        assertEquals(newImg2[i][j].getRGB()[1], img2[i][j].getRGB()[1], 40);
        assertEquals(newImg2[i][j].getRGB()[2], img2[i][j].getRGB()[2], 40);
      }
    }
  }
}

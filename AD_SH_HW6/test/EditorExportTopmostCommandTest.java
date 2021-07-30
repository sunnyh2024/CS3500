import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import controller.commands.EditorExportTopmostCommand;
import controller.commands.IEditorCommand;
import java.awt.Color;
import model.IMultiLayerEditorModel;
import model.IPixel;
import model.ImageUtil;
import model.MultiLayerEditorModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the EditorExportTopmostCommand class and all dependencies: tests to ensure that
 * EditorExportTopmostCommand and all methods inside work correctly.
 */
public class EditorExportTopmostCommandTest {

  private IMultiLayerEditorModel model;

  @Before
  public void init() {
    model = new MultiLayerEditorModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullFilename() {
    IEditorCommand cmd = new EditorExportTopmostCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteBadFilename() {
    IEditorCommand cmd = new EditorExportTopmostCommand("res/testExportCommand.gif");
    cmd.execute(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteNullModel() {
    IEditorCommand cmd = new EditorExportTopmostCommand("res/testExportCommand.png");
    cmd.execute(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteMtModel() {
    IEditorCommand cmd = new EditorExportTopmostCommand("res/testExportCommand.png");
    cmd.execute(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteAllInvisibleLayers() {
    IPixel[][] img = ImageUtil.createChecker(Color.red, Color.white, 5, 5, 5);
    IPixel[][] img2 = ImageUtil.createChecker(Color.red, Color.black, 5, 5, 5);
    model = new MultiLayerEditorModel(img, img2);
    model.changeVisibility(0);
    model.changeVisibility(1);
    IEditorCommand cmd = new EditorExportTopmostCommand("res/testExportCommand.png");
    cmd.execute(model);
  }

  @Test
  public void testExecuteValidPPM() {
    IPixel[][] img = ImageUtil.createChecker(Color.red, Color.green, 10, 10, 10);
    IPixel[][] img2 = ImageUtil.createChecker(Color.red, Color.blue, 10, 10, 10);
    model = new MultiLayerEditorModel(img, img2);
    model.changeVisibility(1);
    IEditorCommand cmd = new EditorExportTopmostCommand("res/testExportTopmostCommand.ppm");
    cmd.execute(model);
    assertArrayEquals(ImageUtil.readPPM("res/testExportTopmostCommand.ppm"), img);
  }

  @Test
  public void testExecuteValidPNG() {
    IPixel[][] img = ImageUtil.createChecker(Color.red, Color.green, 10, 10, 10);
    IPixel[][] img2 = ImageUtil.createChecker(Color.red, Color.blue, 10, 10, 10);
    model = new MultiLayerEditorModel(img, img2);
    model.changeVisibility(0);
    IEditorCommand cmd = new EditorExportTopmostCommand("res/testExportTopmostCommand.png");
    cmd.execute(model);
    assertArrayEquals(ImageUtil.readImage("res/testExportTopmostCommand.png"), img2);
  }

  @Test
  public void testExecuteValidJPG() {
    IPixel[][] img = ImageUtil.readImage("res/amongus.jpg");
    IPixel[][] img2 = ImageUtil.createChecker(Color.red, Color.blue, 1, 481, 481);
    model = new MultiLayerEditorModel(img, img2);
    model.changeVisibility(1);
    new EditorExportTopmostCommand("res/testExportTopmostCommand.jpg");
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

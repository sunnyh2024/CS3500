import controller.commands.EditorGrayscaleCommand;
import controller.commands.IEditorCommand;
import model.IMultiLayerEditorModel;
import model.IPixel;
import model.ImageEditorModel;
import model.ImageUtil;
import model.MultiLayerEditorModel;
import model.SimpleImageEditorModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Tests for the EditorGrayscaleCommand class and all dependencies: tests to ensure that
 * EditorGrayscaleCommand and all methods inside work correctly.
 */
public class EditorGrayscaleCommandTest {

  private IMultiLayerEditorModel model;

  @Before
  public void init() {
    model = new MultiLayerEditorModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadLayer() {
    IEditorCommand cmd = new EditorGrayscaleCommand(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteNullModel() {
    IEditorCommand cmd = new EditorGrayscaleCommand(1);
    cmd.execute(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteValidModelWithIAE() {
    IEditorCommand cmd = new EditorGrayscaleCommand(1);
    cmd.execute(model);
  }

  @Test
  public void testExecuteValid() {
    IPixel[][] img = ImageUtil.readImage("res/amongus.png");
    ImageEditorModel simpleModel = new SimpleImageEditorModel(img);
    simpleModel.grayscale();
    IPixel[][] gray = simpleModel.getImage();
    model.addImage(img, 0);
    IEditorCommand cmd = new EditorGrayscaleCommand(1);
    cmd.execute(model);
    assertArrayEquals(model.getSingleImage(0), gray);
  }
}

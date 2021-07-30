import controller.GUIEditorController;
import controller.IFeatures;
import controller.commands.EditorExportAllCommand;
import controller.commands.EditorRemoveAllCommand;
import java.awt.Color;
import java.util.ArrayList;
import model.ECMultiLayerModel;
import model.IECMultiLayerModel;
import model.IPixel;
import model.ImageUtil;
import model.PixelImpl;
import org.junit.Before;
import org.junit.Test;
import view.IGUIView;
import view.SimpleGUIView;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the GUIEditorController class and all dependencies: tests to ensure that
 * GUIEditorController and all methods inside work correctly.
 */
public class GUIEditorControllerTest {

  Appendable ap;
  IGUIView view;
  IECMultiLayerModel model;
  IFeatures controller;

  @Before
  public void init() {
    this.ap = new StringBuilder();
    this.model = new ECMultiLayerModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructNullModel() {
    IGUIView view = new SimpleGUIView();
    IFeatures badController = new GUIEditorController(null, view);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testConstructNullView() {
    IFeatures badController = new GUIEditorController(this.model, null);
  }

  @Test
  public void testBlurStringLayerNotInt() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("dd");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.blur();
    assertEquals("Given input is not a valid integer", ap.toString());
  }

  @Test
  public void testInvalidBlur() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("0");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.blur();
    assertEquals("Index out of bounds.", ap.toString());
  }

  @Test
  public void testValidBlur() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("1");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    IPixel[][] img = new IPixel[][]{{new PixelImpl(200, 200, 200)}};
    IPixel[][] result = new IPixel[][]{{new PixelImpl(50, 50, 50)}};
    this.model.addImage(img, 0);
    this.controller.blur();
    assertArrayEquals(result, this.model.getSingleImage(0));
  }

  @Test
  public void testSharpenStringLayerNotInt() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("dd");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.sharpen();
    assertEquals("Given input is not a valid integer", ap.toString());
  }

  @Test
  public void testInvalidSharpen() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("0");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.sharpen();
    assertEquals("Index out of bounds.", ap.toString());
  }

  @Test
  public void testValidSharpen() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("1");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel g = new PixelImpl(137, 137, 137);
    IPixel[][] img = new IPixel[][]{
        {b, g, g, g, w},
        {g, b, g, w, g},
        {g, g, g, g, g},
        {g, w, g, b, g},
        {w, g, g, g, b}
    };
    IPixel one22 = new PixelImpl(122, 122, 122);
    IPixel one52 = new PixelImpl(152, 152, 152);
    IPixel one32 = new PixelImpl(132, 132, 132);
    IPixel two23 = new PixelImpl(223, 223, 223);
    IPixel one08 = new PixelImpl(108, 108, 108);
    IPixel[][] result = new IPixel[][]{
        {b, one22, one52, w, w},
        {one22, one08, two23, w, w},
        {one52, two23, one32, two23, one52},
        {w, w, two23, one08, one22},
        {w, w, one52, one22, b}
    };
    this.model.addImage(img, 0);
    this.controller.sharpen();
    assertArrayEquals(result, this.model.getSingleImage(0));
  }

  @Test
  public void testGrayscaleStringLayerNotInt() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("dd");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.grayscale();
    assertEquals("Given input is not a valid integer", ap.toString());
  }

  @Test
  public void testInvalidGrayscale() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("0");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.grayscale();
    assertEquals("Index out of bounds.", ap.toString());
  }

  @Test
  public void testValidGrayscale() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("1");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    IPixel[][] img = new PixelImpl[2][2];
    img[0][0] = new PixelImpl(100, 30, 60);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][0] = new PixelImpl(90, 60, 30);
    img[1][1] = new PixelImpl(250, 80, 60);
    IPixel[][] img2 = new PixelImpl[2][2];
    img2[0][0] = new PixelImpl(47, 47, 47);
    img2[0][1] = new PixelImpl(0, 0, 0);
    img2[1][0] = new PixelImpl(64, 64, 64);
    img2[1][1] = new PixelImpl(115, 115, 115);
    this.model.addImage(img, 0);
    this.controller.grayscale();
    assertArrayEquals(img2, this.model.getSingleImage(0));
  }

  @Test
  public void testSepiaStringLayerNotInt() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("dd");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.sepia();
    assertEquals("Given input is not a valid integer", ap.toString());
  }

  @Test
  public void testInvalidSepia() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("0");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.sepia();
    assertEquals("Index out of bounds.", ap.toString());
  }

  @Test
  public void testValidSepia() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("1");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    IPixel[][] img = ImageUtil.createChecker(Color.BLACK, Color.WHITE, 10, 10, 10);
    IPixel[][] img2 = ImageUtil.createChecker(Color.BLACK, new Color(255, 255, 239), 10, 10, 10);
    this.model.addImage(img, 0);
    this.controller.sepia();
    assertArrayEquals(img2, this.model.getSingleImage(0));
  }

  @Test
  public void testRemoveStringLayerNotInt() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("dd");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.remove();
    assertEquals("Given input is not a valid integer", ap.toString());
  }

  @Test
  public void testInvalidRemove() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("0");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.remove();
    assertEquals("Index out of bounds.", ap.toString());
  }

  @Test
  public void testValidRemove() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("1");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    IPixel[][] img = ImageUtil.createChecker(Color.BLACK, Color.WHITE, 10, 10, 10);
    this.model.addImage(img, 0);
    this.controller.remove();
    assertEquals(0, model.getNumLayers());
  }

  @Test
  public void testToggleVisibilityStringLayerNotInt() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("dd");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.toggleVisibility();
    assertEquals("Given input is not a valid integer", ap.toString());
  }

  @Test
  public void testInvalidToggleVisibility() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("0");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.toggleVisibility();
    assertEquals("Index out of bounds.", ap.toString());
  }

  @Test
  public void testValidToggleVisibility() {
    IPixel[][] img = ImageUtil.createChecker(Color.BLACK, Color.WHITE, 10, 10, 10);
    this.model.addImage(img, 0);
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("1");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.toggleVisibility();
    assertEquals(false, this.model.getLayerVisibility().get(0));
  }

  @Test
  public void testCopy1stStringLayerNotInt() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("dd");
    inputs.add("0");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.copy();
    assertEquals("Given input is not a valid integer", ap.toString());
  }

  @Test
  public void testCopy2ndStringLayerNotInt() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("0");
    inputs.add("dd");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.copy();
    assertEquals("Given input is not a valid integer", ap.toString());
  }

  @Test
  public void testInvalidCopy() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("0");
    inputs.add("0");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.copy();
    assertEquals("Index out of bounds.", ap.toString());
  }

  @Test
  public void testValidCopy() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("1");
    inputs.add("1");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    IPixel[][] img = ImageUtil.createChecker(Color.BLACK, Color.WHITE, 10, 10, 10);
    this.model.addImage(img, 0);
    this.controller.copy();
    assertArrayEquals(img, this.model.getSingleImage(0));
    assertArrayEquals(img, this.model.getSingleImage(1));
  }

  @Test
  public void testMosaic1stStringLayerNotInt() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("dd");
    inputs.add("0");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.mosaic();
    assertEquals("Given input is not a valid integer", ap.toString());
  }

  @Test
  public void testMosaic2ndStringLayerNotInt() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("0");
    inputs.add("dd");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.mosaic();
    assertEquals("Given input is not a valid integer", ap.toString());
  }

  @Test
  public void testInvalidMosaic() {
    IPixel[][] img = ImageUtil.createChecker(Color.RED, Color.BLUE, 1, 1, 1);
    this.model.addImage(img, 0);
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("-3");
    inputs.add("1");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.mosaic();
    assertEquals("Number of seeds should be at least 0 and layer should be at least 1",
        ap.toString());
  }

  @Test
  public void testDownscale1stStringLayerNotInt() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("dd");
    inputs.add("0");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.downscale();
    assertEquals("Given input is not a valid integer", ap.toString());
  }

  @Test
  public void testDownscale2ndStringLayerNotInt() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("0");
    inputs.add("dd");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.downscale();
    assertEquals("Given input is not a valid integer", ap.toString());
  }

  @Test
  public void testInvalidDownscale() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("-3");
    inputs.add("0");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.downscale();
    assertEquals("No visible image found", ap.toString());
  }

  @Test
  public void testValidDownscale() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("6");
    inputs.add("4");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    IPixel[][] start = ImageUtil.createChecker(Color.white, Color.black, 5, 2, 2);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel[][] expected = new IPixel[][]{
        {w, w, b, b},
        {w, w, b, b},
        {w, w, b, b},
        {b, b, w, w},
        {b, b, w, w},
        {b, b, w, w}
    };
    this.model.addImage(start, 0);
    this.controller.downscale();
    assertArrayEquals(expected, this.model.getSingleImage(0));
  }

  @Test
  public void testValidRemoveAll() {
    ArrayList<String> inputs = new ArrayList<String>();
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    IPixel[][] start = ImageUtil.createChecker(Color.white, Color.black, 5, 2, 2);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel[][] expected = new IPixel[][]{
        {w, w, b, b},
        {w, w, b, b},
        {w, w, b, b},
        {b, b, w, w},
        {b, b, w, w},
        {b, b, w, w}
    };
    this.model.addImage(start, 0);
    this.controller.removeAll();
    assertEquals(0, this.model.getAllImages().size());
  }

  @Test
  public void testLoadFile2ndStringLayerNotInt() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("0");
    inputs.add("dd");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.loadFile();
    assertEquals("Given input is not a valid integer", ap.toString());
  }

  @Test
  public void testInvalidLoadFile() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("dd");
    inputs.add("1");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.loadFile();
    assertEquals("Can't find given file\n", ap.toString());
  }

  @Test
  public void testValidLoadFile() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("res/testCreateCommand.ppm");
    inputs.add("1");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    IPixel[][] img = ImageUtil.createChecker(Color.red, Color.green, 10, 10, 10);
    ImageUtil.writePPM(img, "res/testCreateCommand.ppm");
    this.controller.loadFile();
    assertArrayEquals(img, this.model.getSingleImage(0));
  }

  @Test
  public void testLoadProject2ndStringLayerNotInt() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("0");
    inputs.add("dd");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.loadProject();
    assertEquals("Given input is not a valid integer", ap.toString());
  }

  @Test
  public void testInvalidLoadProject() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("dd");
    inputs.add("1");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.loadProject();
    assertEquals("Can't find given file.\n", ap.toString());
  }

  @Test
  public void testValidLoadProject() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("res/testLoadProject");
    inputs.add("1");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    IPixel[][] img = ImageUtil.createChecker(Color.red, Color.green, 10, 10, 10);
    IPixel[][] img2 = ImageUtil.createChecker(Color.red, Color.blue, 10, 10, 10);
    IECMultiLayerModel newModel = new ECMultiLayerModel(img, img2);
    new EditorExportAllCommand("res/testLoadProject", "png").execute(newModel);
    new EditorRemoveAllCommand().execute(newModel);
    this.controller.loadProject();
    assertArrayEquals(model.getSingleImage(0), img);
    assertArrayEquals(model.getSingleImage(1), img2);
  }

  @Test
  public void testChecker1stStringLayerNotInt() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("red");
    inputs.add("blue");
    inputs.add("dd");
    inputs.add("1");
    inputs.add("1");
    inputs.add("1");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.checker();
    assertEquals("At least one of the given inputs is not an integer", ap.toString());
  }

  @Test
  public void testChecker2ndStringLayerNotInt() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("red");
    inputs.add("blue");
    inputs.add("1");
    inputs.add("dd");
    inputs.add("1");
    inputs.add("1");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.checker();
    assertEquals("At least one of the given inputs is not an integer", ap.toString());
  }

  @Test
  public void testChecker3rdStringLayerNotInt() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("red");
    inputs.add("blue");
    inputs.add("1");
    inputs.add("1");
    inputs.add("dd");
    inputs.add("1");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.checker();
    assertEquals("At least one of the given inputs is not an integer", ap.toString());
  }

  @Test
  public void testChecker4thStringLayerNotInt() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("red");
    inputs.add("blue");
    inputs.add("1");
    inputs.add("1");
    inputs.add("1");
    inputs.add("dd");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.checker();
    assertEquals("At least one of the given inputs is not an integer", ap.toString());
  }

  @Test
  public void testInvalidChecker() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("dddd");
    inputs.add("blue");
    inputs.add("1");
    inputs.add("1");
    inputs.add("1");
    inputs.add("1");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.checker();
    assertEquals("Given color is not valid\n", ap.toString());
  }

  @Test
  public void testValidChecker() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("green");
    inputs.add("blue");
    inputs.add("1");
    inputs.add("1");
    inputs.add("1");
    inputs.add("1");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.checker();
    assertArrayEquals(ImageUtil.createChecker(Color.GREEN, Color.BLUE, 1, 1, 1),
        this.model.getSingleImage(0));
  }

  @Test
  public void testInvalidSave() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("green");
    inputs.add("blue");
    inputs.add("1");
    inputs.add("1");
    inputs.add("1");
    inputs.add("1");
    inputs.add("res");
    inputs.add("testSave.gif");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.checker();
    this.controller.save();
    assertEquals("Image type not supported.", this.ap.toString());
  }

  @Test
  public void testValidSave() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("green");
    inputs.add("blue");
    inputs.add("1");
    inputs.add("1");
    inputs.add("1");
    inputs.add("1");
    inputs.add("res");
    inputs.add("testSave.png");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.checker();
    this.controller.save();
    assertArrayEquals(ImageUtil.readImage("res/testSave.png"), this.model.getSingleImage(0));
  }

  @Test
  public void testInvalidSaveAll() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("res");
    inputs.add("amongus.png");
    inputs.add("png");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    this.controller.saveAll();
    assertEquals("Destination is not a valid filepath.", this.ap.toString());
  }

  @Test
  public void testValidSaveAll() {
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add("res");
    inputs.add("testSaveAll");
    inputs.add("png");
    this.view = new MockGUI(inputs, this.ap);
    this.controller = new GUIEditorController(this.model, this.view);
    IPixel[][] img = ImageUtil.createChecker(Color.red, Color.green, 10, 10, 10);
    IPixel[][] img2 = ImageUtil.createChecker(Color.red, Color.blue, 10, 10, 10);
    model.addImage(img, 0);
    model.addImage(img2, 1);
    this.controller.saveAll();
    assertArrayEquals(ImageUtil.readDirectory("res/testSaveAll").get(0), img);
    assertArrayEquals(ImageUtil.readDirectory("res/testSaveAll").get(1), img2);
  }
}

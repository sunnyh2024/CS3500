import controller.IEditorController;
import controller.SimpleEditorController;
import controller.commands.EditorBlurCommand;
import controller.commands.EditorChangeVisibilityCommand;
import controller.commands.EditorCheckerCommand;
import controller.commands.EditorCopyCommand;
import controller.commands.EditorCreateAllCommand;
import controller.commands.EditorCreateCommand;
import controller.commands.EditorExportAllCommand;
import controller.commands.EditorExportCommand;
import controller.commands.EditorExportTopmostCommand;
import controller.commands.EditorGrayscaleCommand;
import controller.commands.EditorRemoveAllCommand;
import controller.commands.EditorRemoveCommand;
import controller.commands.EditorSepiaCommand;
import controller.commands.EditorSharpenCommand;
import controller.commands.IEditorCommand;
import java.awt.Color;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.IMultiLayerEditorModel;
import model.IPixel;
import model.ImageEditorModel;
import model.ImageUtil;
import model.MultiLayerEditorModel;
import model.PixelImpl;
import model.SimpleImageEditorModel;
import model.Utils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Tests for the ImageUtil class and all dependencies: tests to ensure that ImageUtil and all
 * methods inside work correctly.
 */
public class ImageUtilsTest {

  private Map<String, Function<Scanner, IEditorCommand>> cmds;

  @Before
  public void init() {
    cmds = new HashMap<>();
    cmds.put("create", s -> new EditorCreateCommand(Utils.getNextString(s), Utils.getNextInt(s)));
    cmds.put("createall",
        s -> new EditorCreateAllCommand(Utils.getNextString(s), Utils.getNextInt(s)));
    cmds.put("checkerboard",
        s -> new EditorCheckerCommand(Utils.getNextString(s), Utils.getNextString(s),
            Utils.getNextInt(s), Utils.getNextInt(s), Utils.getNextInt(s), Utils.getNextInt(s)));
    cmds.put("copy", s -> new EditorCopyCommand(Utils.getNextInt(s), Utils.getNextInt(s)));
    cmds.put("export", s -> new EditorExportCommand(Utils.getNextString(s), Utils.getNextInt(s)));
    cmds.put("exportall",
        s -> new EditorExportAllCommand(Utils.getNextString(s), Utils.getNextString(s)));
    cmds.put("exporttopmost", s -> new EditorExportTopmostCommand(Utils.getNextString(s)));
    cmds.put("remove", s -> new EditorRemoveCommand(Utils.getNextInt(s)));
    cmds.put("removeall", s -> new EditorRemoveAllCommand());
    cmds.put("changevisibility", s -> new EditorChangeVisibilityCommand(Utils.getNextInt(s)));
    cmds.put("blur", s -> new EditorBlurCommand(Utils.getNextInt(s)));
    cmds.put("grayscale", s -> new EditorGrayscaleCommand(Utils.getNextInt(s)));
    cmds.put("sepia", s -> new EditorSepiaCommand(Utils.getNextInt(s)));
    cmds.put("sharpen", s -> new EditorSharpenCommand(Utils.getNextInt(s)));
  }

  // Tests the checkImageRect method with null array
  @Test(expected = IllegalArgumentException.class)
  public void testCheckImageNullRect() {
    ImageUtil.checkImageRect(null);
  }

  // Tests the checkImageRect method with a non-rectangular image(not all pixels are filled)
  @Test(expected = IllegalArgumentException.class)
  public void testCheckImageNotRect() {
    IPixel[][] img = new IPixel[2][2];
    img[0][0] = new PixelImpl(0, 0, 0);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][0] = new PixelImpl(0, 0, 0);
    ImageUtil.checkImageRect(img);
  }

  // Tests the checkImageRect method with a non-rectangular image(cascading array)
  @Test(expected = IllegalArgumentException.class)
  public void testCheckImageNotRectBadArray() {
    IPixel p = new PixelImpl(0, 0, 0);
    IPixel[][] img = new IPixel[][]{{p}, {p, p}, {p, p, p}};
    ImageUtil.checkImageRect(img);
  }

  // Tests the checkImageRect method with an empty image
  @Test(expected = IllegalArgumentException.class)
  public void testCheckImageRectEmptyImage() {
    IPixel p = new PixelImpl(0, 0, 0);
    IPixel[][] img = new IPixel[0][0];
    ImageUtil.checkImageRect(img);
  }

  @Test
  public void testCheckImageRectSinglePixel() {
    IPixel[][] img = new IPixel[][]{{new PixelImpl(40, 10, 255)}};
    boolean checkImageRect = true;
    try {
      ImageUtil.checkImageRect(img);
    } catch (IllegalArgumentException iae) {
      checkImageRect = false;
    }
    assertEquals(true, checkImageRect);
  }

  // Tests the checkImageRect method with a valid rectangular array
  @Test
  public void testCheckImageRectValid() {
    IPixel[][] img = new IPixel[2][2];
    img[0][0] = new PixelImpl(0, 0, 0);
    img[0][1] = new PixelImpl(0, 0, 0);
    img[1][0] = new PixelImpl(0, 0, 0);
    img[1][1] = new PixelImpl(0, 0, 0);
    boolean checkImageRect = true;
    try {
      ImageUtil.checkImageRect(img);
    } catch (IllegalArgumentException iae) {
      checkImageRect = false;
    }
    assertEquals(true, checkImageRect);
  }

  // Tests the checkImageRect method with a checkerboard
  @Test
  public void testCheckImageRectCheckerboardValid() {
    IPixel[][] img = ImageUtil
        .createChecker(new Color(0, 0, 204), new Color(255, 0, 127), 20, 10, 10);
    ImageUtil.checkImageRect(img);
    boolean checkImageRect = true;
    try {
      ImageUtil.checkImageRect(img);
    } catch (IllegalArgumentException iae) {
      checkImageRect = false;
    }
    assertEquals(true, checkImageRect);
  }

  // Tests the readPPM method with a null file name
  @Test(expected = IllegalArgumentException.class)
  public void testReadPPMNullFileName() {
    ImageUtil.readPPM(null);
  }

  // Tests the readPPM method with a non-existent file name
  @Test(expected = IllegalArgumentException.class)
  public void testReadPPMBadFileName() {
    ImageUtil.readPPM("res/nonExistentFile");
  }

  // Tests the readPPM method with a ppm file that is not P3
  @Test(expected = IllegalArgumentException.class)
  public void testReadPPMNotP3() {
    ImageUtil.readPPM("res/P6File.ppm");
  }

  // Tests the readPPM method with a valid ppm file
  @Test
  public void testReadPPMValid() {
    IPixel[][] write = ImageUtil.createChecker(Color.white, Color.black, 1, 3, 3);
    ImageUtil.writePPM(write, "res/readPPMTest.ppm");
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel[][] img = new IPixel[][]{
        {w, b, w},
        {b, w, b},
        {w, b, w}
    };
    assertArrayEquals(img, ImageUtil.readPPM("res/readPPMTest.ppm"));
  }

  // Tests the writePPm method with a null image
  @Test(expected = IllegalArgumentException.class)
  public void testWritePPMNullImage() {
    ImageUtil.writePPM(null, "res/testFile.ppm");
  }

  // Tests the writePPm method with a null destination
  @Test(expected = IllegalArgumentException.class)
  public void testWritePPMNullDest() {
    IPixel[][] img = ImageUtil.createChecker(Color.black, Color.white, 50, 5, 5);
    ImageUtil.writePPM(img, null);
  }

  // Tests the writePPm method with a valid destination and image
  @Test
  public void testWritePPMValid() {
    IPixel r = new PixelImpl(200, 0, 0);
    IPixel g = new PixelImpl(0, 200, 0);
    IPixel[][] img = new IPixel[][]{
        {g, r, g, r},
        {g, r, g, r},
        {r, g, r, g},
        {r, g, r, g}
    };
    ImageUtil.writePPM(img, "res/writePPMTest.ppm");
    assertArrayEquals(ImageUtil.readPPM("res/writePPMTest.ppm"), img);
  }

  // Testing that reading and writing a file multiple times does not change the file.
  @Test
  public void testReadWritePPMRepeat() {
    IPixel r = new PixelImpl(200, 0, 0);
    IPixel g = new PixelImpl(0, 200, 0);
    IPixel[][] img = new IPixel[][]{
        {g, r, g, r},
        {g, r, g, r},
        {r, g, r, g},
        {r, g, r, g}
    };
    ImageUtil.writePPM(img, "res/writePPMTest.ppm");
    assertArrayEquals(ImageUtil.readPPM("res/writePPMTest.ppm"), img);
    IPixel[][] newImg = ImageUtil.readPPM("res/writePPMTest.ppm");
    ImageUtil.writePPM(newImg, "res/writePPMTest.ppm");
    IPixel[][] finImg = ImageUtil.readPPM("res/writePPMTest.ppm");
    assertArrayEquals(finImg, img);
    assertArrayEquals(finImg, newImg);
  }

  // Tests reading a file, applying a filter to the image, writing to another file, and reading it
  // again to make sure it is expected
  @Test
  public void testReadWritePPMFilter() {
    IPixel r = new PixelImpl(200, 0, 0);
    IPixel g = new PixelImpl(0, 200, 0);
    IPixel[][] img = new IPixel[][]{
        {g, r, g, r},
        {g, r, g, r},
        {r, g, r, g},
        {r, g, r, g}
    };
    IPixel p1 = new PixelImpl(43, 43, 43);
    IPixel p2 = new PixelImpl(143, 143, 143);
    IPixel[][] img2 = new IPixel[][]{
        {p2, p1, p2, p1},
        {p2, p1, p2, p1},
        {p1, p2, p1, p2},
        {p1, p2, p1, p2}
    };

    ImageUtil.writePPM(img, "res/writePPMTest.ppm");
    ImageEditorModel model = new SimpleImageEditorModel(ImageUtil.readPPM("res/writePPMTest.ppm"));
    model.grayscale();
    ImageUtil.writePPM(model.getImage(), "res/writePPMTest.ppm");
    assertArrayEquals(img2, ImageUtil.readPPM("res/writePPMTest.ppm"));
  }

  // Testing createChecker throws exception when given a null color.
  @Test(expected = IllegalArgumentException.class)
  public void testCreateCheckerNullCol1() {
    ImageUtil.createChecker(null, Color.WHITE, 3, 5, 6);
  }

  // Testing createChecker throws exception when given a null color.
  @Test(expected = IllegalArgumentException.class)
  public void testCreateCheckerNullCol2() {
    ImageUtil.createChecker(Color.BLACK, null, 3, 5, 6);
  }

  // Testing createChecker throws exception when given invalid dimensions.
  @Test(expected = IllegalArgumentException.class)
  public void testCreateCheckerBadDimensions() {
    ImageUtil.createChecker(Color.BLACK, Color.WHITE, -7, 0, -1);
  }

  // Testing createChecker throws exception when given invalid dimensions.
  @Test(expected = IllegalArgumentException.class)
  public void testCreateCheckerSameColor() {
    ImageUtil.createChecker(Color.RED, Color.RED, 2, 3, 3);
  }

  // Tests createChecker creating a single-pixel image.
  @Test
  public void testCreateCheckSinglePixel() {
    IPixel[][] img = new IPixel[][]{{new PixelImpl(200, 0, 128)}};
    assertArrayEquals(ImageUtil.createChecker(new Color(200, 0, 128), Color.black, 1, 1, 1), img);
  }

  // Tests createChecker creating image with even dimensions for width and height.
  @Test
  public void testCreateCheckerEvenDimension() {
    IPixel b = new PixelImpl(0, 0, 200);
    IPixel r = new PixelImpl(200, 0, 0);
    IPixel[][] img = new IPixel[][]{
        {r, b, r, b, r, b},
        {b, r, b, r, b, r},
        {r, b, r, b, r, b},
        {b, r, b, r, b, r},
        {r, b, r, b, r, b},
        {b, r, b, r, b, r}
    };
    assertArrayEquals(ImageUtil.createChecker(new Color(200, 0, 0), new Color(0, 0, 200), 1, 6, 6),
        img);
  }

  // Tests createChecker creating image with odd dimensions for width and height.
  @Test
  public void testCreateCheckerOddDimension() {
    IPixel b = new PixelImpl(0, 0, 200);
    IPixel r = new PixelImpl(200, 0, 0);
    IPixel[][] img = new IPixel[][]{
        {r, b, r, b, r},
        {b, r, b, r, b},
        {r, b, r, b, r},
        {b, r, b, r, b},
        {r, b, r, b, r}
    };
    assertArrayEquals(ImageUtil.createChecker(new Color(200, 0, 0), new Color(0, 0, 200), 1, 5, 5),
        img);
  }

  // Tests createChecker with non-square dimensions for width and height.
  @Test
  public void testCreateCheckerNonSquare() {
    IPixel b = new PixelImpl(0, 0, 200);
    IPixel r = new PixelImpl(200, 0, 0);
    IPixel[][] img = new IPixel[][]{
        {r, b, r, b, r},
        {b, r, b, r, b},
        {r, b, r, b, r},
        {b, r, b, r, b},
        {r, b, r, b, r},
        {b, r, b, r, b},
        {r, b, r, b, r},
        {b, r, b, r, b},
        {r, b, r, b, r},
        {b, r, b, r, b}
    };
    assertArrayEquals(ImageUtil.createChecker(new Color(200, 0, 0), new Color(0, 0, 200), 1, 5, 10),
        img);
  }

  // Tests createChecker with a box size greater than 1.
  @Test
  public void testCreateCheckerLargerSize() {
    IPixel b = new PixelImpl(0, 0, 200);
    IPixel r = new PixelImpl(200, 0, 0);
    IPixel[][] img = new IPixel[][]{
        {b, b, b, r, r, r, b, b, b},
        {b, b, b, r, r, r, b, b, b},
        {b, b, b, r, r, r, b, b, b},
        {r, r, r, b, b, b, r, r, r},
        {r, r, r, b, b, b, r, r, r},
        {r, r, r, b, b, b, r, r, r},
        {b, b, b, r, r, r, b, b, b},
        {b, b, b, r, r, r, b, b, b},
        {b, b, b, r, r, r, b, b, b}
    };
    assertArrayEquals(ImageUtil.createChecker(new Color(0, 0, 200), new Color(200, 0, 0), 3, 3, 3),
        img);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadImageNullImage() {
    ImageUtil.readImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadImageBadPath() {
    ImageUtil.readImage("badImage.jpg");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadImageUnsupportedType() {
    ImageUtil.readImage("res/babyYoda.ppm");
  }

  @Test
  public void testReadImageValidPNG() {
    IPixel[][] write = ImageUtil.createChecker(Color.white, Color.black, 1, 3, 3);
    ImageUtil.exportPNG(write, "res/readPNGTest.png");
    IPixel b = new PixelImpl(0, 0, 0);
    IPixel w = new PixelImpl(255, 255, 255);
    IPixel[][] img = new IPixel[][]{
        {w, b, w},
        {b, w, b},
        {w, b, w}
    };
    assertArrayEquals(img, ImageUtil.readImage("res/readPNGTest.png"));
  }

  @Test
  public void testReadImageValidJPEG() {
    IPixel[][] write = ImageUtil.createChecker(Color.white, Color.black, 2, 4, 4);
    ImageUtil.exportJPEG(write, "res/readJPGTest.jpg");
    IPixel[][] newImg = ImageUtil.readImage("res/readJPGTest.jpg");

    for (int i = 0; i < newImg.length; i += 1) {
      for (int j = 0; j < newImg[0].length; j += 1) {
        assertEquals(newImg[i][j].getRGB()[0], write[i][j].getRGB()[0], 10);
        assertEquals(newImg[i][j].getRGB()[1], write[i][j].getRGB()[1], 10);
        assertEquals(newImg[i][j].getRGB()[2], write[i][j].getRGB()[2], 10);
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportJPEGNullImage() {
    ImageUtil.exportJPEG(null, "res/testFail");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportPNGNullImage() {
    ImageUtil.exportPNG(null, "res/testFail");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportJPEGBadPath() {
    IPixel[][] img = ImageUtil.createChecker(Color.BLACK, Color.WHITE, 3, 3, 3);
    ImageUtil.exportPNG(img, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportPNGBadPath() {
    IPixel[][] img = ImageUtil.createChecker(Color.BLACK, Color.WHITE, 3, 3, 3);
    ImageUtil.exportPNG(img, null);
  }

  @Test
  public void testReadExportJPEGRepeat() {
    IPixel[][] img = ImageUtil.readImage("res/amongus.png");
    ImageUtil.exportJPEG(img, "res/amongusExport.jpg");
    IPixel[][] newImg = ImageUtil.readImage("res/amongusExport.jpg");
    for (int i = 0; i < newImg.length; i += 1) {
      for (int j = 0; j < newImg[0].length; j += 1) {
        assertEquals(newImg[i][j].getRGB()[0], img[i][j].getRGB()[0], 85);
        assertEquals(newImg[i][j].getRGB()[1], img[i][j].getRGB()[1], 85);
        assertEquals(newImg[i][j].getRGB()[2], img[i][j].getRGB()[2], 85);
      }
    }
    ImageUtil.exportJPEG(newImg, "res/amongusExport.jpg");
    IPixel[][] finImg = ImageUtil.readImage("res/amongusExport.jpg");
    for (int i = 0; i < finImg.length; i += 1) {
      for (int j = 0; j < finImg[0].length; j += 1) {
        assertEquals(finImg[i][j].getRGB()[0], newImg[i][j].getRGB()[0], 85);
        assertEquals(finImg[i][j].getRGB()[1], newImg[i][j].getRGB()[1], 85);
        assertEquals(finImg[i][j].getRGB()[2], newImg[i][j].getRGB()[2], 85);
      }
    }
  }

  @Test
  public void testReadExportPNGRepeat() {
    IPixel r = new PixelImpl(200, 0, 0);
    IPixel g = new PixelImpl(0, 200, 0);
    IPixel[][] img = new IPixel[][]{
        {g, r, g, r},
        {g, r, g, r},
        {r, g, r, g},
        {r, g, r, g}
    };
    ImageUtil.exportPNG(img, "res/exportPNGTest.png");
    assertArrayEquals(ImageUtil.readImage("res/exportPNGTest.png"), img);
    IPixel[][] newImg = ImageUtil.readImage("res/exportPNGTest.png");
    ImageUtil.exportPNG(newImg, "res/exportPNGTest.png");
    IPixel[][] finImg = ImageUtil.readImage("res/exportPNGTest.png");
    assertArrayEquals(finImg, img);
    assertArrayEquals(finImg, newImg);
  }

  @Test
  public void testReadExportJPEGFiltered() {
    IPixel[][] img = ImageUtil.readImage("res/amongus.jpg");
    ImageEditorModel model = new SimpleImageEditorModel(img);
    model.grayscale();
    ImageUtil.exportJPEG(model.getImage(), "res/amongusExport.jpg");
    ImageUtil.exportPNG(model.getImage(), "res/amongusExport.png");
    IPixel[][] newImg = ImageUtil.readImage("res/amongusExport.jpg");
    IPixel[][] target = ImageUtil.readImage("res/amongusExport.png");
    for (int i = 0; i < newImg.length; i += 1) {
      for (int j = 0; j < newImg[0].length; j += 1) {
        assertEquals(newImg[i][j].getRGB()[0], target[i][j].getRGB()[0], 40);
        assertEquals(newImg[i][j].getRGB()[1], target[i][j].getRGB()[1], 40);
        assertEquals(newImg[i][j].getRGB()[2], target[i][j].getRGB()[2], 40);
      }
    }
  }

  @Test
  public void testReadExportPNGFiltered() {
    IPixel r = new PixelImpl(200, 0, 0);
    IPixel g = new PixelImpl(0, 200, 0);
    IPixel[][] img = new IPixel[][]{
        {g, r, g, r},
        {g, r, g, r},
        {r, g, r, g},
        {r, g, r, g}
    };
    IPixel p1 = new PixelImpl(43, 43, 43);
    IPixel p2 = new PixelImpl(143, 143, 143);
    IPixel[][] img2 = new IPixel[][]{
        {p2, p1, p2, p1},
        {p2, p1, p2, p1},
        {p1, p2, p1, p2},
        {p1, p2, p1, p2}
    };

    ImageUtil.exportPNG(img, "res/exportPNGTest.png");
    ImageEditorModel model = new SimpleImageEditorModel(ImageUtil
        .readImage("res/exportPNGTest.png"));
    model.grayscale();
    ImageUtil.exportPNG(model.getImage(), "res/exportPNGTest.png");
    assertArrayEquals(img2, ImageUtil.readImage("res/exportPNGTest.png"));
  }

  @Test
  public void testReadExportRepeatDifferentTypes() {
    IPixel[][] img = ImageUtil.readImage("res/amongus.png");
    ImageUtil.exportPNG(img, "res/amongusExport.png");
    assertArrayEquals(ImageUtil.readImage("res/amongusExport.png"), img);
    IPixel[][] newImg = ImageUtil.readImage("res/amongusExport.png");
    ImageUtil.exportJPEG(newImg, "res/amongusExport.jpg");
    IPixel[][] finImg = ImageUtil.readImage("res/amongusExport.jpg");
    for (int i = 0; i < finImg.length; i += 1) {
      for (int j = 0; j < finImg[0].length; j += 1) {
        assertEquals(finImg[i][j].getRGB()[0], img[i][j].getRGB()[0], 85);
        assertEquals(finImg[i][j].getRGB()[1], img[i][j].getRGB()[1], 85);
        assertEquals(finImg[i][j].getRGB()[2], img[i][j].getRGB()[2], 85);
      }
    }
    for (int i = 0; i < finImg.length; i += 1) {
      for (int j = 0; j < finImg[0].length; j += 1) {
        assertEquals(finImg[i][j].getRGB()[0], newImg[i][j].getRGB()[0], 85);
        assertEquals(finImg[i][j].getRGB()[1], newImg[i][j].getRGB()[1], 85);
        assertEquals(finImg[i][j].getRGB()[2], newImg[i][j].getRGB()[2], 85);
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadDirectoryNullFilename() {
    ImageUtil.readDirectory(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadDirectoryBadFilename() {
    ImageUtil.readDirectory("res/nonExistentDir");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadDirectoryImageFilename() {
    ImageUtil.readDirectory("res/amongus.png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadDirectoryBadFileInside() {
    ImageUtil.readDirectory("res/ReadDirBadFileTest");
  }

  @Test
  public void testReadDirectoryValid() {
    IPixel[][] img = ImageUtil.createChecker(Color.red, Color.black, 50, 4, 4);
    IPixel[][] img2 = ImageUtil.createChecker(Color.blue, Color.black, 50, 4, 4);
    StringReader reader = new StringReader("exportall res/ReadDirTest png removeall quit");
    Appendable append = new StringBuilder();
    IMultiLayerEditorModel model = new MultiLayerEditorModel(img, img2);
    IEditorController controller = new SimpleEditorController(model, reader, append, cmds);
    controller.startEditor();
    ArrayList<IPixel[][]> imports = ImageUtil.readDirectory("res/ReadDirTest");
    assertEquals(2, imports.size());
    assertArrayEquals(imports.get(0), img);
    assertArrayEquals(imports.get(1), img2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetVisibilitiesNullFilename() {
    ImageUtil.readDirectory(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetVisibilitiesBadFilename() {
    ImageUtil.readDirectory("res/nonExistentDir");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetVisibilitiesImageFilename() {
    ImageUtil.readDirectory("res/amongus.png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetVisibilitiesBadFileInside() {
    ImageUtil.readDirectory("res/BadTestDirectory");
  }

  @Test
  public void testGetVisibilitiesValid() {
    IPixel[][] img = ImageUtil.createChecker(Color.red, Color.black, 50, 4, 4);
    IPixel[][] img2 = ImageUtil.createChecker(Color.blue, Color.black, 50, 4, 4);
    StringReader reader = new StringReader(
        "changevisibility 1 exportall res/ReadDirTest png removeall quit");
    Appendable append = new StringBuilder();
    IMultiLayerEditorModel model = new MultiLayerEditorModel(img, img2);
    IEditorController controller = new SimpleEditorController(model, reader, append, cmds);
    controller.startEditor();
    ArrayList<Boolean> visibilities = ImageUtil.getVisibilities("res/ReadDirTest");
    assertEquals(2, visibilities.size());
    assertFalse(visibilities.get(0));
    assertTrue(visibilities.get(1));
  }
}

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
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.IMultiLayerEditorModel;
import model.IPixel;
import model.ImageUtil;
import model.MultiLayerEditorModel;
import model.Utils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the SimpleEditorController class and all dependencies: tests to ensure that
 * SimpleEditorController and all methods inside work correctly.
 */
public class SimpleEditorControllerTest {
  private IMultiLayerEditorModel model;
  private IEditorController control;
  private Appendable out;
  private Map<String, Function<Scanner, IEditorCommand>> cmds;

  @Before
  public void init() {
    this.model = new MultiLayerEditorModel();
    this.out = new StringBuilder();
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

  // Tests invalid init of controller when given a null model
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInitNullModel() {
    this.control = new SimpleEditorController(null,
        new StringReader(""), new StringBuilder(), cmds);
  }

  // Tests invalid init of controller when given a null readable
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInitNullReadable() {
    this.control = new SimpleEditorController(this.model,
        null, new StringBuilder(), cmds);
  }

  // Tests invalid init of controller when given a null appendable
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInitNullAppendable() {
    this.control = new SimpleEditorController(this.model,
        new StringReader(""), null, cmds);
  }

  // Tests invalid init of controller when given a null hashmap
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInitNullMap() {
    this.control = new SimpleEditorController(this.model,
        new StringReader(""), new StringBuilder(), null);
  }

  @Test
  public void testStartEditorImmediateQuit() {
    this.control = new SimpleEditorController(this.model,
        new StringReader("Quit"), this.out, this.cmds);
    this.control.startEditor();
    assertEquals("Quit Editor.", this.out.toString());
  }

  @Test
  public void testStartEditorQuitDuringCommand() {
    this.control = new SimpleEditorController(this.model,
        new StringReader("Create res/amongus.png quit 1"), this.out, this.cmds);
    this.control.startEditor();
    assertEquals("Next input is not an integer\nQuit Editor.", this.out.toString());
  }

  @Test
  public void testStartEditorQuitAfterValidCommand() {
    this.control = new SimpleEditorController(this.model,
        new StringReader("Create res/amongus.png 1 quit"), this.out, this.cmds);
    this.control.startEditor();
    assertEquals("Quit Editor.", this.out.toString());
    assertEquals(1, model.getAllImages().size());
  }

  @Test
  public void testStartEditorQuitAfterInvalidCommand() {
    this.control = new SimpleEditorController(this.model,
        new StringReader("blend layer 1 with layer 2 poggers QUIT"), this.out, this.cmds);
    this.control.startEditor();
    assertEquals("Could not parse command. Please try again.\n"
        + "Could not parse command. Please try again.\n"
        + "Could not parse command. Please try again.\n"
        + "Could not parse command. Please try again.\n"
        + "Could not parse command. Please try again.\n"
        + "Could not parse command. Please try again.\n"
        + "Could not parse command. Please try again.\nQuit Editor.", this.out.toString());
  }

  @Test
  public void testStartEditorBatchCommands() {
    IPixel[][] img = ImageUtil.createChecker(Color.red, Color.blue, 50, 4, 4);
    this.control = new SimpleEditorController(this.model,
        new StringReader("checkerboard red blue 50 4 4 1\n"
            + "checkerboard green yellow 50 4 4 2\n"
            + "grayscale 2\n"
            + "changevisibility 2\n"
            + "exporttopmost res/TopMost.png\n"
            + "quit"), this.out, this.cmds);
    this.control.startEditor();
    assertEquals("Quit Editor.", this.out.toString());
    IPixel[][] newImg = ImageUtil.readImage("res/TopMost.png");
    assertArrayEquals(newImg, img);
  }

  @Test(expected = IllegalStateException.class)
  public void testStartEditorRunOutOfInputs() {
    this.control = new SimpleEditorController(this.model,
        new StringReader(""), this.out, this.cmds);
    this.control.startEditor();
  }

  @Test
  public void testStartEditorHandleIAEFromCommand() {
    this.control = new SimpleEditorController(this.model,
        new StringReader("create res/amongus.png 2 quit"), this.out, this.cmds);
    this.control.startEditor();
    assertEquals("Index out of bounds.\n"
        + "Quit Editor.", this.out.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testStartEditorFailedWrite() {
    this.control = new SimpleEditorController(this.model,
        new StringReader("create res/amongus.png 2 quit"), new FakeAppendable(), this.cmds);
    this.control.startEditor();
  }
}

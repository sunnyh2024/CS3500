
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
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.IMultiLayerEditorModel;
import model.MultiLayerEditorModel;
import model.Utils;

/**
 * Main class that will be used to run the current editor with a text view and simple controller.
 * Currently, the editor supports the following functionality (with more information in USEME.md
 * file).
 * - creating/importing single images
 * - creating/importing multiple images from a directory previously created by this editor
 * - copying layers in the model
 * - creating checkerboard images
 * - blurring single layers
 * - sharpening single layer
 * - applying a grayscale filter to single layers
 * - applying a sepia filter to single layers
 * - changing the visibility of single layers
 * - removing single layers/images
 * - removing all the layers in the model
 * - exporting single layers
 * - exporting the topmost layer
 * - exporting all the layers in the model.
 */
public class ImageEditor {

  /**
   * Main method that serves as entry point for running this image editor program.
   *
   * @param args commands that will be used to use image editor
   */
  public static void main(String[] args) {
    Map<String, Function<Scanner, IEditorCommand>> cmds = new HashMap<>();
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
    Readable read;
    if (args.length > 0) {
      try {
        read = new FileReader(args[0]);
      } catch (IOException e) {
        return;
      }
    } else {
      read = new InputStreamReader(System.in);
    }
    IMultiLayerEditorModel model = new MultiLayerEditorModel();
    SimpleEditorController controller = new SimpleEditorController(model, read, System.out, cmds);
    controller.startEditor();
  }
}

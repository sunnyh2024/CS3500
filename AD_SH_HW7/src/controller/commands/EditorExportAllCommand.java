package controller.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import model.IMultiLayerEditorModel;
import model.IPixel;
import model.ImageUtil;
import model.Utils;

/**
 * Function object that represents a command that will be executed to export all the images in an
 * IMultiLayerEditorModel.
 */
public class EditorExportAllCommand implements IEditorCommand {

  private final String dest;
  private final String type;

  /**
   * Constructs an EditorExportAllCommand with the given destination and type of image.
   *
   * @param dest the destination folder that where all the images will be located
   * @param type the type of image that will be exported
   * @throws IllegalArgumentException if the dest or the type are invalid
   */
  public EditorExportAllCommand(String dest, String type) throws IllegalArgumentException {
    this.dest = Utils.requireNonNull(dest);
    this.type = Utils.requireNonNull(type).toLowerCase();
  }

  @Override
  public void execute(IMultiLayerEditorModel model) throws IllegalArgumentException {
    Utils.requireNonNull(model);
    File dir;
    File txt;
    FileWriter writer;
    try {
      dir = new File(dest);
      dir.mkdir();
      txt = new File(dest + "/layerLocations.txt");
      writer = new FileWriter(txt);
    } catch (InvalidPathException | IOException e) {
      throw new IllegalArgumentException("Destination is not a valid filepath.");
    }

    ArrayList<IPixel[][]> images = model.getAllImages();
    ArrayList<Boolean> visibles = model.getLayerVisibility();
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < images.size(); i += 1) {
      String filename;
      switch (type) {
        case "ppm":
          filename = String.format("%s/Layer%d.ppm", dest, i + 1);
          ImageUtil.writePPM(images.get(i), filename);
          break;
        case "jpeg":
        case "jpg":
          filename = String.format("%s/Layer%d.jpg", dest, i + 1);
          ImageUtil.exportJPEG(images.get(i), filename);
          break;
        case "png":
          filename = String.format("%s/Layer%d.png", dest, i + 1);
          ImageUtil.exportPNG(images.get(i), filename);
          break;
        default:
          throw new IllegalArgumentException("Image type not supported.");
      }
      builder.append(filename).append("\n");
      if (!visibles.get(i)) {
        builder.append("invisible\n");
      } else {
        builder.append("visible\n");
      }
    }

    try {
      writer.write(builder.toString());
      writer.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not write to writer.");
    }
  }
}

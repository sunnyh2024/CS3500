package controller.commands;

import java.awt.Color;
import java.lang.reflect.Field;
import model.IMultiLayerEditorModel;
import model.ImageUtil;
import model.Utils;

/**
 * Function object that represents a command that will be executed on an IMultiLayerEditorModel to
 * create a checkerboard layer at a given layer index.
 */
public class EditorCheckerCommand implements IEditorCommand {

  private final Color color1;
  private final Color color2;
  private final int size;
  private final int width;
  private final int height;
  private final int layer;

  /**
   * Constructs an EditorCheckerCommand with 2 given colors, a box size, and a checkerboard width
   * and height (in boxes).
   *
   * @param c1     the color that starts in the top left corner of the checkerboard
   * @param c2     the color that c1 will alternate with
   * @param size   the size of a checker box (in pixels)
   * @param width  the width of the checkerboard (in checker boxes)
   * @param height the height of the checkerboard (in checker boxes)
   * @param layer  the index that this checkerboard layer will be added at
   * @throws IllegalArgumentException if the layer index is not positive
   */
  public EditorCheckerCommand(String c1, String c2, int size, int width, int height, int layer)
      throws IllegalArgumentException {
    if (layer < 1) {
      throw new IllegalArgumentException("Layer must be greater than 1");
    }
    if (size < 1 || width < 1 || height < 1) {
      throw new IllegalArgumentException("Size, width, and height must be greater than 1");
    }
    // This solution was found on stackoverflow at https://stackoverflow.com/questions/3772098/how-does-java-awt-color-getcolorstring-colorname-work
    try {
      Field field1 = Color.class.getField(Utils.requireNonNull(c1));
      this.color1 = (Color) field1.get(null);
      Field field2 = Color.class.getField(Utils.requireNonNull(c2));
      this.color2 = (Color) field2.get(null);
    } catch (Exception e) {
      throw new IllegalArgumentException("Given color is not valid\n");
    }
    this.size = size;
    this.width = width;
    this.height = height;
    this.layer = layer - 1;
  }

  @Override
  public void execute(IMultiLayerEditorModel model) throws IllegalArgumentException {
    model.addImage(ImageUtil.createChecker(color1, color2, size, width, height), layer);
  }
}

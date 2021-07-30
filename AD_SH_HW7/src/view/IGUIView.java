package view;

import controller.IFeatures;
import java.awt.image.BufferedImage;
import model.IPixel;

/**
 * Represents a GUI view that is much more interactive than the ITextView. This GUI view will be
 * able to show the updated topmost image of the project that is being manipulated, as well as have
 * buttons that will perform the filtering and manipulations, rather than having the user type out a
 * command.
 */
public interface IGUIView extends ITextView {

  /**
   * converts this program's representation of images as a 2D array of pixels into a buffered
   * image.
   *
   * @param img the image that will be rendered
   * @throws IllegalArgumentException if the image is invalid
   */
  BufferedImage renderImage(IPixel[][] img) throws IllegalArgumentException;

  /**
   * updates the current image being shown by the editor to the image represented by the given 2D
   * array of pixels.
   *
   * @param img the image that will be replacing the current one being displayed by the editor
   * @throws IllegalArgumentException if the image is invalid
   */
  void updateImage(IPixel[][] img) throws IllegalArgumentException;

  /**
   * Updates the current layer text in the top left corner of the GUI based on the given layer.
   *
   * @param i the layer index
   * @throws IllegalArgumentException if the given int is negative
   */
  void setCurrentLayerText(int i) throws IllegalArgumentException;

  /**
   * Updates the number of layers text in the GUI's top left corner based on the given integer
   * input.
   *
   * @param i the number of layers
   * @throws IllegalArgumentException if the given int is negative
   */
  void setNumOfLayersText(int i) throws IllegalArgumentException;

  /**
   * Resets the focus back to the frame of the view.
   */

  void resetFocus();

  /**
   * Opens a new JOptionPane that asks for some sort of user input and returns that input as a
   * string.
   *
   * @param message the message that will be displayed on the panel
   * @return the user's input to the JOptionPane
   * @throws IllegalArgumentException if the message is invalid or the cancel button is pressed
   */
  String getInput(String message) throws IllegalArgumentException;

  /**
   * shows a preview of the image that will be manipulated on a separate JOptionPane.
   *
   * @param img     the image, represented as a 2D array of IPixels, that will be shown
   * @param message the message that will be displayed beside the image
   * @throws IllegalArgumentException if the image or message is invalid or the cancel button is
   *                                  pressed
   */
  void showPreview(IPixel[][] img, String message) throws IllegalArgumentException;

  /**
   * opens a file chooser that allows the user to select a file from their device and returns the
   * string representing that file's path.
   *
   * @param showFilter whether the chooser should only be allowed to select certain types of files
   * @return string representing the filepath of the selected file
   * @throws IllegalArgumentException if the cancel button is pressed
   */
  String fileOpenChooser(boolean showFilter, boolean directoryOnly) throws IllegalArgumentException;

  /**
   * opens a file chooser that allows the user to select a directory and returns the String
   * representing that directory's path.
   *
   * @return string representing the filepath of the selected directory
   */
  String fileSaveChooser();

  /**
   * Links the given actions to specific features using a map.
   *
   * @param features features that will be mapped to the views actions
   */
  void addFeatures(IFeatures features);
}

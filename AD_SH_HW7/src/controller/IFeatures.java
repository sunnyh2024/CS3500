package controller;

/**
 * represents all the features currently provided by the GUI Image Editor. These features will act
 * as the action listeners, and handle their respective processes in the GUI Image Editor.
 */
public interface IFeatures {

  /**
   * acts as an action listener that will handle the blurring process for single images in the GUI
   * view.
   */
  void blur();

  /**
   * acts as an action listener that will handle the sharpening process for single images in the GUI
   * view.
   */
  void sharpen();

  /**
   * acts as an action listener that will handle the grayscale process for single images in the GUI
   * view.
   */
  void grayscale();

  /**
   * acts as an action listener that will handle the sepia process for single layers in the GUI
   * view.
   */
  void sepia();

  /**
   * acts as an action listener that will handle the process of removing layers in the GUI view.
   */
  void remove();

  /**
   * acts as an action listener that will handle the process of toggling the visibility of a layer
   * in the GUI view.
   */
  void toggleVisibility();

  /**
   * acts as an action listener that will handle the process of copying layers in the GUI view.
   */
  void copy();

  /**
   * acts as an action listener that will handle the process of applying a mosaic filter to a layer
   * in the GUI view.
   */
  void mosaic();

  /**
   * acts as an action listener that will handle the process of downscaling the dimensions of the
   * layers in the project in the GUI view.
   */
  void downscale();

  /**
   * acts as an action listener that will handle the process of removing all layers in the project
   * in the GUI view.
   */
  void removeAll();

  /**
   * acts as an action listener that will handle the process of loading single images in the GUI
   * view.
   */
  void loadFile();

  /**
   * acts as an action listener that will handle the process of loading multiple images into the
   * project in the GUI view.
   */
  void loadProject();

  /**
   * acts as an action listener that will handle the process of creating a checkerboard layer in the
   * GUI view.
   */
  void checker();

  /**
   * acts as an action listener that will handle the process of saving the topmost layer in the
   * project in the GUI view.
   */
  void save();

  /**
   * acts as an action listener that will handle the process of saving all layers in a project in
   * the GUI view.
   */
  void saveAll();
}

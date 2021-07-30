package model;

/**
 * Represents a model for an image editor. The model will support various filters and other
 * image-processing capabilities that can be applied to images supplied.
 */
public interface ImageEditorModel {

  /**
   * Makes a deep copy of this model's image.
   *
   * @return deep copy of the image being edited
   */
  IPixel[][] getImage();

  /**
   * Applies a blur filter to this model's image.
   */
  void blur();

  /**
   * Applies a sharpen filter to this model's image.
   */
  void sharpen();

  /**
   * Applies a grayscale filter to this model's image.
   */
  void grayscale();

  /**
   * Applies a sepia filter to this model's image.
   */
  void sepia();
}

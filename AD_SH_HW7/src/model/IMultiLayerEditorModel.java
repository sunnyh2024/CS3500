package model;

import java.util.ArrayList;

/**
 * Represents a model for an image editor that is capable of dealing with multiple images at once
 * as image layers. The model will support various filters and other
 * image-processing capabilities that can be applied to each individual image layer. This model can
 * also turn images invisible when viewing for the user.
 */
public interface IMultiLayerEditorModel {

  /**
   * Makes a deep copy of all this model's images.
   *
   * @return deep copy of the all images being edited
   */
  ArrayList<IPixel[][]> getAllImages();

  /**
   * Makes a deep copy of the desired image in the model.
   *
   * @param index The desired index that corresponds to an image in the model
   * @return deep copy of the image being edited
   * @throws IllegalArgumentException for an invalid index
   */
  IPixel[][] getSingleImage(int index) throws IllegalArgumentException;

  /**
   * Finds the top most image(the highest image that is visible).
   *
   * @return deep copy of the top most image
   * @throws IllegalArgumentException if there is no top most image
   */
  IPixel[][] getTopMostImage() throws IllegalArgumentException;

  /**
   * Makes a deep copy of the arraylist of booleans that represent whether each layer is visible or
   * not.
   *
   * @return an arraylist of booleans that signify whether each layer is visible or not
   */
  ArrayList<Boolean> getLayerVisibility();

  /**
   * Applies a blur filter to this model's image at the given layer (starting from 0).
   *
   * @param index The desired index that corresponds to an image in the model
   * @throws IllegalArgumentException for an invalid index
   */
  void blur(int index) throws IllegalArgumentException;

  /**
   * Applies a sharpen filter to this model's image at the given layer (starting from 0).
   *
   * @param index The desired index that corresponds to an image in the model
   * @throws IllegalArgumentException for an invalid index
   */
  void sharpen(int index) throws IllegalArgumentException;

  /**
   * Applies a grayscale filter to this model's image at the given layer (starting from 0).
   *
   * @param index The desired index that corresponds to an image in the model
   * @throws IllegalArgumentException for an invalid index
   */
  void grayscale(int index) throws IllegalArgumentException;

  /**
   * Applies a sepia filter to this model's image at the given layer (starting from 0).
   *
   * @param index The desired index that corresponds to an image in the model
   * @throws IllegalArgumentException for an invalid index
   */
  void sepia(int index) throws IllegalArgumentException;

  /**
   * Adds the given image at the layer at the given index of the model.
   *
   * @param image The desired image which is a 2D array of pixels to be added to the top layer
   * @param index The desired index of where to insert the image
   * @throws IllegalArgumentException if the given image is invalid
   */
  void addImage(IPixel[][] image, int index) throws IllegalArgumentException;

  /**
   * Removes the desired image based on the given index.
   *
   * @param index The desired index that corresponds to an image in the model
   * @throws IllegalArgumentException for an invalid index
   */
  void removeImage(int index) throws IllegalArgumentException;

  /**
   * Copies an image from the layer corresponding index and inserts it at the given index.
   *
   * @param copyIndex The desired index that corresponds to an image in the model
   * @param destIndex The desired index of where to place the image in the model
   * @throws IllegalArgumentException if at least one of the given indices is invalid
   */
  void copyImage(int copyIndex, int destIndex) throws IllegalArgumentException;

  /**
   * Changes the visibility of the layer at the specified index.
   *
   * @param index The desired index that corresponds to an image in the model
   * @throws IllegalArgumentException for an invalid index
   */
  void changeVisibility(int index) throws IllegalArgumentException;
}

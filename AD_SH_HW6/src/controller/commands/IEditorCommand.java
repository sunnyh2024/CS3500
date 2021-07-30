package controller.commands;

import model.IMultiLayerEditorModel;

/**
 * Represents a command function object that uses an IMultiLayerEdtiorModel to execute an operation.
 * Currently, this interface does not work if executed on a ImageEditorModel.
 */
public interface IEditorCommand {

  /**
   * Executes an operation on the model. The current implementations of IEditorCommand gives
   * functionality for:
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
   *
   * @param model The IMultiLayerEditorModel that is used for its operations
   * @throws IllegalArgumentException if the given model is null or the operation cannot be
   *                                  executed
   */
  void execute(IMultiLayerEditorModel model) throws IllegalArgumentException;
}

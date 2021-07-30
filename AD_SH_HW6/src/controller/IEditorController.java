package controller;

/**
 * Represents a controller for an image editor that will take in commands from the user and feed
 * them to the model to execute. This controller only supports IMultiLayerEditorModels (Other models
 * in this project, such as ImageEditorModels, are not supported).
 */
public interface IEditorController {

  /**
   * Starts the editor and allows the user to start inputting commands into the terminal. The list
   * of supported commands what each of them do are available in the USEME.md file in the main
   * project dir.
   *
   * @throws IllegalStateException if writing to the Appendable object used by it fails or reading
   *                               from the provided Readable fails
   */
  void startEditor() throws IllegalStateException;
}

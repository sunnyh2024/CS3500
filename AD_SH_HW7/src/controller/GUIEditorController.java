package controller;

import controller.commands.EditorBlurCommand;
import controller.commands.EditorChangeVisibilityCommand;
import controller.commands.EditorCheckerCommand;
import controller.commands.EditorCopyCommand;
import controller.commands.EditorCreateAllCommand;
import controller.commands.EditorCreateCommand;
import controller.commands.EditorDownscaleCommand;
import controller.commands.EditorExportAllCommand;
import controller.commands.EditorExportTopmostCommand;
import controller.commands.EditorGrayscaleCommand;
import controller.commands.EditorMosaicCommand;
import controller.commands.EditorRemoveAllCommand;
import controller.commands.EditorRemoveCommand;
import controller.commands.EditorSepiaCommand;
import controller.commands.EditorSharpenCommand;
import java.io.IOException;
import model.ECMultiLayerModel;
import model.IECMultiLayerModel;
import model.IPixel;
import model.PixelImpl;
import model.Utils;
import view.IGUIView;
import view.SimpleGUIView;

/**
 * To represent a controller that takes input from the user through a GUI Viewto control the editor
 * model. This controller can only support features that exist in IECMultiLayerModel and cannot
 * support IMultiLayerEditorModel and SimpleImageEditorModel.
 */
public class GUIEditorController implements IFeatures {

  private final IECMultiLayerModel model;
  private final IGUIView view;

  /**
   * Constructs a GUIEditorController with an EC model and a IGUIView.
   *
   * @param model model that will handle image transformations and manipulations
   * @param view  graphical view that will display the current state of the editor
   * @throws IllegalArgumentException if the model or view are invalid
   */
  public GUIEditorController(IECMultiLayerModel model, IGUIView view) {
    this.model = Utils.requireNonNull(model);
    this.view = Utils.requireNonNull(view);
    view.addFeatures(this);
  }

  /**
   * Convenience constructor that constructs a GUIEditorController with an ECMultiLayerModel input.
   */
  public GUIEditorController(IECMultiLayerModel model) {
    this(model, new SimpleGUIView());
  }

  /**
   * Convenience constructor that constructs a GUIEditorController with an ECMultiLayerModel and
   * SimpleGUIView.
   */
  public GUIEditorController() {
    this(new ECMultiLayerModel(), new SimpleGUIView());
  }

  @Override
  public void blur() {
    String layer;
    try {
      layer = view.getInput("Enter the layer to blur");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    int i;
    try {
      i = Integer.parseInt(layer);
    } catch (NumberFormatException nfe) {
      this.renderMessage("Given input is not a valid integer");
      return;
    }
    try {
      view.showPreview(model.getSingleImage(i - 1), "Selected image. Click OK to proceed");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    try {
      new EditorBlurCommand(i).execute(this.model);
    } catch (IllegalArgumentException iae) {
      this.renderMessage(iae.getMessage());
      return;
    }
    view.updateImage(model.getTopMostImage());
    view.resetFocus();
  }

  @Override
  public void sharpen() {
    String layer;
    try {
      layer = view.getInput("Enter the layer to sharpen");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    int i;
    try {
      i = Integer.parseInt(layer);
    } catch (NumberFormatException nfe) {
      this.renderMessage("Given input is not a valid integer");
      return;
    }
    try {
      view.showPreview(model.getSingleImage(i - 1), "Selected image. Click OK to proceed");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    try {
      new EditorSharpenCommand(i).execute(this.model);
    } catch (IllegalArgumentException iae) {
      this.renderMessage(iae.getMessage());
      return;
    }
    view.updateImage(model.getTopMostImage());
    view.resetFocus();
  }

  @Override
  public void grayscale() {
    String layer;
    try {
      layer = view.getInput("Enter the layer to grayscale");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    int i;
    try {
      i = Integer.parseInt(layer);
    } catch (NumberFormatException nfe) {
      this.renderMessage("Given input is not a valid integer");
      return;
    }
    try {
      view.showPreview(model.getSingleImage(i - 1), "Selected image. Click OK to proceed");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    try {
      new EditorGrayscaleCommand(i).execute(this.model);
    } catch (IllegalArgumentException iae) {
      this.renderMessage(iae.getMessage());
      return;
    }
    view.updateImage(model.getTopMostImage());
    view.resetFocus();
  }

  @Override
  public void sepia() {
    String layer;
    try {
      layer = view.getInput("Enter the layer to sepia");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    int i;
    try {
      i = Integer.parseInt(layer);
    } catch (NumberFormatException nfe) {
      this.renderMessage("Given input is not a valid integer");
      return;
    }
    try {
      view.showPreview(model.getSingleImage(i - 1), "Selected image. Click OK to proceed");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    try {
      new EditorSepiaCommand(i).execute(this.model);
    } catch (IllegalArgumentException iae) {
      this.renderMessage(iae.getMessage());
      return;
    }
    view.updateImage(model.getTopMostImage());
    view.resetFocus();
  }

  @Override
  public void remove() {
    String layer;
    try {
      layer = view.getInput("Enter the layer to remove");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    int i;
    try {
      i = Integer.parseInt(layer);
    } catch (NumberFormatException nfe) {
      this.renderMessage("Given input is not a valid integer");
      return;
    }
    try {
      view.showPreview(model.getSingleImage(i - 1), "Selected image. Click OK to proceed");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    try {
      new EditorRemoveCommand(i).execute(this.model);
    } catch (IllegalArgumentException iae) {
      this.renderMessage(iae.getMessage());
      return;
    }
    try {
      view.updateImage(model.getTopMostImage());
    } catch (IllegalArgumentException e) {
      view.updateImage(new IPixel[][] {{new PixelImpl(0, 0, 0)}});
    }
    view.resetFocus();
  }

  @Override
  public void toggleVisibility() {
    String layer;
    try {
      layer = view.getInput("Enter the layer to toggle visibility");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    int i;
    try {
      i = Integer.parseInt(layer);
    } catch (NumberFormatException nfe) {
      this.renderMessage("Given input is not a valid integer");
      return;
    }
    try {
      view.showPreview(model.getSingleImage(i - 1), "Selected image. Click OK to proceed");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    try {
      new EditorChangeVisibilityCommand(i).execute(this.model);
    } catch (IllegalArgumentException iae) {
      this.renderMessage(iae.getMessage());
      return;
    }
    try {
      view.updateImage(model.getTopMostImage());
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    view.setCurrentLayerText(model.getTopLayerIndex() + 1);
    view.resetFocus();
  }

  @Override
  public void copy() {
    String src;
    String dest;
    try {
      src = view.getInput("Enter the source layer to copy");
      dest = view.getInput("Enter the destination layer");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    int i1;
    int i2;
    try {
      i1 = Integer.parseInt(src);
      i2 = Integer.parseInt(dest);
    } catch (NumberFormatException nfe) {
      this.renderMessage("Given input is not a valid integer");
      return;
    }
    try {
      view.showPreview(model.getSingleImage(i1 - 1), "Selected image. Click OK to proceed");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    try {
      new EditorCopyCommand(i1, i2).execute(this.model);
    } catch (IllegalArgumentException iae) {
      this.renderMessage(iae.getMessage());
      return;
    }
    view.updateImage(model.getTopMostImage());
    view.setNumOfLayersText(model.getNumLayers());
    view.setCurrentLayerText(model.getTopLayerIndex() + 1);
    view.resetFocus();
  }

  @Override
  public void mosaic() {
    String seedCount;
    String layer;
    try {
      seedCount = view.getInput("Enter the desired seed count");
      layer = view.getInput("Enter the layer to apply mosaic");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    int i1;
    int i2;
    try {
      i1 = Integer.parseInt(seedCount);
      i2 = Integer.parseInt(layer);
    } catch (NumberFormatException nfe) {
      this.renderMessage("Given input is not a valid integer");
      return;
    }
    try {
      view.showPreview(model.getSingleImage(i2 - 1), "Selected image. Click OK to proceed");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    try {
      new EditorMosaicCommand(i1, i2 - 1).execute(model);
    } catch (IllegalArgumentException iae) {
      this.renderMessage(iae.getMessage());
      return;
    }
    view.updateImage(model.getTopMostImage());
    view.resetFocus();
  }

  @Override
  public void downscale() {
    String removeWidth;
    String removeHeight;
    try {
      removeWidth = view.getInput("Enter the number of pixels to remove from width");
      removeHeight = view.getInput("Enter the number of pixels to remove from height");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    int i1;
    int i2;
    try {
      i1 = Integer.parseInt(removeWidth);
      i2 = Integer.parseInt(removeHeight);
    } catch (NumberFormatException nfe) {
      this.renderMessage("Given input is not a valid integer");
      return;
    }
    try {
      view.showPreview(model.getTopMostImage(), "New size. Click OK to proceed");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    try {
      new EditorDownscaleCommand(i1, i2).execute(model);
    } catch (IllegalArgumentException iae) {
      this.renderMessage(iae.getMessage());
      return;
    }
    view.updateImage(model.getTopMostImage());
    view.resetFocus();
  }

  @Override
  public void removeAll() {
    try {
      new EditorRemoveAllCommand().execute(this.model);
    } catch (IllegalArgumentException iae) {
      this.renderMessage(iae.getMessage());
    }
    view.updateImage(new IPixel[][]{{new PixelImpl(0, 0, 0)}});
    view.setNumOfLayersText(model.getNumLayers());
    view.setCurrentLayerText(model.getTopLayerIndex() + 1);
    view.resetFocus();
  }

  @Override
  public void loadFile() {
    String filename;
    String layer;
    try {
      filename = view.fileOpenChooser(true, false);
      layer = view.getInput("Enter the layer where this file will be added");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    int i;
    try {
      i = Integer.parseInt(layer);
    } catch (NumberFormatException nfe) {
      this.renderMessage("Given input is not a valid integer");
      return;
    }
    try {
      new EditorCreateCommand(filename, i).execute(this.model);
    } catch (IllegalArgumentException iae) {
      this.renderMessage(iae.getMessage());
      return;
    }
    view.updateImage(model.getTopMostImage());
    view.setNumOfLayersText(model.getNumLayers());
    view.setCurrentLayerText(model.getTopLayerIndex() + 1);
    view.resetFocus();
  }

  @Override
  public void loadProject() {
    String filename;
    String layer;
    try {
      filename = view.fileOpenChooser(false, true);
      layer = view.getInput("Enter the layer where this project will be added");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    int i;
    try {
      i = Integer.parseInt(layer);
    } catch (NumberFormatException nfe) {
      this.renderMessage("Given input is not a valid integer");
      return;
    }
    try {
      new EditorCreateAllCommand(filename, i).execute(this.model);
    } catch (IllegalArgumentException iae) {
      this.renderMessage(iae.getMessage());
      return;
    }
    view.updateImage(model.getTopMostImage());
    view.setNumOfLayersText(model.getNumLayers());
    view.setCurrentLayerText(model.getTopLayerIndex() + 1);
    view.resetFocus();
  }

  @Override
  public void checker() {
    String c1;
    String c2;
    String size;
    String width;
    String height;
    String layer;
    try {
      c1 = view.getInput("Enter the first color");
      c2 = view.getInput("Enter the second color");
      size = view.getInput("Enter the box size");
      width = view.getInput("Enter the checkerboard width");
      height = view.getInput("Enter the checkerboard height");
      layer = view.getInput("Enter the layer to add this checkerboard to");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    int i1;
    int i2;
    int i3;
    int i4;
    try {
      i1 = Integer.parseInt(size);
      i2 = Integer.parseInt(width);
      i3 = Integer.parseInt(height);
      i4 = Integer.parseInt(layer);
    } catch (NumberFormatException nfe) {
      this.renderMessage("At least one of the given inputs is not an integer");
      return;
    }
    try {
      new EditorCheckerCommand(c1, c2, i1, i2, i3, i4).execute(this.model);
    } catch (IllegalArgumentException iae) {
      this.renderMessage(iae.getMessage());
      return;
    }
    view.updateImage(model.getTopMostImage());
    view.setNumOfLayersText(model.getNumLayers());
    view.setCurrentLayerText(model.getTopLayerIndex() + 1);
    view.resetFocus();
  }

  @Override
  public void save() {
    String dest;
    String filename;
    try {
      dest = view.fileSaveChooser();
      filename = view.getInput("Enter the name the file will be saved as");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    try {
      new EditorExportTopmostCommand(dest + "/" + filename).execute(this.model);
    } catch (IllegalArgumentException iae) {
      this.renderMessage(iae.getMessage());
    }
    view.resetFocus();
  }

  @Override
  public void saveAll() {
    String dest;
    String projectName;
    String fileType;
    try {
      dest = view.fileSaveChooser();
      projectName = view.getInput("Enter the name of the project");
      fileType = view.getInput("Enter the filetype the images will be exported as");
    } catch (IllegalArgumentException e) {
      this.renderMessage(e.getMessage());
      return;
    }
    try {
      new EditorExportAllCommand(dest + "/" + projectName, fileType).execute(this.model);
    } catch (IllegalArgumentException iae) {
      this.renderMessage(iae.getMessage());
    }
    view.resetFocus();
  }

  /**
   * Renders the message to the Appendable message through the view and handles the IOExcpetion.
   *
   * @param message The message to be written to the Appendable object
   * @throws IllegalStateException when the given message fails to write to the Appendable object
   */
  private void renderMessage(String message) throws IllegalStateException {
    try {
      this.view.renderMessage(message);
    } catch (IOException ie) {
      throw new IllegalStateException("Message failed to write to Appendable object");
    }
  }
}

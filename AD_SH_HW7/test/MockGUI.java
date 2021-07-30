import controller.IFeatures;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import model.IPixel;
import view.IGUIView;

/**
 * Represents an implementation IGUIView that is solely used for testing.
 */
public class MockGUI implements IGUIView {

  Appendable ap;
  private ArrayList<String> inputs;
  private int currentInput = 0;

  /**
   * Constructs a MockGUi with the given appendable.
   *
   * @param ap Appendable to add messages to
   */
  public MockGUI(Appendable ap) {
    this.ap = ap;
  }

  public MockGUI(ArrayList<String> inputs, Appendable ap) {
    this.inputs = inputs;
    this.ap = ap;
  }

  @Override
  public BufferedImage renderImage(IPixel[][] img) throws IllegalArgumentException {
    return new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
  }

  @Override
  public void updateImage(IPixel[][] img) throws IllegalArgumentException {
    // does nothing
  }

  @Override
  public void setCurrentLayerText(int i) throws IllegalArgumentException {
    // does nothing
  }

  @Override
  public void setNumOfLayersText(int i) throws IllegalArgumentException {
    // does nothing
  }

  @Override
  public void resetFocus() {
    // does nothing
  }

  @Override
  public String getInput(String message) {
    String string = this.inputs.get(currentInput);
    this.currentInput ++;
    return string;
  }

  @Override
  public void showPreview(IPixel[][] img, String message) throws IllegalArgumentException {
    // does nothing
  }

  @Override
  public String fileOpenChooser(boolean addFilter, boolean directoryOnly) {
    String string = this.inputs.get(currentInput);
    this.currentInput ++;
    return string;
  }

  @Override
  public String fileSaveChooser() {
    String string = this.inputs.get(currentInput);
    this.currentInput ++;
    return string;
  }

  @Override
  public void addFeatures(IFeatures features) {
    // does nothing
  }

  @Override
  public void renderMessage(String message) throws IOException, IllegalArgumentException {
    this.ap.append(message);
  }
}

package view;

import controller.IFeatures;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.IPixel;
import model.ImageUtil;
import model.PixelImpl;
import model.Utils;

/**
 * An implementation of IGUIView that uses JFrame to provide an interaction GUI editor. This view
 * allows for only the functionality provided by ECMultiLayerModel.
 */
public class SimpleGUIView extends JFrame implements IGUIView {

  private final JPanel mainPanel;
  private final JLabel topMostImage;

  private final JButton loadButton;
  private final JButton loadProjectButton;
  private final JButton exportButton;
  private final JButton exportAllButton;

  private final JMenuItem openFile;
  private final JMenuItem openProject;
  private final JMenuItem saveFile;
  private final JMenuItem saveProject;

  private final JMenuItem blurOption;
  private final JMenuItem sharpenOption;
  private final JMenuItem grayscaleOption;
  private final JMenuItem sepiaOption;
  private final JMenuItem downscaleOption;
  private final JMenuItem mosaicOption;

  private final JMenuItem copyOption;
  private final JMenuItem removeOption;
  private final JMenuItem removeAllOption;
  private final JMenuItem toggleVisOption;
  private final JMenuItem checkerOption;

  private final JLabel layerLabel;
  private final JLabel numLayersLabel;

  /**
   * Constructs a SimpleGUIView whose window will be titled with the given caption.
   *
   * @param caption title of the editor window
   */
  public SimpleGUIView(String caption) {
    super(caption);
    Utils.requireNonNull(caption);

    setMinimumSize(new Dimension(540, 540));
    // creating the main panel that will house smaller panels.
    this.mainPanel = new JPanel();
    mainPanel.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    add(mainPanel);

    // creating the panel that will contain information about the current opened project.
    JPanel descriptionPanel = new JPanel();
    JMenuBar fileBar = new JMenuBar();
    fileBar.setLayout(new BoxLayout(fileBar, BoxLayout.PAGE_AXIS));
    fileBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));

    JMenu fileMenu = new JMenu("File");
    this.openFile = new JMenuItem("Open");
    this.openProject = new JMenuItem("Open Project");
    this.saveFile = new JMenuItem("Save");
    this.saveProject = new JMenuItem("Save All");
    fileMenu.add(openFile);
    fileMenu.add(openProject);
    fileMenu.add(saveFile);
    fileMenu.add(saveProject);
    fileBar.add(fileMenu);
    descriptionPanel.add(fileBar);

    this.layerLabel = new JLabel("Current Layer: 0");
    this.numLayersLabel = new JLabel("Number of Layers: 0");

    descriptionPanel.add(layerLabel);
    descriptionPanel.add(numLayersLabel);
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 0.5;
    mainPanel.add(descriptionPanel, 0);

    // creating the panel that will display the project's current topmost image.
    this.topMostImage = new JLabel();
    c.weightx = 1.0;
    c.weighty = 1.0;
    c.gridwidth = 6;
    c.gridheight = 4;
    c.gridx = 1;
    c.gridy = 1;
    topMostImage.setIcon(new ImageIcon(renderImage(new IPixel[][]{{new PixelImpl(0, 0, 0)}})));
    mainPanel.add(topMostImage, c);

    // creating the panel that contains all the buttons that will apply filters to the images.
    JPanel filterPanel = new JPanel();
    filterPanel.setSize(200, 125);
    filterPanel.setBorder(BorderFactory.createTitledBorder("Image Filter Options"));
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 0.5;
    c.weighty = 0.5;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.gridx = 7;
    c.gridy = 1;
    c.insets = new Insets(0, 20, 0, 20);
    mainPanel.add(filterPanel, c);

    JMenuBar filterBar = new JMenuBar();
    filterBar.setLayout(new BoxLayout(filterBar, BoxLayout.PAGE_AXIS));
    filterBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));

    JMenu filterMenu = new JMenu("Filter");
    this.blurOption = new JMenuItem("Blur");
    this.sharpenOption = new JMenuItem("Sharpen");
    this.grayscaleOption = new JMenuItem("Grayscale");
    this.sepiaOption = new JMenuItem("Sepia");
    this.downscaleOption = new JMenuItem("Downscale");
    this.mosaicOption = new JMenuItem("Mosaic");
    filterMenu.add(blurOption);
    filterMenu.add(sharpenOption);
    filterMenu.add(grayscaleOption);
    filterMenu.add(sepiaOption);
    filterMenu.add(downscaleOption);
    filterMenu.add(mosaicOption);
    filterBar.add(filterMenu);
    filterPanel.add(filterBar);

    // creating the panel that will add other miscellaneous functionality to the images
    JPanel miscPanel = new JPanel();
    miscPanel.setBorder(BorderFactory.createTitledBorder("Miscellaneous Options"));
    c.gridy = 2;
    mainPanel.add(miscPanel, c);

    JMenuBar miscBar = new JMenuBar();
    miscBar.setLayout(new BoxLayout(miscBar, BoxLayout.PAGE_AXIS));
    miscBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));

    JMenu miscMenu = new JMenu("Other");
    this.copyOption = new JMenuItem("Copy Layer");
    this.removeOption = new JMenuItem("Remove Layer");
    this.removeAllOption = new JMenuItem("Remove All");
    this.toggleVisOption = new JMenuItem("Toggle Visibility");
    this.checkerOption = new JMenuItem("Create Checkerboard");
    miscMenu.add(copyOption);
    miscMenu.add(removeOption);
    miscMenu.add(removeAllOption);
    miscMenu.add(toggleVisOption);
    miscMenu.add(checkerOption);
    miscBar.add(miscMenu);
    miscPanel.add(miscBar);

    // creating the panel that will deal with importing, creating, and exporting images.
    JPanel createExportPanel = new JPanel();
    createExportPanel.setLayout(new GridLayout(1, 0, 30, 10));
    c.weighty = 0;
    c.gridwidth = 4;
    c.gridx = 1;
    c.gridy = 5;
    c.insets = new Insets(20, 0, 20, 0);
    mainPanel.add(createExportPanel, c);

    this.loadButton = new JButton("Load File");
    createExportPanel.add(loadButton, c);

    this.loadProjectButton = new JButton("Load Project");
    c.gridx = 2;
    createExportPanel.add(loadProjectButton, c);

    this.exportButton = new JButton("Export");
    c.gridx = 3;
    createExportPanel.add(exportButton, c);

    this.exportAllButton = new JButton("Export All");
    c.gridx = 4;
    createExportPanel.add(exportAllButton, c);

    pack();
    setVisible(true);
  }

  /**
   * Convenience constructor that automatically sets the title of the JFrame window to Image Editor.
   */
  public SimpleGUIView() {
    this("Image Editor");
  }

  @Override
  public void renderMessage(String message) throws IllegalArgumentException {
    Utils.requireNonNull(message);
    JOptionPane.showMessageDialog(mainPanel, message);
  }

  @Override
  public BufferedImage renderImage(IPixel[][] img) throws IllegalArgumentException {
    ImageUtil.checkImageRect(img);
    int height = img.length;
    int width = img[0].length;
    BufferedImage display = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < height; i += 1) {
      for (int j = 0; j < width; j += 1) {
        int[] rgbArray = img[i][j].getRGB();
        Color color = new Color(rgbArray[0], rgbArray[1], rgbArray[2]);
        int colorValue = color.getRGB();
        display.setRGB(j, i, colorValue);
      }
    }
    return display;
  }

  @Override
  public void updateImage(IPixel[][] img) throws IllegalArgumentException {
    ImageUtil.checkImageRect(img);
    topMostImage.setIcon(new ImageIcon(renderImage(img)));
  }

  @Override
  public void setCurrentLayerText(int i) throws IllegalArgumentException {
    if (i < 0) {
      throw new IllegalArgumentException("The current layer must be positive.");
    }
    layerLabel.setText("Current Layer: " + String.valueOf(i));
  }

  @Override
  public void setNumOfLayersText(int i) throws IllegalArgumentException {
    if (i < 0) {
      throw new IllegalArgumentException("The total number of layers must be positive");
    }
    numLayersLabel.setText("Number of layers: " + String.valueOf(i));
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void addFeatures(IFeatures features) {
    Utils.requireNonNull(features);

    openFile.addActionListener(evt -> features.loadFile());
    openProject.addActionListener(evt -> features.loadProject());
    saveFile.addActionListener(evt -> features.save());
    saveProject.addActionListener(evt -> features.saveAll());

    blurOption.addActionListener(evt -> features.blur());
    sharpenOption.addActionListener(evt -> features.sharpen());
    grayscaleOption.addActionListener(evt -> features.grayscale());
    sepiaOption.addActionListener(evt -> features.sepia());
    downscaleOption.addActionListener(evt -> features.downscale());
    mosaicOption.addActionListener(evt -> features.mosaic());

    copyOption.addActionListener(evt -> features.copy());
    removeOption.addActionListener(evt -> features.remove());
    removeAllOption.addActionListener(evt -> features.removeAll());
    toggleVisOption.addActionListener(evt -> features.toggleVisibility());
    checkerOption.addActionListener(evt -> features.checker());

    loadButton.addActionListener(evt -> features.loadFile());
    loadProjectButton.addActionListener(evt -> features.loadProject());
    exportButton.addActionListener(evt -> features.save());
    exportAllButton.addActionListener(evt -> features.saveAll());
  }

  @Override
  public String getInput(String message) throws IllegalArgumentException {
    Utils.requireNonNull(message);
    String text = JOptionPane.showInputDialog(mainPanel, message, JOptionPane.CANCEL_OPTION);
    if (text == null || text.length() == 0) {
      throw new IllegalArgumentException("Operation cancelled");
    } else {
      return text;
    }
  }

  @Override
  public String fileOpenChooser(boolean addFilter, boolean directoryOnly) {
    JFileChooser fileChooser = new JFileChooser(".");
    if (addFilter) {
      FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG PNG PPM Images", "jpg",
          "png", "ppm");
      fileChooser.setFileFilter(filter);
    }
    if (directoryOnly) {
      fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }
    int input = fileChooser.showOpenDialog(mainPanel);
    if (input == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      return f.getAbsolutePath();
    } else if (input == JFileChooser.ERROR_OPTION) {
      throw new IllegalArgumentException("Cannot write to the given path");
    } else {
      throw new IllegalArgumentException("Operation cancelled");
    }
  }

  @Override
  public String fileSaveChooser() {
    JFileChooser fileChooser = new JFileChooser(".");
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int input = fileChooser.showSaveDialog(mainPanel);
    if (input == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      return f.getAbsolutePath();
    } else if (input == JFileChooser.ERROR_OPTION) {
      throw new IllegalArgumentException("Cannot write to the given path");
    } else {
      throw new IllegalArgumentException("Operation cancelled");
    }
  }

  @Override
  public void showPreview(IPixel[][] img, String message) {
    ImageUtil.checkImageRect(img);
    Utils.requireNonNull(message);
    BufferedImage bufferedImage = this.renderImage(img);
    Icon icon = new ImageIcon(bufferedImage);
    int input = JOptionPane.showConfirmDialog(mainPanel, message, "Preview",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
    if (input == JOptionPane.CANCEL_OPTION) {
      throw new IllegalArgumentException("Operation cancelled");
    }
  }
}

package model;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


/**
 * This class contains utility methods to read a PPM image from file and write a 2D array of pixels
 * to a PPM image file. Also contains the ability to create customizable checkerboard images.
 */
public class ImageUtil {

  /**
   * Makes sure that the given image is rectangular.
   *
   * @param image 2D array of pixels
   * @throws IllegalArgumentException if the given image is empty, not rectangular, or is null
   */
  public static void checkImageRect(IPixel[][] image) throws IllegalArgumentException {
    Utils.requireNonNull(image);
    if (image.length == 0 || image[0].length == 0) {
      throw new IllegalArgumentException("Given image is empty");
    }
    int firstRowSize = image[0].length;
    for (IPixel[] row : image) {
      if (row.length != firstRowSize) {
        throw new IllegalArgumentException("Given image is not rectangular");
      }
      for (IPixel pixel : row) {
        if (pixel == null) {
          throw new IllegalArgumentException("Given image is not rectangular");
        }
      }
    }
  }

  /**
   * Read an image file in the PPM format and returns the given image in an IPixel 2D array.
   *
   * @param filename the path of the file.
   * @throws IllegalArgumentException if the filename is not valid
   */
  public static IPixel[][] readPPM(String filename) throws IllegalArgumentException {
    Utils.requireNonNull(filename);
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      throw new IllegalArgumentException("Can't find given file");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    if (maxValue != 255) {
      throw new IllegalArgumentException("Max value has to be 255");
    }

    IPixel[][] grid = new IPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        grid[i][j] = new PixelImpl(r, g, b);
      }
    }
    return grid;
  }

  /**
   * Reads the image at the given file name and returns the image in a IPixel 2D array.
   *
   * @param filename filepath of the image
   * @return the 2D array of pixels representing the image
   * @throws IllegalArgumentException if the filename is not valid or is not supported
   */
  public static IPixel[][] readImage(String filename) throws IllegalArgumentException {
    BufferedImage img;
    int height;
    int width;
    try {
      img = ImageIO.read(new File(filename));
      height = img.getHeight();
      width = img.getWidth();
    } catch (IOException | NullPointerException e) {
      System.out.println("File " + filename + " not found!\n");
      throw new IllegalArgumentException("Can't find given file\n");
    }
    IPixel[][] pixelImg = new IPixel[height][width];
    for (int i = 0; i < height; i += 1) {
      for (int j = 0; j < width; j += 1) {
        int rgb = img.getRGB(j, i);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        pixelImg[i][j] = new PixelImpl(red, green, blue);
      }
    }
    return pixelImg;
  }

  /**
   * Reads the images at the given directory name and returns the images in an arrayList of IPixel
   * 2D arrays.
   *
   * @param filename filepath of the folder/directory
   * @return an arrayList of 2D array of pixels representing the images
   * @throws IllegalArgumentException if the filename or any of the images are not valid or are not
   *                                  supported
   */
  public static ArrayList<IPixel[][]> readDirectory(String filename)
      throws IllegalArgumentException {
    Utils.requireNonNull(filename);
    Scanner sc;
    ArrayList<IPixel[][]> ans = new ArrayList<>();
    try {
      sc = new Scanner(new File(filename + "/layerLocations.txt"));
    } catch (IOException e) {
      throw new IllegalArgumentException("Can't find given file.\n");
    }
    while (sc.hasNext()) {
      String next = sc.next();
      String type = next.substring(next.lastIndexOf(".") + 1);

      switch (type) {
        case "invisible":
        case "visible":
          break;
        case "ppm":
          ans.add(ImageUtil.readPPM(next));
          break;
        case "png":
        case "jpg":
        case "jpeg":
          ans.add(ImageUtil.readImage(next));
          break;
        default:
          throw new IllegalArgumentException("Image type not supported.\n");
      }
    }
    return ans;
  }

  /**
   * Reads the txt file in the given directory and returns an array of booleans representing the
   * visibilities of each layer in the directory.
   *
   * @param filename filepath of the folder/directory
   * @return an arrayList of booleans representing layer visibilities
   * @throws IllegalArgumentException if the filename is not valid
   */
  public static ArrayList<Boolean> getVisibilities(String filename)
      throws IllegalArgumentException {
    Utils.requireNonNull(filename);
    Scanner sc;
    ArrayList<Boolean> ans = new ArrayList<>();
    try {
      sc = new Scanner(new File(filename + "/layerLocations.txt"));
    } catch (IOException e) {
      throw new IllegalArgumentException("Can't find given file.\n");
    }
    while (sc.hasNext()) {
      String next = sc.next();
      if (next.equals("invisible")) {
        ans.add(false);
      }
      if (next.equals("visible")) {
        ans.add(true);
      }
    }
    return ans;
  }

  /**
   * Writes the given 2D array of pixels into a ppm file and stores it at the given destination.
   *
   * @param image       that will be converted to ppm
   * @param destination filepath of the ppm
   * @throws IllegalArgumentException if the image or the
   */
  public static void writePPM(IPixel[][] image, String destination)
      throws IllegalArgumentException {
    ImageUtil.checkImageRect(image);
    Utils.requireNonNull(destination);
    int height = image.length;
    int width = image[0].length;
    File f;
    try {
      f = new File(destination);
    } catch (InvalidPathException e) {
      throw new IllegalArgumentException("Destination is not a valid filepath.\n");
    }
    StringBuilder ppm = new StringBuilder();
    ppm.append("P3\n# ").append(f.getName()).append("\n").append(image[0].length)
        .append("\n").append(image.length).append("\n255\n");
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] rgb = image[i][j].getRGB();
        ppm.append(rgb[0]).append("  ");
        ppm.append(rgb[1]).append("  ");
        ppm.append(rgb[2]).append("\n");
      }
    }
    String ppmImg = ppm.toString().trim();
    PrintWriter writer;
    try {
      writer = new PrintWriter(destination);
    } catch (IOException e) {
      return;
    }
    writer.print(ppmImg);
    writer.close();
  }

  /**
   * Creates a checkerboard with the two given colors, the size of each checkerboard box, and the
   * width and height of the image, in checkerboard boxes.
   *
   * @param col1   color of the top left box in the checkerboard pattern
   * @param col2   other color in the checkerboard pattern
   * @param size   the size (in pixels) of each checkerboard box
   * @param width  the width of the image (in checkerboard boxes)
   * @param height the height of the image (in checkerboard boxes)
   * @throws IllegalArgumentException if either color is invalid or the dimensions are not positive
   */

  public static IPixel[][] createChecker(Color col1, Color col2, int size, int width, int height)
      throws IllegalArgumentException {
    if (col1 == null || col2 == null) {
      throw new IllegalArgumentException("invalid colors.");
    }
    if (size < 1 || width < 1 || height < 1) {
      throw new IllegalArgumentException("invalid dimensions.");
    }
    if (col1.getRGB() == col2.getRGB()) {
      throw new IllegalArgumentException("Colors must be different in a valid checkerboard.");
    }
    IPixel[][] img = new IPixel[size * height][size * width];
    for (int i = 0; i < height; i += 1) {
      for (int x = 0; x < size; x += 1) {
        boolean color = i % 2 == 0;
        for (int j = 0; j < width; j += 1) {
          for (int y = 0; y < size; y += 1) {
            if (color) {
              img[i * size + x][j * size + y] = new PixelImpl(col1.getRed(), col1.getGreen(),
                  col1.getBlue());
            } else {
              img[i * size + x][j * size + y] = new PixelImpl(col2.getRed(), col2.getGreen(),
                  col2.getBlue());
            }
          }
          color = !color;
        }
      }
    }
    return img;
  }

  /**
   * exports the given 2D Array of IPixel into a JPEG image to the given filepath destination.
   *
   * @param image the image to be exported, represented as a 2D Array of IPixels
   * @param path  the destination of the image
   * @throws IllegalArgumentException if the image or the given filepath is not valid
   */
  public static void exportJPEG(IPixel[][] image, String path) throws IllegalArgumentException {
    exportImage(image, path, "jpeg");
  }

  /**
   * exports the given 2D Array of IPixel into a JPEG image to the given filepath destination.
   *
   * @param image the image to be exported, represented as a 2D Array of IPixels
   * @param path  the destination of the image
   * @throws IllegalArgumentException if the image or the given filepath is not valid
   */
  public static void exportPNG(IPixel[][] image, String path) throws IllegalArgumentException {
    exportImage(image, path, "png");
  }

  /**
   * exports the given 2D Array of IPixel into an image file of the given type to the given filepath
   * destination.
   *
   * @param image the image to be exported, represented as a 2D Array of IPixels
   * @param path  the destination of the image
   * @param type  the type of file that the image will be in
   * @throws IllegalArgumentException if the image is not rectangular or is invalid, or if the given
   *                                  filepath is not valid
   */
  private static void exportImage(IPixel[][] image, String path, String type)
      throws IllegalArgumentException {
    ImageUtil.checkImageRect(image);
    Utils.requireNonNull(path);

    int height = image.length;
    int width = image[0].length;

    BufferedImage img = new BufferedImage(width, height,
        BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < height; i += 1) {
      for (int j = 0; j < width; j += 1) {
        int[] rgbArray = image[i][j].getRGB();
        Color color = new Color(rgbArray[0], rgbArray[1], rgbArray[2]);
        int colorValue = color.getRGB();
        img.setRGB(j, i, colorValue);
      }
    }
    File imageFile = new File(path);
    try {
      ImageIO.write(img, type, imageFile);
    } catch (IOException e) {
      throw new IllegalArgumentException("Given path is not valid\n");
    }
  }
}


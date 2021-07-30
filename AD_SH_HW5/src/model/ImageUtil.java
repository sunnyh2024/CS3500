package model;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.File;


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
  public static void checkImageRect(IPixel[][] image) {
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
        builder.append(s + System.lineSeparator());
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
   * writes the given 2D array of pixels into a ppm file and stores it at the given destination.
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
      throw new IllegalArgumentException("Destination is not a valid filepath.");
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

  public static IPixel[][] createChecker(Color col1, Color col2, int size, int width, int height) {
    if (col1 == null || col2 == null) {
      throw new IllegalArgumentException("invalid colors.");
    }
    if (size < 1 || width < 1 || height < 1) {
      throw new IllegalArgumentException("invalid dimensions.");
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
}


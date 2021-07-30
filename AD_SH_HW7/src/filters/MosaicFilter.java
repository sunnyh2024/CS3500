package filters;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import model.IPixel;
import model.ImageUtil;
import model.PixelImpl;
import model.Utils;

/**
 * Represents a filter that, when applied, will split images into chunks using a set of randomly
 * selected pixels, called seeds, and linking each pixel in the image to the nearest seed. The
 * resulting clusters are composed of the average RGB values of the pixels within the cluster. The
 * resulting image looks as though it is being seen through a stained-glass window.
 */
public class MosaicFilter implements IFilter {

  private final int numSeeds;
  private final Random random;

  /**
   * Constructs a MosaicFilter, where a random seed can be passed in to get a preset sequence of
   * ints. This constructor is mainly used for testing purposes.
   *
   * @param seeds  the number of seeds that the mosaic will have
   * @param random the random object that will be used to create the seeds
   * @throws IllegalArgumentException if the number of seeds is not positive or the random is
   *                                  invalid
   */
  public MosaicFilter(int seeds, Random random) throws IllegalArgumentException {
    if (seeds < 1) {
      throw new IllegalArgumentException("Seeds has to be at least 1");
    }
    this.numSeeds = seeds;
    this.random = Utils.requireNonNull(random);
  }

  /**
   * Constructs a MosaicFilter with a new, unseeded Random object.
   *
   * @param seeds the number of seeds that the mosaic will have
   * @throws IllegalArgumentException if the number of seeds is not positive
   */
  public MosaicFilter(int seeds) throws IllegalArgumentException {
    this(seeds, new Random());
  }

  @Override
  public IPixel[][] apply(IPixel[][] image) throws IllegalArgumentException {
    ImageUtil.checkImageRect(image);
    if (this.numSeeds > image.length * image[0].length) {
      throw new IllegalArgumentException(
          "Given image is too small for the number of seeds to be made");
    }

    int height = image.length;
    int width = image[0].length;
    Position[] seeds = this.generateSeeds(width, height);
    int[][][] clusters = this.getClusters(seeds, image);
    Map<Integer, Integer[]> avgRGB = this.getAvgRGB(clusters, seeds, image);

    IPixel[][] ans = new IPixel[height][width];
    for (int i = 0; i < height; i += 1) {
      for (int j = 0; j < width; j += 1) {
        int clusterInd = clusters[j][i][0];
        Integer[] rgb = avgRGB.get(clusterInd);
        ans[j][i] = new PixelImpl(rgb[0], rgb[1], rgb[2]);
      }
    }
    return ans;
  }

  /**
   * returns a map that links the index of a cluster to an integer array representing the average
   * RGB values in that cluster.
   *
   * @param clusters triple int array. First two arrays represents the coordinates that the pixel is
   *                 located at in the image, and the inner array represents the index of the
   *                 cluster that the pixel is linked to
   * @param seeds    position array that represents the positions of all the seeds in the image
   * @param image    2D array of pixels that represents the image that the clusters and seeds are
   *                 referencing
   * @return map of integers to integer arrays, where the integer represents the cluster index, and
   *         the array represents the average RGB value of that cluster
   * @throws IllegalArgumentException if the clusters, seeds, or image is invalid
   */
  private Map<Integer, Integer[]> getAvgRGB(int[][][] clusters, Position[] seeds,
      IPixel[][] image) throws IllegalArgumentException {
    Utils.requireNonNull(clusters);
    Utils.requireNonNull(seeds);
    Utils.requireNonNull(image);

    Map<Integer, Integer[]> ans = new HashMap<>();
    int[][] clusterColors = new int[seeds.length][3];

    for (int seedInd = 0; seedInd < seeds.length; seedInd += 1) {
      int totalPixels = 0;
      for (int i = 0; i < image.length; i += 1) {
        for (int j = 0; j < image[0].length; j += 1) {
          Position currPos = new Position(j, i);
          if (clusters[j][i][0] == seedInd) {
            int[] rgb = image[j][i].getRGB();
            clusterColors[seedInd][0] += rgb[0];
            clusterColors[seedInd][1] += rgb[1];
            clusterColors[seedInd][2] += rgb[2];
            totalPixels += 1;
          }
        }
      }
      int avgR = clusterColors[seedInd][0] / totalPixels;
      int avgG = clusterColors[seedInd][1] / totalPixels;
      int avgB = clusterColors[seedInd][2] / totalPixels;
      ans.put(seedInd, new Integer[]{avgR, avgG, avgB});
    }
    return ans;
  }

  /**
   * helper method for apply that returns the cluster that each pixel (represented here as a
   * position in the image) belongs to.
   *
   * @param seeds an array containing the positions of the different seeds
   * @param image the image that the seeds refer to
   * @return a map that links each pixel's position to the index of the nearest cluster
   */
  private int[][][] getClusters(Position[] seeds, IPixel[][] image) {
    Utils.requireNonNull(seeds);
    Utils.requireNonNull(image);
    int[][][] ans = new int[image.length][image[0].length][1];
    for (int i = 0; i < image.length; i += 1) {
      for (int j = 0; j < image[0].length; j += 1) {
        Position currPos = new Position(j, i);
        //ACCUMULATORS: the current closest cluster and current minDist to a seed
        int cluster = 0;
        double minDist = currPos.getDistance(seeds[0]);

        for (int ind = 1; ind < seeds.length; ind += 1) {
          double tempDist = currPos.getDistance(seeds[ind]);
          if (tempDist < minDist) {
            minDist = tempDist;
            cluster = ind;
          }
        }
        ans[j][i][0] = cluster;
      }
    }
    return ans;
  }

  /**
   * Generates the seeds for a mosaic image using the given width and height of the image.
   *
   * @param width  maximum x value where a position is made
   * @param height maximum y value where a position is made
   * @return an array of Positions of where seeds are to be made
   */
  private Position[] generateSeeds(int width, int height) throws IllegalArgumentException {
    Position currPosn;
    Position[] seeds = new Position[this.numSeeds];
    int posnsFound = 0;
    while (posnsFound < this.numSeeds) {
      currPosn = new Position(this.random.nextInt(width), this.random.nextInt(height));
      if (!Arrays.asList(seeds).contains(currPosn)) {
        seeds[posnsFound] = currPosn;
        posnsFound += 1;
      }
    }
    return seeds;
  }


  /**
   * Represents a 2D cartesian coordinate with an x and a y value.
   */
  private class Position {

    private final int x;
    private final int y;

    /**
     * Constructs a Position with an x and a y coordinate.
     *
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     */
    private Position(int x, int y) {
      this.x = x;
      this.y = y;
    }

    /**
     * returns an int array representing the x and y coordinates of this position.
     *
     * @return an int array that represents this position in the form (x, y)
     */
    private int[] getPos() {
      int[] ans = new int[]{x, y};
      return ans;
    }

    /**
     * returns the Euclidean distance between this position and the other given position.
     *
     * @param other the other position whose distance from this position will be measured
     * @return the distance between this and other, using Pythagorean Theorem
     * @throws IllegalArgumentException if the other is invalid
     */
    private double getDistance(Position other) {
      Utils.requireNonNull(other);
      double xDist = Math.pow(other.getPos()[0] - this.x, 2);
      double yDist = Math.pow(other.getPos()[1] - this.y, 2);
      return Math.sqrt(xDist + yDist);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Position pos = (Position) o;
      return x == pos.x && y == pos.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }
  }
}

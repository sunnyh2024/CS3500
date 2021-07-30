package filters;

import model.IPixel;
import model.ImageUtil;
import model.PixelImpl;
import model.Utils;

/**
 * Represents an image filter that reduces an image's size to the desired with and height.
 */
public class DownscaleFilter implements IFilter {

  private final int targetWidth;
  private final int targetHeight;

  /**
   * Constructs a Downscale filter with the given target width and target height.
   *
   * @param tWidth  target width to reduce an image to
   * @param tHeight target height to reduce an image to
   */
  public DownscaleFilter(int tWidth, int tHeight) {
    this.targetWidth = tWidth;
    this.targetHeight = tHeight;
  }

  @Override
  public IPixel[][] apply(IPixel[][] image) {
    ImageUtil.checkImageRect(image);
    IPixel[][] ans = new IPixel[targetHeight][targetWidth];
    int height = image.length;
    int width = image[0].length;
    for (int i = 0; i < height; i += 1) {
      for (int j = 0; j < width; j += 1) {
        float y = (targetHeight * j) / height;
        float x = (targetWidth * i) / width;
        if (y % 1 != 0 || x % 1 != 0) {
          IPixel pixA = image[(int) Math.floor(y)][(int) Math.floor(x)];
          IPixel pixB = image[(int) Math.floor(y)][(int) Math.ceil(x)];
          IPixel pixC = image[(int) Math.ceil(y)][(int) Math.floor(x)];
          IPixel pixD = image[(int) Math.ceil(y)][(int) Math.ceil(x)];
          ans[Math.round(y)][Math.round(x)] = getFourPointPixel(pixA, pixB, pixC, pixD, y, x);
        } else {
          ans[Math.round(y)][Math.round(x)] = image[j][i];
        }
      }
    }
    return ans;
  }

  /**
   * Returns a pixel whose colors have been computed using the four given pixels around the floating
   * point location x, y.
   *
   * @param pixA pixel at the location floor(x), floor(y)
   * @param pixB pixel at the location floor(x), floor(y)
   * @param pixC pixel at the location floor(x), floor(y)
   * @param pixD pixel at the location floor(x), floor(y)
   * @param y    the y location of the return pixel (not rounded)
   * @param x    the x location of the return pixel (not rounded)
   * @return Pixel whose colors are computed from the four given pixels
   */
  private IPixel getFourPointPixel(IPixel pixA, IPixel pixB, IPixel pixC, IPixel pixD,
      float y, float x) {
    Utils.requireNonNull(pixA);
    Utils.requireNonNull(pixB);
    Utils.requireNonNull(pixC);
    Utils.requireNonNull(pixD);
    int[] a = pixA.getRGB();
    int[] b = pixB.getRGB();
    int[] c = pixC.getRGB();
    int[] d = pixD.getRGB();
    int[] ansRGB = new int[3];
    for (int i = 0; i < 3; i += 1) {
      double m = b[i] * (x - Math.floor(x)) + a[i] * (Math.ceil(x) - x);
      double n = d[i] * (x - Math.floor(x)) + c[i] * (Math.ceil(x) - x);
      int color = (int) (n * (y - Math.floor(y)) + m * (Math.ceil(y) - y));
      ansRGB[i] = color;
    }
    return new PixelImpl(ansRGB);
  }
}

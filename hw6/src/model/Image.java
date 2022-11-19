package model;


import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an image as a 2D array of Pixels.
 */
public class Image {
  private final Pixel[][] pixels;
  private final int height;
  private final int width;
  private final int maxValue;

  /**
   * The constructor for an Image.
   *
   * @param pixels   the pixels of the image as a 2D array
   * @param height   the number of rows of pixels
   * @param width    the number of columns of pixels
   * @param maxValue the maximum color value
   * @throws IllegalArgumentException if pixels is null
   */
  public Image(Pixel[][] pixels, int height, int width, int maxValue)
          throws IllegalArgumentException {
    if (pixels == null) {
      throw new IllegalArgumentException("Pixels cannot be null");
    }
    this.pixels = pixels;
    this.height = height;
    this.width = width;
    this.maxValue = maxValue;
  }

  /**
   * Convenience constructor for an image.
   *
   * @param pixels the pixels of the image as a 2D array
   */
  public Image(Pixel[][] pixels) {
    this(pixels, pixels.length, pixels[0].length, 255);
  }


  /**
   * Creates a greyscale version of this image.
   *
   * @param type the component used to make the image greyscale
   * @return a new image whose pixels are all grey.
   */
  public Image makeImageGrey(Pixel.GreyscaleType type) {

    Pixel[][] copy = new Pixel[this.height][this.width];

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        copy[i][j] = this.pixels[i][j].makePixelGrey(type);
      }
    }
    return new Image(copy);
  }

  /**
   * Creates a flipped version of this image.
   *
   * @param isVertical true if the flip is vertical, false if horizontal
   * @return a new flipped image
   */
  public Image flip(boolean isVertical) {

    Pixel[][] copy = new Pixel[this.height][this.width];

    if (isVertical) {

      for (int i = 0; i < this.height; i++) {
        System.arraycopy(pixels[this.height - 1 - i], 0, copy[i], 0, this.width);
      }
    } else {
      for (int i = 0; i < this.height; i++) {
        for (int j = 0; j < this.width; j++) {
          copy[i][j] = pixels[i][this.width - 1 - j];
        }
      }
    }
    return new Image(copy);
  }

  /**
   * Creates a version of this image that is either brightened or darkened by a certain amount.
   *
   * @param increment the amount to brighten, if positive, or darken, if negative
   * @return a new image that is brightened or darkened
   */
  public Image changeBrightness(int increment) {

    Pixel[][] copy = new Pixel[this.height][this.width];

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        copy[i][j] = this.pixels[i][j].changePixelBrightness(increment);
      }
    }
    return new Image(copy);
  }

  /**
   * Represents an Image as a string.
   *
   * @return a string containing the width, height, max value and rgb values of each pixel
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n");
    sb.append(this.width);
    sb.append(" ");
    sb.append(this.height);
    sb.append("\n");
    sb.append(this.maxValue);
    sb.append("\n");
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        sb.append(pixels[i][j].toString());
        sb.append("   ");

      }
      sb.append("\n");
    }

    return sb.toString();
  }

  /**
   * Creates a blurred version of this image.
   *
   * @return a new image with the gaussian blur filter applied.
   */
  public Image blur() {
    double[][] gaussian = new double[][]{
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}
    };

    return new Image(this.matrixNearbyPixels(gaussian));
  }

  /**
   * Creates a sharpened version of this image.
   *
   * @return a new image that has been sharpened.
   */
  public Image sharpen() {
    double[][] sharpener = new double[][]{
            {-.125, -.125, -.125, -.125, -.125},
            {-.125, .25, .25, .25, -.125},
            {-.125, .25, 1, .25, -.125},
            {-.125, .25, .25, .25, -.125},
            {-.125, -.125, -.125, -.125, -.125}
    };
    return new Image(this.matrixNearbyPixels(sharpener));

  }

  /**
   * Creates a greyscale version of this image using a color matrix.
   *
   * @return a new greyscale image.
   */
  public Image greyCT() {
    double[][] grey = new double[][]{
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}
    };

    return new Image(this.matrixMultiplication(grey));
  }

  /**
   * Creates a version of this image with a sepia filter applied.
   *
   * @return a new image with a sepia filter applied.
   */
  public Image sepia() {
    double[][] sepia = new double[][]{
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    };

    return new Image(this.matrixMultiplication(sepia));
  }

  private Pixel[][] matrixNearbyPixels(double[][] matrix) throws IllegalArgumentException {
    if (matrix.length % 2 != 1) {
      throw new IllegalArgumentException("Matrix must have odd size");
    }
    int nearbyIndex = (matrix.length - 1) / 2;
    Pixel[][] newImage = new Pixel[this.height][this.width];

    // loop through image
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        // init r g b values
        double r = 0;
        double g = 0;
        double b = 0;

        // loop through matrix and apply to values, skipping invalids
        for (int x = -nearbyIndex; x <= nearbyIndex; x++) {
          for (int y = -nearbyIndex; y <= nearbyIndex; y++) {
            // tries to go out of bounds
            if (i + x < 0 || i + x >= this.height || j + y < 0 || j + y >= this.width) {
              // skip this pixel
              continue;
            } else {
              // sum up all r g b values
              double multiplier = matrix[x + nearbyIndex][y + nearbyIndex];
              double[] rgb = this.pixels[i + x][j + y].changeMultiplier(multiplier);
              r += rgb[0];
              g += rgb[1];
              b += rgb[2];
            }
          }
        }
        // change pixel
        newImage[i][j] = new Pixel((int) r, (int) g, (int) b);
      }
    }

    return newImage;
  }


  private Pixel[][] matrixMultiplication(double[][] matrix) {

    Pixel[][] newImage = new Pixel[this.height][this.width];

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        newImage[i][j] = this.pixels[i][j].colorTransformation(matrix);
      }
    }

    return newImage;
  }

  /**
   * Turns an Image into a BufferedImage.
   */
  public BufferedImage makeBufferedImage() {
    BufferedImage bufferedImage = new BufferedImage(this.width, this.height,
            BufferedImage.TYPE_INT_RGB);

    for (int rows = 0; rows < this.height; rows++) {
      for (int cols = 0; cols < this.width; cols++) {

        bufferedImage.setRGB(cols, rows, this.pixels[rows][cols].getRGBColor());
      }
    }

    return bufferedImage;
  }

  /**
   * @return
   */
  public Map<Integer, int[]> getHistogram() {
    Map<Integer, int[]> frequencies = new HashMap<Integer, int[]>();

    // maps all frequencies from 0-255 as zero to start
    for (int value = 0; value < 256; value++) {
      int[] startingCounts = {0, 0, 0, 0};
      frequencies.put(value, startingCounts);
    }

    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {

        int[] pixelValues = this.pixels[row][col].getRGBI();

        // gets each component value and updates the corresponding count in frequencies
        // first iteration updates red, second updates green, third blue, fourth intensity
        for (int component = 0; component < 4; component++) {
          // RED: component = 0, rgbiCounts[0] = the count for the current value of red
          // GREEN: component = 1, rgbiCounts[1] = the count for the current value of green
          // BLUE: component = 2, rgbiCounts[2] = the count for the current value of blue
          // INTENSITY: component = 3, rgbiCounts[3] = the count for the current value of intensity
          int[] rgbiCounts = frequencies.get(pixelValues[component]);
          rgbiCounts[component]++;

          frequencies.put(pixelValues[component], rgbiCounts);

        }
      }
    }
    return frequencies;
  }
}

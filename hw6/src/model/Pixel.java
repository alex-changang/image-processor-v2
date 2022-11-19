package model;

import java.awt.*;

/**
 * Represents a pixel in RGB form.
 */
public class Pixel {
  private final int r;
  private final int g;
  private final int b;
  private final int value;
  private final int intensity;
  private final int luma;
  private final int MAX_VALUE = 255;

  /**
   * The constructor for a pixel.
   *
   * @param r the red value
   * @param g the green value
   * @param b the blue value
   */
  public Pixel(int r, int g, int b) {
    this.r = this.clamp(r);
    this.g = this.clamp(g);
    this.b = this.clamp(b);
    this.value = Math.max(Math.max(r, g), b);
    this.intensity = (r + g + b) / 3;
    this.luma = (int) ((0.2126 * r) + (.7152 * g) + (.0722 * b));

  }

  /**
   * The constructor for a grey pixel.
   *
   * @param color the value for all three channels.
   */
  public Pixel(int color) {
    this(color, color, color);
  }

  /**
   * Makes a new pixel whose RGB values are all the same as either
   * the R, G, or B value of this pixel.
   *
   * @param type The greyscale type
   * @return a new pixel with all three values the same
   */
  public Pixel makePixelGrey(GreyscaleType type) {
    Pixel p;
    switch (type) {
      case R:
        p = new Pixel(this.r);
        break;
      case G:
        p = new Pixel(this.g);
        break;
      case B:
        p = new Pixel(this.b);
        break;
      case V:
        p = new Pixel(this.value);
        break;
      case I:
        p = new Pixel(this.intensity);
        break;
      case L:
        p = new Pixel(this.luma);
        break;
      default:
        throw new IllegalArgumentException("Invalid greyscale type");
    }
    return p;
  }

  /**
   * Creates a new pixel whose values are all increased or decreased by a certain amount.
   *
   * @param increment the amount to increase or decrease by
   * @return a new pixel with values increased or decreased equally
   */
  public Pixel changePixelBrightness(int increment) {
    int r = clamp(this.r + increment);
    int g = clamp(this.g + increment);
    int b = clamp(this.b + increment);

    return new Pixel(r, g, b);
  }

  /**
   * Clamps a given value to be in between 0 and a pixel's maximum value.
   *
   * @param value The value we want to clamp.
   * @return Clamped value.
   */
  private int clamp(int value) {
    int newValue;
    if (value > MAX_VALUE) {
      newValue = MAX_VALUE;
    } else if (value < 0) {
      newValue = 0;
    } else {
      newValue = value;
    }
    return newValue;
  }

  /**
   * Represents this pixel as a string.
   *
   * @return a string with the RGB values
   */
  public String toString() {
    return this.r + " " + this.g + " " + this.b;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (!(that instanceof Pixel)) {
      return false;
    }
    return this.r == ((Pixel) that).r
            && this.g == ((Pixel) that).g
            && this.b == ((Pixel) that).b;

  }

  @Override
  public int hashCode() {
    return this.r + this.g * 2 + this.b * 3;
  }

  // todo: clamp pixels with these
  protected double[] changeMultiplier(double multiplier) {
    return new double[]{this.r * multiplier, this.g * multiplier, this.b * multiplier};
  }

  protected Pixel colorTransformation(double[][] matrix) {

    // todo: make this into a loop
    double r = (matrix[0][0] * this.r) + (matrix[0][1] * this.g) + (matrix[0][2] * this.b);
    double g = (matrix[1][0] * this.r) + (matrix[1][1] * this.g) + (matrix[1][2] * this.b);
    double b = (matrix[2][0] * this.r) + (matrix[2][1] * this.g) + (matrix[2][2] * this.b);

    return new Pixel((int) r, (int) g, (int) b);
  }

  protected int getRGBColor() {
    Color color = new Color(this.r, this.g, this.b);
    return color.getRGB();

  }


  /**
   * Represents the possible components used to make a greyscale Pixel/Image.
   */
  public enum GreyscaleType {
    R, G, B, V, I, L
  }


    public int[] getRGBI() {
      int[] rgbi = {this.r, this.g, this.b, this.intensity};
      return rgbi;
    }

}

import org.junit.Test;

import model.Pixel;

import static org.junit.Assert.assertEquals;


/**
 * Represents the tests for Pixel class methods.
 */
public class PixelTest {

  private final Pixel pMax = new Pixel(255);
  private final Pixel pAlmostMax = new Pixel(253);
  private final Pixel pMin = new Pixel(0);
  private final Pixel p = new Pixel(1, 2, 3);

  @Test
  public void makePixelGrey() {
    assertEquals(new Pixel(1), p.makePixelGrey(Pixel.GreyscaleType.R));
  }

  @Test
  public void changePixelBrightness() {
    assertEquals(new Pixel(6, 7, 8), p.changePixelBrightness(5));
    // checks that rgb values clamp to 255
    assertEquals(new Pixel(255, 255, 255), pMax.changePixelBrightness(5));
    assertEquals(new Pixel(255, 255, 255), pAlmostMax.changePixelBrightness(5));
    // checks that rgb values clamp to 0
    assertEquals(new Pixel(0, 0, 0), pMin.changePixelBrightness(-5));
  }

  @Test
  public void testToString() {
    assertEquals("1 2 3", p.toString());
  }
}
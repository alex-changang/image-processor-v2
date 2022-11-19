import org.junit.Test;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;

import model.Image;
import model.Pixel;

import static controller.AbstractImageController.readPPM;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Represents the tests for Image class methods.
 */
public class ImageTest {

  private final Image link = readPPM("hw6/res/link.ppm");

  @Test(expected = IllegalArgumentException.class)
  public void nullConstructorException() {
    Image nullImage = new Image(null, 2,
            2, 255);
  }

  @Test
  public void makeImageGrey() {
    assertEquals("\n"
                    + "4 4\n"
                    + "255\n"
                    + "255 255 255   255 255 255   34 34 34   255 255 255   \n"
                    + "255 255 255   239 239 239   239 239 239   127 127 127   \n"
                    + "34 34 34   34 34 34   127 127 127   127 127 127   \n"
                    + "185 185 185   34 34 34   185 185 185   127 127 127   \n",
            link.makeImageGrey(Pixel.GreyscaleType.R).toString());
  }

  @Test
  public void flip() {
    assertEquals("\n"
                    + "4 4\n"
                    + "255\n"
                    + "185 122 87   34 177 76   185 122 87   127 127 127   \n"
                    + "34 177 76   34 177 76   127 127 127   127 127 127   \n"
                    + "255 255 255   239 228 176   239 228 176   127 127 127   \n"
                    + "255 255 255   255 201 14   34 177 76   255 255 255   \n",
            link.flip(true).toString());
    assertEquals("\n"
                    + "4 4\n"
                    + "255\n"
                    + "255 255 255   34 177 76   255 201 14   255 255 255   \n"
                    + "127 127 127   239 228 176   239 228 176   255 255 255   \n"
                    + "127 127 127   127 127 127   34 177 76   34 177 76   \n"
                    + "127 127 127   185 122 87   34 177 76   185 122 87   \n",
            link.flip(false).toString());
  }

  @Test
  public void changeBrightness() {
    assertEquals("\n"
                    + "4 4\n"
                    + "255\n"
                    + "255 255 255   255 255 255   255 255 255   255 255 255   \n"
                    + "255 255 255   255 255 255   255 255 255   255 255 255   \n"
                    + "255 255 255   255 255 255   255 255 255   255 255 255   \n"
                    + "255 255 255   255 255 255   255 255 255   255 255 255   \n",
            link.changeBrightness(255).toString());
    assertEquals("\n"
                    + "4 4\n"
                    + "255\n"
                    + "0 0 0   0 0 0   0 0 0   0 0 0   \n"
                    + "0 0 0   0 0 0   0 0 0   0 0 0   \n"
                    + "0 0 0   0 0 0   0 0 0   0 0 0   \n"
                    + "0 0 0   0 0 0   0 0 0   0 0 0   \n",
            link.changeBrightness(-255).toString());
    assertEquals("\n"
                    + "4 4\n"
                    + "255\n"
                    + "255 255 255   255 202 15   35 178 77   255 255 255   \n"
                    + "255 255 255   240 229 177   240 229 177   128 128 128   \n"
                    + "35 178 77   35 178 77   128 128 128   128 128 128   \n"
                    + "186 123 88   35 178 77   186 123 88   128 128 128   \n",
            link.changeBrightness(1).toString());
  }

  @Test
  public void testToString() {
    assertEquals("\n"
                    + "4 4\n"
                    + "255\n"
                    + "255 255 255   255 201 14   34 177 76   255 255 255   \n"
                    + "255 255 255   239 228 176   239 228 176   127 127 127   \n"
                    + "34 177 76   34 177 76   127 127 127   127 127 127   \n"
                    + "185 122 87   34 177 76   185 122 87   127 127 127   \n",
            link.toString());
  }

  @Test
  public void testBlur() {
    assertEquals("\n" +
            "4 4\n" +
            "255\n" +
            "142 135 108   160 162 93   125 151 93   98 116 100   \n" +
            "147 169 132   185 210 142   167 186 136   119 127 114   \n" +
            "84 138 87   116 178 113   137 154 121   105 101 95   \n" +
            "56 85 45   69 115 62   92 103 75   78 70 66   \n", link.blur().toString());
  }

  @Test
  public void testSharpen() {
    assertEquals("\n" +
            "4 4\n" +
            "255\n" +
            "255 255 255   255 255 150   208 255 148   255 255 255   \n" +
            "255 255 255   255 255 255   255 255 255   213 226 247   \n" +
            "83 255 151   211 255 218   245 255 242   221 157 203   \n" +
            "79 134 41   36 221 65   162 163 108   152 103 133   \n", link.sharpen().toString());
  }

  @Test
  public void testGreyCT() {
    assertEquals("\n4 4\n255\n254 254 254   198 198 198   139 139 139   254 254 254   \n"
                    + "254 254 254   226 226 226   226 226 226   127 127 127   \n"
                    + "139 139 139   139 139 139   127 127 127   127 127 127   \n"
                    + "132 132 132   139 139 139   132 132 132   127 127 127   \n",
            link.greyCT().toString());
  }

  @Test
  public void testSepia() {
    assertEquals("\n" +
            "4 4\n" +
            "255\n" +
            "255 255 238   255 229 178   163 146 113   255 255 238   \n" +
            "255 255 238   255 255 209   255 255 209   171 152 118   \n" +
            "163 146 113   163 146 113   171 152 118   171 152 118   \n" +
            "182 162 126   163 146 113   182 162 126   171 152 118   \n", link.sepia().toString());

  }

  @Test
  public void makeBufferedImage() {

    BufferedImage test = link.makeBufferedImage();
    Raster r = test.getRaster();
    Appendable vals = new StringBuilder();

    for (int row = 0; row < r.getHeight(); row++) {
      for (int col = 0; col < r.getHeight(); col++) {
        int[] info = new int[4];
        r.getPixel(col, row, info);
        try {
          vals.append(info[0] + "\n");
          vals.append(info[1] + "\n");
          vals.append(info[2] + "\n");
        } catch (IOException e) {
          fail("Failed by appending");
        }
      }
    }

    assertEquals("255\n" +
            "255\n" +
            "255\n" +
            "255\n" +
            "201\n" +
            "14\n" +
            "34\n" +
            "177\n" +
            "76\n" +
            "255\n" +
            "255\n" +
            "255\n" +
            "255\n" +
            "255\n" +
            "255\n" +
            "239\n" +
            "228\n" +
            "176\n" +
            "239\n" +
            "228\n" +
            "176\n" +
            "127\n" +
            "127\n" +
            "127\n" +
            "34\n" +
            "177\n" +
            "76\n" +
            "34\n" +
            "177\n" +
            "76\n" +
            "127\n" +
            "127\n" +
            "127\n" +
            "127\n" +
            "127\n" +
            "127\n" +
            "185\n" +
            "122\n" +
            "87\n" +
            "34\n" +
            "177\n" +
            "76\n" +
            "185\n" +
            "122\n" +
            "87\n" +
            "127\n" +
            "127\n" +
            "127\n", vals.toString());

    assertNotNull(test);
  }
}
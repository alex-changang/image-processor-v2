//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//import model.SimpleImageProcessor;
//import model.Image;
//import model.Pixel;
//
//import static controller.AbstractImageController.readPPM;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
///**
// * Represents tests for our SimpleImageProcessor model.
// */
//public class SimpleImageProcessorTest {
//  private Image link;
//  private Image test4x4;
//  private Map<String, Image> testMap;
//
//  @Before
//  public void initImage() {
//    this.link = readPPM("hw6/res/link.ppm");
//    this.test4x4 = readPPM("hw6/test4x4.ppm");
//
//  }
//
//  @Test
//  public void testImagesConstructor() {
//    Map<String, Image> testMap = new HashMap<String, Image>();
//    testMap.put("link", this.link);
//    testMap.put("test4x4", this.test4x4);
//
//    IImageProcessor testProcessor = new SimpleImageProcessor(testMap);
//
//    assertNotNull(testProcessor);
//  }
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testImagesConstructorException() {
//    IImageProcessor failProcessor = new SimpleImageProcessor(null);
//  }
//
//  @Test
//  public void testEmptyConstructor() {
//    IImageProcessor testProcessor = new SimpleImageProcessor();
//    assertNotNull(testProcessor);
//  }
//
//  @Test
//  public void flip() {
//    String expected = "\n4 4\n255\n185 122 87   34 177 76   185 122 87   127 127 127   \n"
//            + "34 177 76   34 177 76   127 127 127   127 127 127   \n"
//            + "255 255 255   239 228 176   239 228 176   127 127 127   \n"
//            + "255 255 255   255 201 14   34 177 76   255 255 255   \n";
//
//    Image linkVertical = this.link.flip(true);
//
//    assertEquals(expected, linkVertical.toString());
//
//    String expectedHorizontal = "\n"
//            + "4 4\n"
//            + "255\n"
//            + "255 255 255   34 177 76   255 201 14   255 255 255   \n"
//            + "127 127 127   239 228 176   239 228 176   255 255 255   \n"
//            + "127 127 127   127 127 127   34 177 76   34 177 76   \n"
//            + "127 127 127   185 122 87   34 177 76   185 122 87   \n";
//    Image linkHorizontal = this.link.flip(false);
//    assertEquals(expectedHorizontal, linkHorizontal.toString());
//  }
//
//  @Test
//  public void brighten() {
//
//    String expectedBright = "\n4 4\n255\n255 255 255   255 221 34   54 197 96   255 255 255   \n"
//            + "255 255 255   255 248 196   255 248 196   147 147 147   \n"
//            + "54 197 96   54 197 96   147 147 147   147 147 147   \n"
//            + "205 142 107   54 197 96   205 142 107   147 147 147   \n";
//
//    String expectedDark = "\n4 4\n255\n235 235 235   235 181 0   14 157 56   235 235 235   \n"
//            + "235 235 235   219 208 156   219 208 156   107 107 107   \n"
//            + "14 157 56   14 157 56   107 107 107   107 107 107   \n"
//            + "165 102 67   14 157 56   165 102 67   107 107 107   \n";
//
//    Image linkBright = this.link.changeBrightness(20);
//    Image linkDark = this.link.changeBrightness(-20);
//
//    assertEquals(expectedBright, linkBright.toString());
//    assertEquals(expectedDark, linkDark.toString());
//  }
//
//  @Test
//  public void grey() {
//    String expectedRed = "\n4 4\n255\n255 255 255   255 255 255   34 34 34   255 255 255   \n"
//            + "255 255 255   239 239 239   239 239 239   127 127 127   \n"
//            + "34 34 34   34 34 34   127 127 127   127 127 127   \n"
//            + "185 185 185   34 34 34   185 185 185   127 127 127   \n";
//
//    String expectedGreen = "\n4 4\n255\n255 255 255   201 201 201   177 177 177   255 255 255   \n"
//            + "255 255 255   228 228 228   228 228 228   127 127 127   \n"
//            + "177 177 177   177 177 177   127 127 127   127 127 127   \n"
//            + "122 122 122   177 177 177   122 122 122   127 127 127   \n";
//
//    String expectedBlue = "\n4 4\n255\n255 255 255   14 14 14   76 76 76   255 255 255   \n"
//            + "255 255 255   176 176 176   176 176 176   127 127 127   \n"
//            + "76 76 76   76 76 76   127 127 127   127 127 127   \n"
//            + "87 87 87   76 76 76   87 87 87   127 127 127   \n";
//
//    String expectedValue = "\n4 4\n255\n255 255 255   255 255 255   177 177 177   255 255 255   \n"
//            + "255 255 255   239 239 239   239 239 239   127 127 127   \n"
//            + "177 177 177   177 177 177   127 127 127   127 127 127   \n"
//            + "185 185 185   177 177 177   185 185 185   127 127 127   \n";
//
//    String expectedIntensity = "\n4 4\n255\n255 255 255   156 156 156   95 95 95   255 255 255   \n"
//            + "255 255 255   214 214 214   214 214 214   127 127 127   \n"
//            + "95 95 95   95 95 95   127 127 127   127 127 127   \n"
//            + "131 131 131   95 95 95   131 131 131   127 127 127   \n";
//
//    String expectedLuma = "\n4 4\n255\n254 254 254   198 198 198   139 139 139   254 254 254   \n"
//            + "254 254 254   226 226 226   226 226 226   127 127 127   \n"
//            + "139 139 139   139 139 139   127 127 127   127 127 127   \n"
//            + "132 132 132   139 139 139   132 132 132   127 127 127   \n";
//
//    assertEquals(expectedRed, this.link.makeImageGrey(Pixel.GreyscaleType.R).toString());
//    assertEquals(expectedGreen, this.link.makeImageGrey(Pixel.GreyscaleType.G).toString());
//    assertEquals(expectedBlue, this.link.makeImageGrey(Pixel.GreyscaleType.B).toString());
//    assertEquals(expectedValue, this.link.makeImageGrey(Pixel.GreyscaleType.V).toString());
//    assertEquals(expectedIntensity, this.link.makeImageGrey(Pixel.GreyscaleType.I).toString());
//    assertEquals(expectedLuma, this.link.makeImageGrey(Pixel.GreyscaleType.L).toString());
//
//  }
//
//  @Test
//  public void addImage() {
//    IImageProcessor testProcessor = new SimpleImageProcessor();
//
//    testProcessor.addImage("link", this.link);
//    assertEquals(testProcessor.getImage("link"), this.link);
//
//    testProcessor.addImage("link", this.test4x4);
//    assertEquals(testProcessor.getImage("link"), this.test4x4);
//  }
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testNullAdd() {
//    IImageProcessor testProcessor = new SimpleImageProcessor();
//    testProcessor.addImage("null", null);
//  }
//
//  @Test
//  public void getImage() {
//    IImageProcessor testProcessor = new SimpleImageProcessor();
//
//    testProcessor.addImage("link", this.link);
//    assertNotNull(testProcessor.getImage("link"));
//  }
//}
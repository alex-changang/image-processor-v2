//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//import model.SmarterImageProcessor;
//import model.Image;
//
//import static controller.AbstractImageController.readPPM;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
///**
// * Represents the tests for the new model functionality.
// */
//public class SmarterImageProcessorTest {
//  private Map<String, Image> testMap;
//
//  @Before
//  public void initImage() {
//    Image link = readPPM("hw6/res/link.ppm");
//    this.testMap = new HashMap<>();
//    this.testMap.put("link", link);
//  }
//
//  @Test
//  public void testImageConstructor() {
//    ISmarterImageProcessor test = new SmarterImageProcessor(this.testMap);
//    assertNotNull(test);
//  }
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testNullImageConstructor() {
//    new SmarterImageProcessor(null);
//  }
//
//  @Test
//  public void testEmptyConstructor() {
//    ISmarterImageProcessor test = new SmarterImageProcessor();
//    assertNotNull(test);
//  }
//
//  @Test
//  public void testBlur() {
//    String expectedBlur = "\n" + "4 4\n" + "255\n"
//            + "142 135 108   160 162 93   125 151 93   98 116 100   \n"
//            + "147 169 132   185 210 142   167 186 136   119 127 114   \n"
//            + "84 138 87   116 178 113   137 154 121   105 101 95   \n"
//            + "56 85 45   69 115 62   92 103 75   78 70 66   \n";
//
//    ISmarterImageProcessor processor = new SmarterImageProcessor(this.testMap);
//    processor.blur("link", "link-blur");
//
//    assertEquals(expectedBlur, processor.getImage("link-blur").toString());
//  }
//
//  @Test
//  public void testSharpen() {
//    String expectedSharpen = "\n" + "4 4\n" + "255\n"
//            + "255 255 255   255 255 150   208 255 148   255 255 255   \n"
//            + "255 255 255   255 255 255   255 255 255   213 226 247   \n"
//            + "83 255 151   211 255 218   245 255 242   221 157 203   \n"
//            + "79 134 41   36 221 65   162 163 108   152 103 133   \n";
//
//    ISmarterImageProcessor processor = new SmarterImageProcessor(this.testMap);
//    processor.sharpen("link", "link-sharpen");
//
//    assertEquals(expectedSharpen, processor.getImage("link-sharpen").toString());
//  }
//
//  @Test
//  public void testGreyCT() {
//    String expectedGreyCT = "\n4 4\n255\n254 254 254   198 198 198   139 139 139   254 254 254   \n"
//            + "254 254 254   226 226 226   226 226 226   127 127 127   \n"
//            + "139 139 139   139 139 139   127 127 127   127 127 127   \n"
//            + "132 132 132   139 139 139   132 132 132   127 127 127   \n";
//
//    ISmarterImageProcessor processor = new SmarterImageProcessor(this.testMap);
//    processor.greyCT("link", "link-greyCT");
//
//    assertEquals(expectedGreyCT, processor.getImage("link-greyCT").toString());
//  }
//
//  @Test
//  public void testSepia() {
//    String expectedSepia =
//            "\n" + "4 4\n" + "255\n" + "255 255 238   255 229 178   163 146 113   255 255 238   \n"
//                    + "255 255 238   255 255 209   255 255 209   171 152 118   \n"
//                    + "163 146 113   163 146 113   171 152 118   171 152 118   \n"
//                    + "182 162 126   163 146 113   182 162 126   171 152 118   \n";
//
//    ISmarterImageProcessor processor = new SmarterImageProcessor(this.testMap);
//    processor.sepia("link", "link-sepia");
//
//    assertEquals(expectedSepia, processor.getImage("link-sepia").toString());
//  }
//}

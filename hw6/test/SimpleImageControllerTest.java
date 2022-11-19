import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import controller.IImageController;
import controller.SimpleImageController;
import model.Image;
import view.IImageView;
import view.SimpleImageView;

import static controller.AbstractImageController.readPPM;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Represents tests for the controller.
 */
public class SimpleImageControllerTest {
  private Image link;
  private Image test4x4;
//  private Map<String, Image> testMap;

  @Before
  public void initImage() {
    this.link = readPPM("hw6/res/link.ppm");
    this.test4x4 = readPPM("hw6/test4x4.ppm");

  }

  @Test
  public void testExecuteFilter() {

    Appendable actualOut = new StringBuilder();
    IImageView view = new SimpleImageView(actualOut);
    Readable input = new StringReader("load hw6/res/link.ppm link\nvertical-flip link "
            + "link-vertical\nsave link-vertical.ppm link-vertical\nq");


    IImageController controller = new SimpleImageController(view, input);

    controller.executeFilter();

    assertEquals("Welcome to our image editor. Commands are as follows:\n"
            + "\n"
            + "  Load an image: load image-path image-name\n"
            + "  Save an image: save image-path image-name\n"
            + "  Vertically flip an image: vertical-flip image-name dest-image-name\n"
            + "  Horizontally flip an image: horizontal-flip image-name dest-image-name\n"
            + "  Brighten or darken an image: brighten increment image-name dest-image-name\n"
            + "  Grey image by red component: red-component image-name dest-image-name\n"
            + "  Grey image by green component: green-component image-name dest-image-name\n"
            + "  Grey image by blue component: blue-component image-name dest-image-name\n"
            + "  Grey image by value component: value-component image-name dest-image-name\n"
            + "  Grey image by intensity component: intensity-component image-name dest-image-name"
            + "\n"
            + "  Grey image by luma component: luma-component image-name dest-image-name\n"
            + "At any time, press 'q + enter' to quit the image editor.\n"
            + "Image loaded successfully\n"
            + "vertical-flip filter applied\n"
            + "Image saved successfully\n"
            + "Image editor quit. Thank you!\n", actualOut.toString());
  }

  @Test
  public void testExecuteFilterInvalidInput() {
    Appendable actualOut = new StringBuilder();
    IImageView view = new SimpleImageView(actualOut);
    Readable input = new StringReader("load hw6/res/link.ppm link\npineapple link "
            + "link-vertical\nq");


    IImageController controller = new SimpleImageController(view, input);

    controller.executeFilter();

    assertEquals("Welcome to our image editor. Commands are as follows:\n"
            + "\n"
            + "  Load an image: load image-path image-name\n"
            + "  Save an image: save image-path image-name\n"
            + "  Vertically flip an image: vertical-flip image-name dest-image-name\n"
            + "  Horizontally flip an image: horizontal-flip image-name dest-image-name\n"
            + "  Brighten or darken an image: brighten increment image-name dest-image-name\n"
            + "  Grey image by red component: red-component image-name dest-image-name\n"
            + "  Grey image by green component: green-component image-name dest-image-name\n"
            + "  Grey image by blue component: blue-component image-name dest-image-name\n"
            + "  Grey image by value component: value-component image-name dest-image-name\n"
            + "  Grey image by intensity component: intensity-component image-name dest-image-name"
            + "\n"
            + "  Grey image by luma component: luma-component image-name dest-image-name\n"
            + "At any time, press 'q + enter' to quit the image editor.\n"
//            + "Image loaded successfully\n"
            + "load filter applied\n"
            + "Invalid input. Inputs must begin with a command.\n"
            + "Image editor quit. Thank you!\n", actualOut.toString());
  }

  @Test
  public void testFailingRender() {
    Appendable out = new FailingRenderMessage();
    IImageView failingView = new SimpleImageView(out);
    Readable input = new StringReader("load hw6/res/link.ppm link\nvertical-flip link "
            + "link-vertical\nsave link-vertical.ppm link-vertical\nq");
    IImageController controller = new SimpleImageController(failingView, input);

    try {
      controller.executeFilter(); // append gets called.
      fail("did not throw an exception");
    } catch (IllegalStateException e) {
      assertEquals("Transmit failed.", e.getMessage());
    }
  }

  @Test
  public void testSave() {
    Map<String, Image> map = new HashMap<>();
    Image link = readPPM("hw6/res/link.ppm");
    map.put("link", link);
    Appendable actualOut = new StringBuilder();
    IImageView view = new SimpleImageView(actualOut);
    Readable input = new StringReader("save hw6/res/link-save.ppm link\nq\n");


    IImageController controller = new SimpleImageController(view, input);
    controller.executeFilter();

    Image linkSave = readPPM("hw6/res/link-save.ppm");

    assertEquals(link.toString(), linkSave.toString());
  }

  @Test
  public void addImage() {
    Appendable actualOut = new StringBuilder();
    IImageView view = new SimpleImageView(actualOut);
    Readable input = new StringReader("");
    IImageController testProcessor = new SimpleImageController(view, input);

    testProcessor.addImage("link", this.link);
    assertEquals(testProcessor.getImage("link"), this.link);

    testProcessor.addImage("link", this.test4x4);
    assertEquals(testProcessor.getImage("link"), this.test4x4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAdd() {
    Appendable actualOut = new StringBuilder();
    IImageView view = new SimpleImageView(actualOut);
    Readable input = new StringReader("");
    IImageController testProcessor = new SimpleImageController(view, input);
    testProcessor.addImage("null", null);
  }

  @Test
  public void getImage() {
    Appendable actualOut = new StringBuilder();
    IImageView view = new SimpleImageView(actualOut);
    Readable input = new StringReader("");
    IImageController testProcessor = new SimpleImageController(view, input);

    testProcessor.addImage("link", this.link);
    assertNotNull(testProcessor.getImage("link"));
  }
}

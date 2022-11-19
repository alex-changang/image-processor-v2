import org.junit.Test;

import java.io.StringReader;

import controller.IImageController;
import controller.SmarterImageController;
import view.IImageView;
import view.SimpleImageView;

import static org.junit.Assert.assertEquals;

/**
 * Represents the tests for the new controller functionality.
 */
public class SmarterImageControllerTest {


  @Test(expected = IllegalArgumentException.class)
  public void testNullViewConstructor() {
    Readable input = new StringReader("load hw6/res/link.ppm link\nvertical-flip link "
            + "link-vertical\nblur link link-blurred\n"
            + "sharpen link link-sharpened\n"
            + "sepia link link-sepia\n"
            + "save link-new.bmp link\nq");

    IImageController nullController = new SmarterImageController(null, input);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullInputConstructor() {
    Appendable actualOut = new StringBuilder();
    IImageView view = new SimpleImageView(actualOut);

    IImageController nullController = new SmarterImageController(view, null);
  }

  @Test
  public void executeFilter() {
    Appendable actualOut = new StringBuilder();
    IImageView view = new SimpleImageView(actualOut);
    Readable input = new StringReader("load hw6/res/link.ppm link\nvertical-flip link "
            + "link-vertical\nblur link link-blurred\n"
            + "sharpen link link-sharpened\n"
            + "sepia link link-sepia\n"
            + "save link-new.bmp link\nq");


    IImageController controller = new SmarterImageController(view, input);

    controller.executeFilter();

    assertEquals("Welcome to our image editor. Commands are as follows:\n\n"
                    + "  Load an image: load image-path image-name\n"
                    + "  Save an image: save image-path image-name\n"
                    + "  Vertically flip an image: vertical-flip image-name dest-image-name\n"
                    + "  Horizontally flip an image: horizontal-flip image-name dest-image-name\n"
                    + "  Brighten or darken an image: brighten increment image-name dest-image-name"
                    + "\n"
                    + "  Grey image by red component: red-component image-name dest-image-name\n"
                    + "  Grey image by green component: green-component image-name dest-image-name"
                    + "\n"
                    + "  Grey image by blue component: blue-component image-name dest-image-name\n"
                    + "  Grey image by value component: value-component image-name dest-image-name"
                    + "\n"
                    + "  Grey image by intensity component: intensity-component image-name "
                    + "dest-image-name\n"
                    + "  Grey image by luma component: luma-component image-name dest-image-name\n"
                    + "  Grey image by color transformation: grey-ct image-name dest-image-name\n"
                    + "  Sepia image: sepia image-name dest-image-name\n"
                    + "  Blur image: blur image-name dest-image-name\n"
                    + "  Sharpen image: sharpen image-name dest-image-name\n"
                    + "At any time, press 'q + enter' to quit the image editor.\n"
                    + "Image loaded successfully\n"
                    + "vertical-flip filter applied\n"
                    + "blur filter applied\n"
                    + "sharpen filter applied\n"
                    + "sepia filter applied\n"
                    + "Image saved successfully\n" + "Image editor quit. Thank you!\n",
            actualOut.toString());
  }

  @Test
  public void testScript() {
    // if the script is read correctly, the output message should be as follows
    Appendable actualOut = new StringBuilder();
    IImageView view = new SimpleImageView(actualOut);
    Readable input = new StringReader("-file hw6/test-script.txt");

    IImageController controller = new SmarterImageController(view, input);

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
            + "  Grey image by color transformation: grey-ct image-name dest-image-name\n"
            + "  Sepia image: sepia image-name dest-image-name\n"
            + "  Blur image: blur image-name dest-image-name\n"
            + "  Sharpen image: sharpen image-name dest-image-name\n"
            + "At any time, press 'q + enter' to quit the image editor.\n"
            + "Reading instructions from file.\n"
            + "Image loaded successfully\n"
            + "Image loaded successfully\n"
            + "vertical-flip filter applied\n"
            + "horizontal-flip filter applied\n"
            + "red-component filter applied\n"
            + "green-component filter applied\n"
            + "blue-component filter applied\n"
            + "value-component filter applied\n"
            + "intensity-component filter applied\n"
            + "luma-component filter applied\n"
            + "luma-component filter applied\n"
            + "brighten filter applied\n"
            + "brighten filter applied\n"
            + "blur filter applied\n"
            + "sharpen filter applied\n"
            + "grey-ct filter applied\n"
            + "grey-ct filter applied\n"
            + "sepia filter applied\n"
            + "Image saved successfully\n"
            + "Image saved successfully\n"
            + "Image saved successfully\n"
            + "Image saved successfully\n"
            + "Image saved successfully\n"
            + "Image saved successfully\n"
            + "Image saved successfully\n"
            + "Image saved successfully\n"
            + "Image editor quit. Thank you!\nFinished file instructions. Exiting image editor. "
            + "Thank you!\n", actualOut.toString());
  }
}
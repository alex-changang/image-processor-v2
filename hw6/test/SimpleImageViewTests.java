import org.junit.Test;

import java.io.IOException;

import view.IImageView;
import view.SimpleImageView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Represents tests for the SimpleImageView class.
 */
public class SimpleImageViewTests {
  @Test
  public void testConstructor() {
    Appendable out = new StringBuilder();

    IImageView testView = new SimpleImageView();

    assertNotNull(testView);
    assertEquals("", out.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullAppendable() {
    new SimpleImageView(null);
  }

  @Test
  public void testSuccessfulRender() {
    Appendable out = new StringBuilder();
    IImageView view = new SimpleImageView(out);

    try {
      view.renderMessage("hello");
    } catch (IOException e) {
      // do nothing
    }

    assertEquals("hello", out.toString());
  }

}

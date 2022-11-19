import java.io.InputStreamReader;

import controller.IImageController;
import controller.SmarterImageController;
import view.IImageView;
import view.SimpleImageView;

/**
 * Runs the image processor.
 */
public class Main {


  /**
   * The main method.
   *
   * @param args the command line input
   */
  public static void main(String[] args) {
    // temporary testing of main
    IImageView view = new SimpleImageView();
    IImageController controller = new SmarterImageController(view,
            new InputStreamReader(System.in));

    controller.executeFilter();
  }

}




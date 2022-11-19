import controller.GUIController;
import controller.IImageController;
import controller.SimpleImageController;
import controller.SmarterImageController;
import view.GUIView;
import view.IImageView;
import view.SimpleImageView;

import java.io.InputStreamReader;

public class GUIMain {
  public static void main(String[] args){
    IImageView view;
    Readable instructions = null;

    if (args.length == 0) {
      view = new GUIView();
      // TODO: The GUI shouldn't need this.
      instructions = new InputStreamReader(System.in);
    }
    else {
      view = new SimpleImageView();
      instructions = new InputStreamReader(System.in);
    }

    // TODO: ideally, the view should be handling the user input and passing the action/command
    // to the controller.
    IImageController controller = new SmarterImageController(view, instructions);

    controller.executeFilter();
  }
}

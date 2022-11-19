package controller.commands.image;

import java.util.ArrayList;

import controller.IImageController;


import model.Pixel;

/**
 * Represents the known commands to make a greyscale image.
 */
public class GreyscaleCommand extends AbstractCommand {

  /**
   * The constructor for GreyscaleCommand.
   *
   * @param inputs the strings that the user inputs in the command line.
   * @throws IllegalArgumentException if the inputs are null.
   */
  public GreyscaleCommand(ArrayList<String> inputs, IImageController controller) throws IllegalArgumentException {
    super(inputs, controller);
  }

  /**
   * The testing constructor for GreyscaleCommand.
   *
   * @param inputs the strings that the user inputs in the command line.
   * @throws IllegalArgumentException if the inputs are null.
   */
  public GreyscaleCommand(ArrayList<String> inputs) throws IllegalArgumentException {
    super(inputs);
  }

  @Override
  public void execute() throws IllegalArgumentException {
    // todo: reimplement the filter() method here and also extract the grey functionality from the processor and
    // put it here instead.
    if (!valid()) {
      throw new IllegalArgumentException("Invalid inputs. Inputs must be: greyscale-component "
              + "image-name dest-image-name");
    }

    // make a type
    Pixel.GreyscaleType type = Pixel.GreyscaleType.L;
    switch (this.inputs.get(0)) {
      case "red-component":
        type = Pixel.GreyscaleType.R;
        break;
      case "green-component":
        type = Pixel.GreyscaleType.G;
        break;
      case "blue-component":
        type = Pixel.GreyscaleType.B;
        break;
      case "value-component":
        type = Pixel.GreyscaleType.V;
        break;
      case "intensity-component":
        type = Pixel.GreyscaleType.I;
        break;
      case "luma-component":
        type = Pixel.GreyscaleType.L;
        break;
      default:
        // do nothing, this case will never trigger
    }

    String name = this.inputs.get(1);
    String destName = this.inputs.get(2);

    controller.addImage(destName, controller.getImage(name).makeImageGrey(type));
  }


}

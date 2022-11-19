package controller.commands.image;

import java.util.ArrayList;

import controller.IImageController;

/**
 * Represents a known command for making a sharpened image.
 */
public class SharpenCommand extends AbstractCommand {

  /**
   * The constructor for SharpenCommand.
   *
   * @param inputs the strings that the user inputs in the command line.
   * @throws IllegalArgumentException if the inputs are null.
   */
  public SharpenCommand(ArrayList<String> inputs, IImageController controller) {
    super(inputs, controller);
  }

  /**
   * The testing constructor for SharpenCommand.
   *
   * @param inputs the strings that the user inputs in the command line.
   * @throws IllegalArgumentException if the inputs are null.
   */
  public SharpenCommand(ArrayList<String> inputs) throws IllegalArgumentException {
    super(inputs);
  }


  @Override
  public void execute() throws IllegalArgumentException {
    // todo: reimplement the filter() method here and also extract the sharpen functionality from the processor and
    // put it here instead.
    if (!valid()) {
      throw new IllegalArgumentException("Invalid inputs. Input must be: sharpen " +
              "image-name dest-image-name");
    }
    String name = this.inputs.get(1);
    String destName = this.inputs.get(2);

//    processor.sharpen(name, destName);

    controller.addImage(destName, controller.getImage(name).sharpen());

  }

}

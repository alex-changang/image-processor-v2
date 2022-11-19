package controller.commands.image;

import java.util.ArrayList;

import controller.IImageController;


/**
 * Represents a known command to apply a sepia filter onto an image.
 */
public class SepiaCommand extends AbstractCommand {


  /**
   * The constructor for SepiaCommand.
   *
   * @param inputs the strings that the user inputs in the command line.
   * @throws IllegalArgumentException if the inputs are null.
   */
  public SepiaCommand(ArrayList<String> inputs, IImageController controller) {
    super(inputs, controller);
  }
  /**
   * The testing constructor for SepiaCommand.
   *
   * @param inputs the strings that the user inputs in the command line.
   * @throws IllegalArgumentException if the inputs are null.
   */
  public SepiaCommand(ArrayList<String> inputs) throws IllegalArgumentException {
    super(inputs);
  }


  @Override
  public void execute() throws IllegalArgumentException {
    // todo: reimplement the filter() method here and also extract the sepia functionality from the processor and
    // put it here instead.
    if (!valid()) {
      throw new IllegalArgumentException("Invalid inputs. Input must be: sepia " +
              "image-name dest-image-name");
    }
    String name = this.inputs.get(1);
    String destName = this.inputs.get(2);

//    processor.sepia(name, destName);
    controller.addImage(destName, controller.getImage(name).sepia());

  }


}

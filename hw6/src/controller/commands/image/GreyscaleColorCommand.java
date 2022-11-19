package controller.commands.image;

import java.util.ArrayList;

import controller.IImageController;

/**
 * Represents a known command to make a greyscale image through color transformation.
 */
public class GreyscaleColorCommand extends AbstractCommand {


  /**
   * The constructor for GreyscaleColorCommand.
   *
   * @param inputs the strings that the user inputs in the command line.
   * @throws IllegalArgumentException if the inputs are null.
   */
  public GreyscaleColorCommand(ArrayList<String> inputs, IImageController controller) throws IllegalArgumentException {
    super(inputs, controller);
  }

  /**
   * The testing constructor for GreyscaleColorCommand.
   *
   * @param inputs the strings that the user inputs in the command line.
   * @throws IllegalArgumentException if the inputs are null.
   */
  public GreyscaleColorCommand(ArrayList<String> inputs) throws IllegalArgumentException {
    super(inputs);
  }


  @Override
  public void execute() throws IllegalArgumentException {
    // todo: reimplement the filter() method here and also extract the greyCT functionality from the processor and
    // put it here instead.
    if (!valid()) {
      throw new IllegalArgumentException("Invalid inputs. Input must be: grey-ct " +
              "image-name dest-image-name");
    }
    String name = this.inputs.get(1);
    String destName = this.inputs.get(2);

    controller.addImage(destName, controller.getImage(name).greyCT());
  }


}

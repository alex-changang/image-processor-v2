package controller.commands.image;

import java.util.ArrayList;

import controller.IImageController;

import model.Image;

/**
 * Represents a known command to make a blurred image.
 */
public class BlurCommand extends AbstractCommand {

  /**
   * The constructor for BlurCommand.
   *
   * @param inputs the strings that the user inputs in the command line.
   * @throws IllegalArgumentException if the inputs are null.
   */
  public BlurCommand(ArrayList<String> inputs, IImageController controller) throws IllegalArgumentException {
    super(inputs, controller);
  }

  /**
   * The testing constructor for BlurCommand.
   *
   * @param inputs the strings that the user inputs in the command line.
   * @throws IllegalArgumentException if the inputs are null.
   */
  public BlurCommand(ArrayList<String> inputs) throws IllegalArgumentException {
    super(inputs);
  }

  @Override
  public void execute() throws IllegalArgumentException {
    if (!valid()) {
      throw new IllegalArgumentException("Invalid inputs. Input must be: blur " +
              "image-name dest-image-name");
    }
    String name = this.inputs.get(1);
    String destName = this.inputs.get(2);
    Image image = this.controller.getImage(name);
    this.controller.addImage(destName, image.blur());
  }


}
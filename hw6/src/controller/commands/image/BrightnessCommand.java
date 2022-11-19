package controller.commands.image;

import java.util.ArrayList;

import controller.IImageController;

import model.Image;


/**
 * Represents a known command to make an image with increased or decreased brightness.
 */
public class BrightnessCommand extends AbstractCommand {

  private static final int EXPECTED_NUMBER_ARGS = 4;

  private IImageController controller;

  /**
   * The constructor for BrightnessCommand.
   *
   * @param inputs the strings that the user inputs in the command line.
   * @throws IllegalArgumentException if the inputs are null.
   */
  public BrightnessCommand(ArrayList<String> inputs, IImageController controller) throws IllegalArgumentException {
    super(inputs);
    this.controller = controller;
  }
  /**
   * The testing constructor for BrightnessCommand.
   *
   * @param inputs the strings that the user inputs in the command line.
   * @throws IllegalArgumentException if the inputs are null.
   */
  public BrightnessCommand(ArrayList<String> inputs) throws IllegalArgumentException {
    super(inputs);
  }

  @Override
  public void execute() throws IllegalArgumentException {
    if (!valid()) {
      throw new IllegalArgumentException("Invalid inputs. Input must be: brighten increment "
              + "image-name dest-image-name");
    }
    String name = this.inputs.get(2);
    String destName = this.inputs.get(3);
    int increment = Integer.parseInt(this.inputs.get(1));

    Image image = this.controller.getImage(name);
    this.controller.addImage(destName, image.changeBrightness(increment));
  }

  @Override
  protected boolean valid() {
    // run this first or else will index out of bounds
    if (EXPECTED_NUMBER_ARGS != inputs.size()) {
      return false;
    }
    // check to see if the second input is an int
    try {
      int increment = Integer.parseInt(inputs.get(1));
    } catch (NumberFormatException e) {
      return false;
    }

    return true;
  }
}

package controller.commands.image;

import java.util.ArrayList;

import controller.IImageController;

import model.Image;

/**
 * Represents the known commands to make a flipped image.
 */
public class FlipCommand extends AbstractCommand {

  private ArrayList<String> inputs;
  private IImageController controller;

  /**
   * The constructor for FlipCommand.
   *
   * @param inputs the strings that the user inputs in the command line.
   * @throws IllegalArgumentException if the inputs are null.
   */
  public FlipCommand(ArrayList<String> inputs, IImageController controller) throws IllegalArgumentException {
    super(inputs, controller);
  }

  /**
   * The testing constructor for FlipCommand.
   *
   * @param inputs the strings that the user inputs in the command line.
   * @throws IllegalArgumentException if the inputs are null.
   */
  public FlipCommand(ArrayList<String> inputs) throws IllegalArgumentException {
    super(inputs);
  }

  @Override
  public void execute() throws IllegalArgumentException {
    if (!valid()) {
      throw new IllegalArgumentException("Invalid inputs. Inputs must be: direction image-name "
              + "dest-image-name");
    }
    boolean isVertical = inputs.get(0).equalsIgnoreCase("vertical-flip");
    String name = inputs.get(1);
    String destName = inputs.get(2);
    Image image = this.controller.getImage(name);
    this.controller.addImage(destName, image.flip(isVertical));
  }

}

package controller.commands.image;


import java.util.ArrayList;

import controller.IImageController;


/**
 * Represents a command that a user inputs.
 */
public abstract class AbstractCommand implements IImageCommand {
  // todo: is there a way we can extract the inputs from the controller and get rid of this field?
  protected final ArrayList<String> inputs;
  protected IImageController controller;
  private static final int EXPECTED_NUMBER_ARGS = 3;

  /**
   * Constructor for an AbstractCommand.
   *
   * @param inputs the strings that the user inputs in the command line.
   * @throws IllegalArgumentException if inputs are null.
   */
  AbstractCommand(ArrayList<String> inputs) throws IllegalArgumentException {
    if (inputs == null) {
      throw new IllegalArgumentException("Inputs cannot be null");
    }
    this.inputs = inputs;
  }

  AbstractCommand(ArrayList<String> inputs, IImageController controller) throws IllegalArgumentException {
    if (inputs == null) {
      throw new IllegalArgumentException("Inputs cannot be null");
    }
    this.inputs = inputs;
    this.controller = controller;
  }

  /**
   * Executes/runs the filter.
   * @throws IllegalArgumentException If inputs are invalid.
   */
  public abstract void execute() throws IllegalArgumentException;

  /**
   * Checks if the processor was given invalid inputs.
   *
   * @return true if input is invalid.
   */
  protected boolean valid() {
    // Returns true if the command is passed the wrong number of inputs
    // or an image with the given name doesn't exist
    return inputs.size() == EXPECTED_NUMBER_ARGS
            || !this.hasNonexistentImage(inputs.get(1));
  }


  /**
   * Checks whether an image with the given name exists in the model.
   *
   * @param name the user-inputted name
   * @return true if the processor does not contain an image with the given name, false otherwise
   */
  protected final boolean hasNonexistentImage(String name) {
    try {
      controller.getImage(name);
    } catch (IllegalArgumentException e) {
      return true;
    }

    return false;
  }
}

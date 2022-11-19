package controller.commands.file;

import java.util.ArrayList;

import controller.IImageController;
import controller.commands.image.IImageCommand;

import static controller.AbstractImageController.readPPM;

public class PPMLoadCommand implements IImageCommand {
  private ArrayList<String> inputs;
  private IImageController controller;

  public PPMLoadCommand(ArrayList<String> inputs, IImageController controller) {
    this.inputs = inputs;
    this.controller = controller;
  }

  @Override
  public void execute() throws IllegalArgumentException {
    if (inputs.size() != 3) {
      throw new IllegalArgumentException("Invalid inputs. Inputs must be: load file-path "
              + "image-name");
    }

    String imagePath = inputs.get(1);
    String imageName = inputs.get(2);

    this.controller.addImage(imageName, readPPM(imagePath));
  }
}

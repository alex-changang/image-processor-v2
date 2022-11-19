package controller.commands.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import controller.IImageController;
import controller.commands.image.IImageCommand;
import model.Image;

import static controller.AbstractImageController.savePPM;

public class PPMSaveCommand implements IImageCommand {

  private ArrayList<String> inputs;
  private IImageController controller;

  public PPMSaveCommand(ArrayList<String> inputs, IImageController controller) {
    this.inputs = inputs;
    this.controller = controller;
  }

  @Override
  public void execute() throws IllegalArgumentException {
    if (inputs.size() != 3) {
      throw new IllegalArgumentException("Invalid inputs. " + "Inputs must be: save file-path "
              + "image-name");
    }

    String imagePath = inputs.get(1);
    String imageName = inputs.get(2);
    String fileType = imagePath.substring(imagePath.length() - 4);
    Image toSave = this.controller.getImage(imageName);
    FileOutputStream file;

    try {

      file = new FileOutputStream(imagePath);
      if (fileType.equalsIgnoreCase(".ppm")) {
        savePPM(file, toSave);
      } else {
        throw new IllegalArgumentException("Path must have .ppm extension");
      }

    } catch (IOException e) {
      throw new IllegalArgumentException("Write to file failed.");
    }
  }
}

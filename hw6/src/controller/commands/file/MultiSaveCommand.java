package controller.commands.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import controller.IImageController;

import controller.commands.image.IImageCommand;
import model.Image;

import static controller.AbstractImageController.savePPM;

public class MultiSaveCommand implements IImageCommand {

  private ArrayList<String> inputs;
  private IImageController controller;

  public MultiSaveCommand(ArrayList<String> inputs, IImageController controller) {
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
    Image toSave = this.controller.getImage(imageName);

    // stores file extension as String
    String fileType = imagePath.substring(imagePath.length() - 4);

    FileOutputStream fileOutput;
    File file = new File(imagePath);

    try {
      fileOutput = new FileOutputStream(file);

      // check for PPM extension
      if (fileType.equalsIgnoreCase(".ppm")) {
        savePPM(fileOutput, toSave);
      }

      // check for other file types
      else if (fileType.equalsIgnoreCase(".png")
              || fileType.equalsIgnoreCase(".jpg")
              || fileType.equalsIgnoreCase(".bmp")) {
        BufferedImage toSaveBuffer = toSave.makeBufferedImage();

        ImageIO.write(toSaveBuffer, fileType.substring(1), fileOutput);

      } else {
        throw new IllegalArgumentException("Error: File extension must be '.ppm', '.png', '.jpg', "
                + "or '.bmp'.\n");
      }

    } catch (IOException io) {
      throw new IllegalArgumentException("Write to file failed.");
    }
  }
}

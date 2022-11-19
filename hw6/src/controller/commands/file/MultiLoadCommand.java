package controller.commands.file;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import controller.IImageController;
import controller.commands.image.IImageCommand;
import model.Image;
import model.Pixel;

import static controller.AbstractImageController.readPPM;

public class MultiLoadCommand implements IImageCommand {

  private ArrayList<String> inputs;
  private IImageController controller;

  public MultiLoadCommand(ArrayList<String> inputs, IImageController controller) {
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
    String fileType = imagePath.substring(imagePath.length() - 4);

    BufferedImage img = null;
    Image toLoad;

    try {
      if (fileType.equalsIgnoreCase(".ppm")) {
        toLoad = readPPM(imagePath);

      } else {
        img = ImageIO.read(new File(imagePath));

        Raster r = img.getRaster();

        int height = r.getHeight();
        int width = r.getWidth();
        Pixel[][] pixels = new Pixel[height][width];

        for (int cols = 0; cols < width; cols++) {
          for (int rows = 0; rows < height; rows++) {
            int[] vals = new int[4];
            r.getPixel(cols, rows, vals);
            pixels[rows][cols] = new Pixel(vals[0], vals[1], vals[2]);
          }
        }
        toLoad = new Image(pixels);
      }

    } catch (IOException e) {
      throw new IllegalArgumentException("Image cannot be found. Accepted file types are BPM,"
              + " JPG, PNG, and PPM");
    }
    this.controller.addImage(imageName, toLoad);
  }
}

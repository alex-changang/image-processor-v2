package controller.commands.file;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.IFeaturesController;
import controller.IImageController;
import controller.commands.image.IImageCommand;
import model.Image;
import model.Pixel;

public class GUILoadCommand implements IImageCommand {
  private IImageController controller;

  public GUILoadCommand(IImageController controller) {
    this.controller = controller;
  }

  /**
   * Loads in the image
   */
  @Override
  public void execute() {

    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Valid image types",
            "png", "jpg", "bmp", "ppm");
    fileChooser.setFileFilter(filter);
    String path = "";
    // todo: figure out what to put inside fileChooser
    int returnVal = fileChooser.showOpenDialog(null);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      path = fileChooser.getSelectedFile().getAbsolutePath();
    }

    BufferedImage img = null;
    try {
      img = ImageIO.read(new File(path));
    } catch (IOException e) {
      throw new IllegalArgumentException("Image could not be saved");
    }

    Raster r = img.getRaster();
    Image toLoad = null;

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

    this.controller.addImage("", toLoad);
  }

}

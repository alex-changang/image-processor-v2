package view.listener;

import controller.IImageController;
import model.Image;
import model.Pixel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BaseActionListener implements ActionListener {
  private IImageController controller;

  public BaseActionListener(IImageController controller) {
    this.controller = controller;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    ArrayList<String> arguments = new ArrayList<>();
    arguments.add(command);
    if (command.equalsIgnoreCase("load")) {
      File file = showFileDialog();

      arguments.add(file.getAbsolutePath());
      arguments.add(file.getName());
    }
    this.controller.executeCommand(command, arguments);
  }

  private File showFileDialog() {
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

    return fileChooser.getSelectedFile();
  }
}

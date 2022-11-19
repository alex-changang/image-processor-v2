//package controller.commands.file;
//
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import javax.swing.filechooser.FileNameExtensionFilter;
//
//import controller.IFeaturesController;
//import controller.commands.image.IImageCommand;
//import model.Image;
//
//public class GUISaveCommand implements IImageCommand {
//  private IFeaturesController controller;
//
//  public GUISaveCommand(IFeaturesController controller) {
//    this.controller = controller;
//  }
//  @Override
//  public void execute() {
//    JFileChooser fileChooser = new JFileChooser();
//
//    int chooseResponse = fileChooser.showSaveDialog(null);
//    fileChooser.setFileFilter(new FileNameExtensionFilter("Image extensions",
//            "png", "jpg", "bpm", "ppm"));
//
//    // if they click approve
//    if (chooseResponse == JFileChooser.APPROVE_OPTION) {
//      File f = fileChooser.getSelectedFile();
//
//      String imagePath = f.getAbsolutePath();
//      String imageExtension = imagePath.substring(imagePath.length() - 4);
//
//      if (imageExtension.equals(".ppm")) {
//        // save as ppm
//      }
//      // todo: add something that can actually make an image (maybe turn this into getCurrentImage?)
//      BufferedImage img = this.controller.makeImageIcon();
//
//      if (img == null) {
//        // do something
//      }
//
//      File file = new File(imagePath);
//
//      try {
//        ImageIO.write(img, imageExtension.substring(imageExtension.length() - 1),
//                new FileOutputStream(file));
//      } catch (IOException e) {
//        // figure out how to render message from here
//        return;
//      }
//
//    }
//
//    // figure out how to render message from here
////    this.view.renderMessage("File saved.");
//  }
//
//    }
//
//    // if they click cancel
//
//  }
//}

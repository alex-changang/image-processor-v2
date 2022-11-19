package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.commands.image.BlurCommand;
import controller.commands.image.BrightnessCommand;
import controller.commands.image.FlipCommand;
import controller.commands.image.GreyscaleColorCommand;
import controller.commands.image.GreyscaleCommand;
import controller.commands.image.IImageCommand;
import controller.commands.image.SepiaCommand;
import controller.commands.image.SharpenCommand;
import model.Image;
import view.GUIView;
import view.IImageView;

public class GUIController implements IFeaturesController {
  // todo: do we need this image field?  may need to change this
  private Image image;
  private IImageController delegate;
  private IImageView view;
  private final Map<String, BiFunction<ArrayList<String>, IImageController, IImageCommand>> commands;

  // todo: what to do with image
  public GUIController(IImageController delegate) {
    this.image = null;
    this.delegate = delegate;
    this.view = new GUIView();
    this.commands = new HashMap<>();

    this.commands.put("blur", (s, i) -> new BlurCommand(s, i));
    this.commands.put("brightness", (s, i) -> new BrightnessCommand(s, i));
    this.commands.put("vertical-flip", (s, i) -> new FlipCommand(s, i));
    this.commands.put("horizontal-flip", (s, i) -> new FlipCommand(s, i));
    this.commands.put("red-component", (s, i) -> new GreyscaleCommand(s, i));
    this.commands.put("green-component", (s, i) -> new GreyscaleCommand(s, i));
    this.commands.put("blue-component", (s, i) -> new GreyscaleCommand(s, i));
    this.commands.put("value-component", (s, i) -> new GreyscaleCommand(s, i));
    this.commands.put("intensity-component", (s, i) -> new GreyscaleCommand(s, i));
    this.commands.put("luma-component", (s, i) -> new GreyscaleCommand(s, i));
    this.commands.put("greyCT", (s, i) -> new GreyscaleColorCommand(s, i));
    this.commands.put("sepia", (s, i) -> new SepiaCommand(s, i));
    this.commands.put("sharpen", (s, i) -> new SharpenCommand(s, i));
    // todo: what to do with gui load and save command? they take features controllers

    // now that we have a controller, add it as a listener to the view
//    this.view.addListeners(this);
  }


  @Override
  public void executeFilter(String command) {
    // do command pattern to apply the right filter (we make the right input,
    // then update the image in controller by getting
    // todo: figure out what to do with string name
    String imageName = "";
    ArrayList<String> inputs = new ArrayList<String>();
    inputs.add(command);

    // add extra inputs
    if (command.equals("vertical-flip")) {
      inputs.add("true");
    }
    else if (command.equals("horizontal-flip")) {
      inputs.add("false");
    }
    else if (command.contains("brightness")) {
      String[] split = command.split(" ");
      if (split[1].equals("")) {
        // todo: figure this out
//        this.view.displayMessage("Brightness must have an increment");
      }
      // "brightness"
      inputs.add(split[0]);
      // "increment"
      inputs.add(split[1]);
    }
    inputs.add(imageName);
    inputs.add(imageName);

    BiFunction<ArrayList<String>, IImageController, IImageCommand> filterObject =
            this.commands.getOrDefault(command, null);

    // todo: I feel like this will never trigger
    if (command == null) {
//      this.view.displayMessage("Invalid filter. Did you click a wrong button?");
    }
    else {
      try {
        filterObject.apply(inputs, this.delegate).execute();
//        this.view.displayMessage(command + " filter applied");
      } catch (IllegalArgumentException e) {
//        this.view.displayMessage(command + " filter failed");
      }
    }

    // shows the correct image
    this.image = this.delegate.getImage(imageName);
    this.makeImageIcon();
  }

  @Override
  public void makeImageIcon() {
    BufferedImage img = this.delegate.getImage("").makeBufferedImage();

    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    try {
      ImageIO.write(img, "jpg", byteOut);
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to render image.");
    }

    byte[] bytes = byteOut.toByteArray();

    ImageIcon imageIcon = new ImageIcon(bytes);

//    this.view.displayImage(imageIcon);
  }


}

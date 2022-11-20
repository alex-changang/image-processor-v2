package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.commands.file.PPMLoadCommand;
import controller.commands.file.PPMSaveCommand;

import controller.commands.image.BrightnessCommand;
import model.CommandModel;
import model.Image;
import view.IImageView;


/**
 * Represents a controller for an image processor.
 */
public class SimpleImageController extends AbstractImageController implements IImageController {

  /**
   * The constructor for a controller.
   *
   * @param view         the view
   * @param instructions the input
   * @throws IllegalArgumentException if any of the given parameters are null
   */
  public SimpleImageController(IImageView view, Readable instructions) throws IllegalArgumentException {
    super(view, instructions);
    // The following commands are specific to the Simple Controller.
    this.filters.put("load", s -> new PPMLoadCommand(s, this));
    this.filters.put("save", s -> new PPMSaveCommand(s, this));

    List<CommandModel> fileCommands = getCommands(FILE_CATEGORY);

    fileCommands.add(new CommandModel("load", "Load",
            "load image-path image-name",
            fileCommands.size() + 1, "hw6/src/view/icons/load_icon-01.png"));

    fileCommands.add(new CommandModel("save", "Save",
            "save image-path image-name",
            fileCommands.size() + 1, "hw6/src/view/icons/save_icon-01.png"));

  }

  /**
   * The testing constructor for a controller.
   *
   * @param images the images in this processor
   * @param view         the view
   * @param instructions the input
   * @throws IllegalArgumentException if any of the given parameters are null
   */
  public SimpleImageController(Map<String, Image> images, IImageView view, Readable instructions) throws IllegalArgumentException {
    super(images, view, instructions);
    // The following commands are specific to the Simple Controller.
    this.filters.put("load", s -> new PPMLoadCommand(s, this));
    this.filters.put("save", s -> new PPMSaveCommand(s, this));
  }

  @Override
  public void executeFilter() throws IllegalStateException {

    // Not sure if this should be the controller's job, but it's OK coz it's delegating it
    // to the view to render.
    this.welcomeMessage();
    this.view.initialize(commandMap, this);

    // I'm also not sure if the controller should be reading the user's input.
    // In this assignment, we are using commandLine, so you kinda have a "CommandLine-based Controller",
    // but on the other hand, is it the View's job to know what the user interface is?
    // What would happen when we go to a GUI-based version?

    // scan the readable instructions into array of strings (inputs)
    boolean quit = false;
    Scanner s = new Scanner(this.instructions);
    ArrayList<String> inputs;
    while (s.hasNextLine() && !quit) {
      String nextInput = s.nextLine();
      inputs = parseInstructions(nextInput);
      quit = executeHelper(inputs);
    }
  }

  @Override
  public void executeCommand(String command, ArrayList<String> arguments) {

  }
}

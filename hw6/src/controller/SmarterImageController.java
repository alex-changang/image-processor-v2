package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import controller.commands.file.MultiLoadCommand;
import controller.commands.file.MultiSaveCommand;
import controller.commands.image.BlurCommand;
import controller.commands.image.GreyscaleColorCommand;
import controller.commands.image.SepiaCommand;
import controller.commands.image.SharpenCommand;
import model.CommandModel;
import model.Image;
import view.IImageView;


/**
 * Represents a new controller with support for some additional commands and png, jpg and bmp files.
 */
public class SmarterImageController extends AbstractImageController implements IImageController {
  /**
   * The constructor for a SmarterImageController.
   *
   * @param view         the view
   * @param instructions the input
   * @throws IllegalArgumentException if any of the given parameters are null
   */
  public SmarterImageController(IImageView view, Readable instructions) throws IllegalArgumentException {
    super(view, instructions);

    List<CommandModel> editCommands = getCommands(EDIT_CATEGORY);

    // The following commands are specific to the Smarter Controller.
    this.filters.put("blur", s -> new BlurCommand(s, this));
    editCommands.add(new CommandModel("blur", "Blur",
            "Blur image: blur image-name dest-image-name",
            editCommands.size() + 1, null));

    this.filters.put("sharpen", s -> new SharpenCommand(s, this));
    editCommands.add(new CommandModel("sharpen", "Sharpen",
            "Sharpen image: sharpen image-name dest-image-name",
            editCommands.size() + 1, null));

    this.filters.put("sepia", s -> new SepiaCommand(s, this));
    editCommands.add(new CommandModel("sepia", "Sepia",
            "Sepia image: sepia image-name dest-image-name",
            editCommands.size() + 1, null));

    this.filters.put("grey-ct", s -> new GreyscaleColorCommand(s, this));
    editCommands.add(new CommandModel("grey-ct", "Greyscale via Color Transformation",
            "Grey image by color transformation: grey-ct image-name dest-image-name",
            editCommands.size() + 1, null));

    List<CommandModel> fileCommands = getCommands(FILE_CATEGORY);
    this.filters.put("load", s -> new MultiLoadCommand(s, this));
    fileCommands.add(new CommandModel("load", "Load",
            "Load an image: load image-path image-name",
            fileCommands.size() + 1,
            "hw6/src/view/icons/load_icon-01.png"));

    this.filters.put("save", s -> new MultiSaveCommand(s, this));
    fileCommands.add(new CommandModel("save", "Save",
            "Save an image: save image-path image-name",
            fileCommands.size() + 1,
            "hw6/src/view/icons/save_icon-01.png"));

    this.welcomeMessage();
    this.view.initialize(commandMap);
  }

  /**
   * The testing constructor for a controller.
   *
   * @param images the images in this processor
   * @param view         the view
   * @param instructions the input
   * @throws IllegalArgumentException if any of the given parameters are null
   */
  public SmarterImageController(Map<String, Image> images, IImageView view, Readable instructions) throws IllegalArgumentException {
    super(view, instructions);
    // The following commands are specific to the Smarter Controller.
    this.filters.put("blur", s -> new BlurCommand(s, this));
    this.filters.put("sharpen", s -> new SharpenCommand(s, this));
    this.filters.put("sepia", s -> new SepiaCommand(s, this));
    this.filters.put("grey-ct", s -> new GreyscaleColorCommand(s, this));
    this.filters.put("load", s -> new MultiLoadCommand(s, this));
    this.filters.put("save", s -> new MultiSaveCommand(s, this));
  }

  @Override
  public void executeFilter() {
    // scan the readable instructions into array of strings (inputs)
    Scanner s = new Scanner(this.instructions);
    ArrayList<String> inputs;
    while (s.hasNextLine()) {
      String nextInput = s.nextLine();
      inputs = parseInstructions(nextInput);

      // if a command is entered to read a file
      if (inputs.size() == 2 && inputs.get(0).equals("-file")) {

        // check to make sure is a .txt file
        String filePath = inputs.get(1);
        if (!filePath.substring(filePath.length() - 4).contains(".txt")) {
          this.tryRenderMessage("Unable to read file. File must be a .txt file.\n");
        }

        // start reading file
        this.tryRenderMessage("Reading instructions from file.\n");
        // try to get file
        Scanner sc;
        try {
          sc = new Scanner(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
          throw new IllegalArgumentException("File not found\n");
        }

        // read through file line by line, handing off instructions to helper
        while (sc.hasNextLine()) {
          inputs = this.parseInstructions(sc.nextLine());
          this.executeHelper(inputs);
        }

        this.tryRenderMessage("Finished file instructions. Exiting image editor. Thank you!\n");
        break;
      } else {
        this.executeHelper(inputs);
      }
    }
  }

  @Override
  protected void welcomeMessage() {
    this.tryRenderMessage("Welcome to our image editor. Commands are as follows:\n\n"
            + "At any time, press 'q + enter' to quit the image editor.\n");
  }
}

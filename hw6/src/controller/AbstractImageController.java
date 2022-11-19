package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

import controller.commands.image.BrightnessCommand;
import controller.commands.image.FlipCommand;
import controller.commands.image.GreyscaleCommand;

import controller.commands.image.IImageCommand;
import model.CommandCategory;
import model.CommandModel;
import model.Image;
import model.Pixel;
import view.IImageView;

/**
 * Represents an abstract controller for an image processor.
 */
public abstract class AbstractImageController implements IImageController {
  protected final IImageView view;
  protected final Readable instructions;
  protected final Map<String, Function<ArrayList<String>, IImageCommand>> filters;

  protected final Map<CommandCategory, List<CommandModel>> commandMap;

  public static final CommandCategory FILE_CATEGORY = new CommandCategory("File", 1);
  public static final CommandCategory EDIT_CATEGORY = new CommandCategory("Edit", 2);
  private Map<String, Image> images;


  /**
   * The constructor for a controller.
   *
   * @param view         the view
   * @param instructions the input
   * @throws IllegalArgumentException if any of the given parameters are null
   */
  public AbstractImageController(IImageView view, Readable instructions) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("Image processor, view, and instructions cannot be null");
    }

    this.view = view;
    this.instructions = instructions;

    this.filters = new HashMap<String, Function<ArrayList<String>, IImageCommand>>();
    // Command category: List of CommandModels
    this.commandMap = new HashMap<>();
    List<CommandModel> editCommands = getCommands(EDIT_CATEGORY);

    this.filters.put("brighten", s -> new BrightnessCommand(s, this));
    editCommands.add(new CommandModel("brighten", "Brighten/Darken",
            "Brighten or darken an image: brighten increment image-name dest-image-name",
            editCommands.size() + 1, null));

    this.filters.put("red-component", s -> new GreyscaleCommand(s, this));
    editCommands.add(new CommandModel("red-component", "Greyscale via Red Component",
            "red-component image-name dest-image-name",
            editCommands.size() + 1, null));

    this.filters.put("blue-component", s -> new GreyscaleCommand(s, this));
    editCommands.add(new CommandModel("blue-component", "Greyscale via Blue Component",
            "blue-component image-name dest-image-name",
            editCommands.size() + 1, null));

    this.filters.put("green-component", s -> new GreyscaleCommand(s, this));
    editCommands.add(new CommandModel("green-component", "Greyscale via Green Component",
            "green-component image-name dest-image-name",
            editCommands.size() + 1, null));

    this.filters.put("value-component", s -> new GreyscaleCommand(s, this));
    editCommands.add(new CommandModel("value-component", "Greyscale via Value Component",
            "value-component image-name dest-image-name",
            editCommands.size() + 1, null));

    this.filters.put("intensity-component", s -> new GreyscaleCommand(s, this));
    editCommands.add(new CommandModel("intensity-component", "Greyscale via Intensity Component",
            "intensity-component image-name dest-image-name",
            editCommands.size() + 1, null));

    this.filters.put("luma-component", s -> new GreyscaleCommand(s, this));
    editCommands.add(new CommandModel("luma-component", "Greyscale via Luma Component",
            "luma-component image-name dest-image-name",
            editCommands.size() + 1, null));

    this.filters.put("vertical-flip", s -> new FlipCommand(s, this));
    editCommands.add(new CommandModel("vertical-flip", "Vertical Flip",
            "Vertically flip an image: vertical-flip image-name dest-image-name",
            editCommands.size() + 1, "hw6/src/view/icons/vertical-flip-01.png"));

    this.filters.put("horizontal-flip", s -> new FlipCommand(s, this));
    editCommands.add(new CommandModel("horizontal-flip", "Horizontal Flip",
            "Horizontally flip an image: horizontal-flip image-name dest-image-name",
            editCommands.size() + 1, "hw6/src/view/icons/horizontal-flip-01.png"));

    this.images = new HashMap<>();
  }

  /**
   * The testing constructor for a controller.
   *
   * @param images the images in this processor
   * @param view         the view
   * @param instructions the input
   * @throws IllegalArgumentException if any of the given parameters are null
   */
  public AbstractImageController(Map<String, Image> images, IImageView view,
                                 Readable instructions) throws IllegalArgumentException {
    this(view, instructions);
    this.images = images;
  }

  /**
   * Returns the list of commands for the requested category.
   * Creates the list if it doesn't exist yet.
   *
   * @param commandCategory
   * @return List of Commands
   */
  protected List<CommandModel> getCommands(CommandCategory commandCategory) {
    List<CommandModel> commands;
    if (commandMap.containsKey(commandCategory)) {
      commands = commandMap.get(commandCategory);
    }
    else {
      commands = new ArrayList<>();
      commandMap.put(commandCategory, commands);
    }
    return commands;
  }

  /**
   * Reads in a PPM file as an Image.
   *
   * @param filename the name of the PPM file
   * @return an Image
   * @throws IllegalArgumentException if the file is not found.
   */
  public static Image readPPM(String filename) throws IllegalArgumentException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Image not found");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    // now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    Pixel[][] pixels = new Pixel[height][width];


    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Pixel pixel = new Pixel(r, g, b);
        pixels[i][j] = pixel;
      }
    }

    Image image = new Image(pixels, height, width, maxValue);

    return image;
  }

  public static void savePPM(FileOutputStream file, Image toSave) throws IOException {
    file.write("P3".getBytes());
    file.write(toSave.toString().getBytes());
    file.close();
  }

  /**
   * Reads and executes the commands given by the user.
   */
  // TODO: switch executeFilter and executeHelper, makes more sense if helper is in subclasses
  public abstract void executeFilter();

  /**
   * @param inputs
   * @return true if quit command received
   */
  protected boolean executeHelper(ArrayList<String> inputs) {

    // no input
    if (inputs.size() == 0) {
      this.tryRenderMessage("No input given\n");
    }

    // get the next input
    String rawCommand = inputs.get(0).toLowerCase();
    if (rawCommand.equalsIgnoreCase("q")) {
      this.tryRenderMessage("Image editor quit. Thank you!\n");
      // Returning true indicates that the quit command was detected.
      // As per my comment above, if we designed the code the other way, we wouldn't have
      // needed to pass this "indicator" to tell the subclass to quit.
      return true;
    }

    // try to run a command. if the input is put in incorrectly, we will transmit a message,
    // incorrect messages are ones that don't start with a valid command

    Function<ArrayList<String>, IImageCommand> command = this.filters.getOrDefault(rawCommand,
            null);

    // command was not given first
    if (command == null) {
      this.tryRenderMessage("Invalid input. Inputs must begin with a command.\n");

    }
    // command was given first, so we can use command pattern
    else {
      try {
        command.apply(inputs).execute();
        this.tryRenderMessage(rawCommand + " filter applied\n");
      } catch (IllegalArgumentException e) {
        this.tryRenderMessage(e.getMessage() + "\n");
      }
    }

    return false;
  }

  protected ArrayList<String> parseInstructions(String input) {
    String[] split = input.split(" ");
    ArrayList<String> inputs = new ArrayList<>();
    Collections.addAll(inputs, split);
    return inputs;
  }

  protected void tryRenderMessage(String message) throws IllegalStateException {
    try {
      this.view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Transmit failed.");
    }
  }

  protected void welcomeMessage() {
    this.tryRenderMessage("Welcome to our image editor. Commands are as follows:\n\n"
            + "At any time, press 'q + enter' to quit the image editor.\n");
  }

  @Override
  public void addImage(String name, Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Cannot add a null image");
    }
    // if the image already exists
    if (this.images.containsKey(name)) {
      this.images.replace(name, image);
    }
    // the image doesn't exist
    else {
      this.images.put(name, image);
    }
  }

  @Override
  public Image getImage(String name) throws IllegalArgumentException {
    if (!this.images.containsKey(name)) {
      throw new IllegalArgumentException("Image does not exist");
    }
    return this.images.get(name);
  }
}
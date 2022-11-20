package view;

import controller.IImageController;
import model.CommandCategory;
import model.CommandModel;
import model.Image;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a simple view for an image processor.
 */
public class SimpleImageView implements IImageView {
  private final Appendable out;

  private IImageController controller;

  /**
   * The default constructor for a SimpleImageView.
   *
   * @param out the output
   * @throws IllegalArgumentException if either is null
   */
  public SimpleImageView(Appendable out)
          throws IllegalArgumentException {
    if (out == null) {
      throw new IllegalArgumentException("output cannot be null");
    }
    this.out = out;
  }

  /**
   * The convenience constructor for a SimpleImageView that works in the command line.
   */
  public SimpleImageView() {
    this(System.out);
  }

  public void initialize(Map<CommandCategory, List<CommandModel>> commands, IImageController controller) {
    this.controller = controller;

    // todo: make this
    try {
      this.out.append("Available commands:\n");

      List<CommandCategory> sortedCategories = commands.keySet().stream()
              .sorted(Comparator.comparingInt(CommandCategory::getSortOrder))
              .collect(Collectors.toList());
      for (CommandCategory commandCategory : sortedCategories) {
        List<CommandModel> commandList = commands.get(commandCategory);
        List<CommandModel> sortedCommandList = commandList.stream()
                .sorted(Comparator.comparingInt(CommandModel::getSortOrder))
                .collect(Collectors.toList());
        for (CommandModel command : sortedCommandList) {
          this.out.append("  " + command.getDisplayName() + ": " + command.getCommandHelp());
          this.out.append("\n");
        }
      }
    } catch (IOException ioException) {
      System.err.println("Problem during initialization");
    }
  }

  @Override
  public void addListener(IImageController controller) {

  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.out.append(message);
  }

  @Override
  public void displayImage(Image image) {
    //no-op

  }
}

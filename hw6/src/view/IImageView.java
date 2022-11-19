package view;

import model.CommandCategory;
import model.CommandModel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Represents the operations for the view of an image processor.
 */
public interface IImageView {

  /**
   *
   */
  void initialize(Map<CommandCategory, List<CommandModel>> commands);

  /**
   * Renders a message in the view.
   *
   * @param message the message to be shown
   * @throws IOException if append fails.
   */
  void renderMessage(String message) throws IOException;

}

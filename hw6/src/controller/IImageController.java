package controller;

import model.Image;

import java.util.ArrayList;

/**
 * Represents the operations of a controller.
 */
public interface IImageController {
  /**
   * Reads and executes the commands given by the user.
   */
  void executeFilter();

  void addImage(String name, Image image) throws IllegalArgumentException;

  Image getImage(String name) throws IllegalArgumentException;

  void executeCommand(String command, ArrayList<String> arguments);
}

package model;

public class CommandModel {
  private int sortOrder;

  private String command;

  private String commandName;

  private String commandHelp;

  // todo: not sure
  private String parentMenu;

  // Optional
  private String imagePath;

  public CommandModel(String command, String commandName, String commandHelp, int sortOrder, String imagePath) {
    this.command = command;
    this.commandName = commandName;
    this.commandHelp = commandHelp;
    this.sortOrder = sortOrder;
    this.imagePath = imagePath;
  }

  public int getSortOrder() {
    return sortOrder;
  }

  public String getCommand() {
    return command;
  }

  public String getCommandName() {
    return commandName;
  }

  public String getCommandHelp() {
    return commandHelp;
  }

  public String getParentMenu() {
    return parentMenu;
  }

  public String getImagePath() {
    return imagePath;
  }
}

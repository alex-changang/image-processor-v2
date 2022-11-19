package model;

public class CommandCategory {
  private int sortOrder;

  private String categoryName;

  public CommandCategory(String categoryName, int sortOrder) {
    this.categoryName = categoryName;
    this.sortOrder = sortOrder;
  }

  public int getSortOrder() {
    return sortOrder;
  }

  public String getCategoryName() {
    return categoryName;
  }
}

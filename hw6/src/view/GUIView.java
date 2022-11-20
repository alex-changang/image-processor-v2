package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.IImageController;
import model.CommandCategory;
import model.CommandModel;
import model.Image;
import view.listener.BaseActionListener;


public class GUIView extends JFrame implements IImageView {

  private JMenuBar mb;
  private JScrollPane imageViewer;
  private JLabel imageLabel;
  private JPanel histogramViewer;
  private JSplitPane splitPane;
  private Map<String, JMenuItem> menuItems;

  private IImageController controller;

  public GUIView() {
    super();
    this.menuItems = new HashMap<>();
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setMinimumSize(new Dimension(1024, 768));

    this.histogramViewer = new JPanel();
    histogramViewer.setPreferredSize(new Dimension(400, 700));

    this.imageLabel = new JLabel();
    this.imageLabel.setHorizontalAlignment(JLabel.CENTER);

    this.imageViewer = new JScrollPane(this.imageLabel);
    imageViewer.setPreferredSize(new Dimension(600, 700));

    this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            imageViewer, histogramViewer);
    this.splitPane.setOneTouchExpandable(true);
    this.splitPane.setDividerLocation(512);

    //Provide minimum sizes for the two components in the split pane
    Dimension minimumSize = new Dimension(100, 50);
    imageViewer.setMinimumSize(minimumSize);
    histogramViewer.setMinimumSize(minimumSize);
  }

  @Override
  public void initialize(Map<CommandCategory, List<CommandModel>> commands,
                         IImageController controller) {
    this.controller = controller;

    // todo: make this
    this.addPanels();
    this.buildMenu(commands);
    try {
      // TODO: Remove - this is just a test
      BufferedImage img = ImageIO.read(new File("/Users/alexchan/Downloads/IMG_0022.jpg"));

      ImageIcon icon = new ImageIcon(img);
      this.imageLabel.setIcon(icon);
    } catch(IOException ioException) {
      //
    }
    this.add(splitPane);
    this.setVisible(true);
  }

  @Override
  public void addListener(IImageController controller) {
    ArrayList<String> arguments = new ArrayList<>();
    for (String name : this.menuItems.keySet()) {
      this.menuItems.get(name).addActionListener(evt -> controller.executeCommand(name, arguments));
    }
  }

  private void addPanels() {
  }

  private void buildMenu(Map<CommandCategory, List<CommandModel>> commands) {
    this.mb = new JMenuBar();
    BaseActionListener actionListener = new BaseActionListener(this.controller);

    List<CommandCategory> sortedCategories = commands.keySet().stream()
            .sorted(Comparator.comparingInt(CommandCategory::getSortOrder))
            .collect(Collectors.toList());
    for (CommandCategory commandCategory : sortedCategories) {
      JMenu menu = new JMenu(commandCategory.getCategoryName());
      List<CommandModel> commandList = commands.get(commandCategory);
      List<CommandModel> sortedCommandList = commandList.stream()
              .sorted(Comparator.comparingInt(CommandModel::getSortOrder))
              .collect(Collectors.toList());
      for (CommandModel command : sortedCommandList) {
        ImageIcon icon;
        JMenuItem cmd;
        if (command.getImagePath() != null) {
          icon = new ImageIcon(command.getImagePath(), command.getCommandHelp());
          cmd = new JMenuItem(command.getDisplayName(), icon);
        }
        else {
          cmd = new JMenuItem(command.getDisplayName());
        }
        cmd.setActionCommand(command.getCommand());
        cmd.addActionListener(actionListener);
        this.menuItems.put(command.getCommand(), cmd);
        menu.add(cmd);
      }
      this.mb.add(menu);
    }

    this.setJMenuBar(mb);
  }

  private void addMenu(String[] commands) {
    this.mb = new JMenuBar();
    // set file menu
    JMenu file = new JMenu("File");

    JMenuItem load = new JMenuItem("Load", new ImageIcon("hw6/src/view/icons/load_icon-01.png"));
    JMenuItem save = new JMenuItem("Save", new ImageIcon("hw6/src/view/icons/save_icon-01.png"));

    file.add(load);
    file.addSeparator();
    file.add(save);
    mb.add(file);
    this.menuItems.put("load", load);
    this.menuItems.put("save", save);

    // set edit menu
    JMenu edit = new JMenu("Edit");

    // flip group
    JMenuItem verticalFlip = new JMenuItem("Vertical Flip", new ImageIcon("hw6/src/"
            + "view/icons/vertical-flip-01.png"));
    JMenuItem horizontalFlip = new JMenuItem("Horizontal Flip", new ImageIcon("hw6/"
            + "src/view/icons/horizontal-flip-01.png"));
    edit.add(verticalFlip);
    edit.add(horizontalFlip);
    edit.addSeparator();





    // sepia
    JMenuItem sepia = new JMenuItem("Sepia");
    edit.add(sepia);
    edit.addSeparator();

    // brighten
    JMenuItem brighten = new JMenuItem("Brighten/Darken");
    final String[] inc = {""};
    // create on the fly action listener that opens an increment dialog
    brighten.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JOptionPane increment = new JOptionPane("Increment");
        inc[0] = increment.showInputDialog("Increment: ");
        System.out.println("brighten " + inc[0]);
      }
    });
    edit.add(brighten);
    edit.addSeparator();

    // blur group
    JMenuItem blur = new JMenuItem("Blur");
    JMenuItem sharpen = new JMenuItem("Sharpen");

    edit.add(blur);
    edit.add(sharpen);

    this.menuItems.put("sepia", sepia);
    this.menuItems.put("brighten" + inc[0], brighten);
    this.menuItems.put("blur", blur);
    this.menuItems.put("sharpen", sharpen);

    mb.add(edit);
    this.setJMenuBar(mb);
  }


  @Override
  public void displayImage(Image image) {
    BufferedImage img = image.makeBufferedImage();

    ImageIcon imageIcon = new ImageIcon(img);
    this.imageLabel.setIcon(imageIcon);
  }


  @Override
  public void renderMessage (String message) {
    JOptionPane msg = new JOptionPane("Message");
    msg.showMessageDialog(new JLabel(), message);
  }


}

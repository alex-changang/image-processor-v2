//package controller.commands.image;
//
//import java.util.ArrayList;
//
//
//
//
//
///**
// * Represents the known commands to make a flipped image.
// */
//// TODO: We can remove this as I have replaced it with the FlipCommand
//public class PositionCommand extends AbstractCommand {
//  private final int EXPECTED_NUMBER_ARGS = 3;
//
//
//  /**
//   * The constructor for PositionCommand.
//   *
//   * @param inputs the strings that the user inputs in the command line.
//   * @throws IllegalArgumentException if the inputs are null.
//   */
//  public PositionCommand(ArrayList<String> inputs) throws IllegalArgumentException {
//    super(inputs);
//  }
//
////
////  @Override
////  public void filter(ISmarterImageProcessor processor) throws IllegalArgumentException {
////    if (invalidInputs(processor)) {
////      throw new IllegalArgumentException("Invalid inputs. Inputs must be: direction image-name "
////              + "dest-image-name");
////    }
////    boolean isVertical = this.inputs.get(0).equalsIgnoreCase("vertical-flip");
////    String name = this.inputs.get(1);
////    String destName = this.inputs.get(2);
////    processor.flip(isVertical, name, destName);
////  }
//
//  @Override
//  public void execute() {
//    if (!valid(inputs)) {
//      throw new IllegalArgumentException("Invalid inputs. Inputs must be: direction image-name "
//              + "dest-image-name");
//    }
//    boolean isVertical = this.inputs.get(0).equalsIgnoreCase("vertical-flip");
//    String name = this.inputs.get(1);
//    String destName = this.inputs.get(2);
//    processor.flip(isVertical, name, destName);
//  }
//
//  @Override
//  protected boolean valid(ArrayList<String> args) {
//    // Returns true if the command is passed the wrong number of inputs
//    // or an image with the given name doesn't exist
//    return args.size() == EXPECTED_NUMBER_ARGS
//            || !this.hasNonexistentImage(inputs.get(1));
//  }
//
////  protected boolean invalidInputs(IImageProcessor processor) {
////    // Returns true if the command is passed the wrong number of inputs
////    // or an image with the given name doesn't exist
////    return this.hasIncorrectSize(3) || this.hasNonexistentImage(inputs.get(1));
////  }
//}

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controller.IImageController;
import controller.SmarterImageController;
import controller.commands.image.BrightnessCommand;
import controller.commands.image.BlurCommand;
import controller.commands.image.FlipCommand;
import controller.commands.image.GreyscaleColorCommand;
import controller.commands.image.GreyscaleCommand;
import controller.commands.image.IImageCommand;
import controller.commands.image.SepiaCommand;
import controller.commands.image.SharpenCommand;
import model.Image;
import model.Pixel;
import view.IImageView;
import view.SimpleImageView;

import static controller.AbstractImageController.readPPM;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Represents tests for known commands.
 */
public class CommandTest {
  private Image link;
  private IImageController controller;
  private IImageView view;
  private Map<String, Image> map;

  @Before
  public void init() {
    this.map = new HashMap<>();
    this.view = new SimpleImageView();
    Readable input = new StringReader("load hw6/res/link.ppm link\nvertical-flip link "
            + "link-vertical\nsave link-vertical.ppm link-vertical\nq");
    this.controller = new SmarterImageController(map, view, input);
    this.link = readPPM("hw6/res/link.ppm");
    map.put("link", this.link);

  }


  // test brightness

  @Test
  public void testBrightnessConstructor() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("brighten");
    inputs.add("20");
    inputs.add("link");
    inputs.add("link-vertical");

    IImageCommand test = new BrightnessCommand(inputs, controller);
    assertNotNull(test);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullInputs() {
    IImageCommand test = new BrightnessCommand(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInputs() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("brighten");
    inputs.add("20");
    inputs.add("bad");
    inputs.add("link-vertical");

    IImageCommand brightnessCommand = new BrightnessCommand(inputs, controller);
    brightnessCommand.execute();
  }

  @Test
  public void testBrightness() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("brighten");
    inputs.add("20");
    inputs.add("link");
    inputs.add("link-bright");

    Readable input = new StringReader("brighten 20 link link-bright");
    IImageController controller = new SmarterImageController(this.map, this.view, input);

    IImageCommand brightnessCommand = new BrightnessCommand(inputs, controller);
    brightnessCommand.execute();

    assertEquals(this.link.changeBrightness(20).toString(),
            controller.getImage("link-bright").toString());

    ArrayList<String> inputsDark = new ArrayList<>();
    inputsDark.add("brighten");
    inputsDark.add("-20");
    inputsDark.add("link");
    inputsDark.add("link-dark");
    Readable inputDark = new StringReader("load hw6/res/link.ppm link\nbrighten -20 link "
            + "link-dark");
    IImageController controllerDark = new SmarterImageController(map, view, inputDark);

    IImageCommand darkCommand = new BrightnessCommand(inputsDark);
    darkCommand.execute();

    assertEquals(this.link.changeBrightness(-20).toString(),
            controllerDark.getImage("link-dark").toString());
  }

  // test greyscale
  @Test
  public void testGreyscaleConstructor() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("red-component");
    inputs.add("link");
    inputs.add("link-red-component");

    IImageCommand test = new BrightnessCommand(inputs, controller);
    assertNotNull(test);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullGreyscaleInputs() {
    IImageCommand test = new GreyscaleCommand(null, controller);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGreyscaleInputs() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("red-component");

    IImageCommand brightnessCommand = new BrightnessCommand(inputs, controller);
    brightnessCommand.execute();
  }

  @Test
  public void testGreyscale() {
    ArrayList<String> inputsRed = new ArrayList<>();
    inputsRed.add("red-component");
    inputsRed.add("link");
    inputsRed.add("link-red-component");

    IImageCommand redComponent = new GreyscaleCommand(inputsRed);
    redComponent.execute();

    assertEquals(this.link.makeImageGrey(Pixel.GreyscaleType.R).toString(),
            this.controller.getImage("link-red-component").toString());

    ArrayList<String> inputsGreen = new ArrayList<>();
    inputsGreen.add("green-component");
    inputsGreen.add("link");
    inputsGreen.add("link-green-component");

    IImageCommand greenComponent = new GreyscaleCommand(inputsGreen);
    greenComponent.execute();

    assertEquals(this.link.makeImageGrey(Pixel.GreyscaleType.G).toString(),
            this.controller.getImage("link-green-component").toString());

    ArrayList<String> inputsBlue = new ArrayList<>();
    inputsBlue.add("blue-component");
    inputsBlue.add("link");
    inputsBlue.add("link-blue-component");

    IImageCommand blueComponent = new GreyscaleCommand(inputsBlue);
    blueComponent.execute();

    assertEquals(this.link.makeImageGrey(Pixel.GreyscaleType.B).toString(),
            this.controller.getImage("link-blue-component").toString());

    ArrayList<String> inputsValue = new ArrayList<>();
    inputsValue.add("value-component");
    inputsValue.add("link");
    inputsValue.add("link-value-component");

    IImageCommand valueComponent = new GreyscaleCommand(inputsValue);
    valueComponent.execute();

    assertEquals(this.link.makeImageGrey(Pixel.GreyscaleType.V).toString(),
            this.controller.getImage("link-value-component").toString());

    ArrayList<String> inputsIntensity = new ArrayList<>();
    inputsIntensity.add("intensity-component");
    inputsIntensity.add("link");
    inputsIntensity.add("link-intensity-component");

    IImageCommand intensityComponent = new GreyscaleCommand(inputsIntensity);
    intensityComponent.execute();

    assertEquals(this.link.makeImageGrey(Pixel.GreyscaleType.I).toString(),
            this.controller.getImage("link-intensity-component").toString());

    ArrayList<String> inputsLuma = new ArrayList<>();
    inputsLuma.add("luma-component");
    inputsLuma.add("link");
    inputsLuma.add("link-luma-component");

    IImageCommand lumaComponent = new GreyscaleCommand(inputsLuma);
    lumaComponent.execute();

    assertEquals(this.link.makeImageGrey(Pixel.GreyscaleType.L).toString(),
            this.controller.getImage("link-luma-component").toString());

  }


  // test position
  @Test
  public void testPositionConstructor() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("horizontal-flip");
    inputs.add("link");
    inputs.add("link-horizontal");

    IImageCommand test = new BrightnessCommand(inputs);
    assertNotNull(test);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullPositionInputs() {
    IImageCommand test = new FlipCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPositionInvalidInputs() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("vertical-flip");

    IImageCommand horizontalCommand = new FlipCommand(inputs);
    horizontalCommand.execute();
  }

  @Test
  public void testPosition() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("vertical-flip");
    inputs.add("link");
    inputs.add("link-vertical");

    IImageCommand verticalCommand = new FlipCommand(inputs);
    verticalCommand.execute();

    assertEquals(this.link.flip(true).toString(),
            this.controller.getImage("link-vertical").toString());

    ArrayList<String> inputsHorizontal = new ArrayList<>();
    inputsHorizontal.add("horizontal-flip");
    inputsHorizontal.add("link");
    inputsHorizontal.add("link-horizontal");

    IImageCommand horizontalCommand = new FlipCommand(inputsHorizontal);
    horizontalCommand.execute();

    assertEquals(this.link.flip(false).toString(),
            this.controller.getImage("link-horizontal").toString());
  }

  // test blur
  @Test
  public void testBlurConstructor() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("blur");
    inputs.add("link");
    inputs.add("blurred-link");

    IImageCommand test = new BlurCommand(inputs);
    assertNotNull(test);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullBlurInputs() {
    IImageCommand test = new BlurCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurInvalidInputs() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("vertical-flip");

    IImageCommand blurCommand = new BlurCommand(inputs);
    blurCommand.execute();
  }

  @Test
  public void testBlur() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("blur");
    inputs.add("link");
    inputs.add("blurred-link");

    IImageCommand blurCommand = new BlurCommand(inputs);
    blurCommand.execute();

    assertEquals(this.link.blur().toString(),
            this.controller.getImage("blurred-link").toString());

  }

  // test sharpen
  @Test
  public void testSharpenConstructor() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("sharpen");
    inputs.add("link");
    inputs.add("sharp-link");

    IImageCommand test = new SharpenCommand(inputs);
    assertNotNull(test);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullSharpenInputs() {
    IImageCommand test = new SharpenCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpenInvalidInputs() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("vertical-flip");

    IImageCommand sharpenCommand = new SharpenCommand(inputs);
    sharpenCommand.execute();
  }

  @Test
  public void testSharpen() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("sharpen");
    inputs.add("link");
    inputs.add("sharp-link");

    IImageCommand sharpenCommand = new SharpenCommand(inputs);
    sharpenCommand.execute();

    assertEquals(this.link.sharpen().toString(),
            this.controller.getImage("sharp-link").toString());

  }

  // test greyscale color
  @Test
  public void testGreyscaleColorConstructor() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("grey-ct");
    inputs.add("link");
    inputs.add("grey-ct-link");

    IImageCommand test = new GreyscaleColorCommand(inputs);
    assertNotNull(test);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullGreyscaleColorInputs() {
    IImageCommand test = new GreyscaleColorCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreyscaleColorInvalidInputs() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("vertical-flip");

    IImageCommand greyCTCommand = new GreyscaleColorCommand(inputs);
    greyCTCommand.execute();
  }

  @Test
  public void testGreyscaleColor() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("grey-ct");
    inputs.add("link");
    inputs.add("grey-ct-link");

    IImageCommand greyCTCommand = new GreyscaleColorCommand(inputs);
    greyCTCommand.execute();

    assertEquals(this.link.greyCT().toString(),
            this.controller.getImage("grey-ct-link").toString());

  }

  // test sepia
  @Test
  public void testSepiaConstructor() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("sepia");
    inputs.add("link");
    inputs.add("sepia-link");

    IImageCommand test = new SepiaCommand(inputs);
    assertNotNull(test);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullSepiaInputs() {
    IImageCommand test = new SepiaCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSepiaInvalidInputs() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("vertical-flip");

    IImageCommand sepiaCommand = new SepiaCommand(inputs);
    sepiaCommand.execute();
  }

  @Test
  public void testSepia() {
    ArrayList<String> inputs = new ArrayList<>();
    inputs.add("sepia");
    inputs.add("link");
    inputs.add("sepia-link");

    IImageCommand sepiaCommand = new SepiaCommand(inputs);
    sepiaCommand.execute();

    assertEquals(this.link.sepia().toString(),
            this.controller.getImage("sepia-link").toString());

  }
}

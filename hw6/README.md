*Created by Mathena Chan and Josh Wang 11/22/22*

Our design is as follows:

**Model**
- ~~`IImageProcessor`: Represents the initial image processor with some filter operations.~~
- ~~`ISmarterImageProcessor`: Represents a smarter image processor that supports all the old operations as well as some new ones.~~
- ~~`SimpleImageProcessor`: Represents the implementation of the old image processor.~~
- ~~`SmarterImageProcessor`: Represents the implementation of the new image processor.~~

- ~~**Util**~~
  - `Image`: An 2D array of pixels.
  - `Pixel`: Represents a single pixel in RGB form

**View**
- `IImageView`: Represents the visible interface that a user uses to interact with the image processor with all necessary operations 
- `SimpleImageView`: Represents a simple text-based view.

**Controller**
- `IImageController`: Allows operations to be performed between user inputs and the model/view.
- `AbstractImageController`: Abstract implementation of a controller.
- `SimpleImageController`: A controller with support for some basic commands.
  - supports `brighten`, `vertical-flip`, `horizontal-flip`, `red-component`, `blue-component`, 
  `green-component`, `value-component`, `intensity-component`, `luma-component`, `load` and `save` commands
  - supports loading and saving `.ppm` files
- `SmarterImageController`: A controller with support for basic commands as well as some additional commands.
  - supports `blur`, `sharpen`, `sepia`, and `greyct` commands
  - supports loading and saving `.png`, `.jpg` and `.bmp` files

- commands.image 
  - `IImageCommand`: Represents a command for image editing a user can input
  - `AbstractCommand`: Represents similar functionality that all user commands must support 
  - `BrightnessCommand`: Represents a command to increase or decrease brightness. 
  - `GreyscaleCommand`: Represents a command to make the image gray based on different components 
  - *(NEW)* ~~`PositionCommand`~~ `FlipCommand`: Represents a command that flips an image vertically or horizontally
  - `GreyscaleColorCommand`: Represents a command that makes an image grey through color transformation
  - `BlurCommand`: Represents a command that blurs an image
  - `SharpenCommand`: Represents a command that sharpens an image
  - `SepiaCommand`: Represents a command that makes an image sepia
- *(NEW)* commands.file
  - *(NEW)* `MultiLoadCommand`: Represents a command that loads a `.png`, `.jpg` or `.bmp` image
  - *(NEW)* `MultiSaveCommand`: Represents a command that saves a `.png`, `.jpg` or `.bmp` image
  - *(NEW)* `PPMLoadCommand`: Represents a command that loads a `.ppm` image
  - *(NEW)* `PPMSaveCommand`: Represents a command that saves a `.ppm` image


Design changes:
- DECOUPLED MODEL AND CONTROLLER: We realized our model and controller were 
highly coupled, so we refactored the functionality in the old `SimpleImageProcessor` and 
`SmarterImageProcessor` classes into the respective controllers, and deleted the processor classes and interface 
entirely. Our model currently only contains the objects that our program deals with, Image and Pixel.
- RENAMED POSITIONCOMMAND: We renamed our previous `PositionCommand` to `FlipCommand`.

**SEE USEME FILE FOR HOW TO RUN PROGRAM**



CITATIONS:
1) Image inspiration for 4x4 link from http://www.stoll.space/4x4/lddknulfzdnovm82cyi0un0fn7ysg4. 

2) Image of woman's face cropped from stock photo
3) Load icon: import by Adrien Coquet from <a href="https://thenounproject.com/icon/import-2952648/" target="_blank" title="import Icons">Noun Project</a>
4) Save icon: Save by Adrien Coquet from <a href="https://thenounproject.com/icon/save-2644504/" target="_blank" title="Save Icons">Noun Project</a>
5) Horizontal flip by Denis Klyuchnikov from <a href="https://thenounproject.com/icon/horizontal-flip-1536137/" target="_blank" title="Horizontal flip Icons">Noun Project</a>
6) vertical flip by Denis Klyuchnikov from <a href="https://thenounproject.com/icon/vertical-flip-1536132/" target="_blank" title="vertical flip Icons">Noun Project</a>

*All images have been created and are owned by Mathena Chan and Josh Wang and are authorized for use in this project.*
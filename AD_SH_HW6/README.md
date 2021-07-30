<h3>Our Design</h3>
<p>Our design has three main parts: the pixel interface (IPixel), the model interface (ImageEditorModel), and the filter 
interface (IFilter). The pixel interface is a way to represent a single pixel in an image. Currently there is 
only one implementation of the interface (PixelImpl), which stores only the pixel's rgb values and is immutable. Next
is the model interface, which is where the full images are stored as a 2D array of IPixels, and where 
filters will be applied to the image. These include blur, sepia, grayscale, and sharpen filters. 
Currently, there is also only one implementation of the 
interface (SimpleImageEditorModel). When constructing a SimpleImageEditorModel, we ensure that the 
given image is not null and is rectangular 
(no pixel is missing in the 2D array and all the inner arrays are the same length). We also create 
instances of each of the filters for the model object to use.
Finally, we have an IFilter interface, which contains 2 abstract
implementations. AColorFilter alters the colors of the image and currently has 2 subclasses: the 
GrayscaleFilter (monochrome) and SepiaFilter classes. AResFilter alters the resolutions of the image
and currently has 2 subclasses: the BlurFilter and SharpenFilter classes.</p>
<p>We also have two Utils classes: one general Utils class that handles exceptions and matrix computations, 
and another ImageUtil class that handles images (reading, writing, creation of checkerboards), as well as running 
the model with the given image and the desired commands. </p>
<h3>New Design (HW6)</h3>
<p>In order to support adding multiple photos as layers to an editor, we created a new interface, IMultiLayerEditorModel, 
with one implementation (MultiLayerEditorModel), that contains an ArrayList of SimpleImageEditorModels, 
each of which serves as a layer in the MultiLayer model. Unfortunately, 
we were unable to have this new IMultiLayerEditorModel extend our original ImageEditorModel interface 
because our new filter methods (blur, sharpen, grayscale, and sepia), now also require an index to apply to.
In addition, we also included a way for this new model to keep track of the visibility of its layers.</p>
<p>Our editor now also includes a view, called ITextView, that currently has only one implementation
(SimpleTextView). This view cannot display actual images, and only displays messages outputted by the controller 
when a command fails/other issue arises. Our controller interface, IEditorController, also currently has one 
implementation (SimpleEditorController). The interface only has one method that runs the editor 
(similar to how the controller in freecell only had a playGame method). Currently, our controller supports the following 
functionalities, which can be inputted into the terminal (more information is given in the USEME.md file):</p>

<li>creating layers</li>
<li>creating multiple layers from a directory created by this editor</li>
<li>copying layers</li>
<li>creating checkerboard layers</li>
<li>removing layers</li>
<li>exporting layers</li>
<li>exporting all layers</li>
<li>exporting the topmost layer</li>
<li>changing the visibility of layers</li>
<li>blurring layers</li>
<li>sharpening layers</li>
<li>applying grayscale filter to layers</li>
<li>applying sepia filter layers</li>

<p>Our controller uses a command design pattern with a hashmap to execute these commands.
The map is created in the main method (in the src/ImageEditor class). It is then passed into the 
controller so that the map can be updated easily with additional functionality straight from the main ImageEditor
class without having to edit parts of the controller. We have also added methods to our Utils and ImageUtil
classes in order to support the new functionality. Mainly, we made methods to support iterating through
a Scanner, as well as to parse through the txt files with the image file locations
that we create when exporting entire projects from our model.</p>

<h3>Image Bibliography</h3>
<p>Reid, Claire. “Baby Yoda Sipping His Soup Is The New Meme We've All Been Waiting For.” LADbible, 
LADbible, 1 Dec. 2019, www.ladbible.com/entertainment/viral-baby-yoda-sipping-his-soup-is-the-new-
meme-weve-all-be-waiting-for-20191201.</p>
<p>Fatih. “Photo by Fatih on Unsplash.” Beautiful Free Images &amp; Pictures, 10 Sept. 2019, 
unsplash.com/photos/vEJe7RVkM5M.</p>
<p>“HD Red Among Us Character PNG.” CityPNG, www.citypng.com/photo/4731/hd-red-among-us-character-png.</p>


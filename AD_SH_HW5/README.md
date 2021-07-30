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

<h3>Image Bibliography</h3>
<p>Reid, Claire. “Baby Yoda Sipping His Soup Is The New Meme We've All Been Waiting For.” LADbible, 
LADbible, 1 Dec. 2019, www.ladbible.com/entertainment/viral-baby-yoda-sipping-his-soup-is-the-new-
meme-weve-all-be-waiting-for-20191201.</p>
<p>Fatih. “Photo by Fatih on Unsplash.” Beautiful Free Images &amp; Pictures, 10 Sept. 2019, 
unsplash.com/photos/vEJe7RVkM5M. </p>


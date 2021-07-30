<h2>User Guide</h2>
<h3>Using the Simple Text Editor</h3>
<p>Our ImageEditor program can be loaded by running the ImageEditor.jar file located in the res folder.
Once this program is running, you will be able to enter in commands to the console/terminal. Our current 
program supports 12 different commands, most of which require additional inputs after calling the 
command. Separate commands, as well as the additional inputs required to run certain commands must 
all be separated by some sort of whitespace (spaces, newLines, etc.). The commands and their required 
inputs will be listed below (case does not matter when using these commands, just make sure they are 
spelled correctly).</p>

<h5>Creating layers</h5>
<p>layers can be created using the "create" command, followed by the filename 
to be loaded, followed by the index in the model this layer will be added to. Currently, our editor
can only support importing 3 kinds of images: .ppm, .png, and .jpg.
Overall syntax will resemble:</p>
<p>create String int</p>

<h5>Creating multiple layers</h5>
<p>multiple layers can be created using the "createall" command. Note that this command will only load
directories that have been created using this editor. This command is very similar to create, except the following 
String must now be the path to the directory. The int will now specify the starting index at which 
layers from the directory will be added. If a layer in the directory was previous invisible when it was
exported from the model, it will be imported as invisible, and similar if it was originally visible.
Overall syntax will resemble:</p>
<p>createall String int</p>

<h5>Copying layers</h5>
<p>Layers in the model can be copied using the "copy" command, followed by the 
index of the layer to be copied, followed by the destination index at which 
the copied layer will be added. Overall syntax will resemble:</p>
<p>copy int int</p>

<h5>Creating checkerboard layers</h5>
<p>Checkerboard image layers can be created programmatically using the "checkerboard" command, 
followed by a String of the color that will start in the top-left corner of the checkerboard,
followed by a second String of the second color that will compose the checkerboard, followed by three integers 
representing the size of a single checkerboard box, the width of the checkerboard in checker-boxes, 
and the height of the checkerboard in checker-boxes, respectively. The last input will be another integer
representing the index at which this checkerboard layer will be added. Overall syntax will resemble:</p>
<p>checkerboard String String int int int int</p>

<h5>Removing layers</h5>
<p>Layers can be removed using the "remove" command followed by the integer representing the index 
of the layer to be removed. Overall syntax will resemble:</p>
<p>remove int</p>

<h5>Removing all layers</h5>
<p>All the layers in the model can be removed, essentially resetting the model, using the "removeall" command.
Overall syntax will resemble:</p>
<p>removeall</p>

<h5>Changing the Visibility</h5>
<p>The visibility of a particular layer can be changed using the "changevisibility" command, followed
by an integer representing the index of the layer whose visibility you wish to change. A layer can only
be visible or invisible (this editor does not support features like opacity). Overall syntax will resemble:</p>
<p>changevisibility int</p>

<h5>Blurring layers</h5>
<p>Layers from the model can be blurred using the "blur" command, followed by an integer representing
the index of the layer to be blurred. Overall syntax will resemble:</p>
<p>blur int</p>

<h5>Sharpening layers</h5>
<p>Layers from the model can be sharpened using the "sharpen" command, followed by an integer representing
the index of the layer to be sharpened. Overall syntax will resemble:</p>
<p>sharpen int</p>

<h5>Grayscaling layers</h5>
<p>A grayscale filter can be applied to layers in the model using the "grayscale" command, followed 
by an integer representing the index of the layer to be grayscaled. Overall syntax will resemble:</p>
<p>grayscale int</p>

<h5>Sepia-ing layers</h5>
<p>A sepia filter can be applied to layers in the model using the "sepia" command, followed by an 
integer representing the index of the layer to be sepia-ed. Overall syntax will resemble:</p>
<p>sepia int</p>

<h5>Exporting layers</h5>
<p>Layers from the model can be exported using the "export" command, followed by a String representing the 
filename where the exported image will be located, followed by an integer representing the index of the 
layer to be exported. Keep in mind that the desired filetype should be included in the filepath input
(ex. res/myFirstImage.png). Overall syntax will resemble:</p>
<p>export String int</p>

<h5>Exporting all layers</h5>
<p>All the layers in the model can be exported using the "exportall" command, followed by a String representing 
the directory where all the images will be exported to (do not include a "/" after the directory path), 
followed by another String representing the desired type of image. This command will export all the 
images, no matter their visibility in the model.
Currently, the editor can only support exporting images in 3 different formats: .ppm, .png, and .jpg. 
In addition to the images, a layerLocations.txt file will also appear in the directory. This is mainly 
used when importing, and should not affect the overall functionality of the directory. Overall syntax will 
resemble:</p>
<p>exportall String String</p>

<h5>Exporting the Topmost Layer</h5>
<p>The topmost layer in the model can be exported using the "exporttopmost" command, followed by a String
representing the filename where the exported image will be located. This command will export the first
visible layer from the top. Overall syntax will resemble:</p>
<p>exporttopmost String</p>

<h5>Exiting the Editor</h5>
You can quit out of the editor by typing "quit" (don't include quotes in the console). This can be done
at any point in the script, and, like all the other commands, should be separated by whitespace.

<h5>A Note About Using the Text Scripts</h5>
<p>If you would like to use the scripts provided in the res folder:</p>
<li>Open command prompt/terminal</li>
<li>Navigate to the res folder</li>
<li>Type "java -jar ImageEditor.jar scriptname", where scriptname is TextScript1.txt or TextScript2.txt</li>
<p>You can also run this program through the main class, ImageEditor. To do this, go to the main method
and replace the args[0] argument in the try statement with either res/TextScript1.txt or res/TextScript2.txt,
depending on which one you would like to run.</p>

<h3>Using the Interactive GUI Editor</h3>
<p>To run the this editor:</p>
<li>Open command prompt/terminal</li>
<li>Navigate to the res folder</li>
<li>Type "java -jar ImageEditor.jar <i>input</i></li>
<p>to use the text editor, the italicized input should be "text". To use the script editor, input
"script", followed by the filepath of the script you want to use (separate these with whitespace). To 
use the GUI editor, input "gui".</p>

<h5>Layout</h5>
<p>The layout of the GUI editor is quite simple. In the top left corner of the editor, there are 3 items. 
First, there is a file menu, which will allow you to load single images and other projects exported by this editor,
as well as save the topmost image in the current project or save all of the layers in the project. There
are also two small labels that tell you the current layer being displayed to the user, as well as the total number of
layers in the project. </p>
<p>In the middle of the editor there is an empty space that will be where the images
appear. Every time you load an image or manipulate a layer, the editor will update that image being displayed 
to the topmost layer in the model. Finally, on the right hand side, there are two more menus. The top one, 
labeled "Image Filters" allows you to blur, sharpen, grayscale, sepia, downscale, and mosaic images 
(note that downscale will scale all the images in the editor down to preserve the size invariant). 
The bottom menu, labeled "Other", allows you to copy layers, remove layers or remove all the layers, 
toggle the visibility of layers, and create checkerboard layers. </p>
<p>Finally, on the bottom there are 
four buttons that perform the same processes as the file menu and are added for convenience.</p>

<p>In order to use any of these features, simply click on the button or menu item corresponding to the
command/filter you wish to use. The editor will then open a separate panel with further instructions.
Simply follow these instructions, until the image on the main panel changes, or, if you wish to cancel
the command, click the cancel or X button on any of the pop-up panels.</p>

<h3>Thanks for reading this USEME, and enjoy using our simple image editor!</h3>

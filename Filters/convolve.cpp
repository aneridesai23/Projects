/*
Name: Aneri Desai(anerid)
Date: 18th October, 2019
Class: CPSC 4040
Project: project4
Description: program applys the filter a user want on any png photo of user liking
             every single time user presses c key and user can get the original
             image by applying r key, save the filtered file or quit the program
*/

#include <OpenImageIO/imageio.h>
#include <iostream>
#include <GL/glut.h>
#include <fstream>
#include <math.h>

using namespace std;
OIIO_NAMESPACE_USING


struct Pixel{ // defines a pixel structure
	unsigned char r,g,b,a;
};

/*
Global variables and constants
*/
const int DEFAULTWIDTH = 600;	// default window dimensions if no image
const int DEFAULTHEIGHT = 600;

int WinWidth, WinHeight;	// window width and height
int ImWidth, ImHeight;		// image width and height
int ImChannels;           // number of channels per image pixel

int VpWidth, VpHeight;		// viewport width and height
int Xoffset, Yoffset;     // viewport offset from lower left corner of window

Pixel **pixmap = NULL;	// the image pixmap used for OpenGL
Pixel **pixmapOriginal = NULL; //pixmap for reload
double **filterArray = NULL; //to store kernel weight

int pixformat; 			// the pixel format used to correctly  draw the image
int arraySize = 0; //N value
double scaleFactor = 0;
string outfilename = "";

//global function declarations
void destroy();
int readimage(string infilename);
void displayimage();
void writeimage(string outfilename);
void handleDisplay();
void convolution();
void reload();
void handleKey(unsigned char key, int x, int y);
void handleReshape(int w, int h);
void scaleFact();
void flipVert();
void flipHoriz();
void readKernel(string filename);

/*
Main program to scan the commandline, set up GLUT and OpenGL, and start Main Loop
*/
int main(int argc, char* argv[]){
  // set up the default window and empty pixmap if no image or image fails to load
  WinWidth = DEFAULTWIDTH;
  WinHeight = DEFAULTHEIGHT;
  ImWidth = 0;
  ImHeight = 0;

  if(argc <= 2) {
    return -1;
  }

  // load the image if present, and size the window to match
  if(argc == 3){
    if(readimage(argv[2])){
      readKernel(argv[1]);
      WinWidth = ImWidth;
      WinHeight = ImHeight;
    }
  }
  else if (argc == 4) {
    if(readimage(argv[2])){
      readKernel(argv[1]);
      WinWidth = ImWidth;
      WinHeight = ImHeight;
      outfilename = argv[3];
    }
  }

  // start up GLUT
  glutInit(&argc, argv);

  // create the graphics window, giving width, height, and title text
  glutInitDisplayMode(GLUT_SINGLE | GLUT_RGBA);
  glutInitWindowSize(WinWidth, WinHeight);
  glutCreateWindow("Image Viewer");

  // set up the callback routines
  glutDisplayFunc(handleDisplay); // display update callback
  glutKeyboardFunc(handleKey);    // keyboard key press callback
  glutReshapeFunc(handleReshape); // window resize callback

  // Enter GLUT's event loop
  glutMainLoop();
  return 0;
}

/*
Routine to read an image file and store in a pixmap
returns the size of the image in pixels if correctly read, or 0 if failure
*/
int readimage(string infilename){
  // Create the oiio file handler for the image, and open the file for reading the image.
  // Once open, the file spec will indicate the width, height and number of channels.
  ImageInput *infile = ImageInput::open(infilename);
  if(!infile){
    cerr << "Could not input image file " << infilename << ", error = " << geterror() << endl;
    return 0;
  }

  // Record image width, height and number of channels in global variables
  ImWidth = infile->spec().width;
  ImHeight = infile->spec().height;
  ImChannels = infile->spec().nchannels;


  // allocate temporary structure to read the image
  unsigned char tmp_pixels[ImWidth * ImHeight * ImChannels];

  // read the image into the tmp_pixels from the input file, flipping it upside down using negative y-stride,
  // since OpenGL pixmaps have the bottom scanline first, and
  // oiio expects the top scanline first in the image file.
  int scanlinesize = ImWidth * ImChannels * sizeof(unsigned char);
  if(!infile->read_image(TypeDesc::UINT8, &tmp_pixels[0] + (ImHeight - 1) * scanlinesize, AutoStride, -scanlinesize)){
    cerr << "Could not read image from " << infilename << ", error = " << geterror() << endl;
    ImageInput::destroy(infile);
    return 0;
  }

 // get rid of the old OpenGL pixmap and make a new one of the new size
  destroy();

 // allocate space for the Pixmap (contiguous approach)
  pixmap = new Pixel*[ImHeight];
  if(pixmap != NULL)
	pixmap[0] = new Pixel[ImWidth * ImHeight];
  for(int i = 1; i < ImHeight; i++)
	pixmap[i] = pixmap[i - 1] + ImWidth;

	pixmapOriginal = new Pixel*[ImHeight];
  if(pixmapOriginal != NULL)
	pixmapOriginal[0] = new Pixel[ImWidth * ImHeight];
  for(int i = 1; i < ImHeight; i++)
	pixmapOriginal[i] = pixmapOriginal[i - 1] + ImWidth;

 //  assign the read pixels to the Pixmap
 int index;
  for(int row = 0; row < ImHeight; ++row) {
    for(int col = 0; col < ImWidth; ++col) {
		index = (row*ImWidth+col)*ImChannels;


			pixmap[row][col].r = tmp_pixels[index];
			pixmap[row][col].g = tmp_pixels[index+1];
			pixmap[row][col].b = tmp_pixels[index+2];
			pixmap[row][col].a = tmp_pixels[index+3];

    }
  }

  // close the image file after reading, and free up space for the oiio file handler
  infile->close();
  ImageInput::destroy(infile);

  // set the pixel format to GL_RGBA and fix the # channels to 4
  pixformat = GL_RGBA;
  ImChannels = 4;

	pixmapOriginal = pixmap;

  // return image size in pixels
  return ImWidth * ImHeight;
}

/*
Routine to write the current framebuffer to an image file
*/
void writeimage(string outfilename){
  // make a pixmap that is the size of the window and grab OpenGL framebuffer into it
   unsigned char local_pixmap[WinWidth * WinHeight * ImChannels];
   glReadPixels(0, 0, WinWidth, WinHeight, pixformat, GL_UNSIGNED_BYTE, local_pixmap);

  // create the oiio file handler for the image
  ImageOutput *outfile = ImageOutput::create(outfilename);
  if(!outfile){
    cerr << "Could not create output image for " << outfilename << ", error = " << geterror() << endl;
    return;
  }

  // Open a file for writing the image. The file header will indicate an image of
  // width WinWidth, height WinHeight, and ImChannels channels per pixel.
  // All channels will be of type unsigned char
  ImageSpec spec(WinWidth, WinHeight, ImChannels, TypeDesc::UINT8);
  if(!outfile->open(outfilename, spec)){
    cerr << "Could not open " << outfilename << ", error = " << geterror() << endl;
    ImageOutput::destroy(outfile);
    return;
  }

  // Write the image to the file. All channel values in the pixmap are taken to be
  // unsigned chars. While writing, flip the image upside down by using negative y stride,
  // since OpenGL pixmaps have the bottom scanline first, and oiio writes the top scanline first in the image file.
  int scanlinesize = WinWidth * ImChannels * sizeof(unsigned char);
  if(!outfile->write_image(TypeDesc::UINT8, local_pixmap + (WinHeight - 1) * scanlinesize, AutoStride, -scanlinesize)){
    cerr << "Could not write image to " << outfilename << ", error = " << geterror() << endl;
    ImageOutput::destroy(outfile);
    return;
  }

  // close the image file after the image is written and free up space for the
  // ooio file handler
  outfile->close();
  ImageOutput::destroy(outfile);
}

/*
Routine to display a pixmap in the current window
*/
void displayimage(){
  // if the window is smaller than the image, scale it down, otherwise do not scale
  if(WinWidth < ImWidth  || WinHeight < ImHeight)
    glPixelZoom(float(VpWidth) / ImWidth, float(VpHeight) / ImHeight);
  else
    glPixelZoom(1.0, 1.0);

  // display starting at the lower lefthand corner of the viewport
  glRasterPos2i(0, 0);

  glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
  glDrawPixels(ImWidth, ImHeight, pixformat, GL_UNSIGNED_BYTE, pixmap[0]);
}

/*
Display Callback Routine: clear the screen and draw the current image
*/
void handleDisplay(){

  // specify window clear (background) color to be opaque black
  glClearColor(0, 0, 0, 1);
  // clear window to background color
  glClear(GL_COLOR_BUFFER_BIT);

  // only draw the image if it is of a valid size
  if(ImWidth > 0 && ImHeight > 0)
    displayimage();

  // flush the OpenGL pipeline to the viewport
  glFlush();
}

/*
Keyboard Callback Routine: 'r' - reload image,
'w' - write the current window to an image file, 'q' or ESC - quit
'c' - applys filters
*/
void handleKey(unsigned char key, int x, int y){
  int ok;

  switch(key){
    case 'r':   // 'r' - reload
    case 'R':
      reload();
      glutPostRedisplay();
      break;

    case 'w':   // 'w' - write the image to a file
    case 'W':
      writeimage(outfilename);
      break;

    case 'c':
    case 'C':
      convolution(); //applys filter
      glutPostRedisplay();
      break;

    case 'q':   // q or ESC - quit
    case 'Q':
    case 27:
      destroy();
      exit(0);

    default:    // not a valid key -- just ignore it
      return;
  }
}

/*
Reshape Callback Routine: If the window is too small to fit the image,
make a viewport of the maximum size that maintains the image proportions.
Otherwise, size the viewport to match the image size. In either case, the
viewport is centered in the window.
*/
void handleReshape(int w, int h){
  float imageaspect = (float)ImWidth / (float)ImHeight; // aspect ratio of image
  float newaspect = (float)w / (float)h; // new aspect ratio of window

  // record the new window size in global variables for easy access
  WinWidth = w;
  WinHeight = h;

  // if the image fits in the window, viewport is the same size as the image
  if(w >= ImWidth && h >= ImHeight){
    Xoffset = (w - ImWidth) / 2;
    Yoffset = (h - ImHeight) / 2;
    VpWidth = ImWidth;
    VpHeight = ImHeight;
  }
  // if the window is wider than the image, use the full window height
  // and size the width to match the image aspect ratio
  else if(newaspect > imageaspect){
    VpHeight = h;
    VpWidth = int(imageaspect * VpHeight);
    Xoffset = int((w - VpWidth) / 2);
    Yoffset = 0;
  }
  // if the window is narrower than the image, use the full window width
  // and size the height to match the image aspect ratio
  else{
    VpWidth = w;
    VpHeight = int(VpWidth / imageaspect);
    Yoffset = int((h - VpHeight) / 2);
    Xoffset = 0;
  }

  // center the viewport in the window
  glViewport(Xoffset, Yoffset, VpWidth, VpHeight);

  // viewport coordinates are simply pixel coordinates
  glMatrixMode(GL_PROJECTION);
  glLoadIdentity();
  gluOrtho2D(0, VpWidth, 0, VpHeight);
  glMatrixMode(GL_MODELVIEW);
}

/*
Read lines from the kernel and put it into 2d array
*/
void readKernel(string filename) {
  std::ifstream file;
  file.open(filename.c_str()); //argv[]

  if (file) {
    file >> arraySize;

    filterArray = new double *[arraySize];

    for(int i = 0; i < arraySize; i++) {
      filterArray[i] = new double[arraySize];
    }

    for(int row = 0; row < arraySize; row++) {
      for(int col = 0; col < arraySize; col++) {
        file >> filterArray[row][col];
      }
    }
  }
  file.close();
  flipHoriz();
  flipVert();
  scaleFact();
}

/*
Flips image vertically
*/
void flipVert() {
  int tmp;
  for(int i = 0; i < arraySize; i++) {
    for(int j = 0; j < arraySize/2; j++) {
      tmp = filterArray[i][arraySize-j-1];
      filterArray[i][arraySize - j - 1] = filterArray[i][j];
      filterArray[i][j] = tmp;
    }
  }
}

/*
Flips image horizontally
*/
void flipHoriz() {
  int tmp;
  for(int i = 0; i < arraySize; i++) {
    for(int j = 0; j < arraySize/2; j++) {
      tmp = filterArray[arraySize-j-1][i];
      filterArray[arraySize-j-1][i] = filterArray[j][i];
      filterArray[j][i] = tmp;
    }
  }
}

/*
finds sum of positive weight and apply it to the kernel array
*/
void scaleFact() {
  //do sum of positive weight
  for(int i = 0; i < arraySize; i++) {
    for(int j = 0; j < arraySize; j++) {
      if(filterArray[i][j] > 0)
        scaleFactor += filterArray[i][j];
    }
  }

  //multiply scale factor to filterArray
  for(int i = 0; i < arraySize; i++) {
    for(int j = 0; j < arraySize; j++) {
      filterArray[i][j] = (filterArray[i][j])/scaleFactor;
    }
  }
}

/*
apply filters to the image
*/
void convolution() {

  Pixel **tempPixmap = new Pixel*[ImHeight];
  tempPixmap[0] = new Pixel[ImWidth * ImHeight];

  for(int i = 1; i < ImHeight; i++) {
    tempPixmap[i] = tempPixmap[i-1] + ImWidth;
  }

  int origCenter = arraySize/2; //center

  //calculation to apply filter on each pixel
  for(int i = 0; i < ImHeight-origCenter; i++) {
    for (int j = 0; j < ImWidth-origCenter; j++) {
      double convSum1 = 0.0, convSum2 = 0.0, convSum3 = 0.0;
      for(int x = 0; x < arraySize; x++) {
        for(int y = 0; y < arraySize; y++) {
          int row = i - origCenter + x;
          int col = j - origCenter + y;
          if(row >= 0 && row < ImWidth && col >= 0 && col < ImWidth){
            convSum1 += pixmap[row][col].r * filterArray[x][y];
            convSum2 += pixmap[row][col].g * filterArray[x][y];
            convSum3 += pixmap[row][col].b * filterArray[x][y];
          }
          else{
            convSum1 += 0;
            convSum2 += 0;
            convSum3 += 0;
          }
        }
      }

      //clamping of values
      if(convSum1 < 0) {
        tempPixmap[i][j].r = 0;
      } else if(convSum1 > 255) {
        tempPixmap[i][j].r = 255;
      } else {
        tempPixmap[i][j].r = convSum1;
      }

      if(convSum2 < 0){
        tempPixmap[i][j].g = 0;
      }else if(convSum2 > 255){
        tempPixmap[i][j].g = 255;
      }else{
        tempPixmap[i][j].g = convSum2;
      }

      if(convSum3 < 0){
        tempPixmap[i][j].b = 0;
      }else if(convSum3 > 255){
        tempPixmap[i][j].b = 255;
      }else{
        tempPixmap[i][j].b = convSum3;
      }
    }
  }
  pixmap = tempPixmap;
}

/*
Displays the original image
*/
void reload() {
  pixmap = pixmapOriginal;
}

/*
Routine to cleanup the memory. Note that GLUT and OpenImageIO v1.6 lead to
memory leaks that cannot be suppressed.
*/
void destroy(){
 if (pixmap){
     delete pixmap[0];
   delete pixmap;
  }
}

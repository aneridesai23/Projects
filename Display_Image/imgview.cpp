/*
Name: Aneri Desai
date: 10th Sept 2019
Description: 
*/
#include <OpenImageIO/imageio.h>
#include <iostream>

#ifdef __APPLE__
#  pragma clang diagnostic ignored "-Wdeprecated-declarations"
#  include <GLUT/glut.h>
#else
#  include <GL/glut.h>
#endif

using namespace std;
OIIO_NAMESPACE_USING

// window dimensions
#define WIDTH 350
#define HEIGHT 350

//global variables and functions
int w = 0;
int h = 0;
int arg = 1;
int channels = 0;

unsigned char* pixmap = NULL;

void writeImage();
void displayImage();
void handleKey(unsigned char key, int x, int y);
void handleReshape(int w, int h);
void readImage(string imageName);
void convRGBA();
void rotateImage();

int main(int argc, char* argv[]) {

	// start up the glut utilities
	glutInit(&argc, argv);

	if(argc < 1) {
		return -1;
	}
	else {
		arg = argc;

		if(argc == 2) {
			readImage(argv[1]);
			glutInitWindowSize(w, h);
		}


		// create the graphics window, giving width, height, and title text
		glutInitDisplayMode(GLUT_SINGLE | GLUT_RGBA);
		if(argc == 1)
			glutInitWindowSize(WIDTH, HEIGHT);
		glutCreateWindow("Get the Picture!");

		// set up the callback routines to be called when glutMainLoop() detects
		// an event
		glutDisplayFunc(displayImage);	 // display callback
		glutKeyboardFunc(handleKey);	 // keyboard callback
		glutReshapeFunc(handleReshape);  // window resize callback

		// Routine that loops forever looking for events. It calls the registered
		// callback routine to handle each event that is detected
		glutMainLoop();
	}
	return 0;
}

/*
Routine to write the current framebuffer to an image file
*/
void writeImage() {

	string outfilename;

	// get a filename for the image. The file suffix should indicate the image file
	// type. For example: output.png to create a png image file named output
	cout << "enter output image filename: ";
	cin >> outfilename;

	// create the oiio file handler for the image
	ImageOutput* outfile = ImageOutput::create(outfilename);
	if (!outfile) {
		cerr << "Could not create output image for " << outfilename << ", error = " << geterror() << endl;
		return;
	}

	unsigned char* rotate_wr = new unsigned char[w * h * 4];
	// get the current pixels from the OpenGL framebuffer and store in pixmap
	glReadPixels(0, 0, w, h, GL_RGBA, GL_UNSIGNED_BYTE, rotate_wr);
	pixmap = rotate_wr;
	rotateImage();

	// open a file for writing the image. The file header will indicate an image of
	// width w, height h, and 4 channels per pixel (RGBA). All channels will be of
	// type unsigned char
	ImageSpec spec(w, h, 4, TypeDesc::UINT8);
	if (!outfile->open(outfilename, spec)) {
		cerr << "Could not open " << outfilename << ", error = " << geterror() << endl;
		ImageOutput::destroy(outfile);
		return;
	}

	// write the image to the file. All channel values in the pixmap are taken to be
	// unsigned chars
	if (!outfile->write_image(TypeDesc::UINT8, pixmap)) {
		cerr << "Could not write image to " << outfilename << ", error = " << geterror() << endl;

	}
	else
		cout << "Image is stored in " << outfilename << endl;


	// close the image file after the image is written
	if (!outfile->close()) {
		cerr << "Could not close " << outfilename << ", error = " << geterror() << endl;
		ImageOutput::destroy(outfile);
		return;
	}

	// free up space associated with the oiio file handler
	delete outfile;
}

/*
Display Callback Routine: clear the screen and draw a square
This routine is called every time the window on the screen needs
to be redrawn, like if the window is iconized and then reopened
by the user, and when the window is first created. It is also
called whenever the program calls glutPostRedisplay()
*/
void displayImage() {

	glClearColor(0, 0, 0, 0);
	glClear(GL_COLOR_BUFFER_BIT);

	if(arg != 1) {
		glRasterPos2i(0,0);
		glDrawPixels(w, h, GL_RGBA, GL_UNSIGNED_BYTE, pixmap);
	}

	// flush the OpenGL pipeline to the viewport
	glFlush();
}

/*
Keyboard Callback Routine: 'r' read image, 'q' or ESC quit,
'w' write the framebuffer to a file.
This routine is called every time a key is pressed on the keyboard
*/
void handleKey(unsigned char key, int x, int y) {

	switch (key) {
		case 'r':
		case 'R':
			readImage("");
			glutReshapeWindow(w,h); //window resize callback
			glutPostRedisplay();
			break;

		case 'w':
		case 'W':
			writeImage();
			break;

		case 'q':
		case 'Q':
		case 27:
			exit(0);

		default:
		return;
	}
}

/*
Reshape Callback Routine: sets up the viewport and drawing coordinates
This routine is called when the window is created and every time the window
is resized, by the program or by the user
*/
void handleReshape(int wi, int hi) {
	// set the viewport to be the entire window
	glViewport(0, 0, wi, hi);

	// define the drawing coordinate system on the viewport
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluOrtho2D(0, wi, 0, hi);
}

/*
Reades in the image from a file provided and calls the functions 
to convert to RGBA format and flips the image
*/
void readImage(string imageName) {
	string filename;

	if(imageName != "") {
		filename = imageName;
	}
	else {
		cout << "Enter image file name: ";
		cin >> filename;
		arg = 2;
	}

	ImageInput *img = ImageInput::open(filename);
	if(!img) {
		cerr << "Could not create: " << geterror();
		exit(-1);
	}

	const ImageSpec &spec = img->spec();
	w = spec.width;
	h = spec.height;
	channels = spec.nchannels;

	pixmap = new unsigned char [channels * w * h]; //creates pixmap to store in the pixels
	img->read_image(TypeDesc::UINT8, &pixmap[0]);

	convRGBA(); //calling the function to convert pixmap to RGBA format
	rotateImage(); //calls the flip image function
	img->close();
}

/*
flips the pixels of the image and that way flip the image 90 degrees
*/
void rotateImage() {
	unsigned char* rotate_pixmap = new unsigned char[w * h * 4]; //temp to store the flipped pixels

	for (int i = 0; i < h; i++) { 
		for (int j = 0; j < w; j++) {
			for (int color = 0; color < 4; color++) {
				rotate_pixmap[i * w * 4 + j * 4 + color] = pixmap[(h - i - 1) * w * 4 + j * 4 + color];
			}
		}
	}

	pixmap = rotate_pixmap;
}

//convert the pixels into RGBA value if the channels are less than 4, assign a fix value to the alpha
void convRGBA() {
	if (channels == 4) { //usually png images
		return;
	}
	int type = 0;
	unsigned char* new_pixmap = new unsigned char[w * h * 4];

	//greyscale
	if (channels == 1) {
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				for (int color = 0; color < 3; color++) {
					new_pixmap[i * h * 4 + j * 4 + color] = pixmap[i * h + j];
				}
				new_pixmap[i * h * 4 + j * 4 + 3] = 255;
			}
		}

	} //jpg, ppm images
	else if (channels = 3) {
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				for (int color = 0; color < 3; color++) {
					new_pixmap[i * h * 4 + j * 4 + color] = pixmap[i * h * 3 + j * 3 + color];
				}
				new_pixmap[i * h * 4 + j * 4 + 3] = 255;
			}
		}
	}
	pixmap = new_pixmap;
}

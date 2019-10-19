
/*

	Name - Aneri Desai(anerid) and Monica Hart(mlhart)
	Course Number and section - CPSC 1070-001
        the Program assignment number - 002
        The due date - 5th March, 2018

	Description - The function newImage malloc to get allocated space and 
	assign new values to the data member of the struct for the new image.  

*/

#include "image.h"

/** newImage 

    Create and initialize a new image_t.

    Prototype:
       image_t *newImage(int rows, int columns, int brightness);
 
    where
       rows:    number of pixel rows in new PPM image
       columns: number of pixel columns in new PPM image
       brightness:  pixel brightness (0-255)

    Return value:
       Pointer to newly allocated and initialized image_t
**/

image_t *newImage(int rows, int columns, int brightness) 
{
   //allocate memory
   image_t *newImagePtr = (image_t *) malloc(sizeof(image_t));
   pixel_t *newPxPtr = (pixel_t *) malloc(rows * columns * sizeof(pixel_t));

   //initialize variables
   newImagePtr -> image = newPxPtr;
   newImagePtr -> rows = rows;
   newImagePtr -> columns = columns;
   newImagePtr -> brightness = brightness;

   return(newImagePtr);
}

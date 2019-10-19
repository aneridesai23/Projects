
/*

	Name: Aneri Desai(anerid) and Monica Hart(mlhart)
	the course number and section - CPSC 1070-001
	The program assignment number - 002
	The due date - 5th March, 2018

	Description - the function writeImage first get the parameters from the 
	mirrored image and assign it to the appropriate data values and prints 
	those values to the output file. 

*/

#include <stdio.h>
#include "image.h"

/** writeImage **/

/** outimage.c

    Output a PPM image to a specified file name.

    Prototype:
       void outImage(char *fileName, image_t *mirroredImage);

    where
       fileName:    pointer to name of output file to create,
       mirroredImage: pointer to the image_t of the image to write.

**/

void writeImage(char *fileName, image_t *mirroredImage) 
{
   int xSize = mirroredImage -> columns;
   int ySize = mirroredImage -> rows;
   int bright = mirroredImage -> brightness;   


   /* Open the output file */

   FILE* outFile = NULL;
   outFile = fopen(fileName, "wb");
   
   /* write the header to the file */

   fprintf(outFile, "P6\n%d %d\n%d\n", xSize, ySize, bright);

   /* write the image data to the file */

   fwrite(mirroredImage -> image, xSize * ySize * sizeof(pixel_t), 1, outFile);

   /* close the file  */ 

   fclose(outFile);
}


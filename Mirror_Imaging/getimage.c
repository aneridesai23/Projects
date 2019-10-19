/** CPSC 1070 - getimage.c **/

/* Retrieve a PPM image */

#include "image.h"

image_t *getImage(char *inFileName) 
{
   char header[3];
   char buf[256];
   FILE *inFile;
   image_t *inImage;
   int inSize;

   if ((inFile = fopen(inFileName, "r")) == NULL ) 
   {
      fprintf(stderr, "Cannot open file \"%s\"\n", inFileName);
      exit(1);
   }

   /* Allocate space for the image structure */
   inImage = malloc(sizeof(image_t));

   /* Process the PPM header */
   if (fscanf(inFile, "%2s", header) != 1) 
   {
      fprintf(stderr,"Premature end to PPM header\n");
      exit(1);
   }

   if (strcmp(header, "P6") != 0) 
   {
      fprintf(stderr, "PPM file does not start with \"P6\"\n");
      exit(1);
   }

   if (getint(inFile, &inImage->columns) <= 0) 
   {
      fprintf(stderr,"Error in PPM header -- columns\n");
      exit(1);
   }

   if (getint(inFile, &inImage->rows) <= 0) 
   {
      fprintf(stderr,"Error in PPM header -- rows\n");
      exit(1);
   }

   if (getint(inFile, &inImage->brightness) <= 0) 
   {
      fprintf(stderr,"Error in PPM header -- brightness\n");
      exit(1);
   }

   if (fgets(buf, sizeof(buf), inFile) == NULL) 
   {
      fprintf(stderr,"Error in PPM header -- after brightness\n");
      exit(1);
   }

   /* Compute the size of the input image, and allocate space for it */
   inSize = inImage->columns * inImage->rows * sizeof(pixel_t);
   inImage->image = malloc(inSize);
 
   /* And read the image */
   if (fread(inImage->image, 1, inSize, inFile) != inSize) 
   {
      fprintf(stderr, "Error: input file truncated\n");
      exit(1);
   }

   fclose(inFile);

   return(inImage);
}

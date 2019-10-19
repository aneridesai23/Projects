/** CPSC 1070 - prog2.c **/

/* Test driver for program 2                         */
/* Convert a mirror image of the original  PPM image */

#include "image.h"

int main(int argc, char *argv[]) 
{
   image_t *inImage;
   image_t *outImage;
   FILE *outFile;

   /* Test and fetch input parameters */
   if (argc != 3) 
   {
      fprintf(stderr, "Usage: ./p2 infilename outfilename\n");
      exit(1);
   }

   /* Input the source image */
   if ((inImage = getImage(argv[1])) == NULL) 
   {
      fprintf(stderr, "getImage() failed\n");
      exit(1);
   }

   if ((outFile = fopen(argv[2], "w")) == NULL) 
   {
      fprintf(stderr, "Cannot open output file\n");
      exit(1);
   }

   /* mirror image   */

   outImage = mirror(inImage);

   /* Was image creation successful? */
   if (outImage == NULL) 
   {
      fprintf(stderr, "mirror() call returned NULL\n");
      fprintf(stderr, "Did not create new mirror image\n");
      exit(1);
   }

   /* output the image   */

   writeImage(argv[2], outImage);

   return 0;
}

/** CPSC 1070 - image.h **/
#ifndef IMAGE_H
#define IMAGE_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>


/** pixel_t definition **/
typedef struct pixelType 
{
   unsigned char r;
   unsigned char g;
   unsigned char b;
} pixel_t;

/** image_t defintion **/
typedef struct imageType 
{
   pixel_t *image;
   int     columns;
   int     rows;
   int     brightness;
} image_t;

/** Prototype statements **/
int getint(FILE *inFile, int *result);
image_t *getImage(char *inFileName);
image_t *newImage(int rows, int cols, int brightness);
image_t * mirror(image_t *inImage);
void writeImage(char *inFileName, image_t *outImage);

#endif

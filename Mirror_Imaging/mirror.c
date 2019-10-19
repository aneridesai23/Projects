/** mirror.c 

    CPSC 1070: Program  2 

    Create a mirror image of the original PPM image. 

**/

/*

	Name: Aneri Desai(anerid) and Monica Hart(mlhart)
	The course number and section - CPSC 1070-001
	The program assignment number - 002
	The due date - 5th March 2018

	Description - The function first checks whether the column
	is even or odd and according to that takes the half left 
	side of the data values and mirror it exactly on the right side
	by going to right to left. 

*/

#include "image.h"


image_t *mirror(image_t *inImage) 
{

   int rows = inImage -> rows;
   int columns = inImage -> columns;
   int brightness = inImage -> brightness;
   pixel_t *inImPtr = inImage -> image;

   image_t *mirroredImage = newImage(rows, columns, brightness);
   pixel_t *mirImPtr = mirroredImage -> image;

   int x, y;
   for(y = 0; y < rows; y++) {
      
      if(columns % 2 == 0){
         for(x = 0; x < columns / 2; x++) {
            int placeLeft = x + y * columns;
            int placeRight = columns - x - 1 + y * columns;
            *((mirImPtr) + placeLeft) = *((inImPtr) + placeLeft);
            *((mirImPtr) + placeRight) = *((inImPtr) + placeLeft);
         }

      }

      else {
         for(x = 0; x < (columns / 2) + 1; x++) {
            int placeLeft = x + y * columns;
            int placeRight = columns - x - 1 + y * columns;
            *((mirImPtr) + placeLeft) = *((inImPtr) + placeLeft);
            *((mirImPtr) + placeRight) = *((inImPtr) + placeLeft);
         }

      }


   }

   return(mirroredImage); 
}

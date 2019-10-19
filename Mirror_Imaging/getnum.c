/** CPSC 1070 - getnum.c                      **/
/** This file contains the function getint()  **/

#include <stdio.h>
#include <stdlib.h>

/** getint **/
int getint(FILE *fp, int *result) 
{
    char buf[100];
    int  code;

    while ((code=fscanf(fp, "%d", result)) != 1) 
    {
       if (fgets(buf, sizeof(buf), fp) == NULL) 
       {
          return(-1);
       }

       if (buf[0] != '#') 
       {
          fprintf(stderr, "getint[%d]: Error in input: \"%s\"\n", code, buf);
          return(0);
       }
    }
    return(1);
}

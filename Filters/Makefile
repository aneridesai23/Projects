CC      = g++
C       = cpp

CFLAGS  = -g

ifeq ("$(shell uname)", "Darwin")
  LDFLAGS     = -framework Foundation -framework GLUT -framework OpenGL -lOpenImageIO -lm
else
  ifeq ("$(shell uname)", "Linux")
    LDFLAGS   = -L /usr/lib64/ -lglut -lGL -lGLU -lOpenImageIO -lm
  endif
endif

PROJECT		= convolve

${PROJECT}:	${PROJECT}.o
	${CC} ${CFLAGS} ${LFLAGS} -o ${PROJECT} ${PROJECT}.o ${LDFLAGS}

${PROJECT}.o:	${PROJECT}.${C}
	${CC} ${CFLAGS} -c ${PROJECT}.${C}

clean:
	rm -f core.* *.o *~ ${PROJECT}

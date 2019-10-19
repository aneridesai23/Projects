C=gcc
CFLAGS=-g -Wall
OBJS = getnum.o getimage.o newimage.o outimage.o prog2.o mirror.o

mirror: ${OBJS}
	@echo Linking $@
	gcc -g -o $@ ${OBJS} 

${OBJS}: image.h

clean:
	rm -f *.o mirror

test:
	./mirror flower.ppm mirror-flower.ppm 


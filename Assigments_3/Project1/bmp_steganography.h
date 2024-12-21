#pragma once
#include <stdio.h>
#include <stdint.h>
/*
Offset	Length	Description
0	2	Format identifier (magic number)
2	4	Size of the file in bytes
6	2	A two-byte reserved value
8	2	Another two-byte reserved value
10	4	Offset to the start of the pixel array
*/
typedef struct {
    unsigned short magic;// 0-2
    unsigned int size; // 
    unsigned short reserve;//
    unsigned short reserve2;//
    unsigned int offset;//
} BMPFileHeader;

void printBMPHeader(BMPFileHeader *header);
/*
Offset	Length	Description
0	4	Size of this DIB header in bytes
4	4	Width of the image in pixels
8	4	Height of the image in pixels
12	2	Number of color planes (don’t worry)
14	2	Number of bits per pixel
16	4	Compression scheme used
20	4	Image size in bytes
24	4	Horizontal resolution
28	4	Vertical resolution
32	4	Number of colors in the palette
36	4	Number of important colors
*/

typedef struct {
   unsigned int size;
   unsigned int width;
   unsigned int height;
   unsigned short numOfColorPlanes;
   unsigned short bitsPerPixel;
   unsigned int compressionScheme;
   unsigned int imageSizeBytes;
   unsigned int horizontalRes;
   unsigned int verticalRes;
   unsigned int numOfColors;
   unsigned int importantColors;
} DIBFileHeader;
void printDIBHeader(DIBFileHeader *header);
typedef struct{
    uint8_t blue;
    uint8_t green;
    uint8_t red;
}Pixel;


void BMPSetUp(FILE * f ,BMPFileHeader* b);
void DIBSetUp(FILE * f, DIBFileHeader *d);
void pixelRows(FILE *f, int width, int height);
void stripLSB(FILE *f, int width, int height);
void shiftMSB(FILE *f, int width, int height);
void combineBytes(FILE*f1,FILE *f2,int width, int height);
void testFlip(FILE* f ,int width, int height);
void reveal(FILE* f ,int width, int height);
void processFile(FILE *f);
int main(int argc, char *argv[]);
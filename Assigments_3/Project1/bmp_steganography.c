#include "bmp_steganography.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>


//assume BMP can be malloced
void BMPSetUp(FILE * f ,BMPFileHeader* b)
{
    unsigned int i;
    unsigned  short j;
    //size_t fread( void *ptr, size_t size, size_t n_items, FILE *stream )
    fread(&j,sizeof(short),1,f);
    b->magic = (unsigned short)j;
    fread(&i,sizeof(int),1,f);
     b->size = (unsigned int)i;
    fread(&j,sizeof(short),1,f);
    b->reserve = (unsigned short)j;
    fread(&j,sizeof(short),1,f);
    b->reserve2 = (unsigned short)j;
    fread(&i,sizeof(int),1,f);
     b->offset = (unsigned int)i;
   
}
void DIBSetUp(FILE * f, DIBFileHeader *d)
{
    unsigned int i;
    unsigned  short j;
    fread(&i,sizeof(int),1,f);
    d->size = (unsigned int)i;
    fread(&i,sizeof(int),1,f);
    d->width = (unsigned int)i;
    fread(&i,sizeof(int),1,f);
    d->height = (unsigned int)i;
    fread(&j,sizeof(short),1,f);
    d->numOfColorPlanes = (unsigned short)j;
    fread(&j,sizeof(short),1,f);
    d->bitsPerPixel = (unsigned short)j;
    fread(&i,sizeof(int),1,f);
    d->compressionScheme = (unsigned int)i;
    fread(&i,sizeof(int),1,f);
    d->imageSizeBytes = (unsigned int)i;
    fread(&i,sizeof(int),1,f);
    d->horizontalRes = (unsigned int)i;
    fread(&i,sizeof(int),1,f);
    d->verticalRes = (unsigned int)i;
    fread(&i,sizeof(int),1,f);
    d->numOfColors = (unsigned int)i;
    fread(&i,sizeof(int),1,f);
    d->importantColors = (unsigned int)i;
}
void printBMPHeader(BMPFileHeader *header)
{//assume header is filled out;
    printf("=== BMP Header ===\n");
    printf("Type: %c%c\n", (header->magic ), (header->magic >> 8));//can't use string cause no null terminator?
    printf("Size: %u\n",header->size);
    printf("Reserved 1: %hu\n",header->reserve);
    printf("Reserved 2: %hu\n",header->reserve2);
    printf("Image Offset: %u\n",header->offset);
}
void printDIBHeader(DIBFileHeader *header)
{/*
 === DIB Header ===
    Size: 40
    Width: 960
    Height: 720
    # color planes: 1
    # bits per pixel: 24
    Compression scheme: 0
    Image size: 2073600
    Horizontal resolution: 7559
    Vertical resolution: 7559
    # colors in palette: 0
    # important colors: 0*/
    printf("=== DIB Header ===\n");
    printf("Size: %u\n",header->size);
    printf("Width: %u\n",header->width);
    printf("Height: %u\n",header->height);
    printf("# color planes: %hu\n",header->numOfColorPlanes);
    printf("# bits per pixel: %hu\n",header->bitsPerPixel);
    printf("Compression scheme: %u\n",header->compressionScheme);
    printf("Image size: %u\n",header->imageSizeBytes);
    printf("Horizontal resolution: %u\n",header->horizontalRes);
    printf("Vertical resolution: %u\n",header->verticalRes);
    printf("# colors in palette: %u\n",header->numOfColors);
    printf("# important colors: %u\n",header->importantColors);
}
void stripLSB(FILE *f, int width, int height)
{
    int rowSize = ((width*3+3)>>2)<<2; 
    int paddingSize = rowSize-(width*3);
    Pixel *rowPix = malloc(rowSize );
    if(rowPix==NULL)
    {
        printf("No space for row array");
        return ;
    }
    for(int i = 0; i<height;i++)
    {
        fread(rowPix, sizeof(Pixel), width, f);//read the pixels
        for(int j = 0; j<width;j++)
        {
            //test flip color
            rowPix[j].blue =  rowPix[j].blue &0xF0;
            rowPix[j].green = rowPix[j].green &0xF0;
            rowPix[j].red =  rowPix[j].red &0xF0;
        }  
        fseek(f, -width * sizeof(Pixel), SEEK_CUR);// move back to original
        fwrite(rowPix, sizeof(Pixel), width, f);
        fseek(f, paddingSize, SEEK_CUR);
    }
    printf("Pixel Data Size: %d\n", width*3);
    printf("Row Size: %d\n", rowSize);
    printf("Padding Size: %d\n", paddingSize);

    free(rowPix);
}
void shiftMSB(FILE *f, int width, int height)
{
    int rowSize = ((width*3+3)>>2)<<2; 
    int paddingSize = rowSize-(width*3);
    Pixel *rowPix = malloc(rowSize );
    if(rowPix==NULL)
    {
        printf("No space for row array");
        return ;
    }
    for(int i = 0; i<height;i++)
    {
        fread(rowPix, sizeof(Pixel), width, f);//read the pixels
        for(int j = 0; j<width;j++)

        {
            //shift and remove the LSB
            rowPix[j].blue =  rowPix[j].blue >>4;
            rowPix[j].green = rowPix[j].green >>4;
            rowPix[j].red =  rowPix[j].red >>4;
        }
        fseek(f, -width * sizeof(Pixel), SEEK_CUR);// move back to original
        fwrite(rowPix, sizeof(Pixel), width, f);
        fseek(f, paddingSize, SEEK_CUR);
    }

    free(rowPix);
}
void combineBytes(FILE*f1,FILE *f2,int width, int height)//one width and one height since the images has to be the same size
{
    int rowSize = ((width*3+3)>>2)<<2; //11001->11100, always divisble by 4 since 1,2,3 are purged and all bits above 4 divisible by 4
    int paddingSize = rowSize-(width*3);
    Pixel *rowPix1 = malloc(rowSize );
    Pixel *rowPix2 = malloc(rowSize );// malloc the arrays
    
    if(rowPix1==NULL||rowPix2==NULL)
    {   //if either can't fit, free both
        free(rowPix1);
        free(rowPix2);
        printf("No space for row array");
        return ;
    }
    for(int i = 0; i<height;i++)// go through a row h times
    {// this is only used to combine images that we know are processed. aka f1 has no LSB and f2's MSB has replaced its LSB
        fread(rowPix1, sizeof(Pixel), width, f1);//read the pixels for both and put them into their respetive array
        fread(rowPix2, sizeof(Pixel), width, f2);
        for(int j = 0; j<width;j++)// gothrough each set of three in the widht

        {
            //combine first one has no LSB, the second has no MSB
            rowPix1[j].blue = (rowPix1[j].blue & 0xF0) | (rowPix2[j].blue & 0x0F); //combine the two images with masking
            rowPix1[j].green = (rowPix1[j].green & 0xF0) | (rowPix2[j].green & 0x0F);//first four bits of 1, second four bits of 2
            rowPix1[j].red = (rowPix1[j].red & 0xF0) | (rowPix2[j].red & 0x0F);// probably unnecessary but safe
        }
        fseek(f1, -width * sizeof(Pixel), SEEK_CUR);// move back to original
        fwrite(rowPix1, sizeof(Pixel), width, f1);
        fseek(f1, paddingSize, SEEK_CUR);
        fseek(f2, paddingSize, SEEK_CUR);
        //printf("rowSize: %d, paddingSize: %d\n", rowSize, paddingSize);

    }
    free(rowPix1);
    free(rowPix2);
}
void testFlip(FILE* f ,int width, int height)
{
      int rowSize = ((width*3+3)>>2)<<2; 
    int paddingSize = rowSize-(width*3);
    Pixel *rowPix = malloc(rowSize );
    if(rowPix==NULL)
    {
        printf("No space for row array");
        return ;
    }
     for(int i = 0; i<height;i++)
    {
        fread(rowPix, sizeof(Pixel), width, f);//read the pixels
        for(int j = 0; j<width;j++)

        {
            //shift
            rowPix[j].blue =  255-rowPix[j].blue ;
            rowPix[j].green = 255-rowPix[j].green;
            rowPix[j].red =  255-rowPix[j].red ;
        }
        fseek(f, -width * sizeof(Pixel), SEEK_CUR);// move back to original
        fwrite(rowPix, sizeof(Pixel), width, f);
        fseek(f, paddingSize, SEEK_CUR);
    }
    free(rowPix);
}
void reveal(FILE* f ,int width, int height)
{
    int rowSize = ((width*3+3)>>2)<<2; 
    int paddingSize = rowSize-(width*3);
    Pixel *rowPix = malloc(rowSize );
    if(rowPix==NULL)
    {
        printf("No space for row array");
        return ;
    }
     for(int i = 0; i<height;i++)
    {
        fread(rowPix, sizeof(Pixel), width, f);//read the pixels
        for(int j = 0; j<width;j++)
        {
            
            rowPix[j].blue =  ((rowPix[j].blue&0x0F) <<4)|((rowPix[j].blue&0xF0) >>4) ;
            rowPix[j].green = ((rowPix[j].green &0x0F)<<4) | ((rowPix[j].green&0xF0) >>4);
            rowPix[j].red =  ((rowPix[j].red &0x0F)<<4 )| ((rowPix[j].red&0xF0) >>4);
        }
         
        fseek(f, -width * sizeof(Pixel), SEEK_CUR);// move back to original
        fwrite(rowPix, sizeof(Pixel), width, f);
        fseek(f, paddingSize, SEEK_CUR);
         // skip padding
    }
    free(rowPix);
}
int main(int argc, char *argv[]) {
   
    char* fileName;
    char* fileName2;
    //--info,--hide,--reveal 
    if (argc<3)
    {   
        printf("ERROR: Missing arguments.\n");
        return 1;
    }
    else if(strcmp(argv[1], "--info")==0){//--info    
        if(argv[2]==NULL)
        {
            printf("ERROR: Missing arguments.\n");
            return 1;
        }
        fileName=argv[2];
        //printf("%s\n",fileName);
        FILE *f1 = fopen(fileName, "rb+");
        if (f1 == NULL ) {// NO FILE or broken
            printf("ERROR: File not found\n");
            return -1;
        } 
        BMPFileHeader *BMHeader1=malloc(sizeof(BMPFileHeader));
        if (BMHeader1 == NULL) {
            free(BMHeader1);
            printf("Can't create space, BM\n");
            fclose(f1);
            return EXIT_FAILURE;
        }
        BMPSetUp(f1,BMHeader1);
        if (BMHeader1->magic != 0x4D42) {//66=42 77 =4D BM
            free(BMHeader1);
            printf("ERROR: The format is not supported.\n");
            fclose(f1);
            return EXIT_FAILURE;
        }
        DIBFileHeader *DIBHeader1=malloc(sizeof(DIBFileHeader));
        if (DIBHeader1 == NULL) {
            printf("Can't create space for DIB\n");
            fclose(f1);
            free(BMHeader1);
            free(DIBHeader1);
            return EXIT_FAILURE;
        }
         DIBSetUp(f1,DIBHeader1);
         if (DIBHeader1->size != 40) {//66=42 77 =4D BM
            printf("ERROR: The format is not supported.\n");
            free(DIBHeader1);
            free(BMHeader1);
            fclose(f1);
            return EXIT_FAILURE;
        } 
        if (DIBHeader1->bitsPerPixel !=24) {//66=42 77 =4D BM
            printf("ERROR: The format is not supported.\n");
            free(DIBHeader1);
            fclose(f1);
            free(DIBHeader1);
            return EXIT_FAILURE;
        }
        printBMPHeader(BMHeader1);
        printf("\n");
        printDIBHeader(DIBHeader1);
        printf("\n");
        free(DIBHeader1);
        free(BMHeader1);   
        fclose(f1);
        return 0;    
     
    }
    else if(strcmp(argv[1],"--hide")==0){//--hide
        if(argc<4 ||argv[2]==NULL||argv[3]==NULL)//needs two args
        {
            printf("ERROR: Missing arguments.\n");
            return 1;
        }
        fileName=argv[2];
        fileName2=argv[3];
        FILE *f1 = fopen(fileName, "rb+");
        FILE *f2 = fopen(fileName2, "rb+");
        if (f1 == NULL ||f2 ==NULL) {// NO FILE
            printf("ERROR: File not found\n");
            return -1;
        }
        BMPFileHeader *BMHeader1=malloc(sizeof(BMPFileHeader));
        BMPFileHeader *BMHeader2=malloc(sizeof(BMPFileHeader));
        if (BMHeader1 == NULL||BMHeader2==NULL) {
        
            free(BMHeader1);
            free(BMHeader2);
            printf("Can't create space\n");
            fclose(f1);
            fclose(f2);
            return EXIT_FAILURE;
        }
        BMPSetUp(f1,BMHeader1);
        BMPSetUp(f2,BMHeader2);
        if (BMHeader1->magic != 0x4D42||BMHeader2->magic != 0x4D42) {//66=42 77 =4D BM
            
            
            free(BMHeader1);
            free(BMHeader2);
            printf("ERROR: The format is not supported.\n");
            fclose(f1);
            fclose(f2);
            return EXIT_FAILURE;
        }
        DIBFileHeader *DIBHeader1=malloc(sizeof(DIBFileHeader));
        DIBFileHeader *DIBHeader2=malloc(sizeof(DIBFileHeader));
        if (DIBHeader1 == NULL||DIBHeader2==NULL) {
            printf("Can't create space for DIB\n");
            fclose(f1);
            fclose(f2);
            free(BMHeader1);
            free(BMHeader2);
            free(DIBHeader1);
            free(DIBHeader2);
            return EXIT_FAILURE;
        }
        DIBSetUp(f1,DIBHeader1);
        DIBSetUp(f2,DIBHeader2);
        if (DIBHeader1->size != 40||DIBHeader2->size !=40) {//66=42 77 =4D BM
            
            printf("ERROR: The format is not supported.\n");
            free(DIBHeader1);
            free(BMHeader1);
            free(DIBHeader2);
            free(BMHeader2);
            fclose(f1);
            fclose(f2);
            return EXIT_FAILURE;
        } 
        if (DIBHeader1->bitsPerPixel !=24||DIBHeader2->bitsPerPixel !=24) {//66=42 77 =4D BM
        
           printf("ERROR: The format is not supported.\n");
            free(DIBHeader1);
            free(BMHeader2);
            fclose(f1);
            free(DIBHeader1);
            free(BMHeader2);
            fclose(f2);
            return EXIT_FAILURE;
        }
        if((DIBHeader1->width!=DIBHeader2->width)||(DIBHeader1->height!=DIBHeader2->height))
        {
            printf("ERROR: The format is not supported.\n");
            free(DIBHeader1);
            free(BMHeader2);
            fclose(f1);
            free(DIBHeader1);
            free(BMHeader2);
            fclose(f2);
            return EXIT_FAILURE;
        }
        shiftMSB(f2,DIBHeader2->width,DIBHeader2->width);
        stripLSB(f1,DIBHeader1->width,DIBHeader1->width);
        fclose(f2);
        fclose(f1);
        f2 = fopen(fileName2, "rb+");
        BMPSetUp(f2,BMHeader2);
        DIBSetUp(f2,DIBHeader2);
        f1 = fopen(fileName, "rb+");
        BMPSetUp(f1,BMHeader1);
        DIBSetUp(f1,DIBHeader1);
        combineBytes(f1,f2,DIBHeader1->width,DIBHeader1->height);
        free(DIBHeader1);
        free(BMHeader1);   
        fclose(f1);
        free(DIBHeader2);
        free(BMHeader2);   
        fclose(f2);
        return 0;
    }
    else if(strcmp(argv[1],"--reveal")==0)
    {
        if(argv[2]==NULL)
        {
            printf("ERROR: Missing arguments.\n");
            return 1;
        }
        fileName=argv[2];
        //printf("%s\n",fileName);
        FILE *f1 = fopen(fileName, "rb+");
        if (f1 == NULL ) {// NO FILE or broken
            printf("ERROR: File not found\n");
            return -1;
        } 
        BMPFileHeader *BMHeader1=malloc(sizeof(BMPFileHeader));
        if (BMHeader1 == NULL) {
            free(BMHeader1);
            printf("Can't create space, BM\n");
            fclose(f1);
            return EXIT_FAILURE;
        }
        BMPSetUp(f1,BMHeader1);
        if (BMHeader1->magic != 0x4D42) {//66=42 77 =4D BM
            free(BMHeader1);
            printf("ERROR: The format is not supported.\n");
            fclose(f1);
            return EXIT_FAILURE;
        }
        DIBFileHeader *DIBHeader1=malloc(sizeof(DIBFileHeader));
        if (DIBHeader1 == NULL) {
            printf("Can't create space for DIB\n");
            fclose(f1);
            free(BMHeader1);
            free(DIBHeader1);
            return EXIT_FAILURE;
        }
         DIBSetUp(f1,DIBHeader1);
         if (DIBHeader1->size != 40) {//66=42 77 =4D BM
            printf("ERROR: The format is not supported.\n");
            free(DIBHeader1);
            free(BMHeader1);
            fclose(f1);
            return EXIT_FAILURE;
        } 
        if (DIBHeader1->bitsPerPixel !=24) {//66=42 77 =4D BM
            printf("ERROR: The format is not supported.\n");
            free(DIBHeader1);
            fclose(f1);
            free(DIBHeader1);
            return EXIT_FAILURE;
        }
        reveal(f1,DIBHeader1->width,DIBHeader1->height);
        free(DIBHeader1);
        free(BMHeader1);   
        fclose(f1);
        return 0;    
    }
     else if(strcmp(argv[1],"--test")==0)
     {
         if(argv[2]==NULL)
        {
            printf("ERROR: Missing arguments.\n");
            return 1;
        }
        fileName=argv[2];
        //printf("%s\n",fileName);
        FILE *f1 = fopen(fileName, "rb+");
        if (f1 == NULL ) {// NO FILE or broken
            printf("ERROR: File not found\n");
            return -1;
        } 
        BMPFileHeader *BMHeader1=malloc(sizeof(BMPFileHeader));
        if (BMHeader1 == NULL) {
            free(BMHeader1);
            printf("Can't create space, BM\n");
            fclose(f1);
            return EXIT_FAILURE;
        }
        BMPSetUp(f1,BMHeader1);
        if (BMHeader1->magic != 0x4D42) {//66=42 77 =4D BM
            free(BMHeader1);
            printf("ERROR: The format is not supported.\n");
            fclose(f1);
            return EXIT_FAILURE;
        }
        DIBFileHeader *DIBHeader1=malloc(sizeof(DIBFileHeader));
        if (DIBHeader1 == NULL) {
            printf("Can't create space for DIB\n");
            fclose(f1);
            free(BMHeader1);
            free(DIBHeader1);
            return EXIT_FAILURE;
        }
         DIBSetUp(f1,DIBHeader1);
         if (DIBHeader1->size != 40) {//66=42 77 =4D BM
            printf("ERROR: The format is not supported.\n");
            free(DIBHeader1);
            free(BMHeader1);
            fclose(f1);
            return EXIT_FAILURE;
        } 
        if (DIBHeader1->bitsPerPixel !=24) {//66=42 77 =4D BM
            printf("ERROR: The format is not supported.\n");
            free(DIBHeader1);
            fclose(f1);
            free(DIBHeader1);
            return EXIT_FAILURE;
        }
        stripLSB(f1,DIBHeader1->width,DIBHeader1->height);
        printBMPHeader(BMHeader1);
        printf("\n");
        printDIBHeader(DIBHeader1);
        printf("\n");
        free(DIBHeader1);
        free(BMHeader1);   
        fclose(f1);
        return 0;    
     }
    else{
        printf("Command not known");
    }
    exit(1);
    
    //do thigns
    //FILE *f1 = fopen("SPACE copy.bmp", "rb+");
    //FILE *f2 = fopen("Mountain.bmp", "rb+");// Mountain, hidden Image
    /*
    FILE *f1 = fopen("pepper_secret.bmp", "rb+");
    FILE *f2 = fopen("pepper_revealed.bmp", "rb+");// Mountain, hidden Image
    if (f1 == NULL ||f2 ==NULL) {// NO FILE
        fprintf(stderr, "ERROR: Cannot open file \n");
        return -1;
    }

    BMPFileHeader *BMHeader1=malloc(sizeof(BMPFileHeader));
    BMPFileHeader *BMHeader2=malloc(sizeof(BMPFileHeader));
    if (BMHeader1 == NULL||BMHeader2==NULL) {
       
         free(BMHeader1);
          free(BMHeader2);
        printf("Can't create space, BM");
        fclose(f1);
        fclose(f2);
        return EXIT_FAILURE;
    }
    BMPSetUp(f1,BMHeader1);
    BMPSetUp(f2,BMHeader2);
     if (BMHeader1->magic != 0x4D42||BMHeader2->magic != 0x4D42) {//66=42 77 =4D BM
        
        
        free(BMHeader1);
        free(BMHeader2);
        printf("Wrong file type: WRONG MAGIC");
        fclose(f1);
        fclose(f2);
        return EXIT_FAILURE;
    }
    DIBFileHeader *DIBHeader1=malloc(sizeof(DIBFileHeader));
    DIBFileHeader *DIBHeader2=malloc(sizeof(DIBFileHeader));
    if (DIBHeader1 == NULL||DIBHeader2==NULL) {
        printf("Can't create space for DIB");
        fclose(f1);
        fclose(f2);
        free(BMHeader1);
        free(BMHeader2);
        free(DIBHeader1);
        free(DIBHeader2);
        return EXIT_FAILURE;
    }
    DIBSetUp(f1,DIBHeader1);
    DIBSetUp(f2,DIBHeader2);
    if (DIBHeader1->size != 40||DIBHeader2->size !=40) {//66=42 77 =4D BM
        
       printf("Wrong size");
       free(DIBHeader1);
        free(BMHeader1);
        free(DIBHeader2);
         free(BMHeader2);
        fclose(f1);
        fclose(f2);
        return EXIT_FAILURE;
    } 
    if (DIBHeader1->bitsPerPixel !=24||DIBHeader2->bitsPerPixel !=24) {//66=42 77 =4D BM
      
       printf("we don’t support the file format:24");
       free(DIBHeader1);
         free(BMHeader2);
        fclose(f1);
        free(DIBHeader1);
         free(BMHeader2);
        fclose(f2);
        return EXIT_FAILURE;
    }
    printf("File1\n");
    printBMPHeader(BMHeader1);
    printf("\n");
    printDIBHeader(DIBHeader1);
    printf("\n");
    printf("File2\n");
    printBMPHeader(BMHeader2);
    printf("\n");
    printDIBHeader(DIBHeader2);
    printf("\n");
    //stripLSB(f1,DIBHeader1->width,DIBHeader1->height);
    //shiftMSB(f2,DIBHeader2->width,DIBHeader2->height);
    //combineBytes(f1,f2,DIBHeader1->width,DIBHeader1->height);
    //reveal(f1,DIBHeader1->width,DIBHeader1->height);
    //testFlip(f1,DIBHeader1->width,DIBHeader1->height);
    free(DIBHeader1);
    free(BMHeader1);
    free(DIBHeader2);
    free(BMHeader2);
   
    fclose(f1);
    fclose(f2);    
    return 0;  
    */
    
}
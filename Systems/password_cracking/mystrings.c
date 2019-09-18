//Navdeep Handa (nkh15)
#include <stdio.h>
#include <stdlib.h>

FILE * open_file(char * filename, char * mode)
{
    //file open, quits program if file doesn't exist
    FILE* input_file = fopen(filename, mode);
    if(input_file == NULL)
    {
        printf("Where's your file bro? Bc it ain't here\n");
        exit(1);
    }
    return input_file;
}

int main(int argc, char *argv[])
{
    FILE * file = open_file(argv[1], "rb");

    char buffer[1];
    char charCrew[5]; // i'm a char and all i need is me and my homies by my side :)

    while(!feof(file))
    {
        //read byte
        fread(buffer, sizeof(char), 1, file);
        char currChar = buffer[0];

        if(currChar >= 32 && currChar <= 126)
        {
            int charCount = 0;
            while(currChar >= 32 && currChar <= 126 && !feof(file))
            {
                charCount++;
                if(charCount <= 4)
                {
                    charCrew[charCount-1] = currChar;
                }
                if(charCount == 4)
                {
                    charCrew[charCount] = '\0';
                    printf("%s", charCrew);
                }
                else if(charCount > 4)
                {
                    printf("%c", currChar);
                }

                fread(buffer, sizeof(char), 1, file);
                currChar = buffer[0];
            }
            if(charCount >= 4)
                printf("\n");
        }
    }
    return 0;
}

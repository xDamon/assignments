/*
 A collection of functions relating
 to the manipulation of FILE pointers

 AUTHOR Damon Ezard
 VERSION 1.0.3
 SINCE 18/10/18
*/

#include <stdio.h>
#include <stdlib.h>
#include "coordinates.h"

/*
 Retrievs the next line in  a file.
 Dynamically allocates enough memory for
 the string. Must be a seekable file.

 PARAM f - A FILE pointer to read from
 PARAM line - A string pointer to store the line in

 RETURNS EOF or '\n'
*/
int getNextLine(FILE *f, char **line)
{
    long int pos = ftell(f);

    int ch = fgetc(f);
    int count = 0;

    /* Gets the line length */
    while (ch != '\n' && ch != EOF)
    {
        ch = fgetc(f);
        count++;
    }

    if (count != 0)
    {
        /* Space for '\0' */
        count++;

        /* Move file pointer back to start of line */
        fseek(f, pos, SEEK_SET);
        *line = (char *) malloc(sizeof(char) * count);
        fgets(*line, count, f);

        /* Remove '\n' or EOF */
        ch = fgetc(f);
    }
    
    return ch;
}

/*
 Writes a graphic log to a particular file
 with information on what action occurred and
 the coordinate values before and after that action.

 PARAM f - A FILE pointer to write to
 PARAM type - A string with what type of command was used
 PARAM previous - Coordinates before the command
 PARAM current - Coordinates after the command
*/
void writeLog(FILE *f, char *type, Coordinates previous, Coordinates current)
{
    char *format = "\t%s (%7.3f, %7.3f)-(%7.3f, %7.3f)\n";

    fprintf(f, format, type, previous.x, previous.y, current.x, current.y);

    if (ferror(f))
    {
        perror("Error writing log to file");
    }

    #ifdef DEBUG
        fprintf(stderr, format, type, previous.x, previous.y, current.x, current.y);

        if (ferror(stderr))
        {
            perror("Error writing log to stderr");
        }
    #endif
}

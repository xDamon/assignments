#ifndef FILE_UTIL_H
#define FILE_UTIL_H

#include "coordinates.h"

/*
 Retrievs the next line in  a file.
 Dynamically allocates enough memory for
 the string. Must be a seekable file.

 PARAM f - A FILE pointer to read from
 PARAM line - A string pointer to store the line in

 RETURNS EOF or '\n'
*/
int getNextLine(FILE *f, char **line);

/*
 Writes a graphic log to a particular file
 with information on what action occurred and
 the coordinate values before and after that action.

 PARAM f - A FILE pointer to write to
 PARAM type - A string with what type of command was used
 PARAM previous - Coordinates before the command
 PARAM current - Coordinates after the command
*/
void writeLog(FILE *f, char *type, Coordinates previous, Coordinates current);
#endif

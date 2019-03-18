#ifndef COMMANDS_H
#define COMMANDS_H

#include "linked_list.h"
#include "coordinates.h"

/*
 Iterates through a linked list of strings
 and performs actions on them if they are valid
 turtle commands.

 PARAM list - A linked list of lines (usually from a file)
 PARAM log - A file pointer for logging turtle commands
*/
void executeCommands(LinkedList *list, FILE *log);

/*
 Rotates an angle by another angle, keeping
 the overall angle between 0 and 360 degrees.

 PARAM curAngle - The current angle in degrees
 PARAM addAngle - The angle to add on to the current angle in degrees
*/
void rotate(double *curAngle, double addAngle);

/*
 Moves the current coordinates based on the
 current angle and a distance value, using trigonometry.

 PARAM current - The current coordinate values
 PARAM curAngle - The current angle in degrees
 PARAM distance - The distance to move by
*/
void move(Coordinates *current, double *curAngle, double distance);

/*
 Draws a line with a particular pattern from the current 
 coordinates to the new coordinates based on an angle and 
 a distance value using trigonometry

 PARAM current - The current coordinate values
 PARAM curAngle - The current angle in degrees
 PARAM distance - The distance to move by
 PARAM curPattern - The pattern to draw at each point along the line
*/
void draw(Coordinates *current, double *curAngle, double distance, char *curPattern);

/*
 Prints the current pattern at
 the particular point in a line

 PARAM plotData - The data to plot (string)
*/
void plotter(void *plotData);

#endif

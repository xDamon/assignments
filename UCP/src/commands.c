/*
 This file includes a collection of
 functions for executing turtle command
 strings.

 AUTHOR Damon Ezard
 VERSION 1.1.3
 SINCE 19/10/18
*/

#include <stdio.h>
#include "linked_list.h"
#include "effects.h"
#include "math_util.h"
#include "string_util.h"
#include "file_util.h"
#include "boolean.h"
#include "coordinates.h"
#include "commands.h"

/*
 Iterates through a linked list of strings
 and performs actions on them if they are valid
 turtle commands.

 PARAM list - A linked list of lines (usually from a file)
 PARAM log - A file pointer for logging turtle commands
*/
void executeCommands(LinkedList *list, FILE *log)
{
    double distance;
    double addAngle;
    double curAngle = 0.0;

    Coordinates previous = { 0.0, 0.0 };
    Coordinates current = { 0.0, 0.0 };

    int nRead;
    int curFg = 7;
    int curBg = 0;
    
    char newPattern;
    char curPattern[2] = { '+', '\0' };
    char *str;

    LinkedListNode *node = list->head;

    clearScreen();
    #ifdef SIMPLE
        curFg = 0;
        curBg = 7;
    #endif
    setFgColour(curFg);
    setBgColour(curBg);
    fputs("\t---\n", log);

    /*
     The list is not filtered prior to this
     because that would force all valid commands
     to be checked twice rather than once.
    */
    while (node != NULL)
    {
        str = (char *) node->value;

        if (startsWith(str, "ROTATE") == TRUE)
        {
            nRead = sscanf(str, "%*s %lf", &addAngle);

            if (nRead == 1)
            {
                rotate(&curAngle, addAngle);
            }
        }
        else if (startsWith(str, "MOVE") == TRUE)
        {
            nRead = sscanf(str, "%*s %lf", &distance);

            if (nRead == 1)
            {
                previous = current;
                move(&current, &curAngle, distance);
                writeLog(log, "MOVE", previous, current);
            }
        }
        else if (startsWith(str, "DRAW") == TRUE)
        {
            nRead = sscanf(str, "%*s %lf", &distance);

            if (nRead == 1)
            {
                previous = current;
                
                /*
                 The line function plots a point
                 even if there is no change in coordinates,
                 this prevents that.
                */
                if (distance != 0)
                {
                    draw(&current, &curAngle, distance, curPattern);
                }

                writeLog(log, "DRAW", previous, current);
            }
        }
        #ifndef SIMPLE
        else if (startsWith(str, "FG") == TRUE)
        {
            nRead = sscanf(str, "%*s %d", &curFg);

            if (nRead == 1)
            {
                setFgColour(curFg);
            }
        }
        else if (startsWith(str, "BG") == TRUE)
        {
            nRead = sscanf(str, "%*s %d", &curBg);

            if (nRead == 1)
            {
                setBgColour(curBg);
            }
        }
        #endif
        else if (startsWith(str, "PATTERN") == TRUE)
        {
            nRead = sscanf(str, "%*s %c", &newPattern);

            if (nRead == 1)
            {
                curPattern[0] = newPattern;
            }
        }

        node = node->next;
    }

    printf("\n\n");
    penDown();
}

/*
 Rotates an angle by another angle, keeping
 the overall angle between 0 and 360 degrees.

 PARAM curAngle - The current angle in degrees
 PARAM addAngle - The angle to add on to the current angle in degrees
*/
void rotate(double *curAngle, double addAngle)
{
    double newAngle = *curAngle + addAngle;

    /*
     Assignment spec requires the angle
     to be between 0 and 360 degrees
    */
    if (newAngle > 360.0)
    {
        newAngle -= 360.0;
    }
    else if (newAngle < 0.0)
    {
        newAngle += 360.0;
    }

    *curAngle = newAngle;
}

/*
 Moves the current coordinates based on the
 current angle and a distance value, using trigonometry.

 PARAM current - The current coordinate values
 PARAM curAngle - The current angle in degrees
 PARAM distance - The distance to move by
*/
void move(Coordinates *current, double *curAngle, double distance)
{
    Coordinates ratios = angleRatios(*curAngle);
    Coordinates deltas = distanceDeltas(ratios, distance);

    current->x += ratios.x + deltas.x;
    current->y += ratios.y + deltas.y;
}

/*
 Draws a line with a particular pattern from the current 
 coordinates to the new coordinates based on an angle and 
 a distance value using trigonometry

 PARAM current - The current coordinate values
 PARAM curAngle - The current angle in degrees
 PARAM distance - The distance to move by
 PARAM curPattern - The pattern to draw at each point along the line
*/
void draw(Coordinates *current, double *curAngle, double distance, char *curPattern)
{
    /* Line function require integers to draw */
    int roundedX = round(current->x);
    int roundedY = round(current->y);

    Coordinates ratios = angleRatios(*curAngle);
    Coordinates deltas = distanceDeltas(ratios, distance);

    line(
        roundedX,
        roundedY,
        roundedX + round(deltas.x),
        roundedY + round(deltas.y),
        &plotter,
        curPattern
    );

    current->x += ratios.x + deltas.x;
    current->y += ratios.y + deltas.y;
}

/*
 Prints the current pattern at
 the particular point in a line

 PARAM plotData - The data to plot (string)
*/
void plotter(void *plotData)
{
    printf("%s", (char *) plotData);
}

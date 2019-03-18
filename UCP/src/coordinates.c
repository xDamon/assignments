#include <math.h>
#include "math_util.h"
#include "coordinates.h"

/*
 Calculates the angle ratios for both x and y
 coordinates using an angle in degrees. The coordinate
 system for this program is not standard so angles
 are transformed by taking the negative value of 
 the trigonometric function where the angle is
 180.0 take the angle passed in.
 
 For example: -cos(180.0 - 90.0)

 PARAM angle - The angle to calculate the ratio from

 RETURNS coordinates with both x and y ratios
*/
Coordinates angleRatios(double angle)
{
    Coordinates ratios;

    /* 90 degrees is up, 0 degrees is right */
    double rads = radians(180.0 - angle);

    ratios.x = -cos(rads);
    ratios.y = -sin(rads);

    return ratios;
}

/*
 Calculates the change in distances of both
 the x and y coordinates using trigonometric
 ratios and a distance value

 PARAM ratios - x and y ratios
 PARAM distance - the distance to multiply by

 RETURNS coordinates with changes in distance
*/
Coordinates distanceDeltas(Coordinates ratios, double distance)
{
    Coordinates deltas;

    /* Stop double printing */
    double lineDistance = (distance - 1.0);

    deltas.x = lineDistance * ratios.x;
    deltas.y = lineDistance * ratios.y;

    return deltas;
}

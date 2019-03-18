#include <math.h>
#include <stdio.h>

#define PI 3.14159265358979323846

/*
 Rounds a real number (double)
 to the nearest integer.

 PARAM value - A value to round

 RETURN The rounded value
*/
int round(double value)
{
    int rounded;

    if (value < 0)
    {
        rounded = (int) (value - 0.5);
    }
    else
    {
        rounded = (int) (value + 0.5);
    }

    return rounded;
}

/*
 Converts a real valued angle from
 degrees to radians.

 PARAM degrees - A value in degrees to convert

 RETURNS The value in radians
*/
double radians(double degrees)
{
    return degrees * (PI / 180.0);
}

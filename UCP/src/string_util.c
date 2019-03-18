/*
 Collection of functions relating
 to the manipulation of strings.

 AUTHOR Damon Ezard
 VERSION 1.1.1
 SINCE 18/10/18
*/

#include <string.h>
#include "boolean.h"

#define LOWER_A 97
#define LOWER_Z 122
#define CASE_DIFF 32

/*
 Converts a string to uppercase.

 PARAM str - A string to convert
*/
void uppercase(char *str)
{
    int i;
    int length = strlen(str);

    for (i = 0; i < length; i++)
    {
        /* Between 'a' and 'z' inclusive */
        if (str[i] >= LOWER_A && str[i] <= LOWER_Z)
        {
             str[i] -= CASE_DIFF;
        }
    }
}

/*
 Checks if a string starts with
 another string.

 PARAM big - The larger string
 PARAM small - The substring to check for

 RETURNS TRUE (!0) or FALSE (0)
*/
int startsWith(char *big, char *small)
{
    int starts = FALSE;
    int length = strlen(small);

    /*
     Checks the start of the big string
     and stops at the length of the small string
    */
    if (strncmp(big, small, length) == 0)
    {
        starts = TRUE;
    }

    return starts;
}

#ifndef STRING_UTIL_H
#define STRING_UTIL_H

/*
 Converts a string to uppercase.

 PARAM str - A string to convert
*/
void uppercase(char *str);

/*
 Checks if a string starts with
 another string.

 PARAM big - The larger string
 PARAM small - The substring to check for

 RETURNS TRUE (!0) or FALSE (0)
*/
int startsWith(char *big, char *small);

#endif

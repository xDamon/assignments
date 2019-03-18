/*
 This file is the main file of the
 turtle graphics program.

 AUTHOR Damon Ezard
 VERSION 1.6.1
 SINCE 19/10/18
*/

#include <stdio.h>
#include <stdlib.h>
#include "commands.h"
#include "linked_list.h"
#include "file_util.h"
#include "string_util.h"

#define LOG_FILE "graphics.log"

/*
 The main function in the turtle
 graphics program.

 PARAM argc - The number of arguments
 PARAM argv - The values of the arguments

 RETURNS 0 if the program ran correctly
*/
int main(int argc, char **argv)
{
    char *str;

    FILE *f = NULL;
    FILE *log = NULL;

    LinkedList *lines = NULL;

    if (argc == 2)
    {
        f = fopen(argv[1], "r");
        log = fopen(LOG_FILE, "a");

        if (f == NULL)
        {
            perror("Error opening file");
        }
        else if (log == NULL)
        {
            perror("Error opening log file");
        }
        else
        {
            lines = createList();

            while (getNextLine(f, &str) != EOF)
            {
                /* Case insensitive */
                uppercase(str);
                insertFirst(lines, str);
            }

            if (ferror(f))
            {
                perror("Error reading from file");
            }
            else if (lines->length == 0)
            {
                printf("Error: File is empty\n");
            }
            else
            {
                /*
                 Inserting at the front of a
                 linked list causes the list to
                 be in reverse order. The list
                 must be reversed to get the correct
                 order.
                */
                reverseList(lines);
                executeCommands(lines, log);
            }

            freeList(lines);
        }

        /*
         These checks are here and not right above
         because of the possibility that one file
         might open correctly and the might not.
        */
        if (f != NULL)
        {
            fclose(f);
        }

        if (log != NULL)
        {
            fclose(log);
        }
    }
    else
    {
        printf("Expected 1 filename argument, got %d\n arguments", argc - 1);
    }

    return 0;
}

/*
 This is a collection of files
 relating to the manipulation of
 a linked list.

 createList
 freeList
 insertFirst

 were originally written here but
 also were previously submitted for
 UCP Practical Worksheet 7.

 AUTHOR Damon Ezard
 VERSION 1.0.1
 SINCE 18/10/18
*/

#include <stdlib.h>
#include "linked_list.h"

/*
 Creates a linked list by allocating
 enough memory for it, then initialising it
 with default values.

 RETURNS A pointer to the linked list
*/
LinkedList *createList(void)
{
    LinkedList *list;

    list = (LinkedList *) malloc(sizeof(LinkedList));
    list->head = NULL;
    list->length = 0;

    return list;
}

/*
 Frees a linked list and each of its
 nodes, as well as the values of the nodes.

 PARAM list - A linked list pointer
*/
void freeList(LinkedList *list)
{
    LinkedListNode *next;
    LinkedListNode *node = list->head;

    while (node != NULL)
    {
        /* Can only store malloc'd data */
        next = node->next;
        free(node->value);
        free(node);
        node = next;
    }

    free(list);
}

/*
 Reverses the order of the nodes
 in a linked list.

 PARAM list - The list to reverse
*/
void reverseList(LinkedList *list)
{
    LinkedListNode *prev = NULL;
    LinkedListNode *next = NULL;
    LinkedListNode *current = list->head;

    if (current != NULL)
    {
        do
        {
            /*
             Changes the next pointer
             of every element to its
             previous element, reversing
             the entire list.
            */
            next = current->next;
            current->next = prev;
            prev = current;
            current = next;
        }
        while (current != NULL);

        list->head = prev;
    }
}

/*
 Inserts an element into the first position
 of a linked list. It is important that this
 element has been malloc'd.

 PARAM list - A list pointer to add the value to
 PARAM value - The malloc'd value to insert
*/
void insertFirst(LinkedList *list, void *value)
{
    LinkedListNode *node = (LinkedListNode *) malloc(sizeof(LinkedListNode));

    node->value = value;
    node->next = list->head;

    list->head = node;
    list->length++;
}


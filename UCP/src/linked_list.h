#ifndef LINKED_LIST_H
#define LINKED_LIST_H

/*
 Struct for an element in a linked list.
 It is generic in the sense it can hold any
 value due to it being of type void pointer.
*/
typedef struct LinkedListNode {
    void *value;
    struct LinkedListNode *next;
} LinkedListNode;

/*
 The overall structure that references
 the first node and keeps track of the length.
 This is the struct that is manipulated by
 the other functions
*/
typedef struct {
    int length;
    LinkedListNode *head;
} LinkedList;

/*
 Creates a linked list by allocating
 enough memory for it, then initialising it
 with default values.

 RETURNS A pointer to the linked list
*/
LinkedList *createList(void);

/*
 Frees a linked list and each of its
 nodes, as well as the values of the nodes.

 PARAM list - A linked list pointer
*/
void freeList(LinkedList *list);

/*
 Reverses the order of the nodes
 in a linked list.

 PARAM list - The list to reverse
*/
void reverseList(LinkedList *list);

/*
 Inserts an element into the first position
 of a linked list. It is important that this
 element has been malloc'd.

 PARAM list - A list pointer to add the value to
 PARAM value - The malloc'd value to insert
*/
void insertFirst(LinkedList *list, void *value);
#endif

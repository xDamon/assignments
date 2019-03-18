def quickSort(arr, comparator = lambda v1, v2: v1 < v2):
    """Wrapper function for the recursive function.

    Args:
        arr (Array or ArrayList): The array to sort.
        comparator (function): A function that compares to values in the array.
    """
    quickSortRecurse(arr, 0, len(arr) - 1, comparator)

def quickSortRecurse(arr, leftIndex, rightIndex, comparator):
    """Recursive function for sorting an array.

    Args:
        arr (Array or ArrayList): The array to srot.
        leftIndex (int): The lower boundary of the sort.
        rightIndex (int): The upper boundary of the sort.
        comparator (function): A function that compares to values in the array.
    """
    # Adapted from sorts lecture slide pseudocode.
    if rightIndex > leftIndex:
        pivotIndex = (leftIndex + rightIndex) // 2
        newPivotIndex = doPartitioning(arr, leftIndex, rightIndex, pivotIndex, comparator)

        quickSortRecurse(arr, leftIndex, newPivotIndex - 1, comparator)
        quickSortRecurse(arr, newPivotIndex + 1, rightIndex, comparator)

def doPartitioning(arr, leftIndex, rightIndex, pivotIndex, comparator):
    """Partions an array around a pivot and sorts it.

    Args:
        arr (Array or ArrayList): The array to sort.
        arr (Array or ArrayList): The array to srot.
        leftIndex (int): The lower boundary of the sort.
        rightIndex (int): The upper boundary of the sort.
        pivotIndex (int): The pivot partition with.
        comparator (function): A function that compares to values in the array.

    """
    # Adapted from sorts lecture slide pseudocode.
    pivotVal = arr[pivotIndex]
    arr[pivotIndex] = arr[rightIndex]
    arr[rightIndex] = pivotVal

    currentIndex = leftIndex

    for ii in range(leftIndex, rightIndex):
        if comparator(arr[ii], pivotVal):
            temp = arr[ii]
            arr[ii] = arr[currentIndex]
            arr[currentIndex] = temp
            currentIndex = currentIndex + 1

    newPivotIndex = currentIndex
    arr[rightIndex] = arr[newPivotIndex]
    arr[newPivotIndex] = pivotVal

    return newPivotIndex
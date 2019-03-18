import numpy

def comparator(mappings, v1, v2):
    """Comparator for sorting objects in an array.

    This function uses mapping lambdas to get properties of
    a particular object and lexicographically compare those
    properties. This allows for a 'multi-sort' or nested sort
    operation.

    Args:
        mappings (ArrayList<function>): A list of functions to compare by.
        v1 (object): The first value to compare
        v2 (object): The second value to compare

    Returns:
        True if v1 < v2 and False if v1 >= v2 
    """
    length = len(mappings)

    arr1 = numpy.empty(length, dtype = object)
    arr2 = numpy.empty(length, dtype = object)

    for i in range(length):
        arr1[i] = mappings[i](v1)
        arr2[i] = mappings[i](v2)

    # If both arrays are the same then
    # just set the index to 0.
    if (numpy.all(arr1 == arr2)):
        index = 0
    else:
        # Lexicographically compare elements.
        # E.g: [5,0,0] is larger than [4,10,10] because the first
        # index that they diif at is compared.
        index = numpy.where((arr1 < arr2) != (arr1 > arr2))[0][0]

    return arr1[index] < arr2[index]

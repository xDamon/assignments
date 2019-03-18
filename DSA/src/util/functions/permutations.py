def permutations(elements):
    """Calculates the permuations of an array.

    This function has a time complexity of O(n!) so it
    will take a very long time for larger arrays.

    Args:
        elements (Array or ArrayList): The array to get permutations for.
    
    Yields:
        A permutation of the array.
    """
    # Adapted from Python implementation of it in itertools.
    if len(elements) <= 1:
        yield elements
    else:
        for permutation in permutations(elements[1:]):
            for i in range(len(elements)):
                yield permutation[:i] + elements[0:1] + permutation[i:]

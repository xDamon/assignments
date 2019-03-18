def nextPrime(start):
    """Calculates the first prime after a specified number.

    Args:
        start (int): The number get the next prime for.

    Returns:
        A prime number that occurs after 'start'.
    """
    prime = start

    if start % 2 == 0:
        prime -= 1

    isPrime = False
    
    while not isPrime:
        prime += 2
        count = 3
        isPrime = True
        root = prime ** 0.5

        while count <= root and isPrime:
            if prime % count == 0:
                isPrime = False
            else:
                count += 2

    return prime
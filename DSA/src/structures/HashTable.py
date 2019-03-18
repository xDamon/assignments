import numpy

from util.functions.prime import nextPrime

class HashTableError(Exception):
    """An error raised by a HashTable."""
    pass

class HashTableEntry:
    """An entry in a HashTable.

    Args:
        key (str): The key to identify the value.
        value (object): The value to store.

    Attributes:
        key (str): The key to identify the value.
        value (object): The value to store.
    """
    def __init__(self, key, value):
        self.key = key
        self.value = value

class HashTable:
    """A table that hashes keys that map to values.

    Note:
        Table resizes when its below or above minimum and
        maximum load factors respectively. The table is also
        orderless.

    Args:
        capacity (int): The initial capacity of the table.
        minFactor (float): The minimum load factor.
        maxFactor (float): The maximum load factor.

    Attributes:
        table(Array<object>): The table of entries.
        minFactor (float): The minimum load factor.
        maxFactor (float): The maximum load factor.
        count (int): The number of entries in the table.
    """
    def __init__(self, capacity = 30, minFactor = 0.4, maxFactor = 0.75):
        self.table = numpy.empty(nextPrime(capacity), dtype = object)
        self.minFactor = minFactor
        self.maxFactor = maxFactor
        self.count = 0

    def __iter__(self):
        # Iteration is O(n) where n is the size of the table
        # not the actual amount of values in the table.
        for entry in self.table:
            if entry is not None:
                yield numpy.array([entry.key, entry.value])

    def __len__(self):
        return self.count

    def set(self, key, value):
        """Sets a key value pair in the table.

        Args:
            key (str): The key to map the value to.
            value (object): The value to store.
        """
        entry = HashTableEntry(key, value)

        if self.count / len(self.table) >= self.maxFactor:
            self.__resize(self.count << 1)

        hashIndex = self.__hash(key)
        hashStep = self.__hashStep(key)

        while self.table[hashIndex] is not None and self.table[hashIndex].key != key:
            hashIndex = (hashIndex + hashStep) % len(self.table)

        self.table[hashIndex] = entry
        self.count += 1

    def get(self, key):
        """Gets a value from the table based off of its key.

        Args:
            key (str): The key to search for.

        Returns:
            The value stored at that keys location.

        Raises:
            HashTableError: If the key cannot be found.
        """
        hashIndex = self.__hash(key)
        hashStep = self.__hashStep(key)
        origIndex = hashIndex

        found = False
        giveUp = False

        while not found and not giveUp:
            if self.table[hashIndex] is None:
                giveUp = True
            elif self.table[hashIndex].key == key:
                found = True
            else:
                hashIndex = (hashIndex + hashStep) % len(self.table)

                if hashIndex == origIndex:
                    giveUp = True

        if not found:
            raise HashTableError("Cannot find key in HashTable")

        return self.table[hashIndex].value

    def has(self, key):
        """Checks if a key is in the table.

        Args:
            key (str): The key to search for.

        Returns:
            True if found, False if not.
        """
        has = True

        try:
            self.get(key)
        except HashTableError:
            has = False

        return has

    def delete(self, key):
        """Deletes a key value pair from the table.

        Args:
            key (str): The key to search for.
        """
        hashIndex = self.__hash(key)
        hashStep = self.__hashStep(key)
        origIndex = hashIndex

        found = False
        giveUp = False

        while not found and not giveUp:
            if self.table[hashIndex] is None:
                giveUp = True
            elif self.table[hashIndex].key == key:
                found = True
            else:
                hashIndex = (hashIndex + hashStep) % len(self.table)

                if hashIndex == origIndex:
                    giveUp = True

        if found:
            self.table[hashIndex] = None
        else:
            raise HashTableError("Cannot find key in HashTable")

        self.count -= 1

        if self.count / len(self.table) <= self.minFactor:
            self.__resize(self.count >> 1)

    def filter(self, fn):
        """Filters the values in the table.

        Args:
            fn (function): The filter function.

        Returns:
            A new HashTable with the filtered values.
        """
        filtered = HashTable(self.count)
        
        for entry in self.table:
            if entry is not None and fn(entry.value):
                filtered.set(entry.key, entry.value)

        return filtered

    def keys(self):
        """Iterates over the keys in the table."""
        for entry in self.table:
            if entry is not None:
                yield entry.key

    def values(self):
        """Iterates over the values in the table."""
        for entry in self.table:
            if entry is not None:
                yield entry.value

    def __resize(self, size):
        entries = self.table
        newSize = nextPrime(size)

        self.table = numpy.empty(newSize, dtype = object)
        self.count = 0

        for entry in entries:
            if entry is not None:
                self.set(entry.key, entry.value)

    def __hash(self, key):
        hashIndex = 0

        for i in range(len(key)):
            hashIndex = (31 * hashIndex) + ord(key[i])

        return hashIndex % len(self.table)

    def __hashStep(self, key):
        step = 0
        prime = nextPrime(len(self.table) >> 2)

        for i in range(len(key)):
            step = step ^ ((step << 5) + (step << 2) + ord(key[i]))

        return prime - (step % prime)

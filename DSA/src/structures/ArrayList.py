import numpy

class ArrayListError(Exception):
    """An Error that is raised by ArrayList"""
    pass

class ArrayList:
    """A list structure implemented with an array.

    Args:
        initial (int or iterable): The initial capacity or values

    Attributes:
        count (int): The number of values in the list.
        items (Array<object>): The values in the list.
    """
    def __init__(self, initial = 10):
        self.count = 0

        if type(initial) is int:
            self.items = numpy.empty(initial, dtype = object)
        elif hasattr(initial, "__iter__"):
            length = 10

            # More efficient to set capacity to
            # length of iterable if possible.
            if hasattr(initial, "__len__"):
                length = len(initial)

            self.items = numpy.empty(length, dtype = object)
        
            for item in initial:
                self.append(item)
        else:
            self.items = numpy.empty(10, dtype = object)
            

    def __len__(self):
        return self.count

    def __iter__(self):
        for i in range(self.count):
            yield self.items[i]

    def __add__(self, other):
        new = ArrayList(len(self) + len(other))
        
        for value in self:
            new.append(value)

        for value in other:
            new.append(value)

        return new

    def __getitem__(self, index):
        item = None

        if self.__validIndex(index):
            if type(index) is slice:
                # Allows for arrayList[2:3] etc
                if index.stop is None or index.stop >= self.count:
                    index = slice(index.start, self.count, index.step)

                item = ArrayList(self.items[index])
            else:
                item = self.items[index] 
        else:
            raise IndexError("ArrayList index out of range")

        return item

    def __setitem__(self, index, item):
        if self.__validIndex(index):
            self.items[index] = item
        else:
            raise IndexError("ArrayList index out of range")

    def append(self, item):
        """Adds an item to the end of the list.

        Note:
            The array grows in size if it is full.

        Args:
            item (object): Any value to add to the end.
        """
        if self.count == len(self.items):
            self.__grow()

        self.items[self.count] = item
        self.count += 1

    def pop(self):
        """Removes the last value from the list.

        Returns:
            The last value from the list.
        """
        self.count -= 1
        popped = self.items[self.count]
        self.items[self.count] = None
        
        return popped

    def find(self, fn):
        """Finds a value in the list based on a function.

        Args:
            fn (function): The filter to use.

        Returns:
            The first item that matches the filter.
        """
        item = None
        index = 0
        found = False

        while not found and index < self.count:
            if fn(self.items[index]):
                found = True
            else:
                index += 1

        if found:
            item = self.items[index]
        else:
            raise ArrayListError("Cannot find item")

        return item

    def filter(self, fn):
        """Filters a list by a function.
        
        Args:
            fn (function): The filter to use.

        Returns:
            A new ArrayList of the filtered values.
        """
        filtered = ArrayList()

        for item in self:
            if fn(item):
                filtered.append(item)

        return filtered

    def __validIndex(self, index):
        valid = False

        if type(index) is int:
            valid = index >= 0 and index < self.count
        elif type(index) is slice:
            valid = True

        return valid

    def __grow(self):
        # Count divided by 2
        extraCapacity = self.count >> 1

        if extraCapacity < 5:
            extraCapacity = 5

        extraItems = numpy.empty(extraCapacity, dtype = object)
        self.items = numpy.concatenate([self.items, extraItems])


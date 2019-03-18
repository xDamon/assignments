import sys
import os
import traceback

# Needed because python's package
# system is very annoying.
sys.path.insert(0,
    os.path.join(
        os.path.dirname(
            os.path.dirname(
                os.path.abspath(__file__)
            )
        ),
        "src"
    )
)

from structures.ArrayList import ArrayList
from structures.HashTable import HashTable
from structures.Graph import Graph

from structures.UnitTestArrayList import UnitTestArrayList
from structures.UnitTestHashTable import UnitTestHashTable
from structures.UnitTestGraph import UnitTestGraph

def runTests(testClass, actualClass):
    print(f"Running tests for {actualClass.__name__}:")

    # This just looks for static methods
    # in the class definition and executes
    # them with an instance of the class they
    # are testing.
    for value in testClass.__dict__.values():
        if type(value) is staticmethod:
            value.__func__(actualClass())
            # Testing stops when an error occurs
            print("\t%-15s %1s %10s" % (value.__func__.__name__, "-", "Passed"))

    print()

runTests(UnitTestArrayList, ArrayList)
runTests(UnitTestHashTable, HashTable)
runTests(UnitTestGraph, Graph)
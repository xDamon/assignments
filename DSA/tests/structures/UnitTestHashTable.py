from structures.HashTable import HashTable
from util.functions.prime import nextPrime

class UnitTestHashTable:
    @staticmethod
    def testInit(table):
        expected = 0
        actual = table.count

        assert expected == actual

        new = HashTable(10)
        expected = nextPrime(10)
        actual = len(new.table)

        assert expected == actual

    @staticmethod
    def testIter(table):
        expected = 2
        actual = 0

        table.set("key", "value")
        table.set("2nd", "thing")

        for _ in table:
            actual += 1

        assert expected == actual

    @staticmethod
    def testLen(table):
        expected = 0
        actual = len(table)

        assert expected == actual

        table.set("key", "value")
        table.set("2nd", "thing")

        expected = 2
        actual = len(table)

        assert expected == actual

    @staticmethod
    def testSet(table):
        expected = "value"

        table.set(expected, expected)

        for key, value in table:
            assert expected == key and expected == value

    @staticmethod
    def testGet(table):
        expected = "value"

        table.set(expected, expected)
        table.set("some", "other")
        table.set("values", "here")

        actual = table.get(expected)

        assert expected == actual

    @staticmethod
    def testHas(table):
        expected = False
        actual = table.has("key")

        assert expected == actual

        expected = True
        table.set("key", "value")
        actual = table.has("key")

        assert expected == actual

    @staticmethod
    def testDelete(table):
        expected = False

        table.set("key", "value")
        table.delete("key")
        
        actual = table.has("key")

        assert expected == actual

    @staticmethod
    def testFilter(table):
        expected = 1

        table.set("some", 0)
        table.set("values", 1)
        table.set("here", 10)

        filtered = table.filter(lambda v: v > 5)
        actual = len(filtered)

        assert expected == actual
from structures.ArrayList import ArrayList

class UnitTestArrayList:
    @staticmethod
    def testInit(arr):
        new = ArrayList(10)
        expected = 0
        actual = new.count

        assert expected == actual

        new = ArrayList([1,2,3,4,5,6])
        expected = 6
        actual = new.count

        assert expected == actual

    @staticmethod
    def testLen(arr):
        expected = 0
        actual = len(arr)

        assert expected == actual

        new = ArrayList([1,2,3,4,5,6,7,8,9,10])
        expected = 10
        actual = len(new)

        assert expected == actual

    @staticmethod
    def testIter(arr):
        new = ArrayList([1,2,3,4,5])
        expected = 5
        actual = 0

        for _ in new:
            actual += 1

        assert expected == actual

    @staticmethod
    def testAdd(arr):
        arr = ArrayList([1,2,3,4,5])
        other = ArrayList([6,7,8])

        new = arr + other

        for i in range(8):
            assert new[i] == i + 1

    @staticmethod
    def testGetItem(arr):
        expected = True
        actual = False

        try:
            arr[0]
        except IndexError:
            actual = True

        assert expected == actual

        new = ArrayList([1,2,3])
        expected = 2

        try:
            actual = new[1]
        except IndexError:
            actual = None

        assert expected == actual

    @staticmethod
    def testSetItem(arr):
        new = ArrayList([1,2,3])
        expected = True
        actual = False

        try:
            new[11] = 12
        except IndexError:
            actual = True

        assert expected == actual

        expected = 10

        try:
            new[2] = expected
            actual = new[2]
        except IndexError:
            actual = None

        assert expected == actual
        

    @staticmethod
    def testAppend(arr):
        expected = "value"
        arr.append(expected)
        actual = arr[0]

        assert expected == actual

    @staticmethod
    def testPop(arr):
        expected = "value"
        arr.append(expected)
        actual = arr.pop()

        assert expected == actual

    @staticmethod
    def testFind(arr):
        expected = "value"
        arr.append("random")
        arr.append(expected)
        arr.append("other")
        arr.append("things")
        actual = arr.find(lambda v: v == expected)

        assert expected == actual

    @staticmethod
    def testFilter(arr):
        expected = 5

        for i in range(10):
            arr.append(i)

        filtered = arr.filter(lambda v: v >= expected)

        for v in filtered:
            assert v >= expected
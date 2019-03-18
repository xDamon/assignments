from structures.ArrayList import ArrayListError

class UnitTestGraph:
    @staticmethod
    def testInit(graph):
        pass

    @staticmethod
    def testAddVertex(graph):
        expected = "A"
        graph.addVertex(expected)
        actual = graph.vertices.get(expected).label

        assert expected == actual

    @staticmethod
    def testAddEdge(graph):
        expected = True
        actual = True

        graph.addVertex("A")
        graph.addVertex("B")
        graph.addEdge("A", "B", 10)

        try:
            graph.edges.find(lambda e: e.start.label == "A" and e.end.label == "B")
        except ArrayListError:
            actual = False

        assert expected == actual

    @staticmethod
    def testHasVertex(graph):
        expected = True
        graph.addVertex("A")
        actual = graph.hasVertex("A")

        assert expected == actual

    @staticmethod
    def testBPT(graph):
        expected = [
            ["A", "C"],
            ["C", "A"],
            ["A", "E"]
        ]

        for letter in ["A", "B", "C", "D", "E"]:
            graph.addVertex(letter)

        graph.addEdge("A", "B", 10)
        graph.addEdge("A", "C", 10)
        graph.addEdge("A", "E", 1)
        graph.addEdge("B", "D", 10)
        graph.addEdge("C", "D", 5)
        graph.addEdge("D", "E", 10)

        actual = graph.brutePathThrough("A", ["C"], "E")

        for i in range(len(expected)):
           assert expected[i][0] == actual[i][0]
           assert expected[i][1] == actual[i][1]
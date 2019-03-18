import numpy

from util.functions.permutations import permutations

from structures.HashTable import HashTable, HashTableError
from structures.ArrayList import ArrayList

class GraphError(Exception):
    """An error raised by a Graph."""
    pass

class GraphEdge:
    """A weighted edge on a directed graph between vertices.
    
    Args:
        start (GraphVertex): The start vertex.
        end (GraphVertex): The end vertex.
        weight (float): The weight of the edge.

    Attributes:
        start (GraphVertex): The start vertex.
        end (GraphVertex): The end vertex.
        weight (float): The weight of the edge.
    """
    def __init__(self, start, end, weight):
        self.start = start
        self.end = end
        self.weight = weight

class GraphVertex:
    """A vertex on a graph.
    
    Args:
        label (str): The label of the vertex.
        value (object): The value of the vertex if it has one.

    Attributes:
        label (str): The label of the vertex.
        value (object): The value of the vertex if it has one.
        edges (ArrayList<GraphEdge>): The edges this vertex has.
    """
    def __init__(self, label, value = None):
        self.label = label
        self.value = value
        self.edges = ArrayList()

class Graph:
    """A network of vertices and edges.

    Attributes:
        vertices (HashTable<str, GraphVertex): The vertices on the graph.
        edges (ArrayList<GraphEdge>): The edges on the graph.
    """
    def __init__(self):
        self.vertices = HashTable()
        self.edges = ArrayList()
        self.__distCache = None
        self.__afterCache = None

    def addVertex(self, label, value = None):
        """Adds a vertex to the graph.

        Args:
            label (str): The label for the vertex.
            value (object): The value of the vertex if it needs one.
        """
        self.vertices.set(label, GraphVertex(label, value))

    def addEdge(self, label1, label2, weight):
        """Adds an edge to the graph.

        Args:
            label1 (str): The label of the first vertex.
            label2 (str): The label of the second vertex.
            weight (float): The weight of the edge.
        """
        vertex1 = self.vertices.get(label1)
        vertex2 = self.vertices.get(label2)

        edge = GraphEdge(vertex1, vertex2, weight)
        back = GraphEdge(vertex2, vertex1, weight)

        # Edges are directed so add the same edge
        # in different direction twice for undirected.
        vertex1.edges.append(edge)
        vertex2.edges.append(back)

        self.edges.append(edge)
        self.edges.append(back)

    def getVertex(self, label):
        """Gets a vertex from its label.

        Args:
            label (str): The label to find.

        Returns:
            The GraphVertex with that label.

        Raises:
            GraphError: If not vertex exists with that label.
        """
        value = None

        try:
            value = self.vertices.get(label)
        except HashTableError:
            raise GraphError(f"Cannot get vertice with label {label}")

        return value

    def hasVertex(self, label):
        """Checks if a vertex exists via its label.

        Args:
            label (str): The label to look for.

        Returns:
            True if the vertex exists, False if not.
        """
        has = True

        try:
            self.vertices.get(label)
        except HashTableError:
            has = False

        return has

    def brutePathThrough(self, start, mustVisit, end, useCache = False):
        """Uses brute force to find the shortest path between 'must pass' vertices.
        
        Don't run this if there are more than 10 or so must pass vertices. It will take
        an extremely long time to calculate. O(n!).

        Args:
            start (str): The vertex to start at.
            mustVist (Array<str> or ArrayList<str>): The vertices that also must be passed.
            end (str): The vertex to finish at.
            useCache (bool): Whether to use cached distances or not.

        Returns:
            ArrayList<ArrayList<str, str, float>>
        """
        matrices = self.floydWarshall(useCache)

        dist = matrices[0]
        after = matrices[1]

        shortest = float("inf")
        paths = ArrayList()

        for permutation in permutations(mustVisit):
            vertices = ArrayList([start])
            currentPaths = ArrayList()

            for u in permutation:
                vertices = vertices + self.shortestPath(vertices.pop(), u, after)

            vertices = vertices + self.shortestPath(vertices.pop(), end, after)

            total = 0

            for i in range(len(vertices) - 1):
                distance = dist.get(vertices[i]).get(vertices[i + 1])
                currentPaths.append(numpy.array([vertices[i], vertices[i + 1], distance], dtype = object))
                total += distance

            if shortest > total:
                shortest = total
                paths = currentPaths

        return paths

    def floydWarshall(self, useCache = False):
        """Uses Floyd Warshall's algorithm to get distances.

        Args:
            useCache (bool): Whether to use cache or not.
        """
        hasDist = self.__distCache is not None
        hasAfter = self.__afterCache is not None

        if useCache and hasDist and hasAfter:
            dist = self.__distCache
            after = self.__afterCache
        else:
            # Adapted code from
            # https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
            dist = HashTable(300)
            after = HashTable(300)

            count = 0
            length = len(self.vertices) ** 3

            for u in self.vertices.keys():
                dist.set(u, HashTable())
                after.set(u, HashTable())
                for v in self.vertices.keys():
                    dist.get(u).set(v, float("inf"))
                    after.get(u).set(v, None)

            for edge in self.edges:
                u = edge.start.label
                v = edge.end.label
                w = edge.weight

                dist.get(u).set(v, w)
                after.get(u).set(v, v)

            for u in self.vertices.keys():
                for v in self.vertices.keys():
                    for t in self.vertices.keys():
                        count += 1
                        print("Floyd Warshall: %.2f%%" % ((count / length) * 100.0), end = "\r")

                        distTable1 = dist.get(v)
                        afterTable1 = after.get(v)

                        distance = distTable1.get(u) + dist.get(u).get(t)

                        if distTable1.get(t) > distance:
                            distTable1.set(t, distance)
                            afterTable1.set(t, afterTable1.get(u))

            self.__distCache = dist
            self.__afterCache = after

        return numpy.array([dist, after], dtype = object)

    def shortestPath(self, u, v, after):
        """Gets shortest path between two vertices.

        Args:
            u (str): The start vertex.
            v (str): The end vertex.
            after (HashTable<str, HashTable<str, str>>): The connections data

        Returns
            ArrayList with the shortest path.
        """
        # Adapted code from
        # https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
        vertices = ArrayList()

        if after.get(u).get(v) is not None:
            vertices.append(u)
            while u != v:
                u = after.get(u).get(v)
                vertices.append(u)
        
        return vertices
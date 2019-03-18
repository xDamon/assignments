"""Election

This module includes everything relating to
the election class.
"""

import re
import numpy

from util.functions.time import timestampToMinutes

from election.Candidate import Candidate
from election.Party import Party
from election.State import State
from election.Division import Division

from structures.HashTable import HashTable
from structures.Graph import Graph

class Election:
    """Represents all the data stored about an election.

    Args:
        kwargs (**object): Keyword arguments for each attribute.

    Attributes:
        candidates (HashTable<str, Candidate>): The candidates in the election.
        parties (HashTable<str, Party>): The parties in the election.
        states (HashTable<str, State>): The states involved in the election.
        divisions (HashTable<str, Division>): The divisions involved in the election.
        graph (Graph): A graph showing the travelling time between divisions.
    """
    def __init__(self, **kwargs):
        self.candidates = kwargs["candidates"]
        self.parties = kwargs["parties"]
        self.states = kwargs["states"]
        self.divisions = kwargs["divisions"]
        self.graph = kwargs["graph"]

    @staticmethod
    def fromFiles(candidatesFilePath, prefsFilePaths, distanceFilePaths):
        """Creates an Election object from files.

        Args:
            candidatesFilePath (str): The file path to candidate data.
            prefsFilePaths (Array<str>): The file paths to polling preferences for each state.
            distanceFilePaths (Array<str>): The distance file paths for making iteneraries.

        Returns:
            An initialised Election object.
        """
        # Obtained from https://stackoverflow.com/.
        csvRegex = r",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"

        with open(candidatesFilePath, "r") as f:
            lines = f.readlines()[2:]
            length = len(lines)

            candidates = HashTable(length)
            parties = HashTable(length // 10)
            states = HashTable(length // 100)
            divisions = HashTable(length // 10)

            for line in lines:
                clean = line.replace("\n", "")
                tokens = numpy.array(re.split(csvRegex, clean))

                state = State(abbreviation = tokens[0])
                division = Division(id = tokens[1], name = tokens[2])
                party = Party(abbreviation = tokens[3], name = tokens[4])
                candidate = Candidate(
                    id = tokens[5],
                    surname = tokens[6],
                    givenName = tokens[7],
                    elected = tokens[8],
                    electedHistoric = tokens[9],
                    party = party,
                    division = division,
                    state = state
                )


                if not candidates.has(candidate.id):
                    candidates.set(candidate.id, candidate)

                if not parties.has(party.abbreviation):
                    parties.set(party.abbreviation, party)
                
                if not states.has(state.abbreviation):
                    states.set(state.abbreviation, state)

                if not divisions.has(division.id):
                    divisions.set(division.id, division)

        for prefsFilePath in prefsFilePaths:
            with open(prefsFilePath, "r") as f:
                lines = f.readlines()[2:]

                for line in lines:
                    clean = line.replace("\n", "")
                    tokens = numpy.array(re.split(csvRegex, clean))
                    divisionID = tokens[1]
                    partyAb = tokens[11]
                    partyName = tokens[12]
                    candidateVotes = tokens[13]

                    if parties.has(partyAb):
                        party = parties.get(partyAb)
                    else:
                        party = Party(abbreviation = partyAb, name = partyName)
                        parties.set(party.abbreviation, party)

                    votes = 0
                    
                    if party.votes.has(divisionID):
                        # A party can have votes from multiple divisions.
                        votes = party.votes.get(divisionID) + int(candidateVotes)
                    else:
                        votes = int(candidateVotes)

                    party.votes.set(divisionID, votes)

        graph = Graph()

        for distanceFilePath in distanceFilePaths:
            with open(distanceFilePath, "r") as f:
                lines = f.readlines()[1:]

                for line in lines:
                    clean = line.replace("\n", "")
                    tokens = numpy.array(re.split(csvRegex, clean))
                    # Division names.
                    division1 = tokens[1]
                    division2 = tokens[5]
                    time = tokens[9]

                    if ":" in time:
                        time = timestampToMinutes(time)
                    else:
                        time = float(time)

                    if not graph.hasVertex(division1):
                        graph.addVertex(division1)

                    if not graph.hasVertex(division2):
                        graph.addVertex(division2)

                    graph.addEdge(division1, division2, time)

        return Election(
            candidates = candidates,
            parties = parties,
            states = states,
            divisions = divisions,
            graph = graph
        )
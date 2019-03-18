from structures.HashTable import HashTable

class Party:
    """Represents a political party in an election.

    Args:
        kwargs (**object): Keyword arguments for each attribute.

    Attributes:
        name (str): The name of the party.
        abbreviation (str): The abbreviated name of the party.
        votes (HashTable<str, int>): The votes this party has accrued in each division.
    """
    def __init__(self, **kwargs):
        self.name = kwargs["name"]
        self.abbreviation = kwargs["abbreviation"]
        self.votes = HashTable(100)
class State:
    """Represents a state in a country/election.

    Args:
        kwargs (**object): Keyword arguments for each attribute.

    Attributes:
        abbreviation (str): The abbreviation of the states name.
    """
    def __init__(self, **kwargs):
        self.abbreviation = kwargs["abbreviation"]
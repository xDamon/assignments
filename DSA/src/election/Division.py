class Division:
    """Represents a seat/division in an election.

    Args:
        kwargs (**object): Keyword arguments for each attribute.

    Attributes:
        id (str): The id of the division.
        name (str): The name of the division.
    """
    def __init__(self, **kwargs):
        self.id = kwargs["id"]
        self.name = kwargs["name"]

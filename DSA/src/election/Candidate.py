class Candidate:
    """Represents a nominee/candidate in an election.

    Args:
        kwargs (**object): Keyword arguments for each attribute.

    Attributes:
        id (str): The id of the candidate.
        givenName (str): The first name of the candidate.
        surname (str): The last name of the candidate.
        elected (str): Whether or not the candidate was elected.
        electedHistoric (str): Whether or not the candidate has been elected previously.
        party (Party): The political party the candidate belongs to. 
        division (Division): The seat the candidate is running for.
        state (State): The state the candidate is located in.
    """
    def __init__(self, **kwargs):
        self.id = kwargs["id"]
        self.givenName = kwargs["givenName"]
        self.surname = kwargs["surname"]
        self.elected = kwargs["elected"]
        self.electedHistoric = kwargs["electedHistoric"]
        self.party = kwargs["party"]
        self.division = kwargs["division"]
        self.state = kwargs["state"]

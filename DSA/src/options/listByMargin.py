from util.Report import Report
from structures.ArrayList import ArrayList

def getMarginalSeats(election):
    """Gets marginal seats based on user input.

    Args:
        election (Election): The election to calculate seats from.

    Returns:
        An ArrayList of marginal seats.
    """
    seats = ArrayList()
    partyAb = input("Enter a party abbreviation: ")
    threshold = input("Enter a custom margin threshold percentage: ")
    
    try:
        threshold = float(threshold)
    except ValueError:
        threshold = 6.0
        print("Using default threshold of 6%")

    if election.parties.has(partyAb):
        party = election.parties.get(partyAb)
        # Parties except informal/invalid votes
        otherParties = election.parties.filter(lambda p: p.abbreviation != partyAb and p.name.upper() != "INFORMAL")

        for divisionID, division in election.divisions:
            votesFor = 0
            votesAgainst = 0

            if party.votes.has(divisionID):
                votesFor = party.votes.get(divisionID)
                
                for opposition in otherParties.values():
                    if opposition.votes.has(divisionID):
                        votesAgainst += opposition.votes.get(divisionID)

                margin = (votesFor / (votesFor + votesAgainst)) * 100 - 50
                marginal = margin > -threshold and margin < threshold

                if marginal:
                    seats.append(division)

    return seats

def listByMargin(election):
    """Displays a report of marginal seats

    Args:
        election (Election): The election data to use in calculations.
    """
    form = lambda d: f"{d.id} {d.name}"
    seats = getMarginalSeats(election)
    report = Report("Marginal Seats", seats, form)
    report.display()

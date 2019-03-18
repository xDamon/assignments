def filterStates(report):
    """Adds a state filter to the report.

    Args:
        report (Report): The report to add the filter to.
    """
    state = input("Enter a state abbreviation: ")
    report.addFilter(lambda c: c.state.abbreviation == state)

def filterParties(report):
    """Adds a party filter to the report.

    Args:
        report (Report): The report to add the filter to.
    """
    party = input("Enter a party abbreviation: ")
    report.addFilter(lambda c: c.party.abbreviation == party)

def filterDivisions(report):
    """Adds a division filter to the report.

    Args:
        report (Report): The report to add the filter to.
    """
    division = input("Enter a division name: ")
    report.addFilter(lambda c: c.division.name == division)
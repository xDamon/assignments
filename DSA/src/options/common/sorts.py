def sortBySurname(report):
    """Adds a surname mapping to the report.

    Args:
        report (Report): The report to add the mapping to.
    """
    report.addMapping(lambda c: c.surname)

def sortByState(report):
    """Adds a state mapping to the report.

    Args:
        report (Report): The report to add the mapping to.
    """
    report.addMapping(lambda c: c.state.abbreviation)

def sortByParty(report):
    """Adds a party mapping to the report.

    Args:
        report (Report): The report to add the mapping to.
    """
    report.addMapping(lambda c: c.party.abbreviation)

def sortByDivision(report):
    """Adds a division mapping to the report.

    Args:
        report (Report): The report to add the mapping to.
    """
    report.addMapping(lambda c: c.division.name)
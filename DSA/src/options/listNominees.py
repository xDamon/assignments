from util.Menu import Menu
from util.Report import Report
from options.common.filters import filterStates, filterParties, filterDivisions
from options.common.sorts import sortBySurname, sortByState, sortByParty, sortByDivision

def showNominees(report):
    """Displays a report of nominees.

    Args:
        report (Report): The report to show.
    """

    report.display()

def filterNominees(report):
    """Displays a menu with filtering options.

    Args:
        report (Report): The report to add filters to.
    """

    menu = Menu("Filter Nominees By")
    menu.addOption("State", filterStates, report)
    menu.addOption("Party", filterParties, report)
    menu.addOption("Division", filterDivisions, report)
    menu.display()

def sortNominees(report):
    """Displays a menu with sorting options.

    Args:
        report (Report): The report to add mappings to.
    """

    menu = Menu("Sort Nominees By")
    menu.addOption("Surname", sortBySurname, report)
    menu.addOption("State", sortByState, report)
    menu.addOption("Party", sortByParty, report)
    menu.addOption("Division", sortByDivision, report)
    menu.display()

def listNominees(election):
    """Displays a menu with options to manipulate a report.

    Args:
        election (Election): The election data to use.
    """
    nominees = election.candidates.values()
    form = lambda c: (
        f"NAME: {c.givenName} {c.surname}, PARTY: {c.party.abbreviation}, "
        f"DIVISION: {c.division.name}, STATE: {c.state.abbreviation}"
    )

    report = Report("Nominees", nominees, form)

    menu = Menu("List Nominees")
    menu.addOption("Show", showNominees, report)
    menu.addOption("Filter", filterNominees, report)
    menu.addOption("Sort", sortNominees, report)
    menu.display()

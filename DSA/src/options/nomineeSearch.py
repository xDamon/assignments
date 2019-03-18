from util.Menu import Menu
from util.Report import Report
from options.common.filters import filterStates, filterParties

def searchNominees(report):
    """Searches for nominees by surname and displays them.

    Args:
        report (Report): The report to display.
    """
    surname = input("Enter a candidate surname: ").lower()
    report.addFilter(lambda c: surname in c.surname.lower())
    report.display()
    report.removeFilter()

def filterNominees(report):
    """Filters nominee data in a report.

    Args:
        report (Report): The report to add filters to.
    """
    menu = Menu("Filter Nominees By")
    menu.addOption("State", filterStates, report)
    menu.addOption("Party", filterParties, report)
    menu.display()

def nomineeSearch(election):
    """Creates a report and allows the user to manipulate it.

    Args:
        election (Election): The election data to use.
    """
    nominees = election.candidates.values()
    form = lambda c: (
        f"ID: {c.id}\n"
        f"GIVEN NAME: {c.givenName}\n"
        f"SURNAME: {c.surname}\n"
        f"PARTY: {c.party.name}\n"
        f"DIVISION: {c.division.name}\n"
        f"STATE: {c.state.abbreviation}\n"
        f"ELECTED: {c.elected}\n"
        f"ELECTED PREVIOUSLY: {c.electedHistoric}\n"
    )

    report = Report("Nominee Search", nominees, form)

    menu = Menu("Nominee Search")
    menu.addOption("Search", searchNominees, report)
    menu.addOption("Filter", filterNominees, report)
    menu.display()
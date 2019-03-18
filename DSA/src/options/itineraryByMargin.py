import numpy

from functools import partial

from util.Report import Report
from util.functions.time import minutesToTimestamp, timestampToMinutes

from options.listByMargin import getMarginalSeats

from structures.Graph import Graph
from structures.ArrayList import ArrayList, ArrayListError

def form(time, c):
    """Formatting function for report.

    It uses an array to keep reference to the value
    as Python doesn't consider assignment to be an expression.
    Therefore this awkward function is required.

    Args:
        time (Array<float>): Current time in minutes.

    Returns:
        Report data string. 

    """
    leave = time[0]
    after = time[0] + c[2]

    string = "%12s %25s %2s %-25s %-12s" % (
        minutesToTimestamp(leave),
        c[0],
        "=>",
        c[1],
        minutesToTimestamp(after)
    )
    
    time[0] = after + 180

    return string

def itineraryByMargin(election):
    """Creates an itinerary based on marginal seats

    Note:
        Please select a small margin, the approach I have done
        for this finds the most optimal path by brute force. The
        time complexity for getting the distances is O(n^3) and the
        time complexity for the brute force part is O(n!). A large
        amount of marginal seats is going to result in a VERY long
        wait. 

    Args:
        election (Election): The election data to use.
    """
    seats = getMarginalSeats(election)
    names = ArrayList(map(lambda s: s.name, seats))

    output = "======= Seats =======\n"

    for name in names:
        output += f"{name}\n"

    print(output)

    start = None
    end = None

    # Need valid names to start and end at.
    while start not in names:
        start = input("Enter a valid seat name to start at: ")

    while end not in names:
        end = input("Enter a valid seat name to end at: ")

    time = None

    while type(time) is not float:
        time = input("Enter the start time: ")
        try:
            time = timestampToMinutes(time)
        except ValueError:
            print("Error: Invalid time format, expected HH:MM:SS")

    names = names.filter(lambda n: n != start and n != end)
    connections = election.graph.brutePathThrough(start, names, end, True)
    formPartial = partial(form, ArrayList([time]))

    report = Report(
        "Itinerary By Margin",
        connections,
        formPartial
    )
    report.display()


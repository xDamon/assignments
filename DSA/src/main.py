import os
import sys
import numpy

from util.Menu import Menu

from options.listNominees import listNominees
from options.nomineeSearch import nomineeSearch
from options.listByMargin import listByMargin
from options.itineraryByMargin import itineraryByMargin

from election.Election import Election

dirname = os.path.dirname(__file__)
dataDir = os.path.join(dirname, "..", "data")

candidatesDir = os.path.join(dataDir, "candidates")
prefsDir = os.path.join(dataDir, "preferences")
distanceDir = os.path.join(dataDir, "distances")

# Read all files in the directories in.
candidatesFilename = numpy.array(os.listdir(candidatesDir))[0]
prefsFilenames = numpy.array(os.listdir(prefsDir))
distanceFilenames = numpy.array(os.listdir(distanceDir))

# Join directory names to the filenames.
candidatesFilePath = os.path.join(candidatesDir, candidatesFilename)
prefsFilePaths = numpy.array(list(map(lambda f: os.path.join(prefsDir, f), prefsFilenames)))
distanceFilePaths = numpy.array(list(map(lambda f: os.path.join(distanceDir, f), distanceFilenames)))

try:
    election = Election.fromFiles(candidatesFilePath, prefsFilePaths, distanceFilePaths)
except EnvironmentError:
    print("Error: Cannot load input files")
except IndexError:
    print("Error: Cannot load incorrectly formatted input file.")
else:
    menu = Menu("Main Menu")
    menu.addOption("List Nominees", listNominees, election)
    menu.addOption("Nominee Search", nomineeSearch, election)
    menu.addOption("List By Margin", listByMargin, election)
    menu.addOption("Itinerary By Margin", itineraryByMargin, election)
    menu.display()

from functools import partial

from structures.ArrayList import ArrayList

from util.functions.comparator import comparator
from util.functions.sort import quickSort

class Report:
    """A text-based report for display data.

    This class handles the sorting and filtering of
    a dataset, as well as displaying it to the user.

    Args:
        title (str): The title of the report.
        items (iterable): The dataset for the report.
        form (function): Formatting function for the data.

    Attributes:
        title (str): The title of the report.
        items (ArrayList<object>): The dataset for the report.
        format (function): Formatting function for the data.
        filters (ArrayList<function>): The filters to apply to the dataset.
        mappings (ArrayList<function>): The attribute getters to sort with.
    """
    def __init__(self, title, items, form = lambda v: v):
        self.title = title
        self.items = ArrayList(items)
        self.format = form
        self.filters = ArrayList()
        self.mappings = ArrayList()

    def __str__(self):
        items = self.items
        report = f"========= {self.title} =========\n"

        for fn in self.filters:
            items = items.filter(fn)

        if len(self.mappings) > 0:
            quickSort(items, partial(comparator, self.mappings))

        for item in items:
            report += f"{self.format(item)}\n"

        return report

    def addFilter(self, fn):
        """Adds a filter to the dataset.

        Args:
            fn (function): The filter function.
        """
        self.filters.append(fn)
    
    def addMapping(self, fn):
        """Adds a attribute getter to the dataset.

        Args:
            fn (function): The attribute getting function.
        """
        self.mappings.append(fn)

    def removeFilter(self):
        """Removes the last filter from the dataset."""
        self.filters.pop()
    
    def removeMapping(self):
        """Removes the last attribute getter from the dataset."""
        self.mappings.pop()
    
    def display(self):
        """Displays the report data, giving an option to save it"""
        report = str(self)

        print(report)

        choice = ""

        while not "y" in choice and not "n" in choice:
            choice = input("Would you like to save this report? (y/n): ")

        if "y" in choice:
            filename = input("Enter a filename: ")

            try:
                with open(filename, "w") as f:
                    f.write(report)
            except EnvironmentError:
                print("Error: Could not save report")
            else:
                print("Successfully saved report")
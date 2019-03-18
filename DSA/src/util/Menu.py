"""Menu System

This module includes functionality
for creating a text-based menu that is
displayed in a terminal.
"""

from functools import partial
from structures.ArrayList import ArrayList

class MenuError(Exception):
    """An error to be thrown during menu operations.

    This exception is thrown whenever an error occurs
    performing an operation with the Menu class. Generally
    it is just a previous exception that has been elevated
    up to a MenuError.
    """
    pass

class MenuOption:
    """A single option in a text-based menu.

    Allows the Menu to perform a specific action
    when a named option is chosen by the user.

    Args:
        name (str): The option name to be displayed in the menu.
        action (function): The function to run when this option is selected.

    Attributes:
        name (str): The option name to be displayed in the menu.
        action (function): The function to run when this option is selected.
    """
    def __init__(self, name, action):
        self.name = name
        self.action = action

class Menu:
    """A text-based menu for the terminal.

    This class handles input and output for a text-based
    menu in the terminal, allowing options to be added and
    functionality to be called via user input using numbers.

    Args:
        name (str): The name of the menu that is displayed to the user.

    Attributes:
        name (str): The name of the menu that is displayed to the user.
        options (ArrayList<function>): The options available to the user.
    """
    def __init__(self, name):
        self.name = name
        self.options = ArrayList()

    def __str__(self):
        menu = f"\n===== {self.name} =====\n"
        count = 1

        for option in self.options:
            menu += f"[{count}] {option.name}\n"
            count += 1

        # Every menu has a quit option.
        menu += "[0] Quit\n"

        return menu

    def addOption(self, name, action, *args):
        """Adds an option to the menu.

        Args:
            name (str): The name of the option.
            action (function): The function to run when selected.
            args (*object): Arguments to provide to option function calls.
        """
        # Partials allow arguments to be binded to
        # the functions before the functions are called.
        self.options.append(MenuOption(name, partial(action, *args)))

    def selectOption(self, index):
        """Selects an option by its number and calls it.

        Args:
            index (int): The option number to choose.

        Raises:
            MenuError: If the option doesn't exist.
        """
        optionIndex = index - 1

        if index == 0:
            print("Quiting...")
        elif optionIndex >= 0 and optionIndex < len(self.options):
            self.options[optionIndex].action()
        else:
            raise MenuError(f"{index} is not a valid menu option")

    def display(self):
        """Displays a text based menu and prompts the user.

        The menu will keep appearing until the user forcibly
        quits the program or selects the 'Quit' option.
        """
        selection = None
        output = f"{self}\nEnter a menu option: "

        # 0 is the Quit option.
        while selection != 0:
            selection = input(output)

            try:
                selection = int(selection)
            except ValueError:
                print("Error: Input must be an integer")
            else:
                try:
                    self.selectOption(selection)
                except MenuError:
                    print("Error: Input must be a valid menu option")

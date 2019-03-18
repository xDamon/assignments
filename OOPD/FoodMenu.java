import java.io.IOException;

/**
 * Provides a text UI with multiple
 * options that the user can choose, 
 * each performing an action relating to
 * the storage / managing of food.
 * 
 * @author Damon Ezard
 * @version 1.1.6
 * @since 30/05/2018
 */
public class FoodMenu
{
    /**
     * Displays the menu and calls
     * the appopriate method based on
     * user input.
     * 
     * @param args Not used.
     */
    public static void run()
    {
        // All options require the same storage
        // object to function, so the main reference
        // to it is kept here.
        Storage storage = null;
        int selection = -1;
        String[] options =
        {
            "Add Food",
            "Remove Food",
            "Display Contents",
            "Find Expired",
            "Read In Storage",
            "Write Out Storage",
            "Exit"
        };

        String menu = UserIO.createMenu("Food Menu", options);

        do
        {
            System.out.println(menu);

            selection = UserIO.getInteger("Enter a menu option: ");

            // all options rely on the storage being read
            // in already, so if the input is not 5 the
            // storage will be checked.
            if (selection == options.length)
            {
                System.out.println("Exiting...");
            }
            else if (selection != 5)
            {
                if (storage == null)
                {
                    System.out.println(
                        "Error: Storage has not been initialised, " +
                        "please read in storage"
                    );
                }
                else
                {
                    switch (selection)
                    {
                        // Since each case was just calling a method
                        // I put them on one line each because it seems
                        // more readable.
                        case 1: addFood(storage); break;
                        case 2: removeFood(storage); break;
                        case 3: displayContents(storage); break;
                        case 4: findExpired(storage); break;
                        case 6: writeOutStorage(storage); break;
                        default: System.out.println("Error: Invalid menu option");
                    }
                }
            }
            else if (storage == null)
            {
                storage = readInStorage();
            }
            else
            {
                System.out.println("Error: Storage cannot be constructed again");
            }
            
        }
        while (selection != options.length);

        UserIO.close();
    }

    /**
     * Adds a new food from user input to
     * the storage system.
     * 
     * @param storage The storage instance.
     */
    public static void addFood(Storage storage)
    {
        try
        {
            storage.store(FoodFactory.createFromUser());
            System.out.println("\nSuccessfully added food\n");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Removes a food from the storage
     * system at a specified area and location.
     * 
     * @param storage The storage instance.
     */
    public static void removeFood(Storage storage)
    {
        int area = -1;
        int location = -1;
        String[] options =
        {
            "Freezer",
            "Fridge",
            "Pantry"
        };
        String menu = UserIO.createMenu("Area Menu", options);

        System.out.println(menu);

        area = UserIO.getInteger("Enter a storage area: ") - 1;
        location = UserIO.getInteger("Enter a storage location: ") - 1;

        try
        {
            storage.remove(area, location);
            System.out.println("\nSuccessfully removed food\n");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Displays the contents of a
     * particular storage area.
     * 
     * @param storage The storage instance.
     */
    public static void displayContents(Storage storage)
    {
        int area = -1;
        String[] options =
        {
            "Freezer",
            "Fridge",
            "Pantry"
        };
        String menu = UserIO.createMenu("Area Menu", options);
        String title = "===== Contents =====\n";

        System.out.println(menu);

        area = UserIO.getInteger("Enter a storage area: ") - 1;

        try
        {
            System.out.println(
                title + storage.getContentsListIn(area)
            );
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Finds expired foods in currently
     * in the storage system.
     * 
     * @param storage The storage instance.
     */
    public static void findExpired(Storage storage)
    {
        String expired = storage.getExpiredList();

        System.out.println("===== Expired =====\n" + expired);
    }

    /**
     * Loads the storage system from
     * a particular file.
     * 
     * @return The constructed storage instance.
     */
    public static Storage readInStorage()
    {
        Storage storage = null;
        String input = UserIO.getString("Enter storage file name: ");

        try
        {
            storage = new Storage(input);
        }
        catch (IOException e)
        {
            System.out.println("Error: " + e.getMessage());
        }

        if (storage != null)
        {
            System.out.println("\nStorage loaded - " + storage.toString() + "\n");
        }

        return storage;
    }

    /**
     * Writes out the storage system
     * to a specified file.
     * 
     * @param storage The storage instance.
     */
    public static void writeOutStorage(Storage storage)
    {
        String fileName = UserIO.getString("Enter an output file name: ");
        String[] contents = storage.getContentsList().split("\\n");
        String[] areas =
        {
            "Freezer, " + storage.getAreaCapicity(Storage.FREEZER_INDEX),
            "Fridge, " + storage.getAreaCapicity(Storage.FRIDGE_INDEX), 
            "Pantry, " + storage.getAreaCapicity(Storage.PANTRY_INDEX)
        };

        // have to combine them because the FileIO.writeNumLines
        // performs a write action, it doesn't append to the file.
        String[] combined = new String[contents.length + areas.length];

        for (int i = 0; i < areas.length; i++)
        {
            combined[i] = areas[i];
        }

        for (int i = 0; i < contents.length; i++)
        {
            // offset for what's already added.
            combined[i + areas.length] = contents[i];
        }
        
        try
        {
            FileIO.writeNumLines(fileName, combined);
            System.out.println("\nSuccessfully wrote to " + fileName + "\n");
        }
        catch (IOException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
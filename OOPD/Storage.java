import java.io.IOException;

/**
 * Stores foods in different locations
 * in different areas base on storage
 * temperatures.
 * 
 * This class is not very extensible due
 * to the task's requirement of using an array
 * with exactly 3 arrays inside of it to
 * represent different storage areas, rather
 * than using a single class of multiple instances
 * tht presented each storage area.
 * 
 * @author Damon Ezard
 * @since 30/05/18
 * @version 1.1.3
 */
public class Storage
{
    // all these constants rely on each other
    // due to the awkward specification.
    public static final int FREEZER_INDEX = 0;
    public static final int FRIDGE_INDEX = 1;
    public static final int PANTRY_INDEX = 2;

    public static final double[][] TEMPERATURE_RANGE =
    {
        { -27.0, -5.0 },
        { -2.0, 6.0 },
        { 8.0, 25.0 }
    };

    public static final String[] AREA_NAMES = { "Freezer" , "Fridge", "Pantry" };

    private Food[][] stored;
    private int[] counts;

    /**
     * Default Constructor.
     */
    public Storage()
    {
        stored = new Food[][]
        {
            new Food[10],
            new Food[10],
            new Food[10]
        };
        counts = new int[] { 0, 0, 0 };
    }

    /**
     * Alternate Constructor.
     * 
     * @param fileName The name of the storage file.
     */
    public Storage(String fileName) throws IOException
    {
        stored = new Food[3][];
        counts = new int[] { 0, 0, 0 };

        String[] lines;
        String[] tokens;
        String type;

        int size;
        int numLines = FileIO.getNumLines(fileName);

        if (numLines >= stored.length)
        {
            lines = FileIO.readNumLines(fileName, numLines);
            
            // first 3 lines to create arrays for each storage area.
            for (int i = 0; i < stored.length; i++)
            {
                // splits by comma and white space if there is any.
                tokens = lines[i].split(",\\s*");
                // make comparisons case insensitive.
                try
                {
                    type = tokens[0].toUpperCase();
                    size = Integer.parseInt(tokens[1].trim());
                }
                catch (NumberFormatException e)
                {
                    throw new IOException(
                        "Invalid storage file format"
                    );
                }
                catch (IndexOutOfBoundsException e)
                {
                    throw new IOException(
                        "Invalid storage file format"
                    );
                }

                if (size < 0)
                {
                    throw new IOException(
                        "Storage file cannot have negative sizes"
                    );
                }

                if (type.equals("FREEZER"))
                {
                    stored[FREEZER_INDEX] = new Food[size];
                }
                else if (type.equals("FRIDGE"))
                {
                    stored[FRIDGE_INDEX] = new Food[size];
                }
                else if (type.equals("PANTRY"))
                {
                    stored[PANTRY_INDEX] = new Food[size];
                }
            }

            // will pick up if 1 of the first 3 lines was invalid.
            for (int i = 0; i < stored.length; i++)
            {
                if (stored[i] == null)
                {
                    throw new IOException("Invalid storage file format");
                }
            }

            // start from stored.length (3) so it skips the
            // lines that build the storage array and uses
            // only the lines with food.
            for (int i = stored.length; i < lines.length; i++)
            {
                try
                {
                    store(FoodFactory.createFromLine(lines[i]));
                }
                catch (IllegalArgumentException e)
                {
                    System.out.println(
                        "Skipping line " + (i + 1) + 
                        " - Invalid value (" + e.getMessage() + ")"
                    );
                }
                catch (IndexOutOfBoundsException e)
                {
                    System.out.println(
                        "Skipping line " + (i + 1) +
                        " - Did not have enough info"
                    );
                }
            }
        }
        else
        {
            throw new IOException("Invalid storage file format");
        }
    }

    /**
     * Copy Constructor.
     * 
     * @param inStorage The storage object to be copied.
     */
    public Storage(Storage inStorage)
    {
        stored = inStorage.getStored();
        counts = inStorage.getCounts();
    }

    /**
     * @return The 2D array of storage areas and locations.
     */
    public Food[][] getStored()
    {
        return copyStored(stored);
    }

    /**
     * @return The array of counts for each storage area.
     */
    public int[] getCounts()
    {
        return copyCounts(counts);
    }

    /**
     * Gives the size of a particular
     * storage area.
     * 
     * @param area The storage area.
     * @return The size of the storage area.
     */
    public int getAreaCapicity(int area)
    {
        if (!validStorageAreaIndex(area))
        {
            throw new IllegalArgumentException("Invalid storage area");
        }

        return stored[area].length;
    }

    /**
     * Creates a string of all foods
     * that are expired, showing where they
     * are stored.
     * 
     * @return String of expired foods separated by "\n".
     */
    public String getExpiredList()
    {
        String list = "";
        SimpleGregorianCalendar today = new SimpleGregorianCalendar();

        for (int i = 0; i < stored.length; i++)
        {
            int j = 0;
            // all food objects are moved towards the start
            // of the array so once first null is found no other
            // foods should be after.
            while (stored[i][j] != null && j < stored[i].length)
            {
                Food food = stored[i][j];
                // IFood had to be implemented by
                // subclasses of Food so food has no
                // knowledge of the method so It has to
                // be typecasted.
                if (food instanceof IFood && ((IFood) food).calcExpiry(today))
                {
                    list += "Expired Food (" + food.getEndDate().toString() +
                        ") in " + AREA_NAMES[i] + " at location " + (j + 1) + "\n";
                }

                j++;
            }
        }

        return list;
    }

    /**
     * Creates a string of all the foods inside
     * every storage area.
     * 
     * @return String of foods separated by "\n".
     */
    public String getContentsList()
    {
        String freezer = getContentsListIn(FREEZER_INDEX);
        String fridge = getContentsListIn(FRIDGE_INDEX);
        String pantry = getContentsListIn(PANTRY_INDEX);

        return freezer + fridge + pantry;
    }

    /**
     * Creates a string of all the foods inside
     * a specified storage area.
     * 
     * @param area The storage area.
     * @return The contents insize that storage area.
     */
    public String getContentsListIn(int area)
    {
        if (!validStorageAreaIndex(area))
        {
            throw new IllegalArgumentException("Invalid storage area");
        }

        int index = 0;
        String list = "";
        Food[] storageArea = stored[area];

        // exit if value is null because every value
        // after will be null as well.
        while (index < storageArea.length && storageArea[index] != null)
        {
            list += storageArea[index++].toString() + "\n";
        }

        return list;
    }

    /**
     * Stores food based on its
     * temperature and whether the storage areas
     * are full or not.
     * 
     * @param food The food object to store.
     */
    public void store(Food food)
    {
        boolean isStored = false;

        double temp = food.getStorageTemp();
        double[] range;

        int index = 0;

        do
        {
            range = TEMPERATURE_RANGE[index];

            if (temp > range[0] && temp < range[1])
            {
                if (counts[index] < stored[index].length)
                {
                    stored[index][counts[index]++] = food;
                    isStored = true;
                }
                else
                {
                    // no temperatures overlap so if the
                    // storage area is full the food cannot
                    // be added and is therefore invalid.
                    throw new IllegalArgumentException(
                        "Cannot store food, " + AREA_NAMES[index] + " is full"
                    );
                }
            }
            
            index++;
        }
        while (!isStored && index < stored.length);

        if (!isStored)
        {
            throw new IllegalArgumentException(
                "Food with temperature of " + temp + "C cannot be stored"
            );
        }
    }

    /**
     * Removes a food from a particular area and
     * storage location, moving the rest of the
     * items to fill the gap.
     * 
     * @param area The food's storage area index.
     * @param location The food's index in the storage area.
     */
    public void remove(int area, int location)
    {
        if (!validStorageAreaIndex(area))
        {
            throw new IllegalArgumentException("Invalid storage area");
        }
        else if (location < 0 || location >= stored[area].length)
        {
            throw new IllegalArgumentException("Invalid storage location");
        }
        else if (stored[area][location] == null)
        {
            throw new IllegalArgumentException(
                "Food doesn't exist at that location"
            );
        }

        int index = location;
        Food[] storageArea = stored[area];
        // remove the food
        storageArea[index] = null;
        counts[area]--;

        while (index < storageArea.length - 1 && storageArea[index + 1] != null)
        {
            // shuffle the values down the array
            storageArea[index] = storageArea[index + 1];
            storageArea[index + 1] = null;
            index++;
        }
    }

    /**
     * Copies the current instance.
     * 
     * @return The storage copy.
     */
    public Storage clone()
    {
        return new Storage(this);
    }

    /**
     * Checks the equality of the current
     * instance and the imported object.
     * 
     * @param inObject The object to compare to.
     * @return Whether or not they are equal.
     */
    @Override
    public boolean equals(Object inObject)
    {
        Food food;
        int[] inCounts;
        Food[][] inStored;
        Storage inStorage;
        boolean isEqual = false;

        if (inObject instanceof Storage)
        {
            inStorage = (Storage) inObject;
            inCounts = inStorage.getCounts();
            isEqual = 
                counts[FREEZER_INDEX] == inCounts[FREEZER_INDEX] &&
                counts[FRIDGE_INDEX] == inCounts[FRIDGE_INDEX] &&
                counts[PANTRY_INDEX] == inCounts[PANTRY_INDEX];

            // if counts are equal then check
            // the arrays fully
            if (isEqual)
            {
                inStored = inStorage.getStored();
                isEqual = stored.length == inStored.length;
                if (isEqual)
                {
                    for (int i = 0; i < stored.length; i++)
                    {
                        int j = 0;
                        isEqual = stored[i].length == inStored[i].length;
                        while (isEqual && j < stored[i].length)
                        {
                            food = stored[i][j];
                            if (food == null)
                            {
                                isEqual = food == inStored[i][j];
                            }
                            else
                            {
                                isEqual = food.equals(inStored[i][j]);
                            }
                            j++;
                        }
                    }
                }
            }
        }
        
        return isEqual;
    }

    /**
     * Converts the storage to a readable string
     * that mentions how much storage is being used
     * for each area.
     * 
     * @return The string representation of this storage.
     */
    @Override
    public String toString()
    {
        return
            "Freezer: " + counts[FREEZER_INDEX] + 
            "/" + stored[FREEZER_INDEX].length +
            ", Fridge: " + counts[FRIDGE_INDEX] +
            "/" + stored[FRIDGE_INDEX].length +
            ", Pantry: " + counts[PANTRY_INDEX] +
            "/" + stored[PANTRY_INDEX].length;
    }

    /**
     * Determines whether the 
     * storage area is valid i.e
     * it is one of the 3 available.
     * 
     * @param area The storage area index to check
     * @return If the index is valid or not.
     */
    private boolean validStorageAreaIndex(int area)
    {
        // relying on constants.
        return area == FREEZER_INDEX ||
               area == FRIDGE_INDEX ||
               area == PANTRY_INDEX;
    }

    /**
     * Copies the array of counts for each
     * storage storageArea.
     * 
     * @param inCounts The counts to copy.
     * @return The copied counts.
     */
    private int[] copyCounts(int[] inCounts)
    {
        int[] copy = new int[inCounts.length];

        for (int i = 0; i < inCounts.length; i++)
        {
            copy[i] = inCounts[i];
        }

        return copy;
    }

    /**
     * Copies a 2D array of foods.
     * 
     * @param inStored The 2D array to copy.
     * @return The copied 2D array.
     */
    private Food[][] copyStored(Food[][] inStored)
    {
        Food[][] copy = new Food[inStored.length][];

        for (int i = 0; i < copy.length; i++)
        {
            copy[i] = new Food[inStored[i].length];

            for (int j = 0; j < copy[i].length; j++)
            {
                Food food = inStored[i][j];

                if (food == null)
                {
                    copy[i][j] = food;
                }
                else
                {
                    copy[i][j] = food.clone();
                }
            }
        }

        return copy;
    }
}
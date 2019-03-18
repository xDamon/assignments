/**
 * Creates Food instances from file
 * input and user input.
 * 
 * This is not an implementation of any
 * of any of those common design patterns,
 * I just wanted a class that created food 
 * 
 * @author Damon Ezard
 * @version 1.0.6
 * @since 30/05/18
 */
public class FoodFactory
{
    /**
     * Creates a food object based
     * on a string read in from a storage
     * file.
     * 
     * Format must fit one of the following:
     * 
     * Meat, [name], [cut], [weight], [storageTemp], [endDate], [packaging]
     * Vegetable, [name], [weight], [storageTemp], [endDate], [packaging]
     * Fruit, [name], [type], [numberOfPieces], [storageTemp], [endDate], [packaging]
     * Grain, [name], [type], [volume], [storageTemp], [endDate], [packaging]
     *
     * @param line The line to create the food from.
     * @return The newly created food.
     */
    public static Food createFromLine(String line)
    {
        Food food = null;

        // non-guarenteed fields
        String type;
        String cut;
        double weight;
        double volume;
        int numOfPieces;

        // common fields
        String[] tokens;
        String group;
        String name;
        String packaging;
        double temp;
        SimpleGregorianCalendar endDate;

        tokens = line.split(",\\s*");
        group = tokens[0].toUpperCase();
        name = tokens[1];
        packaging = tokens[tokens.length - 1];
        temp = Double.parseDouble(tokens[tokens.length - 3]);
        endDate = new SimpleGregorianCalendar(
            tokens[tokens.length - 2]
        );

        if (group.equals("MEAT"))
        {
            cut = tokens[2];
            weight = Double.parseDouble(tokens[3]);
            food = new Meat(name, packaging, temp, endDate, cut, weight);
        }
        else if (group.equals("VEGETABLE"))
        {
            weight = Double.parseDouble(tokens[2]);
            food = new Vegetable(name, packaging, temp, endDate, weight);
        }
        else if (group.equals("FRUIT"))
        {
            type = tokens[2];
            numOfPieces = Integer.parseInt(tokens[3]);
            food = new Fruit(name, packaging, temp, endDate, type, numOfPieces);
        }
        else if (group.equals("GRAIN"))
        {
            type = tokens[2];
            volume = Double.parseDouble(tokens[3]);
            food = new Grain(name, packaging, temp, endDate, type, volume);
        }
        else
        {
            throw new IllegalArgumentException(
                "Invalid line, group name incorrect"
            );
        }

        return food;
    }

    public static Food createFromUser()
    {
        System.out.println("Please enter the following: ");

        Food food = null;

        // non-guarenteed fields
        String type;
        String cut;
        double weight;
        double volume;
        int numOfPieces;

        // common fields
        String group = UserIO.getString("Food group: ").toUpperCase();
        String name = UserIO.getString("Name: ");
        String packaging = UserIO.getString("Packaging: ");
        double temp = UserIO.getRangedReal(
            "Storage temperature: ",
            Food.MIN_STORAGE_TEMP,
            Food.MAX_STORAGE_TEMP
        );
        SimpleGregorianCalendar endDate = UserIO.getDate(
            "Best before / use-by date: "
        );

        if (group.equals("MEAT"))
        {
            cut = UserIO.getString("Cut: ");
            weight = UserIO.getRangedReal(
                "Weight: ",
                Meat.MIN_WEIGHT,
                Meat.MAX_WEIGHT
            );
            food = new Meat(name, packaging, temp, endDate, cut, weight);
        }
        else if (group.equals("VEGETABLE"))
        {
            weight = UserIO.getRangedReal(
                "Weight: ",
                Vegetable.MIN_WEIGHT,
                Vegetable.MAX_WEIGHT
            );
            food = new Vegetable(name, packaging, temp, endDate, weight);
        }
        else if (group.equals("FRUIT"))
        {
            type = UserIO.getString("Type: ");
            numOfPieces = UserIO.getRangedInteger(
                "Number of pieces: ",
                Fruit.MIN_NUMBER_OF_PIECES,
                Fruit.MAX_NUMBER_OF_PIECES
            );
            food = new Fruit(name, packaging, temp, endDate, type, numOfPieces);
        }
        else if (group.equals("GRAIN"))
        {
            type = UserIO.getString("Type: ");
            volume = UserIO.getRangedReal(
                "Volume: ",
                Grain.MIN_VOLUME,
                Grain.MAX_VOLUME
            );
            food = new Grain(name, packaging, temp, endDate, type, volume);
        }
        else
        {
            throw new IllegalArgumentException("Invalid food group");
        }

        return food;
    }
}
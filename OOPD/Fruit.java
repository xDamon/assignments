import java.util.*;

/**
 * Class representing all foods
 * that are considered fruits.
 * 
 * @author Damon Ezard
 * @version 1.0.4
 * @since 30/05/2018
 */
public class Fruit extends Food implements IFood
{
    public static final int MAX_NUMBER_OF_PIECES = 20;
    public static final int MIN_NUMBER_OF_PIECES = 1;

    private String type;
    private int numberOfPieces;

    /**
     * Default Constructor.
     */
    public Fruit()
    {
        super();

        type = "Default Type";
        numberOfPieces = 10;
    }

    /**
     * Alternate Constructor.
     * 
     * @param inName The name of the fruit.
     * @param inPackaging The type of packaging for the fruit.
     * @param inStorageTemp The temperature the fruit should be stored at.
     * @param inEndDate The end date when the fruit's condition changes.
     * @param inType The type of fruit it is.
     * @param inNumberOfPieces The number of pieces of the fruit.
     */
    public Fruit(
        String inName,
        String inPackaging,
        double inStorageTemp,
        SimpleGregorianCalendar inEndDate,
        String inType,
        int inNumberOfPieces)
    {
        super(inName, inPackaging, inStorageTemp, inEndDate);

        setType(inType);
        setNumberOfPieces(inNumberOfPieces);
    }

    /**
     * Copy Constructor.
     */
    public Fruit(Fruit inFruit)
    {
        super(inFruit);

        type = inFruit.getType();
        numberOfPieces = inFruit.getNumberOfPieces();
    }

    /**
     * @return The type of Fruit.
     */
    public String getType()
    {
        return type;
    }

    /**
     * @return The number of pieces of fruit.
     */
    public int getNumberOfPieces()
    {
        return numberOfPieces;
    }

    /**
     * @param inType The new type of fruit.
     */
    public void setType(String inType)
    {
        if (inType == null)
        {
            throw new IllegalArgumentException("Type cannot be null");
        }
        else if (inType.trim().length() == 0)
        {
            throw new IllegalArgumentException("Type cannot be empty");
        }

        type = inType;
    }

    /**
     * @param inNumberOfPieces The new number of pieces of fruit.
     */
    public void setNumberOfPieces(int inNumberOfPieces)
    {
        if (inNumberOfPieces < MIN_NUMBER_OF_PIECES || 
            inNumberOfPieces > MAX_NUMBER_OF_PIECES)
        {
            throw new IllegalArgumentException(
                "Number of pieces cannot must be between 1 and 20"
            );
        }

        numberOfPieces = inNumberOfPieces;
    }

    /**
     * @see IFood#calcExpiry()
     */
    @Override
    public boolean calcExpiry(Calendar today) {
        return !(getEndDate().after(today));
    }

    /**
     * @see IFood#calcSpace(Food)
     */
    @Override
    public int calcSpace(Food food) {
        int volume = -1;
        // would normally have constants for this kind of
        // thing but I really don't understand what this
        // method is meant to do, am I meant to repeat this
        // code 4 times?
        if (food instanceof Meat)
        {
            volume = (int) Math.ceil(((Meat) food).getWeight() * 0.86);
        }
        else if (food instanceof Vegetable)
        {
            volume = (int) Math.ceil(((Vegetable) food).getWeight() * 1.025);
        }
        else if (food instanceof Grain)
        {
            volume = (int) Math.ceil(((Grain) food).getVolume());
        }
        else if (food instanceof Fruit)
        {
            volume = (int) Math.ceil(((Fruit) food).getNumberOfPieces() * 1.95);
        }

        return volume;
    }

    /**
     * Compares the values of an imported
     * object with the values of the current
     * Fruit object.
     * 
     * @param inObject The object compare with.
     * @return Boolean representing whether or not the objects are equal.
     */
    @Override
    public boolean equals(Object inObject)
    {
        Fruit inFruit = null;
        boolean isEqual = false;

        if (inObject instanceof Fruit)
        {
            inFruit = (Fruit) inObject;
            isEqual = super.equals(inFruit) &&
                type.equals(inFruit.getType()) &&
                numberOfPieces == inFruit.getNumberOfPieces();
        }

        return isEqual;
    }

    /**
     * @see Food#toString()
     */
    @Override
    public String toString()
    {
        return "Fruit, " + getName() + ", " + type + ", " + 
                numberOfPieces + ", " + getStorageTemp() + ", " + 
                getEndDate() + ", " + getPackaging();
    }

    /**
     * @see Food#clone()
     */
    @Override
    public Fruit clone()
    {
        return new Fruit(this);
    }
}

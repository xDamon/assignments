import java.util.*;

/**
 * Class representing all foods
 * that are considered grains.
 * 
 * @author Damon Ezard
 * @version 1.0.2
 * @since 30/05/2018
 */
public class Grain extends Food implements IFood
{
    public static final double MIN_VOLUME = 0.2;
    public static final double MAX_VOLUME = 5.0;

    private String type;
    private double volume;

    /**
     * Default Constructor.
     */
    public Grain()
    {
        super();

        type = "Default Type";
        volume = 10.0;
    }

    /**
     * Alternate Constructor.
     * 
     * @param inName The name of the grain.
     * @param inPackaging The type of packaging for the grain.
     * @param inStorageTemp The temperature the grain should be stored at.
     * @param inEndDate The end date when the grain's condition changes.
     * @param inType The type of grain it is.
     * @param inVolume The volume the grain consumes.
     */
    public Grain(
        String inName,
        String inPackaging,
        double inStorageTemp,
        SimpleGregorianCalendar inEndDate,
        String inType,
        double inVolume)
    {
        super(inName, inPackaging, inStorageTemp, inEndDate);

        setType(inType);
        setVolume(inVolume);
    }

    /**
     * Copy Constructor.
     */
    public Grain(Grain inGrain)
    {
        super(inGrain);

        type = inGrain.getType();
        volume = inGrain.getVolume();
    }

    /**
     * @return The type of grain.
     */
    public String getType()
    {
        return type;
    }

    /**
     * @return The volume the grain consumes.
     */
    public double getVolume()
    {
        return volume;
    }

    /**
     * @param inType The new type of grain.
     */
    public void setType(String inType)
    {
        if (inType == null)
        {
            throw new IllegalArgumentException("Type cannot be null");
        }
        else if (isEmptyField(inType))
        {
            throw new IllegalArgumentException("Type cannot be empty");
        }

        type = inType;
    }

    /**
     * @param inVolume The new volume of the grain.
     */
    public void setVolume(double inVolume)
    {
        if (inVolume <= 0.0)
        {
            throw new IllegalArgumentException("Volume cannot be negative");
        }

        volume = inVolume;
    }

    /**
     * Grain can't expire, has best
     * before date.
     * 
     * @see IFood#calcExpiry()
     */
    @Override
    public boolean calcExpiry(Calendar today) {
        return false;
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
     * Grain object.
     * 
     * @param inObject The object compare with.
     * @return Boolean representing whether or not the objects are equal.
     */
    @Override
    public boolean equals(Object inObject)
    {
        Grain inGrain = null;
        boolean isEqual = false;

        if (inObject instanceof Grain)
        {
            inGrain = (Grain) inObject;
            isEqual = super.equals(inGrain) &&
                type.equals(inGrain.getType()) &&
                Math.abs(volume - inGrain.getVolume()) < Food.TOLERANCE;
        }

        return isEqual;
    }

    /**
     * @see Food#toString()
     */
    @Override
    public String toString()
    {
        return "Grain, " + getName() + ", " + type + ", " + volume +
               ", " + getStorageTemp() + ", " + getEndDate() +
               ", " + getPackaging();
    }

    /**
     * @see Food#clone()
     */
    @Override
    public Grain clone()
    {
        return new Grain(this);
    }
}

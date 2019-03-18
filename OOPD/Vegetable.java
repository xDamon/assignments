import java.util.*;

/**
 * Class representing all foods
 * that are considered vegetable.
 * 
 * @author Damon Ezard
 * @version 1.0.1
 * @since 30/05/2018
 */
public class Vegetable extends Food implements IFood
{
    public static final double MIN_WEIGHT = 0.2;
    public static final double MAX_WEIGHT = 5.0;

    private double weight;

    /**
     * Default Constructor.
     */
    public Vegetable()
    {
        super();

        weight = 2.5;
    }

    /**
     * Alternate Constructor.
     * 
     * @param inName The name of the vegetable.
     * @param inPackaging The packaging type for the vegetable.
     * @param inStorageTemp The temperature the vegetable should be stored at.
     * @param inEndDate The end date when the vegetable's condition changes.
     * @param inWeight The weight of the vegetable.
     */
    public Vegetable(
        String inName,
        String inPackaging,
        double inStorageTemp,
        SimpleGregorianCalendar inEndDate,
        double inWeight)
    {
        super(inName, inPackaging, inStorageTemp, inEndDate);

        setWeight(inWeight);
    }

    /**
     * Copy Constructor.
     * 
     * @param inVegetable The Vegetable object to be copied.
     */
    public Vegetable(Vegetable inVegetable)
    {
        super(inVegetable);

        weight = inVegetable.getWeight();
    }

    /**
     * @return The weight of the vegetable.
     */
    public double getWeight()
    {
        return weight;
    }

    /**
     * @param inWeight The new weight for the vegetable.
     */
    public void setWeight(double inWeight)
    {
        if (inWeight <= 0.2 || inWeight >= 5.0)
        {
            throw new IllegalArgumentException(
                "Weight must be between 0.2 and 0.5"
            );
        }

        weight = inWeight;
    }

    /**
     * Vegetable can't expire, has best
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
     * Vegetable object.
     * 
     * @param inObject The object compare with.
     * @return Boolean representing whether or not the objects are equal.
     */
    @Override
    public boolean equals(Object inObject)
    {
        Vegetable inVegetable = null;
        boolean isEqual = false;

        if (inObject instanceof Vegetable)
        {
            inVegetable = (Vegetable) inObject;
            isEqual = super.equals(inVegetable) &&
                Math.abs(weight - inVegetable.getWeight()) < Food.TOLERANCE;
        }

        return isEqual;
    }

    /**
     * @see Food#toString()
     */
    @Override
    public String toString()
    {
        return "Vegetable, " + getName() + ", " + weight + ", " + 
               getStorageTemp() + ", " + getEndDate() + ", " + getPackaging();
    }

    /**
     * @see Food#clone()
     */
    @Override
    public Vegetable clone()
    {
        return new Vegetable(this);
    }
}
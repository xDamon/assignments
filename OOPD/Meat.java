import java.util.*;

/**
 * Class representing all foods
 * that are considered meat.
 * 
 * @author Damon Ezard
 * @version 1.0.3
 * @since 30/05/2018
 */
public class Meat extends Food implements IFood
{
    public static final double MIN_WEIGHT = 0.2;
    public static final double MAX_WEIGHT = 5.0;

    private String cut;
    private double weight;

    /**
     * Default Constructor.
     */
    public Meat()
    {
        super();

        cut = "Default Cut";
        weight = 2.5;
    }

    /**
     * Alternate Constructor.
     * 
     * @param inName The name of the meat.
     * @param inPackaging The packaging type for the meat.
     * @param inStorageTemp The temperature the meat should be stored at.
     * @param inEndDate The end date when the meats's condition changes.
     * @param inCut The cut of meat.
     * @param inWeight The weight of the meat.
     */
    public Meat(
        String inName,
        String inPackaging,
        double inStorageTemp,
        SimpleGregorianCalendar inEndDate,
        String inCut,
        double inWeight)
    {
        super(inName, inPackaging, inStorageTemp, inEndDate);

        setCut(inCut);
        setWeight(inWeight);
    }

    /**
     * Copy Constructor.
     * 
     * @param inMeat The Meat object to be copied.
     */
    public Meat(Meat inMeat)
    {
        super(inMeat);

        cut = inMeat.getCut();
        weight = inMeat.getWeight();
    }

    /**
     * @return The cut of meat.
     */
    public String getCut()
    {
        return cut;
    }

    /**
     * @return The weight of the meat.
     */
    public double getWeight()
    {
        return weight;
    }

    /**
     * @param inCut The new cut of meat.
     */
    public void setCut(String inCut)
    {
        if (inCut == null)
        {
            throw new IllegalArgumentException("Cut cannot be null");
        }
        else if (inCut.trim().length() == 0)
        {
            throw new IllegalArgumentException("Cut cannot be empty");
        }

        cut = inCut;
    }

    /**
     * @param inWeight The new weight for the meat.
     */
    public void setWeight(double inWeight)
    {
        if (inWeight <= MIN_WEIGHT|| inWeight >= MAX_WEIGHT)
        {
            throw new IllegalArgumentException(
                "Weight must be between " + MIN_WEIGHT + " and " + MAX_WEIGHT
            );
        }

        weight = inWeight;
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
     * meat object.
     * 
     * @param inObject The object compare with.
     * @return Boolean representing whether or not the objects are equal.
     */
    @Override
    public boolean equals(Object inObject)
    {
        Meat inMeat = null;
        boolean isEqual = false;

        if (inObject instanceof Meat)
        {
            inMeat = (Meat) inObject;
            isEqual = super.equals(inMeat) &&
                cut.equals(inMeat.getCut()) &&
                Math.abs(weight - inMeat.getWeight()) < Food.TOLERANCE;
        }

        return isEqual;
    }

    /**
     * @see Food#toString()
     */
    @Override
    public String toString()
    {
        return "Meat, " + getName() + ", " + cut + ", " + weight + ", " +
               getStorageTemp() + ", " + getEndDate() + ", " + getPackaging();
    }

    /**
     * @see Food#clone()
     */
    @Override
    public Meat clone()
    {
        return new Meat(this);
    }
}
import java.util.*;

/**
 * Abstract class from which all
 * types of foods extend from.
 * 
 * @author Damon Ezard
 * @version 1.0.6
 * @since 18/05/2018
 */
public abstract class Food
{
    public static final double MIN_STORAGE_TEMP = -273.15;
    public static final double MAX_STORAGE_TEMP = 40.0;
    public static final double TOLERANCE = 0.001;

    private String name;
    private String packaging;
    private double storageTemp;
    private SimpleGregorianCalendar endDate;

    /**
     * Default Constructor.
     */
    public Food()
    {
        name = "Default";
        packaging = "Box";
        storageTemp = 3.0;
        endDate = new SimpleGregorianCalendar();
    }

    /**
     * Alternate Constructor.
     * 
     * @param inName The name of the food.
     * @param inPackaging The type of packaging for the food.
     * @param inStorageTemp The temperature the food should be stored at.
     * @param inEndDate The end date when the food's condition changes.
     */
    public Food(
        String inName,
        String inPackaging,
        double inStorageTemp,
        SimpleGregorianCalendar inEndDate)
    {
        setName(inName);
        setPackaging(inPackaging);
        setStorageTemp(inStorageTemp);
        setEndDate(inEndDate);
    }

    /**
     * Copy Constructor.
     * 
     * @param inFood The food object to be copied.
     */
    public Food(Food inFood)
    {
        name = inFood.getName();
        packaging = inFood.getPackaging();
        storageTemp = inFood.getStorageTemp();
        endDate = inFood.getEndDate();
    }

    /**
     * @return The name of the food.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return The type of packaging for the food.
     */
    public String getPackaging()
    {
        return packaging;
    }

    /**
     * @return The temperature the food should be stored at.
     */
    public double getStorageTemp()
    {
        return storageTemp;
    }

    /**
     * @return The end date at which the foods quality changes.
     */
    public SimpleGregorianCalendar getEndDate()
    {
        return endDate.clone();
    }

    /**
     * @param inName The new name of the food.
     */
    public void setName(String inName)
    {
        if (inName == null)
        {
            throw new IllegalArgumentException("Name cannot be null");
        }
        else if (isEmptyField(inName))
        {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        name = inName;
    }

    /**
     * @param inPackaging The new packaging for the food.
     */
    public void setPackaging(String inPackaging)
    {
        if (inPackaging == null)
        {
            throw new IllegalArgumentException("Packaging cannot be null");
        }
        else if (isEmptyField(inPackaging))
        {
            throw new IllegalArgumentException("Packaging cannot be empty");
        }

        packaging = inPackaging;
    }

    /**
     * @param inStorageTemp The new storage temperature for the food.
     */
    public void setStorageTemp(double inStorageTemp)
    {
        if (inStorageTemp <= MIN_STORAGE_TEMP)
        {
            throw new IllegalArgumentException(
                "Storage temperature cannot be less than absolute zero"
            );
        }

        storageTemp = inStorageTemp;
    }

    /**
     * @param inEndDate The new end date for the food.
     */
    public void setEndDate(SimpleGregorianCalendar inEndDate)
    {
        if (inEndDate.before(new SimpleGregorianCalendar()))
        {
            throw new IllegalArgumentException(
                "Use-by/best before date cannot be in the past"
            );
        }

        endDate = inEndDate;
    }

    /**
     * This checks if the string is not only
     * white space. Would probably make this protected
     * but we aren't allowed to use protected.
     * 
     * @param field Field to check if its only whitespace.
     * @return Whether or not the string is empty.
     */
    public boolean isEmptyField(String field)
    {
        return field.trim().length() == 0;
    }

    /**
     * Compares the values of an imported object
     * with the current Food instance to see whether
     * they are the same.
     * 
     * @param inObject The object to compare with.
     * @return Boolean representing whether or not they are equal.
     */
    @Override
    public boolean equals(Object inObject)
    {
        Food inFood = null;
        boolean isEqual = false;

        if (inObject instanceof Food)
        {
            inFood = (Food) inObject;
            isEqual = 
                name.equals(inFood.getName()) &&
                packaging.equals(inFood.getPackaging()) &&
                Math.abs(storageTemp - inFood.getStorageTemp()) < TOLERANCE &&
                endDate.equals(inFood.getEndDate());
        }

        return isEqual;
    }

    /**
     * Creates a string representing the
     * Food instance calling the method.
     * 
     * @return A String with the values of each Food field.
     */
    public abstract String toString();

    /**
     * Creates a new Food object
     * from the current instances
     * field values.
     * 
     * @return A new Food object
     */
    public abstract Food clone();
}
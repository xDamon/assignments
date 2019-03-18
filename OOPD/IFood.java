import java.util.Calendar;

/**
 * The interface that all FOOD subclasses
 * must implement.
 * 
 * @author Mark Upston
 * @version 1.0.0
 * @since 15/05/18
 */
public interface IFood
{
    /**
     * imports todays date and exports true if this food item
     * has reached its expiry date, false otherwise.
     * 
     * @param today Today's date.
     * @return Whether or not the food as expired.
     */
    public boolean calcExpiry(Calendar today);

    /**
     * imports a Food class object, and exports an integer
     * specifying the volume in litres of storage required.
     * 
     * This is not meant to make sense.
     * 
     * @param food The food to calculate the space for.
     * @return The volume of storage required in litres.
     */
    public int calcSpace(Food food);
}
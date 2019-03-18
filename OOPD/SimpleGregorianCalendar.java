import java.util.*;

/**
 * Same as GregorianCalendar but the
 * time component is set to 0 so only
 * the date is used.
 * 
 * There is no pseudocode for this class as
 * its implementation is very java specific.
 * 
 * @author Damon Ezard
 * @version 1.0.1
 * @since 28/05/2018
 */
public class SimpleGregorianCalendar extends GregorianCalendar
{
    /**
     * Default Constructor.
     */
    public SimpleGregorianCalendar()
    {
        super();

        ignoreTime();
    }

    /**
     * Alternate Constructor 1.
     * 
     * @param year The year for the calendar.
     * @param month The month for the calendar (0 based).
     * @param day The day for the calendar.
     */
    public SimpleGregorianCalendar(int year, int month, int day)
    {
        super(year, month, day);

        ignoreTime();
    }

    /**
     * Alternate Constructor 2.
     * 
     * @param date The date string to parse
     */
    public SimpleGregorianCalendar(String date)
    {
        super();

        String[] dayMonthYear;
        
        int day;
        int month;
        int year;

        try
        {
            dayMonthYear = date.split("/");
        
            day = Integer.parseInt(dayMonthYear[0]); 
            month = Integer.parseInt(dayMonthYear[1]);
            year = Integer.parseInt(dayMonthYear[2]);

            set(Calendar.YEAR, year);
            set(Calendar.MONTH, month - 1);
            set(Calendar.DAY_OF_MONTH, day);

            ignoreTime();
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException("Invalid date string");
        }
        catch (IndexOutOfBoundsException e)
        {
            throw new IllegalArgumentException("Invalid date string");
        }
    }

    /**
     * Clones the calendar.
     * 
     * @return The copy of the calendar instance.
     */
    @Override
    public SimpleGregorianCalendar clone()
    {
        return new SimpleGregorianCalendar(
            get(Calendar.YEAR),
            get(Calendar.MONTH),
            get(Calendar.DAY_OF_MONTH)
        );
    }

    /**
     * Converts the calendar to a string
     * 
     * @return The calendar in the format dd/mm/yyyy
     */
    @Override
    public String toString()
    {
        int day = get(Calendar.DAY_OF_MONTH);
        int month = get(Calendar.MONTH) + 1;
        int year = get(Calendar.YEAR);

        String dayString = String.valueOf(day);
        String monthString = String.valueOf(month);

        if (day < 10)
        {
            dayString = "0" + dayString;
        }

        if (month < 10)
        {
            monthString = "0" + monthString;
        }

        return dayString + "/" + monthString + "/" + year;
    }

    /**
     * Sets time related fields to 0 effectively
     * removing them from consideration when
     * comparing calendars.
     */
    private void ignoreTime()
    {
        set(Calendar.HOUR_OF_DAY, 0);
        set(Calendar.MINUTE, 0);
        set(Calendar.SECOND, 0);
        set(Calendar.MILLISECOND, 0);
    }
}

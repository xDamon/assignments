import java.util.*;

/**
 * Utility class containing all
 * methods related to getting valid
 * user input and creating user output.
 * 
 * @author Damon Ezard
 * @version 1.0.5
 * @since 30/05/18
 */
public class UserIO
{
    private static final Scanner scanner = new Scanner(System.in);

    public static String createMenu(String title, String[] options)
    {
        String menu = "===== " + title + " =====\n";

        for (int i = 0; i < options.length; i++)
        {
            menu += "[" + (i + 1) + "] " + options[i] + "\n";
        }

        return menu;
    }

    /**
     * Prompts the user to input an
     * integer, looping until the
     * integer is given.
     * 
     * @param prompt The message to display to the user.
     * @return The inputted integer.
     */
    public static int getInteger(String prompt)
    {
        int input;

        System.out.print(prompt);

        while (!scanner.hasNextInt())
        {
            // clear the invalid value.
            scanner.nextLine();
            System.out.print("Error: Input must be an integer\n" + prompt);
        }

        input = scanner.nextInt();
        // clear \n character
        scanner.nextLine();

        return input;
    }

    /**
     * Prompts the user to input an
     * integer within a certain range, looping
     * until the input is valid.
     * 
     * @param prompt The message to display to the user.
     * @param min The minimum value allowed.
     * @param max The maximum value allowed.
     * @return The valid inputted integer.
     */
    public static int getRangedInteger(String prompt, int min, int max)
    {
         int input = getInteger(prompt);

        while (input < min || input > max)
        {
            System.out.println(
                "Error: Input must be between " +
                min + " and " + max + " inclusive"
            );
            input = getInteger(prompt);
        }

        return input;
    }

    /**
     * Prompts the user to input
     * a real number, it loops until
     * a real number is given.
     * 
     * @param prompt The message to display to the user.
     * @return The inputted real nunber.
     */
    public static double getReal(String prompt)
    {
        double input;

        System.out.print(prompt);

        while (!scanner.hasNextDouble())
        {
            // clear the invalid value.
            scanner.nextLine();
            System.out.print("Error: Input must be a real number\n" + prompt);
        }

        input = scanner.nextDouble();
        // clear \n character
        scanner.nextLine();

        return input;
    }

    /**
     * Prompts the user to input a
     * a real number within a certain range, looping
     * until the input is valid.
     * 
     * @param prompt The message to display to the user.
     * @param min The minimum value allowed.
     * @param max The maximum value allowed.
     * @return The valid inputted real number.
     */
    public static double getRangedReal(String prompt, double min, double max)
    {
        double input = getReal(prompt);

        while (input < min || input > max)
        {
            System.out.println(
                "Error: Input must be between " +
                min + " and " + max + " inclusive"
            );
            input = getReal(prompt);
        }

        return input;
    }

    /**
     * Prompts the user to input a string.
     * 
     * @param prompt The message to display to the user.
     * @return The inputted string.
     */
    public static String getString(String prompt)
    {
        String input;

        System.out.print(prompt);

        input = scanner.nextLine();

        while (input.trim().length() == 0)
        {
            System.out.println("Error: Input cannot be empty\n" + prompt);
            input = scanner.nextLine();
        }

        return input;
    }

    public static SimpleGregorianCalendar getDate(String prompt)
    {
        boolean valid = false;
        String input;
        SimpleGregorianCalendar date = null;

        do
        {
            input = getString(prompt);

            try
            {
                date = new SimpleGregorianCalendar(input);

                if (date.before(new SimpleGregorianCalendar()))
                {
                    System.out.println("Error: Date cannot be in the past");
                }
                else
                {
                    valid = true;
                }
            }
            catch (IllegalArgumentException e)
            {
                System.out.println("Error: Invalid date string");
            }
        }
        while (!valid);

        return date;
    }

    /**
     * Closes the scanner instance
     * and System.in, should only be
     * called when no user input is needed
     * anymore.
     */
    public static void close()
    {
        scanner.close();
    }
}
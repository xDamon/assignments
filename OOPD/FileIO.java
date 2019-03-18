import java.io.*;

/**
 * Provides static methods for
 * generic file input and output
 * operations.
 * 
 * @author Damon Ezard
 * @version 1.0.4
 * @since 30/05/2018
 */
public class FileIO
{
    /**
     * Reads a file and counts
     * how many non-empty lines are in it.
     * 
     * @param fileName
     * @return Number of lines in the file
     */
    public static int getNumLines(String fileName) throws IOException
    {
        int numLines = 0;
        FileInputStream stream = new FileInputStream(fileName);
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(stream)
        );

        // if this errors the stream should still
        // close if possible, no null check is needed
        // because the code would have already exited
        // if creating the FileInputStream threw.
        try
        {
            while (reader.readLine() != null)
            {
                numLines++;
            }
        }
        finally
        {
            stream.close();
        }

        return numLines;
    }

    /**
     * Reads a specified number of
     * non-empty lines from a file.
     * 
     * @param fileName The name of the file to read.
     * @param numLines The number of lines from the file to read.
     * @return The values of each line read as an array of strings
     */
    public static String[] readNumLines(String fileName, int numLines) throws IOException
    {
        String line;
        String[] filteredLines;
        String[] lines = new String[numLines];

        int emptyLines = 0;
        int filteredLinesIndex = 0;

        FileInputStream stream = new FileInputStream(fileName);
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(stream)
        );

        try
        {
            for (int i = 0; i < lines.length; i++)
            {
                line = reader.readLine();

                // dont add empty lines.
                if (line != null)
                {
                    if (line.trim().length() == 0)
                    {
                        emptyLines++;
                    }

                    lines[i] = line;
                }
            }
        }
        finally
        {
            stream.close();
        }

        // have to count amount of empty lines and do all this
        // stuff because we can't use lists
        filteredLines = new String[lines.length - emptyLines];

        for (int i = 0; i < lines.length; i++)
        {
            line = lines[i].trim();

            if (line.length() > 0)
            {
                filteredLines[filteredLinesIndex++] = line;
            }
        }

        return filteredLines;
    }

    /**
     * Writes an array of lines to
     * a specified file.
     * 
     * @param fileName The file to write to.
     * @param lines The lines to write.
     */
    public static void writeNumLines(String fileName, String[] lines) throws IOException
    {
        FileOutputStream stream = new FileOutputStream(fileName);
        PrintWriter writer = new PrintWriter(stream);

        for (int i = 0; i < lines.length; i++)
        {
            writer.println(lines[i]);
        }

        writer.close();
    }
}
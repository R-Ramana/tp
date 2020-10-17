package seedu.duke.ui;

import seedu.duke.data.notebook.Notebook;
import seedu.duke.data.notebook.Note;
import seedu.duke.data.timetable.Timetable;
import seedu.duke.data.timetable.Event;

import java.util.ArrayList;
import java.util.Stack;

import static com.diogonunes.jcolor.Ansi.PREFIX;
import static com.diogonunes.jcolor.Ansi.POSTFIX;
import static com.diogonunes.jcolor.Ansi.RESET;

public class Formatter {

    /** A platform independent line separator. */
    public static final String LS = System.lineSeparator();

    private static final int NUM_ANSI_CHAR = 9;

    private static final String ROW_SPLIT = "=";
    private static final String COLUMN_SPLIT = "|";
    private static final String COLUMN_START = "|| ";
    private static final String COLUMN_END = " ||";
    private static final String EMPTY_STRING = " ";
    private static final char EMPTY_CHAR = ' ';

    /** Maximum number of characters within a row. */
    private static final int MAX_ROW_LENGTH = 150;
    /** Maximum length of message to within a row, minus the start and end formatting. */
    private static final int MAX_MESSAGE_LENGTH = MAX_ROW_LENGTH - COLUMN_START.length() - COLUMN_END.length();

    private static final int ANSI_PREFIX_LENGTH = 5;


    // Character code adapted from http://patorjk.com/software/taag/#p=display&f=Ghost&t=NotUS
    // Slight modifications made to make it easier on the eyes
    private static final String NOTUS_LOGO = LS
            + LS
            + "     .-') _               .-') _                 .-')    "
            + LS
            + "    ( OO ) )             (  OO) )               ( OO ).  "
            + LS
            + ",--./ ,--,'  .-'),-----. /     '._ ,--. ,--.   (_)---\\_) "
            + LS
            + "|   \\ |  |\\ ( OO'  .-.  '|'--...__)|  | |  |   /    _ |  "
            + LS
            + "|    \\|  | )/   |  | |  |'--.  .--'|  | | .-') \\  :` `.  "
            + LS
            + "|  .     |/ \\_) |  | |  |   |  |   |  | |( OO ) '..`''.) "
            + LS
            + "|  |\\    |    \\ |  | |  |   |  |   |  | | `-' /.-._)   \\ "
            + LS
            + "|  | \\   |     `'  '-'  '   |  |  ('  '-'(_.-' \\       / "
            + LS
            + "`--'  `--'       `-----'    `--'    `-----'     `-----'  "
            + LS;

    public static String getNotusLogo() {
        return NOTUS_LOGO;
    }

    public static String formatNotebook(String header, Notebook notebook) {
        String formattedString = "";
        return formattedString;
    }

    public static String formatNotebook(Notebook notebook) {
        String formattedString = "";
        return formattedString;
    }

    public static String formatNote(String header, Note note) {
        String formattedString = "";
        return formattedString;
    }

    public static String formatTimetable(String header, Timetable timetable) {
        String formattedString = "";
        return formattedString;
    }

    public static String formatEvent(String header, Event event) {
        String formattedString = "";
        return formattedString;
    }

    /**
     * Formats a string to be printed out.
     *
     * @param message String to be formatted.
     * @return Formatted message.
     */
    public static String formatString(String message) {
        return encloseTopAndBottom(encloseRow(message));
    }

    /**
     * Formats an arraylist of strings to be printed out. Each element in the list will be printed in a newline.
     *
     * @param messages Arraylist of strings to be formatted.
     * @param hasHeader Determines if there is a header. Header MUST be the first element in the list.
     * @return Formatted message.
     */
    public static String formatString(ArrayList<String> messages, boolean hasHeader) {
        String formattedString = "";
        if (hasHeader) {
            formattedString = generatesHeader(messages.get(0));

            for (int i = 1; i < messages.size(); ++i) {
                formattedString = formattedString.concat(encloseRow(messages.get(i)));
            }
        } else {
            for (String s : messages) {
                formattedString = formattedString.concat(encloseRow(s));
            }
        }
        return encloseTopAndBottom(formattedString);
    }

    /**
     * Formats a array of strings to be printed out. Each element in the list will be printed in a newline.
     *
     * @param messages Array of strings to be formatted.
     * @param hasHeader Determines if there is a header. Header MUST be the first element in the list.
     * @return Formatted message.
     */
    public static String formatString(String[] messages, boolean hasHeader) {
        String formattedString = "";
        if (hasHeader) {
            formattedString = generatesHeader(messages[0]);

            for (int i = 1; i < messages.length; ++i) {
                formattedString = formattedString.concat(encloseRow(messages[i]));
            }
        } else {
            for (String s : messages) {
                formattedString = formattedString.concat(encloseRow(s));
            }
        }
        return encloseTopAndBottom(formattedString);
    }

    /**
     * Generates a header row with the format.
     *
     * @param header Header message
     * @return Formatted header.
     */
    private static String generatesHeader(String header) {
        return encloseRow(header) + generatesRowSplit();
    }

    /**
     * Generates a row of pre-defined characters as to segregate row contents.
     *
     * @return A row of defined characters.
     */
    private static String generatesRowSplit() {
        return ROW_SPLIT.repeat(MAX_ROW_LENGTH) + LS;
    }

    /**
     * Encloses the top and bottom of the formatted message.
     *
     * @param message Formatted message to be enclosed.
     * @return Enclosed message.
     */
    private static String encloseTopAndBottom(String message) {
        return generatesRowSplit() + message + generatesRowSplit();
    }

    /**
     * Encloses the sides of the message.
     *
     * @param message Message to be enclosed.
     * @return Enclosed message.
     */
    private static String encloseRow(String message) {
        int numBlanks;

        int numColoredString = 0;

        // Array list to store startIndex, endIndex and the color of the string
        ArrayList<Integer> coloredStringStartIndexList = new ArrayList<>();
        ArrayList<Integer> coloredStringEndIndexList = new ArrayList<>();
        ArrayList<String> stringColorList = new ArrayList<>();

        // Stack is used to match the PREFIX and POSTFIX of each color. Possible patterns:
        // 1. Individual string has its own color.
        // [PREFIX]string[POSTFIX] [PREFIX]string[POSTFIX]
        // 2. Individual string color has its own color but is enclosed by another color.
        // [PREFIX][PREFIX]string[POSTFIX][PREFIX]string[POSTFIX][POSTFIX]
        // 3. String enclosed by nested colors
        // [PREFIX][PREFIX]PREFIX]string[POSTFIX][POSTFIX][POSTFIX]
        Stack<Integer> coloredStringStartIndexStack = new Stack<>();
        Stack<String> stringColorStack = new Stack<>();

        // Count the number of colored string in the message.
        String[] temp = message.split(EMPTY_STRING);
        int messageLength = 0;
        for (int i = 0; i < temp.length; ++i) {
            // Checks if it matches the PREFIX of a color declaration and is not the RESET color
            if (temp[i].contains(PREFIX) && !temp[i].contains(RESET)) {
                ++numColoredString;
                // Add them into the stack first
                coloredStringStartIndexStack.push(messageLength);
                stringColorStack.push(temp[i].substring(0, temp[i].indexOf(POSTFIX) + 1));
            }
            // If it matches a RESET, pairs it with the top item of the stack
            if (temp[i].contains(RESET)) {
                if (coloredStringStartIndexStack.size() > 0) {
                    coloredStringStartIndexList.add(coloredStringStartIndexStack.pop());
                    stringColorList.add(stringColorStack.pop());
                    coloredStringEndIndexList.add(messageLength + temp[i].indexOf(RESET));
                }
            }
            // Increment the message length by each string array, also account for the space
            messageLength += temp[i].length() + 1;
        }

        // Calculate the number of space needed to fill up if the message length is less than the MAX_MESSAGE_LENGTH
        numBlanks = MAX_MESSAGE_LENGTH - message.length() + numColoredString * NUM_ANSI_CHAR;

        // Adds empty space to the message
        if (numBlanks >= 0) {
            return COLUMN_START + message + EMPTY_STRING.repeat(numBlanks) + COLUMN_END + LS;
        } else {
            // Cut off the message and prints it on the next line.
            int startIndexOfNextLine = MAX_MESSAGE_LENGTH;
            // Checks if the start index for the next line lines between any colored word.
            boolean cutOffWordIsColored = false;
            String truncatedColor = RESET;

            // Iterate through all the color words that are previously stored.
            for (int i = 0; i < coloredStringStartIndexList.size(); ++i) {
                // If the message contains any ansi escape code, shift the start index since they will be removed
                // when printing
                if (startIndexOfNextLine > coloredStringStartIndexList.get(i)) {
                    startIndexOfNextLine += ANSI_PREFIX_LENGTH;
                    if (startIndexOfNextLine > coloredStringEndIndexList.get(i)) {
                        startIndexOfNextLine += RESET.length();
                    } else {
                        cutOffWordIsColored = true;
                        truncatedColor = stringColorList.get(i);
                        break;
                    }
                }
            }

            // If the cutoff is in the middle of a colored word, shift all the way to the space directly before it.
            if (cutOffWordIsColored) {
                while (message.charAt(startIndexOfNextLine) != EMPTY_CHAR) {
                    --startIndexOfNextLine;
                }
            }
            // Split the strings and enclose individual string
            String preservedMessage = message.substring(0, startIndexOfNextLine);
            String truncatedMessage = message.substring(startIndexOfNextLine);

            // Concat reset color at the end of the first line and add the color from the previous line to the next line
            if (cutOffWordIsColored) {
                preservedMessage = preservedMessage.concat(RESET);
                truncatedMessage = truncatedColor + truncatedMessage;
            }
            return encloseRow(preservedMessage) + encloseRow(truncatedMessage);
        }
    }
}

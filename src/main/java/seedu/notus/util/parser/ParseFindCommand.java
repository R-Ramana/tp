package seedu.notus.util.parser;

//@@author R-Ramana

import seedu.notus.command.Command;
import seedu.notus.command.FindCommand;
import seedu.notus.data.exception.SystemException;
import seedu.notus.data.exception.SystemException.ExceptionType;

import java.util.logging.Level;
import java.util.logging.Logger;

import static seedu.notus.storage.StorageManager.LOGS_DIR;

/**
 * Represents a parser object specifically to parse message for FindCommand.
 */
public class ParseFindCommand extends Parser {

    public ParseFindCommand(String userMessage) {
        super(userMessage);
    }

    /**
     * Ensures that the user does not leave input blank after entering the find command word.
     *
     * @return Returns a FindCommand to be executed by NotUS.
     * @throws SystemException for missing keyword.
     */
    @Override
    public Command parse() throws SystemException {
        Logger loggerFind = Logger.getLogger("ParserPrepareFind");
        setupLogger(loggerFind, LOGS_DIR + "/FindCommandParser.log");

        try {
            userMessage = checkBlank(userMessage, ExceptionType.EXCEPTION_MISSING_KEYWORD);
            loggerFind.log(Level.INFO, "If no null pointer, keyword is trimmed.");
        } catch (NullPointerException exception) {
            loggerFind.log(Level.INFO, "Null pointer exception caught.");
            throw new SystemException(ExceptionType.EXCEPTION_MISSING_KEYWORD);
        }
        loggerFind.log(Level.INFO, "Will execute FindCommand");
        return new FindCommand(userMessage);
    }
}

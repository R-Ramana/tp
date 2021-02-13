package seedu.notus.command;

import seedu.notus.data.timetable.Reminder;
import seedu.notus.ui.Formatter;

import java.util.ArrayList;

import static seedu.notus.util.CommandMessage.NO_REMINDERS_MESSAGE;
import static seedu.notus.util.CommandMessage.REMINDERS_MESSAGE;

//@@author brandonywl
/**
 * Returns all the reminders that should occur today.
 */
public class RemindCommand extends Command {

    public static final String COMMAND_WORD = "remind-e";

    /**
     * Default constructor of RemindEvent. No arguments are expected as we are only looking at reminders today.
     */
    public RemindCommand() {

    }

    @Override
    public String execute() {
        ArrayList<Reminder> reminders = timetable.getReminders();
        if (reminders.size() == 0) {
            return Formatter.formatString(NO_REMINDERS_MESSAGE);
        } else {
            return Formatter.formatReminders(REMINDERS_MESSAGE, reminders);
        }
    }
}

package seedu.notus.command;

import org.junit.jupiter.api.Test;

import seedu.notus.data.tag.Tag;
import seedu.notus.data.timetable.DailyEvent;
import seedu.notus.data.timetable.Event;
import seedu.notus.data.timetable.Timetable;
import seedu.notus.data.timetable.Reminder;
import seedu.notus.ui.Formatter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.notus.util.CommandMessage.REMINDERS_MESSAGE;

//@@author brandonywl
class RemindCommandTest {
    private static final String COMMAND_SUCCESSFUL_MESSAGE = REMINDERS_MESSAGE;
    private static final String TEST_TITLE_1 = "CS2113 Tutorial";
    private static final String TEST_TITLE_2 = "CS2113 Lecture";
    private static final String TEST_TITLE_3 = "CS2113 Meeting";
    private static final String TEST_TITLE_4 = "CS2113 Coding";
    private static final LocalDateTime TEST_DATE_TIME = LocalDateTime.of(2020,02,02,12,45);
    private static final LocalDateTime TEST_END_DATE_TIME = LocalDateTime.of(2020,02,02,13,45);
    private static final boolean TEST_REMINDER = true;
    private static final ArrayList<Integer> TEST_TIME_PERIODS = new ArrayList<>(List.of(1,3));
    private static HashMap<String, ArrayList<Integer>> reminderSchedule = new HashMap<>();
    private static final ArrayList<Tag> tags = new ArrayList<>();
    private DailyEvent dailyEvent = new DailyEvent(TEST_TITLE_4, TEST_DATE_TIME, TEST_END_DATE_TIME,
            TEST_REMINDER, reminderSchedule, tags);

    private static Timetable timetable = new Timetable();



    private RemindCommand command = new RemindCommand();

    @Test
    void execute_singleEvent_success() {
        reminderSchedule.put("day", TEST_TIME_PERIODS);
        timetable.addEvent(dailyEvent);
        command.setData(null, timetable, null, null);
        DailyEvent reminderEvent = new DailyEvent(dailyEvent.getTitle(),
                TEST_DATE_TIME.plusDays(1), TEST_END_DATE_TIME.plusDays(1),
                TEST_REMINDER, reminderSchedule, tags);

        String expected = Formatter.formatReminders(COMMAND_SUCCESSFUL_MESSAGE, timetable.getReminders());
        assertEquals(expected, command.execute());

    }
}

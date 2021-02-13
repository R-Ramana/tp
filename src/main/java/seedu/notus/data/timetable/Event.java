package seedu.notus.data.timetable;

import seedu.notus.data.tag.Tag;
import seedu.notus.data.tag.TaggableObject;
import seedu.notus.ui.Formatter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

//@@author brandonywl
/**
 * Represents an Event. Contains all the information of an Event.
 */
public class Event extends TaggableObject implements Comparable<Event> {
    public static final String REMINDER_DAY = "day";
    public static final String REMINDER_WEEK = "week";

    private String title;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Boolean isToRemind;
    private Boolean isRecurring;
    private HashMap<String, ArrayList<Integer>> reminderPeriods = new HashMap<>();


    /**
     * Creates an Event object with its title, date and time provided.
     *
     * @param title Title of the event.
     * @param startDateTime Start DateTime of Event
     * @param endDateTime End DateTime of Event
     */
    public Event(String title, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.isToRemind = false;
        this.isRecurring = false;
    }

    /**
     * Creates an Event object with its title, date, time, isToRemind and isRecurring provided.
     *
     * @param title Title of the event.
     * @param startDateTime Start DateTime of Event
     * @param endDateTime End DateTime of Event
     * @param isToRemind Whether the event requires a reminder.
     * @param isRecurring Whether the event will re-occur.
     */
    public Event(String title, LocalDateTime startDateTime, LocalDateTime endDateTime,
                 boolean isToRemind, boolean isRecurring) {
        this(title, startDateTime, endDateTime);
        this.isToRemind = isToRemind;
        this.isRecurring = isRecurring;
    }

    /**
     * Creates an Event object with its title, date, time, isToRemind, isRecurring, reminder periods and tags provided.
     *
     * @param title Title of event.
     * @param startDateTime Start DateTime of Event
     * @param endDateTime End DateTime of Event
     * @param isToRemind Whether the event requires a reminder.
     * @param isRecurring Whether the event will reoccur.
     * @param reminderPeriods When the reminders will be given.
     * @param tags Tags of the event.
     */
    public Event(String title, LocalDateTime startDateTime, LocalDateTime endDateTime,
                 boolean isToRemind, boolean isRecurring,
                 HashMap<String, ArrayList<Integer>> reminderPeriods, ArrayList<Tag> tags) {
        this(title, startDateTime, endDateTime, isToRemind, isRecurring);
        this.reminderPeriods = reminderPeriods;
        this.tags = tags;
    }

    /**
     * Creates an Event object with its title, date, time, isToRemind, isRecurring as well as reminder periods provided.
     *
     * @param title Title of event.
     * @param startDateTime Start DateTime of Event
     * @param endDateTime End DateTime of Event
     * @param isToRemind Whether the event requires a reminder.
     * @param isRecurring Whether the event will reoccur.
     * @param reminderPeriods When the reminders will be given.
     */
    public Event(String title, LocalDateTime startDateTime, LocalDateTime endDateTime,
                 boolean isToRemind, boolean isRecurring,
                 HashMap<String, ArrayList<Integer>> reminderPeriods) {
        this(title, startDateTime, endDateTime, isToRemind, isRecurring);
        this.reminderPeriods = reminderPeriods;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime dateTime) {
        startDateTime = dateTime;
    }

    public LocalDate getStartDate() {
        return startDateTime.toLocalDate();
    }

    public LocalTime getStartTime() {
        return startDateTime.toLocalTime();
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime dateTime) {
        endDateTime = dateTime;
    }

    public LocalDate getEndDate() {
        return endDateTime.toLocalDate();
    }


    public LocalTime getEndTime() {
        return endDateTime.toLocalTime();
    }

    public String getStartDateTimeString() {
        return getLocalDateTimeStringRep(startDateTime);
    }

    public String getEndDateTimeString() {
        return getLocalDateTimeStringRep(endDateTime);
    }

    /**
     * Provides a format for LocalDateTime output.
     *
     * @param dateTime DateTime to format.
     * @return String representation for saving.
     */
    private String getLocalDateTimeStringRep(LocalDateTime dateTime) {
        return dateTime.toLocalDate().toString() + " " + dateTime.toLocalTime().toString();
    }

    public boolean getIsToRemind() {
        return isToRemind;
    }

    public void setIsToRemind(boolean isToRemind) {
        this.isToRemind = isToRemind;
    }

    public HashMap<String, ArrayList<Integer>> getReminderPeriods() {
        return this.reminderPeriods;
    }

    public void setReminderPeriods(HashMap<String, ArrayList<Integer>> reminderPeriods) {
        this.reminderPeriods = reminderPeriods;
    }

    public boolean getRecurring() {
        return isRecurring;
    }

    public ArrayList<String> getReminderPeriodsString() {
        ArrayList<String> periods = new ArrayList<>();

        ArrayList<Integer> dayRepeatPeriod = reminderPeriods.get(REMINDER_DAY);
        ArrayList<Integer> weekRepeatPeriod = reminderPeriods.get(REMINDER_WEEK);
        if (dayRepeatPeriod != null) {
            dayRepeatPeriod.sort(Integer::compareTo);
            for (Integer unit: dayRepeatPeriod) {
                periods.add(unit + "-day");
            }
        }

        if (weekRepeatPeriod != null) {
            weekRepeatPeriod.sort(Integer::compareTo);
            for (Integer unit : weekRepeatPeriod) {
                periods.add(unit + "-week");
            }
        }
        return periods;
    }

    /**
     * Checks if the start and end date are the same date.
     *
     * @return Whether the start date's date is the same as the end date's date.
     */
    public boolean hasSameStartEndDateDate() {
        return (getStartDate().compareTo(getEndDate()) == 0);
    }

    /**
     * Get the event length in minutes.
     *
     * @return Minutes of how long the event will run.
     */
    public int getEventLengthInMinutes() {
        int eventDuration = (getEndTime().getHour() - getStartTime().getHour()) * 60;
        eventDuration += (getEndTime().getMinute() - getStartTime().getMinute());
        return eventDuration;
    }

    /**
     * Get all the reminder dates from that this Event would have.
     *
     * @return An ArrayList of Dates that reminders of this event should show.
     */

    public ArrayList<LocalDate> getReminderDates() {
        ArrayList<LocalDate> dates = new ArrayList<>();
        if (!isToRemind) {
            return dates;
        }
        for (String unit : reminderPeriods.keySet()) {
            ArrayList<Integer> timePeriodsInUnit = reminderPeriods.get(unit);
            LocalDate date = this.startDateTime.toLocalDate();
            for (Integer timePeriod : timePeriodsInUnit) {
                switch (unit) {
                case REMINDER_DAY:
                    date = this.startDateTime.toLocalDate().plusDays(-timePeriod);
                    break;
                case REMINDER_WEEK:
                    date = this.startDateTime.toLocalDate().plusWeeks(-timePeriod);
                    break;
                default:
                    break;
                }
                dates.add(date);
            }
        }
        dates.sort(LocalDate::compareTo);
        return dates;
    }

    public boolean occursDuringEvent(Event event) {
        if (startDateTime.equals(endDateTime)
                && (startDateTime.equals(event.startDateTime) || startDateTime.equals(event.endDateTime))) {
            return true;
        }
        if (checkClash(event.startDateTime, event.endDateTime, startDateTime)) {
            return !startDateTime.equals(event.endDateTime);
        }
        if (checkClash(startDateTime, endDateTime, event.startDateTime)) {
            return !event.startDateTime.equals(endDateTime);
        }
        return false;
    }

    private boolean checkClash(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime timeToCheck) {
        return (timeToCheck.compareTo(startDateTime) >= 0 && timeToCheck.compareTo(endDateTime) <= 0);
    }

    /**
     * Converts an Event to a format for a reminder.
     *
     * @return Reduced String representation of an Event.
     */
    public String toReminderString() {
        String titleString = "Event: " + title;
        String dateString = "Date: " + startDateTime.toLocalDate().toString()
                + "\tTime: " + startDateTime.toLocalTime().toString();
        return titleString + Formatter.LS + dateString;
    }

    @Override
    public String toString() {
        String titleString = "Event: " + title;
        String dateString = "Date: " + startDateTime.toLocalDate().toString()
                + "\tTime: " + startDateTime.toLocalTime().toString();
        String remindString = "Reminder: " + isToRemind;
        String repeatingString = "Repeating: " + isRecurring;
        String lineSeparator = Formatter.LS;
        return titleString + lineSeparator + dateString + lineSeparator + remindString
                + lineSeparator + repeatingString;
    }

    @Override
    public int compareTo(Event o) {
        return startDateTime.compareTo(o.startDateTime);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Event) {
            Event event = (Event) o;
            return (occursDuringEvent(event) && title.equalsIgnoreCase(event.title));
        }
        return false;
    }
}

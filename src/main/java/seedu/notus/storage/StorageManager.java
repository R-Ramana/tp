package seedu.notus.storage;

import seedu.notus.command.AddEventCommand;
import seedu.notus.command.AddNoteCommand;
import seedu.notus.command.Command;

import seedu.notus.data.exception.SystemException;

import seedu.notus.data.notebook.Note;
import seedu.notus.data.notebook.Notebook;
import seedu.notus.data.tag.Tag;
import seedu.notus.data.tag.TagManager;

import seedu.notus.data.timetable.Event;
import seedu.notus.data.timetable.RecurringEvent;
import seedu.notus.data.timetable.Timetable;

import seedu.notus.util.PrefixSyntax;
import seedu.notus.util.parser.ParserManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static seedu.notus.ui.Formatter.LS;
import static seedu.notus.util.PrefixSyntax.PREFIX_ARCHIVE;
import static seedu.notus.util.PrefixSyntax.PREFIX_DELIMITER;
import static seedu.notus.util.PrefixSyntax.PREFIX_LOAD;
import static seedu.notus.util.PrefixSyntax.PREFIX_TAG;

//@@author prachi2023

/** Represents a StorageManager.
 * Manages the saving and loading of task list data.
 */
public class StorageManager {
    /** logging. */
    private static final Logger LOGGER = Logger.getLogger("StorageManager");

    /** Default folders directory. */
    public static final String LOGS_DIR = "logs";
    public static final String FOLDER_DIR = "data";
    public static final String NOTES_DIR = "/notes";
    public static final String ARCHIVED_NOTES_DIR = "/archived";

    /** Default file path. */
    public static final String NOTEBOOK_FILE_PATH = "/notebook.txt";
    private static final String ARCHIVED_NOTEBOOK_FILE_PATH = "/archived_notebook.txt";
    private static final String TAG_FILE_PATH = "/tags.txt";
    private static final String TIMETABLE_FILE_PATH = "/timetable.txt";

    /** Related classes. */
    private Timetable timetable;
    private ParserManager parserManager;
    private Notebook notebook;
    private TagManager tagManager;

    public StorageManager(Timetable timetable, ParserManager parserManager,
                    Notebook notebook, TagManager tagManager) {
        this.timetable = timetable;
        this.parserManager = parserManager;
        this.notebook = notebook;
        this.tagManager = tagManager;

        setupLogger();
        LOGGER.log(Level.INFO, "New storageManager object created.");
    }

    /* Set up of Storage manager */

    /**
     * Checks if the file directories exist otherwise creates them.
     * It also creates the files for the Notebook and timetable information if it does not already exist
     *
     * @throws SystemException when it is unable to create a file
     */
    public void createFiles() throws SystemException {
        //Create directories
        String dataPath = FOLDER_DIR;
        String notesPath = FOLDER_DIR + NOTES_DIR;
        String archivedNotesPath = FOLDER_DIR + ARCHIVED_NOTES_DIR;

        String notebookFilePath = FOLDER_DIR + NOTEBOOK_FILE_PATH;
        String archivedNotebookFilePath = FOLDER_DIR + ARCHIVED_NOTEBOOK_FILE_PATH;
        String tagsFilePath = FOLDER_DIR + TAG_FILE_PATH;
        String timetableFilePath = FOLDER_DIR + TIMETABLE_FILE_PATH;

        String[] paths = {dataPath, notesPath, archivedNotesPath};
        String[] files = {notebookFilePath, archivedNotebookFilePath, tagsFilePath, timetableFilePath};

        for (String path: paths) {
            createDirectory(path);
        }

        for (String file: files) {
            try {
                createFile(file);
            } catch (IOException exception) {
                LOGGER.log(Level.INFO, "Unable to create a file");
                throw new SystemException(SystemException.ExceptionType.EXCEPTION_FILE_CREATION_ERROR);
            }
        }
    }

    /**
     * Creates a directory path data/notes. In case both data and /notes do not exist.
     */
    public static void createDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdir();
            if (!path.equals(LOGS_DIR)) {
                LOGGER.log(Level.INFO, "Created directory: " + directory);
            }
        }
    }

    /**
     * Checks if a file exists. If it does not, creates file with the input path
     * @param path path of file to be created
     * @throws IOException thrown when directory does not exist. Unable to create file
     */
    public void createFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
            LOGGER.log(Level.INFO, "Created file: " + file);
        }
    }

    /* Loading of information from files */

    /**
     * Loads the Notebook details and content for unArchived Notebooks.
     *
     * @param isArchive  Boolean to determine whether to load archived or non-archived files.
     */
    public void loadAllNotes(boolean isArchive) throws SystemException {
        String path;
        if (isArchive) {
            path = FOLDER_DIR + ARCHIVED_NOTEBOOK_FILE_PATH;
        } else {
            path = FOLDER_DIR + NOTEBOOK_FILE_PATH;
        }

        File f = new File(path);
        Scanner s;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException exception) {
            throw new SystemException(SystemException.ExceptionType.EXCEPTION_FILE_NOT_FOUND_ERROR);
        }
        LOGGER.log(Level.INFO, "Found file: " + path);
        while (s.hasNext()) {
            String taskDetails = AddNoteCommand.COMMAND_WORD + " " +  s.nextLine()
                                + " " + PREFIX_DELIMITER + PREFIX_ARCHIVE + " " + isArchive
                                + " " + PREFIX_DELIMITER + PREFIX_LOAD;
            Command command = parserManager.parseCommand(taskDetails);
            command.setData(notebook, timetable, tagManager, this);
            command.execute();
        }
        s.close();
    }

    /**
     * Loads all the details from the the timetable stored in the text file.
     *
     * @throws SystemException Thrown when there is no file to read from.
     */
    public void loadTimetable() throws SystemException {
        String path = FOLDER_DIR + TIMETABLE_FILE_PATH;
        File f = new File(path);

        Scanner s;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException exception) {
            throw new SystemException(SystemException.ExceptionType.EXCEPTION_FILE_NOT_FOUND_ERROR);
        }
        LOGGER.log(Level.INFO, "Found file: " + path);
        while (s.hasNext()) {
            String eventDetails = AddEventCommand.COMMAND_WORD + " " +  s.nextLine();
            Command command = parserManager.parseCommand(eventDetails);
            command.setData(notebook, timetable, tagManager, this);
            command.execute();
        }
        s.close();
    }

    public ArrayList<String> getNoteContent(Note note, boolean isArchive) throws SystemException {
        ArrayList<String> content = new ArrayList<>();
        String path;

        if (isArchive) {
            path = FOLDER_DIR + ARCHIVED_NOTES_DIR + "/" + note.getTitle() + ".txt";
        } else {
            path = FOLDER_DIR + NOTES_DIR + "/" + note.getTitle() + ".txt";
        }

        File f = new File(path);
        Scanner s;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException exception) {
            throw new SystemException(SystemException.ExceptionType.EXCEPTION_FILE_NOT_FOUND_ERROR);
        }
        LOGGER.log(Level.INFO, "Found file: " + path);
        while (s.hasNext()) {
            content.add(s.nextLine());
        }
        s.close();
        return content;
    }

    /* Saving and deleting notes */

    /**
     * Clears the content in the original file storing all the note details.
     * Replaces it with the new note content details.
     *
     * @param isArchive determines whether to save archived notes or normal notes
     * @throws IOException thrown when unable to write to the file
     */
    public void saveAllNoteDetails(Boolean isArchive) throws IOException {
        String path;

        ArrayList<Note> notes;

        if (isArchive) {
            notes = notebook.getArchivedNotes();
            path = FOLDER_DIR + ARCHIVED_NOTEBOOK_FILE_PATH;
        } else {
            notes = notebook.getNotes();
            path = FOLDER_DIR + NOTEBOOK_FILE_PATH;
        }
        FileWriter fw = new FileWriter(path, false);
        fw.write("");
        fw.close();

        for (Note note: notes) {
            saveNoteDetails(note, isArchive);
        }
    }

    public void saveNote(Note note, boolean isArchive) throws IOException {
        if (!noteExists(note, isArchive)) {
            saveNoteContent(note, isArchive);
            saveNoteDetails(note, isArchive);
        }
    }

    /**
     * Saves an individual note to the storage file.
     *
     * @param note The note to be saved
     */
    public void saveNoteContent(Note note, boolean isArchive) throws IOException {
        String path;

        if (isArchive) {
            path = FOLDER_DIR + ARCHIVED_NOTES_DIR + "/" + note.getTitle() + ".txt";
        } else {
            path = FOLDER_DIR + NOTES_DIR + "/" + note.getTitle() + ".txt";
        }

        createFile(path);
        FileWriter fw = new FileWriter(path);
        fw.write(note.getContentString());
        fw.close();
    }

    /**
     * Saves the details of notes such as title, tags and pinned status to the notebook text file.
     * @param note Note of which details are to be saved to the file
     */
    public void saveNoteDetails(Note note, boolean isArchive) throws IOException {
        String path;

        if (isArchive) {
            path = FOLDER_DIR + ARCHIVED_NOTEBOOK_FILE_PATH;
        } else {
            path = FOLDER_DIR + NOTEBOOK_FILE_PATH;
        }

        FileWriter fwAppend = new FileWriter(path, true);
        fwAppend.write(note.toSaveString());
        fwAppend.close();
    }

    public void deleteNoteContentFile(String noteTitle, boolean isArchive) throws SystemException {
        String path;

        if (isArchive) {
            path = FOLDER_DIR + ARCHIVED_NOTES_DIR + "/" + noteTitle + ".txt";
        } else {
            path = FOLDER_DIR + NOTES_DIR + "/" + noteTitle + ".txt";
        }

        File file = new File(path);

        if (file.exists()) {
            LOGGER.log(Level.INFO, "Found file: " + path);
            if (!file.delete()) {
                throw new SystemException(SystemException.ExceptionType.EXCEPTION_FILE_DELETION_ERROR);
            }
        } else {
            LOGGER.log(Level.INFO, "Unable to find file: " + path);
            throw new SystemException(SystemException.ExceptionType.EXCEPTION_FILE_NOT_FOUND_ERROR);
        }
    }

    /**
     * Returns a boolean of whether the file storing the content of the note already exists.
     *
     * @param note note whose file status needs to be checked
     * @return boolean
     */
    public boolean noteExists(Note note, boolean isArchive) {
        String path;

        if (isArchive) {
            path = FOLDER_DIR + ARCHIVED_NOTES_DIR + "/" + note.getTitle() + ".txt";
        } else {
            path = FOLDER_DIR + NOTES_DIR + "/" + note.getTitle() + ".txt";
        }

        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    /* Timetable Saving and Loading */

    /**
     * Saves all the Events in the Timetable to the storage file.
     *
     */
    public void saveTimetable() throws IOException {
        String path = FOLDER_DIR + TIMETABLE_FILE_PATH;

        //clear file
        FileWriter fwClear = new FileWriter(path, false);
        fwClear.write("");
        fwClear.close();

        //rewrite information to the file
        FileWriter fwAppend = new FileWriter(path, true);

        ArrayList<Event> allEvents = timetable.getEvents();
        String eventDetails;

        for (Event event: allEvents) {
            eventDetails = getEventDetailsSaveFormat(event);
            fwAppend.write(eventDetails);
        }
        fwAppend.close();
    }

    private static String getEventDetailsSaveFormat(Event event) {
        String eventDetails;
        String tagDetails = "";

        for (Tag tag: event.getTags()) {
            tagDetails += PREFIX_DELIMITER + PREFIX_TAG + " " + tag.toSaveString() + " ";
        }

        eventDetails = PREFIX_DELIMITER + PrefixSyntax.PREFIX_TITLE + " " + event.getTitle() + " "
                + PREFIX_DELIMITER + PrefixSyntax.PREFIX_TIMING + " "
                + event.getStartDateTimeString() + " "
                + PREFIX_DELIMITER + PrefixSyntax.PREFIX_END_TIMING + " "
                + event.getEndDateTimeString() + " "
                + tagDetails + " ";

        ArrayList<String> reminderPeriods = event.getReminderPeriodsString();

        if (!reminderPeriods.isEmpty()) {
            eventDetails += PREFIX_DELIMITER + PrefixSyntax.PREFIX_REMIND + " ";
            for (String reminderPeriod : reminderPeriods) {
                eventDetails += reminderPeriod + " ";
            }
        }

        if (event instanceof RecurringEvent) {
            RecurringEvent recEvent = (RecurringEvent) event;
            eventDetails += PREFIX_DELIMITER + PrefixSyntax.PREFIX_RECURRING
                    + " " + recEvent.getRecurrenceType() + " ";
            eventDetails += PREFIX_DELIMITER + PrefixSyntax.PREFIX_STOP_RECURRING
                    + " " + recEvent.getEndRecurrenceDateTime();
        }
        return eventDetails + LS;
    }

    /**
     * Saves all the information that has been changed.
     * Information includes: note details and event/timetable information.
     *
     * @throws IOException if unable to save to the file.
     */
    public void saveAll() throws IOException {
        saveTimetable();
        saveAllNoteDetails(false);
        saveAllNoteDetails(true);
    }

    private void setupLogger() {
        LogManager.getLogManager().reset();
        LOGGER.setLevel(Level.INFO);

        try {
            FileHandler fileHandler = new FileHandler(LOGS_DIR + "/storage.log");
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.INFO);
            LOGGER.addHandler(fileHandler);
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "File logger not working.", exception);
        }

    }
}

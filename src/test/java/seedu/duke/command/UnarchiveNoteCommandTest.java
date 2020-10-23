package seedu.duke.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.data.notebook.Note;
import seedu.duke.data.notebook.Notebook;
import seedu.duke.data.notebook.NotebookStub;
import seedu.duke.data.notebook.Tag;
import seedu.duke.ui.FormatterStub;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnarchiveNoteCommandTest {
    final int MAX_ROW_LENGTH = 100;
    final int INDEX = 1;

    Notebook notebook;
    ArrayList<String> content;

    ArrayList<Tag> tag = new ArrayList<>();
    ArrayList<Tag> tagSet = new ArrayList<>();
    Tag tagImpt;
    Tag tagCs2113;
    Tag tagNus;

    @BeforeEach
    void setup() {
        notebook = new Notebook();
        content = new ArrayList<>();
        tagImpt = new Tag("Impt", Tag.COLOR_RED_STRING);
        tagCs2113 = new Tag("CEG", Tag.COLOR_YELLOW_STRING);
        tagNus = new Tag("NUS", Tag.COLOR_BLUE_STRING);

        content.add("default");
        content.add("hi how are you");

        tag.add(tagImpt);

        tagSet.add(tagNus);
        tagSet.add(tagCs2113);

        Note testNote1 = new Note("Default", content, true, tag);
        Note testNote2 = new Note("TestNote1", content, false);
        Note testNote3 = new Note("TestNote2", content, false, tagSet);
        Note testNote4 = new Note("Random Text", content, true, tagSet);

        notebook.addNote(testNote1);
        notebook.addNote(testNote2);
        notebook.addNote(testNote3);
        notebook.addNote(testNote4);

        notebook.archiveNotes(INDEX - 1);
        notebook.archiveNotes("random text");
    }

    @Test
    void execute_validIndex_returnsUnarchiveMessage() {
        int index = 1;
        String title = NotebookStub.getUnarchiveNoteTitle(index);

        String expected = "=".repeat(MAX_ROW_LENGTH)
                + FormatterStub.encloseRow(title, true)
                + "=".repeat(MAX_ROW_LENGTH)
                + System.lineSeparator();
        String result = getCommandExecutionString(notebook, index - 1);

        assertEquals(expected, result);
    }

    @Test
    void execute_invalidIndex_returnsInvalidIndexMessage() {
        int index = 50;
        String title = NotebookStub.getUnarchiveNoteTitle(index);

        String expected = "=".repeat(MAX_ROW_LENGTH)
                + FormatterStub.encloseRow(title)
                + "=".repeat(MAX_ROW_LENGTH)
                + System.lineSeparator();
        String result = getCommandExecutionString(notebook, index - 1);

        assertEquals(expected, result);
    }

    @Test
    void execute_validTitle_returnsUnarchiveMessage() {
        String title = "random text";

        String expected = "=".repeat(MAX_ROW_LENGTH)
                + FormatterStub.encloseRow(NotebookStub.getUnarchiveNoteTitle(title), true)
                + "=".repeat(MAX_ROW_LENGTH)
                + System.lineSeparator();
        String result = getCommandExecutionString(notebook, title);

        assertEquals(expected, result);
    }

    @Test
    void execute_invalidTitle_returnsNoNotes() {
        String title = "rando";

        String expected = "=".repeat(MAX_ROW_LENGTH)
                + FormatterStub.encloseRow(NotebookStub.getUnarchiveNoteTitle(title))
                + "=".repeat(MAX_ROW_LENGTH)
                + System.lineSeparator();
        String result = getCommandExecutionString(notebook, title);

        assertEquals(expected, result);
    }

    private String getCommandExecutionString(Notebook notebook, String keyword) {
        UnarchiveNoteCommand unarchiveCommand = new UnarchiveNoteCommand(keyword);
        unarchiveCommand.setData(notebook, null, null, null);
        return unarchiveCommand.execute();
    }

    private String getCommandExecutionString(Notebook notebook, int index) {
        UnarchiveNoteCommand unarchiveCommand = new UnarchiveNoteCommand(index);
        unarchiveCommand.setData(notebook, null, null, null);
        return unarchiveCommand.execute();
    }

}
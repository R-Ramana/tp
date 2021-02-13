package seedu.notus.command;

import seedu.notus.data.exception.SystemException;
import seedu.notus.data.notebook.Note;
import seedu.notus.ui.Formatter;

import java.util.ArrayList;
import java.util.Map;
import java.io.IOException;

import static seedu.notus.util.CommandMessage.EDIT_NOTE_SUCCESSFUL_MESSAGE;
import static seedu.notus.util.CommandMessage.FILE_WRITE_UNSUCCESSFUL_MESSAGE;
import static seedu.notus.util.CommandMessage.INVALID_LINE_UNSUCCESSFUL_MESSAGE;
import static seedu.notus.util.CommandMessage.NOTE_DOES_NOT_EXIST_MESSAGE;
import static seedu.notus.util.CommandMessage.SAME_NOTE_TITLE_UNSUCCESSFUL_MESSAGE;
import static seedu.notus.util.CommandMessage.NOTE_EXIST_MESSAGE;
import static seedu.notus.util.parser.Parser.inputContent;

//@@author Nazryl
public class EditNoteCommand extends Command {

    public static final String COMMAND_WORD = "edit-n";

    private int index;
    private boolean isInput;
    private Note newNote;
    private Map<Integer, String> addLists;
    private Map<Integer, String> editLists;
    private Map<Integer, String> deleteLists;

    /**
     * Constructs an EditNoteCommand to edit a Note.
     *
     * @param index of the Note to be edited.
     */
    public EditNoteCommand(int index, Note newNote, Map<Integer, String> addLists,
                           Map<Integer, String> editLists, Map<Integer, String> deleteLists, boolean isInput) {
        this.index = index;
        this.newNote = newNote;
        this.addLists = addLists;
        this.editLists = editLists;
        this.deleteLists = deleteLists;
        this.isInput = isInput;
    }

    @Override
    public String execute() {
        Note oldNote;
        ArrayList<String> content;

        boolean isEditTitle = false;
        String oldNoteTitle = "";

        // Retrieve note
        try {
            oldNote = notebook.getNote(index);
            content = oldNote.getContent();
        } catch (IndexOutOfBoundsException exception) {
            return Formatter.formatString(NOTE_DOES_NOT_EXIST_MESSAGE);
        }

        // Set title
        if (!newNote.getTitle().isBlank()) {
            if (newNote.getTitle().equals(oldNote.getTitle())) {
                return Formatter.formatString(SAME_NOTE_TITLE_UNSUCCESSFUL_MESSAGE);
            }
            if (notebook.getNote(newNote.getTitle()))  {
                return Formatter.formatString(NOTE_EXIST_MESSAGE);
            }
            isEditTitle = true;
            oldNoteTitle = oldNote.getTitle();
            oldNote.setTitle(newNote.getTitle());
        }

        // Set new lines
        if (!addLists.isEmpty()) {
            for (Map.Entry<Integer, String> entry : addLists.entrySet()) {
                if (entry.getKey() < content.size()) {
                    content.add(entry.getKey(), entry.getValue());
                } else {
                    return Formatter.formatString(INVALID_LINE_UNSUCCESSFUL_MESSAGE);
                }
            }
            oldNote.setContent(content);
        }

        // Edit lines
        if (!editLists.isEmpty()) {
            for (Map.Entry<Integer, String> entry : editLists.entrySet()) {
                if (entry.getKey() < content.size()) {
                    content.set(entry.getKey(), entry.getValue());
                } else {
                    return Formatter.formatString(INVALID_LINE_UNSUCCESSFUL_MESSAGE);
                }
            }
            oldNote.setContent(content);
        }

        // Delete lines
        if (!deleteLists.isEmpty()) {
            for (int key : deleteLists.keySet()) {
                if (key < content.size() && content.size() != 1) {
                    content.remove(key);
                } else {
                    return Formatter.formatString(INVALID_LINE_UNSUCCESSFUL_MESSAGE);
                }
            }
            oldNote.setContent(content);
        }

        // Edit tags
        if (!newNote.getTags().isEmpty()) {
            tagManager.tagAndUntag(oldNote, newNote.getTags(), "", "");
        }

        // Add extra content
        if (isInput) {
            content.addAll(inputContent());
            oldNote.setContent(content);
        }

        try {
            storageManager.saveAllNoteDetails(false);
            storageManager.saveNoteContent(oldNote, false);

            if (isEditTitle) {
                storageManager.deleteNoteContentFile(oldNoteTitle, false);
            }
        } catch (IOException | SystemException exception) {
            return Formatter.formatString(FILE_WRITE_UNSUCCESSFUL_MESSAGE);
        }

        notebook.setNotes(index, oldNote);
        return Formatter.formatNote(EDIT_NOTE_SUCCESSFUL_MESSAGE, oldNote);
    }
}

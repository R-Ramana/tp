---
layout : page
title : Developer Guide
---

## Table of Contents
#### [1. Introduction](#intro)
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.1 Setting Up](#setup)
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.2 Project Management & Development Practices](#management)
#### [2. Design & Implementation](#design)
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.1 Architecture Overview](#overview)
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.2 NotUS](#notus)
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.3 Parser & ParserManager](#parserManager)
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.4 Commands](#commands)
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.5 Notebook](#note)
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.6 Timetable](#event)
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.7 Tags](#tag)
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.8 Storage](#storage)
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.9 User Interface](#ui)
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.10 System Exception](#exception)
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.11 Usage of External Libraries](#color)
#### [3. Product Scope](#scope)
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[3.1 Target User Persona](#userpersona)
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[3.2 Target User Profile](#userprofile)
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[3.3 Value Proposition](#value)

<div style="page-break-after: always;"></div>

#### [4. User Stories](#userstories)
#### [5. Non-Functional Requirements](#nfr)
#### [6. Glossary](#gloss)
#### [7. Instructions for Manual Testing](#testinstr)
#### [8. Appendix](#appendix)
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[8.1 List of ParseCommand Classes](#parseXYZCommands)
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[8.2 List of Command Classes](#XYZCommands)

<br>

## <a id="intro">1. Introduction</a>

NotUS is a quick and simple, Command Line Interface (CLI) based, note-taking application for keyboard-inclined users. NotUS allows for users to categorize notes by tagging as well as pinning the more important notes. NotUS is also designed to assist in planning timetables to highlight possible clashes.

This document describes the design, implementation and architecture of NotUS. The aim of this developer guide is to get developers and potential contributors familiarised with the design and implementation of NotUS. It is assumed that the reader has some basic understanding of UML Notations. If you do not possess such knowledge, this document is probably not meant for you. Please access the [User Guide](https://github.com/AY2021S1-CS2113-T13-1/tp/blob/master/docs/UserGuide.md) instead.

#### <a id="setup"><ins>1.1 Setting Up</ins></a>

**Prerequisites:**
* JDK 11
* IntelliJ IDE

Fork this repo and clone it onto your local machine.
Import the project as a **Gradle project**.
Ensures that you are using the correct JDK version (For this project we are using JDK 11).

For a more detailed set of instructions, please refer to the [following guide](https://github.com/AY2021S1-CS2113-T13-1/tp/blob/master/README.md).

<div style="page-break-after: always;"></div>

#### <a id="management"><ins>1.2 Project Management and Development Practices</ins></a>

Please refer to the [Development Practices Guide](DevelopmentPractices.md) for the Software Development practices used in the project. The document serves to inform on the Project Management frameworks used in the project.

## <a id="design">2. Design & Implementation</a>

This section seeks to explain the high-level design of the application. Given below is a quick overview of each component and the explanation of the design architecture in greater detail. NotUS is the main class of the application, and handles the initializing and execution of the appropriate classes. <br>
Diagrams found in our documentation were generated using <a href="https://plantuml.com/">PlantUML</a> and references were made to <a href="https://github.com/se-edu/addressbook-level2/tree/master/src/seedu/addressbook">addressbook-level2</a> for the structure of the classes and packages. The structures have been modified to meet the needs of our application.

#### <a id="overview"><ins>2.1 Architecture Overview</ins></a>

<p align="center">
  <img alt="NotUS Architecture Overview" src="diagrams/out/Architecture_OverviewV3.png" />
  <br><em>Figure 1</em>
</p>

Figure 1 depicts the architecture design of NotUS. The main components of NotUS are:

<div style="page-break-after: always;"></div>

1. `InterfaceManager`: Manages the user input as well as the message output from application.
1. `ParserManager`: Creates a suitable parser, based on the command, to make sense of user message. The respective parsers then make sense of the information and calls the respective commands.
1. `Command`: Executes the necessary tasks, depending on the respective command calls.
1. `TagManager`: Stores and manages the creation and deletion of tags and other tag-related functionality.
1. `Timetable`: Stores and manages the creation and deletion of events and other event-related functionality.
1. `Notebook`: Stores and manages the creation and deletion of notes and other note-related functionality.
1. `StorageManager`: Manages the loading of existing saved files and exporting of data to human-editable files.

#### <a id="notus"><ins>2.2 NotUS</ins></a>

NotUS manages the flow of the application. On launch, it will create the necessary components, as listed above and then attempts to load any existing saved files into the application. Subsequently, it will accept and interpret the user input and execute the commands accordingly. Figure 2 below depicts the main flow of the application.

<p align="center">
  <img alt="NotUS" src="diagrams/out/Notus.png" />
  <br><em>Figure 2</em>
</p>

💡 The lifeline for Parser and Command should end at destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram. This applies to the rest of the sequence diagrams in the document. <br>
💡 Due to a limitation of PlantUML, some of the sequence diagrams may have the destroy marker (X) immediately at the end of the activation bar when there should be a gap before the object is deleted.

<div style="page-break-after: always;"></div>

#### <a id="parserManager"><ins>2.3 Parser & ParserManager</ins></a>

The ParserManager manages the creation of specific parser objects based on the type of command. The parser then makes sense of the user input and calls the respective commands into action. The class diagram is as follows.

<p align="center">
  <img alt="ParserManagerClass" src="diagrams/out/ParserManagerClass.png" />
  <br><em>Figure 3</em>
</p>

💡 Note that variables and methods in the Command class is empty as it will be covered under [Commands](#commands). <br>
💡 For a full list of ParseXYZCommand, see [Appendix 8.1 List of ParseCommand Classes](#parseXYZCommands)

1. The ParserManager receives the user input message as a whole.
1. Interprets the type of command and creates the respective parser for each command.
1. The parser then splits the message to identify all the parameters provided.
1. Creates and returns the Command class respectively.

The sequence diagram is as follows.

<p align="center">
  <img alt="Parser" src="diagrams/out/Parser.png" />
  <br><em>Figure 4</em>
</p>

💡 Note that the alternate paths in the sequence diagram above are not exhaustive. There is an alternate path for each unique command. As there are various paths, they are omitted from the diagram. The Command object in the diagram is used to represent a generic Command object that is created through the Parser. Refer to Figure 5 for more details.

 <p align="center">
   <img alt="AddNoteParser" src="diagrams/out/AddNoteParser.png" />
   <br><em>Figure 5</em>
 </p>

Based on the user input, the Parser handles and creates the corresponding Command object.

#### <a id="commands"><ins>2.4 Commands</ins></a>

The Command classes update the Notebook and Timetable accordingly. The class diagram is as follows.

<p align="center">
  <img alt="CommandClass" src="diagrams/out/CommandClass.png" />
  <br><em>Figure 6</em>
</p>

💡 Different XYZCommand has different additional variables which are omitted in Figure 6. <br>
💡 For a full list of XYZCommand classes, see [Appendix 8.2 List of Command Classes](#XYZCommands)

<!--
Command used:

1. `help`: Shows a list of all the commands that the user can enter.
1. `add-n`: Adds a new note to the list of note items.
1. `list-n`: Shows a list of all the notes in the notebook.
1. `view-n`: View selected note
1. `edit-n`: Edits an existing note.
1. `find-n`: Finds the notes and return a list of notes that contain the keyword(s) in the title.
1. `pin-n`: Pins a note to the top of the list.
1. `archive-n`: Archives a note.
1. `unarchive-n`: Unarchive a note.
1. `delete-n`: Deletes an existing note.
1. `create-t`: Create multiple tags.
1. `list-t`: Shows a list of tags that have been created.
1. `tag-n`: Tags or untags a note by its given tag name.
1. `tag-e`: Tags or untags an event by its given tag name.
1. `delete-t`: Deletes a tag from the list of tags and remove the tag from the related notes and events.
1. `add-e`: Adds an event to the list.
1. `edit-e`: Edits an existing event in the event list/timetable.
1. `list-e`: Display the module timetable on the current day.
1. `remind-e`: Reminds the specified event from the timetable.
1. `delete-e`: Adds a new item to the list of todo items.
1. `exit`: Exits the program.
-->

The following are some examples of the different type of Command Classes and its flow.

<div style="page-break-after: always;"></div>

**AddNoteCommand**

Command used to add notes into the notebook.

1. Created by the ParseAddNoteCommand with the note object and all the information.
1. Obtain content input into note.
1. Add the note into the notebook.
1. Save the note's tag(s) into TagManager.
1. Save the data into a text file.
1. Returns the formatted result string to be displayed.

<p align="center">
   <img alt="AddNote_Sequence" src="diagrams/out/AddNote_Sequence.png"/>
   <br><em>Figure 7</em>
</p>

**PinCommand**

Command used to pin/unpin notes.

1. Created by the ParsePinCommand.
1. Gets the note that is referenced either by title or index.
1. Toggles the pinned status of the specified note. 
1. Returns the formatted the title as well as the pinned status of the note to be displayed.

<p align="center"> 
   <img alt="PinCommand" src="diagrams/out/PinCommand.png"/>
   <br><em>Figure 8</em>
</p>

**AddEventCommand**
1. Created by the ParseAddEventCommand with the event object and all the information.
1. Checks if the event's information is valid. If it is not, return the error.
1. Gets all events that clashes with this.
1. Saves the event into a text file.
1. Returns the result of the operation including warnings like clashes and duplicates.

<p align="center"> 
   <img alt="AddEventCommand" src="diagrams/out/AddEvent_Sequence.png"/>
   <br><em>Figure 9</em>
</p>

**RemindCommand**
1. Created by the ParseRemindCommand.
1. Calls the getReminders from Timetable.
1. Timetable then gets all events that is occurring 1 month from now.
1. Timetable then generates all reminders for all the events from the previous step.
1. Timetable returns all reminders that are to occur today.
1. Formatter formats the reminders.
1. Returns the result of the formatting.

<p align="center"> 
   <img alt="RemindCommand" src="diagrams/out/Remind_Sequence.png"/>
   <br><em>Figure 10</em>
</p>

<div style="page-break-after: always;"></div>

#### <a id="note"><ins>2.5 Notebook</ins></a>

The notebook component stores a catalogue of notes. On launch, an empty notebook will be created. The note will be created by the user.
Notebook handles adding, deleting, editing, finding, sorting, pinning and archiving of notes.

A single note holds information such as title, contents, tags, if its pinned and if its archived. Tag helps to sort user's notes as the program allows user to retrieve notes by tags.
Figure 11 below is a class diagram of the relationship between the Notebook, Note and Tags.

<p align="center">
   <img alt="NotebookObject" src="diagrams/out/NotebookObject.png"/>
   <br><em>Figure 11</em>
</p>

There are multiple overloaded methods. The uses are given below:

1. getNote(): is used to retrieve note by integer or note title. getNote is also used to retrieve archived note.
1. deleteNote(): is used to delete note by integer or note title.
1. getPinnedNotes(): is used to retrieve all pinned notes from the notebook or all pinned notes from a specific notebook.
1. getUnpinnedNotes(): is used to retrieve all unpinned notes from the notebook or all unpinned notes from a specific notebook.
1. getSortedList(): is used to sort the notebook alphabetically or sort specified notebook alphabetically.
1. archiveNotes(): is used to archive note by integer or note title.
1. unarchiveNotes(): is used to unarchive note by integer or note title.

The rationale for overloading such methods are given below:<br>

**1. getNote()**<br>
- There are a total of 3 getNote() methods.
    1. First one takes in an `int` index as an argument.
    2. Second takes in a `String` of the note title.
    3. The third takes in a `String` of the note title, and a `boolean` isArchive.  

- getNote(`int`) returns the Note that is in the position of specified index within the default list of notes.

<div style="page-break-after: always;"></div>

- getNote(`String`) checks if the note of the specified title exists in the default list of notes and returns a `boolean` value.
- getNote(`String`, `boolean`) checks if the note of the specified title exists and returns a `boolean` value. The `boolean` acts as a flag to determine which of the list of notes (default/archived), will be streamed and filtered.

**2. getPinnedNotes()**<br>
- There are a total of 2 getPinnedNotes() methods.
    1. First one takes no arguments.
    2. Second takes in an `ArrayList<Note>` representing the ArrayList to be filtered.

- getPinnedNotes() returns an `ArrayList<Note>` containing all the pinned notes found in the default list of notes.
- getPinnedNotes(`ArrayList<Note>`) returns an `ArrayList<Note>` containing all the pinned notes found in the `ArrayList<Note>` parameter that was passed in. Used when the user wants to filter the list-n search with tags. The ArrayList would only contain notes with the specific tags.

**3. getUnpinnedNotes()**<br>
- There are a total of 2 getUnpinnedNotes() methods.
    1. First one takes no arguments.
    2. Second takes in an `ArrayList<Note>` representing the ArrayList to be filtered.

- getUnpinnedNotes() returns an `ArrayList<Note>` containing all the unpinned notes found in the default list of notes.
- getUnpinnedNotes(`ArrayList<Note>`) returns an `ArrayList<Note>` containing all the unpinned notes found in the `ArrayList<Note>` parameter that was passed in. Used when the user wants to filter the list-n search with tags. The ArrayList would only contain notes with the specific tags.

**4. getSortedList()**<br>
- There are a total of 2 getSortedList() methods.
    1. First one takes in 2 `Boolean` parameters specifying if only pinned notes from the default list of notes are to be filtered, as well as a flag for the sort order.
    2. Second takes in 2 `Boolean` parameters specifying if only pinned notes are to be filtered, as well as a flag for the sort order. A third `ArrayList<Note>` parameter representing the ArrayList to be filtered is also taken in.
- getSortedList(`Boolean`, `Boolean`) returns an `ArrayList<Note>` containing all notes, or just pinned notes found in the default list of notes, in the specified sort order.

<div style="page-break-after: always;"></div>

- getSortedList(`Boolean`, `Boolean`, `ArrayList<Note>`) returns an `ArrayList<Note>` containing all notes, or just pinned notes found in the `ArrayList<Note>` parameter that was passed, in the specified sort order. Used when the user wants to filter the list-n search with tags. The ArrayList would only contain notes with the specific tags.

**5. archiveNotes()**<br>
- There are a total of 2 archiveNotes() methods.
    1. First one takes in an `int` index as an argument.
    2. Second takes in a `String` of the note title.

- archiveNotes(`int`) returns a String value of the note title that is in the position of specified index, and that is being archived.
- archiveNotes(`String`) checks if the note of the specified title exists in the default list of notes and returns a `boolean` value. If the note exists, it will be archived.

**6. unarchiveNotes()**<br>
- There are a total of 2 unarchiveNotes() methods.
    1. First one takes in an `int` index as an argument.
    2. Second takes in a `String` of the note title.

- unarchiveNotes(`int`) returns a String value of the note title that is in the position of specified index, and that is being unarchived.
- unarchiveNotes(`String`) checks if the note of the specified title exists in the archived list of notes and returns a `boolean` value. If the note exists, it will be unarchived.

#### <a id="event"><ins>2.6 Timetable</ins></a>

Timetable handles adding, deleting and getting all instances of stored events in a given time period. All scheduling, retrieving and processing of events are done here

The timetable component stores an array of all events and 5 different arrays of recurring events split by frequency of re-occurrence. On launch, an empty timetable will be created. All stored events will be loaded via the StorageManger. 

 <p align="center">
   <img alt="TimetableClassDiagram" src="diagrams/out/TimetableClass.png" />
   <br><em>Figure 12</em>
 </p>
 
 Key Methods Provided:
 1. getNonRecurringEvents(): Gets all non-recurring events that occurs between the start and end date parameters.
 1. getAllRecurringEvents(): Gets all recurring events that occurs between the start and end date parameters. Includes repeated events for those that re-occurs.
 1. getEventSetReminder(): Gets all reminders from a provided set of events.
 1. getReminders(): Gets all reminders to occur today.
 1. getTimetable(): Gets all events to occur between the start and end date parameters. Returns a hashmap mapping the month to nested hashmap. The nested hashmap acts as a standard calendar where the keys are the day of the month and values are ArrayList of events occurring on that day.
 1. getMonthTimetable(): Wrapper around getTimetable for a specific month.
 1. getYearTimetable(): Wrapper around getMonthTimetable for a specific year.
 1. getClashingEvents(): Checks the input event to all other events in the timetable to check if the timing clashes. Uses getTimetable on that date of the input event.
 
 <div style="page-break-after: always;"></div>
 
 An event holds information about all information about start date time, end date time, name, reminders set and how often to re-occur.
 
 On re-occurrence, a new event will be instantiated for display on the timetable.
 
  <p align="center">
    <img alt="EventClassDiagram" src="diagrams/out/EventClass.png" />
    <br><em>Figure 13</em>
  </p>
  
  Event Class Key Methods Provided:
  1. getReminderDates(): Get an ArrayList of all reminders that this Event will generate.
  1. occursDuringEvent(): Returns true if another event is occurring during this event's time duration.
  1. equals(): Checks if the title of another event is the same and has a clashing time period. If both conditions are satisfied, return true.
  
<div style="page-break-after: always;"></div>
  
  RecurringEvent Class Key Methods Provided:
  1. timeStep(): Returns a LocalDateTime with one time step depending on what the event is.
  1. toReoccur(): Checks if this event is to reoccur on this date.
  1. getRecurrences(): Get an ArrayList of Events of all instances this event will reoccur during the specified time period.
  1. checkAfterEndRecurrences(): Checks if this Event should still reoccur as of this date.
  
  
#### <a id="tag"><ins>2.7 Tags</ins></a>

Figure 14 below denotes the class diagram for the TagManager and the Taggable Objects (Notes and Events).

<p align="center">
   <img alt="TaggableObject" src="diagrams/out/TaggableObject.png"/>
   <br><em>Figure 14</em>
</p>
 
💡 As the focus of this diagram is on Tag, TaggableObject and TagManager, the variables and methods of Notes and Events are omitted.
 
Notes and Events inherit from the abstract class, TaggableObject, and TagManager contains a map of individual unique tags to an ArrayList of TaggableObjects. The TagManager also handles the creation, deletion as well as the tagging and untagging of tags from notes or events.

<div style="page-break-after: always;"></div>

#### <a id="storage"><ins>2.8 Storage</ins></a>

The StorageManager saves and loads data to text files. On launch, the storage manager checks for existing directories that may contain previously saved data, otherwise it creates the necessary directories. Following that, it will load the previously saved notes and events from the text files into NotUS.Below is the class diagram representing the relationship between the StorageManager, Timetable, Notebook, TagManager and ParserManager.

<p align="center">
   <img alt="StorageManagerClassDiagram" src="diagrams/out/StorageManager.png"/>
   <br><em>Figure 15</em>
</p>

While loading information is passed to the parser manager to prepare the information to be added. Following that, the respective Add Command will be called to add the event/note to the program. Below is the sequence for loading the notes and events when the program first starts up. 

<p align="center">
   <img alt="StorageManagerObjectDiagram" src="diagrams/out/StorageManagerObject.png"/>
   <br><em>Figure 16</em>
</p>

During the program, as changes are made to the data, the storageManager saves the data to the hard disk. In case the user force closes the program, the updated data is saved. The sequence diagram below is an example of a note being archived. 

<p align="center">
   <img alt="StorageManagerSaveDuringDiagram" src="diagrams/out/StorageManagerSaveDuring.png"/>
   <br><em>Figure 17</em>
</p>

When exiting the program, the storageManager saves all the data to the hard disk, in case the user tampers with the txt files while the program is ongoing. Hence, saving the latest version of the data. Below is the sequence diagram of the final saving procedure.

<p align="center">
   <img alt="StorageManagerSaveEndDiagram" src="diagrams/out/StorageManagerSaveEnd.png"/>
   <br><em>Figure 18</em>
</p>

#### <a id="ui"><ins>2.9 User Interface</ins></a>

The InterfaceManger receives the input from the user which is then processed by ParserManager, as well as printing the output. The class diagram is as follow.

<p align="center">
   <img alt="InterfaceManager" src="diagrams/out/InterfaceManagerClass.png"/>
   <br><em>Figure 19</em>
</p>

<div style="page-break-after: always;"></div>

The Formatter class handles the formatting of the Note(s), Event(s) and message(s) into a String which is then passed to InterfaceManager to be printed out through NotUS. Any changes to the layout or information to display will be done in this class. This class only contains static methods to eliminate the need of a Formatter object.

<p align="center">
   <img alt="Formatter" src="diagrams/out/Formatter.png"/>
   <br><em>Figure 20</em>
</p>

There are few overloaded functions such as formatNotes, formatTimetable and formatString. These functions are overloaded due to the different format that is to be printed for the different Commands.

<div style="page-break-after: always;"></div>

A notable function is the `encloseRow(String)` which is a recursive function. It takes in the string to be formatted and split the string if it exceeds the maximum character display length, which is then recursively formatted. One additional consideration to take note of is the ANSI escape code for color as they have to be accounted when splitting the string as well as adding spaces to fill up the gap.

#### <a id="exception"><ins>2.10 System Exception</ins></a>

The System Exception Enumeration contains all the possible types of exception with specific messages.

<p align="center">
   <img alt="SystemExceptionEnum1" src="diagrams/out/SystemExceptionEnum1.png"/>
   <br><em>Figure 21</em>
</p>

<p align="center">
   <img alt="SystemExceptionEnum1" src="diagrams/out/SystemExceptionEnum2.png"/>
   <br><em>Figure 22</em>
</p>

💡 As there are various types of exception, the diagram is split into two.

#### <a id="color"><ins>2.11 Usage of External Libraries</ins></a>

This application uses 2 color libraries, <a href="https://github.com/dialex/JColor">JColor</a> and <a href="https://fusesource.github.io/jansi/">Jansi</a>, to print colored messages on the terminals using ANSI escape codes. While JColor itself is sufficient to colorize the strings, Windows 10 terminal, by default, **does NOT support** ANSI escape code. Hence, there was a need for the Jansi library to support ANSI escape codes on Windows.
  
<ins>Note on usage of JColor library:</ins>

IntelliJ's *'Dracula'* and *'High Contrast'* themes print white fonts as black and vice versa. Developers using either of the themes will have to change the white and black console color to reflect the correct color that is being printed. Instructions to do so are given below.

- Go under Settings -> Editor -> Color Scheme -> Console Colors -> ANSI colors -> Change the Foreground color for Black and White to the correct RGB value.

<div style="page-break-after: always;"></div>

Figure 23 below illustrates what you should see on your screen.

<p align="center">
  <img alt="Changing console color" src="diagrams/out/ConsoleColor.png" />
 <br><em>Figure 23</em>
</p>

<ins>Note on usage of Jansi library:</ins>

While Jansi provides support for Windows terminal to print colored texts, it does not work within IntelliJ IDEA console. Therefore, when running on IntelliJ console, comment out the following lines in NotUS.java main function:

 `AnsiConsole.systemInstall();`
 `AnsiConsole.systemUninstall();`
 
Remember to uncomment them when building jar files for release.

<div style="page-break-after: always;"></div>

## <a id="scope">3. Product Scope</a>

#### <a id="userpersona"><ins>3.1 Target User Persona</ins></a>

Jane Doe is a NUS undergraduate student who is in SOC/FOE and is having a hard time managing her responsibilities and extra curricular activities. She wants to have a convenient platform to take notes and categorize them according to her modules. She also wants to plan her time so she is more aware of her module schedule.

She also wants to be able to export the information so she is able to share them with whomever easily.


#### <a id="userprofile"><ins>3.2 Target User Profile</ins></a>

1. NUS students, specifically SOC and CEG students (herein referred to as students) who are comfortable and adept at using CLI.
2. Students who want to take notes and categorize them so they are not all over the place.
3. Students who are comfortable with CLI.


#### <a id="value"><ins>3.3 Value Proposition</ins></a>

A all-in-one solution for note-taking and managing your schedule. NotUS solves the following problems.

1. Lack of access to organizing schedule
1. Lack of access to organizing notes

<div style="page-break-after: always;"></div>

## <a id="userstories">4. User Stories</a>

| Version | Target User | Function/Feature | User's Benefit |
|--------|----------|---------------|------------------|
|v1.0| As a ... | I want to ... | So that I can ... |
|v1.0|CEG student|Keep track of my notes|Be organized and find notes easily|
|v1.0|Meticulous student|Categorize my notes by level of importance|Focus on the important topics|
|v1.0|Student who is overloading|Categorize my notes by module|Be more aware of which notes are necessary for the upcoming lessons|
|v1.0|Student who is more visual|Categorize my notes by colour (visible on CLI)|Be able to easily identify which module is which|
|v1.0|Meticulous student|Create daily and weekly task lists|Keep track of my work progress|
|v1.0|Forgetful student|To obtain reminders about my schedule for the day|So I do not forget what I have on for the day|
|v1.0|Busy student|Pin important notes, events and todo list|Focus on the important information|
|v1.0|CEG student|Be able to edit my notes|Update missing information|

<div style="page-break-after: always;"></div>

| Version | Target User | Function/Feature | User's Benefit |
|--------|----------|---------------|------------------|
|v1.0|CEG student|Be able to read my notes|To revise before exams|
|v1.0|CEG student|Be able to delete my notes|To clear up space and keep it more organized|
|v2.0|Outgoing student|Able to add my social events to the timetable|View all my upcoming events and classes|
|v2.0|Busy student|Be alerted if there are clashes in between my events and classes|Reschedule my plan|
|v2.0|Student leader|Be able to share certain events with others|Can get people to join events more conveniently|
|v2.0|CEG student|Archive old notes|Keep dashboard neat while allowing me to refer to old notes when necessary|
|v2.0|CEG student|Be able to import my notes|To make edits|
|v2.0|CEG student|Be able to export my notes|To share my notes with my peers|
|v2.0|CEG student|Be able to import my timetable/events|To make adjustments to my schedule|
|v2.0|CEG student|Be able to export my timetable/events|To share my schedule with my peers|

<div style="page-break-after: always;"></div>

## <a id="nfr">5. Non-Functional Requirements</a>

| Requirement Type | Description |
|------------------|-------------|
|Constraint|Single user product|
|Performance|Software should not be dependent on a remote server|
|Performance|Software should not exceed 100Mb for JAR file and 15MB per PDF file|
|Quality|Users should prefer CLI/Typing|
|Technical|Must have Java 11 installed|
|Technical|No DBMS, all data to be stored locally|
|Technical|Data stored must be in human-editable files|
|Technical|Programme should be platform independent|
|Technical|Programme should work without an installer|

<div style="page-break-after: always;"></div>

## <a id="gloss">6. Glossary</a>

* *CLI* - Command Line Interface
* *DBMS* - Database Management System
* *UML* - Unified Modelling Language
* *PERT* -  Program Evaluation Review Technique
* *IntelliJ* - An Integrated Development Environment (IDE) developed by [JetBrains](https://www.jetbrains.com/idea/) for developing computer software.
* *SOC* - School of Computing
* *FOE* - Faculty of Engineering
* *CEG* - Computer Engineering

## <a id="testinstr">7. Instructions for Manual Testing</a>

1. Download the jar file and copy it into an empty folder.
1. Open a new terminal window and navigate to the same directory where the notus.jar is located. As a shortcut if you are on windows, you can open the folder where the notus.jar is located > click on the address bar > type `cmd` > press enter on your keyboard.
1. Enter the command `java -jar notus.jar` into the terminal window to launch the application. The application should now be running.
1. Enter the command `help` to get a list of all available commands and its usages.
1. For a detailed list on the command features, refer to the [user guide](https://github.com/AY2021S1-CS2113-T13-1/tp/blob/master/docs/UserGuide.md#features).
1. Simply enter `exit` to terminate and exit the application.

<div style="page-break-after: always;"></div>

## <a id="appendix">8. Appendix</a>

#### <a id="parseXYZCommands"><ins>8.1 List of ParseCommand Classes</ins></a>

| ParseXYZCommands |  Functions  |
|------------------|-------------|
|ParseAddNoteCommand|Creates a AddNoteCommand|
|ParseAddEventCommand|Creates a AddEventCommand|
|ParseEditNoteCommand|Creates a EditNoteCommand|
|ParseEditEventCommand|Creates a EditEventCommand|
|ParseDeleteNoteCommand|Creates a DeleteNoteCommand|
|ParseDeleteEventCommand|Creates a DeleteEventCommand|
|ParseListNoteCommand|Creates a ListNoteCommand|
|ParseListEventCommand|Creates a ListEventCommand|
|ParseFindCommand|Creates a FindCommand|
|ParsePinCommand|Creates a PinCommand|
|ParseViewNoteCommand|Creates a ViewNoteCommand|
|ParseArchiveOrUnarchiveNoteCommand|Creates either ArchiveNoteCommand or UnarchiveNoteCommand|
|ParseCreateOrDeleteTagCommand|Creates a CreateTagCommand or DeleteTagCommand|
|ParseTagCommand|Creates a TagNoteCommand or TagEventCommand|

<div style="page-break-after: always;"></div>

#### <a id="XYZCommands"><ins>8.2 List of Command Classes</ins></a>

The list of Command classes is as follow:

1. AddNoteCommand
1. AddEventCommand
1. EditNoteCommand
1. EditEventCommand
1. DeleteNoteCommand
1. DeleteEventCommand
1. ListNoteCommand
1. ListEventCommand
1. PinCommand
1. FindCommand
1. ArchiveNoteCommand
1. UnarchiveNoteCommand
1. ViewNoteCommand
1. RemindCommand
1. CreateTagCommand
1. DeleteTagCommand
1. ListTagCommand
1. TagNoteCommand
1. TagEventCommand
1. HelpCommand
1. ExitCommand
1. IncorrectCommand

---
layout : page
title : Jun Xiang - Project Portfolio Page
---

<!-- @@author chongjx -->
**Overview**

NotUS is a quick and simple, Command Line Interface (CLI) based, note-taking application for keyboard-inclined users. NotUS allows for users to categorize notes by tagging as well as pinning the more important notes. NotUS is also designed to assist in planning timetables to highlight possible clashes.
- Co-team lead: Responsible for overall project coordination alongside Ramana.
- Software Architect: Designed the software  architecture of the programme with Nazryl. Ensures the logic of the code structure. 
- Integration: Worked with Ramana for the versioning of the code, maintaining the code repository, integrating various parts of the software to create a whole.

**Summary of Contributions**

*Features implemented*

1\. Implemented `TaggableObject` class which is inherited by `Note` and `Event`, which can have multiple tags and different Taggable objects can share same tag(s).

2\. Implemented `Tag` class which contains the tag name and its color to differentiate tags.

3\. Implemented `TagManager` class which manages all the tags in application. A hashmap is used where each unique tag is mapped to a Taggable object.

4\. Implemented create tag, delete tag, as well as tag or untag taggable objects.

5\. Implemented `ParserManager` class which manages the creation of different parser objects to parse the user input to the required parameters.

6\. Implemented `SystemException` class which extends from `Exception` class. Enumeration for all possible types of exception that may occur with a unique string as its message.

7\. Implemented `Formatter` class which formats Note, Event and String before printing.
    
*Code contributed*: [RepoSense link](https://nus-cs2113-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=chongjx&sort=groupTitle&sortWithin=title&since=2020-09-27&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

<div style="page-break-after: always;"></div>

*Project management*: Set up the framework of the programme for team members to work on, managed code cleanup before every release, managed releases `v1.0`, `v2.0` and `v2.1` on GitHub.

*Contributions to User Guide*

[Section 3.11 Create Tags](https://ay2021s1-cs2113-t13-1.github.io/tp/UserGuide.html#create-t) <br>
[Section 3.12 List Tags](https://ay2021s1-cs2113-t13-1.github.io/tp/UserGuide.html#list-t) <br>
[Section 3.13 Tag/Untag Notes](https://ay2021s1-cs2113-t13-1.github.io/tp/UserGuide.html#tag-n) <br>
[Section 3.14 Tag/Untag Events](https://ay2021s1-cs2113-t13-1.github.io/tp/UserGuide.html#tag-e) <br>
[Section 3.15 Delete Tags](https://ay2021s1-cs2113-t13-1.github.io/tp/UserGuide.html#delete-t) <br>

*Contributions to Developer Guide*

[Section 2.1 Architecture Overview](https://ay2021s1-cs2113-t13-1.github.io/tp/DeveloperGuide.html#overview) <br>
[Section 2.3 Parser & ParserManager](https://ay2021s1-cs2113-t13-1.github.io/tp/DeveloperGuide.html#parserManager) <br>
[Section 2.4 Commands](https://ay2021s1-cs2113-t13-1.github.io/tp/DeveloperGuide.html#commands) (Class diagram of Command Class, Figure 6) <br>
[Section 2.7 Tags](https://ay2021s1-cs2113-t13-1.github.io/tp/DeveloperGuide.html#tag) <br>
[Section 2.9 User Interface](https://ay2021s1-cs2113-t13-1.github.io/tp/DeveloperGuide.html#ui) <br>
[Section 2.10 SystemException](https://ay2021s1-cs2113-t13-1.github.io/tp/DeveloperGuide.html#exception) <br>
[Section 2.11 Usage of External Libraries](https://ay2021s1-cs2113-t13-1.github.io/tp/DeveloperGuide.html#color) <br>
[Section 8.1 List of ParseCommand Classes](https://ay2021s1-cs2113-t13-1.github.io/tp/DeveloperGuide.html#parseXYZCommands) <br>
[Section 8.2 List of Command Classes](https://ay2021s1-cs2113-t13-1.github.io/tp/DeveloperGuide.html#XYZCommands) <br>

*Community*

1\. Reviewed most of the PRs to ensure the Classes and functions follow the project architecture as well code logic of the programme. Reviewed a total of 66 PRs out of 114 PRs, with [167 comments](https://nus-cs2113-ay2021s1.github.io/dashboards/contents/tp-comments.html).

2\. Reported bugs and suggested fixes for team members. ([#115](https://github.com/AY2021S1-CS2113-T13-1/tp/issues/115), [#135](https://github.com/AY2021S1-CS2113-T13-1/tp/issues/135), [#148](https://github.com/AY2021S1-CS2113-T13-1/tp/issues/148), [#159](https://github.com/AY2021S1-CS2113-T13-1/tp/issues/159), [#168](https://github.com/AY2021S1-CS2113-T13-1/tp/issues/168), [#175](https://github.com/AY2021S1-CS2113-T13-1/tp/issues/175) and [#199](https://github.com/AY2021S1-CS2113-T13-1/tp/issues/199))

3\. Contributed to forum discussions on usage of Jcolor and jansi libraries. ([#86](https://github.com/nus-cs2113-AY2021S1/forum/issues/86) and [#93](https://github.com/nus-cs2113-AY2021S1/forum/issues/93))

*Tools*: Integrated a third party libraries - [(JColor)](https://github.com/dialex/JColor) [(#37)](https://github.com/AY2021S1-CS2113-T13-1/tp/pull/37) and [(jansi)](https://fusesource.github.io/jansi) to the project [(#87)](https://github.com/AY2021S1-CS2113-T13-1/tp/pull/87).

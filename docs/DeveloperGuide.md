---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# HR-Nexus Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

HR Nexus is based on the Address Book Level 3 (AB3) project from the SE-EDU initiative. We thank the AB3 team for their work, which helped shape our project.

We also want to acknowledge the following tools and libraries that made HR Nexus possible:

- [AB3 Codebase](https://github.com/se-edu/addressbook-level3)
- JavaFX
- JUnit
- Jackson Library

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S2-CS2103T-T15-3/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S2-CS2103T-T15-3/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S2-CS2103T-T15-3/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S2-CS2103T-T15-3/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S2-CS2103T-T15-3/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S2-CS2103T-T15-3/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2425S2-CS2103T-T15-3/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S2-CS2103T-T15-3/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:
* Small-to-medium enterprises (SME) with limited or no dedicated HR personnel
* Need to manage a significant number of contacts efficiently
* Work with multiple employee records daily
* Need to track staff availability
* Prefer keyboard-driven productivity tools
* Value speed and organization in HR tasks

**Value proposition**: Manage contacts faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​    | I want to …​                    | So that I can…​                                                              |
|------|------------|---------------------------------|------------------------------------------------------------------------------|
| `* * *` | new user   | see usage instructions          | refer to instructions when I forget how to use the App                       |
| `* * *` | HR admin   | add new employee records        | maintain an up-to-date employee database                                     |
| `* * *` | HR admin   | edit new employee records       | maintain an up-to-date employee database                                     |
| `* * *` | HR admin   | delete employee records         | remove terminated employees from the employee database                       |
| `* * *` | HR admin   | mark attendance for employees   | ensure accurate payroll and monitor employees' productivity                  |
| `* * *` | HR admin   | add leave entries for employees | ensure all employees do not exceed the number of leaves they are entitled to |
| `* * *` | HR admin   | add tags to employees            | understand skillset of employees and plan for their training and development |
| `* * *` | HR admin   | clear all employee data         | reset the employee database system for new deployments                       |
| `* * *` | HR manager | view complete employee details  | evaluate the performance of employees                                        |
| `* *` | HR admin   | search employees by name        | locate details of employees more efficiently                                 |
| `*`  | HR admin   | sort persons by name            | locate a person easily                                                       |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `HR Nexus` and the **Actor** is the `user`, unless specified otherwise)

**Use Case: Add an employee**

**MSS**

1.  User enters employee details with all required fields.
2.  HR Nexus validates all fields.
3.  HR Nexus adds the new employee.

    Use case ends.

**Extensions**

* 2a. Missing required field.

    * 2a1. HR Nexus shows an error message.

        Use case resumes at step 1.

* 2b. Invalid field format.

    * 2b1. HR Nexus shows an error message.

        Use case resumes at step 1.

**Use case: Delete an employee**

**MSS**

1.  User requests to list employees.
2.  HR Nexus shows a list of employees.
3.  User specifies employee index to delete in the list.
4.  HR Nexus validates the existence of employee index.
5.  HR Nexus deletes the employee.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. Invalid index.

    * 3a1. HR Nexus shows an error message.

        Use case resumes at step 3.

**Use Case: Edit an employee**

**MSS**

1.  User requests to list employees.
2.  HR Nexus shows a list of employees.
3.  User specifies employee index and fields to update.
4.  HR Nexus updates specified fields.
5.  HR Nexus displays updated employee details in the list.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. Invalid index.

    * 3a1. HR Nexus shows an error message.

        Use case resumes at step 3.

* 3b. No field specified.

    * 3b1. HR Nexus shows an error message.

        Use case resumes at step 3.

**Use case: Add leave entry for employee**

**MSS**

1.  User specifies employee identifier, date range and reason
2.  HR Nexus validates existence of employee as well as format of date range and reason.
3.  HR Nexus adds leave entry for specified employee.

    Use case ends.

**Extensions**

* 2a. Invalid employee identifier.

    * 2a1. HR Nexus shows an error message.

        Use case resumes at step 1.

* 2b. Invalid starting date and ending date requirements.

    * 2b1. HR Nexus shows an error message.

        Use case resumes at step 1.

* 2c. Invalid reason.

    * 2c1. HR Nexus shows an error message.

        Use case resumes at step 1.

**Use case: Remove leave entry for employee**

**MSS**

1.  User specifies employee identifier and leave start date.
2.  HR Nexus validates existence of leave for specified employee.
3.  HR Nexus removes leave entry for specified employee.

    Use case ends.

**Extensions**

* 2a. Invalid employee identifier.

    * 2a1. HR Nexus shows an error message.

      Use case resumes at step 1.

* 2b. No matching leave.

    * 2b1. HR Nexus shows an error message.

        Use case resumes at step 1.

**Use case: Marks attendance for employee**

1.  User enters attendance command with absent NRICs.
2.  HR Nexus validates NRIC format.
3.  HR Nexus marks specified employees as absent.
4.  All other employees are marked present.

    Use case ends.

**Extensions**

* 2a. Invalid NRIC format.

    * 2a1. HR Nexus shows an error message.

        Use case resumes at step 1.

* 2b. Some NRICs do not match with any existing employee.

    * 2b1. HR Nexus ignores unmatching NRICs.

        Use case resumes at step 3.

* 2c. Duplicated NRICs found.

    * 2c1. HR Nexus keeps only a singular instance of the duplicated NRICs.

        Use case resumes at step 3.

**Use case: Add employee tag**

**MSS**

1.  User specifies employee index and tag.
2.  HR Nexus validates the existence of employee index and tag, as well as format of tag.
3.  HR Nexus adds tag on employee's profile.

    Use case ends.

**Extensions**

* 2a. Tag already exists.

    * 2a1. HR Nexus shows an error message.

        Use case ends.

* 2b. Invalid employee index.

    * 2b1. HR Nexus shows an error message.

        Use case resumes at step 1.

* 2c. Invalid tag format.

    * 2c1. HR Nexus shows an error message.

        Use case resumes at step 1.

**Use case: Remove employee tag**

**MSS**

1.  User specifies employee index and tag.
2.  HR Nexus validates the existence of employee and employee tag as well as format of tag.
3.  HR Nexus adds tag on employee's profile.

    Use case ends.

**Extensions**

* 2a. Tag does not exist.

    * 2a1. HR Nexus shows an error message.

        Use case ends.

* 2b. Invalid employee index.

    * 2b1. HR Nexus shows an error message.

        Use case resumes at step 1.

* 2c. Invalid tag format.

    * 2c1. HR Nexus shows an error message.

        Use case resumes at step 1.

**Use case: View employee details**

**MSS**

1.  User specifies employee index.
2.  HR Nexus validates the existence of employee index.
3.  HR Nexus displays employee details.

    Use case ends.

**Extensions**

* 2a. Invalid employee index.

    * 2a1. HR Nexus shows an error message.

      Use case resumes at step 1.

**Use case: Find employee by name**

**MSS**

1.  User specifies search keywords.
2.  HR Nexus displays employees whose names contain any of the specified keywords.
3.  HR Nexus maintains filtered list of employees until next list/find command.

    Use case ends.

**Extensions**

* 1a. No keywords provided.

    * 1a1. HR Nexus shows an error message.

        Use case resumes at step 1.

**Use case: Sort employee**

**MSS**

1.  User specifies which field to be used for sorting and direction of sorting.
2.  HR Nexus displays employees sorted as specified.
3.  Sort order persists until next sort command.

    Use case ends.

**Use case: View help**

**MSS**

1.  User enters help command.
2.  HR Nexus pops a help window with a URL link to User Guide.
3.  User can browse through available commands.

    Use case ends.

**Use case: Clear all data**

**MSS**

1.  User enters clear command.
2.  HR Nexus removes all employee records.
3.  HR Nexus shows empty list.

    Use case ends.

*{More to be added}*

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Open Command Prompt/Terminal

      Windows: Press Win + R, type cmd, and hit Enter.

      Mac/Linux: Open Terminal.

   1. Navigate to the JAR Folder: </br>
      `cd "path/to/folder/containing/jar"` </br>
      Example: </br>
      `cd "C:\Users\YourName"`

   1. Launch the Application: `java -jar HRNexus.jar` </br>
      Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app: `java -jar HRNexus.jar`<br>
       Expected: The most recent window size and location is retained.

1. Exit program

   1. Enter the exit command. The window closes.
   
   1. Launch the Application: `java -jar HRNexus.jar`<br>
   Expected: Shows the GUI with contacts. The most recent window size and location is
      retained.

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.'

   1. Test case: `delete x` (where x is larger than the list size)<br>
      Expected: No person is deleted. Error details of invalid index shown in the status message. Status bar remains the same. 
   '
   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete y`, `...` (where y is not a positive integer)<br>
      Expected: Similar to previous.

### Saving data

1. Dealing with missing data files

   1. Prerequisites: Executed the HRNexus jar file.

   1. Open the home holder of HRNexus. Delete the Data file

   1. Launch the Application: `java -jar HRNexus.jar`<br>
      Expected: Shows the GUI with the sample contacts. The most recent window size and location is
      retained.

1. Dealing with corrupted data files

   1. Prerequisites: Executed the HRNexus jar file.

   1. Open the home holder of HRNexus. Open the Data folder. Open the addressbook.json.

   1. In the open addressbook.json, modify the first "persons" at line 1 to "ersons". Close and Save the file.
   
   1. Launch the Application: `java -jar HRNexus.jar`<br>
      Expected: Shows the GUI with empty contacts. The most recent window size and location is retained.

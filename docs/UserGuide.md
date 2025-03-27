---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# HR Nexus User Guide

HR Nexus is a desktop app designed for SMEs to simplify employee management, especially for businesses with limited or 
no dedicated HR personnel. It helps you keep track of essential employee records in one place, including personal details,
attendance records, and leave history.

With a clean and intuitive interface combining both Command Line (CLI) efficiency and Graphical User Interface (GUI), 
HR Nexus makes it easy to stay organized without the overhead of a full-fledged HR system. It is a lightweight, practical
solution to help you lay the foundation for a more efficient HR process in the future.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Get started in minutes

#### Prerequisites
- **Java 17 or later**
  - Verify with `java -version` in your terminal.
    - You should see something like `java version "17.X.X"` in the output.
  - If you don't have Java 17 installed, you can download and install it [here](https://www.oracle.com/java/technologies/downloads/#java17).
  - For Mac users, ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).


#### Steps
1. Download the `HRNexus.jar` file
   -  Click [here](https://github.com/AY2425S2-CS2103T-T15-3/tp/releases) to download the latest version
2. Run the app
   - Move the downloaded `HRNexus.jar` file to a folder of your choice.
   - Open your preferred terminal and navigate to the same folder using:
       ```ps
       cd /path/to/folder
       ```
   - Then, start HR Nexus with:
       ```properties
       java -jar HRNexus.jar
       ```
- A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.
    ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * Lists all employees:
      ```properties
      list
      ```

   * Add an employee named **John Doe**:
      ```properties
      add /name John Doe /nric S1234567A /email johnd@example.com /phone 98765432 /address John street, block 123, #01-01 /hire 2025-03-05
      ```

   * Delete the first employee:
      ```properties
      delete 1
      ```

   * Deletes all employees:
      ```properties
      clear
      ```

   * Exits the app:
      ```properties
      exit
      ```

1. Refer to the [Features](#features) below for more details on each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" header="#### Notes on the command format" icon-size="2x" seamless>

* Words in **UPPER_CASE** are the **parameters** to be supplied by the user.
  - For example, the `add` command requires these fields and parameters:
  
    ```properties
    add /name NAME /nric NRIC /phone PHONE_NUMBER /email EMAIL /address ADDRESS /hire DATE
    ```
  - The **parameters** are `NAME`, `NRIC`, `PHONE_NUMBER`, `EMAIL`, `ADDRESS`, and `DATE`.
<p></p>

* **Parameters** followed by `…`​ accept multiple space-separated values.
  - For example, the `attendance` command can be used in the following ways:
    - If `NRIC` is not provided, it marks all employees as present.
    - If one or more `NRIC` are provided, it marks the specified employees as absent.
  
      ```properties
      attendance /absent 
      attendance /absent S1234567A
      attendance /absent S1234567A S2345678B
      ```

  
* **Parameters** are **not positional** and **can be given in any order**.
  - The following commands are equivalent:
    ```properties
    add /name NAME /nric NRIC /phone PHONE_NUMBER /email EMAIL /address ADDRESS /hire DATE
    add /hire DATE /addre ADDRESS /email EMAIL /phone PHONE_NUMBER /nric NRIC /name NAME
    ```

* **Parameters in square brackets are optional**.
  - For example, the `edit` command takes in optional parameters:
  
     ```properties
     edit INDEX [/name NAME] [/nric NRIC] [/phone PHONE] [/email EMAIL] [/address ADDRESS] [/hire DATE]
     ```
    - Only the `INDEX` is **compulsory**, in addition to **at least one** of the optional parameters.
<p></p>

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  - If the command specifies `help 123`, it will be interpreted as `help`.
<p></p>

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>
<br>

<panel type="seamless" header="### Viewing help: `help`" expanded no-close no-switch>

##### Shows a message explaining how to access the help page.
**Format:**
```properties
help
```
**Output:**
![help message](images/helpMessage.png)
</panel>

<panel type="seamless" header="### Adding an employee: `add`" expanded no-close no-switch>

##### Adds an employee to the system.

**Required fields:** `name`, `nric`, `phone`, `email`, `address`, `hire`.

**Format:** 
```properties
add /name NAME /nric NRIC /phone PHONE_NUMBER /email EMAIL /address ADDRESS /hire DATE
```

**Examples:**
```properties
add /name John Doe /nric S1234567A /email johnd@example.com /phone 98765432 /address John street, block 123, #01-01 /hire 2025-03-05
```
</panel>


<panel type="seamless" header="### Listing all employees: `list`" expanded no-close no-switch>

##### Shows a list of all employees in the HR Nexus system.

**Format:**
```properties
list
```
**Output:**
![list](images/Ui.png)
</panel>

<panel type="seamless" header="### Editing an employee: `edit`" expanded no-close no-switch>

##### Edits an existing employee in the HR Nexus system.
**Compulsory field:** <span class="badge bg-primary">INDEX</span>

**Optional fields:** `name`, `nric`, `phone`, `email`, `address`, `hire`.
- **At least one** of the optional fields must be provided.
- Existing values will be updated to the input values.

**Format:**
```properties
edit INDEX [/name NAME] [/nric NRIC] [/phone PHONE] [/email EMAIL] [/address ADDRESS] [/hire DATE]
```

**Examples:**
- Edit the name of the **1st** employee to be `Robert Lee`:
    ```properties
    edit 1 /name Robert Lee
    ```
**Output:**
```
Edited Person: Robert Lee; Nric: T0000001A; Phone: 87438807; Email: alexyeoh@example.com; Address: Blk 30 Geylang Street 29, #06-40; Hire: 2025-01-01; Tags: friends
```
</panel>

<panel type="seamless" header="### Find employee by **name**: `find`" expanded no-close no-switch>

##### Finds employees whose **names** contain any of the given keywords.

Format:
```properties
find KEYWORD [MORE_KEYWORDS]
```

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

**Examples:**
- Find persons with the name `alex` and `david`:
```properties
find alex david
```
  

**Output:**

Returns both `Alex Yeoh` and `David Li`
![result for 'find alex david'](images/findAlexDavidResult.png)
</panel>

<panel type="seamless" header="### Deleting an employee: `delete`" expanded no-close no-switch>

##### Deletes the specified person from the HR Nexus system.

Format:
```properties
delete INDEX
```

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
- First, list all employees, then delete the **1st** employee in the list:
```properties
list
delete 1
```

**Output:**
- The **1st** employee in the list will be deleted.
```
Deleted Person: Robert Lee; Nric: T0000001A; Phone: 87438807; Email: alexyeoh@example.com; Address: Blk 30 Geylang Street 29, #06-40; Hire: 2025-01-01; Tags: friends
```

</panel>

<panel type="seamless" header="### Clearing all entries : `clear`" expanded no-close no-switch>

##### Clears all employee entries from the HR Nexus system.

Format:
```properties
clear
```

**Output:**
```
Address book has been cleared!
```
</panel>

<panel type="seamless" header="### Exiting the program : `exit`" expanded no-close no-switch>

##### Exits the program.

Format:
```properties
exit
```
</panel>
<br>

## Data Storage

HR Nexus automatically saves your data to disk after any command that modifies it. There is no need to perform manual saves.

### Data file location

Employee records are stored in a JSON file `addressbook.json` located at:
```ps
[HRNexus.jar file location]/data/addressbook.json
```

### Editing the data file

Advanced users can directly edit the data file if they are familiar with JSON. The data is stored in a format that is easy to understand and edit. 
However, it is recommended to use the app to edit the data file whenever possible.

<box type="warning" header="#### Caution" icon-size="2x" seamless>

Editing the data file manually can lead to issues if the file format becomes invalid. If the file cannot be parsed on startup, HR Nexus will discard its contents and create a new, empty data file.
We strongly recommend creating a backup before making any changes.

Additionally, incorrect edits (e.g., values outside acceptable ranges or missing fields) may cause HR Nexus to behave unexpectedly. Proceed only if you are confident in editing JSON files safely.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`

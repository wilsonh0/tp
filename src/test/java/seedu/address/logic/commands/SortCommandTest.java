package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.comparators.AddressComparator;
import seedu.address.model.person.comparators.EmailComparator;
import seedu.address.model.person.comparators.HireComparator;
import seedu.address.model.person.comparators.NameComparator;
import seedu.address.model.person.comparators.NricComparator;
import seedu.address.model.person.comparators.PhoneComparator;

public class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validNameAscending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name", "ascending");
        SortCommand command = new SortCommand("name", "asc");
        expectedModel.setAddressBook(getTypicalAddressBook());
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.setAddressBook(sortAddressBook(expectedModel, "name", "asc"));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validNameDescending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name", "descending");
        SortCommand command = new SortCommand("name", "desc");
        expectedModel.setAddressBook(getTypicalAddressBook());
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.setAddressBook(sortAddressBook(expectedModel, "name", "desc"));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validPhoneAscending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "phone", "ascending");
        SortCommand command = new SortCommand("phone", "asc");
        expectedModel.setAddressBook(getTypicalAddressBook());
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.setAddressBook(sortAddressBook(expectedModel, "phone", "asc"));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validEmailDescending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "email", "descending");
        SortCommand command = new SortCommand("email", "desc");
        expectedModel.setAddressBook(getTypicalAddressBook());
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.setAddressBook(sortAddressBook(expectedModel, "email", "desc"));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validHireAscending_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "hire", "ascending");
        SortCommand command = new SortCommand("hire", "asc");
        expectedModel.setAddressBook(getTypicalAddressBook());
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.setAddressBook(sortAddressBook(expectedModel, "hire", "asc"));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_success() {
        Model emptyModel = new ModelManager();
        Model expectedEmptyModel = new ModelManager();
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name", "ascending");
        SortCommand command = new SortCommand("name", "asc");
        assertCommandSuccess(command, emptyModel, expectedMessage, expectedEmptyModel);
    }

    @Test
    public void execute_singlePersonAddressBook_success() {
        Model singlePersonModel = new ModelManager();
        singlePersonModel.addPerson(ALICE);
        Model expectedSinglePersonModel = new ModelManager();
        expectedSinglePersonModel.addPerson(ALICE);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "nric", "descending");
        SortCommand command = new SortCommand("nric", "desc");
        assertCommandSuccess(command, singlePersonModel, expectedMessage, expectedSinglePersonModel);
    }

    @Test
    public void execute_multiplePersonsAddressBookByName_success() {
        // Create a model with multiple persons in unsorted order
        Model multiplePersonsModel = new ModelManager();
        multiplePersonsModel.addPerson(BENSON); // Should appear second in name sort
        multiplePersonsModel.addPerson(ALICE); // Should appear first in name sort
        multiplePersonsModel.addPerson(CARL); // Should appear third in name sort

        // Create expected model with sorted persons
        Model expectedSortedModel = new ModelManager();
        expectedSortedModel.addPerson(ALICE);
        expectedSortedModel.addPerson(BENSON);
        expectedSortedModel.addPerson(CARL);
        expectedSortedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name", "ascending");
        SortCommand command = new SortCommand("name", "asc");

        // Verify the command succeeds and produces the correct sorted order
        assertCommandSuccess(command, multiplePersonsModel, expectedMessage, expectedSortedModel);

        // Additional verification: Check the exact order of persons
        List<Person> sortedPersons = multiplePersonsModel.getFilteredPersonList();
        assertEquals(ALICE, sortedPersons.get(0));
        assertEquals(BENSON, sortedPersons.get(1));
        assertEquals(CARL, sortedPersons.get(2));
    }

    @Test
    public void execute_multiplePersonsPhoneDescending_success() {
        // Create a model with multiple persons in unsorted order
        Model multiplePersonsModel = new ModelManager();
        multiplePersonsModel.addPerson(ALICE); // 94351253, should appear third
        multiplePersonsModel.addPerson(BENSON); // 98765432, should appear first
        multiplePersonsModel.addPerson(CARL); // 95352563, should appear second

        // Create expected model with sorted persons (descending)
        Model expectedSortedModel = new ModelManager();
        expectedSortedModel.addPerson(BENSON);
        expectedSortedModel.addPerson(CARL);
        expectedSortedModel.addPerson(ALICE);
        expectedSortedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "phone", "descending");
        SortCommand command = new SortCommand("phone", "desc");

        assertCommandSuccess(command, multiplePersonsModel, expectedMessage, expectedSortedModel);

        // Verify exact order
        List<Person> sortedPersons = multiplePersonsModel.getFilteredPersonList();
        assertEquals(BENSON, sortedPersons.get(0));
        assertEquals(CARL, sortedPersons.get(1));
        assertEquals(ALICE, sortedPersons.get(2));
    }

    @Test
    public void execute_multiplePersonsNricAscending_success() {
        // Create a model with multiple persons in unsorted NRIC order
        Model multiplePersonsModel = new ModelManager();
        multiplePersonsModel.addPerson(ALICE); // S1234567A, should appear first in NRIC sort
        multiplePersonsModel.addPerson(BENSON); // T2132398K, should appear third in NRIC sort
        multiplePersonsModel.addPerson(CARL); // S1234572F, should appear second in NRIC sort

        // Create expected model with sorted persons
        Model expectedSortedModel = new ModelManager();
        expectedSortedModel.addPerson(ALICE);
        expectedSortedModel.addPerson(CARL);
        expectedSortedModel.addPerson(BENSON);
        expectedSortedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "nric", "ascending");
        SortCommand command = new SortCommand("nric", "asc");

        assertCommandSuccess(command, multiplePersonsModel, expectedMessage, expectedSortedModel);

        // Verify exact order
        List<Person> sortedPersons = multiplePersonsModel.getFilteredPersonList();
        assertEquals(ALICE, sortedPersons.get(0));
        assertEquals(CARL, sortedPersons.get(1));
        assertEquals(BENSON, sortedPersons.get(2));
    }

    @Test
    public void execute_multiplePersonsAddressDescending_success() {
        // Create a model with multiple persons in unsorted address order
        Model multiplePersonsModel = new ModelManager();
        multiplePersonsModel.addPerson(ALICE); // "123, Jurong West Ave 6, #08-111", should appear third
        multiplePersonsModel.addPerson(BENSON); // "311, Clementi Ave 2, #02-25", should appear second
        multiplePersonsModel.addPerson(CARL); // "wall street", should appear first

        // Create expected model with sorted persons (descending)
        Model expectedSortedModel = new ModelManager();
        expectedSortedModel.addPerson(CARL);
        expectedSortedModel.addPerson(BENSON);
        expectedSortedModel.addPerson(ALICE);
        expectedSortedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "address", "descending");
        SortCommand command = new SortCommand("address", "desc");

        assertCommandSuccess(command, multiplePersonsModel, expectedMessage, expectedSortedModel);

        // Verify exact order
        List<Person> sortedPersons = multiplePersonsModel.getFilteredPersonList();
        assertEquals(CARL, sortedPersons.get(0));
        assertEquals(BENSON, sortedPersons.get(1));
        assertEquals(ALICE, sortedPersons.get(2));
    }

    @Test
    public void execute_multiplePersonsEmailAscending_success() {
        // Create a model with multiple persons in unsorted email order
        Model multiplePersonsModel = new ModelManager();
        multiplePersonsModel.addPerson(BENSON); // "johnd@example.com"
        multiplePersonsModel.addPerson(ALICE); // "alice@example.com"
        multiplePersonsModel.addPerson(CARL); // "heinz@example.com"

        // Create expected model with sorted persons
        Model expectedSortedModel = new ModelManager();
        expectedSortedModel.addPerson(ALICE);
        expectedSortedModel.addPerson(CARL);
        expectedSortedModel.addPerson(BENSON);
        expectedSortedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "email", "ascending");
        SortCommand command = new SortCommand("email", "asc");

        assertCommandSuccess(command, multiplePersonsModel, expectedMessage, expectedSortedModel);

        // Verify exact order
        List<Person> sortedPersons = multiplePersonsModel.getFilteredPersonList();
        assertEquals(ALICE, sortedPersons.get(0));
        assertEquals(CARL, sortedPersons.get(1));
        assertEquals(BENSON, sortedPersons.get(2));
    }

    @Test
    public void execute_multiplePersonsHireDescending_success() {
        // Create a model with multiple persons in unsorted hire date order
        Model multiplePersonsModel = new ModelManager();
        multiplePersonsModel.addPerson(ALICE); // "2019-10-10"
        multiplePersonsModel.addPerson(BENSON); // "2022-10-10"
        multiplePersonsModel.addPerson(CARL); // "2020-10-10"

        // Create expected model with sorted persons (descending)
        Model expectedSortedModel = new ModelManager();
        expectedSortedModel.addPerson(BENSON); // Newest hire first
        expectedSortedModel.addPerson(CARL);
        expectedSortedModel.addPerson(ALICE); // Oldest hire last
        expectedSortedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "hire", "descending");
        SortCommand command = new SortCommand("hire", "desc");

        assertCommandSuccess(command, multiplePersonsModel, expectedMessage, expectedSortedModel);

        // Verify exact order
        List<Person> sortedPersons = multiplePersonsModel.getFilteredPersonList();
        assertEquals(BENSON, sortedPersons.get(0));
        assertEquals(CARL, sortedPersons.get(1));
        assertEquals(ALICE, sortedPersons.get(2));
    }

    @Test
    public void execute_invalidAttribute_throwsCommandException() {
        SortCommand command = new SortCommand("invalid", "asc");
        String expectedMessage = SortCommand.MESSAGE_INVALID_ATTRIBUTE + SortCommand.MESSAGE_USAGE;
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_invalidDirection_throwsCommandException() {
        SortCommand command = new SortCommand("name", "invalid");
        String expectedMessage = SortCommand.MESSAGE_INVALID_DIRECTION + SortCommand.MESSAGE_USAGE;
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void equals() {
        SortCommand sortNameAscCommand = new SortCommand("name", "asc");
        SortCommand sortNameDescCommand = new SortCommand("name", "desc");
        SortCommand sortPhoneAscCommand = new SortCommand("phone", "asc");

        // same object -> returns true
        assertTrue(sortNameAscCommand.equals(sortNameAscCommand));

        // same values -> returns true
        SortCommand sortNameAscCommandCopy = new SortCommand("name", "asc");
        assertTrue(sortNameAscCommand.equals(sortNameAscCommandCopy));

        // different types -> returns false
        assertFalse(sortNameAscCommand.equals(1));

        // null -> returns false
        assertFalse(sortNameAscCommand.equals(null));

        // different direction -> returns false
        assertFalse(sortNameAscCommand.equals(sortNameDescCommand));

        // different attribute -> returns false
        assertFalse(sortNameAscCommand.equals(sortPhoneAscCommand));
    }

    /**
     * Helper method to sort the address book according to the given attribute and direction.
     */
    private ReadOnlyAddressBook sortAddressBook(Model model, String attribute, String direction) {
        // Create a new address book with the same persons
        AddressBook sortedAddressBook = new AddressBook(model.getAddressBook());

        // Get a modifiable list of persons
        List<Person> persons = new ArrayList<>(model.getAddressBook().getPersonList());

        // Sort the modifiable list
        switch (attribute) {
        case "name":
            persons.sort(direction.equals("desc")
                ? new NameComparator().reversed()
                : new NameComparator());
            break;
        case "nric":
            persons.sort(direction.equals("desc")
                ? new NricComparator().reversed()
                : new NricComparator());
            break;
        case "phone":
            persons.sort(direction.equals("desc")
                ? new PhoneComparator().reversed()
                : new PhoneComparator());
            break;
        case "address":
            persons.sort(direction.equals("desc")
                ? new AddressComparator().reversed()
                : new AddressComparator());
            break;
        case "email":
            persons.sort(direction.equals("desc")
                ? new EmailComparator().reversed()
                : new EmailComparator());
            break;
        case "hire":
            persons.sort(direction.equals("desc")
                ? new HireComparator().reversed()
                : new HireComparator());
            break;
        default:
            throw new IllegalArgumentException("Invalid attribute");
        }

        // Set the sorted list back into the address book
        sortedAddressBook.setPersons(persons);
        return sortedAddressBook;
    }
}

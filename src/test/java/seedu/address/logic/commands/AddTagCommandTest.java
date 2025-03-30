package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.model.person.TagSet;

public class AddTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validTag_success() {
        // Setup
        Index index = INDEX_FIRST_PERSON;
        Tag validTag = new Tag("friend");
        Person personToEdit = model.getFilteredPersonList().get(index.getZeroBased());

        // Prepare the expected person with the new tag
        Set<Tag> newTags = new HashSet<>(personToEdit.getTags().getTags());
        newTags.add(validTag);
        TagSet updatedTags = new TagSet(newTags);
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getNric(),
                personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getHire(),
                updatedTags, personToEdit.getLeaves());

        // Create the AddTagCommand
        AddTagCommand addTagCommand = new AddTagCommand(index, validTag);

        // Prepare the expected model
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        // Adjust the expected success message to use only the name, as per your current implementation
        String expectedMessage = String.format("Tag \"%s\" added successfully to %s.", validTag,
                                                personToEdit.getName());

        // Execute and assert success
        assertCommandSuccess(addTagCommand, model, expectedMessage, editedPerson, expectedModel);
    }


    @Test
    public void execute_invalidTag_throwsCommandException() {
        // Setup
        Index index = INDEX_FIRST_PERSON;

        // Try creating the AddTagCommand with the invalid tag and expect an exception
        assertThrows(IllegalArgumentException.class, () -> {
            new AddTagCommand(index, new Tag("friend123@"));
        });
    }



    @Test
    public void equals() {
        // Setup: Same values
        AddTagCommand addTagCommand1 = new AddTagCommand(INDEX_FIRST_PERSON, new Tag("friend"));
        AddTagCommand addTagCommand2 = new AddTagCommand(INDEX_FIRST_PERSON, new Tag("friend"));
        assertEquals(addTagCommand1, addTagCommand2);

        // Different index
        AddTagCommand addTagCommand3 = new AddTagCommand(INDEX_SECOND_PERSON, new Tag("friend"));
        assertFalse(addTagCommand1.equals(addTagCommand3));

        // Different tag
        AddTagCommand addTagCommand4 = new AddTagCommand(INDEX_FIRST_PERSON, new Tag("family"));
        assertFalse(addTagCommand1.equals(addTagCommand4));
    }
}

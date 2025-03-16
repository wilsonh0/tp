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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.model.person.TagSet;

public class RemoveTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validTag_success() {
        // Setup
        Index index = INDEX_FIRST_PERSON;
        Person personToEdit = model.getFilteredPersonList().get(index.getZeroBased());
        Tag tagToRemove = new Tag("friends"); // Assuming 'friend' is a tag that exists for the person

        // Prepare the expected person with the tag removed
        Set<Tag> newTags = new HashSet<>(personToEdit.getTags().getTags());
        newTags.remove(tagToRemove);  // Remove the tag
        TagSet updatedTags = new TagSet(newTags);
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getNric(),
            personToEdit.getPhone(), personToEdit.getEmail(),
            personToEdit.getAddress(), personToEdit.getHire(), updatedTags);

        // Create the RemoveTagCommand
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(index, tagToRemove);

        // Prepare the expected model
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        // Adjust the expected success message
        String expectedMessage = String.format("Tag \"%s\" removed successfully from %s.",
                                                tagToRemove, personToEdit.getName());

        // Execute and assert success
        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTag_throwsCommandException() {
        // Setup
        Index index = INDEX_FIRST_PERSON;

        // Try creating the RemoveTagCommand with the invalid tag and expect an exception
        assertThrows(CommandException.class, () -> {
            new RemoveTagCommand(index, new Tag("nonexistenttag")).execute(model);
        });
    }

    @Test
    public void equals() {
        // Setup: Same values
        RemoveTagCommand removeTagCommand1 = new RemoveTagCommand(INDEX_FIRST_PERSON, new Tag("friend"));
        RemoveTagCommand removeTagCommand2 = new RemoveTagCommand(INDEX_FIRST_PERSON, new Tag("friend"));
        assertEquals(removeTagCommand1, removeTagCommand2);

        // Different index
        RemoveTagCommand removeTagCommand3 = new RemoveTagCommand(INDEX_SECOND_PERSON, new Tag("friend"));
        assertFalse(removeTagCommand1.equals(removeTagCommand3));

        // Different tag
        RemoveTagCommand removeTagCommand4 = new RemoveTagCommand(INDEX_FIRST_PERSON, new Tag("family"));
        assertFalse(removeTagCommand1.equals(removeTagCommand4));
    }
}

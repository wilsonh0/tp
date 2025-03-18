package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.model.person.TagSet;

/**
 * Removes a tag from an existing person in the address book.
 */
public class RemoveTagCommand extends Command {

    public static final String COMMAND_WORD = "removetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a tag from the person identified "
        + "by the index number used in the displayed person list. "
        + "Only one tag can be removed at a time.\n"
        + "Parameters: INDEX (must be a positive integer) TAG\n"
        + "Example: " + COMMAND_WORD + " 1 Software Engineer";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Tag \"%1$s\" removed successfully from %2$s.";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag \"%1$s\" not found for %2$s.";
    public static final String MESSAGE_INVALID_INDEX = "Error: Index can only contain numbers.";
    public static final String MESSAGE_INVALID_TAG = "Error: Tag name can only contain letters, numbers, and spaces.";
    public static final String MESSAGE_INVALID_FORMAT = "Error: Invalid command format. Usage: removetag [INDEX] [TAG]";

    private final Index index;
    private final Tag tagToRemove;

    /**
     * @param index of the person in the filtered person list to remove a tag from
     * @param tagToRemove the tag to remove from the person
     */
    public RemoveTagCommand(Index index, Tag tagToRemove) {
        requireNonNull(index);
        requireNonNull(tagToRemove);

        this.index = index;
        this.tagToRemove = tagToRemove;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        TagSet existingTags = personToEdit.getTags();

        // Normalize tag for case-insensitivity
        String normalizedTagName = tagToRemove.tagName.trim().toLowerCase();

        // Check if the tag exists for the person
        if (existingTags.getTags().stream().noneMatch(tag -> tag.tagName.equalsIgnoreCase(normalizedTagName))) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, tagToRemove, personToEdit.getName()));
        }


        // Create a new set with the existing tags and remove the tag
        Set<Tag> updatedTagsSet = new HashSet<>(existingTags.getTags());
        updatedTagsSet.removeIf(tag -> tag.tagName.equalsIgnoreCase(normalizedTagName));
        TagSet updatedTags = new TagSet(updatedTagsSet);

        Person updatedPerson = new Person(personToEdit.getName(), personToEdit.getNric(),
            personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getAddress(),
            personToEdit.getHire(), updatedTags, personToEdit.getLeaves());

        model.setPerson(personToEdit, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS, tagToRemove, updatedPerson.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemoveTagCommand)) {
            return false;
        }

        RemoveTagCommand otherCommand = (RemoveTagCommand) other;
        return index.equals(otherCommand.index) && tagToRemove.equals(otherCommand.tagToRemove);
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.model.person.TagSet;

/**
 * Adds a tag to an existing person in the address book.
 */
public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "addtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag to the person identified "
        + "by the index number used in the displayed person list. "
        + "Existing tags will be retained. Only one tag can be added at a time.\n"
        + "Parameters: INDEX (must be a positive integer) TAG\n"
        + "Example: " + COMMAND_WORD + " 1 Software Engineer";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Tag \"%1$s\" added successfully to %2$s.";
    public static final String MESSAGE_DUPLICATE_TAG = "Tag \"%1$s\" already exists for %2$s.";
    public static final String MESSAGE_INVALID_INDEX = "Error: Index can only contain numbers.";
    public static final String MESSAGE_INVALID_TAG = "Error: Tag name can only contain letters, numbers, spaces, and"
        + " punctuation like hyphens and apostrophes.";
    public static final String MESSAGE_INVALID_FORMAT = "Error: Invalid command format. Usage: addtag [INDEX] [TAG]";
    public static final String MESSAGE_INDEX_OUT_OF_BOUNDS =
        "Error: Index out of bounds! It should be a positive number and less than %d.";
    public static final String MESSAGE_INDEX_NEGATIVE =
        "Error: Index cannot be negative! It should be a positive number.";


    private final Index index;
    private final Tag tagToAdd;

    /**
     * @param index of the person in the filtered person list to add a tag to
     * @param tagToAdd the tag to add to the person
     */
    public AddTagCommand(Index index, Tag tagToAdd) {
        requireNonNull(index);
        requireNonNull(tagToAdd);

        this.index = index;
        this.tagToAdd = tagToAdd;
    }

    /**
     * Executes the command and adds the tag to the person at the specified index.
     * @param model {@code Model} which the command should operate on.
     * @return the result of the command execution
     * @throws CommandException if the command execution fails
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INDEX_OUT_OF_BOUNDS, lastShownList.size() + 1));
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        TagSet existingTags = personToEdit.getTags();

        // Normalize tag for case-insensitivity
        String normalizedTagName = tagToAdd.tagName.trim().toLowerCase();
        Tag normalizedTag = new Tag(normalizedTagName);

        if (existingTags.getTags().stream().anyMatch(tag -> tag.tagName.equalsIgnoreCase(normalizedTagName))) {
            return new CommandResult(String.format(MESSAGE_DUPLICATE_TAG, tagToAdd, personToEdit.getName()));
        }

        // Create a new set with the existing tags and add the new tag
        Set<Tag> updatedTagsSet = new HashSet<>(existingTags.getTags());
        updatedTagsSet.add(normalizedTag);
        TagSet updatedTags = new TagSet(updatedTagsSet);

        Person updatedPerson = new Person(personToEdit.getName(), personToEdit.getNric(),
            personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getAddress(),
            personToEdit.getHire(), updatedTags, personToEdit.getLeaves(), personToEdit.getAttendance());

        model.setPerson(personToEdit, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, tagToAdd, updatedPerson.getName()),
            updatedPerson);
    }

    /**
     * Checks if the command is equal to another command.
     * @param other the other command to compare with
     * @return true if both commands are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        AddTagCommand otherCommand = (AddTagCommand) other;
        return index.equals(otherCommand.index) && tagToAdd.equals(otherCommand.tagToAdd);
    }

}

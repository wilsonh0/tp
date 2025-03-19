package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REASON;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.NricMatchesPredicate;
import seedu.address.model.person.Person;

/**
 * Adds a leave to a person.
 */
public class LeaveCommand extends Command {

    public static final String COMMAND_WORD = "leave";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Leave management system [add/remove]\n"
            + "- add: Adds a leave to a person\n"
            + "\tUsage: " + COMMAND_WORD + " add [INDEX or NRIC] "
            + PREFIX_LEAVE_START + "START_DATE "
            + PREFIX_LEAVE_END + "END_DATE "
            + PREFIX_REASON + "REASON\n"
            + "- remove: Removes a leave from a person\n"
            + "\tUsage: " + COMMAND_WORD + " remove [INDEX or NRIC] "
            + PREFIX_LEAVE_START + "START_DATE\n"
            + "\tExample: " + COMMAND_WORD + " remove 1 "
            + PREFIX_LEAVE_START + "2021-10-01";


    public static final String MESSAGE_ADD_SUCCESS = "Leave added: %1$s for %2$s";
    public static final String MESSAGE_REMOVE_SUCCESS = "Leave removed: %1$s for %2$s";
    public static final String MESSAGE_LEAVE_EXISTS = "Leave already exists starting on %1$s";
    public static final String MESSAGE_LEAVE_NOT_FOUND = "Leave starting on %1$s not found";
    public static final String MESSAGE_OVERLAPPING_LEAVE = "Leave from %1$s to %2$s overlaps with existing leave";
    public static final String MESSAGE_PERSON_NRIC_NOT_FOUND = "Person with NRIC %1$s not found";
    public static final String MESSAGE_PERSON_INDEX_NOT_FOUND = "Person with index %1$s not found";
    public static final String MESSAGE_UNKNOWN_SUBCOMMAND = "Unknown subcommand: %1$s";

    private final String subCommand;
    private final Index index;
    private final NricMatchesPredicate predicate;
    private final Leave leave;

    /**
     * Creates a LeaveCommand to perform an operation on a person identified by index.
     */
    public LeaveCommand(String subCommand, Index index, Leave leave) {
        requireNonNull(subCommand);
        requireNonNull(index);
        requireNonNull(leave);
        this.subCommand = subCommand;
        this.index = index;
        this.predicate = null;
        this.leave = leave;
    }

    /**
     * Creates a LeaveCommand to perform an operation on a person identified by NRIC.
     */
    public LeaveCommand(String subCommand, NricMatchesPredicate predicate, Leave leave) {
        requireNonNull(subCommand);
        requireNonNull(predicate);
        requireNonNull(leave);
        this.subCommand = subCommand;
        this.index = null;
        this.predicate = predicate;
        this.leave = leave;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person targetPerson = findTargetPerson(model);

        switch (subCommand.toLowerCase()) {
        case "add":
            return processAdd(targetPerson);
        case "remove":
            return processRemove(targetPerson);
        default:
            throw new CommandException(String.format(MESSAGE_UNKNOWN_SUBCOMMAND, subCommand));
        }
    }

    /**
     * Finds the target person based on index or NRIC.
     */
    private Person findTargetPerson(Model model) throws CommandException {
        if (index != null) {
            List<Person> lastShownList = model.getFilteredPersonList();
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(String.format(MESSAGE_PERSON_INDEX_NOT_FOUND, index.getOneBased()));
            }
            return lastShownList.get(index.getZeroBased());
        } else if (predicate != null) {
            List<Person> matchingPersons = model.getAddressBook().getPersonList().stream()
                    .filter(predicate)
                    .toList();

            if (matchingPersons.isEmpty()) {
                throw new CommandException(String.format(MESSAGE_PERSON_NRIC_NOT_FOUND, predicate.toString()));
            }

            if (matchingPersons.size() > 1) {
                throw new CommandException("Multiple persons found with the same NRIC");
            }

            return matchingPersons.get(0);
        }

        throw new CommandException(MESSAGE_INVALID_COMMAND_FORMAT);
    }

    /**
     * Adds a leave to the person's list of leaves.
     */
    private CommandResult processAdd(Person person) throws CommandException {
        if (person.hasLeave(leave)) {
            throw new CommandException(String.format(MESSAGE_LEAVE_EXISTS, leave.getStartDate()));
        }

        if (person.hasOverlappingLeave(leave)) {
            throw new CommandException(String.format(MESSAGE_OVERLAPPING_LEAVE,
                    leave.getStartDate(), leave.getEndDate()));
        }

        person.addLeave(leave);
        return new CommandResult(String.format(MESSAGE_ADD_SUCCESS, leave, person.getName()));
    }

    /**
     * Removes a leave from the person's list of leaves.
     */
    private CommandResult processRemove(Person person) throws CommandException {
        if (!person.hasLeave(leave)) {
            throw new CommandException(MESSAGE_LEAVE_NOT_FOUND);
        }

        Leave actualLeave = person.getLeaves().stream()
                .filter(l -> l.equals(leave))
                .findFirst()
                .orElseThrow(() -> new CommandException(MESSAGE_LEAVE_NOT_FOUND));

        person.removeLeave(actualLeave);
        return new CommandResult(String.format(MESSAGE_REMOVE_SUCCESS, actualLeave, person.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LeaveCommand)) {
            return false;
        }

        LeaveCommand otherCommand = (LeaveCommand) other;
        return subCommand.equals(otherCommand.subCommand)
                && Objects.equals(index, otherCommand.index)
                && Objects.equals(predicate, otherCommand.predicate)
                && leave.equals(otherCommand.leave);
    }
}

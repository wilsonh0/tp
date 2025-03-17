package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REASON;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Adds a leave to a person.
 */
public class LeaveCommand extends Command {

    public static final String COMMAND_WORD = "leave";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + "SUBCOMMAND "
            + PREFIX_NRIC + " NRIC "
            + PREFIX_LEAVE_START + " START_DATE "
            + PREFIX_LEAVE_END + " END_DATE "
            + PREFIX_REASON + " REASON "
            + "\n "
            + "Example: " + COMMAND_WORD + " "
            + "add "
            + PREFIX_NRIC + " S9100308A "
            + PREFIX_LEAVE_START + " 2024-11-25 "
            + PREFIX_LEAVE_END + " 2024-11-26 "
            + PREFIX_REASON + " Sick Leave ";

    public static final String MESSAGE_SUCCESS = "Leave added: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person with NRIC %1$s not found";

    private final String subCommand;
    private final Nric nric;
    private final Leave leave;

    /**
     * Creates a LeaveCommand to add the specified {@code Leave}
     */
    public LeaveCommand(String subCommand, Nric nric, Leave leave) {
        requireNonNull(subCommand);
        requireNonNull(nric);
        requireNonNull(leave);
        this.subCommand = subCommand;
        this.nric = nric;
        this.leave = leave;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Find person by NRIC
        List<Person> personList = model.getFilteredPersonList();
        Person personToEdit = personList.stream()
                .filter(p -> p.getNric().equals(nric))
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, nric)));
        personToEdit.addLeave(leave);
        // output debug message
        System.out.println("Leave added: " + leave);
        return new CommandResult(String.format(MESSAGE_SUCCESS, leave));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeaveCommand // instanceof handles nulls
                && nric.equals(((LeaveCommand) other).nric)
                && leave.equals(((LeaveCommand) other).leave));
    }
}

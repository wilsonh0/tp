package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE_ABSENT;

import java.util.HashSet;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Marks attendance for all persons in the address book, treating the provided NRICs as absentees.
 * Increments the global working day count by one and increases the absent day count for each person
 * whose NRIC is listed as absent. NRICs that do not correspond to any person in the address book are ignored.
 */
public class AttendanceCommand extends Command {

    public static final String COMMAND_WORD = "attendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks attendance for everyone. "
            + "NRIC that does not match with any person will be ignored. "
            + "Duplicated NRICs will be regarded as just one NRIC of that particular alphanumerical sequence. "
            + "Parameters: "
            + PREFIX_ATTENDANCE_ABSENT + " LIST OF NRICs separated by at least one whitespace "
            + "(Can contain 0 or more valid NRICs) "
            + "\n "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ATTENDANCE_ABSENT + " T0123456A" + " T1234567B" + " T2345678C ";

    public static final String MESSAGE_SUCCESS = "Attendance added: %1$s";
    private final List<String> nricList;

    /**
     * Creates an AttendanceCommand to mark attendance, excluding those in the given NRIC list as absent.
     *
     * @param nricList List of NRIC strings representing absent individuals.
     */
    public AttendanceCommand(List<String> nricList) {
        requireNonNull(nricList);
        this.nricList = nricList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Get all persons whose NRIC is in nricList (i.e. absent people)
        List<Person> personList = model.getFilteredPersonList();

        // Increment work day count once for everyone
        for (Person person : personList) {
            person.incrementWorkDay();
        }

        // Get all persons whose absent
        List<Person> absentPersons = personList.stream()
                .filter(p -> nricList.contains(p.getNric().getNric()))
                .toList();

        // Mark absent people by incrementing their absent day count
        for (Person person : absentPersons) {
            person.incrementAbsentDay();
        }

        // Debug: output number of absentees
        System.out.println("Number of absentees: " + absentPersons.size());

        return new CommandResult(String.format(MESSAGE_SUCCESS, absentPersons.size() + " person marked as absent."));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AttendanceCommand // instanceof handles nulls
                && new HashSet<>(nricList).equals(new HashSet<>(((AttendanceCommand) other).nricList)));
    }

}

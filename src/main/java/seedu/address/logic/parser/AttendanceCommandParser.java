package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE_ABSENT;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.AttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input and creates a new {@link AttendanceCommand} object.
 */
public class AttendanceCommandParser implements Parser<AttendanceCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AttendanceCommand}
     * and returns a new {@code AttendanceCommand} object for execution.
     *
     * @param args The user input arguments.
     * @return An AttendanceCommand initialized with the parsed NRIC list.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ATTENDANCE_ABSENT);

        List<String> nricList = ParserUtil.parseAttendance(argMultimap.getValue(PREFIX_ATTENDANCE_ABSENT).get());

        if (!arePrefixesPresent(argMultimap, PREFIX_ATTENDANCE_ABSENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttendanceCommand.MESSAGE_USAGE));
        }

        return new AttendanceCommand(nricList);
    }

    /**
     * (Originally written in AddCommandParser.java by @MightyCupcakes)
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

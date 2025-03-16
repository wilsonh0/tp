package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REASON;

import java.util.stream.Stream;

import seedu.address.logic.commands.LeaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new LeaveCommand object
 */
public class LeaveCommandParser implements Parser<LeaveCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LeaveCommand
     * and returns a LeaveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LeaveCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Extract sub-command
        String[] parts = args.trim().split(" ", 2);
        if (parts.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveCommand.MESSAGE_USAGE));
        }

        String subCommand = parts[0];
        String arguments = (parts.length > 1) ? " " + parts[1] : ""; // arguments may be empty

        // Tokenize without sub-command
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                arguments, PREFIX_NRIC, PREFIX_LEAVE_START, PREFIX_LEAVE_END, PREFIX_REASON);

        if (!arePrefixesPresent(argMultimap, PREFIX_NRIC, PREFIX_LEAVE_START, PREFIX_LEAVE_END, PREFIX_REASON)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveCommand.MESSAGE_USAGE));
        }

        Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
        Leave leave = ParserUtil.parseLeave(argMultimap.getValue(PREFIX_LEAVE_START).get(),
                argMultimap.getValue(PREFIX_LEAVE_END).get(),
                argMultimap.getValue(PREFIX_REASON).get());

        return new LeaveCommand(subCommand, nric, leave);
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

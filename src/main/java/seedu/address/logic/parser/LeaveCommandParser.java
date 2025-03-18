package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REASON;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LeaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;

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
        String identifier = parts[1];

        switch (subCommand) {
        case "add":
            return parseAddLeave(subCommand, identifier);
        case "remove":
            return parseRemoveLeave(subCommand, identifier);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code ArgumentMultimap} and returns a LeaveCommand object for execution.
     * Requires all fields to be present.
     * @throws ParseException if the user input does not conform the expected format
     */
    private LeaveCommand parseAddLeave(String subCommand, String identifier) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                identifier, PREFIX_LEAVE_START, PREFIX_LEAVE_END, PREFIX_REASON);

        if (!arePrefixesPresent(argMultimap, PREFIX_LEAVE_START, PREFIX_LEAVE_END, PREFIX_REASON)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveCommand.MESSAGE_USAGE));
        }

        // Process identifier (index or NRIC)
        String id = argMultimap.getPreamble();
        Index index = null;
        Nric nric = null;

        try {
            index = ParserUtil.parseIndex(id);
        } catch (ParseException e) {
            nric = ParserUtil.parseNric(id);
        }

        Leave leave = ParserUtil.parseLeave(
                argMultimap.getValue(PREFIX_LEAVE_START).get(),
                argMultimap.getValue(PREFIX_LEAVE_END).get(),
                argMultimap.getValue(PREFIX_REASON).get());

        return getLeaveCommand(subCommand, index, nric, leave);
    }

    /**
     * Parses the given {@code ArgumentMultimap} and returns a LeaveCommand object for execution.
     * Only requires the start date to be present.
     * @throws ParseException if the user input does not conform the expected format
     */
    private LeaveCommand parseRemoveLeave(String subCommand, String identifier) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                identifier, PREFIX_LEAVE_START);

        if (!arePrefixesPresent(argMultimap, PREFIX_LEAVE_START)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveCommand.MESSAGE_USAGE));
        }

        // Process identifier (index or NRIC)
        String id = argMultimap.getPreamble();
        Index index = null;
        Nric nric = null;

        try {
            index = ParserUtil.parseIndex(id);
        } catch (ParseException e) {
            nric = ParserUtil.parseNric(id);
        }

        // Get optional parameters with defaults
        String startDate = argMultimap.getValue(PREFIX_LEAVE_START).get();
        String endDate = startDate; // Default to start date
        String reason = "-"; // Default to "-"

        Leave leave = ParserUtil.parseLeave(startDate, endDate, reason);

        return getLeaveCommand(subCommand, index, nric, leave);
    }

    /**
     * Parses the given {@code ArgumentMultimap} and returns a LeaveCommand object for execution.
     * Requires all fields to be present.
     * @throws ParseException if the user input does not conform the expected format
     */
    private LeaveCommand getLeaveCommand(String subCommand, Index index, Nric nric, Leave leave) throws ParseException {
        if (index != null) {
            return new LeaveCommand(subCommand, index, leave);
        } else if (nric != null) {
            NricMatchesPredicate predicate = new NricMatchesPredicate(nric);
            return new LeaveCommand(subCommand, predicate, leave);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveCommand.MESSAGE_USAGE));
        }
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

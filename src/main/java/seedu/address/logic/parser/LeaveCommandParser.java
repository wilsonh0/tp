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
    public static final String MESSAGE_INVALID_INDEX = "INDEX must be a positive integer.";
    /**
     * Parses the given {@code String} of arguments in the context of the LeaveCommand
     * and returns a LeaveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LeaveCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Extract sub-command
        String[] parts = args.trim().split(" ", 2);
        if (parts.length == 0 || parts[0].isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveCommand.MESSAGE_USAGE));
        }

        String subCommand = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (subCommand) {
        case "add":
            return parseAddLeave(arguments);
        case "remove":
            return parseRemoveLeave(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code ArgumentMultimap} and returns a LeaveCommand object for execution.
     * Requires all fields to be present.
     * @throws ParseException if the user input does not conform the expected format
     */
    private LeaveCommand parseAddLeave(String arguments) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                arguments, PREFIX_LEAVE_START, PREFIX_LEAVE_END, PREFIX_REASON);


        // Process identifier (index or NRIC)
        Object identifier = parseIdentifier(argMultimap.getPreamble(), LeaveCommand.ADD_MESSAGE_USAGE);

        if (!arePrefixesPresent(argMultimap, PREFIX_LEAVE_START, PREFIX_LEAVE_END, PREFIX_REASON)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveCommand.ADD_MESSAGE_USAGE));
        }


        Leave leave = ParserUtil.parseLeave(
                argMultimap.getValue(PREFIX_LEAVE_START).get(),
                argMultimap.getValue(PREFIX_LEAVE_END).get(),
                argMultimap.getValue(PREFIX_REASON).get());

        return getLeaveCommand("add", identifier, leave);
    }

    /**
     * Parses the given {@code ArgumentMultimap} and returns a LeaveCommand object for execution.
     * Only requires the start date to be present.
     * @throws ParseException if the user input does not conform the expected format
     */
    private LeaveCommand parseRemoveLeave(String arguments) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                arguments, PREFIX_LEAVE_START);

        // Process identifier (index or NRIC)
        Object identifier = parseIdentifier(argMultimap.getPreamble(), LeaveCommand.REMOVE_MESSAGE_USAGE);

        if (!arePrefixesPresent(argMultimap, PREFIX_LEAVE_START)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveCommand.REMOVE_MESSAGE_USAGE));
        }

        // Get optional parameters with defaults
        String startDate = argMultimap.getValue(PREFIX_LEAVE_START).get();
        if (startDate.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveCommand.REMOVE_MESSAGE_USAGE));
        }
        String endDate = startDate; // Default to start date
        String reason = "-"; // Default to "-"

        Leave leave = ParserUtil.parseLeave(startDate, endDate, reason);

        return getLeaveCommand("remove", identifier, leave);
    }

    /**
     * Creates and returns a LeaveCommand based on given identifier (Index or Nric)
     */
    private LeaveCommand getLeaveCommand(String subCommand, Object identifier, Leave leave) throws ParseException {
        if (identifier instanceof Index) {
            return new LeaveCommand(subCommand, (Index) identifier, leave);
        } else if (identifier instanceof Nric) {
            return new LeaveCommand(subCommand, new NricMatchesPredicate((Nric) identifier), leave);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Extracts and returns either an Index or NRIC.
     * @throws ParseException if neither is valid.
     */
    private Object parseIdentifier(String input, String message) throws ParseException {
        if (input == null || input.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, message));
        }

        // Get first word when there are prefixes
        String cleanIdentifier = input.trim().split("\\s+")[0];

        // Check if it looks like a prefix first
        if (cleanIdentifier.startsWith("/")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, message));
        }

        try {
            return ParserUtil.parseIndex(cleanIdentifier); // Try parsing as index
        } catch (ParseException e) {
            try {
                return ParserUtil.parseNric(cleanIdentifier.toUpperCase()); // Try parsing as NRIC
            } catch (ParseException nricException) {
                throw new ParseException((String.format("%s\n1. %s\n2. %s", "Invalid INDEX or NRIC",
                        MESSAGE_INVALID_INDEX, Nric.MESSAGE_CONSTRAINTS)));
            }
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

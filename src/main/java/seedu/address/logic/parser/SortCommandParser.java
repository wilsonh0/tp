package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    private static final Set<String> VALID_ATTRIBUTES = new HashSet<>(Arrays.asList("name", "nric", "phone", "address",
        "email", "hire"));
    private static final Set<String> VALID_DIRECTIONS = new HashSet<>(Arrays.asList("asc", "desc"));

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * @throws ParseException if the user input does not conform to the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String[] parameters = trimmedArgs.replaceAll("\\s+", " ").split(" ", 2);

        if (parameters.length != 2 || parameters[1].trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        } else {
            String attribute = parameters[0].toLowerCase();
            String direction = parameters[1].toLowerCase();
            if (!VALID_ATTRIBUTES.contains(attribute) || !VALID_DIRECTIONS.contains(direction)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
            }
            return new SortCommand(attribute, direction);
        }
    }
}

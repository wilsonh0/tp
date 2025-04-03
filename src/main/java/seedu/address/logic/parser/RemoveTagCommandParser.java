package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Tag;

/**
 * Parses input arguments and creates a new RemoveTagCommand object
 */
public class RemoveTagCommandParser implements Parser<RemoveTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagCommand
     * and returns a RemoveTagCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        // If no arguments provided, return usage message
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(RemoveTagCommand.MESSAGE_USAGE);
        }

        String[] splitArgs = trimmedArgs.split(" ", 2);

        // Ensure we have both an index and a tag
        if (splitArgs.length < 2 || splitArgs[1].trim().isEmpty()) {
            throw new ParseException(RemoveTagCommand.MESSAGE_INVALID_FORMAT);
        }

        try {
            Index index = ParserUtil.parseIndex(splitArgs[0]);
            String tagName = splitArgs[1].trim();

            // Replace multiple spaces with a single space
            tagName = tagName.replaceAll("\\s+", " ");

            // Ensure tag name is valid
            if (!tagName.matches("[a-zA-Z0-9 ]+")) {
                throw new ParseException(RemoveTagCommand.MESSAGE_INVALID_TAG);
            }

            return new RemoveTagCommand(index, new Tag(tagName));
        } catch (NumberFormatException e) {
            throw new ParseException(RemoveTagCommand.MESSAGE_INVALID_INDEX);
        }
    }
}

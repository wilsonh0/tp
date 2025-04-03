package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Tag;

/**
 * Parses input arguments and creates a new AddTagCommand object
 */
public class AddTagCommandParser implements Parser<AddTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTagCommand
     * and returns an AddTagCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        // If no input, show usage message
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(AddTagCommand.MESSAGE_USAGE);
        }

        String[] splitArgs = trimmedArgs.split(" ", 2);

        // Ensure we have at least an index and a tag
        if (splitArgs.length < 2 || splitArgs[1].trim().isEmpty()) {
            throw new ParseException(AddTagCommand.MESSAGE_INVALID_FORMAT);
        }

        try {
            // Ensure the index is a valid number
            int indexValue = Integer.parseInt(splitArgs[0]);
            if (indexValue <= 0) {
                throw new ParseException(AddTagCommand.MESSAGE_INDEX_NEGATIVE);
            }

            Index index = Index.fromOneBased(indexValue);
            String tagName = splitArgs[1].trim();

            // Replace multiple spaces with a single space
            tagName = tagName.replaceAll("\\s+", " ");

            // Ensure tag name is valid
            if (!tagName.matches("[a-zA-Z0-9 \\-']+")) {
                throw new ParseException(AddTagCommand.MESSAGE_INVALID_TAG);
            }

            return new AddTagCommand(index, new Tag(tagName));
        } catch (NumberFormatException e) {
            throw new ParseException(AddTagCommand.MESSAGE_INVALID_INDEX);
        }
    }

}

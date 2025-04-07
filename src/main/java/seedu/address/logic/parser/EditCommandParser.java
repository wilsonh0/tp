package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HIRE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {
    public static final String MESSAGE_INDEX_NEGATIVE =
        "Error: Index cannot be negative! It should be a positive number.";
    public static final String MESSAGE_INDEX_ZERO =
        "Error: Index cannot be zero! It should be a positive number starting from 1.";
    public static final String MESSAGE_INDEX_NOT_A_POSITIVE_INTEGER =
        "Error: Index is not a valid positive integer! Please provide a positive integer.";

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NRIC, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_HIRE);

        String preamble = argMultimap.getPreamble();
        if (preamble.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            int parsedIndex = Integer.parseInt(preamble.split(" ")[0]);
            if (parsedIndex == 0) {
                throw new ParseException(MESSAGE_INDEX_ZERO);
            }
            if (parsedIndex < 0) {
                throw new ParseException(MESSAGE_INDEX_NEGATIVE);
            }
            index = Index.fromOneBased(parsedIndex);
        } catch (NumberFormatException nfe) {
            throw new ParseException(MESSAGE_INDEX_NOT_A_POSITIVE_INTEGER);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            editPersonDescriptor.setNric(ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_HIRE).isPresent()) {
            editPersonDescriptor.setHire(ParserUtil.parseHire(argMultimap.getValue(PREFIX_HIRE).get()));
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

}

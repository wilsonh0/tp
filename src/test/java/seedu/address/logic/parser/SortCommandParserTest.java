package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        // Valid attributes and directions
        assertParseSuccess(parser, "name asc",
            new SortCommand("name", "asc"));
        assertParseSuccess(parser, "nric desc",
            new SortCommand("nric", "desc"));
        assertParseSuccess(parser, "phone asc",
            new SortCommand("phone", "asc"));

        // With multiple spaces between words
        assertParseSuccess(parser, "address   desc",
            new SortCommand("address", "desc"));
    }

    @Test
    public void parse_missingParts_failure() {
        // Empty input
        assertParseFailure(parser, "",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // Missing direction
        assertParseFailure(parser, "name",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // Missing attribute
        assertParseFailure(parser, "asc",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_failure() {
        // Invalid attribute
        assertParseFailure(parser, "invalid asc",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // Invalid direction
        assertParseFailure(parser, "name invalid",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // Both attribute and direction invalid
        assertParseFailure(parser, "invalid invalid",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // Extra arguments
        assertParseFailure(parser, "name asc extra",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_caseInsensitive_success() {
        // Mixed case attribute and direction
        assertParseSuccess(parser, "NaMe AsC",
            new SortCommand("name", "asc"));
        assertParseSuccess(parser, "NRIC DESC",
            new SortCommand("nric", "desc"));
    }

    @Test
    public void parse_whitespaceHandling_success() {
        // Leading/trailing whitespace
        assertParseSuccess(parser, "  name  asc  ",
            new SortCommand("name", "asc"));

        // Multiple internal whitespaces
        assertParseSuccess(parser, "email     desc",
            new SortCommand("email", "desc"));
    }
}

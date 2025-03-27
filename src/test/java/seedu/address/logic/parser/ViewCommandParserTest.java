package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewCommand;

public class ViewCommandParserTest {

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", ViewCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_nonNumericArg_throwsParseException() {
        assertParseFailure(parser, "abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsViewCommand() {
        Index expectedIndex = Index.fromOneBased(1);
        ViewCommand expectedViewCommand = new ViewCommand(expectedIndex);

        // No leading/trailing whitespaces
        assertParseSuccess(parser, "1", expectedViewCommand);

        // With leading and trailing whitespaces
        assertParseSuccess(parser, "  1  ", expectedViewCommand);
    }
}

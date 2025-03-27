package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE_ABSENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AttendanceCommand;
import seedu.address.model.person.Nric;

public class AttendanceCommandParserTest {
    private AttendanceCommandParser parser = new AttendanceCommandParser();

    @Test
    public void parse_validArgs_success() {
        List<String> expectedNrics = Arrays.asList(VALID_NRIC_AMY, VALID_NRIC_BOB);

        // single NRIC
        assertParseSuccess(parser, " " + PREFIX_ATTENDANCE_ABSENT + VALID_NRIC_AMY,
                new AttendanceCommand(Arrays.asList(VALID_NRIC_AMY)));

        // multiple NRICs
        assertParseSuccess(parser, " " + PREFIX_ATTENDANCE_ABSENT + VALID_NRIC_AMY + " " + VALID_NRIC_BOB,
                new AttendanceCommand(expectedNrics));

        // with whitespace preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " " + PREFIX_ATTENDANCE_ABSENT
                + VALID_NRIC_AMY + " " + VALID_NRIC_BOB, new AttendanceCommand(expectedNrics));
    }

    @Test
    public void parse_missingPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttendanceCommand.MESSAGE_USAGE);

        // missing prefix
        assertParseFailure(parser, VALID_NRIC_AMY + " " + VALID_NRIC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NRIC_AMY + " " + VALID_NRIC_BOB,
                expectedMessage);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttendanceCommand.MESSAGE_USAGE);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + " " + PREFIX_ATTENDANCE_ABSENT + VALID_NRIC_AMY,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid NRIC
        assertParseFailure(parser, " " + PREFIX_ATTENDANCE_ABSENT + INVALID_NRIC_DESC,
                Nric.MESSAGE_CONSTRAINTS);

        // one valid and one invalid NRIC
        assertParseFailure(parser, " " + PREFIX_ATTENDANCE_ABSENT + VALID_NRIC_AMY + " "
                + PREFIX_ATTENDANCE_ABSENT + INVALID_NRIC_DESC, Nric.MESSAGE_CONSTRAINTS);
    }

}

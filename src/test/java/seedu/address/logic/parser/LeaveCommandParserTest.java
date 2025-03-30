package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REASON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LeaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.NricMatchesPredicate;

public class LeaveCommandParserTest {
    // Reuse leaves from TypicalPersons
    private static final Leave ALICE_LEAVE = ALICE.getLeaves().get(0);
    private static final Leave BENSON_LEAVE = BENSON.getLeaves().get(0);

    private final LeaveCommandParser parser = new LeaveCommandParser();

    @Test
    public void parse_addLeaveByIndex_success() throws Exception {
        // Using ALICE's leave details but with index
        String input = String.format("add 1 %s%s %s%s %s%s",
                PREFIX_LEAVE_START, ALICE_LEAVE.getStartDate(),
                PREFIX_LEAVE_END, ALICE_LEAVE.getEndDate(),
                PREFIX_REASON, ALICE_LEAVE.getReason());

        LeaveCommand expected = new LeaveCommand("add", Index.fromOneBased(1), ALICE_LEAVE);
        assertEquals(expected, parser.parse(input));
    }

    @Test
    public void parse_addLeaveByNric_success() throws Exception {
        // Using BENSON's NRIC with ALICE's leave details
        String input = String.format("add %s %s%s %s%s %s%s",
                BENSON.getNric(),
                PREFIX_LEAVE_START, ALICE_LEAVE.getStartDate(),
                PREFIX_LEAVE_END, ALICE_LEAVE.getEndDate(),
                PREFIX_REASON, ALICE_LEAVE.getReason());

        LeaveCommand expected = new LeaveCommand("add",
                new NricMatchesPredicate(BENSON.getNric()), ALICE_LEAVE);
        assertEquals(expected, parser.parse(input));
    }

    @Test
    public void parse_removeLeaveByIndex_success() throws Exception {
        // Using ALICE's first leave start date
        String input = String.format("remove 1 %s%s",
                PREFIX_LEAVE_START, ALICE_LEAVE.getStartDate());

        LeaveCommand expected = new LeaveCommand("remove",
                Index.fromOneBased(1),
                new Leave(ALICE_LEAVE.getStartDate().toString(), ALICE_LEAVE.getStartDate().toString(), "-"));
        assertEquals(expected, parser.parse(input));
    }

    @Test
    public void parse_missingSubcommand_throwsParseException() {
        String input = String.format("1 %s%s",
                PREFIX_LEAVE_START, ALICE_LEAVE.getStartDate());
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_missingStartDate_throwsParseException() {
        String input = String.format("add 1 %s%s %s%s",
                PREFIX_LEAVE_END, ALICE_LEAVE.getEndDate(),
                PREFIX_REASON, ALICE_LEAVE.getReason());
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String input = String.format("add 0 %s%s %s%s %s%s",
                PREFIX_LEAVE_START, ALICE_LEAVE.getStartDate(),
                PREFIX_LEAVE_END, ALICE_LEAVE.getEndDate(),
                PREFIX_REASON, ALICE_LEAVE.getReason());
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_emptyReason_throwsParseException() {
        String input = String.format("add 1 %s%s %s%s %s",
                PREFIX_LEAVE_START, ALICE_LEAVE.getStartDate(),
                PREFIX_LEAVE_END, ALICE_LEAVE.getEndDate(),
                PREFIX_REASON);
        assertThrows(ParseException.class, () -> parser.parse(input));
    }
}

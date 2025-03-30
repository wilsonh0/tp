package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class LeaveCommandTest {

    private static final Leave LEAVE_1 = new Leave("2025-10-01", "2025-10-05", "Annual Leave");
    private static final Leave LEAVE_2 = new Leave("2025-11-01", "2025-11-05", "Sick Leave");

    @Test
    public void execute_addLeaveByIndex_success() throws Exception {
        Model model = new ModelManager();
        Person alice = new PersonBuilder(ALICE).build();
        model.addPerson(alice);

        LeaveCommand command = new LeaveCommand("add", Index.fromZeroBased(0), LEAVE_1);
        CommandResult result = command.execute(model);

        assertEquals(String.format(LeaveCommand.MESSAGE_ADD_SUCCESS, LEAVE_1, ALICE.getName()),
                result.getFeedbackToUser());
        assertTrue(model.getFilteredPersonList().get(0).getLeaves().contains(LEAVE_1));
    }

    @Test
    public void execute_addLeaveByNric_success() throws Exception {
        Model model = new ModelManager();
        Person alice = new PersonBuilder(ALICE).build();
        model.addPerson(alice);

        NricMatchesPredicate predicate = new NricMatchesPredicate(ALICE.getNric());
        LeaveCommand command = new LeaveCommand("add", predicate, LEAVE_1);
        CommandResult result = command.execute(model);

        assertEquals(String.format(LeaveCommand.MESSAGE_ADD_SUCCESS, LEAVE_1, ALICE.getName()),
                result.getFeedbackToUser());
        assertTrue(model.getFilteredPersonList().get(0).getLeaves().contains(LEAVE_1));
    }

    @Test
    public void execute_removeLeave_success() throws Exception {
        Model model = new ModelManager();
        Person aliceWithLeave = new PersonBuilder(ALICE).withLeaves(LEAVE_1).build();
        model.addPerson(aliceWithLeave);

        LeaveCommand command = new LeaveCommand("remove", Index.fromZeroBased(0), LEAVE_1);
        CommandResult result = command.execute(model);

        assertEquals(String.format(LeaveCommand.MESSAGE_REMOVE_SUCCESS, LEAVE_1, ALICE.getName()),
                result.getFeedbackToUser());
        assertFalse(model.getFilteredPersonList().get(0).getLeaves().contains(LEAVE_1));
    }

    @Test
    public void execute_addDuplicateLeave_throwsCommandException() {
        Model model = new ModelManager();
        Person aliceWithLeave = new PersonBuilder(ALICE).withLeaves(LEAVE_1).build();
        model.addPerson(aliceWithLeave);

        LeaveCommand command = new LeaveCommand("add", Index.fromZeroBased(0), LEAVE_1);

        assertThrows(CommandException.class,
                String.format(LeaveCommand.MESSAGE_LEAVE_EXISTS, LEAVE_1.getStartDate()), () -> command.execute(model));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Model model = new ModelManager();
        model.addPerson(ALICE);

        LeaveCommand command = new LeaveCommand("add", Index.fromZeroBased(1), LEAVE_1);

        assertThrows(CommandException.class,
                String.format(LeaveCommand.MESSAGE_PERSON_INDEX_NOT_FOUND, 2), () -> command.execute(model));
    }

    @Test
    public void execute_invalidNric_throwsCommandException() {
        Model model = new ModelManager();
        model.addPerson(ALICE);

        Nric invalidNric = new Nric("S9999999Z");
        LeaveCommand command = new LeaveCommand("add", new NricMatchesPredicate(invalidNric), LEAVE_1);

        assertThrows(CommandException.class,
                String.format(LeaveCommand.MESSAGE_PERSON_NRIC_NOT_FOUND, invalidNric), () -> command.execute(model));
    }

    @Test
    public void execute_unknownSubcommand_throwsCommandException() {
        Model model = new ModelManager();
        model.addPerson(ALICE);

        LeaveCommand command = new LeaveCommand("invalid", Index.fromZeroBased(0), LEAVE_1);

        assertThrows(CommandException.class,
                String.format(LeaveCommand.MESSAGE_UNKNOWN_SUBCOMMAND, "invalid"), () -> command.execute(model));
    }

    @Test
    public void equals() {
        NricMatchesPredicate alicePredicate = new NricMatchesPredicate(ALICE.getNric());

        LeaveCommand addCommand1 = new LeaveCommand("add", Index.fromZeroBased(0), LEAVE_1);
        LeaveCommand addCommand2 = new LeaveCommand("add", Index.fromZeroBased(0), LEAVE_1);
        LeaveCommand addCommandDifferentIndex = new LeaveCommand("add", Index.fromZeroBased(1), LEAVE_1);
        LeaveCommand addCommandDifferentLeave = new LeaveCommand("add", Index.fromZeroBased(0), LEAVE_2);
        LeaveCommand removeCommand = new LeaveCommand("remove", Index.fromZeroBased(0), LEAVE_1);
        LeaveCommand nricCommand = new LeaveCommand("add", alicePredicate, LEAVE_1);

        // same object -> returns true
        assertTrue(addCommand1.equals(addCommand1));

        // same values -> returns true
        assertTrue(addCommand1.equals(addCommand2));

        // different types -> returns false
        assertFalse(addCommand1.equals(1));

        // null -> returns false
        assertFalse(addCommand1.equals(null));

        // different index -> returns false
        assertFalse(addCommand1.equals(addCommandDifferentIndex));

        // different leave -> returns false
        assertFalse(addCommand1.equals(addCommandDifferentLeave));

        // different subcommand -> returns false
        assertFalse(addCommand1.equals(removeCommand));

        // different identifier type -> returns false
        assertFalse(addCommand1.equals(nricCommand));
    }
}

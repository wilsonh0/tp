package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class AttendanceCommandTest {

    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    private Person alice = model.getFilteredPersonList().stream()
            .filter(p -> p.isSamePerson(TypicalPersons.ALICE))
            .findFirst()
            .orElseThrow();
    private Person benson = model.getFilteredPersonList().stream()
            .filter(p -> p.isSamePerson(TypicalPersons.BENSON))
            .findFirst()
            .orElseThrow();

    @Test
    public void execute_withAbsentees_success() throws CommandException {
        List<String> absentNrics = Arrays.asList(TypicalPersons.ALICE.getNric().getNric());

        int aliceOrininalWorkDayCount = alice.getAttendance().getWorkDayCount();
        int aliceOrininalAbsentDayCount = alice.getAttendance().getAbsentDayCount();

        AttendanceCommand attendanceCommand = new AttendanceCommand(absentNrics);

        CommandResult result = attendanceCommand.execute(model);
        String expectedMessage = String.format(AttendanceCommand.MESSAGE_SUCCESS, "1 person marked as absent.");
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(aliceOrininalWorkDayCount + 1, alice.getAttendance().getWorkDayCount());
        assertEquals(aliceOrininalAbsentDayCount + 1, alice.getAttendance().getAbsentDayCount());
    }

    @Test
    public void execute_withMultipleAbsentees_success() throws CommandException {
        List<String> absentNrics = Arrays.asList(
                TypicalPersons.ALICE.getNric().getNric(),
                TypicalPersons.BENSON.getNric().getNric()
        );

        int aliceOrininalWorkDayCount = alice.getAttendance().getWorkDayCount();
        int aliceOrininalAbsentDayCount = alice.getAttendance().getAbsentDayCount();
        int bensonOrininalWorkDayCount = benson.getAttendance().getWorkDayCount();
        int bensonOrininalAbsentDayCount = benson.getAttendance().getAbsentDayCount();

        AttendanceCommand attendanceCommand = new AttendanceCommand(absentNrics);

        CommandResult result = attendanceCommand.execute(model);
        String expectedMessage = String.format(AttendanceCommand.MESSAGE_SUCCESS, "2 person marked as absent.");
        assertEquals(expectedMessage, result.getFeedbackToUser());

        assertEquals(aliceOrininalWorkDayCount + 1, alice.getAttendance().getWorkDayCount());
        assertEquals(aliceOrininalAbsentDayCount + 1, alice.getAttendance().getAbsentDayCount());
        assertEquals(bensonOrininalWorkDayCount + 1, benson.getAttendance().getWorkDayCount());
        assertEquals(bensonOrininalAbsentDayCount + 1, benson.getAttendance().getAbsentDayCount());
    }

    @Test
    public void execute_withNoAbsentees_success() throws CommandException {
        List<String> absentNrics = Arrays.asList();

        int aliceOrininalWorkDayCount = alice.getAttendance().getWorkDayCount();
        int aliceOrininalAbsentDayCount = alice.getAttendance().getAbsentDayCount();

        AttendanceCommand attendanceCommand = new AttendanceCommand(absentNrics);

        CommandResult result = attendanceCommand.execute(model);
        String expectedMessage = String.format(AttendanceCommand.MESSAGE_SUCCESS, "0 person marked as absent.");
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(aliceOrininalWorkDayCount + 1, alice.getAttendance().getWorkDayCount());
        assertEquals(aliceOrininalAbsentDayCount, alice.getAttendance().getAbsentDayCount());
    }

    @Test
    public void execute_withUnknownNric_success() throws CommandException {
        List<String> absentNrics = Arrays.asList("T9999999A");

        int aliceOrininalWorkDayCount = alice.getAttendance().getWorkDayCount();
        int aliceOrininalAbsentDayCount = alice.getAttendance().getAbsentDayCount();

        AttendanceCommand attendanceCommand = new AttendanceCommand(absentNrics);

        CommandResult result = attendanceCommand.execute(model);
        String expectedMessage = String.format(AttendanceCommand.MESSAGE_SUCCESS, "0 person marked as absent.");
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(aliceOrininalWorkDayCount + 1, alice.getAttendance().getWorkDayCount());
        assertEquals(aliceOrininalAbsentDayCount, alice.getAttendance().getAbsentDayCount());
    }

    @Test
    public void equals() {
        AttendanceCommand attendanceCommand1 = new AttendanceCommand(Arrays.asList("T0123456A"));
        AttendanceCommand attendanceCommand2 = new AttendanceCommand(Arrays.asList("T0123456A"));
        AttendanceCommand attendanceCommand3 = new AttendanceCommand(Arrays.asList("T1234567B"));

        // same object -> returns true
        assertTrue(attendanceCommand1.equals(attendanceCommand1));

        // same values -> returns true
        assertTrue(attendanceCommand1.equals(attendanceCommand2));

        // different types -> returns false
        assertFalse(attendanceCommand1.equals(1));

        // null -> returns false
        assertFalse(attendanceCommand1.equals(null));

        // different NRIC lists -> returns false
        assertFalse(attendanceCommand1.equals(attendanceCommand3));
    }
}

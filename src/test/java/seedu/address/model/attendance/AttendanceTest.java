package seedu.address.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    public void constructor_defaultValues() {
        Attendance attendance = new Attendance();
        assertEquals(0, attendance.getWorkDayCount());
        assertEquals(0, attendance.getAbsentDayCount());
        assertEquals(100.0, attendance.getAttendanceRate());
    }

    @Test
    public void constructor_validInputs_success() {
        // Valid inputs
        Attendance attendance = new Attendance(10, 2);
        assertEquals(10, attendance.getWorkDayCount());
        assertEquals(2, attendance.getAbsentDayCount());
        assertEquals(80.0, attendance.getAttendanceRate());
    }

    @Test
    public void constructor_invalidWorkDayCount_throwsIllegalArgumentException() {
        // Negative work day count
        assertThrows(IllegalArgumentException.class, () -> new Attendance(-1, 0));
    }

    @Test
    public void constructor_invalidAbsentDayCount_throwsIllegalArgumentException() {
        // Negative absent day count
        assertThrows(IllegalArgumentException.class, () -> new Attendance(5, -1));
    }

    @Test
    public void constructor_absentExceedsWorkDays_throwsIllegalArgumentException() {
        // Absent days exceed work days
        assertThrows(IllegalArgumentException.class, () -> new Attendance(5, 6));
    }

    @Test
    public void isValidAttendance() {
        // valid cases
        assertTrue(Attendance.isValidAttendance(0, 0));
        assertTrue(Attendance.isValidAttendance(5, 0));
        assertTrue(Attendance.isValidAttendance(10, 5));
        assertTrue(Attendance.isValidAttendance(100, 50));

        // invalid cases
        assertFalse(Attendance.isValidAttendance(-1, 0));  // negative work days
        assertFalse(Attendance.isValidAttendance(5, -1));  // negative absent days
        assertFalse(Attendance.isValidAttendance(5, 6));   // absent > work days
    }

    @Test
    public void isValidWorkDayCount() {
        // valid cases
        assertTrue(Attendance.isValidWorkDayCount(0));
        assertTrue(Attendance.isValidWorkDayCount(1));
        assertTrue(Attendance.isValidWorkDayCount(100));

        // invalid cases
        assertFalse(Attendance.isValidWorkDayCount(-1));
        assertFalse(Attendance.isValidWorkDayCount(-100));
    }

    @Test
    public void isValidAbsentDayCount() {
        // valid cases
        assertTrue(Attendance.isValidAbsentDayCount(0));
        assertTrue(Attendance.isValidAbsentDayCount(1));
        assertTrue(Attendance.isValidAbsentDayCount(100));

        // invalid cases
        assertFalse(Attendance.isValidAbsentDayCount(-1));
        assertFalse(Attendance.isValidAbsentDayCount(-100));
    }

    @Test
    public void setWorkDayCount_validInput_success() {
        Attendance attendance = new Attendance(10, 2);
        double initialAttendanceRate = attendance.getAttendanceRate();
        attendance.setWorkDayCount(15);
        double finalAttendanceRate = attendance.getAttendanceRate();
        assertEquals(15, attendance.getWorkDayCount());
        assertNotEquals(initialAttendanceRate, finalAttendanceRate, 0.0);
    }

    @Test
    public void setWorkDayCount_invalidInput_throwsIllegalArgumentException() {
        Attendance attendance = new Attendance(10, 2);
        assertThrows(IllegalArgumentException.class, () -> attendance.setWorkDayCount(-1));
        assertThrows(IllegalArgumentException.class, () -> attendance.setWorkDayCount(1)); // absent > work days
    }

    @Test
    public void setAbsentDayCount_validInput_success() {
        Attendance attendance = new Attendance(10, 2);
        double initialAttendanceRate = attendance.getAttendanceRate();
        attendance.setAbsentDayCount(3);
        double finalAttendanceRate = attendance.getAttendanceRate();
        assertEquals(3, attendance.getAbsentDayCount());
        assertNotEquals(initialAttendanceRate, finalAttendanceRate, 0.0);
    }

    @Test
    public void setAbsentDayCount_invalidInput_throwsIllegalArgumentException() {
        Attendance attendance = new Attendance(10, 2);
        assertThrows(IllegalArgumentException.class, () -> attendance.setAbsentDayCount(-1));
        assertThrows(IllegalArgumentException.class, () -> attendance.setAbsentDayCount(11)); // absent > work days
    }

    @Test
    public void calculateAttendanceRate() {
        // No work days - should return 100%
        Attendance attendance1 = new Attendance(0, 0);
        assertEquals(100.0, attendance1.getAttendanceRate());

        // All present
        Attendance attendance2 = new Attendance(10, 0);
        assertEquals(100.0, attendance2.getAttendanceRate());

        // Half present
        Attendance attendance3 = new Attendance(10, 5);
        assertEquals(50.0, attendance3.getAttendanceRate());

        // Some present
        Attendance attendance4 = new Attendance(10, 3);
        assertEquals(70.0, attendance4.getAttendanceRate());
    }

    @Test
    public void incrementWorkDay() {
        Attendance attendance = new Attendance(10, 2);
        attendance.incrementWorkDay();
        assertEquals(11, attendance.getWorkDayCount());
    }

    @Test
    public void incrementAbsentDay() {
        Attendance attendance = new Attendance(10, 2);
        attendance.incrementAbsentDay();
        assertEquals(3, attendance.getAbsentDayCount());
    }

    @Test
    public void equals() {
        Attendance attendance = new Attendance(10, 2);

        // same values -> returns true
        assertTrue(attendance.equals(new Attendance(10, 2)));

        // same object -> returns true
        assertTrue(attendance.equals(attendance));

        // null -> returns false
        assertFalse(attendance.equals(null));

        // different types -> returns false
        assertFalse(attendance.equals(5.0f));

        // different workDayCount -> returns false
        assertFalse(attendance.equals(new Attendance(9, 2)));

        // different absentDayCount -> returns false
        assertFalse(attendance.equals(new Attendance(10, 3)));

        // both different -> returns false
        assertFalse(attendance.equals(new Attendance(9, 3)));
    }

    @Test
    public void toString_validInput_correctStringRepresentation() {
        Attendance attendance = new Attendance(10, 2);
        String expected =
                "Number of working days so far this year: 10; Number of days absent: 2; Attendance Rate: 80.0";
        assertEquals(expected, attendance.toString());
    }
}
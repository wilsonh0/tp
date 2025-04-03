package seedu.address.model.leave;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LeaveTest {
    // Test Leave Data
    private static final String VALID_START_DATE = "2025-03-10";
    private static final String VALID_END_DATE = "2025-03-15";
    private static final String VALID_REASON = "Annual Leave";
    private static final String VALID_FORMATTED_START_DATE = "10 Mar 2025";
    private static final String VALID_FORMATTED_END_DATE = "15 Mar 2025";

    // Alternate Test Data
    private static final String ALT_START_DATE = "2025-03-12";
    private static final String ALT_END_DATE = "2025-03-16";
    private static final String ALT_REASON = "Sick Leave";

    // Invalid Test Data
    private static final String INVALID_START_DATE = "2025/03/10";
    private static final String EARLIER_END_DATE = "2025-03-09"; // Earlier than start
    private static final String EMPTY_REASON = "";

    // Overlap Test Data
    private static final String OVERLAP_START_EARLY = "2025-03-08";
    private static final String OVERLAP_END_EARLY = "2025-03-12";
    private static final String OVERLAP_START_LATE = "2025-03-14";
    private static final String OVERLAP_END_LATE = "2025-03-17";
    private static final String NON_OVERLAP_START = "2025-03-05";
    private static final String NON_OVERLAP_END = "2025-03-08";

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Leave(null, VALID_END_DATE, VALID_REASON));
        assertThrows(NullPointerException.class, () ->
                new Leave(VALID_START_DATE, null, VALID_REASON));
        assertThrows(NullPointerException.class, () ->
                new Leave(VALID_START_DATE, VALID_END_DATE, null));
    }

    @Test
    public void constructor_invalidDateOrder_throwsIllegalArgumentException() {
        // End date before start date
        assertThrows(IllegalArgumentException.class, () ->
                new Leave(VALID_START_DATE, EARLIER_END_DATE, VALID_REASON));
    }

    @Test
    public void constructor_emptyReason_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Leave(VALID_START_DATE, VALID_END_DATE, EMPTY_REASON));
    }

    @Test
    public void isValidLeave() {
        assertTrue(Leave.isValidLeave(VALID_START_DATE, VALID_END_DATE, VALID_REASON));
        assertFalse(Leave.isValidLeave(VALID_START_DATE, INVALID_START_DATE, VALID_REASON));
        assertFalse(Leave.isValidLeave(INVALID_START_DATE, VALID_END_DATE, VALID_REASON));
        assertFalse(Leave.isValidLeave(VALID_START_DATE, VALID_END_DATE, EMPTY_REASON));
    }

    @Test
    public void overlaps() {
        Leave baseLeave = new Leave(VALID_START_DATE, VALID_END_DATE, VALID_REASON);

        // Exact same dates
        assertTrue(baseLeave.overlaps(new Leave(VALID_START_DATE, VALID_END_DATE, ALT_REASON)));

        // Overlapping cases
        assertTrue(baseLeave.overlaps(new Leave(OVERLAP_START_EARLY, OVERLAP_END_EARLY, ALT_REASON)));
        assertTrue(baseLeave.overlaps(new Leave(OVERLAP_START_LATE, OVERLAP_END_LATE, ALT_REASON)));

        // Non-overlapping cases
        assertFalse(baseLeave.overlaps(new Leave(NON_OVERLAP_START, NON_OVERLAP_END, ALT_REASON)));
    }

    @Test
    public void equals() {
        // equals() only checks for start date
        Leave baseLeave = new Leave(VALID_START_DATE, VALID_END_DATE, VALID_REASON);
        Leave sameLeave = new Leave(VALID_START_DATE, VALID_END_DATE, VALID_REASON);
        Leave differentEndLeave = new Leave(VALID_START_DATE, ALT_END_DATE, VALID_REASON);
        Leave differentStartLeave = new Leave(ALT_START_DATE, VALID_END_DATE, VALID_REASON);
        Leave differentReasonLeave = new Leave(VALID_START_DATE, VALID_END_DATE, ALT_REASON);

        assertTrue(baseLeave.equals(baseLeave));
        assertTrue(baseLeave.equals(sameLeave));
        assertTrue(baseLeave.equals(differentEndLeave));
        assertFalse(baseLeave.equals(differentStartLeave));
        assertTrue(baseLeave.equals(differentReasonLeave));
        assertFalse(baseLeave.equals(null));
        assertFalse(baseLeave.equals(1));
    }

    @Test
    public void toStringMethod() {
        Leave leave = new Leave(VALID_START_DATE, VALID_END_DATE, VALID_REASON);
        String expected = VALID_START_DATE + " to " + VALID_END_DATE + " (" + VALID_REASON + ")";
        assertEquals(expected, leave.toString());
    }

    @Test
    public void formattedDates() {
        Leave leave = new Leave(VALID_START_DATE, VALID_END_DATE, VALID_REASON);
        assertEquals(VALID_FORMATTED_START_DATE, leave.getFormattedStartDate());
        assertEquals(VALID_FORMATTED_END_DATE, leave.getFormattedEndDate());
    }

    @Test
    public void testDateValidation() {
        assertFalse(Leave.isValidDate("2025-02-30")); // February 30th should be invalid
        assertFalse(Leave.isValidDate("2025-04-31")); // April 31st should be invalid
        assertFalse(Leave.isValidDate("2025-14-01")); // Invalid month
        assertFalse(Leave.isValidDate("abcd-ef-gh")); // Invalid input
        assertFalse(Leave.isValidDate("01-01-2025")); // Invalid format
        assertFalse(Leave.isValidDate(null)); // Null input
        assertFalse(Leave.isValidDate("")); // Empty string
        assertTrue(Leave.isValidDate("2025-02-28")); // Valid date

        // Leap year - If a year is fully divisible by 4, it is a leap year, unless it is also divisible by 100.
        // e.g. 1600, 2000, 2400 are leap years, but 1700, 1800, 1900 are not.

        assertFalse(Leave.isValidDate("1900-02-29")); // Invalid leap year
        assertTrue(Leave.isValidDate("2000-02-29")); // Valid leap year

        // Test for years outside the valid range
        assertFalse(Leave.isValidDate("1899-12-30")); // Invalid date before MIN_YEAR
        assertTrue(Leave.isValidDate("1900-01-01")); // Valid date on MIN_YEAR

        assertFalse(Leave.isValidDate("2101-01-01")); // Invalid date after MAX_YEAR
        assertTrue(Leave.isValidDate("2100-12-30")); // Valid date on MAX_YEAR
    }

    @Test
    public void testDateDuration() {
        assertTrue(Leave.isValidDuration("2025-01-01", "2025-01-30")); // Valdi duration (30 days)
        assertFalse(Leave.isValidDuration("2025-01-01", "2025-01-31")); // Invalid duration (30 days)
    }
}

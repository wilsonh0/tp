package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;

class JsonAdaptedAttendanceTest {
    private static final int VALID_WORK_DAYS = 10;
    private static final int VALID_ABSENT_DAYS = 2;
    private static final int INVALID_NEGATIVE_WORK_DAYS = -1;
    private static final int INVALID_NEGATIVE_ABSENT_DAYS = -1;
    private static final int INVALID_ABSENT_EXCEEDS_WORK_DAYS = 11;

    @Test
    void toModelType_validAttendance_success() throws Exception {
        JsonAdaptedAttendance jsonAttendance = new JsonAdaptedAttendance(VALID_WORK_DAYS, VALID_ABSENT_DAYS);
        Attendance expectedAttendance = new Attendance(VALID_WORK_DAYS, VALID_ABSENT_DAYS);

        assertEquals(expectedAttendance, jsonAttendance.toModelType());
    }

    @Test
    void toModelType_defaultConstructor_success() throws Exception {
        JsonAdaptedAttendance jsonAttendance = new JsonAdaptedAttendance();
        Attendance expectedAttendance = new Attendance(0, 0);

        assertEquals(expectedAttendance, jsonAttendance.toModelType());
    }

    @Test
    void toModelType_fromAttendanceSource_success() throws Exception {
        Attendance sourceAttendance = new Attendance(VALID_WORK_DAYS, VALID_ABSENT_DAYS);
        JsonAdaptedAttendance jsonAttendance = new JsonAdaptedAttendance(sourceAttendance);

        assertEquals(sourceAttendance, jsonAttendance.toModelType());
    }

    @Test
    void toModelType_negativeWorkDays_throwsIllegalValueException() {
        JsonAdaptedAttendance jsonAttendance = new JsonAdaptedAttendance(
                INVALID_NEGATIVE_WORK_DAYS, VALID_ABSENT_DAYS);
        assertThrows(IllegalValueException.class, jsonAttendance::toModelType);
    }

    @Test
    void toModelType_negativeAbsentDays_throwsIllegalValueException() {
        JsonAdaptedAttendance jsonAttendance = new JsonAdaptedAttendance(
                VALID_WORK_DAYS, INVALID_NEGATIVE_ABSENT_DAYS);
        assertThrows(IllegalValueException.class, jsonAttendance::toModelType);
    }

    @Test
    void toModelType_absentExceedsWorkDays_throwsIllegalValueException() {
        JsonAdaptedAttendance jsonAttendance = new JsonAdaptedAttendance(
                VALID_WORK_DAYS, INVALID_ABSENT_EXCEEDS_WORK_DAYS);
        assertThrows(IllegalValueException.class, jsonAttendance::toModelType);
    }
}
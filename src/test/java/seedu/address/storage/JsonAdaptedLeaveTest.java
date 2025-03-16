package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.leave.Leave;



class JsonAdaptedLeaveTest {

    private static final String VALID_START_DATE = "2025-03-10";
    private static final String VALID_END_DATE = "2025-03-12";
    private static final String VALID_REASON = "Sick Leave";

    private static final String INVALID_START_DATE = "invalid-date";
    private static final String INVALID_END_DATE = "2025-02-28"; // Before start date
    private static final DateTimeFormatter DateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Test
    void toModelType_validLeave_success() throws Exception {
        JsonAdaptedLeave jsonLeave = new JsonAdaptedLeave(VALID_START_DATE, VALID_END_DATE, VALID_REASON);
        Leave expectedLeave = new Leave(VALID_START_DATE, VALID_END_DATE, VALID_REASON);

        assertEquals(expectedLeave, jsonLeave.toModelType());
    }

    @Test
    void toModelType_missingStartDate_throwsIllegalValueException() {
        JsonAdaptedLeave jsonLeave = new JsonAdaptedLeave(null, VALID_END_DATE, VALID_REASON);
        assertThrows(IllegalValueException.class, jsonLeave::toModelType);
    }

    @Test
    void toModelType_missingEndDate_throwsIllegalValueException() {
        JsonAdaptedLeave jsonLeave = new JsonAdaptedLeave(VALID_START_DATE, null, VALID_REASON);
        assertThrows(IllegalValueException.class, jsonLeave::toModelType);
    }

    @Test
    void toModelType_missingReason_throwsIllegalValueException() {
        JsonAdaptedLeave jsonLeave = new JsonAdaptedLeave(VALID_START_DATE, VALID_END_DATE, null);
        assertThrows(IllegalValueException.class, jsonLeave::toModelType);
    }

    @Test
    void toModelType_invalidStartDateFormat_throwsIllegalValueException() {
        JsonAdaptedLeave jsonLeave = new JsonAdaptedLeave(INVALID_START_DATE, VALID_END_DATE, VALID_REASON);
        assertThrows(IllegalValueException.class, jsonLeave::toModelType);
    }

    @Test
    void toModelType_endDateBeforeStartDate_throwsIllegalValueException() {
        JsonAdaptedLeave jsonLeave = new JsonAdaptedLeave(VALID_END_DATE, INVALID_END_DATE, VALID_REASON);
        assertThrows(IllegalValueException.class, jsonLeave::toModelType);
    }
}

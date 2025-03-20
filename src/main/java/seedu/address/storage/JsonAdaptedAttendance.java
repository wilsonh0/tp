package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;

/**
 * Jackson-friendly version of {@link Attendance}.
 */
public class JsonAdaptedAttendance {

    private final int absentDayCount;
    private final int workDayCount;

    /**
     * Constructs a {@code JsonAdaptedAttendance} with the given attendance details.
     */
    @JsonCreator
    public JsonAdaptedAttendance(@JsonProperty("absentDayCount") int absentDayCount,
                                 @JsonProperty("workDayCount") int workDayCount) {
        this.absentDayCount = absentDayCount;
        this.workDayCount = workDayCount;
    }

    /**
     * Converts a given {@code Attendance} into this class for Jackson use.
     */
    public JsonAdaptedAttendance(Attendance source) {
        this.absentDayCount = source.getAbsentDayCount();
        this.workDayCount = source.getWorkDayCount();
    }

    /**
     * Converts this Jackson-friendly adapted attendance object into the model's {@code Attendance} object.
     *
     * @throws IllegalValueException if data constraints are violated.
     */
    public Attendance toModelType() throws IllegalValueException {
        if (absentDayCount < 0) {
            throw new IllegalValueException("Absent day count cannot be negative.");
        }
        if (workDayCount < 0) {
            throw new IllegalValueException("Work day count cannot be negative.");
        }

        // Safely reset the static workDayCount to the stored value.
        Attendance.setWorkDayCount(workDayCount);

        Attendance reconstructedAttendance = new Attendance();
        for (int i = 0; i < absentDayCount; i++) {
            reconstructedAttendance.incrementAbsentDay();
        }

        return reconstructedAttendance;
    }
}

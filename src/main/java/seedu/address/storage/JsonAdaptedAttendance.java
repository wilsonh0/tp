package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;

/**
 * Jackson-friendly version of {@link Attendance}.
 */
public class JsonAdaptedAttendance {

    private final int workDayCount;
    private final int absentDayCount;


    public JsonAdaptedAttendance() {
        this(0, 0); // Default to empty attendance
    }

    /**
     * Constructs a {@code JsonAdaptedAttendance} with the given attendance details.
     */
    @JsonCreator
    public JsonAdaptedAttendance(@JsonProperty("workDayCount") int workDayCount,
                                 @JsonProperty("absentDayCount") int absentDayCount) {
        this.workDayCount = workDayCount;
        this.absentDayCount = absentDayCount;
    }

    /**
     * Converts a given {@code Attendance} into this class for Jackson use.
     */
    public JsonAdaptedAttendance(Attendance source) {
        requireNonNull(source);
        this.workDayCount = source.getWorkDayCount();
        this.absentDayCount = source.getAbsentDayCount();
    }

    /**
     * Converts this Jackson-friendly adapted attendance object into the model's {@code Attendance} object.
     *
     * @throws IllegalValueException if data constraints are violated.
     */
    public Attendance toModelType() throws IllegalValueException {
        if (workDayCount < 0 || absentDayCount < 0 || workDayCount < absentDayCount) {
            throw new IllegalValueException(Attendance.MESSAGE_CONSTRAINTS);
        }

        Attendance reconstructedAttendance = new Attendance(this.workDayCount, this.absentDayCount);
        return reconstructedAttendance;
    }
}

package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.leave.Leave;

/**
 * Jackson-friendly version of {@link Leave}.
 */
public class JsonAdaptedLeave {

    private final String startDate;
    private final String endDate;
    private final String reason;

    /**
     * Constructs a {@code JsonAdaptedLeave} with the given leave details.
     */
    @JsonCreator
    public JsonAdaptedLeave(@JsonProperty("startDate") String startDate,
                            @JsonProperty("endDate") String endDate,
                            @JsonProperty("reason") String reason) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }

    /**
     * Converts a given {@code Leave} into this class for Jackson use.
     */
    public JsonAdaptedLeave(Leave source) {
        this.startDate = source.getStartDate().toString();
        this.endDate = source.getEndDate().toString();
        this.reason = source.getReason();
    }

    /**
     * Converts this Jackson-friendly adapted attendance object into the model's {@code Attendance} object.
     *
     * @throws IllegalValueException if data constraints are violated, specifically:
     *         - If workDayCount is negative
     *         - If absentDayCount is negative
     *         - If workDayCount is less than absentDayCount
     */
    public Leave toModelType() throws IllegalValueException {
        if (!Leave.isValidLeave(startDate, endDate, reason)) {
            throw new IllegalValueException(Leave.DATE_CONSTRAINTS);
        }
        return new Leave(startDate, endDate, reason);
    }
}

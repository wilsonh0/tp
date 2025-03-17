package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Leave in the address book.
 */
public class Leave {
    public static final String MESSAGE_CONSTRAINTS = "Reason should not be empty.";
    public static final String DATE_CONSTRAINTS = "Dates should be in the format of yyyy-MM-dd. "
            + "Start date should be before end date.";
    private static final DateTimeFormatter DateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String reason;

    /**
     * Constructs a {@code Leave}.
     *
     * @param startDate A valid start date.
     * @param endDate   A valid end date.
     * @param reason     A valid reason.
     */
    public Leave(String startDate, String endDate, String reason) {
        requireNonNull(startDate);
        requireNonNull(endDate);
        requireNonNull(reason);
        checkArgument(isValidLeave(startDate, endDate, reason), DATE_CONSTRAINTS);
        this.startDate = LocalDate.parse(startDate, DateFormatter);
        this.endDate = LocalDate.parse(endDate, DateFormatter);
        this.reason = reason;
    }

    /**
     * Returns true if a given string is a valid leave.
     */
    public static boolean isValidLeave(String startDateString, String endDateString, String reason) {
        if (!isValidDate(startDateString) || !isValidDate(endDateString)) {
            return false;
        }

        if (!isValidReason(reason)) {
            return false;
        }

        return LocalDate.parse(startDateString, DateFormatter).isBefore(LocalDate.parse(endDateString, DateFormatter));
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DateFormatter);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if a given string is a valid reason.
     * A valid reason is a non-empty string.
     */
    public static boolean isValidReason(String reason) {
        // Reason should not be empty or null
        return reason != null && !reason.isEmpty();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getReason() {
        return reason;
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Leave)) {
            return false;
        }

        Leave otherLeave = (Leave) other;
        return startDate.equals(otherLeave.startDate)
                && endDate.equals(otherLeave.endDate)
                && reason.equals(otherLeave.reason);
    }
    @Override
    public int hashCode() {
        return startDate.hashCode() + endDate.hashCode() + reason.hashCode();
    }
    @Override
    public String toString() {
        return "Start Date: " + startDate + " End Date: " + endDate + " Reason: " + reason;
    }
}

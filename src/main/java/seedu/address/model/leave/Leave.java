package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * Represents a Leave in the address book.
 */
public class Leave {
    public static final int MIN_YEAR = 1900;
    public static final int MAX_YEAR = 2100;

    public static final String DATE_CONSTRAINTS =
            "Please ensure your dates meet all these requirements:\n"
                    + "1. FORMAT: Must use yyyy-MM-dd (e.g., 2025-02-28)\n"
                    + "2. YEAR RANGE: Must be between " + MIN_YEAR + " and " + MAX_YEAR + " (inclusive)\n"
                    + "3. VALID DATE: Must be a real calendar date\n"
                    + "   - February has 28 days (29 in leap years)\n"
                    + "   - April, June, September, November have 30 days\n"
                    + "   - Other months have 31 days\n"
                    + "4. DATE ORDER: Start date must be on or before end date";

    public static final String REASON_CONSTRAINTS = "Reason should not be empty.";


    public static final DateTimeFormatter DATE_FORMATER = DateTimeFormatter
            .ofPattern("uuuu-MM-dd")
            .withResolverStyle(ResolverStyle.STRICT);

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
        this.startDate = LocalDate.parse(startDate, DATE_FORMATER);
        this.endDate = LocalDate.parse(endDate, DATE_FORMATER);
        this.reason = reason;
    }

    /**
     * Returns true if a given string is a valid leave.
     */
    public static boolean isValidLeave(String startDate, String endDate, String reason) {
        return isValidDate(startDate)
                && isValidDate(endDate)
                && isValidReason(reason)
                && isValidOrder(startDate, endDate);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date, DATE_FORMATER);
            int year = parsedDate.getYear();

            return year >= MIN_YEAR && year <= MAX_YEAR;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns true if a given string is a valid reason.
     * A valid reason is a non-empty string.
     */
    public static boolean isValidReason(String reason) {
        // Reason should not be empty or null
        return reason != null && !reason.isEmpty();
    }

    /**
     * Returns true if the start date is before or equal to the end date.
     */
    public static boolean isValidOrder(String startDate, String endDate) {
        // startDate <= endDate
        return !LocalDate.parse(startDate, DATE_FORMATER).isAfter(LocalDate.parse(endDate, DATE_FORMATER));
    }

    /**
     * Returns true if this leave overlaps with another leave.
     */
    public boolean overlaps(Leave other) {
        // startDate <= other.endDate && endDate >= other.startDate
        return (startDate.isBefore(other.endDate) || startDate.isEqual(other.endDate))
                && (endDate.isAfter(other.startDate) || endDate.isEqual(other.startDate));
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

    public String getFormattedStartDate() {
        return startDate.format(DateTimeFormatter.ofPattern("dd MMM uuuu"));
    }

    public String getFormattedEndDate() {
        return endDate.format(DateTimeFormatter.ofPattern("dd MMM uuuu"));
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
        return startDate.equals(otherLeave.startDate);
    }

    @Override
    public int hashCode() {
        return startDate.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s to %s (%s)", startDate.format(DATE_FORMATER), endDate.format(DATE_FORMATER), reason);
    }
}

package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents a Person's date of hire in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHire(String)}
 */
public class Hire {
    public static final int MIN_YEAR = 1900;
    public static final int MAX_YEAR = 2099;

    public static final String MESSAGE_CONSTRAINTS =
            "Hire date must meet all these requirements:\n"
                    + "1. FORMAT: Must use yyyy-MM-dd (e.g., 2025-02-28)\n"
                    + "2. YEAR RANGE: Must be between " + MIN_YEAR + " and " + MAX_YEAR + " (inclusive)\n"
                    + "3. VALID DATE: Must be a real calendar date\n"
                    + "   - February has 28 days (29 in leap years)\n"
                    + "   - April, June, September, November have 30 days\n"
                    + "   - Other months have 31 days\n";
    private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(2099, 12, 31);

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd")
            .withResolverStyle(ResolverStyle.STRICT);

    public final String hire;

    /**
     * Constructs a {@code Hire}.
     *
     * @param hire A valid hire date.
     */
    public Hire(String hire) {
        requireNonNull(hire);
        checkArgument(isValidHire(hire), MESSAGE_CONSTRAINTS);
        this.hire = hire;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidHire(String test) {
        try {
            LocalDate date = LocalDate.parse(test, DATE_FORMATTER);
            return !date.isBefore(MIN_DATE) && !date.isAfter(MAX_DATE);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns the hire date as a LocalDate object.
     *
     * @return LocalDate representation of the hire date.
     */
    public LocalDate toLocalDate() {
        return LocalDate.parse(hire, DATE_FORMATTER);
    }

    @Override
    public String toString() {
        return hire;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Hire)) {
            return false;
        }

        Hire otherHire = (Hire) other;
        return hire.equals(otherHire.hire);
    }

    @Override
    public int hashCode() {
        return hire.hashCode();
    }

}

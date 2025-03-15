package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's date of hire in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHire(String)}
 */
public class Hire {

    public static final String MESSAGE_CONSTRAINTS =
            "Hire date must be in the format of YYYY-MM-DD and must be a valid date (between 1900-2099).";

    /*
     * Regex to ensure the date follows the YYYY-MM-DD format and
     * the first character of the address must not be a whitespace.
     * - (?:19|20)\d{2} ensures a valid year between 1900-2099.
     * - (0[1-9]|1[0-2]) ensures a valid month (01-12).
     * - (0[1-9]|[12][0-9]|3[01]) ensures a valid day (01-31).
     */
    public static final String VALIDATION_REGEX = "^(?:19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

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
        return test.matches(VALIDATION_REGEX);
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

package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHire(String)}
 */
public class Hire {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[0-9-]+$";

    public final String date;

    /**
     * Constructs a {@code Hire}.
     *
     * @param hire A valid hire date.
     */
    public Hire(String hire) {
        requireNonNull(hire);
        checkArgument(isValidHire(hire), MESSAGE_CONSTRAINTS);
        this.date = hire;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidHire(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return date;
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
        return date.equals(otherHire.date);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}

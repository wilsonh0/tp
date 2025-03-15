package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNric(String)}
 */
public class Nric {

    public static final String MESSAGE_CONSTRAINTS = "NRIC should begin with either 'S' or 'T', "
            + "followed by 7 numerical characters, "
            + "and end with 1 uppercase alphabetical character (e.g., S7654321A). "
            + "It should not be blank.";

    /**
     * Regex to validate a Singapore NRIC.
     * - The first character must be 'S' or 'T', representing the issuance year.
     * - Followed by exactly 7 numerical digits.
     * - Ends with a single uppercase alphabetical character .
     * - Ensures no leading or trailing spaces.
     * - Example of a valid NRIC: "S1234567A".
     */
    public static final String VALIDATION_REGEX = "^[ST]\\d{7}[A-Z]$";

    public final String nric;

    /**
     * Constructs a {@code Nric}.
     *
     * @param nric A valid nric.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_CONSTRAINTS);
        this.nric = nric;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidNric(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return nric;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Nric)) {
            return false;
        }

        Nric otherNric = (Nric) other;
        return nric.equals(otherNric.nric);
    }

    @Override
    public int hashCode() {
        return nric.hashCode();
    }

}

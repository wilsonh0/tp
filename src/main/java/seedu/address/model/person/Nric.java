package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's NRIC in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNric(String)}
 */
public class Nric {

    public static final String MESSAGE_CONSTRAINTS = "NRIC should begin with either 'S', 'T', 'F', 'G', or 'M' "
        + "(case-insensitive), "
        + "followed by 7 numerical characters, "
        + "and end with 1 alphabetical character (case-insensitive). "
        + "E.g., S7654321A, t1234567b. "
        + "It should not be blank.";

    /**
     * Regex to validate a Singapore NRIC.
     * - The first character must be 'S', 'T', 'F', 'G', or 'M' (case-insensitive)
     * - Followed by exactly 7 numerical digits
     * - Ends with a single alphabetical character (case-insensitive)
     * - Example of valid NRICs: "S1234567A", "t1234567b", "F9876543C"
     */
    public static final String VALIDATION_REGEX = "^[STFGMstfgm]\\d{7}[A-Za-z]$";

    public final String nric;

    /**
     * Constructs a {@code Nric}.
     *
     * @param nric A valid nric (will be converted to uppercase).
     */
    public Nric(String nric) {
        requireNonNull(nric);
        String uppercasedNric = nric.toUpperCase();
        checkArgument(isValidNric(nric), MESSAGE_CONSTRAINTS);
        this.nric = uppercasedNric; // Store in uppercase
    }

    /**
     * Returns true if a given string is a valid NRIC.
     */
    public static boolean isValidNric(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getNric() {
        return this.nric;
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

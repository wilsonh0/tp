package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remark in the address book.
 */
public class Remark {
    public final String value;

    /**
     * Constructs a {@code Remark}.
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        boolean result;
        if (other == this) {
            result = true;
        } else if (!(other instanceof Remark otherRemark)) {
            result = false;
        } else {
            result = value.equals(otherRemark.value);
        }

        return result;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Nric} matches the nric given.
 */
public class NricMatchesPredicate implements Predicate<Person> {
    private final Nric nric;

    public NricMatchesPredicate(Nric nric) {
        this.nric = nric;
    }

    /**
     * Retrieves nric
     */
    public Nric getNric() {
        return nric;
    }

    @Override
    public boolean test(Person person) {
        return person.getNric().equals(nric);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NricMatchesPredicate // instanceof handles nulls
                && nric.equals(((NricMatchesPredicate) other).nric)); // state check
    }
}

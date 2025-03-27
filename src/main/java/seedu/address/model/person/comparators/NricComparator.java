package seedu.address.model.person.comparators;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * A comparator that compares {@link Person} objects based on their nric field.
 * The comparison is case-insensitive ("S123" and "s123" are considered equal).
 */
public class NricComparator implements Comparator<Person> {
    /**
     * Compares two Person objects based on their nric.
     *
     * @param a the first Person to compare
     * @param b the second Person to compare
     * @return a negative integer, zero, or a positive integer as the first argument's nric
     *         is less than, equal to, or greater than the second's
     */
    @Override
    public int compare(Person a, Person b) {
        String nric1 = a.getNric().toString().toLowerCase();
        String nric2 = b.getNric().toString().toLowerCase();
        return nric1.compareTo(nric2);
    }
}

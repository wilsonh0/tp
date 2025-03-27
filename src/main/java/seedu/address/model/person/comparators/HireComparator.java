package seedu.address.model.person.comparators;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * A comparator that compares {@link Person} objects based on their hire date field.
 * The comparison assumes the hire date is stored as a string in a consistent format (e.g., ISO 8601).
 */
public class HireComparator implements Comparator<Person> {
    /**
     * Compares two Person objects based on their hire date.
     *
     * @param a the first Person to compare
     * @param b the second Person to compare
     * @return a negative integer, zero, or a positive integer as the first argument's hire date
     *         is less than, equal to, or greater than the second's
     */
    @Override
    public int compare(Person a, Person b) {
        return a.getHire().toString().compareTo(b.getHire().toString());
    }

}

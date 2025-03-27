package seedu.address.model.person.comparators;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * A comparator that compares {@link Person} objects based on their phone number field.
 * The comparison is done using the natural ordering of strings.
 */
public class PhoneComparator implements Comparator<Person> {
    /**
     * Compares two Person objects based on their phone number.
     *
     * @param a the first Person to compare
     * @param b the second Person to compare
     * @return a negative integer, zero, or a positive integer as the first argument's phone number
     *         is less than, equal to, or greater than the second's
     */
    @Override
    public int compare(Person a, Person b) {
        return a.getPhone().toString().compareTo(b.getPhone().toString());
    }
}
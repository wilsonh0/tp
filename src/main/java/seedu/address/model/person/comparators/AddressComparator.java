package seedu.address.model.person.comparators;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * A comparator that compares {@link Person} objects based on their address field.
 * The comparison is case-insensitive, meaning "Main Street" and "main street" would be considered equal.
 */
public class AddressComparator implements Comparator<Person> {
    /**
     * Compares two Person objects based on their address.
     *
     * @param a the first Person to compare
     * @param b the second Person to compare
     * @return a negative integer, zero, or a positive integer as the first argument's address
     *         is less than, equal to, or greater than the second's, ignoring case considerations
     */
    @Override
    public int compare(Person a, Person b) {
        String address1 = a.getAddress().toString().toLowerCase();
        String address2 = b.getAddress().toString().toLowerCase();
        return address1.compareTo(address2);
    }
}

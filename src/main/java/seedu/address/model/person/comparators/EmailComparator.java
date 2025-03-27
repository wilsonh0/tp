package seedu.address.model.person.comparators;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * A comparator that compares {@link Person} objects based on their email field.
 * The comparison is case-insensitive, as email addresses are typically case-insensitive.
 */
public class EmailComparator implements Comparator<Person> {
    /**
     * Compares two Person objects based on their email address.
     *
     * @param a the first Person to compare
     * @param b the second Person to compare
     * @return a negative integer, zero, or a positive integer as the first argument's email
     *         is less than, equal to, or greater than the second's, ignoring case considerations
     */
    @Override
    public int compare(Person a, Person b) {
        String email1 = a.getEmail().toString().toLowerCase();
        String email2 = b.getEmail().toString().toLowerCase();
        return email1.compareTo(email2);
    }

}

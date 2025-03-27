package seedu.address.model.person.comparators;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * A comparator that compares {@link Person} objects based on their name field.
 * The comparison is case-insensitive, meaning "John" and "john" would be considered equal.
 * The comparison is done using the natural ordering of strings.
 */
public class NameComparator implements Comparator<Person> {
    /**
     * Compares two Person objects based on their name.
     *
     * @param a the first Person to compare
     * @param b the second Person to compare
     * @return a negative integer, zero, or a positive integer as the first argument's name
     *         is less than, equal to, or greater than the second's, ignoring case considerations
     */
    @Override
    public int compare(Person a, Person b) {
        String name1 = a.getName().toString().toLowerCase();
        String name2 = b.getName().toString().toLowerCase();
        return name1.compareTo(name2);
    }
}

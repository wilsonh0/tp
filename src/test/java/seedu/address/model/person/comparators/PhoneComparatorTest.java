package seedu.address.model.person.comparators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PhoneComparatorTest {
    private final PhoneComparator comparator = new PhoneComparator();

    @Test
    public void compare_equalPhones_returnsZero() {
        Person person1 = new PersonBuilder().withPhone("12345678").build();
        Person person2 = new PersonBuilder().withPhone("12345678").build();
        assertEquals(0, comparator.compare(person1, person2));
    }

    @Test
    public void compare_differentPhones_returnsCorrectComparison() {
        Person person1 = new PersonBuilder().withPhone("11111111").build();
        Person person2 = new PersonBuilder().withPhone("22222222").build();
        assertTrue(comparator.compare(person1, person2) < 0);
    }

    @Test
    public void compare_phoneNumberFormats() {
        Person person1 = new PersonBuilder().withPhone("12345678").build();
        Person person2 = new PersonBuilder().withPhone("87654321").build();
        assertTrue(comparator.compare(person1, person2) < 0);
    }
}

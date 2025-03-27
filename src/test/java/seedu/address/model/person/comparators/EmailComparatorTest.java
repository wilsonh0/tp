package seedu.address.model.person.comparators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class EmailComparatorTest {
    private final EmailComparator comparator = new EmailComparator();

    @Test
    public void compare_equalEmail_returnsZero() {
        Person person1 = new PersonBuilder().withEmail("test@example.com").build();
        Person person2 = new PersonBuilder().withEmail("test@example.com").build();
        assertEquals(0, comparator.compare(person1, person2));
    }

    @Test
    public void compare_differentEmail_returnsCorrectComparison() {
        Person person1 = new PersonBuilder().withEmail("a@example.com").build();
        Person person2 = new PersonBuilder().withEmail("b@example.com").build();
        assertTrue(comparator.compare(person1, person2) < 0);
    }

    @Test
    public void compare_caseInsensitiveComparison() {
        Person person1 = new PersonBuilder().withEmail("test@example.com").build();
        Person person2 = new PersonBuilder().withEmail("TEST@EXAMPLE.COM").build();
        assertEquals(0, comparator.compare(person1, person2));
    }
}

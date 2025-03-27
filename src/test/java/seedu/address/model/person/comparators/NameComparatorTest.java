package seedu.address.model.person.comparators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class NameComparatorTest {
    private final NameComparator comparator = new NameComparator();

    @Test
    public void compare_equalNames_returnsZero() {
        Person person1 = new PersonBuilder().withName("Alice").build();
        Person person2 = new PersonBuilder().withName("Alice").build();
        assertEquals(0, comparator.compare(person1, person2));
    }

    @Test
    public void compare_differentNames_returnsCorrectComparison() {
        Person person1 = new PersonBuilder().withName("Alice").build();
        Person person2 = new PersonBuilder().withName("Bob").build();
        assertTrue(comparator.compare(person1, person2) < 0);
    }

    @Test
    public void compare_caseInsensitiveComparison() {
        Person person1 = new PersonBuilder().withName("alice").build();
        Person person2 = new PersonBuilder().withName("ALICE").build();
        assertEquals(0, comparator.compare(person1, person2));
    }
}

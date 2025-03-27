package seedu.address.model.person.comparators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class NricComparatorTest {
    private final NricComparator comparator = new NricComparator();

    @Test
    public void compare_equalNrics_returnsZero() {
        Person person1 = new PersonBuilder().withNric("S1234567A").build();
        Person person2 = new PersonBuilder().withNric("S1234567A").build();
        assertEquals(0, comparator.compare(person1, person2));
    }

    @Test
    public void compare_differentNrics_returnsCorrectComparison() {
        Person person1 = new PersonBuilder().withNric("S1234567A").build();
        Person person2 = new PersonBuilder().withNric("T1234567B").build();
        assertTrue(comparator.compare(person1, person2) < 0);
    }

    @Test
    public void compare_caseInsensitiveComparison() {
        Person person1 = new PersonBuilder().withNric("s1234567a").build();
        Person person2 = new PersonBuilder().withNric("S1234567A").build();
        assertEquals(0, comparator.compare(person1, person2));
    }
}

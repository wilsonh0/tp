package seedu.address.model.person.comparators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class HireComparatorTest {
    private final HireComparator comparator = new HireComparator();

    @Test
    public void compare_equalHireDate_returnsZero() {
        Person person1 = new PersonBuilder().withHire("2020-01-01").build();
        Person person2 = new PersonBuilder().withHire("2020-01-01").build();
        assertEquals(0, comparator.compare(person1, person2));
    }

    @Test
    public void compare_earlierDateFirst_returnsNegative() {
        Person person1 = new PersonBuilder().withHire("2020-01-01").build();
        Person person2 = new PersonBuilder().withHire("2021-01-01").build();
        assertTrue(comparator.compare(person1, person2) < 0);
    }

    @Test
    public void compare_laterDateFirst_returnsPositive() {
        Person person1 = new PersonBuilder().withHire("2021-01-01").build();
        Person person2 = new PersonBuilder().withHire("2020-01-01").build();
        assertTrue(comparator.compare(person1, person2) > 0);
    }
}

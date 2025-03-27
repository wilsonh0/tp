package seedu.address.model.person.comparators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddressComparatorTest {
    private final AddressComparator comparator = new AddressComparator();

    @Test
    public void compare_equalAddress_returnsZero() {
        Person person1 = new PersonBuilder().withAddress("123 Main St").build();
        Person person2 = new PersonBuilder().withAddress("123 Main St").build();
        assertEquals(0, comparator.compare(person1, person2));
    }

    @Test
    public void compare_differentAddress_returnsCorrectComparison() {
        Person person1 = new PersonBuilder().withAddress("123 Main St").build();
        Person person2 = new PersonBuilder().withAddress("456 Oak Ave").build();
        assertTrue(comparator.compare(person1, person2) < 0);
    }

    @Test
    public void compare_caseInsensitiveComparison() {
        Person person1 = new PersonBuilder().withAddress("main street").build();
        Person person2 = new PersonBuilder().withAddress("MAIN STREET").build();
        assertEquals(0, comparator.compare(person1, person2));
    }
}

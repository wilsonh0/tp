package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Hire;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Tag;
import seedu.address.model.person.TagSet;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Nric("T0000001A"), new Phone("87438807"),
                new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                new Hire("2025-01-01"), getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Nric("T0000002A"), new Phone("99272758"),
                new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new Hire("2025-01-02"), getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Nric("T0000003A"), new Phone("93210283"),
                new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new Hire("2025-01-03"), getTagSet("neighbours")),
            new Person(new Name("David Li"), new Nric("T0000004A"), new Phone("91031282"),
                new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new Hire("2025-01-04"), getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Nric("T0000005A"), new Phone("92492021"),
                new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                new Hire("2025-01-05"), getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Nric("T0000006A"), new Phone("92624417"),
                new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                new Hire("2025-01-06"), getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a TagSet containing the list of strings given.
     */
    public static TagSet getTagSet(String... strings) {
        Set<Tag> tagSet = Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
        return new TagSet(tagSet);  // Create and return a TagSet
    }
}

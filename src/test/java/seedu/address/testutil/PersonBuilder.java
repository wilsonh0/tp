package seedu.address.testutil;

import seedu.address.model.person.*;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_NRIC = "S1234567A";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_HIRE = "2023-01-01"; // Example hire date
    public static final String DEFAULT_TAG = "friends";

    private Name name;
    private Nric nric;
    private Phone phone;
    private Email email;
    private Address address;
    private Hire hire;
    private TagSet tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        nric = new Nric(DEFAULT_NRIC);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        hire = new Hire(DEFAULT_HIRE);  // Assuming Hire is a class with a constructor accepting a date string
        tags = SampleDataUtil.getTagSet(DEFAULT_TAG);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        nric = personToCopy.getNric();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        hire = personToCopy.getHire();
        tags = personToCopy.getTags();
    }

    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public PersonBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    public PersonBuilder withHire(String hire) {
        this.hire = new Hire(hire);  // Assuming Hire class
        return this;
    }

    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Person build() {
        return new Person(name, nric, phone, email, address, hire, tags);
    }
}

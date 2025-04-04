package seedu.address.testutil;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import seedu.address.model.attendance.Attendance;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Hire;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TagSet;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_NRIC = "T0123456A";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_HIRE = "1900-01-01";

    private Name name;
    private Nric nric;
    private Phone phone;
    private Email email;
    private Address address;
    private Hire hire;
    private TagSet tags;
    private List<Leave> leaves;
    private Attendance attendance;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        nric = new Nric(DEFAULT_NRIC);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        hire = new Hire(DEFAULT_HIRE);
        tags = new TagSet(new HashSet<>()); // Empty set instead of having a tag by default
        leaves = new ArrayList<>();
        attendance = new Attendance();
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
        leaves = new ArrayList<>(personToCopy.getLeaves());
        attendance = personToCopy.getAttendance();
        attendance.setAbsentDayCount(personToCopy.getAttendance().getAbsentDayCount());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Person} that we are building.
     */
    public PersonBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Hire} of the {@code Person} that we are building.
     */
    public PersonBuilder withHire(String hire) {
        this.hire = new Hire(hire);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code leaves} into a {@code List<Leave>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withLeaves(Leave ... leaves) {
        this.leaves = SampleDataUtil.getLeaveList(leaves);
        return this;
    }

    /**
     * Sets the {@code Attendance} of the {@code Person} that we are building.
     * @param attendance
     * @return
     */
    public PersonBuilder withAttendance(Attendance attendance) {
        this.attendance = attendance;
        return this;
    }

    /**
     * Builds a Person object with the given attributes.
     */
    public Person build() {
        return new Person(name, nric, phone, email, address, hire, tags, leaves);
    }
}

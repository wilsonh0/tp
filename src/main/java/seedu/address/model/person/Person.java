package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.leave.Leave;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Nric nric;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Hire hire;
    private final Set<Tag> tags = new HashSet<>();
    private final List<Leave> leaves = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Nric nric, Phone phone, Email email, Address address, Hire hire, Set<Tag> tags,
                  List<Leave> leaves) {
        requireAllNonNull(name, nric, phone, email, address, hire, tags);
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.hire = hire;
        this.tags.addAll(tags);
        this.leaves.addAll(leaves);
    }

    public Name getName() {
        return name;
    }

    public Nric getNric() {
        return nric;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Hire getHire() {
        return hire;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable leave list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Leave> getLeaves() {
        return Collections.unmodifiableList(leaves);
    }

    /**
     * Adds a leave to the person's list of leaves.
     */
    public void addLeave(Leave leave) {
        leaves.add(leave);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && nric.equals(otherPerson.nric)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && hire.equals(otherPerson.hire)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, nric, phone, email, address, hire, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("nric", nric)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("hire", hire)
                .add("tags", tags)
                .toString();
    }

}

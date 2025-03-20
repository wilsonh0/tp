package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.leave.Leave;

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
    private final TagSet tags;
    private final List<Leave> leaves = new ArrayList<>();
    private final Attendance attendance;

    /**
     * Every field must be present and not null.
     */

    public Person(Name name, Nric nric, Phone phone, Email email, Address address, Hire hire, TagSet tags,
                  List<Leave> leaves) {
        requireAllNonNull(name, nric, phone, email, address, hire, tags);
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.hire = hire;
        this.tags = tags;
        this.leaves.addAll(leaves);
        this.attendance = new Attendance();
    }

    /**
     * An overloaded constructor designed for EditCommand.java
     */
    public Person(Name name, Nric nric, Phone phone, Email email, Address address, Hire hire, TagSet tags,
                  List<Leave> leaves, Attendance attendance) {
        requireAllNonNull(name, nric, phone, email, address, hire, tags);
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.hire = hire;
        this.tags = tags;
        this.leaves.addAll(leaves);
        this.attendance = attendance;
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

    public TagSet getTags() {
        return tags;
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

    public Attendance getAttendance() {
        return attendance;
    }

    public void incrementAbsentDay() {
        this.attendance.incrementAbsentDay();
    }

    /**
     * Removes a leave from the person's list of leaves.
     */
    public void removeLeave(Leave leave) {
        leaves.remove(leave);
    }

    /**
     * Checks if the person has an existing leave.
     */
    public boolean hasLeave(Leave leave) {
        return leaves.contains(leave);
    }

    /**
     * Checks if the person has an overlapping leave.
     */
    public boolean hasOverlappingLeave(Leave newLeave) {
        for (Leave existingLeave : leaves) {
            if (existingLeave.overlaps(newLeave)) {
                return true;
            }
        }
        return false;
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

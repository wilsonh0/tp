package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a set of tags associated with a person.
 * Guarantees: immutable; does not allow modification after creation.
 */
public class TagSet {
    private final Set<Tag> tags;

    /**
     * Constructs a {@code TagSet} with the given tags.
     *
     * @param tags A set of tags.
     */
    public TagSet(Set<Tag> tags) {
        requireNonNull(tags);
        this.tags = new HashSet<>(tags);
    }

    /**
     * Returns an immutable view of the tags.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both TagSets contain the same tags.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TagSet)) {
            return false;
        }
        TagSet otherTagSet = (TagSet) other;
        return tags.equals(otherTagSet.tags);
    }

    @Override
    public int hashCode() {
        return tags.hashCode();
    }

    @Override
    public String toString() {
        return tags.toString();
    }
}

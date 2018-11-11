package seedu.meeting.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.meeting.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;

import seedu.meeting.model.group.Group;
import seedu.meeting.model.group.UniqueGroupList;
import seedu.meeting.model.shared.Address;
import seedu.meeting.model.tag.Tag;



/**
 * Represents a Person in the MeetingBook.
 * Guarantees: details are present and not null, field values are validated,
 * immutable, except UniqueGroupList.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    // @@author Derek-Hardy
    // Group field
    private final UniqueGroupList groups = new UniqueGroupList();
    // @@author

    /**
     * Name, phone, email, address and tags must be present and not null.
     * Information related to group can be empty.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    // @@author Derek-Hardy
    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Set<Tag> tags, UniqueGroupList groups) {
        requireAllNonNull(name, phone, email, address, tags, groups);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.groups.setGroups(groups);
    }
    // @@author

    public Name getName() {
        return name;
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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    // @@author Derek-Hardy
    /**
     * Returns an immutable group list which the person has enrolled in.
     * It throws {@code UnsupportedOperationException} if modification is attempted.
     *
     */
    public ObservableList<Group> getGroups() {
        return this.groups.asUnmodifiableObservableList();
    }

    /**
     * Returns true if the person is in the same group as {@code group}.
     * @param group The group to check membership
     */
    public boolean hasGroup(Group group) {
        return this.groups.contains(group);
    }

    /**
     * Add the person into a group.
     * @param group The group that the person is added in
     */
    public void addGroup(Group group) {
        requireNonNull(group);
        this.groups.add(group);

        if (!group.hasMember(this)) {
            group.addMember(this);
        }
    }

    /**
     * Remove the person from a group.
     * @param group The group that should remove the person from
     */
    public void removeGroup(Group group) {
        requireNonNull(group);

        this.groups.remove(group);

        group.removeMemberHelper(this);
    }

    /**
     * This method is reserved to be called only from
     * {@link seedu.meeting.model.group.Group#removeMember(Person)}}
     * and {@link Group#clearMembers()} methods.
     */
    public void removeGroupHelper(Group group) {
        this.groups.remove(group);
    }

    /**
     * Remove all the groups that this person is already in.
     */
    public void clearMembership() {
        // enhanced for loop to remove the person from each group
        for (Group group : this.groups) {
            if (group.hasMember(this)) {
                group.removeMemberHelper(this);
            }
        }
        this.groups.clear();
    }

    /**
     * Set up the group connection for the person.
     */
    public void setUpMembership() {
        // enhanced for loop to set up the person's group connections
        for (Group group : this.groups) {
            if (!group.hasMember(this)) {
                group.addMember(this);
            }
        }
    }

    /**
     * Create a copy of this person.
     */
    public Person copy() {
        return new Person(name, phone, email, address, tags, groups);
    }

    // @@author
    /**
     * Returns true if both persons of the same name.
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
     * Returns true if both persons have the same identity.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags())
                /*&& otherPerson.getGroups().equals(getGroups())*/;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, groups);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}

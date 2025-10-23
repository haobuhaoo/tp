package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.MembershipIndex;
import seedu.address.model.group.UniqueGroupList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;

    // NEW: first-class groups + membership relation
    private final UniqueGroupList groups;
    private final MembershipIndex memberships;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        // NEW: init group structures
        groups = new UniqueGroupList();
        memberships = new MembershipIndex();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setPersons(newData.getPersonList());
        clearGroupsAndMemberships();
    }

    /**
     * Clears all groups and their membership relations from this address book.
     */
    public void clearGroupsAndMemberships() {
        groups.setGroups(java.util.Collections.emptyList());
        memberships.clear();
    }


    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        persons.setPerson(target, editedPerson);
        // Memberships are keyed by Person identity (equals/hashCode).
        // If your Person identity fields change, memberships still refer to the new instance in the list,
        // because AB3's setPerson preserves identity semantics.
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
        // NEW: also remove from all groups to avoid orphans
        memberships.removeAllForPerson(key);
    }

    //// group-level operations (NEW)

    /** Returns true if a group with {@code name} exists. */
    public boolean hasGroup(GroupName name) {
        requireNonNull(name);
        return groups.contains(name);
    }

    /** Creates a new empty group. Throws if duplicate. */
    public void addGroup(Group group) {
        requireNonNull(group);
        groups.add(group);
        memberships.ensureGroup(group.getName());
    }

    /** Deletes a group and clears its memberships. Throws if not found. */
    public void removeGroup(GroupName name) {
        requireNonNull(name);
        groups.remove(name);
        memberships.removeGroup(name);
    }

    /** Adds members to a group (idempotent per person). */
    public void addMembers(GroupName name, List<Person> people) {
        requireNonNull(name);
        requireNonNull(people);
        memberships.ensureGroup(name);
        memberships.addMembers(name, people);
    }

    /** Removes members from a group (no-op for non-members). */
    public void removeMembers(GroupName name, List<Person> people) {
        requireNonNull(name);
        requireNonNull(people);
        memberships.removeMembers(name, people);
    }

    /** Unmodifiable view of groups for UI/logic. */
    public ObservableList<Group> getGroupList() {
        return groups.asUnmodifiableObservableList();
    }

    /** All groups that contain {@code person}. */
    public Set<GroupName> getGroupsOf(Person person) {
        requireNonNull(person);
        // Delegate to MembershipIndex helper; see its groupsOf(person)
        return new HashSet<>(memberships.groupsOf(person));
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                // keep concise; groups/memberships omitted to avoid noisy logs
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        // Note: equality only compares persons (same as before), to avoid changing semantics in tests.
        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}

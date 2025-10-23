package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.address.model.person.Person;

/**
 * Stores membership relations: which persons belong to a given group.
 * Backed by Map(GroupName -> Set(Person)).
 */
public final class MembershipIndex {

    private final Map<GroupName, Set<Person>> map = new HashMap<>();

    /** Ensure the group key exists. Idempotent. */
    public void ensureGroup(GroupName group) {
        requireNonNull(group);
        map.computeIfAbsent(group, k -> new HashSet<>());
    }

    /** Remove a group and all its memberships. Idempotent if group not present. */
    public void removeGroup(GroupName group) {
        requireNonNull(group);
        map.remove(group);
    }

    /** Add members to a group (duplicates ignored). */
    public void addMembers(GroupName group, Iterable<Person> persons) {
        requireNonNull(group);
        ensureGroup(group);
        Set<Person> set = map.get(group);
        for (Person p : persons) {
            if (p != null) {
                set.add(p);
            }
        }
    }

    /** Remove members from a group (non-members ignored). */
    public void removeMembers(GroupName group, Iterable<Person> persons) {
        requireNonNull(group);
        Set<Person> set = map.get(group);
        if (set == null) {
            return;
        }
        for (Person p : persons) {
            if (p != null) {
                set.remove(p);
            }
        }
    }

    /** Returns true if the person is a member of the group. */
    public boolean contains(GroupName group, Person person) {
        requireNonNull(group);
        requireNonNull(person);
        Set<Person> set = map.get(group);
        return set != null && set.contains(person);
    }

    /** Unmodifiable view of members in a group (empty if missing). */
    public Set<Person> getMembers(GroupName group) {
        requireNonNull(group);
        Set<Person> set = map.get(group);
        return set == null ? Collections.emptySet() : Collections.unmodifiableSet(set);
    }

    /** Remove this person from ALL groups. */
    public void removeAllForPerson(Person person) {
        requireNonNull(person);
        for (Set<Person> set : map.values()) {
            set.remove(person);
        }
    }

    /** Remove all memberships (keeps no groups). */
    public void clear() {
        map.clear();
    }

    /** Returns a shallow copy of the mapping for read-only purposes (e.g., debugging). */
    public Map<GroupName, Set<Person>> snapshot() {
        Map<GroupName, Set<Person>> copy = new HashMap<>();
        for (var e : map.entrySet()) {
            copy.put(e.getKey(), Collections.unmodifiableSet(new HashSet<>(e.getValue())));
        }
        return Collections.unmodifiableMap(copy);
    }

    /** Returns all groups that contain the given person. */
    public Set<GroupName> groupsOf(Person person) {
        requireNonNull(person);
        Set<GroupName> out = new HashSet<>();
        for (Map.Entry<GroupName, Set<Person>> e : map.entrySet()) {
            if (e.getValue().contains(person)) {
                out.add(e.getKey());
            }
        }
        return out;
    }

}

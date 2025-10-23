package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Set;
import java.util.function.Function;

import seedu.address.model.group.GroupName;
import seedu.address.model.person.Person;

/**
 * Minimal bridge so UI nodes (e.g., PersonCard) can obtain group info
 * without threading the Logic reference through every node.
 *
 * Install once from MainWindow:
 *   UiGroupAccess.install(logic::getGroupsOf);
 */
public final class UiGroupAccess {

    private static Function<Person, Set<GroupName>> groupsOfFn = p -> Collections.emptySet();

    private UiGroupAccess() {}

    /** Installs the supplier used by UI nodes to fetch a person's groups. */
    public static void install(Function<Person, Set<GroupName>> groupsOf) {
        groupsOfFn = requireNonNull(groupsOf);
    }

    /** Returns the groups for the given person (never null). */
    public static Set<GroupName> groupsOf(Person person) {
        return groupsOfFn.apply(requireNonNull(person));
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendance.AttendanceIndex;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.PersonBuilder;

public class GroupRemoveCommandTest {

    @Test
    public void execute_success_removesMembers() throws Exception {
        ModelStubAccepting model = new ModelStubAccepting();
        model.createGroup(GroupName.of("Group A"));

        // Seed membership 1 and 3 first via add
        new GroupAddCommand(
                GroupName.of("Group A"),
                List.of(Index.fromOneBased(1), Index.fromOneBased(3))
        ).execute(model);
        assertEquals(Set.of(0, 2), model.getGroupMembersIndexZero());

        GroupRemoveCommand cmd = new GroupRemoveCommand(
                GroupName.of("Group A"),
                List.of(Index.fromOneBased(3))
        );

        CommandResult result = cmd.execute(model);
        assertEquals(
                String.format(GroupRemoveCommand.MESSAGE_SUCCESS, 1, GroupName.of("Group A")),
                result.getFeedbackToUser()
        );
        // After removal of index 3 (zero-based 2), only index 0 should remain
        assertEquals(Set.of(0), model.getGroupMembersIndexZero());
    }

    @Test
    public void execute_groupMissing_throws() {
        ModelStubAccepting model = new ModelStubAccepting();
        GroupRemoveCommand cmd = new GroupRemoveCommand(
                GroupName.of("Nope"),
                List.of(Index.fromOneBased(1))
        );
        assertThrows(CommandException.class, () -> cmd.execute(model));
    }

    @Test
    public void execute_indexOutOfBounds_throws() {
        ModelStubAccepting model = new ModelStubAccepting();
        model.createGroup(GroupName.of("Group A"));

        GroupRemoveCommand cmd = new GroupRemoveCommand(
                GroupName.of("Group A"),
                List.of(Index.fromOneBased(9))
        );
        assertThrows(CommandException.class, () -> cmd.execute(model));
    }

    private static class ModelStubAccepting implements Model {

        private final List<Person> persons = List.of(
                new PersonBuilder().withName("Alex Yeoh").build(),
                new PersonBuilder().withName("Bernice Yu").build(),
                new PersonBuilder().withName("Charlotte Oliveiro").build()
        );

        private final Set<GroupName> groups = new java.util.HashSet<>();
        private final java.util.Map<GroupName, java.util.Set<Integer>> membership = new java.util.HashMap<>();
        private Set<Integer> groupMembersIndexZero = Collections.emptySet();

        public Set<Integer> getGroupMembersIndexZero() {
            return groupMembersIndexZero;
        }

        private java.util.Set<Integer> ensureBucket(GroupName name) {
            return membership.computeIfAbsent(name, k -> new java.util.HashSet<>());
        }

        private Set<Integer> copy(java.util.Set<Integer> s) {
            return Set.copyOf(s);
        }

        @Override
        public boolean hasGroup(GroupName name) {
            return groups.contains(name);
        }

        @Override
        public void createGroup(GroupName name) {
            groups.add(name);
            membership.put(name, new java.util.HashSet<>());
        }

        @Override
        public void deleteGroup(GroupName name) {
            groups.remove(name);
            membership.remove(name);
        }

        @Override
        public void addToGroup(GroupName name, List<Person> members) {
            java.util.Set<Integer> set = ensureBucket(name);
            for (Person p : members) {
                int idx = persons.indexOf(p);
                if (idx >= 0) {
                    set.add(idx);
                }
            }
            groupMembersIndexZero = copy(set);
        }

        @Override
        public void removeFromGroup(GroupName name, List<Person> members) {
            java.util.Set<Integer> set = ensureBucket(name);
            for (Person p : members) {
                int idx = persons.indexOf(p);
                if (idx >= 0) {
                    set.remove(idx);
                }
            }
            groupMembersIndexZero = copy(set);
        }

        @Override
        public ObservableList<Group> getGroupList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public java.util.Set<GroupName> getGroupsOf(Person person) {
            return Set.of();
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList(persons);
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
        }

        @Override
        public void updateFilteredReminderList(Predicate<Reminder> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPersonName(String name) {
            if (name == null) {
                return false;
            }
            String n = name.trim().toLowerCase();
            return persons.stream()
                    .anyMatch(p -> p.getName().fullName.trim().toLowerCase().equals(n));
        }

        @Override
        public AttendanceIndex getAttendanceIndex() {
            return new AttendanceIndex();
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            return new UserPrefs();
        }

        @Override
        public GuiSettings getGuiSettings() {
            return new GuiSettings();
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
        }

        @Override
        public Path getAddressBookFilePath() {
            return Path.of("dummy.json");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return null;
        }

        @Override
        public boolean hasPerson(Person person) {
            return persons.contains(person);
        }

        @Override
        public void deletePerson(Person target) {
        }

        @Override
        public void addPerson(Person person) {
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
        }

        @Override
        public void addReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteReminder(Reminder target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setReminder(Reminder target, Reminder editedReminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Reminder> getFilteredReminderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void refreshReminders() {
            throw new AssertionError("This method should not be called.");
        }
    }
}

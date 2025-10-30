package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
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

public class GroupDeleteCommandTest {

    @Test
    public void execute_success_deletesGroup() throws Exception {
        ModelStubGroups model = new ModelStubGroups();
        model.createGroup(GroupName.of("Group A"));
        GroupDeleteCommand cmd = new GroupDeleteCommand(GroupName.of("Group A"));

        CommandResult result = cmd.execute(model);
        assertEquals(
                String.format(GroupDeleteCommand.MESSAGE_SUCCESS, GroupName.of("Group A")),
                result.getFeedbackToUser()
        );
        org.junit.jupiter.api.Assertions.assertFalse(model.hasGroup(GroupName.of("Group A")));
    }

    @Test
    public void execute_missingGroup_throws() {
        ModelStubGroups model = new ModelStubGroups();
        GroupDeleteCommand cmd = new GroupDeleteCommand(GroupName.of("Nope"));
        assertThrows(CommandException.class, () -> cmd.execute(model));
    }

    private static class ModelStubGroups implements Model {

        private final List<Person> persons = List.of(
                new PersonBuilder().withName("Alex Yeoh").build()
        );

        private final Set<GroupName> groups = new java.util.HashSet<>();

        @Override
        public boolean hasGroup(GroupName name) {
            return groups.contains(name);
        }

        @Override
        public void createGroup(GroupName name) {
            groups.add(name);
        }

        @Override
        public void deleteGroup(GroupName name) {
            groups.remove(name);
        }

        @Override
        public void addToGroup(GroupName name, List<Person> members) {
        }

        @Override
        public void removeFromGroup(GroupName name, List<Person> members) {
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

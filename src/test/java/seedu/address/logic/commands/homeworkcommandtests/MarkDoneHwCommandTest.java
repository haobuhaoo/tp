package seedu.address.logic.commands.homeworkcommandtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.homeworkcommands.DeleteHomeworkCommand;
import seedu.address.logic.commands.homeworkcommands.MarkDoneHwCommand;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.attendance.AttendanceIndex;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.homework.Homework;
import seedu.address.model.person.LessonTime;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.reminder.Reminder;

public class MarkDoneHwCommandTest {
    private Name marcusName;
    private Phone marcusPhone;
    private Set<LessonTime> marcusLessonTime;
    private Person marcus;

    private Name johnName;
    private Phone johnPhone;
    private Set<LessonTime> johnLessonTime;
    private Person john;

    /**
     * Sets up common Person (Marcus, John) classes used across tests.
     * Ensures each test have independent instances of person class.
     */
    @BeforeEach
    public void setUp() {
        marcusName = new Name("Marcus");
        marcusPhone = new Phone("91326770");
        marcusLessonTime = new HashSet<>();
        marcusLessonTime.add(new LessonTime("1000 Sat"));
        marcus = new Person(marcusName, marcusPhone, marcusLessonTime);

        johnName = new Name("John");
        johnPhone = new Phone("99999999");
        johnLessonTime = new HashSet<>();
        johnLessonTime.add(new LessonTime("1000 Sat"));
        john = new Person(johnName, johnPhone, johnLessonTime);
    }

    /**
     * Minimal {@link Model} stub for {@link MarkDoneHwCommand} tests.
     */
    private static class ModelStubFilteredOnly implements Model {
        private final ObservableList<Person> filtered;
        private final ObservableList<Reminder> filteredReminders;

        ModelStubFilteredOnly(Collection<Person> showedPeople) {
            this.filtered = FXCollections.observableArrayList(showedPeople);
            this.filteredReminders = FXCollections.observableArrayList();
        }

        //This is the method used by MarkDoneHwCommand

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return filtered;
        }

        @Override
        public void addReminder(Reminder reminder) {
            filteredReminders.add(reminder);
        }

        @Override
        public void deleteReminder(Reminder target) {
            filteredReminders.remove(target);
        }

        @Override
        public ObservableList<Reminder> getFilteredReminderList() {
            return filteredReminders;
        }

        //These are methods not used by MarkDoneHwCommand

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public AttendanceIndex getAttendanceIndex() {
            return new AttendanceIndex();
        }

        @Override
        public boolean hasPersonName(String name) {
            return false;
        }

        @Override
        public boolean hasGroup(GroupName name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void createGroup(GroupName name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteGroup(GroupName name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addToGroup(GroupName name, List<Person> members) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeFromGroup(GroupName name, List<Person> members) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Group> getGroupList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Set<GroupName> getGroupsOf(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void filterByGroup(GroupName name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setReminder(Reminder target, Reminder editedReminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredReminderList(Predicate<Reminder> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * Verifies that executing {@link MarkDoneHwCommand} with a matching student and homework
     * marks it done and returns the expected message.
     */
    @Test
    public void execute_success_markDone() throws Exception {
        Homework hw = new Homework("Math WS 3", LocalDate.parse("2025-10-23"));
        marcus.addHomework(hw);

        Model model = new ModelStubFilteredOnly(List.of(marcus));
        MarkDoneHwCommand command = new MarkDoneHwCommand(marcusName, Index.fromOneBased(1));
        CommandResult result = command.execute(model);

        String expected = String.format(MarkDoneHwCommand.MESSAGE_SUCCESS, marcus.getName().fullName,
                hw.getDescription());
        assertEquals(expected, result.getFeedbackToUser());
        assertTrue(hw.isDone(), "Homework should be marked done");
        assertEquals(0, model.getFilteredReminderList().size());
    }

    /**
     * Verifies that marking an already done homework still succeeds and keeps it done
     */
    @Test
    public void execute_alreadyDone_stillSuccess() throws Exception {
        Homework hw = new Homework("Reading", LocalDate.parse("2025-12-01"));
        hw.markDone();
        marcus.addHomework(hw);

        Model model = new ModelStubFilteredOnly(List.of(marcus));
        MarkDoneHwCommand command = new MarkDoneHwCommand(marcusName, Index.fromOneBased(1));
        CommandResult res = command.execute(model);

        String expected = String.format(MarkDoneHwCommand.MESSAGE_SUCCESS,
                marcus.getName().fullName, hw.getDescription());
        assertEquals(expected, res.getFeedbackToUser());
        assertTrue(hw.isDone(), "Homework remains done");
        assertEquals(0, model.getFilteredReminderList().size());
    }

    /**
     * Verifies that when the target student is not present in the current filtered list
     * (e.g., after a prior search), a {@link CommandException} is thrown with
     * {@link MarkDoneHwCommand#MESSAGE_NO_PERSON_FOUND}
     */
    @Test
    public void execute_nameNotInFilteredList_throwsNoPersonFound() {
        Homework hw = new Homework("Math WS 3", LocalDate.parse("2025-10-23"));
        marcus.addHomework(hw);

        Model model = new ModelStubFilteredOnly(List.of(john));
        MarkDoneHwCommand cmd = new MarkDoneHwCommand(marcusName, Index.fromOneBased(1));

        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals(MarkDoneHwCommand.MESSAGE_NO_PERSON_FOUND, ex.getMessage());
        assertFalse(john.getHomeworkList().contains(hw), "Unrelated student should not change");
    }

    /**
     * Verifies that when the student is present but the homework index is out of bounds
     * a {@link CommandException} with {@link MarkDoneHwCommand#MESSAGE_INVALID_HW_INDEX} is thrown.
     */
    @Test
    public void execute_hwNotFound_throwsNoHwFound() {
        Homework hw = new Homework("Chem WS", LocalDate.parse("2025-10-24"));
        marcus.addHomework(hw);

        Model model = new ModelStubFilteredOnly(List.of(marcus));
        MarkDoneHwCommand cmd = new MarkDoneHwCommand(marcusName, Index.fromOneBased(2));

        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        String expectedMsg = String.format(DeleteHomeworkCommand.MESSAGE_INVALID_HW_INDEX, 2, 1);
        assertEquals(expectedMsg, ex.getMessage());
        assertFalse(hw.isDone(), "Non-matching homework should remain unchanged");
    }

    /**
     * Tests for equality as specified under {@link MarkDoneHwCommand}
     */
    @Test
    public void equals_various() {
        MarkDoneHwCommand a1 = new MarkDoneHwCommand(marcusName, Index.fromOneBased(1));
        MarkDoneHwCommand a1copy = new MarkDoneHwCommand(new Name("Marcus"), Index.fromOneBased(1));
        MarkDoneHwCommand a2 = new MarkDoneHwCommand(marcusName, Index.fromOneBased(2));
        MarkDoneHwCommand b1 = new MarkDoneHwCommand(johnName, Index.fromOneBased(1));
        assertEquals(a1, a1);
        assertEquals(a1, a1copy);
        assertNotEquals(a1, null);
        assertNotEquals(a1, "str");
        assertNotEquals(a1, a2);
        assertNotEquals(a1, b1);
    }
}











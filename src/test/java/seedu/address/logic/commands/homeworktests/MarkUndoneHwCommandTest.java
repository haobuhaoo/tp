package seedu.address.logic.commands.homeworktests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.homeworkcommands.MarkDoneHwCommand;
import seedu.address.logic.commands.homeworkcommands.MarkUndoneHwCommand;
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


public class MarkUndoneHwCommandTest {
    private Name marcusName;
    private Phone marcusPhone;
    private LessonTime marcusLessonTime;
    private Person marcus;

    private Name johnName;
    private Phone johnPhone;
    private LessonTime johnLessonTime;
    private Person john;

    /**
     * Sets up common Person (Marcus, John) classes used across tests.
     * Ensures each test have independent instances of person class.
     */
    @BeforeEach
    public void setUp() {
        marcusName = new Name("Marcus");
        marcusPhone = new Phone("91326770");
        marcusLessonTime = new LessonTime("1000");
        marcus = new Person(marcusName, marcusPhone, marcusLessonTime);

        johnName = new Name("John");
        johnPhone = new Phone("99999999");
        johnLessonTime = new LessonTime("1400");
        john = new Person(johnName, johnPhone, johnLessonTime);
    }

    /**
     * Minimal {@link Model} stub for {@link MarkUndoneHwCommandTest} tests.
     */
    private static class ModelStubFilteredOnly implements Model {
        private final ObservableList<Person> filtered;

        ModelStubFilteredOnly(Collection<Person> showedPeople) {
            this.filtered = FXCollections.observableArrayList(showedPeople);
        }

        //This is the method used by MarkUndoneHwCommand

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return filtered;
        }

        //These are methods not used by MarkUndoneHwCommand

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

    }

    /**
     * Verifies that executing {@link MarkUndoneHwCommandTest} with a matching student and homework
     * marks it done and returns the expected message.
     */
    @Test
    public void execute_success_markUndone() throws Exception {
        Homework hw = new Homework("Math WS 3", LocalDate.parse("2025-10-23"));
        marcus.addHomework(hw);

        Model model = new ModelStubFilteredOnly(List.of(marcus));
        MarkDoneHwCommand command1 = new MarkDoneHwCommand(marcusName, "mAtH wS 3");
        MarkUndoneHwCommand command2 = new MarkUndoneHwCommand(marcusName, "mAtH wS 3");
        command1.execute(model);
        CommandResult result2 = command2.execute(model);

        String expected = String.format(MarkUndoneHwCommand.MESSAGE_SUCCESS, marcus.getName().fullName,
                hw.getDescription());
        assertEquals(expected, result2.getFeedbackToUser());
        assertTrue(!hw.isDone(), "Homework should be marked undone");
    }

    /**
     * Verifies that unmarking an already undone homework still succeeds and keeps it undone
     */
    @Test
    public void execute_alreadyUndone_stillSuccess() throws Exception {
        Homework hw = new Homework("Reading", LocalDate.parse("2025-12-01"));
        marcus.addHomework(hw);

        Model model = new ModelStubFilteredOnly(List.of(marcus));
        MarkUndoneHwCommand command = new MarkUndoneHwCommand(marcusName, "Reading");
        CommandResult res = command.execute(model);

        String expected = String.format(MarkUndoneHwCommand.MESSAGE_SUCCESS,
                marcus.getName().fullName, hw.getDescription());
        assertEquals(expected, res.getFeedbackToUser());
        assertTrue(!hw.isDone(), "Homework remains undone");
    }

    /**
     * Verifies that when the target student is not present in the current filtered list
     * (e.g., after a prior search), a {@link CommandException} is thrown with
     * {@link MarkUndoneHwCommand#MESSAGE_NO_PERSON_FOUND}
     */
    @Test
    public void execute_nameNotInFilteredList_throwsNoPersonFound() {
        Homework hw = new Homework("Math WS 3", LocalDate.parse("2025-10-23"));
        marcus.addHomework(hw);

        Model model = new ModelStubFilteredOnly(List.of(john));
        MarkUndoneHwCommand cmd = new MarkUndoneHwCommand(marcusName, "Math WS 3");

        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals(MarkDoneHwCommand.MESSAGE_NO_PERSON_FOUND, ex.getMessage());
        assertFalse(john.getHomeworkList().contains(hw), "Unrelated student should not change");
    }

    /**
     * Verifies that when the student is present but the homework description does not
     * match any entry, a {@link CommandException} with
     * {@link MarkUndoneHwCommand#MESSAGE_NO_HW_FOUND} is thrown.
     */
    @Test
    public void execute_hwNotFound_throwsNoHwFound() {
        Homework hw = new Homework("Chem WS", LocalDate.parse("2025-10-24"));
        marcus.addHomework(hw);

        Model model = new ModelStubFilteredOnly(List.of(marcus));
        MarkUndoneHwCommand cmd = new MarkUndoneHwCommand(marcusName, "Physics WS");

        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals(MarkDoneHwCommand.MESSAGE_NO_HW_FOUND, ex.getMessage());
        assertFalse(hw.isDone(), "Non-matching homework should remain unchanged");
    }

    /**
     * Tests for equality as specified under {@link MarkUndoneHwCommand}
     */
    @Test
    public void equals_various() {
        MarkUndoneHwCommand a1 = new MarkUndoneHwCommand(marcusName, "A");
        MarkUndoneHwCommand a1copy = new MarkUndoneHwCommand(new Name("Marcus"), "A");
        MarkUndoneHwCommand a2 = new MarkUndoneHwCommand(marcusName, "B");
        MarkUndoneHwCommand b1 = new MarkUndoneHwCommand(johnName, "A");

        assertEquals(a1, a1);
        assertEquals(a1, a1copy);
        assertNotEquals(a1, null);
        assertNotEquals(a1, "str");
        assertNotEquals(a1, a2);
        assertNotEquals(a1, b1);
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collections;
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

/**
 * Tests for {@link AttendanceCommand}.
 */
public class AttendanceCommandTest {

    @Test
    public void execute_success_presentRecorded() throws Exception {
        Model model = new ModelStubWithPerson("Alex Yeoh");
        String cmdName = "Alex Yeoh";
        String date = "2025-09-19";
        String status = "1";

        AttendanceCommand cmd = new AttendanceCommand(cmdName, date, status);
        CommandResult result = cmd.execute(model);

        assertEquals("Success: Attendance recorded: Alex Yeoh, 2025-09-19, Present.",
                result.getFeedbackToUser());

        AttendanceIndex idx = model.getAttendanceIndex();
        boolean taken = idx.isTaken("Alex Yeoh", LocalDate.parse(date));
        assertEquals(true, taken);
    }

    @Test
    public void execute_duplicateSameStatus_throws() throws Exception {
        Model model = new ModelStubWithPerson("Alex Yeoh");

        // first record: success
        new AttendanceCommand("Alex Yeoh", "2025-09-19", "1").execute(model);

        // duplicate with same status
        AttendanceCommand dup = new AttendanceCommand("Alex Yeoh", "2025-09-19", "1");
        CommandException ex = assertThrows(CommandException.class, () -> dup.execute(model));
        assertEquals("Student Alex Yeoh is already marked as Present on 2025-09-19.", ex.getMessage());
    }

    @Test
    public void execute_invalidName_throws() {
        Model model = new ModelStubWithPerson("Alex Yeoh");
        AttendanceCommand cmd = new AttendanceCommand("Nonexistent", "2025-09-19", "1");
        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals("Invalid student name: no matching student found.", ex.getMessage());
    }

    @Test
    public void execute_invalidDate_throws() {
        Model model = new ModelStubWithPerson("Alex Yeoh");
        AttendanceCommand cmd = new AttendanceCommand("Alex Yeoh", "19-09-2025", "1");
        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals("Invalid date. The format must be YYYY-MM-DD.", ex.getMessage());
    }

    @Test
    public void execute_invalidStatus_throws() {
        Model model = new ModelStubWithPerson("Alex Yeoh");
        AttendanceCommand cmd = new AttendanceCommand("Alex Yeoh", "2025-09-19", "9");
        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals("Invalid status. Use 1 for present, 0 for absent.", ex.getMessage());
    }

    /**
     * Minimal model stub supporting hasPersonName() and AttendanceIndex.
     */
    private static class ModelStubWithPerson implements Model {
        private final String storedName;
        private final AttendanceIndex index = new AttendanceIndex();

        ModelStubWithPerson(String storedName) {
            this.storedName = norm(storedName);
        }

        private static String norm(String s) {
            return s.trim().replaceAll("\\s+", " ").toLowerCase();
        }

        // --- Methods used by AttendanceCommand ---

        @Override
        public boolean hasPersonName(String name) {
            return norm(name).equals(storedName);
        }

        @Override
        public AttendanceIndex getAttendanceIndex() {
            return index;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            // no-op
        }

        // --- Unused methods (throw AssertionError if called) ---

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError();
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
            throw new AssertionError();
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError();
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError();
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError();
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError();
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError();
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError();
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError();
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList();
        }

        // ===== Groups (no-op implementations for tests) =====
        @Override
        public boolean hasGroup(GroupName name) {
            return false;
        }

        @Override
        public void createGroup(GroupName name) {
            // no-op for tests
        }

        @Override
        public void deleteGroup(GroupName name) {
            // no-op for tests
        }

        @Override
        public void addToGroup(GroupName name, List<Person> members) {
            // no-op for tests
        }

        @Override
        public void removeFromGroup(GroupName name, List<Person> members) {
            // no-op for tests
        }

        @Override
        public ObservableList<Group> getGroupList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public Set<GroupName> getGroupsOf(Person person) {
            return Collections.emptySet();
        }
    }
}

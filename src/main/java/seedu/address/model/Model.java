package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Reminder> PREDICATE_SHOW_ALL_REMINDERS = unused -> true;

    // ============ UserPrefs ===================================================

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    // ============ AddressBook =================================================

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person. The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person. {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    // ============ Attendance / Participation ================================

    /**
     * Attendance index used by the UI date selection and legacy attendance functionality.
     * (We still leverage it to propagate the "current UI date".)
     */
    seedu.address.model.attendance.AttendanceIndex getAttendanceIndex();

    /**
     * Returns true if there exists a person whose normalized name equals {@code name}.
     */
    boolean hasPersonName(String name);
    //java.util.Optional<seedu.address.model.person.Person> findPersonByName(String name);

    // ============ Reminders ============================================

    /**
     * Returns true if a reminder with the same identity as {@code reminder} exists in the address book.
     */
    boolean hasReminder(Reminder reminder);

    /**
     * Deletes the given reminder.
     * The reminder must exist in the address book.
     */
    void deleteReminder(Reminder target);

    /**
     * Adds the given reminder.
     * {@code reminder} must not already exist in the address book.
     */
    void addReminder(Reminder reminder);

    /**
     * Replaces the given reminder {@code target} with {@code editedReminder}.
     * {@code target} must exist in the address book.
     * The {@code editedReminder} must not be the same as another existing reminder in the address book.
     */
    void setReminder(Reminder target, Reminder editedReminder);

    /**
     * Returns an unmodifiable view of the filtered reminder list
     */
    ObservableList<Reminder> getFilteredReminderList();

    /**
     * Updates the filter of the filtered reminder list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReminderList(Predicate<Reminder> predicate);

    /**
     * Refreshes the auto-generated UnmodifiableReminders to match the current state of student list.
     */
    void refreshReminders();

    // ============ Groups ==========================================================
    boolean hasGroup(GroupName name);

    void createGroup(GroupName name);

    void deleteGroup(GroupName name);

    void addToGroup(GroupName name, List<Person> members);

    void removeFromGroup(GroupName name, List<Person> members);

    ObservableList<Group> getGroupList();

    Set<GroupName> getGroupsOf(Person person);

    /**
     * Convenience: filter list by group membership (optional but handy).
     */
    default void filterByGroup(GroupName name) {
        updateFilteredPersonList(p -> getGroupsOf(p).contains(name));
    }
}

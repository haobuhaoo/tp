package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_TIME_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalReminders.REMINDER_1;
import static seedu.address.testutil.TypicalReminders.REMINDER_3;
import static seedu.address.testutil.TypicalReminders.REMINDER_4;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.ReminderBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalReminders;

public class AddressBookTest {
    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getReminderList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        List<Person> newPersonData = TypicalPersons.getTypicalPersons();
        List<Reminder> newReminderData = TypicalReminders.getTypicalReminders();
        AddressBook newData = new AddressBook();
        newData.setPersons(newPersonData);
        newData.setReminders(newReminderData);
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicate_throwsDuplicateException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withLessonTime(VALID_LESSON_TIME_BOB).build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        List<Reminder> newReminders = TypicalReminders.getTypicalReminders();
        AddressBookStub newData1 = new AddressBookStub(newPersons, newReminders);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData1));

        // Two same reminders
        newPersons = TypicalPersons.getTypicalPersons();
        newReminders = Arrays.asList(REMINDER_1, REMINDER_1);
        AddressBookStub newData2 = new AddressBookStub(newPersons, newReminders);

        assertThrows(DuplicateReminderException.class, () -> addressBook.resetData(newData2));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withLessonTime(VALID_LESSON_TIME_AMY).build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void hasReminder_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasReminder(null));
    }

    @Test
    public void hasReminder_reminderNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasReminder(REMINDER_1));
    }

    @Test
    public void hasReminder_reminderInAddressBook_returnsTrue() {
        addressBook.addReminder(REMINDER_1);
        assertTrue(addressBook.hasReminder(REMINDER_1));
    }

    @Test
    public void hasReminder_reminderWithSameDueDateOnlyInAddressBook_returnsFalse() {
        addressBook.addReminder(REMINDER_1);
        Reminder editedReminder = new ReminderBuilder(REMINDER_1).withDescription(VALID_DESCRIPTION_1).build();
        assertFalse(addressBook.hasReminder(editedReminder));
    }

    @Test
    public void getReminderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getReminderList().remove(0));
    }

    @Test
    public void mergeAddressBook_success() {
        addressBook.addPerson(ALICE);
        addressBook.addReminder(REMINDER_1);

        List<Person> persons = Arrays.asList(CARL, DANIEL);
        List<Reminder> reminders = Arrays.asList(REMINDER_3, REMINDER_4);
        AddressBookStub otherAddressBook = new AddressBookStub(persons, reminders);

        addressBook.mergeAddressBook(otherAddressBook);

        List<Person> newPerson = Arrays.asList(ALICE, CARL, DANIEL);
        List<Reminder> newReminder = Arrays.asList(REMINDER_1, REMINDER_3, REMINDER_4);
        AddressBook newData = new AddressBook();
        newData.setPersons(newPerson);
        newData.setReminders(newReminder);

        assertEquals(newData, addressBook);
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName()
                + "{persons=" + addressBook.getPersonList() + ", "
                + "reminders=" + addressBook.getReminderList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons and/or reminders list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Reminder> reminders = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<Reminder> reminders) {
            this.persons.setAll(persons);
            this.reminders.setAll(reminders);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Reminder> getReminderList() {
            return reminders;
        }
    }
}

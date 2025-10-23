package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

/**
 * A utility class containing a list of {@code Person} objects and a list of {@code Reminder}
 * objects to be used in tests.
 */
public class TypicalAddressBook {
    private TypicalAddressBook() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons and reminders.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : TypicalPersons.getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Reminder reminder : TypicalReminders.getTypicalReminders()) {
            ab.addReminder(reminder);
        }
        ab.sortReminder();
        return ab;
    }
}

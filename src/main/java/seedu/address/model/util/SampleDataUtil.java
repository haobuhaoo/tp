package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.LessonTime;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.reminder.Description;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Reminder;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new LessonTime("1000")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new LessonTime("1430")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new LessonTime("1200")),
            new Person(new Name("David Li"), new Phone("91031282"), new LessonTime("1830")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new LessonTime("1500")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new LessonTime("1600"))
        };
    }

    public static Reminder[] getSampleReminders() {
        return new Reminder[] {
            new Reminder(new DueDate("2025-10-21 1530"), new Description("Alice: Submit homework for Math")),
            new Reminder(new DueDate("2025-10-22 1000"), new Description("Benson: Prepare for English lesson")),
            new Reminder(new DueDate("2025-10-25"), new Description("Carl: Assignment review")),
            new Reminder(new DueDate("2025-11-01"), new Description("Daniel: Feedback session")),
            new Reminder(new DueDate("2025-11-03 1400"), new Description("Benson: Mock test practice")),
            new Reminder(new DueDate("2025-11-05 1600"), new Description("Carl: Science project discussion"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Reminder sampleReminder : getSampleReminders()) {
            sampleAb.addReminder(sampleReminder);
        }
        return sampleAb;
    }
}

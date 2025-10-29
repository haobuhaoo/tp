package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.homework.Homework;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class UnmodifiableHwReminderTest {
    @Test
    public void factoryMethod_personAndHomework() {
        Person person = new PersonBuilder().build();
        Homework homework = new Homework("Math worksheet", LocalDate.parse("2025-10-31"));
        UnmodifiableHwReminder reminder = UnmodifiableHwReminder.of(person, homework);

        assertNotNull(reminder);
        assertFalse(reminder.isModifiable());
        assertFalse(reminder.isPaymentReminder());
    }

    @Test
    public void factoryMethod_dueDateAndDescription() {
        DueDate dueDate = new DueDate("2025-10-31");
        Description description = new Description("Homework due soon");
        UnmodifiableHwReminder reminder = UnmodifiableHwReminder.of(dueDate, description);

        assertNotNull(reminder);
        assertFalse(reminder.isModifiable());
        assertFalse(reminder.isPaymentReminder());
    }

    @Test
    public void equalsMethod() {
        DueDate dueDate = new DueDate("2025-10-31");
        Description description = new Description("Math worksheet");
        UnmodifiableHwReminder reminder1 = UnmodifiableHwReminder.of(dueDate, description);

        Person person = new PersonBuilder().build();
        Homework homework = new Homework("Math worksheet", LocalDate.parse("2025-10-31"));
        UnmodifiableHwReminder reminder2 = UnmodifiableHwReminder.of(person, homework);

        // same values -> return true
        assertEquals(UnmodifiableHwReminder.of(dueDate, description), reminder1);

        // same object -> return true
        assertEquals(reminder1, reminder1);

        // null -> return false
        assertNotEquals(null, reminder1);

        // different values -> return false
        assertNotEquals(reminder1, reminder2);

        // different due date -> return false
        Homework hw = new Homework("Math worksheet", LocalDate.parse("2025-09-30"));
        assertNotEquals(reminder2, UnmodifiableHwReminder.of(person, hw));

        // different description -> return false
        hw = new Homework("Sci worksheet", LocalDate.parse("2025-10-31"));
        assertNotEquals(reminder2, UnmodifiableHwReminder.of(person, hw));
    }

    @Test
    public void hashCodeMethod() {
        DueDate dueDate = new DueDate("2025-10-31");
        Description description = new Description("Math worksheet");
        UnmodifiableHwReminder reminder = UnmodifiableHwReminder.of(dueDate, description);

        assertEquals(UnmodifiableHwReminder.of(dueDate, description).hashCode(), reminder.hashCode());

        DueDate due = new DueDate("2025-09-31");
        assertNotEquals(UnmodifiableHwReminder.of(due, description).hashCode(), reminder.hashCode());

        Description desc = new Description("Sci worksheet");
        assertNotEquals(UnmodifiableHwReminder.of(dueDate, desc).hashCode(), reminder.hashCode());
    }
}

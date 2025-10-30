package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class UnmodifiablePaymentReminderTest {
    @Test
    public void factoryMethod_monthAndPerson() {
        Person person = new PersonBuilder().build();
        UnmodifiablePaymentReminder reminder = UnmodifiablePaymentReminder.of(6, person, "June");

        assertNotNull(reminder);
        assertFalse(reminder.isModifiable());
        assertTrue(reminder.isPaymentReminder());
    }

    @Test
    public void factoryMethod_dueDateAndDescription() {
        DueDate dueDate = new DueDate("2025-10-31");
        Description description = new Description("Payment due soon");
        UnmodifiablePaymentReminder reminder = UnmodifiablePaymentReminder.of(dueDate, description);

        assertNotNull(reminder);
        assertFalse(reminder.isModifiable());
        assertTrue(reminder.isPaymentReminder());
    }

    @Test
    public void equalsMethod() {
        DueDate dueDate = new DueDate("2025-06-30");
        Description description = new Description("Payment due in June");
        UnmodifiablePaymentReminder reminder1 = UnmodifiablePaymentReminder.of(dueDate, description);

        Person person = new PersonBuilder().build();
        UnmodifiablePaymentReminder reminder2 = UnmodifiablePaymentReminder.of(6, person, "June");

        // same values -> return true
        assertEquals(UnmodifiablePaymentReminder.of(dueDate, description), reminder1);

        // same object -> return true
        assertEquals(reminder1, reminder1);

        // null -> return false
        assertNotEquals(null, reminder1);

        // different values -> return false
        assertNotEquals(reminder1, reminder2);

        // different due date -> return false
        UnmodifiablePaymentReminder reminder = UnmodifiablePaymentReminder.of(7, person, "July");
        assertNotEquals(reminder2, reminder);

        // different description -> return false
        description = new Description("Payment due in July");
        assertNotEquals(reminder2, UnmodifiablePaymentReminder.of(dueDate, description));
    }

    @Test
    public void hashCodeMethod() {
        DueDate dueDate = new DueDate("2025-06-30");
        Description description = new Description("Payment due in June");
        UnmodifiablePaymentReminder reminder = UnmodifiablePaymentReminder.of(dueDate, description);

        assertEquals(UnmodifiablePaymentReminder.of(dueDate, description).hashCode(), reminder.hashCode());

        DueDate due = new DueDate("2025-09-30");
        assertNotEquals(UnmodifiablePaymentReminder.of(due, description).hashCode(), reminder.hashCode());

        Description desc = new Description("Payment due in July");
        assertNotEquals(UnmodifiablePaymentReminder.of(dueDate, desc).hashCode(), reminder.hashCode());
    }
}

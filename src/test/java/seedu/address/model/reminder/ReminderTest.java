package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUEDATE_1;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReminders.REMINDER_1;
import static seedu.address.testutil.TypicalReminders.REMINDER_2;
import static seedu.address.testutil.TypicalReminders.REMINDER_4;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ReminderBuilder;

public class ReminderTest {
    private final Reminder reminder = new Reminder(new DueDate("2025-10-10"), new Description("valid"));

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Reminder(null, null));
        assertThrows(NullPointerException.class, () -> new Reminder(null, new Description("valid")));
        assertThrows(NullPointerException.class, () -> new Reminder(new DueDate("2025-10-10"), null));
    }

    @Test
    public void equals() {
        // same values -> return true
        Reminder reminderCopy = new ReminderBuilder(REMINDER_1).build();
        assertEquals(REMINDER_1, reminderCopy);

        // same object -> return true
        assertEquals(REMINDER_1, REMINDER_1);

        // null -> return false
        assertNotEquals(null, REMINDER_1);

        // different type -> return false
        assertNotEquals(5.0f, REMINDER_1);

        // different values -> return false
        assertNotEquals(REMINDER_1, REMINDER_2);

        // different due date -> return false
        Reminder editedReminder = new ReminderBuilder(REMINDER_1).withDueDate(VALID_DUEDATE_1).build();
        assertNotEquals(REMINDER_1, editedReminder);

        // different description -> return false
        editedReminder = new ReminderBuilder(REMINDER_1).withDescription(VALID_DESCRIPTION_1).build();
        assertNotEquals(REMINDER_1, editedReminder);
    }

    @Test
    public void hashCodeMethod() {
        assertEquals(new Reminder(new DueDate("2025-10-10"), new Description("valid")).hashCode(),
                reminder.hashCode());
        assertNotEquals(new Reminder(new DueDate("2020-12-12 1212"), new Description("valid")).hashCode(),
                reminder.hashCode());
        assertNotEquals(new Reminder(new DueDate("2025-10-10"), new Description("diff")).hashCode(),
                reminder.hashCode());
    }

    @Test
    public void toStringMethod() {
        String expected = Reminder.class.getCanonicalName() + "{dueDate=" + REMINDER_1.getDueDate()
                + ", description=" + REMINDER_1.getDescription() + "}";
        assertEquals(expected, REMINDER_1.toString());
    }

    @Test
    public void compareToMethod() {
        // same reminder
        assertEquals(0, REMINDER_1.compareTo(REMINDER_1));

        // both past reminder
        assertEquals(-1, REMINDER_1.compareTo(REMINDER_2));

        // both upcoming reminder
        Clock clock = Clock.fixed(Instant.parse("2020-12-12T00:00:00.00Z"), ZoneId.systemDefault());
        LocalDateTime.now(clock);
        assertEquals(-1, REMINDER_1.compareTo(REMINDER_2));

        // REMINDER_1 is past, REMINDER_4 is upcoming
        clock = Clock.fixed(Instant.parse("2020-10-25T00:00:00.00Z"), ZoneId.systemDefault());
        LocalDateTime.now(clock);
        assertEquals(1, REMINDER_1.compareTo(REMINDER_4));
        assertEquals(-1, REMINDER_4.compareTo(REMINDER_1));

        // both have same due date but different description
        Reminder reminder = new ReminderBuilder(REMINDER_1).withDescription(VALID_DESCRIPTION_1).build();
        clock = Clock.fixed(Instant.parse("2025-10-21T00:00:00.00Z"), ZoneId.systemDefault());
        LocalDateTime.now(clock);
        assertEquals(0, REMINDER_1.compareTo(reminder));
    }
}

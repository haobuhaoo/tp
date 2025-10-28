package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.storage.JsonAdaptedReminder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReminders.REMINDER_2;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Description;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.UnmodifiableHwReminder;
import seedu.address.model.reminder.UnmodifiablePaymentReminder;
import seedu.address.testutil.PersonBuilder;

public class JsonAdaptedReminderTest {
    private static final String INVALID_DUEDATE = "20 Aug 2025 10:10 am";
    private static final String INVALID_DESCRIPTION = " ";
    private static final boolean REMINDER = true;
    private static final boolean UNMODIFIABLE_REMINDER = false;

    private static final String VALID_DUEDATE = REMINDER_2.getDueDate().toInputString();
    private static final String VALID_DESCRIPTION = REMINDER_2.getDescription().toString();

    @Test
    public void toModelType_validReminderDetails_returnsReminder() throws Exception {
        JsonAdaptedReminder reminder = new JsonAdaptedReminder(REMINDER_2);
        assertEquals(REMINDER_2, reminder.toModelType());
    }

    @Test
    public void toModelType_invalidDueDate_throwsIllegalValueException() {
        JsonAdaptedReminder reminder =
                new JsonAdaptedReminder(INVALID_DUEDATE, VALID_DESCRIPTION, REMINDER);
        String expectedMessage = DueDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, reminder::toModelType);
    }

    @Test
    public void toModelType_nullDueDate_throwsIllegalValueException() {
        JsonAdaptedReminder reminder = new JsonAdaptedReminder(null, VALID_DESCRIPTION, REMINDER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DueDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, reminder::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedReminder reminder =
                new JsonAdaptedReminder(VALID_DUEDATE, INVALID_DESCRIPTION, REMINDER);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, reminder::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedReminder reminder = new JsonAdaptedReminder(VALID_DUEDATE, null, REMINDER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, reminder::toModelType);
    }

    @Test
    public void toModelType_UnmodifiablePaymentReminder() throws Exception {
        JsonAdaptedReminder reminder = new JsonAdaptedReminder(VALID_DUEDATE,
                UnmodifiablePaymentReminder.TWO_LETTER_PREFIX + VALID_DESCRIPTION, UNMODIFIABLE_REMINDER);
        UnmodifiablePaymentReminder paymentReminder =
                UnmodifiablePaymentReminder.of(REMINDER_2.getDueDate(), new Description(VALID_DESCRIPTION));
        assertEquals(paymentReminder, reminder.toModelType());
    }

    @Test
    public void toModelType_UnmodifiableHwReminder() throws Exception {
        JsonAdaptedReminder reminder = new JsonAdaptedReminder(VALID_DUEDATE,
                UnmodifiableHwReminder.TWO_LETTER_PREFIX + VALID_DESCRIPTION, UNMODIFIABLE_REMINDER);
        UnmodifiableHwReminder hwReminder =
                UnmodifiableHwReminder.of(REMINDER_2.getDueDate(), new Description(VALID_DESCRIPTION));
        assertEquals(hwReminder, reminder.toModelType());
    }

    @Test
    public void toModelType_invalidPrefix_throwsIllegalValueException() {
        JsonAdaptedReminder reminder = new JsonAdaptedReminder(VALID_DUEDATE,
                "PH" + VALID_DESCRIPTION, UNMODIFIABLE_REMINDER);
        String expectedMessage = "Unknown unmodifiable reminder type with prefix: PH";
        assertThrows(IllegalValueException.class, expectedMessage, reminder::toModelType);
    }

    @Test
    public void toModelType_shortDescription() {
        JsonAdaptedReminder reminder = new JsonAdaptedReminder(VALID_DUEDATE,
                "R", UNMODIFIABLE_REMINDER);
        String expectedMessage = "Description of unmodifiable reminder should have at least 2 characters";
        assertThrows(AssertionError.class, expectedMessage, reminder::toModelType);
    }

    @Test
    public void constructor_invalidNonUnmodifiableReminder_assertionError() {
        Reminder invalidReminder = new Reminder(new DueDate("2025-10-31"), new Description("Invalid")) {
            @Override
            public boolean isModifiable() {
                return false;
            }
        };

        assertThrows(AssertionError.class, () -> new JsonAdaptedReminder(invalidReminder));
    }

    @Test
    public void constructor_unmodifiablePaymentReminder_success() {
        Person samplePerson = new PersonBuilder().withName("Alice").build();
        UnmodifiablePaymentReminder reminder = UnmodifiablePaymentReminder.of(10,
                samplePerson, "October");

        JsonAdaptedReminder adapted = new JsonAdaptedReminder(reminder);

        String expectedPrefix = UnmodifiablePaymentReminder.TWO_LETTER_PREFIX;
        assertTrue(adapted.getDescription().startsWith(expectedPrefix));
        assertFalse(adapted.isModifiable());
    }

    @Test
    public void constructor_unmodifiableHwReminder_success() {
        UnmodifiableHwReminder reminder = UnmodifiableHwReminder.of(new DueDate("2025-10-30 2359"),
                new Description("CS2103T Tutorial 6"));

        JsonAdaptedReminder adapted = new JsonAdaptedReminder(reminder);

        String expectedPrefix = UnmodifiableHwReminder.TWO_LETTER_PREFIX;
        assertTrue(adapted.getDescription().startsWith(expectedPrefix));
        assertFalse(adapted.isModifiable());
    }
}

package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedReminder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReminders.REMINDER_2;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.Description;
import seedu.address.model.reminder.DueDate;

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
}

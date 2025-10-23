package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.DESC_REMINDER_1;
import static seedu.address.logic.commands.CommandTestUtil.DESC_REMINDER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUEDATE_2;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditReminderCommand.EditReminderDescriptor;
import seedu.address.testutil.EditReminderDescriptorBuilder;

public class EditReminderDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditReminderDescriptor descriptorWithSameValues = new EditReminderDescriptor(DESC_REMINDER_1);
        assertEquals(DESC_REMINDER_1, descriptorWithSameValues);

        // same object -> returns true
        assertEquals(DESC_REMINDER_1, DESC_REMINDER_1);

        // null -> returns false
        assertNotEquals(null, DESC_REMINDER_1);

        // different types -> returns false
        assertNotEquals(5, DESC_REMINDER_1);

        // different values -> returns false
        assertNotEquals(DESC_REMINDER_1, DESC_REMINDER_2);

        // different due date -> returns false
        EditReminderDescriptor editedReminder =
                new EditReminderDescriptorBuilder(DESC_REMINDER_1).withDueDate(VALID_DUEDATE_2).build();
        assertNotEquals(DESC_REMINDER_1, editedReminder);

        // different description -> returns false
        editedReminder = new EditReminderDescriptorBuilder(DESC_REMINDER_1)
                .withDescription(VALID_DESCRIPTION_2).build();
        assertNotEquals(DESC_REMINDER_1, editedReminder);
    }

    @Test
    public void toStringMethod() {
        EditReminderDescriptor editReminderDescriptor = new EditReminderDescriptor();
        String expected = EditReminderDescriptor.class.getCanonicalName() + "{dueDate="
                + editReminderDescriptor.getDueDate().orElse(null) + ", description="
                + editReminderDescriptor.getDescription().orElse(null) + "}";
        assertEquals(expected, editReminderDescriptor.toString());
    }
}

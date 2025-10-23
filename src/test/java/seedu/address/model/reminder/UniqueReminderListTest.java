package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_2;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReminders.REMINDER_1;
import static seedu.address.testutil.TypicalReminders.REMINDER_2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;
import seedu.address.testutil.ReminderBuilder;

public class UniqueReminderListTest {
    private final UniqueReminderList uniqueReminderList = new UniqueReminderList();

    @Test
    public void contains_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.contains(null));
    }

    @Test
    public void contains_reminderNotInList_returnsFalse() {
        assertFalse(uniqueReminderList.contains(REMINDER_1));
    }

    @Test
    public void contains_reminderInList_returnsTrue() {
        uniqueReminderList.add(REMINDER_1);
        assertTrue(uniqueReminderList.contains(REMINDER_1));
    }

    @Test
    public void contains_reminderWithSameDueDateOnlyInList_returnsFalse() {
        uniqueReminderList.add(REMINDER_1);
        Reminder editedReminder = new ReminderBuilder(REMINDER_1).withDescription(VALID_DESCRIPTION_2).build();
        assertFalse(uniqueReminderList.contains(editedReminder));
    }

    @Test
    public void add_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.add(null));
    }

    @Test
    public void add_duplicateReminder_throwsDuplicateReminderException() {
        uniqueReminderList.add(REMINDER_1);
        assertThrows(DuplicateReminderException.class, () -> uniqueReminderList.add(REMINDER_1));
    }

    @Test
    public void setReminder_nullTargetReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.setReminder(null, REMINDER_1));
    }

    @Test
    public void setReminder_nullEditedReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.setReminder(REMINDER_1, null));
    }

    @Test
    public void setReminder_targetReminderNotInList_throwsReminderNotFoundException() {
        assertThrows(ReminderNotFoundException.class, () -> uniqueReminderList.setReminder(REMINDER_1, REMINDER_1));
    }

    @Test
    public void setReminder_editedReminderIsSameReminder_success() {
        uniqueReminderList.add(REMINDER_1);
        uniqueReminderList.setReminder(REMINDER_1, REMINDER_1);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(REMINDER_1);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminder_editedReminderHasSameDueDate_success() {
        uniqueReminderList.add(REMINDER_1);
        Reminder editedReminder = new ReminderBuilder(REMINDER_1).withDescription(VALID_DESCRIPTION_2).build();
        uniqueReminderList.setReminder(REMINDER_1, editedReminder);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(editedReminder);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminder_editedReminderHasDifferentIdentity_success() {
        uniqueReminderList.add(REMINDER_1);
        uniqueReminderList.setReminder(REMINDER_1, REMINDER_2);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(REMINDER_2);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void remove_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.remove(null));
    }

    @Test
    public void remove_reminderDoesNotExist_throwsReminderNotFoundException() {
        assertThrows(ReminderNotFoundException.class, () -> uniqueReminderList.remove(REMINDER_1));
    }

    @Test
    public void remove_existingReminder_removesReminder() {
        uniqueReminderList.add(REMINDER_1);
        uniqueReminderList.remove(REMINDER_1);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminders_nullUniqueReminderList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.setReminders((UniqueReminderList) null));
    }

    @Test
    public void setReminders_uniqueReminderList_replacesOwnListWithProvidedUniqueReminderList() {
        uniqueReminderList.add(REMINDER_1);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(REMINDER_2);
        uniqueReminderList.setReminders(expectedUniqueReminderList);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminders_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.setReminders((List<Reminder>) null));
    }

    @Test
    public void setReminders_list_replacesOwnListWithProvidedList() {
        uniqueReminderList.add(REMINDER_1);
        List<Reminder> reminderList = Collections.singletonList(REMINDER_2);
        uniqueReminderList.setReminders(reminderList);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(REMINDER_2);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminders_listWithDuplicateReminders_throwsDuplicateReminderException() {
        List<Reminder> listWithDuplicateReminders = Arrays.asList(REMINDER_1, REMINDER_1);
        assertThrows(DuplicateReminderException.class, ()
                -> uniqueReminderList.setReminders(listWithDuplicateReminders));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueReminderList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueReminderList.asUnmodifiableObservableList().toString(), uniqueReminderList.toString());
    }
}

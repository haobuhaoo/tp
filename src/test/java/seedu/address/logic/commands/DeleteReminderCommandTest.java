package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_OUT_OF_BOUNDS_DELETE_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showReminderAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_REMINDER;
import static seedu.address.testutil.TypicalReminders.REMINDER_1;
import static seedu.address.testutil.TypicalReminders.REMINDER_2;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderFieldsContainsKeywordsPredicate;
import seedu.address.testutil.ReminderBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteReminderCommand}.
 */
public class DeleteReminderCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Reminder reminderToDelete = model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased());
        DeleteReminderCommand deleteCommand = new DeleteReminderCommand(INDEX_FIRST_REMINDER);

        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS,
                Messages.format(reminderToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteReminder(reminderToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReminderList().size() + 1);
        DeleteReminderCommand deleteCommand = new DeleteReminderCommand(outOfBoundIndex);

        String expectedMessage = String.format(MESSAGE_OUT_OF_BOUNDS_DELETE_INDEX,
                outOfBoundIndex.getOneBased(),
                model.getFilteredReminderList().size());

        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showReminderAtIndex(model, INDEX_FIRST_REMINDER);

        Reminder reminderToDelete = model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased());
        DeleteReminderCommand deleteCommand = new DeleteReminderCommand(INDEX_FIRST_REMINDER);

        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS,
                Messages.format(reminderToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteReminder(reminderToDelete);
        showNoReminder(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showReminderAtIndex(model, INDEX_FIRST_REMINDER);

        Index outOfBoundIndex = INDEX_SECOND_REMINDER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getReminderList().size());

        DeleteReminderCommand deleteCommand = new DeleteReminderCommand(outOfBoundIndex);

        String expectedMessage = String.format(MESSAGE_OUT_OF_BOUNDS_DELETE_INDEX,
                outOfBoundIndex.getOneBased(),
                model.getFilteredReminderList().size());

        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        DeleteReminderCommand deleteFirstCommand = new DeleteReminderCommand(INDEX_FIRST_REMINDER);
        DeleteReminderCommand deleteSecondCommand = new DeleteReminderCommand(INDEX_SECOND_REMINDER);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteReminderCommand deleteFirstCommandCopy = new DeleteReminderCommand(INDEX_FIRST_REMINDER);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different reminder command -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteReminderCommand deleteCommand = new DeleteReminderCommand(targetIndex);
        String expected = DeleteReminderCommand.class.getCanonicalName()
                + "{targetIndex=" + Optional.of(targetIndex) + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteReminderCommand((Index) null));
    }

    @Test
    public void execute_deleteLastReminder_success() {
        int lastIndex = model.getFilteredReminderList().size();
        Index targetIndex = Index.fromOneBased(lastIndex);
        Reminder reminderToDelete = model.getFilteredReminderList().get(lastIndex - 1);

        DeleteReminderCommand deleteCommand = new DeleteReminderCommand(targetIndex);

        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS,
                Messages.format(reminderToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteReminder(reminderToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteFromEmptyList_throwsCommandException() {
        // Clear all reminders
        model.updateFilteredReminderList(p -> false);
        assertTrue(model.getFilteredReminderList().isEmpty());

        DeleteReminderCommand deleteCommand = new DeleteReminderCommand(Index.fromOneBased(1));

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_keywordMatchesDueDate_deletesReminder() throws CommandException {
        Model model = new ModelManager();
        Reminder reminder = new ReminderBuilder(REMINDER_1).build();
        model.addReminder(reminder);

        DeleteReminderCommand command = new DeleteReminderCommand(
                new ReminderFieldsContainsKeywordsPredicate(List.of("21 Oct")));
        CommandResult result = command.execute(model);

        assertEquals(String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS, Messages.format(reminder)),
                result.getFeedbackToUser());
        assertFalse(model.getFilteredReminderList().contains(reminder));
    }

    @Test
    public void execute_keywordMatchesDescription_deletesReminder() throws CommandException {
        Model model = new ModelManager();
        Reminder reminder = new ReminderBuilder(REMINDER_1).build();
        model.addReminder(reminder);

        DeleteReminderCommand command = new DeleteReminderCommand(
                new ReminderFieldsContainsKeywordsPredicate(List.of("home")));
        CommandResult result = command.execute(model);

        assertEquals(String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS, Messages.format(reminder)),
                result.getFeedbackToUser());
        assertFalse(model.getFilteredReminderList().contains(reminder));
    }

    @Test
    public void execute_keywordNoMatch_returnsNoMatchMessage() throws CommandException {
        Model model = new ModelManager();
        Reminder reminder = new ReminderBuilder(REMINDER_1).build();
        model.addReminder(reminder);

        DeleteReminderCommand command = new DeleteReminderCommand(
                new ReminderFieldsContainsKeywordsPredicate(List.of("hello")));
        CommandResult result = command.execute(model);

        assertEquals(DeleteReminderCommand.MESSAGE_NO_MATCH, result.getFeedbackToUser());
        assertTrue(model.getAddressBook().getReminderList().contains(reminder));
    }

    @Test
    public void execute_keywordMultipleMatches_returnsMultipleMatchesMessage() throws CommandException {
        Model model = new ModelManager();
        Reminder reminder1 = new ReminderBuilder(REMINDER_1).build();
        Reminder reminder2 = new ReminderBuilder(REMINDER_2).build();
        model.addReminder(reminder1);
        model.addReminder(reminder2);

        DeleteReminderCommand command = new DeleteReminderCommand(
                new ReminderFieldsContainsKeywordsPredicate(List.of("oct")));
        CommandResult result = command.execute(model);

        String expectedMessageStart = DeleteReminderCommand.MESSAGE_MULTIPLE_MATCHES;
        assertTrue(result.getFeedbackToUser().startsWith(expectedMessageStart));
        assertTrue(result.getFeedbackToUser().contains(Messages.format(reminder1)));
        assertTrue(result.getFeedbackToUser().contains(Messages.format(reminder2)));
        assertTrue(model.getFilteredReminderList().contains(reminder1));
        assertTrue(model.getFilteredReminderList().contains(reminder2));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoReminder(Model model) {
        model.updateFilteredReminderList(p -> false);

        assertTrue(model.getFilteredReminderList().isEmpty());
    }
}

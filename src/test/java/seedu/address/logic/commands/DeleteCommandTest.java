package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_OUT_OF_BOUNDS_DELETE_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.PersonBuilder.DEFAULT_NAME;
import static seedu.address.testutil.PersonBuilder.DEFAULT_PHONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentFieldsContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        String expectedMessage = String.format(MESSAGE_OUT_OF_BOUNDS_DELETE_INDEX,
                outOfBoundIndex.getOneBased(),
                model.getFilteredPersonList().size());

        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        String expectedMessage = String.format(MESSAGE_OUT_OF_BOUNDS_DELETE_INDEX,
                outOfBoundIndex.getOneBased(),
                model.getFilteredPersonList().size());

        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + Optional.of(targetIndex) + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        try {
            new DeleteCommand((Index) null);
        } catch (NullPointerException e) {
            assertTrue(true);
            return;
        }
        assertFalse(true, "Expected NullPointerException was not thrown.");
    }

    @Test
    public void execute_deleteLastPerson_success() {
        int lastIndex = model.getFilteredPersonList().size();
        Index targetIndex = Index.fromOneBased(lastIndex);
        Person personToDelete = model.getFilteredPersonList().get(lastIndex - 1);

        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteFromEmptyList_throwsCommandException() {
        // Clear all persons
        model.updateFilteredPersonList(p -> false);
        assertTrue(model.getFilteredPersonList().isEmpty());

        DeleteCommand deleteCommand = new DeleteCommand(Index.fromOneBased(1));

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void equals_differentObjectSameIndex_returnsFalse() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        Object dummy = new Object();
        assertFalse(deleteFirstCommand.equals(dummy));
    }

    @Test
    public void toStringMethod_differentIndex_returnsDifferentStrings() {
        Index firstIndex = Index.fromOneBased(1);
        Index secondIndex = Index.fromOneBased(2);

        DeleteCommand firstCommand = new DeleteCommand(firstIndex);
        DeleteCommand secondCommand = new DeleteCommand(secondIndex);

        assertFalse(firstCommand.toString().equals(secondCommand.toString()));
    }

    @Test
    public void execute_keywordMatchesName_deletesPerson() throws CommandException {
        Model model = new ModelManager();
        Person person = new PersonBuilder().build();
        model.addPerson(person);

        DeleteCommand command = new DeleteCommand(
                new StudentFieldsContainsKeywordsPredicate(List.of(DEFAULT_NAME)));
        CommandResult result = command.execute(model);

        assertEquals(String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(person)),
                result.getFeedbackToUser());
        assertFalse(model.getFilteredPersonList().contains(person));
    }

    @Test
    public void execute_keywordMatchesPhone_deletesPerson() throws CommandException {
        Model model = new ModelManager();
        Person person = new PersonBuilder().build();
        model.addPerson(person);

        DeleteCommand command = new DeleteCommand(
                new StudentFieldsContainsKeywordsPredicate(List.of(DEFAULT_PHONE)));
        CommandResult result = command.execute(model);

        assertEquals(String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(person)),
                result.getFeedbackToUser());
        assertFalse(model.getFilteredPersonList().contains(person));
    }

    @Test
    public void execute_keywordMatchesLessonTime_deletesPerson() throws CommandException {
        Model model = new ModelManager();
        Person person = new PersonBuilder().build();
        model.addPerson(person);

        DeleteCommand command = new DeleteCommand(
                new StudentFieldsContainsKeywordsPredicate(List.of("10:00 am")));
        CommandResult result = command.execute(model);

        assertEquals(String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(person)),
                result.getFeedbackToUser());
        assertFalse(model.getFilteredPersonList().contains(person));
    }

    @Test
    public void execute_keywordNoMatch_returnsNoMatchMessage() throws CommandException {
        Model model = new ModelManager();
        Person person = new PersonBuilder().build();
        model.addPerson(person);

        DeleteCommand command = new DeleteCommand(
                new StudentFieldsContainsKeywordsPredicate(List.of("hello")));
        CommandResult result = command.execute(model);

        assertEquals(DeleteCommand.MESSAGE_NO_MATCH, result.getFeedbackToUser());
        assertTrue(model.getAddressBook().getPersonList().contains(person));
    }

    @Test
    public void execute_keywordMultipleMatches_returnsMultipleMatchesMessage() throws CommandException {
        Model model = new ModelManager();
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder()
                .withName("Amy Tan")
                .withPhone("88887777")
                .withLessonTime("1500 Wed")
                .build();
        model.addPerson(person1);
        model.addPerson(person2);

        DeleteCommand command = new DeleteCommand(
                new StudentFieldsContainsKeywordsPredicate(List.of("amy")));
        CommandResult result = command.execute(model);

        String expectedMessageStart = DeleteCommand.MESSAGE_MULTIPLE_MATCHES;
        assertTrue(result.getFeedbackToUser().startsWith(expectedMessageStart));
        assertTrue(result.getFeedbackToUser().contains(Messages.format(person1)));
        assertTrue(result.getFeedbackToUser().contains(Messages.format(person2)));
        assertTrue(model.getFilteredPersonList().contains(person1));
        assertTrue(model.getFilteredPersonList().contains(person2));
    }


    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}

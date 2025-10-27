package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class MarkUnpaidCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexAndMonth_success() throws Exception {
        Person paidAlice = new PersonBuilder()
                .withName("Alice Pauline")
                .withPhone("94351253")
                .withLessonTime("1000 Sat")
                .withPaymentStatus("111111111111")
                .build();

        paidAlice.setPaymentStatus(5, true);

        Model model = new ModelManager();
        model.addPerson(paidAlice);

        Index index = Index.fromOneBased(1);
        int month = 5;
        MarkUnpaidCommand markUnpaidCommand = new MarkUnpaidCommand(index, month);
        markUnpaidCommand.execute(model);

        assertFalse(model.getFilteredPersonList().get(0).isPaidForMonth(5));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkUnpaidCommand markUnpaidCommand = new MarkUnpaidCommand(outOfBoundIndex, 5);

        assertCommandFailure(markUnpaidCommand, model, "Invalid student index provided.");
    }

    @Test
    public void execute_invalidMonthTooLow_throwsCommandException() {
        MarkUnpaidCommand markUnpaidCommand = new MarkUnpaidCommand(INDEX_FIRST_PERSON, 0);
        assertCommandFailure(markUnpaidCommand, model, MarkUnpaidCommand.MESSAGE_INVALID_MONTH);
    }

    @Test
    public void execute_invalidMonthTooHigh_throwsCommandException() {
        MarkUnpaidCommand markUnpaidCommand = new MarkUnpaidCommand(INDEX_FIRST_PERSON, 13);
        assertCommandFailure(markUnpaidCommand, model, MarkUnpaidCommand.MESSAGE_INVALID_MONTH);
    }

    @Test
    public void execute_alreadyUnpaid_throwsCommandException() throws Exception {
        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        personToMark.setPaymentStatus(6, false);

        MarkUnpaidCommand markUnpaidCommand = new MarkUnpaidCommand(INDEX_FIRST_PERSON, 6);

        String expectedMessage = String.format(MarkUnpaidCommand.MESSAGE_ALREADY_UNPAID,
                personToMark.getName(), "June");

        assertCommandFailure(markUnpaidCommand, model, expectedMessage);
    }

    @Test
    public void execute_allMonths_success() throws Exception {
        for (int month = 1; month <= 12; month++) {
            Model testModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
            Person personToMark = testModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

            personToMark.setPaymentStatus(month, true);

            MarkUnpaidCommand markUnpaidCommand = new MarkUnpaidCommand(INDEX_FIRST_PERSON, month);

            CommandResult result = markUnpaidCommand.execute(testModel);
            assertFalse(personToMark.isPaidForMonth(month));
        }
    }

    @Test
    public void equals() {
        MarkUnpaidCommand markUnpaidFirst = new MarkUnpaidCommand(INDEX_FIRST_PERSON, 5);
        MarkUnpaidCommand markUnpaidSecond = new MarkUnpaidCommand(Index.fromOneBased(2), 5);
        MarkUnpaidCommand markUnpaidDifferentMonth = new MarkUnpaidCommand(INDEX_FIRST_PERSON, 6);

        // same returns true
        assertTrue(markUnpaidFirst.equals(markUnpaidFirst));

        MarkUnpaidCommand markUnpaidFirstCopy = new MarkUnpaidCommand(INDEX_FIRST_PERSON, 5);
        assertTrue(markUnpaidFirst.equals(markUnpaidFirstCopy));

        // different returns false
        assertFalse(markUnpaidFirst.equals(1));

        assertFalse(markUnpaidFirst.equals(null));

        assertFalse(markUnpaidFirst.equals(markUnpaidSecond));

        assertFalse(markUnpaidFirst.equals(markUnpaidDifferentMonth));
    }
}

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

public class MarkPaidCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexAndMonth_success() throws Exception {
        Person unpaidAlice = new PersonBuilder()
                .withName("Alice Pauline")
                .withPhone("94351253")
                .withLessonTime("1000 Sat")
                .withPaymentStatus("000000000000")
                .build();

        Model model = new ModelManager();
        model.addPerson(unpaidAlice);

        Index index = Index.fromOneBased(1);
        int month = 5;
        MarkPaidCommand markPaidCommand = new MarkPaidCommand(index, month);
        markPaidCommand.execute(model);

        assertTrue(model.getFilteredPersonList().get(0).isPaidForMonth(5));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkPaidCommand markPaidCommand = new MarkPaidCommand(outOfBoundIndex, 5);

        assertCommandFailure(markPaidCommand, model, "Invalid student index provided.");
    }

    @Test
    public void execute_invalidMonthTooLow_throwsCommandException() {
        MarkPaidCommand markPaidCommand = new MarkPaidCommand(INDEX_FIRST_PERSON, 0);
        assertCommandFailure(markPaidCommand, model, MarkPaidCommand.MESSAGE_INVALID_MONTH);
    }

    @Test
    public void execute_invalidMonthTooHigh_throwsCommandException() {
        MarkPaidCommand markPaidCommand = new MarkPaidCommand(INDEX_FIRST_PERSON, 13);
        assertCommandFailure(markPaidCommand, model, MarkPaidCommand.MESSAGE_INVALID_MONTH);
    }

    @Test
    public void execute_alreadyPaid_throwsCommandException() throws Exception {
        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        personToMark.setPaymentStatus(6, true);

        MarkPaidCommand markPaidCommand = new MarkPaidCommand(INDEX_FIRST_PERSON, 6);

        String expectedMessage = String.format(MarkPaidCommand.MESSAGE_ALREADY_PAID,
                personToMark.getName(), "June");

        assertCommandFailure(markPaidCommand, model, expectedMessage);
    }

    @Test
    public void execute_allMonths_success() throws Exception {
        for (int month = 1; month <= 12; month++) {
            Model testModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
            Person personToMark = testModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
            MarkPaidCommand markPaidCommand = new MarkPaidCommand(INDEX_FIRST_PERSON, month);

            CommandResult result = markPaidCommand.execute(testModel);
            assertTrue(personToMark.isPaidForMonth(month));
        }
    }

    @Test
    public void equals() {
        MarkPaidCommand markPaidFirst = new MarkPaidCommand(INDEX_FIRST_PERSON, 5);
        MarkPaidCommand markPaidSecond = new MarkPaidCommand(Index.fromOneBased(2), 5);
        MarkPaidCommand markPaidDifferentMonth = new MarkPaidCommand(INDEX_FIRST_PERSON, 6);

        assertTrue(markPaidFirst.equals(markPaidFirst));

        MarkPaidCommand markPaidFirstCopy = new MarkPaidCommand(INDEX_FIRST_PERSON, 5);
        assertTrue(markPaidFirst.equals(markPaidFirstCopy));

        assertFalse(markPaidFirst.equals(1));

        assertFalse(markPaidFirst.equals(null));

        assertFalse(markPaidFirst.equals(markPaidSecond));

        assertFalse(markPaidFirst.equals(markPaidDifferentMonth));
    }
}
